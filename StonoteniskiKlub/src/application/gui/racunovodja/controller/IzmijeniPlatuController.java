package application.gui.racunovodja.controller;

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
import application.model.dao.PlataDAO;
import application.model.dto.ClanDTO;
import application.model.dto.ClanarinaDTO;
import application.model.dto.PlataDTO;
import application.model.dto.ZaposleniDTO;
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

public class IzmijeniPlatuController extends BaseController {
	@FXML
	private Label lblZaposleni;
	@FXML
	private Label lblDatumUplate;
	@FXML
	private ComboBox<ZaposleniDTO> comboBoxZaposleni;
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
	private Label lblOpis;
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private TextArea txtOpis;
	
	private ObservableList<PlataDTO> listaPlata;
	private ObservableList<ZaposleniDTO> listaZaposlenih;
	private PlataDTO plata;
	private EvidentiranjeIsplacenihPlataController evidentiranjeController;
	public void setEvidentiranjeController(EvidentiranjeIsplacenihPlataController evidentiranjeController) {
		this.evidentiranjeController = evidentiranjeController;
	}
	public IzmijeniPlatuController() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void setComboBoxZaposleni(ObservableList<ZaposleniDTO> lista, int zaposleni) {
		this.comboBoxZaposleni.setItems(lista);
		for(ZaposleniDTO z : lista) {
			if(z.getId()==zaposleni) {
				comboBoxZaposleni.getSelectionModel().select(z);
			}
		}
	}
	
	public ObservableList<PlataDTO> getListaPlata() {
		return listaPlata;
	}
	public void setTxtIznos(String txtIznos) {
		this.txtIznos.setText(txtIznos);
	}
	public void setTxtOpis(String txtOpis) {
		this.txtOpis.setText(txtOpis);
	}
	public void setDatePicker(Date input) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(input);
		LocalDate date = LocalDate.of(cal.get(Calendar.YEAR),
		        cal.get(Calendar.MONTH) + 1,
		        cal.get(Calendar.DAY_OF_MONTH));
		this.datePicker.setValue(date);
	}

	public void setListaPlata(ObservableList<PlataDTO> listaPlata) {
		this.listaPlata = listaPlata;
	}
	public PlataDTO getPlata() {
		return plata;
	}
	public void setPlata(PlataDTO plata) {
		this.plata = plata;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	// TODO Auto-generated method stub
	
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
		ZaposleniDTO zaposleni = comboBoxZaposleni.getValue();
		String opis = txtOpis.getText();
		LocalDate localDate = datePicker.getValue();
		Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
		Date datum = Date.from(instant);
		String tipTransakcije = "plata"; //hardcode, popraviti hashmap...!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		PlataDTO plata1 = new PlataDTO(plata.getId(), datum, iznos, opis, tipTransakcije,zaposleni);
		evidentiranjeController.getListaPlata().remove(plata1);
		evidentiranjeController.getListaPlata().add(plata1);
		DAOFactoryTransakcije.getDAOFactory().getPlataDAO().UPDATE(plata1,zaposleni);
		DAOFactoryTransakcije.getDAOFactory().getNovcanaSredstvaDAO().dodajRashode(plata1.getIznos().get()-plata.getIznos().get());
		this.getPrimaryStage().close();
	}
	public void otkazi() {
		this.getPrimaryStage().close();
	}
}
