package application.gui.racunovodja.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.ClanDTO;
import application.model.dto.ClanarinaDTO;
import application.model.dto.TransakcijaDTO;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EvidentiranjeClanarinaController extends TransakcijaDecorater{
	@FXML
	private AnchorPane pane;
	@FXML
	private TableView<ClanarinaDTO> tableClanarine;
	@FXML
	private TableColumn<ClanarinaDTO,String> tableColumnIme;
	@FXML
	private TableColumn<ClanarinaDTO,String> tableColumnPrezime;
	@FXML
	private TableColumn<ClanarinaDTO,Integer> tableColumnMjesec;
	@FXML
	private TableColumn<ClanarinaDTO,Integer> tableColumnGodina;
	@FXML
	private TableColumn<ClanarinaDTO,String> tableColumnDatumUplate;
	@FXML
	private TableColumn<ClanarinaDTO, Double> tableColumnIznos;
	@FXML
	private Label lblPrikazPo;
	@FXML
	private RadioButton radiobtnClan;
	@FXML
	private RadioButton radiobtnMjesec;
	
	@FXML
	private Label lblMjesec;
	@FXML
	private Spinner<Integer> spinnerMjesecPrikazi;
	@FXML
	private Label lblClan;
	@FXML
	private ComboBox<ClanDTO> comboBoxClanPrikazi;
	@FXML
	private Button btnDodaj;
	@FXML
	private Label lblMjesecDodaj;
	@FXML
	private Spinner<Integer> spinnerMjesecDodaj;
	@FXML
	private Label lblClanDodaj;
	@FXML
	private ComboBox<ClanDTO> comboBoxClanDodaj;
	@FXML
	private Label lblIznos;
	
	@FXML
	private Label lblKM;
	@FXML
	private Label lblDatumUplate;
	
	
	@FXML
	private Button btnObrisi;
	@FXML
	private Button btnIzmijeni;
	@FXML
	private Label lblGodina;
	@FXML
	private Spinner<Integer> spinnerGodina;
	@FXML
	private Label lblGodinaDodaj;
	@FXML
	private Spinner<Integer> spinnerGodinaDodaj;
	@FXML
	private Label lblOpis;
	
	
	private ObservableList<ClanarinaDTO> listaClanarina;
	private ObservableList<ClanDTO> listaClanova;
	private ObservableList<ClanarinaDTO> lista = FXCollections.observableArrayList(); //za pretragu

	
	  @FXML
	    void odjaviteSe(ActionEvent event) {
	    	try {
				BaseController.changeScene("/application/gui/administrator/view/LoginView.fxml", primaryStage);
			} catch (IOException e) {
				e.printStackTrace();
				new ErrorLogger().log(e);
			}
	    }
	
	@Override
	public void metoda() {
		super.metoda();
		System.out.println("Clanarine metoda");
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		System.out.println(txtIznos);
		System.out.println(lblOpis);
		super.setController(new TransakcijaController(txtIznos, datePicker, txtOpis, radiobtnSve,btnPrikazi));
		
		metoda();
		ToggleGroup group = new ToggleGroup();
		radiobtnClan.setToggleGroup(group);
		radiobtnMjesec.setToggleGroup(group);
		radiobtnSve.setToggleGroup(group);
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 1);
		SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 1);
		SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(2010, 2018, 2014);
		SpinnerValueFactory<Integer> valueFactory4 = new SpinnerValueFactory.IntegerSpinnerValueFactory(2010, 2018, 2014);
		spinnerMjesecDodaj.setValueFactory(valueFactory);
		spinnerMjesecPrikazi.setValueFactory(valueFactory2);
		spinnerGodinaDodaj.setValueFactory(valueFactory1);
		spinnerGodina.setValueFactory(valueFactory4);
		this.popuniTabelu();
		this.popuniComboBox();
		comboBoxClanPrikazi.setDisable(true);
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
		BooleanBinding binding = radiobtnSve.selectedProperty().not().and(radiobtnClan.selectedProperty().not()).and(radiobtnMjesec.selectedProperty().not());
		btnPrikazi.disableProperty().bind(binding);
		BooleanBinding bindingObrisi = tableClanarine.getSelectionModel().selectedItemProperty().isNull();
		btnObrisi.disableProperty().bind(bindingObrisi);
		btnIzmijeni.disableProperty().bind(bindingObrisi);
	}
	
	public void radioClan() {
		comboBoxClanPrikazi.setDisable(false);
		spinnerMjesecPrikazi.setDisable(true);
		spinnerGodina.setDisable(true);
	}
	public void radioMjesec() {
		spinnerMjesecPrikazi.setDisable(false);
		spinnerGodina.setDisable(false);
		comboBoxClanPrikazi.setDisable(true);
	}
	public void radioSve() {
		comboBoxClanPrikazi.setDisable(true);
		spinnerMjesecPrikazi.setDisable(true);
		spinnerGodina.setDisable(true);
	}
	private void popuniTabelu() {
		
		postaviKolone();
		
		listaClanarina = DAOFactory.getDAOFactory().getClanarinaDAO().SELECT_ALL();
		tableClanarine.setItems(listaClanarina);
		tableClanarine.getSelectionModel().select(0);
		
	}
	
	private void postaviKolone() {
		tableColumnIme.setCellValueFactory(new PropertyValueFactory<ClanarinaDTO, String>("imeClana"));
		tableColumnPrezime.setCellValueFactory(new PropertyValueFactory<ClanarinaDTO, String>("prezimeClana"));
		tableColumnMjesec.setCellValueFactory(new PropertyValueFactory<ClanarinaDTO, Integer>("mjesec"));
		tableColumnGodina.setCellValueFactory(new PropertyValueFactory<ClanarinaDTO, Integer>("godina"));
		tableColumnIznos.setCellValueFactory(new PropertyValueFactory<ClanarinaDTO, Double>("iznos"));
		tableColumnDatumUplate.setCellValueFactory(new PropertyValueFactory<ClanarinaDTO,String>("datum"));
	}
	private void popuniComboBox() {
		listaClanova = DAOFactory.getDAOFactory().getClanDAO().SELECT_ALL();
		comboBoxClanDodaj.setItems(listaClanova);
		comboBoxClanDodaj.getSelectionModel().selectFirst();
		comboBoxClanPrikazi.setItems(listaClanova);
		comboBoxClanPrikazi.getSelectionModel().selectFirst();
	}
	
	public ClanarinaDTO dodaj() {
		TransakcijaDTO transakcija = super.dodaj();
		if(transakcija==null)
			return null;
		Integer mjesec = spinnerMjesecDodaj.getValue();
		Integer godina = spinnerGodinaDodaj.getValue();
		ClanDTO clan = comboBoxClanDodaj.getValue();
		String tipTransakcije = DAOFactory.getDAOFactory().getTipTransakcijeDAO().getById(1).getTip();
		ClanarinaDTO clanarina = new ClanarinaDTO(null, transakcija.getDatum(), transakcija.getIznos().doubleValue(), transakcija.getOpis().getValue(), tipTransakcije, mjesec, godina, clan.getIme(), clan.getPrezime(),clan.getId());
		boolean ok = DAOFactory.getDAOFactory().getClanarinaDAO().INSERT(clanarina, clan);
		if(ok) {
			listaClanarina.add(clanarina);
			boolean flag = DAOFactory.getDAOFactory().getNovcanaSredstvaDAO().dodajPrihode(clanarina.getIznos().get());
			super.uspjesnoDodavanje();
			return clanarina;
		}
		return null;
	}
	
	public void prikazi() {
		System.out.println("prikazi");
		lista = FXCollections.observableArrayList();
		if(radiobtnClan.isSelected()) {
			ClanDTO clan = comboBoxClanPrikazi.getValue();
			for(ClanarinaDTO cl : listaClanarina) {
				if(cl.getClanId() == clan.getId()) {
					lista.add(cl);
				}
			}
			tableClanarine.setItems(lista);
		}else if(radiobtnMjesec.isSelected()) {
			System.out.println("mjesec");
			int mjesec = spinnerMjesecPrikazi.getValue();
			int godina = spinnerGodina.getValue();
			for(ClanarinaDTO cl : listaClanarina) {
				if(cl.getMjesec().get()==mjesec && cl.getGodina().get()==godina) {
					lista.add(cl);
				}
			}
			tableClanarine.setItems(lista);
		}else if(radiobtnSve.isSelected()) {
			tableClanarine.setItems(listaClanarina);
		}
		tableClanarine.getSelectionModel().select(0);
	}
	public void izmijeni() {
		Stage noviStage = new Stage();
		System.out.println("prije loader");
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/racunovodja/view/IzmijeniClanarinuView.fxml"));
		AnchorPane root;
		IzmijeniClanarinuController controller=null;
		
		try {
			root = (AnchorPane) loader.load();//initialize
			Scene scene = new Scene(root,300,400);//
			controller = loader.<IzmijeniClanarinuController>getController();
			controller.setPrimaryStage(noviStage);//
			noviStage.setScene(scene);//
			noviStage.setResizable(false);//
			noviStage.setTitle("Stonoteniski klub");//
			noviStage.initModality(Modality.APPLICATION_MODAL);//
			ClanarinaDTO clanarina = tableClanarine.getSelectionModel().getSelectedItem();
			controller.setListaClanova(listaClanova);
			controller.setClan(clanarina.getImeClana().get(),clanarina.getPrezimeClana().get(),clanarina.getClanId());
			controller.setTxtIznos(new String(clanarina.getIznos().getValue().toString()));//
			controller.setTxtOpis(clanarina.getOpis().get());//
			controller.setSpinnerGodina(clanarina.getGodina().intValue());
			controller.setSpinnerMjesec(clanarina.getMjesec().intValue());
			controller.setDatePicker(clanarina.getDatum());//
			controller.setClanarina(clanarina);
			controller.setEvidentiranjeController(this);//
			
			noviStage.showAndWait();//
			tableClanarine.refresh();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new ErrorLogger().log(e);
		}
		
		
	}

	public ObservableList<ClanarinaDTO> getListaClanarina() {
		return listaClanarina;
	}

	public void setListaClanarina(ObservableList<ClanarinaDTO> listaClanarina) {
		this.listaClanarina = listaClanarina;
	}
	public void obrisi() {
		ClanarinaDTO temp = tableClanarine.getSelectionModel().getSelectedItem();
		DAOFactory.getDAOFactory().getTransakcijaDAO().delete(temp.getId());
		DAOFactory.getDAOFactory().getNovcanaSredstvaDAO().dodajPrihode(-temp.getIznos().get());
		listaClanarina.remove(tableClanarine.getSelectionModel().getSelectedItem());
		if(!radiobtnSve.isSelected()) {
			lista.remove(tableClanarine.getSelectionModel().getSelectedItem());
		}
		tableClanarine.refresh();
	}
}