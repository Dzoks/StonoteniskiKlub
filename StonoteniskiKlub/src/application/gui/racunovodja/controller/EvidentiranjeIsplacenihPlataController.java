package application.gui.racunovodja.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.PlataDTO;
import application.model.dto.TransakcijaDTO;
import application.model.dto.ZaposleniDTO;
import application.util.ErrorLogger;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EvidentiranjeIsplacenihPlataController extends TransakcijaDecorater{
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
	private ObservableList<PlataDTO> lista = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.setController(new TransakcijaController(txtIznos, datePicker, txtOpis, radiobtnSve,btnPrikazi));

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
		BooleanBinding bindingObrisi = tablePlate.getSelectionModel().selectedItemProperty().isNull();
		btnObrisi.disableProperty().bind(bindingObrisi);
		btnIzmijeni.disableProperty().bind(bindingObrisi);
	}
	
	  @FXML
	    void odjaviteSe(ActionEvent event) {
	    	try {
				BaseController.changeScene("/application/gui/administrator/view/LoginView.fxml", primaryStage);
			} catch (IOException e) {
				e.printStackTrace();
				new ErrorLogger().log(e);
			}
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
		listaPlata = DAOFactory.getDAOFactory().getPlataDAO().SELECT_ALL();
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
			noviStage.setTitle("Stonoteniski klub");
			noviStage.initModality(Modality.APPLICATION_MODAL); 
			//odavde
			PlataDTO plata = tablePlate.getSelectionModel().getSelectedItem(); //+
			controller.setListaPlata(listaPlata);								//+
			//
			System.out.println(controller);
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
			new ErrorLogger().log(e);
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
	public TransakcijaDTO dodaj() {
		TransakcijaDTO transakcija = super.dodaj();
		if(transakcija==null)
			return null;
		ZaposleniDTO zaposleni = comboBoxZaposleniDodaj.getSelectionModel().getSelectedItem();
		String tipTransakcije = DAOFactory.getDAOFactory().getTipTransakcijeDAO().getById(2).getTip();
		PlataDTO plata = new PlataDTO(null, transakcija.getDatum(), transakcija.getIznos().doubleValue(), transakcija.getOpis().getValue(), tipTransakcije, zaposleni);
		boolean ok = DAOFactory.getDAOFactory().getPlataDAO().INSERT(plata,zaposleni);
		if(ok) {
			DAOFactory.getDAOFactory().getNovcanaSredstvaDAO().dodajRashode(plata.getIznos().get());
			listaPlata.add(plata);
			super.uspjesnoDodavanje();
			return plata;
		}
		return null;
	}
	
	public void prikazi() {
		lista = FXCollections.observableArrayList();
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
		PlataDTO temp = tablePlate.getSelectionModel().getSelectedItem();
		DAOFactory.getDAOFactory().getTransakcijaDAO().delete(temp.getId());
		DAOFactory.getDAOFactory().getNovcanaSredstvaDAO().dodajRashode(-temp.getIznos().get());
		listaPlata.remove(tablePlate.getSelectionModel().getSelectedItem());
		if(!radiobtnSve.isSelected()) {
			lista.remove(tablePlate.getSelectionModel().getSelectedItem());
		}
		tablePlate.refresh();
	}
}
