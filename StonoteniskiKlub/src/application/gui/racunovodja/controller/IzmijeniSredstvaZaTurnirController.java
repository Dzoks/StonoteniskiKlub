package application.gui.racunovodja.controller;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactoryTransakcije;
import application.model.dao.NovcanaSredstvaDAO;
import application.model.dao.TroskoviOpremaDAO;
import application.model.dao.TroskoviTurnirDAO;
import application.model.dto.Narudzba;
import application.model.dto.TroskoviOpremaDTO;
import application.model.dto.TroskoviTurnirDTO;
import application.model.dto.TurnirDTO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

import javafx.scene.control.ScrollPane;

import javafx.scene.control.ComboBox;

import javafx.scene.control.TextArea;

import javafx.scene.control.DatePicker;

public class IzmijeniSredstvaZaTurnirController extends BaseController{
	@FXML
	private Label lblDatum;
	@FXML
	private DatePicker datePicker;
	@FXML
	private Button btnIzmijeni;
	@FXML
	private Button btnOtkazi;
	@FXML
	private Label lblIznos;
	@FXML
	private TextField txtIznos;
	@FXML
	private Label lblKM;
	@FXML
	private Label lblTurnir;
	@FXML
	private ComboBox comboBoxTurnir;
	@FXML
	private Label lblOpis;
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private TextArea txtOpis;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	private EvidentiranjeSredstavaZaTurnireController evidentiranjeController;
	private ObservableList<TroskoviTurnirDTO> listaTroskovi;
	private TroskoviTurnirDTO trosak;
	private ObservableList<TurnirDTO> listaTurniri;
	
	public void setTxtIznos(String txtIznos) {
		this.txtIznos.setText(txtIznos);;
	}
	public void setTxtOpis(String txtOpis) {
		this.txtOpis.setText(txtOpis);;
	}
	public void setComboBoxTurnir(ObservableList<TurnirDTO> lista) {
		this.comboBoxTurnir.setItems(lista);
		comboBoxTurnir.getSelectionModel().select(trosak.getTurnir());
		
	}
	public void setListaTroskovi(ObservableList<TroskoviTurnirDTO> listaTroskovi) {
		this.listaTroskovi = listaTroskovi;
	}
	public void setTrosak(TroskoviTurnirDTO trosak) {
		this.trosak = trosak;
	}


	public void setDatePicker(Date input) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(input);
		LocalDate date = LocalDate.of(cal.get(Calendar.YEAR),
		        cal.get(Calendar.MONTH) + 1,
		        cal.get(Calendar.DAY_OF_MONTH));
		this.datePicker.setValue(date);
	}
	
	public void setEvidentiranjeController(EvidentiranjeSredstavaZaTurnireController evidentiranjeController) {
		this.evidentiranjeController = evidentiranjeController;
	}
	
public void izmijeni() {
		
	Double iznos = null;
	try {
		iznos = Double.parseDouble(txtIznos.getText());
		if(iznos<0)
			throw new NumberFormatException();
	}catch(NumberFormatException ex) {
		Alert alert = new Alert(AlertType.INFORMATION, "Niste ispravno unijeli informaciju o iznosu.");
		alert.showAndWait();
		return;
	}
		
		TurnirDTO turnir = (TurnirDTO)comboBoxTurnir.getSelectionModel().getSelectedItem();
		String opis = txtOpis.getText();
		LocalDate localDate = datePicker.getValue();
		Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
		Date datum = Date.from(instant);
		
		String tipTransakcije = "troskoviOprema"; //hardcode, popraviti hashmap...!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		TroskoviTurnirDTO trosak1 = new TroskoviTurnirDTO(trosak.getId(), datum, iznos, opis, tipTransakcije, turnir);
		evidentiranjeController.getListaTroskovi().remove(trosak1);
		evidentiranjeController.getListaTroskovi().add(trosak1);
		DAOFactoryTransakcije.getDAOFactory().getTroskoviTurnirDAO().UPDATE(trosak1,turnir);
		DAOFactoryTransakcije.getDAOFactory().getNovcanaSredstvaDAO().dodajRashode(trosak1.getIznos().get()-trosak.getIznos().get());

		this.getPrimaryStage().close();
	}
	public void otkazi() {
		this.getPrimaryStage().close();
	}
}
