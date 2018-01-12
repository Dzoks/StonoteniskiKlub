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
import application.model.dao.ClanarinaDAO;
import application.model.dao.DAOFactoryTransakcije;
import application.model.dao.DistributerOpremeDAO;
import application.model.dao.NarudzbaDAO;
import application.model.dao.NovcanaSredstvaDAO;
import application.model.dao.TipTransakcijeDAO;
import application.model.dao.TransakcijaDAO;
import application.model.dao.TroskoviOpremaDAO;
import application.model.dto.ClanDTO;
import application.model.dto.ClanarinaDTO;
import application.model.dto.DistributerOpreme;
import application.model.dto.Narudzba;
import application.model.dto.TroskoviOpremaDTO;
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

public class EvidentiranjeSredstavaZaOpremuController extends BaseController{
	@FXML
	private AnchorPane pane;
	@FXML
	private TableView<TroskoviOpremaDTO> tableTroskoviOprema;
	@FXML
	private TableColumn<TroskoviOpremaDTO,String> tableColumnNarudzba;
	@FXML
	private TableColumn<TroskoviOpremaDTO,String> tableColumnDatumUplate;
	@FXML
	private TableColumn<TroskoviOpremaDTO,Double> tableColumnIznos;
	@FXML
	private TableColumn<TroskoviOpremaDTO,String> tableColumnOpis;
	@FXML
	private TableColumn<TroskoviOpremaDTO, String> tableColumnDistributer;
	@FXML
	private Label lblPrikazPo;
	@FXML
	private RadioButton radiobtnSve;
	@FXML
	private RadioButton radiobtnDistributer;
	@FXML
	private Button btnDodaj;
	@FXML
	private Label lblIznos;
	@FXML
	private TextField txtIznos;
	@FXML
	private Label lblKM;
	@FXML
	private Label lblDatum;
	@FXML
	private DatePicker datePicker;
	@FXML
	private Button btnPrikazi;
	@FXML
	private Button btnObrisi;
	@FXML
	private Button btnIzmijeni;
	@FXML
	private Label lblNarudzbaDodaj;
	@FXML
	private ComboBox<Narudzba> comboBoxNarudzba;
	@FXML
	private Label lblDistributer;
	@FXML
	private ComboBox<DistributerOpreme> comboBoxDistributer;
	@FXML
	private Label lblOpis;
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private TextArea txtOpis;
	
	private ObservableList<TroskoviOpremaDTO> listaTroskovi;
	
