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
import application.model.dao.TurnirDAO;
import application.model.dao.UcesnikPrijavaDAO;
import application.model.dao.UplataZaTurnirDAO;
import application.model.dto.ClanDTO;
import application.model.dto.ClanarinaDTO;
import application.model.dto.TransakcijaDTO;
import application.model.dto.TroskoviTurnirDTO;
import application.model.dto.TurnirDTO;
import application.model.dto.UcesnikPrijavaDTO;
import application.model.dto.UplataZaTurnirDTO;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

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

public class EvidentiranjeUplataZaTurnirController extends TransakcijaDecorater{
	@FXML
	private AnchorPane pane;
	@FXML
	private TableView<UplataZaTurnirDTO> tableUplateZaTurnir;
	@FXML
	private TableColumn<UplataZaTurnirDTO,String> tableColumnIme;
	@FXML
	private TableColumn<UplataZaTurnirDTO,String> tableColumnPrezime;
	@FXML
	private TableColumn<UplataZaTurnirDTO,String> tableColumnNazivTurnira;
	@FXML
	private TableColumn<UplataZaTurnirDTO,String> tableColumnDatumUplate;
	@FXML
	private TableColumn<UplataZaTurnirDTO,Double> tableColumnIznos;
	@FXML
	private Label lblPrikazPo;
	@FXML
	private RadioButton radiobtnUcesnik;
	@FXML
	private RadioButton radiobtnTurnir;
	@FXML
	private RadioButton radiobtnSve;
	@FXML
	private Label lblUcesnik;
	@FXML
	private ComboBox<UcesnikPrijavaDTO> comboBoxUcesnikPrikazi;
	@FXML
	private Button btnDodaj;
	@FXML
	private Label lblUcesnikDodaj;
	@FXML
	private ComboBox<UcesnikPrijavaDTO> comboBoxClanUcesnik;
	@FXML
	private Label lblIznos;
	
	@FXML
	private Label lblKM;
	@FXML
	private Label lblDatumUplate;
	
	@FXML
	private Button btnPrikazi;
	@FXML
	private Button btnObrisi;
	@FXML
	private Button btnIzmijeni;
	@FXML
	private Label lblTurnirPrikazi;
	@FXML
	private ComboBox<TurnirDTO> comboBoxTurnirPrikazi;
	@FXML
	private Label lblOpis;
	@FXML
	private ScrollPane scrollPane;
	

	private ObservableList<UplataZaTurnirDTO> listaUplata;
	private ObservableList<UcesnikPrijavaDTO> listaUcesnika;
	private ObservableList<TurnirDTO> listaTurnira;
	private ObservableList<UplataZaTurnirDTO> lista = FXCollections.observableArrayList();

	public void obrisi() {
		DAOFactory.getDAOFactory().getTransakcijaDAO().delete(tableUplateZaTurnir.getSelectionModel().getSelectedItem().getId());
		listaUplata.remove(tableUplateZaTurnir.getSelectionModel().getSelectedItem());
		if(!radiobtnSve.isSelected()) {
			lista.remove(tableUplateZaTurnir.getSelectionModel().getSelectedItem());
		}
		tableUplateZaTurnir.refresh();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.setTxtIznos(txtIznos);
		super.setDatePicker(datePicker);
		super.setTxtOpis(txtOpis);
		super.setBtnPrikazi(btnPrikazi);
		super.setRadiobtnSve(radiobtnSve);
		// TODO Auto-generated method stub
		ToggleGroup group = new ToggleGroup();
		radiobtnTurnir.setToggleGroup(group);
		radiobtnUcesnik.setToggleGroup(group);
		radiobtnSve.setToggleGroup(group);
		this.popuniTabelu();
		this.popuniComboBox();
		comboBoxTurnirPrikazi.setDisable(true);
		comboBoxUcesnikPrikazi.setDisable(true);
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
		BooleanBinding binding = radiobtnSve.selectedProperty().not().and(radiobtnTurnir.selectedProperty().not()).and(radiobtnUcesnik.selectedProperty().not());
		btnPrikazi.disableProperty().bind(binding);
	}
	
	public void radioUcesnik() {
		comboBoxUcesnikPrikazi.setDisable(false);
		comboBoxTurnirPrikazi.setDisable(true);
	}
	public void radioTurnir() {
		comboBoxUcesnikPrikazi.setDisable(true);
		comboBoxTurnirPrikazi.setDisable(false);
	}
	public void radioSve() {
		comboBoxUcesnikPrikazi.setDisable(true);
		comboBoxTurnirPrikazi.setDisable(true);
	}
	private void popuniTabelu() {
		
		postaviKolone();
		listaUplata = DAOFactory.getDAOFactory().getUplataZaTurnirDAO().SELECT_ALL();
		tableUplateZaTurnir.setItems(listaUplata);
		tableUplateZaTurnir.getSelectionModel().select(0);
		
	}
	private void postaviKolone() {
		tableColumnIme.setCellValueFactory(new PropertyValueFactory<UplataZaTurnirDTO, String>("ime"));
		tableColumnPrezime.setCellValueFactory(new PropertyValueFactory<UplataZaTurnirDTO, String>("prezime"));
		tableColumnIznos.setCellValueFactory(new PropertyValueFactory<UplataZaTurnirDTO, Double>("iznos"));
		tableColumnDatumUplate.setCellValueFactory(new PropertyValueFactory<UplataZaTurnirDTO, String>("datum"));
		tableColumnNazivTurnira.setCellValueFactory(new PropertyValueFactory<UplataZaTurnirDTO, String>("nazivTurnira"));
	}
	private void popuniComboBox() {
		listaUcesnika = UcesnikPrijavaDAO.SELECT_ALL();
		//listaUcesnika = DAOFactory.getDAOFactory().
		comboBoxClanUcesnik.setItems(listaUcesnika);
		comboBoxClanUcesnik.getSelectionModel().selectFirst();
		comboBoxUcesnikPrikazi.setItems(listaUcesnika);
		comboBoxUcesnikPrikazi.getSelectionModel().selectFirst();
		listaTurnira = TurnirDAO.getAll();
		comboBoxTurnirPrikazi.setItems(listaTurnira);
		comboBoxTurnirPrikazi.getSelectionModel().select(0);
	}
	
