package application.gui.organizator.controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import application.gui.controller.BaseController;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class TurniriController extends BaseController{
	@FXML
	private TableView tblTurniri;
	@FXML
	private TableColumn clnRedniBroj;
	@FXML
	private TableColumn clnNaziv;
	@FXML
	private TableColumn clnDatum;
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
//		primaryStage.setTitle("Turniri");
		btnDodaj.disableProperty().bind(txtNaziv.textProperty().isEmpty().or(dpDatum.valueProperty().isNull()));
	}
	
	public void odjaviSe() {
		try {
			changeScene("/application/gui/administrator/view/LoginView.fxml", primaryStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void zatvoriTurnir(){
		
	}
	
	public void urediTurnir(){
		
		try {
			UrediTurnirController noviStage=(UrediTurnirController)changeScene("/application/gui/organizator/view/UrediTurnirView.fxml", primaryStage);
			noviStage.inicijalizuj(1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void pregledajTurnir(){
		
		try {
			PregledTurniraController noviStage=(PregledTurniraController)changeScene("/application/gui/organizator/view/PregledTurniraView.fxml", primaryStage);
			noviStage.inicijalizuj(1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void dodajTurnir(){
		
	}
}
