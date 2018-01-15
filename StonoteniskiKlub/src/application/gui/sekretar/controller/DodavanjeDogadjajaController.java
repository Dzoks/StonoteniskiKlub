package application.gui.sekretar.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTimePicker;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.DogadjajDTO;
import application.model.dto.DogadjajTipDTO;
import application.util.AlertDisplay;
import application.util.InputValidator;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.converter.LocalTimeStringConverter;

public class DodavanjeDogadjajaController extends BaseController {
	@FXML
	private JFXTimePicker tpPocetak;
	@FXML
	private Label lblDatum;
	@FXML
	private ComboBox<DogadjajTipDTO> cbTIpDogadjaja;
	@FXML
	private TextField txtNoviTip;
	@FXML
	private Button btnDodajNoviTip;
	@FXML
	private JFXTimePicker tpKraj;
	@FXML
	private TextArea taOpis;
	@FXML
	private Button btnDodajDogadjaj;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		populateComboBoxes();
		initTimePicker();
	}

	// Event Listener on Button[#btnDodajNoviTip].onAction
	@FXML
	public void dodajNoviTip(ActionEvent event) {
		if (InputValidator.allEntered(txtNoviTip.getText())) {
			DogadjajTipDTO noviTip = new DogadjajTipDTO(null, txtNoviTip.getText());
			if (DAOFactory.getDAOFactory().getDogadjajTipDAO().insert(noviTip)) {
				cbTIpDogadjaja.getItems().add(noviTip);
			}
			txtNoviTip.setText("");
		}
	}

	// Event Listener on Button[#btnDodajDogadjaj].onAction
	@FXML
	public void dodajDogadjaj(ActionEvent event) {
		if (InputValidator.allEntered(tpPocetak.getValue(), tpKraj.getValue(), taOpis.getText())) {
			LocalDateTime pocetak = LocalDateTime.of(datum, tpPocetak.getValue());
			LocalDateTime kraj = LocalDateTime.of(datum, tpKraj.getValue());
			DogadjajDTO dogadjaj = new DogadjajDTO(null, pocetak, kraj, taOpis.getText(),
					cbTIpDogadjaja.getSelectionModel().getSelectedItem(), null);
			int result = DAOFactory.getDAOFactory().getDogadjajDAO().insert(dogadjaj);
			if (result > 0) {
				parentController.dodajDogadjajUKalendar(dogadjaj);
				AlertDisplay.showInformation("Uspjesno", "", "Dodavanje dogadjaja uspjesno.");
			} else if (result == -1) {
				AlertDisplay.showInformation("Greska", "",
						"Dogadjaj se preklapa sa drugim dogadjajem u istom terminu.");
			} else if (result == -2) {
				AlertDisplay.showInformation("Greska", "", "Vrijeme kraja dogadjaja mora biti nakon vremena pocetka.");
			}
		}
	}

	public void setParams(LocalDate datum, List<DogadjajDTO> dogadjaji) {
		this.datum = datum;
		lblDatum.setText(datum.toString());
	}

	public void setParentController(CalendarController parentController) {
		this.parentController = parentController;
	}

	private LocalDate datum;
	private CalendarController parentController;

	private void populateComboBoxes() {
		ObservableList<DogadjajTipDTO> tipovi = DAOFactory.getDAOFactory().getDogadjajTipDAO().selectAll();
		cbTIpDogadjaja.setItems(tipovi);
		cbTIpDogadjaja.getSelectionModel().select(0);
	}

	private void initTimePicker() {
		tpPocetak.setIs24HourView(true);
		tpKraj.setIs24HourView(true);
		tpPocetak.setConverter(new LocalTimeStringConverter(DateTimeFormatter.ofPattern("HH:mm"),
				DateTimeFormatter.ofPattern("HH:mm")));
		tpKraj.setConverter(new LocalTimeStringConverter(DateTimeFormatter.ofPattern("HH:mm"),
				DateTimeFormatter.ofPattern("HH:mm")));
	}

}
