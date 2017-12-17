package application.gui.organizator.controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import application.gui.controller.BaseController;
import application.model.dao.KategorijaTurniraDAO;
import application.model.dao.TurnirDAO;
import application.model.dto.KategorijaTurniraDTO;
import application.model.dto.TurnirDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class TurniriController extends BaseController{
	@FXML
	private TableView<TurnirDTO> tblTurniri;
	@FXML
	private TableColumn<TurnirDTO,String> clnZatvoren;
	@FXML
	private TableColumn<TurnirDTO,String> clnNaziv;
	@FXML
	private TableColumn<TurnirDTO,String> clnDatum;
	@FXML
	private Button btnUredi;
	@FXML
	private Button btnPregledaj;
	@FXML
	private Button btnZatvori;
	@FXML
	private Button btnOdjaviSe;
	@FXML
	private Button btnDodaj;
	@FXML
	private TextField txtNaziv;
	@FXML
	private DatePicker dpDatum;
	@FXML
	private ChoiceBox<KategorijaTurniraDTO> cbKategorija;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnDodaj.disableProperty().bind(txtNaziv.textProperty().isEmpty().or(dpDatum.valueProperty().isNull()));
		cbKategorija.disableProperty().bind(tblTurniri.getSelectionModel().selectedItemProperty().isNull());
		cbKategorija.setItems(KategorijaTurniraDAO.getAll());
		btnUredi.setDisable(true);
		btnPregledaj.setDisable(true);
		btnZatvori.setDisable(true);
		popuniTabelu();
	}
	
	public void selektovan(){
		if(tblTurniri.getSelectionModel().getSelectedItem().getZatvoren().equals("Ne")){
			btnZatvori.setDisable(false);
			btnUredi.setDisable(false);
			btnPregledaj.setDisable(true);
		}
		else{
			btnZatvori.setDisable(true);
			btnUredi.setDisable(true);
			btnPregledaj.setDisable(false);
		}
	}
	
	public void popuniTabelu(){
		clnNaziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
		clnDatum.setCellValueFactory(new PropertyValueFactory<>("konvertovanDatum"));
		clnZatvoren.setCellValueFactory(new PropertyValueFactory<>("zatvoren"));
		tblTurniri.setItems(TurnirDAO.getAll());
	}
	
	public void odjaviSe() {
		try {
			changeScene("/application/gui/administrator/view/LoginView.fxml", primaryStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void zatvoriTurnir(){
		TurnirDAO.zatvori(tblTurniri.getSelectionModel().getSelectedItem().getId());
		popuniTabelu();
		btnUredi.setDisable(true);
		btnPregledaj.setDisable(true);
		btnZatvori.setDisable(true);
	}
	
	public void urediTurnir(){
		if(cbKategorija.getSelectionModel().isEmpty()){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Greška");
			alert.setHeaderText("Potrebno je izabrati kategoirju!");
			alert.setContentText("Nije moguće pristupiti turniru, dok niste prethodno izabrali kategoriju turnira sa kojom"
					+ " želite da radite.");
			alert.show();
		}
		else{
			try {
				if(cbKategorija.getSelectionModel().getSelectedItem().getId()<=2){
					SinglTurnirController noviStage=(SinglTurnirController)changeScene("/application/gui/organizator/view/SinglTurnirView.fxml",primaryStage);
					noviStage.inicijalizuj(tblTurniri.getSelectionModel().getSelectedItem().getId(),
							cbKategorija.getSelectionModel().getSelectedItem().getId());
				}
				else{
					DublTurnirController noviStage=(DublTurnirController)changeScene("/application/gui/organizator/view/DublTurnirView.fxml",primaryStage);
					noviStage.inicijalizuj(tblTurniri.getSelectionModel().getSelectedItem().getId(),
							cbKategorija.getSelectionModel().getSelectedItem().getId());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void pregledajTurnir(){
		if(cbKategorija.getSelectionModel().isEmpty()){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Greška");
			alert.setHeaderText("Potrebno je izabrati kategoirju!");
			alert.setContentText("Nije moguće pristupiti turniru, dok niste prethodno izabrali kategoriju turnira sa kojom"
					+ " želite da radite.");
			alert.show();
		}
		else{
			Stage noviStage=new Stage();
			try {
				FXMLLoader loader;
				if(cbKategorija.getSelectionModel().getSelectedItem().getId()<=2){
					loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/organizator/view/SinglZrijebView.fxml"));
					AnchorPane root = (AnchorPane) loader.load();
					Scene scene = new Scene(root);
					noviStage.setScene(scene);
					noviStage.setResizable(false);
					noviStage.setTitle("Žrijeb");
					noviStage.show();
					SinglZrijebController controller=loader.<SinglZrijebController>getController();
					controller.setPrimaryStage(noviStage);
					controller.inicijalizuj(tblTurniri.getSelectionModel().getSelectedItem().getId(),
							cbKategorija.getSelectionModel().getSelectedItem().getId());
				}
				else{
					loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/organizator/view/DublZrijebView.fxml"));
					AnchorPane root = (AnchorPane) loader.load();
					Scene scene = new Scene(root);
					noviStage.setScene(scene);
					noviStage.setResizable(false);
					noviStage.setTitle("Žrijeb");
					noviStage.show();
					DublZrijebController controller=loader.<DublZrijebController>getController();
					controller.setPrimaryStage(noviStage);
					controller.inicijalizuj(tblTurniri.getSelectionModel().getSelectedItem().getId(),
							cbKategorija.getSelectionModel().getSelectedItem().getId());
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void dodajTurnir(){
		TurnirDAO.insert(txtNaziv.getText(), Date.valueOf(dpDatum.getValue()));
		popuniTabelu();
		txtNaziv.clear();
		dpDatum.setValue(null);
	}
	
	public static String konvertujIzSQLDate(Date sqlDatum){
		String datum;
		String[]niz=sqlDatum.toString().split("-");
		datum=niz[2]+"."+niz[1]+"."+niz[0]+".";
		return datum;
	}
}
