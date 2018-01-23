package application.gui.racunovodja.controller;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dao.DistributerOpremeDAO;
import application.model.dao.NarudzbaDAO;
import application.model.dao.NovcanaSredstvaDAO;
import application.model.dao.TipTransakcijeDAO;
import application.model.dao.TransakcijaDAO;
import application.model.dao.TroskoviOpremaDAO;
import application.model.dao.TroskoviTurnirDAO;
import application.model.dao.mysql.MySQLTurnirDAO;
import application.model.dto.DistributerOpreme;
import application.model.dto.Narudzba;
import application.model.dto.TransakcijaDTO;
import application.model.dto.TroskoviOpremaDTO;
import application.model.dto.TroskoviTurnirDTO;
import application.model.dto.TurnirDTO;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;

import javafx.scene.control.ScrollPane;

import javafx.scene.control.ComboBox;

import javafx.scene.control.TextArea;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.RadioButton;

import javafx.scene.control.TableView;

import javafx.scene.control.DatePicker;

import javafx.scene.control.TableColumn;

public class EvidentiranjeSredstavaZaTurnireController extends TransakcijaDecorater{
	@FXML
	private AnchorPane pane;
	@FXML
	private TableView<TroskoviTurnirDTO> tableTroskoviTurnir;
	@FXML
	private TableColumn<TroskoviTurnirDTO,String> tableColumnNazivTurnira;
	@FXML
	private TableColumn<TroskoviTurnirDTO,String> tableColumnDatumUplate;
	@FXML
	private TableColumn<TroskoviTurnirDTO,String> tableColumnOpis;
	@FXML
	private TableColumn<TroskoviTurnirDTO,Double> tableColumnIznos;
	@FXML
	private Label lblPrikazPo;
	@FXML
	private RadioButton radiobtnTurnir;
	@FXML
	private Button btnDodaj;
	@FXML
	private Label lblIznos;
	@FXML
	private Label lblKM;
	@FXML
	private Label lblDatum;
	@FXML
	private Button btnObrisi;
	@FXML
	private Button btnIzmijeni;
	@FXML
	private Label lblTurnirDodaj;
	@FXML
	private ComboBox<TurnirDTO> comboBoxTurnir;
	@FXML
	private Label lblTurnirPrikazi;
	@FXML
	private ComboBox<TurnirDTO> comboBoxTurnirPrikazi;
	@FXML
	private Label lblOpis;
	@FXML
	private ScrollPane scrollPane;
	
	
	private ObservableList<TroskoviTurnirDTO> listaTroskovi;
	private	ObservableList<TroskoviTurnirDTO> lista = FXCollections.observableArrayList();

