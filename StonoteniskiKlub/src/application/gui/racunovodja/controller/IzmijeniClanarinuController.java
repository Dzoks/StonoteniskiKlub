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
import application.model.dto.ClanDTO;
import application.model.dto.ClanarinaDTO;
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

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.DatePicker;

public class IzmijeniClanarinuController extends BaseController{
	@FXML
	private Label lblClan;
	@FXML
	private Label lblMjesec;
	@FXML
	private Label lblGodina;
	@FXML
	private Label lblGodinaUplate;
	@FXML
	private ComboBox<ClanDTO> comboBoxClan;
	@FXML
	private Spinner<Integer> spinnerMjesec;
	@FXML
	private Spinner<Integer> spinnerGodina;
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
	
	private ClanarinaDTO clanarina;
	private EvidentiranjeClanarinaController evidentiranjeController;
	
	public EvidentiranjeClanarinaController getEvidentiranjeController() {
		return evidentiranjeController;
	}
	public void setEvidentiranjeController(EvidentiranjeClanarinaController evidentiranjeController) {
		this.evidentiranjeController = evidentiranjeController;
	}
	public ClanarinaDTO getClanarina() {
		return clanarina;
	}
	public void setClanarina(ClanarinaDTO clanarina) {
		this.clanarina = clanarina;
	}
	public void setComboBoxClan(ObservableList<ClanDTO> lista, int clan) { //poslati Clana tu, promijeniti
		this.comboBoxClan.setItems(lista);
		for(ClanDTO cl : listaClanova) {
			if(cl.getId()==clan) {
				comboBoxClan.getSelectionModel().select(cl);
			}
		}
	}
	public void setSpinnerMjesec(int spinnerMjesec) {
		this.spinnerMjesec.getValueFactory().setValue(spinnerMjesec);
	}
	public void setSpinnerGodina(int spinnerGodina) {
		this.spinnerGodina.getValueFactory().setValue(spinnerGodina);
	}
	public void setTxtIznos(String txtIznos) {
		this.txtIznos.setText(txtIznos);;
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
	public void setListaClanova(ObservableList<ClanDTO> listaClanova) {
		this.listaClanova = listaClanova;
	}
	@FXML
	private Label lblKM;
	@FXML
	private Label lblOpis;
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private TextArea txtOpis;
	
	
	private ObservableList<ClanDTO> listaClanova;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("initialize");
		
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 1);
		SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(2010, 2018, 2014);
		spinnerMjesec.setValueFactory(valueFactory);
		spinnerGodina.setValueFactory(valueFactory1);
	}
	public ComboBox getComboBoxClan() {
		return comboBoxClan;
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
		Integer mjesec = spinnerMjesec.getValue();
		Integer godina = spinnerGodina.getValue();
		ClanDTO clan = comboBoxClan.getValue();
		String opis = txtOpis.getText();
		LocalDate localDate = datePicker.getValue();
		Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
		Date datum = Date.from(instant);
		System.out.println(clanarina.getId());
		String tipTransakcije = "clanarina"; //hardcode, popraviti hashmap...!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		ClanarinaDTO clanarina1 = new ClanarinaDTO(clanarina.getId(), datum, iznos, opis, tipTransakcije, mjesec, godina, clan.getIme(), clan.getPrezime(),clan.getId());
		evidentiranjeController.getListaClanarina().remove(clanarina1);
		evidentiranjeController.getListaClanarina().add(clanarina1);
		DAOFactoryTransakcije.getDAOFactory().getClanarinaDAO().UPDATE(clanarina1,clan);
		DAOFactoryTransakcije.getDAOFactory().getNovcanaSredstvaDAO().dodajPrihode(clanarina1.getIznos().get()-clanarina.getIznos().get());
		this.getPrimaryStage().close();
	}
	public void otkazi() {
		this.getPrimaryStage().close();
	}
}
