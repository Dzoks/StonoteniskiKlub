package application.gui.racunovodja.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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
import application.model.dao.TipTransakcijeDAO;
import application.model.dao.TransakcijaDAO;
import application.model.dao.TroskoviTurnirDAO;
import application.model.dto.TipTransakcijeDTO;
import application.model.dto.TransakcijaDTO;
import application.model.dto.TroskoviTurnirDTO;
import application.model.dto.TurnirDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.ScrollPane;

import javafx.scene.control.ComboBox;

import javafx.scene.control.TextArea;

import javafx.scene.control.DatePicker;

public class IzmijeniTransakcijuController extends BaseController{
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
	private ComboBox<TipTransakcijeDTO> comboBoxTipTransakcije;
	@FXML
	private ComboBox<String> comboBoxVrsta;
	@FXML
	private Label lblOpis;
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private TextArea txtOpis;
	@FXML
	private Button btnDodajTipTransakcije;

	private EvidentiranjeNovcanihSredstavaController evidentiranjeController;
	private ObservableList<TransakcijaDTO> listaTransakcija;
	private ObservableList<TipTransakcijeDTO> listaTip;
	private TransakcijaDTO transakcija;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		;
		ObservableList<String> vrsta = FXCollections.observableArrayList();
		vrsta.add("Prihod");
		vrsta.add("Rashod");
		comboBoxVrsta.setItems(vrsta);
		comboBoxVrsta.getSelectionModel().select(0);
	}
	public void setListaTransakcija(ObservableList<TransakcijaDTO> listaTransakcija) {
		this.listaTransakcija = listaTransakcija;
	}
	public void setDatePicker(Date input) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(input);
		LocalDate date = LocalDate.of(cal.get(Calendar.YEAR),
		        cal.get(Calendar.MONTH) + 1,
		        cal.get(Calendar.DAY_OF_MONTH));
		this.datePicker.setValue(date);
	}
	public void setTransakcija(TransakcijaDTO transakcija) {
		this.transakcija = transakcija;
	}
	public void setTxtIznos(String txtIznos) {
		this.txtIznos.setText(txtIznos);
	}
	public void setTxtOpis(String txtOpis) {
		this.txtOpis.setText(txtOpis);
	}
	public void setEvidentiranjeController(EvidentiranjeNovcanihSredstavaController evidentiranjeController) {
		this.evidentiranjeController = evidentiranjeController;
	}
	public void setListaTip(ObservableList<TipTransakcijeDTO> listaTip) {
		this.listaTip = listaTip;
		comboBoxTipTransakcije.setItems(listaTip);
		comboBoxTipTransakcije.getSelectionModel().select(0);
	}
	public void izmijeni() { //dodati uticaj na budzet
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
		TipTransakcijeDTO tip = (TipTransakcijeDTO)comboBoxTipTransakcije.getValue();
		System.out.println(tip.getId());
		System.out.println(transakcija.getId());
		String opis = txtOpis.getText();
		LocalDate localDate = datePicker.getValue();
		Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
		Date datum = Date.from(instant);
		boolean jeUplata = false;
		String vrsta = comboBoxVrsta.getValue();
		if(vrsta.equals("Prihod"))
			jeUplata=true;
		
		TransakcijaDTO transakcija1 = new TransakcijaDTO(transakcija.getId(), datum, iznos, opis, tip.getTip(), jeUplata);
		evidentiranjeController.getListaTransakcija().remove(transakcija1);
		evidentiranjeController.getListaTransakcija().add(transakcija1);
		DAOFactoryTransakcije.getDAOFactory().getTransakcijaDAO().UPDATE(transakcija1,tip);
		if(jeUplata) {
			DAOFactoryTransakcije.getDAOFactory().getNovcanaSredstvaDAO().dodajPrihode(transakcija1.getIznos().get()-transakcija.getIznos().get());

		}else {
			DAOFactoryTransakcije.getDAOFactory().getNovcanaSredstvaDAO().dodajRashode(transakcija1.getIznos().get()-transakcija.getIznos().get());
		}
		this.getPrimaryStage().close();
	}
	
	public void otkazi() {
		this.getPrimaryStage().close();
	}
	public void dodajTipTransakcije() {
		Stage noviStage = new Stage();
		System.out.println("prije loader");
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/racunovodja/view/DodajTipTransakcijeView.fxml"));
		AnchorPane root;
		try {
			root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,300,400);
			DodajTipTransakcijeController controller = loader.<DodajTipTransakcijeController>getController();
			controller.setPrimaryStage(noviStage);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa finansijama");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			noviStage.showAndWait();
			listaTip = DAOFactoryTransakcije.getDAOFactory().getTipTransakcijeDAO().SELECT_ALL();
			comboBoxTipTransakcije.setItems(listaTip);
			comboBoxTipTransakcije.getSelectionModel().select(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//initialize
	}
}
