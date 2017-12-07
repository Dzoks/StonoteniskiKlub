package application.gui.trener.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.gui.sekretar.controller.DodajNarudzbuProzorController;
import application.model.dao.NarudzbaDAO;
import application.model.dao.OpremaClanaDAO;
import application.model.dao.OpremaKlubaDAO;
import application.model.dto.NarudzbaDTO;
import application.model.dto.OpremaClanaDTO;
import application.model.dto.OpremaKlubaDTO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OpremaGlavniProzorController extends BaseController implements Initializable{
	@FXML
	private Button btnPretraziOpremaKluba;
	@FXML
	private Button btnDodajOpremaKluba;
	@FXML
	private Button btnPretraziOpremaClanova;
	@FXML
	private Button btnDodajOpremaClanova;
	@FXML
	private TableView<OpremaKlubaDTO> tblOpremaKluba;
	@FXML
	private TableColumn<OpremaKlubaDTO, Integer> idKlub;
	@FXML
	private TableColumn<OpremaKlubaDTO, String> tipKlub;
	@FXML
	private TableColumn<OpremaKlubaDTO, String> proizvodjacKlub;
	@FXML
	private TableColumn<OpremaKlubaDTO, String> modelKlub;
	@FXML
	private TableColumn<OpremaKlubaDTO, Boolean> doniranaKlub;
	@FXML
	private TableView<OpremaClanaDTO> tblOpremaClana;
	@FXML
	private TableColumn<OpremaClanaDTO, Integer> idClan;
	@FXML
	private TableColumn<OpremaClanaDTO, String> tipClan;
	@FXML
	private TableColumn<OpremaClanaDTO, String> proizvodjacClan;
	@FXML
	private TableColumn<OpremaClanaDTO, String> modelClan;
	@FXML
	private TableColumn<OpremaClanaDTO, String> jmbClan;
	@FXML
	private TableColumn<OpremaClanaDTO, String> imeClan;
	@FXML
	private TableColumn<OpremaClanaDTO, String> prezimeClan;
	@FXML
	private TableColumn<OpremaClanaDTO, Boolean> doniranaClan;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		popuniTabele();
	}
	
	public void popuniTabele() {
		idKlub.setCellValueFactory(new PropertyValueFactory<OpremaKlubaDTO, Integer>("id"));
		tipKlub.setCellValueFactory(new PropertyValueFactory<OpremaKlubaDTO, String>("tipOpreme"));
		proizvodjacKlub.setCellValueFactory(new PropertyValueFactory<OpremaKlubaDTO, String>("tipProizvodjac"));
		modelKlub.setCellValueFactory(new PropertyValueFactory<OpremaKlubaDTO, String>("tipModel"));
		doniranaKlub.setCellValueFactory(new PropertyValueFactory<OpremaKlubaDTO, Boolean>("donirana"));
		
		ObservableList<OpremaKlubaDTO> listaOpremeKluba = OpremaKlubaDAO.SELECT_ALL();
		
		tblOpremaKluba.setItems(listaOpremeKluba);
		
		idClan.setCellValueFactory(new PropertyValueFactory<OpremaClanaDTO, Integer>("id"));
		tipClan.setCellValueFactory(new PropertyValueFactory<OpremaClanaDTO, String>("tipOpreme"));
		proizvodjacClan.setCellValueFactory(new PropertyValueFactory<OpremaClanaDTO, String>("tipProizvodjac"));
		modelClan.setCellValueFactory(new PropertyValueFactory<OpremaClanaDTO, String>("tipModel"));
		jmbClan.setCellValueFactory(new PropertyValueFactory<OpremaClanaDTO, String>("jmbClana"));
		imeClan.setCellValueFactory(new PropertyValueFactory<OpremaClanaDTO, String>("imeClana"));
		prezimeClan.setCellValueFactory(new PropertyValueFactory<OpremaClanaDTO, String>("prezimeClana"));
		doniranaClan.setCellValueFactory(new PropertyValueFactory<OpremaClanaDTO, Boolean>("donirana"));
		
		ObservableList<OpremaClanaDTO> listaOpremeClana = OpremaClanaDAO.SELECT_ALL();
		
		tblOpremaClana.setItems(listaOpremeClana);
	}
	
	public void idiNaDodajOpremuKluba() {
		Stage noviStage = new Stage();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/trener/view/DodajOpremuProzor.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,761,576);
			DodajOpremuProzorController controller = loader.<DodajOpremuProzorController>getController();
			controller.setPrimaryStage(noviStage);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa opremom");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			
			controller.setOpremaKluba();
			controller.ucitajComboBoxeve();
			controller.disableDodajDugme();
			
			noviStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void idiNaDodajOpremuClana() {
		Stage noviStage = new Stage();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/trener/view/DodajOpremuProzor.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,761,576);
			DodajOpremuProzorController controller = loader.<DodajOpremuProzorController>getController();
			controller.setPrimaryStage(noviStage);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa opremom");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			
			controller.ucitajComboBoxeve();
			controller.disableDodajDugme();
			
			noviStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
