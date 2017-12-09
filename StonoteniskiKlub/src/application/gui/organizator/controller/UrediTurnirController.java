package application.gui.organizator.controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;

public class UrediTurnirController extends BaseController{
	@FXML
	private Label lblNaziv;
	@FXML
	private Label lblDatum;
	@FXML
	private ComboBox cbKategorija;
	@FXML
	private Button btnNazad;
	@FXML
	private Button btnUredi;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void inicijalizuj(Integer id){
		primaryStage.setTitle("Pregled turnira");
	}
	
	public void urediTurnir(){
		try {
//			if(checkBox==1)
//			SinglTurnirController noviStage=(SinglTurnirController)changeScene("/application/gui/organizator/view/SinglTurnirView.fxml",primaryStage);
			DublTurnirController noviStage=(DublTurnirController)changeScene("/application/gui/organizator/view/DublTurnirView.fxml",primaryStage);
			noviStage.inicijalizuj(1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void vratiNazad(){
		try {
			changeScene("/application/gui/organizator/view/TurniriView.fxml", primaryStage);
			primaryStage.setTitle("Turniri");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
