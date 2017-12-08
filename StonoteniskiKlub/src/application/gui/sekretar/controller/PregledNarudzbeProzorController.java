package application.gui.sekretar.controller;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dto.NarudzbaDTO;
import application.model.dto.NarudzbaStavkaDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.Label;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;

public class PregledNarudzbeProzorController extends BaseController implements Initializable{
	@FXML
	private Label lblDistributer;
	@FXML
	private Label lblDatum;
	@FXML
	private Label lblId;
	@FXML
	private TableView<NarudzbaStavkaDTO> tblNarudzbe;
	@FXML
	private TableColumn<NarudzbaStavkaDTO, Integer> id;
	@FXML
	private TableColumn<NarudzbaStavkaDTO, String> tipOpreme;
	@FXML
	private TableColumn<NarudzbaStavkaDTO, String> proizvodjacOpreme;
	@FXML
	private TableColumn<NarudzbaStavkaDTO, String> modelOpreme;
	@FXML
	private TableColumn<NarudzbaStavkaDTO, Integer> kolicina;
	@FXML
	private TableColumn<NarudzbaStavkaDTO, String> velicina;
	@FXML
	private TableColumn<NarudzbaStavkaDTO, Double> cijena;
	@FXML
	private Button btnOK;
	
	private NarudzbaDTO narudzba = null;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void popuniTabelu() {
		id.setCellValueFactory(new PropertyValueFactory<NarudzbaStavkaDTO, Integer>("idNarudzbe"));
		tipOpreme.setCellValueFactory(new PropertyValueFactory<NarudzbaStavkaDTO, String>("tipOpreme"));
		proizvodjacOpreme.setCellValueFactory(new PropertyValueFactory<NarudzbaStavkaDTO, String>("tipProizvodjac"));
		modelOpreme.setCellValueFactory(new PropertyValueFactory<NarudzbaStavkaDTO, String>("tipModel"));
		kolicina.setCellValueFactory(new PropertyValueFactory<NarudzbaStavkaDTO, Integer>("kolicina"));
		velicina.setCellValueFactory(new PropertyValueFactory<NarudzbaStavkaDTO, String>("velicina"));
		cijena.setCellValueFactory(new PropertyValueFactory<NarudzbaStavkaDTO, Double>("cijena"));
		
		tblNarudzbe.setItems(narudzba.getListaStavki());
	}
	
	public void popuniPodatke() {
		lblDistributer.setText(narudzba.getNazivDistributeraOpreme());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String datumString = df.format(narudzba.getDatum());
		lblDatum.setText(datumString);
		lblId.setText(narudzba.getId().toString());
		
		popuniTabelu();
	}
	
	public void ugasi() {
		primaryStage.close();
	}

	public void setNarudzba(NarudzbaDTO narudzba) {
		this.narudzba = narudzba;
	}
}
