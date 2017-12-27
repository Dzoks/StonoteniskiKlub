package application.gui.sekretar.controller;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.ToggleGroup;

import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dao.OpremaTipDAO;
import application.model.dto.DonacijaDTO;
import application.model.dto.OpremaTip;
import application.model.dto.SponzorDTO;
import application.model.dto.UgovorDTO;
import application.util.AlertDisplay;
import application.util.InputValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.ListView;

import javafx.scene.control.TextArea;

import javafx.scene.control.ComboBox;

import javafx.scene.control.RadioButton;

import javafx.scene.input.MouseEvent;

import javafx.scene.control.CheckBox;

import javafx.scene.control.DatePicker;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rbNovcana.disableProperty().bind(cbUkljucujeDonacije.selectedProperty().not());
		rbOprema.disableProperty().bind(cbUkljucujeDonacije.selectedProperty().not());
		taOpisDonacije.disableProperty().bind(cbUkljucujeDonacije.selectedProperty().not());
		lstDonacije.disableProperty().bind(cbUkljucujeDonacije.selectedProperty().not());
		btnDodajDonaciju.disableProperty().bind(cbUkljucujeDonacije.selectedProperty().not());
		btnUkloniDonaciju.disableProperty().bind(cbUkljucujeDonacije.selectedProperty()
				.and(lstDonacije.getSelectionModel().selectedItemProperty().isNotNull()).not());
		tfNovcaniIznos.disableProperty()
				.bind((cbUkljucujeDonacije.selectedProperty().and(rbNovcana.selectedProperty()).not()));
		cbTipOpreme.disableProperty()
				.bind((cbUkljucujeDonacije.selectedProperty().and(rbOprema.selectedProperty()).not()));
		tfKolicina.disableProperty()
				.bind((cbUkljucujeDonacije.selectedProperty().and(rbOprema.selectedProperty()).not()));
		ObservableList<OpremaTip> cbItems = OpremaTipDAO.SELECT_ALL();
		cbTipOpreme.setItems(cbItems);
		cbTipOpreme.getSelectionModel().select(0);
		donacije = FXCollections.observableArrayList();
	}

	// Event Listener on ListView[#lstDonacije].onMouseClicked
	@FXML
	public void selectDonacija(MouseEvent event) {
		DonacijaDTO selected = lstDonacije.getSelectionModel().getSelectedItem();
		if(selected.getNovcanaDonacija()){
			rbNovcana.fire();
			tfNovcaniIznos.setText(selected.getNovcaniIznos().toString());
		} else{
			rbOprema.fire();
			tfKolicina.setText(selected.getKolicina().toString());
			cbTipOpreme.getSelectionModel().select(selected.getTipOpreme());
		}
		taOpisDonacije.setText(selected.getOpis());
	}

	// Event Listener on Button[#btnUkloniDonaciju].onAction
	@FXML
	public void ukloniDonaciju(ActionEvent event) {
		donacije.remove(lstDonacije.getSelectionModel().getSelectedItem());
	}

	// Event Listener on Button[#btnSacuvaj].onAction
	@FXML
	public void sacuvaj(ActionEvent event) {
		if(InputValidator.allEntered(dpDatumOd.getValue(), dpDatumDo.getValue(), taOpisUgovora.getText())){
			if(!(cbUkljucujeDonacije.isSelected() && donacije.isEmpty())){
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
				}
				Integer max = sponzor.getMaxUgovorId() + 1;
				UgovorDTO ugovor = new UgovorDTO(max, datumOd, datumDo, taOpisUgovora.getText(), null);
				sponzor.getUgovori().add(ugovor);
				DAOFactory.getDAOFactory().getUgovorDAO().insert(sponzor, ugovor);
				if(cbUkljucujeDonacije.isSelected()){
					ugovor.setDonacije(donacije);
					for(DonacijaDTO donacija : donacije){
						donacija.setRedniBroj(donacije.indexOf(donacija)+1);
						donacija.setSponzor(sponzor);
						donacija.setUgovor(ugovor);
						DAOFactory.getDAOFactory().getDonacijaDAO().insert(sponzor, ugovor, donacija);
					}
				}
			} else{
				AlertDisplay.showInformation("Greska", "", "Niste dodali donaciju!");
			}
		} else{
			AlertDisplay.showInformation("Greska", "", "Niste unijeli podatke o ugovoru!");
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
			AlertDisplay.showInformation("Greska", "", "Niste unijeli sve podatke o donaciji");
		}
	}

	public void setSponzor(SponzorDTO sponzor) {
		this.sponzor = sponzor;
		lblSponzor.setText(sponzor.toString());
	}

	private SponzorDTO sponzor;
	private ObservableList<DonacijaDTO> donacije;

}
