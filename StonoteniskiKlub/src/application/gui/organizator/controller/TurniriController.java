package application.gui.organizator.controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import application.model.dao.TurnirDAO;
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
	private TableColumn<TurnirDTO,Date> clnDatum;
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
		btnDodaj.disableProperty().bind(txtNaziv.textProperty().isEmpty().or(dpDatum.valueProperty().isNull()));
		clnNaziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
		clnDatum.setCellValueFactory(new PropertyValueFactory<>("datum"));
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
		TurnirDAO.insert(txtNaziv.getText(), Date.valueOf(dpDatum.getValue()));
	}
}