	public TransakcijaDTO dodaj() {
		System.out.println("dodaj");
		for(UcesnikPrijavaDTO u : listaUcesnika) {
			System.out.println(u.getIdPrijave());
		}
		TransakcijaDTO transakcija = super.dodaj();
		if(transakcija==null)
			return null;
		
		UcesnikPrijavaDTO ucesnik = comboBoxClanUcesnik.getSelectionModel().getSelectedItem();
		
		String tipTransakcije = DAOFactory.getDAOFactory().getTipTransakcijeDAO().getById(4).getTip();
		UplataZaTurnirDTO uplata = new UplataZaTurnirDTO(null, transakcija.getDatum(), transakcija.getIznos().get(), transakcija.getOpis().get(), tipTransakcije, ucesnik);
		System.out.println("Ucesnik "+ucesnik.getIdPrijave());
		//comboBoxUcesnikPrikazi.
		boolean ok = DAOFactory.getDAOFactory().getUplataZaTurnirDAO().INSERT(uplata, ucesnik);
		if(ok) {
			listaUplata.add(uplata);
			tableUplateZaTurnir.setItems(listaUplata);
			DAOFactory.getDAOFactory().getNovcanaSredstvaDAO().dodajPrihode(uplata.getIznos().get());
			super.uspjesnoDodavanje();
			return uplata;
		}
		return null;
	}
	public void prikazi() {
		System.out.println("prikazi");
		lista = FXCollections.observableArrayList();
		if(radiobtnTurnir.isSelected()) {
			TurnirDTO turnir = comboBoxTurnirPrikazi.getSelectionModel().getSelectedItem();
			for(UplataZaTurnirDTO cl : listaUplata) {
				if(cl.getUcesnik().getIdTurnira() == turnir.getId()) {
					lista.add(cl);
				}
			}
			tableUplateZaTurnir.setItems(lista);
		}else if(radiobtnUcesnik.isSelected()) {
			System.out.println("mjesec");
			UcesnikPrijavaDTO ucesnik = comboBoxUcesnikPrikazi.getSelectionModel().getSelectedItem();
			for(UplataZaTurnirDTO cl : listaUplata) {
				if(cl.getUcesnik().getIdPrijave()==ucesnik.getIdPrijave()) {
					lista.add(cl);
				}
			}
			tableUplateZaTurnir.setItems(lista);
		}else if(radiobtnSve.isSelected()) {
			tableUplateZaTurnir.setItems(listaUplata);
		}
	}
	public ObservableList<UplataZaTurnirDTO> getListaUplata() {
		return listaUplata;
	}
	public void setListaUplata(ObservableList<UplataZaTurnirDTO> listaUplata) {
		this.listaUplata = listaUplata;
	}
	public void izmijeni() {
		Stage noviStage = new Stage();
		System.out.println("prije loader");
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/racunovodja/view/IzmijeniUplatuZaTurnir.fxml"));
		AnchorPane root;
		IzmijeniUplatuZaTurnirController controller=null;
		
		try {
			root = (AnchorPane) loader.load();//initialize
			Scene scene = new Scene(root,300,400);
			controller = loader.<IzmijeniUplatuZaTurnirController>getController();
			controller.setPrimaryStage(noviStage);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa finansijama");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			UplataZaTurnirDTO trosak = tableUplateZaTurnir.getSelectionModel().getSelectedItem();
			controller.setUplata(trosak);
			controller.setListaUplate(listaUplata);
			controller.setComboBoxUcesnik(UcesnikPrijavaDAO.SELECT_ALL());
			controller.setTxtIznos(new String(trosak.getIznos().getValue().toString()));
			controller.setTxtOpis(trosak.getOpis().get());
			controller.setDatePicker(trosak.getDatum());
			controller.setEvidentiranjeController(this);
			noviStage.showAndWait();
			//postaviKolone();
			//tableClanarine.setItems(listaClanarina);
			tableUplateZaTurnir.refresh();
			postaviKolone();
			//popuniTabelu();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void ispisi() {
		System.out.println(comboBoxClanUcesnik.getSelectionModel().getSelectedItem().getIdPrijave());
	}
}
