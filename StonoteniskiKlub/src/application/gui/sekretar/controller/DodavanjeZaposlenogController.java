package application.gui.sekretar.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.scene.control.ToggleGroup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.bouncycastle.jcajce.provider.keystore.BC;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dao.OsobaDAO;
import application.model.dto.ZaposleniDTO;
import application.model.dto.ZaposleniTipDTO;
import application.model.dto.ZaposlenjeDTO;
import application.util.AlertDisplay;
import application.util.ConnectionPool;
import application.util.InputValidator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.RadioButton;

import javafx.scene.control.DatePicker;

public class DodavanjeZaposlenogController extends BaseController {

	public static final int DODAVANJE_ZAPOSLENOG = 1;
	public static final int DODAVANJE_ZAPOSLENJA = 2;
	public static final int AZURIRANJE_ZAPOSLENOG = 3;

	@FXML
	private ImageView imgFotografija;
	@FXML
	private TextField txtIme;
	@FXML
	private TextField txtPrezime;
	@FXML
	private TextField txtImeRoditelja;
	@FXML
	private TextField txtJMB;
	@FXML
	private TextField tfBrojTelefona;
	@FXML
	private Button btnDodajBrojTelefona;
	@FXML
	private DatePicker dpDatumRodjenja;
	@FXML
	private RadioButton rbMuskiPol;
	@FXML
	private ToggleGroup groupPol;
	@FXML
	private RadioButton rbZenskiPol;
	@FXML
	private Button btnDodajFotografiju;
	@FXML
	private DatePicker dpZaposlenOd;
	@FXML
	private DatePicker dpZaposlenDo;
	@FXML
	private ComboBox<ZaposleniTipDTO> cmbRadnoMjesto;
	@FXML
	private TextField txtPlata;
	@FXML
	private Button btnSacuvajPodatke;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cmbRadnoMjesto.setItems(DAOFactory.getDAOFactory().getZaposleniTipDAO().selectAll());
		cmbRadnoMjesto.getSelectionModel().select(0);
	}

	// Event Listener on Button[#btnDodajBrojTelefona].onAction
	@FXML
	public void dodajBrojTelefona(ActionEvent event) {
		// TODO Autogenerated
	}

	// Event Listener on Button[#btnDodajFotografiju].onAction
	@FXML
	public void dodajFotografiju(ActionEvent event) {
		imgChooser.setTitle("Izaberite fotografiju");
		imgChooser.setSelectedExtensionFilter(new ExtensionFilter("slike", ".jpg", ".jpeg", ".bmp", ".gif"));
		File file = imgChooser.showOpenDialog(this.primaryStage);
		if (file != null) {
			System.out.println(file.getAbsolutePath());
			fotografijaLik = file;
			try {
				imgFotografija.setImage(new Image(new FileInputStream(file)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	// Event Listener on Button[#btnSacuvajPodatke].onAction
	@FXML
	public void sacuvaj(ActionEvent event) {

		if ((tip == AZURIRANJE_ZAPOSLENOG && !InputValidator.allEntered(txtIme.getText(), txtPrezime.getText(),
				txtImeRoditelja.getText(), txtJMB.getText(), dpDatumRodjenja.getValue()))
				|| (tip == DODAVANJE_ZAPOSLENJA && !InputValidator.allEntered(dpZaposlenOd.getValue(),
						dpZaposlenDo.getValue(), txtPlata.getText()))
				|| (tip == DODAVANJE_ZAPOSLENOG && !InputValidator.allEntered(txtIme.getText(), txtPrezime.getText(),
						txtImeRoditelja.getText(), txtJMB.getText(), dpDatumRodjenja.getValue(),
						dpZaposlenOd.getValue(), txtPlata.getText()))) {
			AlertDisplay.showInformation("Greska", "", "Niste unijeli sve podatke");
		} else {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date datumRodjenja = null;
			Date datumOd = null;
			Date datumDo = null;
			try {
				if(dpDatumRodjenja.getValue() != null){
					datumRodjenja = formatter.parse(dpDatumRodjenja.getValue().toString());
				}
				if(dpZaposlenOd.getValue() != null){
					datumOd = formatter.parse(dpZaposlenOd.getValue().toString());
				}
				if (dpZaposlenDo.getValue() != null) {
					datumDo = formatter.parse(dpZaposlenDo.getValue().toString());
				}
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			if (tip == AZURIRANJE_ZAPOSLENOG) {
				this.zaposleniZaAzurirati.setIme(txtIme.getText());
				this.zaposleniZaAzurirati.setImeRoditelja(txtImeRoditelja.getText());
				this.zaposleniZaAzurirati.setPrezime(txtPrezime.getText());
				this.zaposleniZaAzurirati.setJmb(txtJMB.getText());
				this.zaposleniZaAzurirati.setDatumRodjenja(datumRodjenja);
				this.zaposleniZaAzurirati.setPol(rbMuskiPol.isSelected() ? 'M' : 'Z');
				try {
					if (fotografijaLik != null) {
						this.zaposleniZaAzurirati.setSlika(convertImageToBlob(fotografijaLik));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				OsobaDAO.update(zaposleniZaAzurirati);
				parent.zamijeni(zaposleniZaAzurirati);
				AlertDisplay.showInformation("Uspjesno", "", "Zaposleni uspjesno azuriran");
			} else if (tip == DODAVANJE_ZAPOSLENJA) {
				ZaposlenjeDTO zaposlenje = new ZaposlenjeDTO(
						cmbRadnoMjesto.getSelectionModel().getSelectedItem().getId(),
						cmbRadnoMjesto.getSelectionModel().getSelectedItem().getTip(), datumOd, datumDo,
						Double.parseDouble(txtPlata.getText()));
				if (DAOFactory.getDAOFactory().getZaposlenjeDAO().insert(zaposleniZaAzurirati, zaposlenje)) {
					zaposleniZaAzurirati.getZaposljenja().add(zaposlenje);
					AlertDisplay.showInformation("Uspjesno", "", "Zaposlenje uspjesno dodano");
				} else {
					AlertDisplay.showInformation("Greska", "", "Greska pri dodavanju");
				}
			} else {
				if (InputValidator.validateJMB(txtJMB.getText())) {
					if (InputValidator.validateDouble(txtPlata.getText())) {

						ZaposleniTipDTO tip = cmbRadnoMjesto.getValue();
						ZaposlenjeDTO zaposlenje = new ZaposlenjeDTO(tip.getId(), tip.getTip(), datumOd, datumDo,
								Double.parseDouble(txtPlata.getText()));
						char pol = rbMuskiPol.isSelected() ? 'M' : 'Z';
						Blob slika = null;
						if (fotografijaLik != null) {
							try {
								slika = convertImageToBlob(fotografijaLik);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						ZaposleniDTO zaposleni = new ZaposleniDTO(null, txtIme.getText(), txtPrezime.getText(),
								txtImeRoditelja.getText(), txtJMB.getText(), pol, datumRodjenja, slika, null, true,
								null);
						if (DAOFactory.getDAOFactory().getZaposleniDAO().insert(zaposleni, zaposlenje, tip)) {
							zaposleni.setZaposljenja(FXCollections.observableArrayList());
							zaposleni.getZaposljenja().add(zaposlenje);
							AlertDisplay.showInformation("Uspjesno", "", "Zaposleni uspjesno dodan!");
							parent.dodajZaposlenog(zaposleni);
						} else {
							AlertDisplay.showInformation("Greska", "", "Greska pri dodavanju");
						}

					} else {
						AlertDisplay.showInformation("Greska", "", "Pogresan format podatka za platu.");
					}
				} else {
					AlertDisplay.showInformation("Greska", "", "Pogresan format JMB-a.");
				}
			}
		}
	}

	private FileChooser imgChooser = new FileChooser();

	public void setParent(RadSaZaposlenimaController parent) {
		this.parent = parent;
	}

	public void setTip(int tip) {
		this.tip = tip;
		if (tip == AZURIRANJE_ZAPOSLENOG) {
			disableZaposlenje();
			;
		} else if (tip == DODAVANJE_ZAPOSLENJA) {
			disableZaposleni();
		}
	}

	public void setZaposleni(ZaposleniDTO zaposleniZaAzurirati) {
		this.zaposleniZaAzurirati = zaposleniZaAzurirati;
		txtIme.setText(zaposleniZaAzurirati.getIme());
		txtImeRoditelja.setText(zaposleniZaAzurirati.getImeRoditelja());
		txtPrezime.setText(zaposleniZaAzurirati.getPrezime());
		txtJMB.setText(zaposleniZaAzurirati.getJmb());
		dpDatumRodjenja.setValue(new java.sql.Date(zaposleniZaAzurirati.getDatumRodjenja().getTime()).toLocalDate());
		if (zaposleniZaAzurirati.getSlika() != null) {
			try {
				imgFotografija.setImage(new Image(zaposleniZaAzurirati.getSlika().getBinaryStream()));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (zaposleniZaAzurirati.getPol().equals("M")) {
			rbMuskiPol.setSelected(true);
		} else {
			rbZenskiPol.setSelected(false);
		}
	}

	public void setDisabled(Node... items) {
		for (int i = 0; i < items.length; i++) {
			items[i].setDisable(true);
		}
	}

	public void disableZaposleni() {
		setDisabled(txtIme, txtPrezime, txtImeRoditelja, txtJMB, dpDatumRodjenja, tfBrojTelefona, btnDodajBrojTelefona,
				btnDodajFotografiju, rbMuskiPol, rbZenskiPol);
	}

	public void disableZaposlenje() {
		setDisabled(dpZaposlenDo, dpZaposlenOd, cmbRadnoMjesto, txtPlata);
	}

	private Blob convertImageToBlob(File fotografija) throws IOException {
		if (fotografija == null)
			return null;
		Connection conn;
		try {
			conn = ConnectionPool.getInstance().checkOut();
			Blob blob = conn.createBlob();

			blob.setBytes(1, Files.readAllBytes(fotografija.toPath()));
			return blob;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private RadSaZaposlenimaController parent;
	private File fotografijaLik = null;
	private int tip;
	private ZaposleniDTO zaposleniZaAzurirati;
}