	public void obrisi() {
		DAOFactoryTransakcije.getDAOFactory().getTransakcijaDAO().delete(tableTroskoviOprema.getSelectionModel().getSelectedItem().getId());
		listaTroskovi.remove(tableTroskoviOprema.getSelectionModel().getSelectedItem());
		tableTroskoviOprema.refresh();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ToggleGroup group = new ToggleGroup();
		radiobtnDistributer.setToggleGroup(group);
		radiobtnSve.setToggleGroup(group);
		this.popuniComboBox();
		this.popuniTabelu();
		comboBoxDistributer.setDisable(true);
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
		BooleanBinding binding = radiobtnSve.selectedProperty().not().and(radiobtnDistributer.selectedProperty().not());
		btnPrikazi.disableProperty().bind(binding);
		tableTroskoviOprema.getSelectionModel().select(0);
	}
	private void obrisiPolja() {
		txtIznos.setText("");
		txtOpis.setText("");
		datePicker.setValue(null);
	}
	public void radioSve() {
		comboBoxDistributer.setDisable(true);
	}
	public void radioDistributer() {
		comboBoxDistributer.setDisable(false);
	}
	private boolean poljaPrazna() {
		return txtIznos.getText().isEmpty() || datePicker.getValue()==null;
	}
	private void popuniTabelu() {
		this.postaviKolone();
		listaTroskovi = DAOFactoryTransakcije.getDAOFactory().getTroskoviOpremaDAO().SELECT_ALL();
		tableTroskoviOprema.setItems(listaTroskovi);
	}
	private void popuniComboBox(){
		ObservableList<Narudzba> listaNarudzba = NarudzbaDAO.SELECT_OPREMA_KLUBA();
		comboBoxNarudzba.setItems(listaNarudzba);
		if(!listaNarudzba.isEmpty())
			comboBoxNarudzba.getSelectionModel().select(0);
		ObservableList<DistributerOpreme> listaDistributer = DistributerOpremeDAO.SELECT_ALL();
		comboBoxDistributer.setItems(listaDistributer);
		if(!listaNarudzba.isEmpty())
			comboBoxDistributer.getSelectionModel().select(0);
	}
	private void postaviKolone() {
		tableColumnNarudzba.setCellValueFactory(new PropertyValueFactory<TroskoviOpremaDTO, String>("narudzba"));
		tableColumnDistributer.setCellValueFactory(new PropertyValueFactory<TroskoviOpremaDTO, String>("distributer"));
		tableColumnDatumUplate.setCellValueFactory(new PropertyValueFactory<TroskoviOpremaDTO, String>("datum"));
		tableColumnIznos.setCellValueFactory(new PropertyValueFactory<TroskoviOpremaDTO, Double>("iznos"));
		tableColumnOpis.setCellValueFactory(new PropertyValueFactory<TroskoviOpremaDTO, String>("opis"));
	}
	public void dodaj() {
		Double iznos = null;
		try {
			iznos = Double.parseDouble(txtIznos.getText());
			if(iznos<0)
				throw new NumberFormatException();
		}catch(NumberFormatException ex) {
			Alert alert = new Alert(AlertType.INFORMATION, "Niste ispravno unijeli informaciju o iznosu.");
			this.obrisiPolja();
			alert.showAndWait();
			return;
		}
		Narudzba narudzba = comboBoxNarudzba.getValue();
		String opis = txtOpis.getText();
		LocalDate localDate = datePicker.getValue();
		Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
		Date datum = Date.from(instant);
		String tipTransakcije = DAOFactoryTransakcije.getDAOFactory().getTipTransakcijeDAO().getById(3).getTip();
		TroskoviOpremaDTO troskovi = new TroskoviOpremaDTO(null, datum, iznos, opis, tipTransakcije, narudzba);
		boolean ok = DAOFactoryTransakcije.getDAOFactory().getTroskoviOpremaDAO().INSERT(troskovi, narudzba);
		if(ok) {
			listaTroskovi.add(troskovi);
			Alert alert = new Alert(AlertType.INFORMATION, "Uspjesno dodavanje!");
			alert.showAndWait();
			this.obrisiPolja();
			DAOFactoryTransakcije.getDAOFactory().getNovcanaSredstvaDAO().dodajRashode(troskovi.getIznos().get());
			radiobtnSve.fire();
			btnPrikazi.fire();
		}
	
	}

	public void prikazi() {
		System.out.println("prikazi");
		ObservableList<TroskoviOpremaDTO> lista = FXCollections.observableArrayList();
		if(radiobtnSve.isSelected()) {
			tableTroskoviOprema.setItems(listaTroskovi);
		}else if(radiobtnDistributer.isSelected()) {
			DistributerOpreme distributer= comboBoxDistributer.getValue();
			for(TroskoviOpremaDTO to : listaTroskovi) {
				if(to.getNarudzba().getIdDistributeraOpreme() == distributer.getId()) {
					lista.add(to);
				}
			}
			tableTroskoviOprema.setItems(lista);
		}
		tableTroskoviOprema.getSelectionModel().select(0);
	}
	public void izmijeni() {
		Stage noviStage = new Stage();
		System.out.println("prije loader");
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/racunovodja/view/IzmijeniSredstvaZaOpremu.fxml"));
		AnchorPane root;
		IzmijeniSredstvaZaOpremuController controller=null;
		
		try {
			root = (AnchorPane) loader.load();//initialize
			Scene scene = new Scene(root,300,400);
			controller = loader.<IzmijeniSredstvaZaOpremuController>getController();
			controller.setPrimaryStage(noviStage);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa finansijama");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			TroskoviOpremaDTO trosak = tableTroskoviOprema.getSelectionModel().getSelectedItem();
			controller.setListaTroskovi(listaTroskovi);
			controller.setComboBoxNarudzba(NarudzbaDAO.SELECT_OPREMA_KLUBA(),trosak.getNarudzba());
			controller.setTxtIznos(new String(trosak.getIznos().getValue().toString()));
			controller.setTxtOpis(trosak.getOpis().get());
			controller.setDatePicker(trosak.getDatum());
			controller.setTrosak(trosak);
			controller.setEvidentiranjeController(this);
			noviStage.showAndWait();
			//postaviKolone();
			//tableClanarine.setItems(listaClanarina);
			tableTroskoviOprema.refresh();
			postaviKolone();
			//popuniTabelu();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	public ObservableList<TroskoviOpremaDTO> getListaTroskovi() {
		return listaTroskovi;
	}
	public void setListaTroskovi(ObservableList<TroskoviOpremaDTO> listaTroskovi) {
		this.listaTroskovi = listaTroskovi;
	}
}
