package application.gui.racunovodja.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.ClanDAO;
import application.model.dao.ClanarinaDAO;
import application.model.dao.DAOFactory;
import application.model.dao.NovcanaSredstvaDAO;
import application.model.dao.TipTransakcijeDAO;
import application.model.dao.TransakcijaDAO;
import application.model.dto.ClanDTO;
import application.model.dto.ClanarinaDTO;
import application.model.dto.NovcanaSredstvaDTO;
import application.model.dto.TipTransakcijeDTO;
import application.model.dto.TransakcijaDTO;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.ComboBox;

import javafx.scene.control.TextArea;

import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableView;

import javafx.scene.control.DatePicker;

import javafx.scene.control.TableColumn;

public class EvidentiranjeNovcanihSredstavaController  extends TransakcijaDecorater{ //sezona popraviti
	@FXML
	private Label lblUsvojeniBudzet;
	@FXML
	private Label lblNovcanaSredstva;
	@FXML
	private Label lblPrihodi;
	@FXML
	private Label lblRashodi;
	@FXML
	private ComboBox<String> comboBoxSezona;
	@FXML
	private TableView<TransakcijaDTO> tableView;
	@FXML
	private TableColumn<TransakcijaDTO,String> tableColumnVrsta;
	@FXML
	private TableColumn<TransakcijaDTO,Double> tableColumnIznos;
	@FXML
	private TableColumn<TransakcijaDTO,String> tableColumnDatum;
	@FXML
	private TableColumn<TransakcijaDTO,String> tableColumnOpis;
	@FXML
	private TableColumn<TransakcijaDTO,String> tableColumnTipTransakcije;
	@FXML
	private ComboBox<TipTransakcijeDTO> comboBoxTipTransakcije;
	@FXML
	private Button btnDodajTipTransakcije;
	@FXML
	private Button btnDodajBudzet;
	@FXML
	private TextField txtIznos;
	@FXML
	private TextField txtIznosBudzet;
	@FXML
	private TextField txtSezona;
	@FXML
	private Label lblOpis;
	@FXML
	private Label lblDatum;
	@FXML
	private Label lblVrsta;
	@FXML
	private ComboBox<String> comboBoxVrsta;
	@FXML
	private Button btnDodaj;
	@FXML
	private RadioButton radioBtnPrihodi;
	@FXML
	private RadioButton radioBtnRashodi;
	
	private NovcanaSredstvaDTO trenutnaNS;
	private ObservableList<TransakcijaDTO> listaTransakcija;
	private ObservableList<TipTransakcijeDTO> listaTip;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		super.setController(new TransakcijaController(txtIznos, datePicker, txtOpis, radiobtnSve,btnPrikazi));

