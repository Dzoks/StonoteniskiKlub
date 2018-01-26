package application.gui.racunovodja.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.DistributerOpreme;
import application.model.dto.Narudzba;
import application.model.dto.TransakcijaDTO;
import application.model.dto.TroskoviOpremaDTO;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EvidentiranjeSredstavaZaOpremuController extends TransakcijaDecorater{
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
	private Label lblKM;
	@FXML
	private Label lblDatum;
	
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
	private ScrollPane scrollPane;
	
	
	private ObservableList<TroskoviOpremaDTO> listaTroskovi;
	private ObservableList<TroskoviOpremaDTO> lista = FXCollections.observableArrayList();

	public void obrisi() {
		TroskoviOpremaDTO temp = tableTroskoviOprema.getSelectionModel().getSelectedItem();
		DAOFactory.getDAOFactory().getTransakcijaDAO().delete(temp.getId());
		DAOFactory.getDAOFactory().getNovcanaSredstvaDAO().dodajRashode(-temp.getIznos().get());
		listaTroskovi.remove(tableTroskoviOprema.getSelectionModel().getSelectedItem());
		if(!radiobtnSve.isSelected()) {
			lista.remove(tableTroskoviOprema.getSelectionModel().getSelectedItem());
		}
		tableTroskoviOprema.refresh();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		super.setController(new TransakcijaController(txtIznos, datePicker, txtOpis, radiobtnSve,btnPrikazi));

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
		BooleanBinding bindingObrisi = tableTroskoviOprema.getSelectionModel().selectedItemProperty().isNull();
		btnObrisi.disableProperty().bind(bindingObrisi);
		btnIzmijeni.disableProperty().bind(bindingObrisi);
	}
	
	public void radioSve() {
		comboBoxDistributer.setDisable(true);
	}
	public void radioDistributer() {
		comboBoxDistributer.setDisable(false);
	}

	private void popuniTabelu() {
		this.postaviKolone();
		listaTroskovi = DAOFactory.getDAOFactory().getTroskoviOpremaDAO().SELECT_ALL();
		tableTroskoviOprema.setItems(listaTroskovi);
	}
	private void popuniComboBox(){
		ObservableList<Narudzba> listaNarudzba = DAOFactory.getDAOFactory().getNarudzbaDAO().SELECT_OPREMA_KLUBA();
		comboBoxNarudzba.setItems(listaNarudzba);
		if(!listaNarudzba.isEmpty())
			comboBoxNarudzba.getSelectionModel().select(0);
		ObservableList<DistributerOpreme> listaDistributer = DAOFactory.getDAOFactory().getDistributerOpremeDAO().SELECT_ALL();
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
	public TransakcijaDTO dodaj() {
		TransakcijaDTO transakcija = super.dodaj();
		if(transakcija==null)
			return null;
		Narudzba narudzba = comboBoxNarudzba.getValue();
		
		String tipTransakcije = DAOFactory.getDAOFactory().getTipTransakcijeDAO().getById(3).getTip();
		TroskoviOpremaDTO troskovi = new TroskoviOpremaDTO(null, transakcija.getDatum(), transakcija.getIznos().get(), transakcija.getOpis().get(), tipTransakcije, narudzba);
		boolean ok = DAOFactory.getDAOFactory().getTroskoviOpremaDAO().INSERT(troskovi, narudzba);
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
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/racunovodja/view/IzmijeniSredstvaZaOpremu.fxml"));
		AnchorPane root;
		IzmijeniSredstvaZaOpremuController controller=null;
		
		try {
			root = (AnchorPane) loader.load();//initialize
			Scene scene = new Scene(root,329,327);
			controller = loader.<IzmijeniSredstvaZaOpremuController>getController();
			controller.setPrimaryStage(noviStage);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			TroskoviOpremaDTO trosak = tableTroskoviOprema.getSelectionModel().getSelectedItem();
			controller.setListaTroskovi(listaTroskovi);
			controller.setComboBoxNarudzba(DAOFactory.getDAOFactory().getNarudzbaDAO().SELECT_OPREMA_KLUBA(),trosak.getNarudzba());//promijenila, ne znam valja li
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
			new ErrorLogger().log(e);
		}
			
	}
	
	public ObservableList<TroskoviOpremaDTO> getListaTroskovi() {
		return listaTroskovi;
	}
	public void setListaTroskovi(ObservableList<TroskoviOpremaDTO> listaTroskovi) {
		this.listaTroskovi = listaTroskovi;
	}
}
