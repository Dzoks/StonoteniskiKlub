package application.gui.racunovodja.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.ClanarinaDAO;
import application.model.dao.DAOFactoryTransakcije;
import application.model.dao.NovcanaSredstvaDAO;
import application.model.dao.TroskoviOpremaDAO;
import application.model.dto.ClanDTO;
import application.model.dto.ClanarinaDTO;
import application.model.dto.Narudzba;
import application.model.dto.TroskoviOpremaDTO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.ScrollPane;

import javafx.scene.control.ComboBox;

import javafx.scene.control.TextArea;

import javafx.scene.control.DatePicker;

public class IzmijeniSredstvaZaOpremuController extends BaseController{
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
	private Label lblNarudzba;

	@FXML
	private Label lblOpis;
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private TextArea txtOpis;
	@FXML
	private ComboBox<Narudzba> comboBoxNarudzba;
	
	private EvidentiranjeSredstavaZaOpremuController evidentiranjeController;
	private ObservableList<TroskoviOpremaDTO> listaTroskovi;
	private TroskoviOpremaDTO trosak;
	private ObservableList<Narudzba> listaNarudzbe;
	
	public void setTxtIznos(String txtIznos) {
		this.txtIznos.setText(txtIznos);;
	}
	public void setTxtOpis(String txtOpis) {
		this.txtOpis.setText(txtOpis);;
	}
	public void setComboBoxNarudzba(ObservableList<Narudzba> lista, Narudzba narudzba) {
		this.comboBoxNarudzba.setItems(lista);
		comboBoxNarudzba.getSelectionModel().select(narudzba);
		
	}
	public void setListaTroskovi(ObservableList<TroskoviOpremaDTO> listaTroskovi) {
		this.listaTroskovi = listaTroskovi;
	}
	public void setTrosak(TroskoviOpremaDTO trosak) {
		this.trosak = trosak;
	}

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void setDatePicker(Date input) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(input);
		LocalDate date = LocalDate.of(cal.get(Calendar.YEAR),
		        cal.get(Calendar.MONTH) + 1,
		        cal.get(Calendar.DAY_OF_MONTH));
		this.datePicker.setValue(date);
	}
	public void setComboBoxNarudzba(ObservableList<Narudzba> lista) {
		this.comboBoxNarudzba.setItems(lista);
	}
	public void setEvidentiranjeController(EvidentiranjeSredstavaZaOpremuController evidentiranjeController) {
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
		Narudzba narudzba = comboBoxNarudzba.getValue();
		String opis = txtOpis.getText();
		LocalDate localDate = datePicker.getValue();
		Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
		Date datum = Date.from(instant);
		
		String tipTransakcije = "troskoviOprema"; //hardcode, popraviti hashmap...!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		TroskoviOpremaDTO trosak1 = new TroskoviOpremaDTO(trosak.getId(), datum, iznos, opis, tipTransakcije, narudzba);
		evidentiranjeController.getListaTroskovi().remove(trosak1);
		evidentiranjeController.getListaTroskovi().add(trosak1);
		DAOFactoryTransakcije.getDAOFactory().getTroskoviOpremaDAO().UPDATE(trosak1,narudzba);
		DAOFactoryTransakcije.getDAOFactory().getNovcanaSredstvaDAO().dodajRashode(trosak1.getIznos().get()-trosak.getIznos().get());

		this.getPrimaryStage().close();
	}
	public void otkazi() {
		this.getPrimaryStage().close();
	}
}
