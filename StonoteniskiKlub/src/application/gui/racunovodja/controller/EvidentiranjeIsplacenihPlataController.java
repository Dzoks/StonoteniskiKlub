package application.gui.racunovodja.controller;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.ClanarinaDAO;
import application.model.dao.DAOFactory;
import application.model.dao.DAOFactoryTransakcije;
import application.model.dao.NovcanaSredstvaDAO;
import application.model.dao.PlataDAO;
import application.model.dao.TipTransakcijeDAO;
import application.model.dao.TransakcijaDAO;
import application.model.dao.ZaposleniDAO;
import application.model.dto.ClanDTO;
import application.model.dto.ClanarinaDTO;
import application.model.dto.PlataDTO;
import application.model.dto.ZaposleniDTO;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import javafx.scene.control.ComboBox;

import javafx.scene.control.TextArea;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.RadioButton;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableView;

import javafx.scene.control.DatePicker;

import javafx.scene.control.TableColumn;

public class EvidentiranjeIsplacenihPlataController extends BaseController{
	@FXML
	private AnchorPane pane;
	@FXML
	private RadioButton radiobtnSve;
	@FXML
	private TableView<PlataDTO> tablePlate;
	@FXML
	private TableColumn<PlataDTO,String> tableColumnIme;
	@FXML
	private TableColumn<PlataDTO,String> tableColumnPrezime;
	@FXML
	private TableColumn<PlataDTO,String> tableColumnRadnoMjesto;
	@FXML
	private TableColumn<PlataDTO, String> tableColumnDatumIsplate;
	@FXML
	private TableColumn <PlataDTO,Double>tableColumnIznos;
	@FXML
	private Label lblPrikazPo;
	@FXML
	private RadioButton radiobtnZaposleni;
	@FXML
	private RadioButton radiobtnMjesec;
	@FXML
	private Label lblMjesec;
	@FXML
	private Spinner<Integer> spinnerMjesecPrikazi;
	@FXML
	private Label lblZaposleni;
	@FXML
	private ComboBox<ZaposleniDTO> comboBoxZaposleniPrikazi;
	@FXML
	private Button btnDodaj;
	@FXML
	private Label lblZaposleniDodaj;
	@FXML
	private ComboBox<ZaposleniDTO> comboBoxZaposleniDodaj;
	@FXML
	private Label lblIznos;
	@FXML
	private TextField txtIznos;
	@FXML
	private Label lblKM;
	@FXML
	private Label lblDatumUplate;
	@FXML
	private DatePicker datePicker;
	@FXML
	private Button btnPrikazi;
	@FXML
	private Button btnObrisi;
	@FXML
	private Button btnIzmijeni;
	@FXML
	private Label lblGodina;
	@FXML
	private Spinner<Integer> spinnerGodina;
	@FXML
	private Label lblOpis;
	@FXML
	private TextArea txtOpis;
	