		this.popuniTabelu();
		this.popuniComboBox();
		btnDodaj.setDisable(true);
		BooleanBinding bb = txtIznos.textProperty().isEmpty().or(datePicker.valueProperty().isNull());
		btnDodaj.disableProperty().bind(bb);
		txtIznos.textProperty().addListener((observable, oldValue, newValue) -> {
			if(poljaPrazna())
				btnDodaj.disableProperty().bind(bb);
		});
		datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
			if(poljaPrazna())
				btnDodaj.disableProperty().bind(bb);
		});
		
		btnDodajBudzet.setDisable(true);
		BooleanBinding bb1 = txtSezona.textProperty().isEmpty().or(txtIznosBudzet.textProperty().isEmpty());
		btnDodajBudzet.disableProperty().bind(bb1);
		txtIznosBudzet.textProperty().addListener((observable, oldValue, newValue) -> {
			if(poljaPraznaBudzet())
				btnDodajBudzet.disableProperty().bind(bb1);
		});
		txtSezona.textProperty().addListener((observable, oldValue, newValue) -> {
			if(poljaPraznaBudzet())
				btnDodajBudzet.disableProperty().bind(bb1);
		});
	}
	private void obrisiPoljaBudzet() {
		txtSezona.setText("");
		txtIznosBudzet.setText("");
	}
	private boolean poljaPraznaBudzet() {
		return txtIznosBudzet.getText().isEmpty() || txtSezona.getText().isEmpty();
	}
	  @FXML
	    void odjaviteSe(ActionEvent event) {
	    	try {
				BaseController.changeScene("/application/gui/administrator/view/LoginView.fxml", primaryStage);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	// Event Listener on Button[#btnDodaj].onAction
	public void prikazi() { //zabraniti da se doda za istu sezonu ako nije obrisano za proslu, pitanje korisniku?
		String sezona = comboBoxSezona.getValue();
		NovcanaSredstvaDTO ns = DAOFactory.getDAOFactory().getNovcanaSredstvaDAO().getBySezona(sezona);
		this.prikaziLabele(ns);
	}
	private void prikaziLabele(NovcanaSredstvaDTO ns) {
		if(ns!=null) {
			comboBoxSezona.getSelectionModel().select(ns.getSezona());
			lblPrihodi.setText(ns.getPrihodi().toString());
			lblRashodi.setText(ns.getRashodi().toString());
			lblUsvojeniBudzet.setText(ns.getBudzet().toString());
			lblNovcanaSredstva.setText(new Double(ns.getBudzet()-ns.getRashodi()+ns.getPrihodi()).toString());
		}
		else {
			lblPrihodi.setText("-");
			lblRashodi.setText("-");
			lblUsvojeniBudzet.setText("-");
			lblNovcanaSredstva.setText("-");
		}
	}

	@FXML
	public void dodajTipTransakcije(ActionEvent event) {
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
	private void popuniTabelu() {
		
		postaviKolone();
		listaTransakcija = DAOFactory.getDAOFactory().getTransakcijaDAO().SELECT_ALL();
		tableView.setItems(listaTransakcija);
		tableView.getSelectionModel().select(0);
		
	}
	
	private void postaviKolone() {
		tableColumnVrsta.setCellValueFactory(new PropertyValueFactory<TransakcijaDTO, String>("vrsta"));
		tableColumnOpis.setCellValueFactory(new PropertyValueFactory<TransakcijaDTO, String>("opis"));
		tableColumnIznos.setCellValueFactory(new PropertyValueFactory<TransakcijaDTO, Double>("iznos"));
		tableColumnDatum.setCellValueFactory(new PropertyValueFactory<TransakcijaDTO,String>("datum"));
		tableColumnTipTransakcije.setCellValueFactory(new PropertyValueFactory<TransakcijaDTO,String>("tipTransakcije"));
	}
	private void popuniComboBox() {//paziti na null ptr
		listaTip = DAOFactory.getDAOFactory().getTipTransakcijeDAO().SELECT_ALL();
		comboBoxTipTransakcije.setItems(listaTip);
		comboBoxTipTransakcije.getSelectionModel().selectFirst();
		ObservableList<String> list = FXCollections.observableArrayList();
		list.add("Prihod");
		list.add("Rashod");
		comboBoxVrsta.setItems(list);
		comboBoxVrsta.getSelectionModel().selectFirst();
		comboBoxSezona.setItems(DAOFactory.getDAOFactory().getNovcanaSredstvaDAO().getSezone());
		trenutnaNS = DAOFactory.getDAOFactory().getNovcanaSredstvaDAO().getNSMaxId();
		System.out.println(trenutnaNS);
		this.prikaziLabele(trenutnaNS);
	}
	public void obrisiPolja() {
		txtIznos.setText("");
		txtOpis.setText("");
		datePicker.setValue(null);
	}
	public TransakcijaDTO dodaj() {
		if(trenutnaNS==null) {
			String sezona = comboBoxSezona.getSelectionModel().getSelectedItem();
			if(sezona!=null) {
				trenutnaNS= DAOFactory.getDAOFactory().getNovcanaSredstvaDAO().getBySezona(sezona);
			}else {
				Alert alert = new Alert(AlertType.ERROR,"Nema podataka o trenutnom budžetu.");
				alert.setTitle("Greška");
				alert.setHeaderText("Greška prilikom dodavanja");
				alert.showAndWait();
				return null;
			}
			if(trenutnaNS==null) {
				Alert alert = new Alert(AlertType.ERROR,"Nema podataka o trenutnom budžetu.");
				alert.setTitle("Greška");
				alert.setHeaderText("Greška prilikom dodavanja");
				alert.showAndWait();
				return null;
			}
		}
		TransakcijaDTO tran = super.dodaj();
		if(tran==null)
			return null;
		TipTransakcijeDTO tip = comboBoxTipTransakcije.getValue();
		String vrsta = comboBoxVrsta.getValue(); 
		boolean jeUplata = false;
		if(vrsta.equals("Prihod"))
			jeUplata=true;
		TransakcijaDTO transakcija = new TransakcijaDTO(null, tran.getDatum(), tran.getIznos().get(), tran.getOpis().get(), tip.getTip(), jeUplata);
		listaTransakcija.add(transakcija);
		int ok = DAOFactory.getDAOFactory().getTransakcijaDAO().INSERT(transakcija, tip);
		if(ok!=0) {
			if(jeUplata) {
				DAOFactory.getDAOFactory().getNovcanaSredstvaDAO().dodajPrihode(transakcija.getIznos().get());
				trenutnaNS.setPrihodi(trenutnaNS.getPrihodi()+transakcija.getIznos().get());

			}else {
				DAOFactory.getDAOFactory().getNovcanaSredstvaDAO().dodajRashode(transakcija.getIznos().get());
				trenutnaNS.setRashodi(trenutnaNS.getRashodi()+transakcija.getIznos().get());
			}
			prikaziLabele(trenutnaNS);
			Alert alert = new Alert(AlertType.INFORMATION, "Uspješno dodavanje!");
			alert.setTitle("Informacija");
			alert.setHeaderText("Dodavanje");
			alert.showAndWait();
			this.obrisiPolja();
			return transakcija;
		}
		return null;
	}
	public void izmijeni() { //obrisi
		Stage noviStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/racunovodja/view/IzmijeniTransakciju.fxml"));
		AnchorPane root;
		IzmijeniTransakcijuController controller=null;
		
		try {
			root = (AnchorPane) loader.load();//initialize
			Scene scene = new Scene(root,300,400);
			controller = loader.<IzmijeniTransakcijuController>getController();
			controller.setPrimaryStage(noviStage);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			TransakcijaDTO transakcija = tableView.getSelectionModel().getSelectedItem();
			controller.setListaTransakcija(listaTransakcija);
			controller.setListaTip(listaTip);
			controller.setTxtIznos(new String(transakcija.getIznos().getValue().toString()));
			controller.setTxtOpis(transakcija.getOpis().get());
			controller.setDatePicker(transakcija.getDatum());
			controller.setTransakcija(transakcija);
			controller.setEvidentiranjeController(this);
			noviStage.showAndWait();
			//postaviKolone();
			//tableClanarine.setItems(listaClanarina);
			tableView.refresh();
			postaviKolone();
			//popuniTabelu();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prikaziLabele(trenutnaNS);
	}
	public ObservableList<TransakcijaDTO> getListaTransakcija() {
		return listaTransakcija;
	}
	public void setListaTransakcija(ObservableList<TransakcijaDTO> listaTransakcija) {
		this.listaTransakcija = listaTransakcija;
	}
	
	public void dodajBudzet() {
		String sezona = txtSezona.getText().trim();
		Double iznos = null;
		try {
			iznos = Double.parseDouble(txtIznosBudzet.getText());
			if(iznos<0)
				throw new NumberFormatException();
		}catch(NumberFormatException ex) {
			Alert alert = new Alert(AlertType.ERROR, "Niste ispravno unijeli informaciju o iznosu.");
			alert.setTitle("Greška");
			alert.setHeaderText("Greška prilikom dodavanja");
			this.obrisiPolja();
			alert.showAndWait();
			return;
		}
		NovcanaSredstvaDTO ns = new NovcanaSredstvaDTO(sezona, iznos, new Double(0), new Double(0));
		boolean ok = DAOFactory.getDAOFactory().getNovcanaSredstvaDAO().INSERT(ns);
		if(ok) {
			comboBoxSezona.setItems(DAOFactory.getDAOFactory().getNovcanaSredstvaDAO().getSezone());
			comboBoxSezona.getSelectionModel().select(0);
			Alert alert = new Alert(AlertType.INFORMATION, "Uspješno dodavanje!");
			alert.setTitle("Informacija");
			alert.setHeaderText("Dodavanje");
			btnPrikazi.fire();
			this.obrisiPolja();
			alert.showAndWait();
			obrisiPoljaBudzet();
		}
		
		
	}
	public void obrisi() {
		DAOFactory.getDAOFactory().getTransakcijaDAO().delete(tableView.getSelectionModel().getSelectedItem().getId());
		listaTransakcija.remove(tableView.getSelectionModel().getSelectedItem());
		tableView.refresh();
	}
}
