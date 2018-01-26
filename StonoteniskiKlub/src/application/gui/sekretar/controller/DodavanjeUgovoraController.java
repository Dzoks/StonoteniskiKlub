package application.gui.sekretar.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.gui.trener.controller.DodajTipOpremeController;
import application.model.dao.DAOFactory;
import application.model.dto.DonacijaDTO;
import application.model.dto.OpremaTip;
import application.model.dto.SponzorDTO;
import application.model.dto.UgovorDTO;
import application.util.AlertDisplay;
import application.util.ErrorLogger;
import application.util.InputValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DodavanjeUgovoraController extends BaseController {
	@FXML
	private Label lblSponzor;
	@FXML
	private DatePicker dpDatumOd;
	@FXML
	private DatePicker dpDatumDo;
	@FXML
	private TextArea taOpisUgovora;
	@FXML
	private CheckBox cbUkljucujeDonacije;
	@FXML
	private RadioButton rbNovcana;
	@FXML
	private ToggleGroup grpTip;
	@FXML
	private RadioButton rbOprema;
	@FXML
	private TextField tfNovcaniIznos;
	@FXML
	private ComboBox<OpremaTip> cbTipOpreme;
	@FXML
	private TextField tfKolicina;
	@FXML
	private TextArea taOpisDonacije;
	@FXML
	private ListView<DonacijaDTO> lstDonacije;
	@FXML
	private Button btnUkloniDonaciju;
	@FXML
	private Button btnSacuvaj;
	@FXML
	private Button btnDodajDonaciju;
	@FXML
	private Button btnAzurirajDonaciju;
	@FXML
	private Button btnDodajTip;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bindDisable();
		populateComboBoxes();
		donacije = FXCollections.observableArrayList();
	}

	// Event Listener on ListView[#lstDonacije].onMouseClicked
	@FXML
	public void selectDonacija(MouseEvent event) {
		DonacijaDTO selected = lstDonacije.getSelectionModel().getSelectedItem();
		if (selected != null) {
			if (selected.getNovcanaDonacija()) {
				rbNovcana.fire();
				tfNovcaniIznos.setText(selected.getNovcaniIznos().toString());
			} else {
				rbOprema.fire();
				tfKolicina.setText(selected.getKolicina().toString());
				cbTipOpreme.getSelectionModel().select(selected.getTipOpreme());
			}
			taOpisDonacije.setText(selected.getOpis());
		}
	}

	// Event Listener on Button[#btnDodajDonaciju].onAction
	@FXML
	public void dodajDonaciju(ActionEvent event) {
		if ((rbNovcana.isSelected() && InputValidator.allEntered(tfNovcaniIznos.getText(), taOpisDonacije.getText()))
				|| (rbOprema.isSelected()
						&& InputValidator.allEntered(tfKolicina.getText(), taOpisDonacije.getText()))) {
			DonacijaDTO donacija = new DonacijaDTO(null, null, null, taOpisDonacije.getText(), null, null,
					rbNovcana.isSelected(), false, null);
			if (rbNovcana.isSelected()) {
				donacija.setNovcaniIznos(new BigDecimal(tfNovcaniIznos.getText()));
				tfNovcaniIznos.setText("");
			} else {
				donacija.setKolicina(new BigDecimal(tfKolicina.getText()));
				donacija.setTipOpreme(cbTipOpreme.getValue());
				tfKolicina.setText("");
			}
			taOpisDonacije.setText("");
			donacije.add(donacija);
			lstDonacije.setItems(donacije);
		} else {
			AlertDisplay.showError("Dodavanje", "Niste unijeli sve podatke o donaciji");
		}
	}

	@FXML
	public void azurirajDonaciju(ActionEvent event) {
		DonacijaDTO selected = donacije.get(donacije.indexOf(lstDonacije.getSelectionModel().getSelectedItem()));
		if (selected.getNovcanaDonacija()) {
			selected.setNovcaniIznos(new BigDecimal(tfNovcaniIznos.getText()));
		} else {
			selected.setTipOpreme(cbTipOpreme.getSelectionModel().getSelectedItem());
			selected.setKolicina(new BigDecimal(tfKolicina.getText()));
		}
		selected.setOpis(taOpisDonacije.getText());
		lstDonacije.refresh();
	}

	// Event Listener on Button[#btnUkloniDonaciju].onAction
	@FXML
	public void ukloniDonaciju(ActionEvent event) {
		donacije.remove(lstDonacije.getSelectionModel().getSelectedItem());
	}

	@FXML
	public void dodajTip(ActionEvent event) {
		Stage noviStage = new Stage();

		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getClassLoader().getResource("application/gui/trener/view/DodajTipOpremeView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root, 267, 207);
			DodajTipOpremeController controller = loader.<DodajTipOpremeController>getController();
			controller.setPrimaryStage(noviStage);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			noviStage.showAndWait();

			if ("YES".equals(controller.getPovratnaVrijednost())) {
				OpremaTip noviTipOpreme = controller.vratiTipOpreme();
				DAOFactory.getDAOFactory().getOpremaTipDAO().INSERT(noviTipOpreme);
				cbTipOpreme.getItems().add(noviTipOpreme);
				cbTipOpreme.getSelectionModel().selectLast();
			}
		} catch (IOException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		}
	}

	// Event Listener on Button[#btnSacuvaj].onAction
	@FXML
	public void sacuvaj(ActionEvent event) {
		if (InputValidator.allEntered(dpDatumOd.getValue(), taOpisUgovora.getText())) {
			if (!(cbUkljucujeDonacije.isSelected() && donacije.isEmpty())) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date datumOd = null;
				Date datumDo = null;
				try {
					datumOd = formatter.parse(dpDatumOd.getValue().toString());
					if (dpDatumDo.getValue() != null) {
						datumDo = formatter.parse(dpDatumDo.getValue().toString());
					}
				} catch (ParseException e) {
					e.printStackTrace();
					new ErrorLogger().log(e);
				}
				UgovorDTO ugovor = new UgovorDTO(null, datumOd, datumDo, taOpisUgovora.getText(), null);
				if (DAOFactory.getDAOFactory().getUgovorDAO().insert(sponzor, ugovor)) {
					sponzor.getUgovori().add(ugovor);
					if (cbUkljucujeDonacije.isSelected()) {
						ugovor.setDonacije(donacije);
						for (DonacijaDTO donacija : donacije) {
							donacija.setSponzor(sponzor);
							donacija.setUgovor(ugovor);
							DAOFactory.getDAOFactory().getDonacijaDAO().insert(sponzor, ugovor, donacija);
						}
					}
					AlertDisplay.showInformation("Dodavanje", "Ugovor uspješno dodan.");
				}

			} else {
				AlertDisplay.showError("Dodavanje", "Niste dodali donaciju!");
			}
		} else {
			AlertDisplay.showError("Dodavanje", "Niste unijeli podatke o ugovoru!");
		}
	}

	public void setSponzor(SponzorDTO sponzor) {
		this.sponzor = sponzor;
		lblSponzor.setText(sponzor.toString());
	}

	private SponzorDTO sponzor;
	private ObservableList<DonacijaDTO> donacije;

	private void bindDisable() {
		rbNovcana.disableProperty().bind(cbUkljucujeDonacije.selectedProperty().not());
		rbOprema.disableProperty().bind(cbUkljucujeDonacije.selectedProperty().not());
		taOpisDonacije.disableProperty().bind(cbUkljucujeDonacije.selectedProperty().not());
		lstDonacije.disableProperty().bind(cbUkljucujeDonacije.selectedProperty().not());
		btnDodajDonaciju.disableProperty().bind(cbUkljucujeDonacije.selectedProperty().not());
		btnUkloniDonaciju.disableProperty().bind(cbUkljucujeDonacije.selectedProperty()
				.and(lstDonacije.getSelectionModel().selectedItemProperty().isNotNull()).not());
		btnAzurirajDonaciju.disableProperty().bind(cbUkljucujeDonacije.selectedProperty()
				.and(lstDonacije.getSelectionModel().selectedItemProperty().isNotNull()).not());
		tfNovcaniIznos.disableProperty()
				.bind((cbUkljucujeDonacije.selectedProperty().and(rbNovcana.selectedProperty()).not()));
		cbTipOpreme.disableProperty()
				.bind((cbUkljucujeDonacije.selectedProperty().and(rbOprema.selectedProperty()).not()));
		tfKolicina.disableProperty()
				.bind((cbUkljucujeDonacije.selectedProperty().and(rbOprema.selectedProperty()).not()));
	}

	private void populateComboBoxes() {
		ObservableList<OpremaTip> cbItems = DAOFactory.getDAOFactory().getOpremaTipDAO().SELECT_ALL();
		cbTipOpreme.setItems(cbItems);
		cbTipOpreme.getSelectionModel().select(0);
	}
}
