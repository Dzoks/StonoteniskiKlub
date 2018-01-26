package application.gui.sekretar.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.ZaposleniDTO;
import application.model.dto.ZaposleniTipDTO;
import application.model.dto.ZaposlenjeDTO;
import application.util.AlertDisplay;
import application.util.ErrorLogger;
import application.util.InputValidator;
import application.util.TextUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
	@FXML
	private ListView<String> lstTelefoni;
	@FXML
	private Button btnUkloniTelefon;
	@FXML
	private Button btnUkloniFotografiju;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		populateComboBoxes();
		bindDisable();
		try {
			defaultImage = new Image(new FileInputStream("resources/avatar.png"));
			imgFotografija.setImage(defaultImage);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		}
	}

	// Event Listener on Button[#btnDodajBrojTelefona].onAction
	@FXML
	public void dodajBrojTelefona(ActionEvent event) {
		String brTelefona = tfBrojTelefona.getText();
		if (InputValidator.validateTelefon(brTelefona)) {
			ObservableList<String> telefoni = lstTelefoni.getItems();
			if (!telefoni.contains(brTelefona)) {
				telefoni.add(brTelefona);
				if (!uklonjeniTelefoni.contains(brTelefona)) {
					noviTelefoni.add(brTelefona);
				}
			}
			if (uklonjeniTelefoni.contains(brTelefona)) {
				uklonjeniTelefoni.remove(telefoni);
			}
		}
	}

	@FXML
	public void ukloniTelefon(ActionEvent event) {
		String telefon = lstTelefoni.getSelectionModel().getSelectedItem();
		lstTelefoni.getItems().remove(lstTelefoni.getSelectionModel().getSelectedIndex());
		uklonjeniTelefoni.add(telefon);
	}

	// Event Listener on Button[#btnDodajFotografiju].onAction
	@FXML
	public void dodajFotografiju(ActionEvent event) {
		File file = TextUtility.configureFileChooser("Izaberite fotografiju", TextUtility.IMAGE_EXTENSIONS)
				.showOpenDialog(this.primaryStage);
		if (file != null) {
			fotografijaLik = file;
			try {
				promjena = true;
				imgFotografija.setImage(new Image(new FileInputStream(file)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				new ErrorLogger().log(e);
			}
		}
	}

	@FXML
	public void ukloniFotografiju(ActionEvent event) {
		imgFotografija.setImage(defaultImage);
		fotografijaLik = null;
		promjena = true;
	}

	// Event Listener on Button[#btnSacuvajPodatke].onAction
	@FXML
	public void sacuvaj(ActionEvent event) {

		if ((tip == AZURIRANJE_ZAPOSLENOG && !InputValidator.allEntered(txtIme.getText(), txtPrezime.getText(),
				txtImeRoditelja.getText(), txtJMB.getText(), dpDatumRodjenja.getValue()))
				|| (tip == DODAVANJE_ZAPOSLENJA
						&& !InputValidator.allEntered(dpZaposlenOd.getValue(), txtPlata.getText()))
				|| (tip == DODAVANJE_ZAPOSLENOG && !InputValidator.allEntered(txtIme.getText(), txtPrezime.getText(),
						txtImeRoditelja.getText(), txtJMB.getText(), dpDatumRodjenja.getValue(),
						dpZaposlenOd.getValue(), txtPlata.getText()))) {
			AlertDisplay.showError("Dodavanje", "Niste unijeli sve podatke!");
		} else {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date datumRodjenja = null;
			Date datumOd = null;
			Date datumDo = null;
			try {
				if (dpDatumRodjenja.getValue() != null) {
					datumRodjenja = formatter.parse(dpDatumRodjenja.getValue().toString());
				}
				if (dpZaposlenOd.getValue() != null) {
					datumOd = formatter.parse(dpZaposlenOd.getValue().toString());
				}
				if (dpZaposlenDo.getValue() != null) {
					datumDo = formatter.parse(dpZaposlenDo.getValue().toString());
				}
			} catch (ParseException e1) {
				e1.printStackTrace();
				new ErrorLogger().log(e1);
			}
			if (tip == AZURIRANJE_ZAPOSLENOG) {
				this.zaposleniZaAzurirati.setIme(txtIme.getText());
				this.zaposleniZaAzurirati.setImeRoditelja(txtImeRoditelja.getText());
				this.zaposleniZaAzurirati.setPrezime(txtPrezime.getText());
				this.zaposleniZaAzurirati.setJmb(txtJMB.getText());
				this.zaposleniZaAzurirati.setDatumRodjenja(datumRodjenja);
				this.zaposleniZaAzurirati.setPol(rbMuskiPol.isSelected() ? 'M' : 'Z');
				this.zaposleniZaAzurirati.setTelefoni(lstTelefoni.getItems());
				if (promjena) {
					try {
						if (fotografijaLik != null) {
							this.zaposleniZaAzurirati.setSlika(TextUtility.convertImageToBlob(fotografijaLik));
						} else {
							this.zaposleniZaAzurirati.setSlika(null);
						}
					} catch (IOException e) {
						e.printStackTrace();
						new ErrorLogger().log(e);
					}
				}
				DAOFactory.getDAOFactory().getOsobaDAO().update(zaposleniZaAzurirati);
				parent.zamijeni(zaposleniZaAzurirati);
				AlertDisplay.showInformation("Izmjena", "Zaposleni uspješno ažuriran.");
				for (String telefon : noviTelefoni) {
					DAOFactory.getDAOFactory().getOsobaDAO().insertTel(telefon, zaposleniZaAzurirati);
				}
			} else if (tip == DODAVANJE_ZAPOSLENJA) {
				ZaposlenjeDTO zaposlenje = new ZaposlenjeDTO(
						cmbRadnoMjesto.getSelectionModel().getSelectedItem().getId(),
						cmbRadnoMjesto.getSelectionModel().getSelectedItem().getTip(), datumOd, datumDo,
						Double.parseDouble(txtPlata.getText()));
				if (DAOFactory.getDAOFactory().getZaposlenjeDAO().insert(zaposleniZaAzurirati, zaposlenje)) {
					zaposleniZaAzurirati.getZaposljenja().add(zaposlenje);
					AlertDisplay.showInformation("Dodavanje", "Zaposlenje uspješno dodano");
				} else {
					AlertDisplay.showError("Dodavanje", "Nešto nije u redu.");
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
								slika = TextUtility.convertImageToBlob(fotografijaLik);
							} catch (IOException e) {
								e.printStackTrace();
								new ErrorLogger().log(e);
							}
						}
						ZaposleniDTO zaposleni = new ZaposleniDTO(null, txtIme.getText(), txtPrezime.getText(),
								txtImeRoditelja.getText(), txtJMB.getText(), pol, datumRodjenja, slika,
								lstTelefoni.getItems(), true, null);
						if (DAOFactory.getDAOFactory().getZaposleniDAO().insert(zaposleni, zaposlenje, tip)) {
							zaposleni.setZaposljenja(FXCollections.observableArrayList());
							zaposleni.getZaposljenja().add(zaposlenje);
							AlertDisplay.showInformation("Dodavanje", "Zaposleni uspješno dodan.");
							parent.dodajZaposlenog(zaposleni);
							for (String telefon : noviTelefoni) {
								DAOFactory.getDAOFactory().getOsobaDAO().insertTel(telefon, zaposleni);
							}
						} else {
							AlertDisplay.showError("Dodavanje", "Nešto nije u redu");
						}

					} else {
						AlertDisplay.showError("Dodavanje", "Pogrešan format podatka za platu.");
					}
				} else {
					AlertDisplay.showError("Dodavanje", "Pogrešan format JMB-a.");
				}
			}
		}
		for (String tel : uklonjeniTelefoni) {
			DAOFactory.getDAOFactory().getSponzorDAO().deleteTelefon(tel);
		}
	}

	public void setParent(RadSaZaposlenimaController parent) {
		this.parent = parent;
	}

	public void setTip(int tip) {
		this.tip = tip;
		if (tip == AZURIRANJE_ZAPOSLENOG) {
			disableZaposlenje();
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
				new ErrorLogger().log(e);
			}
		}
		if (zaposleniZaAzurirati.getPol().equals("M")) {
			rbMuskiPol.setSelected(true);
		} else {
			rbZenskiPol.setSelected(false);
		}
		if (zaposleniZaAzurirati.getTelefoni() != null && zaposleniZaAzurirati.getTelefoni().size() > 0) {
			for (String telefon : zaposleniZaAzurirati.getTelefoni()) {
				lstTelefoni.getItems().add(telefon);
			}
		}
	}

	public void setDisabled(Node... items) {
		for (int i = 0; i < items.length; i++) {
			items[i].setDisable(true);
		}
	}

	public void disableZaposleni() {
		setDisabled(txtIme, txtPrezime, txtImeRoditelja, txtJMB, dpDatumRodjenja, tfBrojTelefona, btnDodajBrojTelefona,
				btnDodajFotografiju, rbMuskiPol, rbZenskiPol, btnUkloniFotografiju, lstTelefoni);
	}

	public void disableZaposlenje() {
		setDisabled(dpZaposlenDo, dpZaposlenOd, cmbRadnoMjesto, txtPlata);
	}

	private void populateComboBoxes() {
		cmbRadnoMjesto.setItems(DAOFactory.getDAOFactory().getZaposleniTipDAO().selectAll());
		cmbRadnoMjesto.getSelectionModel().select(0);
	}

	private void bindDisable() {
		btnUkloniTelefon.disableProperty().bind(lstTelefoni.getSelectionModel().selectedItemProperty().isNull());
	}

	private Image defaultImage;
	private RadSaZaposlenimaController parent;
	private File fotografijaLik = null;
	private boolean promjena = false;
	private int tip;
	private ZaposleniDTO zaposleniZaAzurirati;
	private ObservableList<String> uklonjeniTelefoni = FXCollections.observableArrayList();
	private ObservableList<String> noviTelefoni = FXCollections.observableArrayList();
}