	private ObservableList<PlataDTO> listaPlata;
	private ObservableList<ZaposleniDTO> listaZaposlenih;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		listaZaposlenih=DAOFactory.getDAOFactory().getZaposleniDAO().selectAll();
		ToggleGroup group = new ToggleGroup();
		radiobtnZaposleni.setToggleGroup(group);
		radiobtnMjesec.setToggleGroup(group);
		radiobtnSve.setToggleGroup(group);
		this.popuniTabelu(); //+
		this.popuniComboBox();//+
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 1);
		SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(2010, 2018, 2014);
		spinnerMjesecPrikazi.setValueFactory(valueFactory);
		spinnerGodina.setValueFactory(valueFactory1);
		tablePlate.getSelectionModel().select(0);
		
		comboBoxZaposleniPrikazi.setDisable(true);
		spinnerMjesecPrikazi.setDisable(true);
		spinnerGodina.setDisable(true);
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
		BooleanBinding binding = radiobtnSve.selectedProperty().not().and(radiobtnZaposleni.selectedProperty().not()).and(radiobtnMjesec.selectedProperty().not());
		btnPrikazi.disableProperty().bind(binding);
	}
	private boolean poljaPrazna() {
		return txtIznos.getText().isEmpty() || datePicker.getValue()==null;
	}
	private void popuniComboBox() {
		comboBoxZaposleniDodaj.setItems(listaZaposlenih);
		comboBoxZaposleniPrikazi.setItems(listaZaposlenih);
		comboBoxZaposleniDodaj.getSelectionModel().select(0);
		comboBoxZaposleniPrikazi.getSelectionModel().select(0);
	}
	private void popuniTabelu() {
		tableColumnIme.setCellValueFactory(new PropertyValueFactory<PlataDTO, String>("ime"));
		tableColumnPrezime.setCellValueFactory(new PropertyValueFactory<PlataDTO, String>("prezime"));
		//??
		tableColumnDatumIsplate.setCellValueFactory(new PropertyValueFactory<PlataDTO, String>("datum")); 
		tableColumnIznos.setCellValueFactory(new PropertyValueFactory<PlataDTO, Double>("iznos")); 
		tableColumnRadnoMjesto.setCellValueFactory(new PropertyValueFactory<PlataDTO,String>("zaposlenje")); 
		//
		listaPlata = DAOFactoryTransakcije.getDAOFactory().getPlataDAO().SELECT_ALL();
		tablePlate.setItems(listaPlata);
	}
	public void izmijeni() {
		Stage noviStage = new Stage();
		System.out.println("prije loader");
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/racunovodja/view/IzmijeniPlatuView.fxml"));
		AnchorPane root;
		IzmijeniPlatuController controller=null;
		
		try {
			root = (AnchorPane) loader.load();//initialize
			Scene scene = new Scene(root,300,400);
			controller = loader.<IzmijeniPlatuController>getController();
			controller.setPrimaryStage(noviStage);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa finansijama");
			noviStage.initModality(Modality.APPLICATION_MODAL); 
			//odavde
			PlataDTO plata = tablePlate.getSelectionModel().getSelectedItem(); //+
			controller.setListaPlata(listaPlata);								//+
			//
			System.out.println(controller);
			controller.setComboBoxZaposleni(listaZaposlenih,plata.getZaposleni().getId()); 
			//
			controller.setTxtIznos(new String(plata.getIznos().getValue().toString())); //+
			controller.setTxtOpis(plata.getOpis().get()); //+
			controller.setDatePicker(plata.getDatum()); //+
			controller.setPlata(plata);//+
			controller.setEvidentiranjeController(this); //+
			noviStage.showAndWait(); //+
			tablePlate.refresh(); //+
			//
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void radioZaposleni() {
		comboBoxZaposleniPrikazi.setDisable(false);
		spinnerMjesecPrikazi.setDisable(true);
		spinnerGodina.setDisable(true);
	}
	public void radioMjesec() {
		spinnerMjesecPrikazi.setDisable(false);
		spinnerGodina.setDisable(false);
		comboBoxZaposleniPrikazi.setDisable(true);
	}
	public void radioSve() {
		comboBoxZaposleniPrikazi.setDisable(true);
		spinnerMjesecPrikazi.setDisable(true);
		spinnerGodina.setDisable(true);
	}
	public ObservableList<PlataDTO> getListaPlata() {
		return listaPlata;
	}
	public void setListaPlata(ObservableList<PlataDTO> listaPlata) {
		this.listaPlata = listaPlata;
	}
	public ObservableList<ZaposleniDTO> getListaZaposlenih() {
		return listaZaposlenih;
	}
	public void setListaZaposlenih(ObservableList<ZaposleniDTO> listaZaposlenih) {
		this.listaZaposlenih = listaZaposlenih;
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
		String opis = txtOpis.getText();//+
		ZaposleniDTO zaposleni = comboBoxZaposleniDodaj.getSelectionModel().getSelectedItem();
		LocalDate localDate = datePicker.getValue();//+
		Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));//+
		Date datum = Date.from(instant);//+
		String tipTransakcije = DAOFactoryTransakcije.getDAOFactory().getTipTransakcijeDAO().getById(2).getTip();
		System.out.println(tipTransakcije);
		PlataDTO plata = new PlataDTO(null, datum, iznos, opis, tipTransakcije, zaposleni);
		DAOFactoryTransakcije.getDAOFactory().getPlataDAO().INSERT(plata,zaposleni);
		DAOFactoryTransakcije.getDAOFactory().getNovcanaSredstvaDAO().dodajRashode(plata.getIznos().get());
		listaPlata.add(plata);
		Alert alert = new Alert(AlertType.INFORMATION, "Uspjesno dodavanje!");
		alert.showAndWait();
		this.obrisiPolja();
		radiobtnSve.fire();
		btnPrikazi.fire();
	}
	private void obrisiPolja() {
		txtIznos.setText("");
		txtOpis.setText("");
		datePicker.setValue(null);
	}
	public void prikazi() {
		ObservableList<PlataDTO> lista = FXCollections.observableArrayList();
		if(radiobtnZaposleni.isSelected()) {
			ZaposleniDTO zaposleni = comboBoxZaposleniPrikazi.getValue();
			for(PlataDTO pl : listaPlata) {
				if(pl.getZaposleni().getId()== zaposleni.getId()) {
					lista.add(pl);
				}
			}
			tablePlate.setItems(lista);
		}else if(radiobtnMjesec.isSelected()) {
			System.out.println("mjesec");
			int mjesec = spinnerMjesecPrikazi.getValue();
			int godina = spinnerGodina.getValue();
			for(PlataDTO pl : listaPlata) {
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(pl.getDatum());
				int month = cal.get(Calendar.MONTH);
				int year = cal.get(Calendar.YEAR);
				System.out.println(pl.getDatum());
				if(month+1==mjesec && year==godina) { 
					lista.add(pl);
				}
			}
			tablePlate.setItems(lista);
		}
		else if(radiobtnSve.isSelected()) {
			tablePlate.setItems(listaPlata);
		}
		tablePlate.getSelectionModel().select(0);
	}
	public void obrisi() {
		DAOFactoryTransakcije.getDAOFactory().getTransakcijaDAO().delete(tablePlate.getSelectionModel().getSelectedItem().getId());
		listaPlata.remove(tablePlate.getSelectionModel().getSelectedItem());
		tablePlate.refresh();
	}
}
