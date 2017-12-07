package application.gui.organizator.controller;
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

}
