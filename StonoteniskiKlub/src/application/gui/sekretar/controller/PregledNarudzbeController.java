package application.gui.sekretar.controller;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dto.Narudzba;
import application.model.dto.NarudzbaStavka;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PregledNarudzbeController extends BaseController implements Initializable{
	@FXML
	private Label lblDistributer;
	@FXML
	private Label lblDatum;
	@FXML
	private Label lblId;
	@FXML
	private TableView<NarudzbaStavka> tblNarudzbe;
	@FXML
	private TableColumn<NarudzbaStavka, Integer> id;
	@FXML
	private TableColumn<NarudzbaStavka, String> tipOpreme;
	@FXML
	private TableColumn<NarudzbaStavka, String> proizvodjacOpreme;
	@FXML
	private TableColumn<NarudzbaStavka, String> modelOpreme;
	@FXML
	private TableColumn<NarudzbaStavka, Integer> kolicina;
	@FXML
	private TableColumn<NarudzbaStavka, String> velicina;
	@FXML
	private TableColumn<NarudzbaStavka, Double> cijena;
	@FXML
	private Button btnOK;
	
	private Narudzba narudzba = null;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void popuniTabelu() {
		id.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, Integer>("idNarudzbe"));
		tipOpreme.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, String>("tipOpreme"));
		proizvodjacOpreme.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, String>("tipProizvodjac"));
		modelOpreme.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, String>("tipModel"));
		kolicina.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, Integer>("kolicina"));
		velicina.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, String>("velicina"));
		cijena.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, Double>("cijena"));
		
		tblNarudzbe.setItems(narudzba.getListaStavki());
	}
	
	public void popuniPodatke() {
		lblDistributer.setText(narudzba.getNazivDistributeraOpreme());
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy.");
		String datumString = df.format(narudzba.getDatum());
		lblDatum.setText(datumString);
		lblId.setText(narudzba.getId().toString());
		
		popuniTabelu();
	}
	
	public void ugasi() {
		primaryStage.close();
	}

	public void setNarudzba(Narudzba narudzba) {
		this.narudzba = narudzba;
	}
}
