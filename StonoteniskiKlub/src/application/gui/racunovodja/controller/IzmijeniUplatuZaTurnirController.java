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
import application.model.dao.TroskoviTurnirDAO;
import application.model.dao.UcesnikPrijavaDAO;
import application.model.dao.UplataZaTurnirDAO;
import application.model.dto.TroskoviTurnirDTO;
import application.model.dto.TurnirDTO;
import application.model.dto.UcesnikPrijavaDTO;
import application.model.dto.UplataZaTurnirDTO;
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

public class IzmijeniUplatuZaTurnirController extends BaseController{
	@FXML
	private Label lblUcesnik;
	@FXML
	private Label lblGodinaUplate;
	@FXML
	private ComboBox<UcesnikPrijavaDTO> comboBoxUcesnik;
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
	private Label lblOpis;
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private TextArea txtOpis;
	
	private EvidentiranjeUplataZaTurnirController evidentiranjeController;
	private ObservableList<UplataZaTurnirDTO> listaUplate;
	private UplataZaTurnirDTO uplata;
	private ObservableList<UcesnikPrijavaDTO> listaUcesnika;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	public void setTxtIznos(String txtIznos) {
		this.txtIznos.setText(txtIznos);;
	}
	public void setTxtOpis(String txtOpis) {
		this.txtOpis.setText(txtOpis);;
	}
	public void setComboBoxUcesnik(ObservableList<UcesnikPrijavaDTO> lista) {
		this.comboBoxUcesnik.setItems(lista);
		comboBoxUcesnik.getSelectionModel().select(uplata.getUcesnik());
		
	}
	public void setListaUplate(ObservableList<UplataZaTurnirDTO> listaUplate) {
		this.listaUplate = listaUplate;
	}
	public void setUplata(UplataZaTurnirDTO uplata) {
		this.uplata = uplata;
	}


	public void setDatePicker(Date input) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(input);
		LocalDate date = LocalDate.of(cal.get(Calendar.YEAR),
		        cal.get(Calendar.MONTH) + 1,
		        cal.get(Calendar.DAY_OF_MONTH));
		this.datePicker.setValue(date);
	}
	
	public void setEvidentiranjeController(EvidentiranjeUplataZaTurnirController evidentiranjeController) {
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
		
		UcesnikPrijavaDTO ucesnik = comboBoxUcesnik.getValue();
		String opis = txtOpis.getText();
		LocalDate localDate = datePicker.getValue();
		Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
		Date datum = Date.from(instant);
		
		String tipTransakcije = "uplataZaTurnir"; //hardcode, popraviti hashmap...!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		UplataZaTurnirDTO trosak1 = new UplataZaTurnirDTO(uplata.getId(), datum, iznos, opis, tipTransakcije,ucesnik);
		evidentiranjeController.getListaUplata().remove(trosak1);
		evidentiranjeController.getListaUplata().add(trosak1);
		DAOFactoryTransakcije.getDAOFactory().getUplataZaTurnirDAO().UPDATE(trosak1,ucesnik);
		DAOFactoryTransakcije.getDAOFactory().getNovcanaSredstvaDAO().dodajPrihode(trosak1.getIznos().get()-uplata.getIznos().get());

		this.getPrimaryStage().close();
	}
	public void otkazi() {
		this.getPrimaryStage().close();
	}
}
