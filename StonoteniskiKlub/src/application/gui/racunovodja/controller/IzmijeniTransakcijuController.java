package application.gui.racunovodja.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import application.model.dao.DAOFactory;
import application.model.dto.TipTransakcijeDTO;
import application.model.dto.TransakcijaDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class IzmijeniTransakcijuController extends TransakcijaIzmijeniDecorater{
	@FXML
	private Label lblDatum;
	
	@FXML
	private Button btnIzmijeni;
	@FXML
	private Button btnOtkazi;
	@FXML
	private Label lblIznos;
	
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
	private Button btnDodajTipTransakcije;

	private EvidentiranjeNovcanihSredstavaController evidentiranjeController;
	private ObservableList<TransakcijaDTO> listaTransakcija;
	private ObservableList<TipTransakcijeDTO> listaTip;
	private TransakcijaDTO transakcija;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.setController(new TransakcijaIzmijeniController(txtIznos, datePicker, txtOpis));

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
	public TransakcijaDTO izmijeni() { //dodati uticaj na budzet
		TransakcijaDTO trans = super.izmijeni();
		if(transakcija==null)
			return null;
		TipTransakcijeDTO tip = (TipTransakcijeDTO)comboBoxTipTransakcije.getValue();
		
		boolean jeUplata = false;
		String vrsta = comboBoxVrsta.getValue();
		if(vrsta.equals("Prihod"))
			jeUplata=true;
		
		TransakcijaDTO transakcija1 = new TransakcijaDTO(transakcija.getId(), trans.getDatum(), trans.getIznos().get(), trans.getOpis().get(), tip.getTip(), jeUplata);
		evidentiranjeController.getListaTransakcija().remove(transakcija1);
		evidentiranjeController.getListaTransakcija().add(transakcija1);
		DAOFactory.getDAOFactory().getTransakcijaDAO().UPDATE(transakcija1,tip);
		if(jeUplata) {
			DAOFactory.getDAOFactory().getNovcanaSredstvaDAO().dodajPrihode(transakcija1.getIznos().get()-transakcija.getIznos().get());

		}else {
			DAOFactory.getDAOFactory().getNovcanaSredstvaDAO().dodajRashode(transakcija1.getIznos().get()-transakcija.getIznos().get());
		}
		this.getPrimaryStage().close();
		return transakcija1;
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
			noviStage.setTitle("Stonoteniski klub");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			noviStage.showAndWait();
			listaTip = DAOFactory.getDAOFactory().getTipTransakcijeDAO().SELECT_ALL();
			comboBoxTipTransakcije.setItems(listaTip);
			comboBoxTipTransakcije.getSelectionModel().select(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//initialize
	}
}