	public void obrisi() {
		DAOFactory.getDAOFactory().getTransakcijaDAO().delete(tableTroskoviTurnir.getSelectionModel().getSelectedItem().getId());
		listaTroskovi.remove(tableTroskoviTurnir.getSelectionModel().getSelectedItem());
		if(!radiobtnSve.isSelected()) {
			lista.remove(tableTroskoviTurnir.getSelectionModel().getSelectedItem());
		}
		tableTroskoviTurnir.refresh();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		super.setController(new TransakcijaController(txtIznos, datePicker, txtOpis, radiobtnSve,btnPrikazi));

		ToggleGroup group = new ToggleGroup();
		radiobtnTurnir.setToggleGroup(group);
		radiobtnSve.setToggleGroup(group);
		this.popuniComboBox();
		this.popuniTabelu();
		comboBoxTurnirPrikazi.setDisable(true);
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
		BooleanBinding binding = radiobtnSve.selectedProperty().not().and(radiobtnTurnir.selectedProperty().not());
		btnPrikazi.disableProperty().bind(binding);
		tableTroskoviTurnir.getSelectionModel().select(0);
	
	}
	  @FXML
	    void odjaviteSe(ActionEvent event) {
	    	try {
				BaseController.changeScene("/application/gui/administrator/view/LoginView.fxml", primaryStage);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }

	public void radioSve() {
		comboBoxTurnirPrikazi.setDisable(true);
	}
	public void radioTurnir() {
		comboBoxTurnirPrikazi.setDisable(false);
	}
	private void popuniTabelu() {
		this.postaviKolone();
		listaTroskovi = DAOFactory.getDAOFactory().getTroskoviTurnirDAO().SELECT_ALL();
		tableTroskoviTurnir.setItems(listaTroskovi);
	}
	private void popuniComboBox(){
		ObservableList<TurnirDTO> listaTurnira = DAOFactory.getDAOFactory().getTurnirDAO().getAll();
		comboBoxTurnir.setItems(listaTurnira);
		if(!listaTurnira.isEmpty())
			comboBoxTurnir.getSelectionModel().select(0);
		comboBoxTurnirPrikazi.setItems(listaTurnira);
		if(!listaTurnira.isEmpty())
			comboBoxTurnirPrikazi.getSelectionModel().select(0);
	}
	private void postaviKolone() {
		tableColumnNazivTurnira.setCellValueFactory(new PropertyValueFactory<TroskoviTurnirDTO, String>("nazivTurnira"));
		tableColumnDatumUplate.setCellValueFactory(new PropertyValueFactory<TroskoviTurnirDTO, String>("datum"));
		tableColumnIznos.setCellValueFactory(new PropertyValueFactory<TroskoviTurnirDTO, Double>("iznos"));
		tableColumnOpis.setCellValueFactory(new PropertyValueFactory<TroskoviTurnirDTO, String>("opis"));
	}
	public TransakcijaDTO dodaj() {
		TransakcijaDTO transakcija = super.dodaj();
		if(transakcija==null)
			return null;
		TurnirDTO turnir = comboBoxTurnir.getValue();
		String tipTransakcije = DAOFactory.getDAOFactory().getTipTransakcijeDAO().getById(5).getTip();
		TroskoviTurnirDTO troskovi = new TroskoviTurnirDTO(null, transakcija.getDatum(), transakcija.getIznos().get(), transakcija.getOpis().get(), tipTransakcije, turnir);
		boolean ok = DAOFactory.getDAOFactory().getTroskoviTurnirDAO().INSERT(troskovi,turnir);
		if(ok) {
			listaTroskovi.add(troskovi);
			DAOFactory.getDAOFactory().getNovcanaSredstvaDAO().dodajRashode(troskovi.getIznos().get());
			super.uspjesnoDodavanje();
			return troskovi;
		}
		return null;
	}
	public void prikazi() {
		lista = FXCollections.observableArrayList();
		System.out.println("prikazi");
		if(radiobtnSve.isSelected()) {
			tableTroskoviTurnir.setItems(listaTroskovi);
		}else if(radiobtnTurnir.isSelected()) {
			TurnirDTO turnir= comboBoxTurnirPrikazi.getValue();
			for(TroskoviTurnirDTO to : listaTroskovi) {
				if(to.getTurnir().getId() == turnir.getId()) {
					lista.add(to);
				}
			}
			tableTroskoviTurnir.setItems(lista);
		}
		tableTroskoviTurnir.getSelectionModel().select(0);
	}
	public void izmijeni() {
		Stage noviStage = new Stage();
		System.out.println("prije loader");
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/racunovodja/view/IzmijeniSredstvaZaTurnir.fxml"));
		AnchorPane root;
		IzmijeniSredstvaZaTurnirController controller=null;
		
		try {
			root = (AnchorPane) loader.load();//initialize
			Scene scene = new Scene(root,300,400);
			controller = loader.<IzmijeniSredstvaZaTurnirController>getController();
			controller.setPrimaryStage(noviStage);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			TroskoviTurnirDTO trosak = tableTroskoviTurnir.getSelectionModel().getSelectedItem();
			controller.setTrosak(trosak);
			controller.setListaTroskovi(listaTroskovi);
			controller.setTxtIznos(new String(trosak.getIznos().getValue().toString()));
			controller.setTxtOpis(trosak.getOpis().get());
			controller.setDatePicker(trosak.getDatum());
			
			controller.setEvidentiranjeController(this);
			noviStage.showAndWait();
			//postaviKolone();
			//tableClanarine.setItems(listaClanarina);
			tableTroskoviTurnir.refresh();
			postaviKolone();
			//popuniTabelu();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	public ObservableList<TroskoviTurnirDTO> getListaTroskovi() {
		return listaTroskovi;
	}
	public void setListaTroskovi(ObservableList<TroskoviTurnirDTO> listaTroskovi) {
		this.listaTroskovi = listaTroskovi;
	}
}
