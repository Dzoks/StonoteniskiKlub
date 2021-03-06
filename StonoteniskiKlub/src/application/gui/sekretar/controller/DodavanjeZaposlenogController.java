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
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dao.mysql.MySQLZaposleniDAO;
import application.model.dto.OsobaDTO;
import application.model.dto.ZaposleniDTO;
import application.model.dto.ZaposleniTipDTO;
import application.model.dto.ZaposlenjeDTO;
import application.util.AlertDisplay;
import application.util.ErrorLogger;
import application.util.GUIUtility;
import application.util.InputValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

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

			defaultImage = new Image(getClass().getResourceAsStream("/avatar.png"));
			imgFotografija.setImage(defaultImage);

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
		File file = GUIUtility.configureFileChooser("Izaberite fotografiju", GUIUtility.IMAGE_EXTENSIONS)
				.showOpenDialog(this.primaryStage);
		if (file != null) {
			fotografijaLik = file;
			try {
				promjena = true;
				imgFotografija.setImage(new Image(new FileInputStream(file)));
			} catch (FileNotFoundException e) {
				 ;
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
		if (tip == DODAVANJE_ZAPOSLENOG) {
			dodajNovog();
		} else if (tip == DODAVANJE_ZAPOSLENJA) {
			dodajZaposlenje();
		} else if (tip == AZURIRANJE_ZAPOSLENOG) {
			azuriraj();
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
				 ;
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

	private void azuriraj() {
		if (!InputValidator.allEntered(txtIme.getText(), txtPrezime.getText(), txtImeRoditelja.getText(),
				txtJMB.getText(), dpDatumRodjenja.getValue())) {
			AlertDisplay.showError("Dodavanje", "Niste unijeli sve podatke!");
		} else if (!InputValidator.validateDate(dpDatumRodjenja.getValue(), 18)) {
			AlertDisplay.showError("Dodavanje", "Zaposleni mora biti punoljetan!");
		} else if (!InputValidator.validateJMB(txtJMB.getText())) {
			AlertDisplay.showError("Dodavanje", "Pogrešan format JMB-a.");
		} else {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date datumRodjenja = null;
			try {
				if (dpDatumRodjenja.getValue() != null) {
					datumRodjenja = formatter.parse(dpDatumRodjenja.getValue().toString());
				}
			} catch (ParseException e1) {
				e1.printStackTrace();
				new ErrorLogger().log(e1);
			}

			this.zaposleniZaAzurirati.setIme(txtIme.getText());
			this.zaposleniZaAzurirati.setImeRoditelja(txtImeRoditelja.getText());
			this.zaposleniZaAzurirati.setPrezime(txtPrezime.getText());
			this.zaposleniZaAzurirati.setJmb(txtJMB.getText());
			this.zaposleniZaAzurirati.setDatumRodjenja(datumRodjenja);
			this.zaposleniZaAzurirati.setPol(rbMuskiPol.isSelected() ? 'M' : 'Ž');
			this.zaposleniZaAzurirati.setTelefoni(lstTelefoni.getItems());
			if (promjena) {
				try {
					if (fotografijaLik != null) {
						this.zaposleniZaAzurirati.setSlika(GUIUtility.convertImageToBlob(fotografijaLik));
					} else {
						this.zaposleniZaAzurirati.setSlika(null);
					}
				} catch (IOException e) {
					 ;
					new ErrorLogger().log(e);
				}
			}
			DAOFactory.getDAOFactory().getOsobaDAO().update(zaposleniZaAzurirati);
			parent.zamijeni(zaposleniZaAzurirati);
			AlertDisplay.showInformation("Izmjena", "Zaposleni uspješno ažuriran.");
			for (String telefon : noviTelefoni) {
				DAOFactory.getDAOFactory().getOsobaDAO().insertTel(telefon, zaposleniZaAzurirati);
			}
			for (String tel : uklonjeniTelefoni) {
				DAOFactory.getDAOFactory().getSponzorDAO().deleteTelefon(tel);
			}
			Stage stage = (Stage) btnSacuvajPodatke.getScene().getWindow();
			stage.close();
		}
	}

	private void dodajNovog() {
		if (!InputValidator.allEntered(txtIme.getText(), txtPrezime.getText(), txtImeRoditelja.getText(),
				txtJMB.getText(), dpDatumRodjenja.getValue(), dpZaposlenOd.getValue(), txtPlata.getText())) {
			AlertDisplay.showError("Dodavanje", "Niste unijeli sve podatke!");
		} else if (!InputValidator.validateDate(dpDatumRodjenja.getValue(), 18)) {
			AlertDisplay.showError("Dodavanje", "Zaposleni mora biti punoljetan!");
		} else if (dpZaposlenOd.getValue().compareTo(LocalDate.now()) >= 0) {
			AlertDisplay.showError("Dodavanje", "Datum od mora biti prije današnjeg!");
		} else if (dpZaposlenDo.getValue() != null && dpZaposlenOd.getValue().compareTo(dpZaposlenDo.getValue()) > 0) {
			AlertDisplay.showError("Dodavanje", "Datum od mora biti prije datuma do!");
		} else if (!InputValidator.validateJMB(txtJMB.getText())) {
			AlertDisplay.showError("Dodavanje", "Pogrešan format JMB-a.");
		} else if (!InputValidator.validateDouble(txtPlata.getText())) {
			AlertDisplay.showError("Dodavanje", "Pogrešan format podatka za platu.");
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
			ZaposleniTipDTO tip = cmbRadnoMjesto.getValue();
			ZaposlenjeDTO zaposlenje = new ZaposlenjeDTO(tip.getId(), tip.getTip(), datumOd, datumDo,
					Double.parseDouble(txtPlata.getText()));
			char pol = rbMuskiPol.isSelected() ? 'M' : 'Ž';
			Blob slika = null;
			if (fotografijaLik != null) {
				try {
					slika = GUIUtility.convertImageToBlob(fotografijaLik);
				} catch (IOException e) {
					 ;
					new ErrorLogger().log(e);
				}
			}
			ZaposleniDTO zaposleni = new ZaposleniDTO(null, txtIme.getText(), txtPrezime.getText(),
					txtImeRoditelja.getText(), txtJMB.getText(), pol, datumRodjenja, slika, lstTelefoni.getItems(),
					true, null);
			Integer uspjesno = DAOFactory.getDAOFactory().getZaposleniDAO().insert(zaposleni, zaposlenje, tip);
			if (uspjesno > -1) {
				zaposleni.setZaposljenja(FXCollections.observableArrayList());
				zaposleni.getZaposljenja().add(zaposlenje);
				AlertDisplay.showInformation("Dodavanje", "Zaposleni uspješno dodan.");
				parent.dodajZaposlenog(zaposleni);
				for (String telefon : noviTelefoni) {
					DAOFactory.getDAOFactory().getOsobaDAO().insertTel(telefon, zaposleni);
				}
				for (String tel : uklonjeniTelefoni) {
					DAOFactory.getDAOFactory().getSponzorDAO().deleteTelefon(tel);
				}
				Stage stage = (Stage) btnSacuvajPodatke.getScene().getWindow();
				stage.close();
			} else if (uspjesno == MySQLZaposleniDAO.DUPLICATE_KEY) {
				OsobaDTO osoba = DAOFactory.getDAOFactory().getOsobaDAO().getByJmb(zaposleni.getJmb());
				zaposleni = DAOFactory.getDAOFactory().getZaposleniDAO().selectById(osoba.getId(), true);
				if (zaposleni != null) {
					Optional<ButtonType> opcija = AlertDisplay.showConfirmation("Dodavanje",
							"Zaposleni vec postoji! Zelite li da dodate zaposlenje?");
					if (opcija.get().getButtonData().equals(ButtonData.YES)) {
						if (DAOFactory.getDAOFactory().getZaposlenjeDAO().insert(zaposleni, zaposlenje)) {
							zaposleni.getZaposljenja().add(zaposlenje);
							AlertDisplay.showInformation("Dodavanje", "Zaposleni uspješno dodan.");
							parent.zamijeni(zaposleni);
							for (String telefon : noviTelefoni) {
								DAOFactory.getDAOFactory().getOsobaDAO().insertTel(telefon, zaposleni);
							}
							for (String tel : uklonjeniTelefoni) {
								DAOFactory.getDAOFactory().getSponzorDAO().deleteTelefon(tel);
							}
							Stage stage = (Stage) btnSacuvajPodatke.getScene().getWindow();
							stage.close();
						} else {
							AlertDisplay.showError("Dodavanje", "Nešto nije u redu");
						}
					}
				} else {
					Optional<ButtonType> opcija = AlertDisplay.showConfirmation("Dodavanje",
							"Osoba vec postoji, ali nije zaposlena! Zelite li da dodate zaposlenog?");
					if (opcija.get().getButtonData().equals(ButtonData.YES)) {
						zaposlenje = new ZaposlenjeDTO(cmbRadnoMjesto.getSelectionModel().getSelectedItem().getId(),
								cmbRadnoMjesto.getSelectionModel().getSelectedItem().getTip(), datumOd, datumDo,
								Double.parseDouble(txtPlata.getText()));
						ZaposleniDTO zap = DAOFactory.getDAOFactory().getZaposleniDAO().selectById(osoba.getId(),
								false);
						boolean uspjeh = false;
						if (zap != null) {
							zaposleni = zap;
							zaposleni.getZaposljenja().add(zaposlenje);
							uspjeh = DAOFactory.getDAOFactory().getZaposlenjeDAO().insert(zaposleni, zaposlenje)
									&& DAOFactory.getDAOFactory().getZaposleniDAO().delete(zaposleni, true);
						} else {
							zaposleni = new ZaposleniDTO(osoba.getId(), osoba.getIme(), osoba.getPrezime(),
									osoba.getImeRoditelja(), osoba.getJmb(), osoba.getPol(), osoba.getDatumRodjenja(),
									osoba.getSlika(), osoba.getTelefoni(), true, null);
							zaposleni.setZaposljenja(FXCollections.observableArrayList());
							zaposleni.getZaposljenja().add(zaposlenje);
							uspjeh = DAOFactory.getDAOFactory().getZaposleniDAO().add(zaposleni)
									&& DAOFactory.getDAOFactory().getZaposlenjeDAO().insert(zaposleni, zaposlenje);
						}

						if (uspjeh) {
							AlertDisplay.showInformation("Dodavanje", "Zaposleni uspješno dodan.");
							parent.dodajZaposlenog(zaposleni);
							for (String telefon : noviTelefoni) {
								DAOFactory.getDAOFactory().getOsobaDAO().insertTel(telefon, zaposleni);
							}
							for (String tel : uklonjeniTelefoni) {
								DAOFactory.getDAOFactory().getSponzorDAO().deleteTelefon(tel);
							}
							Stage stage = (Stage) btnSacuvajPodatke.getScene().getWindow();
							stage.close();
						} else {
							AlertDisplay.showError("Dodavanje", "Nešto nije u redu");
						}
					} else {
						AlertDisplay.showError("Dodavanje", "Nešto nije u redu");
					}
				}
			} else {
				AlertDisplay.showError("Dodavanje", "Nešto nije u redu");
			}
		}
	}

	private void dodajZaposlenje() {
		if (!InputValidator.allEntered(dpZaposlenOd.getValue(), txtPlata.getText())) {
			AlertDisplay.showError("Dodavanje", "Niste unijeli sve podatke!");
		} else if (dpZaposlenOd.getValue().compareTo(LocalDate.now()) >= 0) {
			AlertDisplay.showError("Dodavanje", "Datum od mora biti prije današnjeg!");
		} else if (dpZaposlenDo.getValue() != null && dpZaposlenOd.getValue().compareTo(dpZaposlenDo.getValue()) > 0) {
			AlertDisplay.showError("Dodavanje", "Datum od mora biti prije datuma do!");
		} else if (!InputValidator.validateDouble(txtPlata.getText())) {
			AlertDisplay.showError("Dodavanje", "Pogrešan format podatka za platu.");
		} else {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date datumOd = null;
			Date datumDo = null;
			try {
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
			ZaposlenjeDTO zaposlenje = new ZaposlenjeDTO(cmbRadnoMjesto.getSelectionModel().getSelectedItem().getId(),
					cmbRadnoMjesto.getSelectionModel().getSelectedItem().getTip(), datumOd, datumDo,
					Double.parseDouble(txtPlata.getText()));
			if (DAOFactory.getDAOFactory().getZaposlenjeDAO().insert(zaposleniZaAzurirati, zaposlenje)) {
				zaposleniZaAzurirati.getZaposljenja().add(zaposlenje);
				AlertDisplay.showInformation("Dodavanje", "Zaposlenje uspješno dodano");
				Stage stage = (Stage) btnSacuvajPodatke.getScene().getWindow();
				stage.close();
			} else {
				AlertDisplay.showError("Dodavanje", "Nešto nije u redu.");
			}
		}
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
