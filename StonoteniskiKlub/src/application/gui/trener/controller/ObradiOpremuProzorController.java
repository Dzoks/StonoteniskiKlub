package application.gui.trener.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.ListView;

import javafx.scene.control.Spinner;

public class ObradiOpremuProzorController extends BaseController implements Initializable{
	@FXML
	private Spinner<Integer> spinnerPristiglo;
	@FXML
	private ListView listClanovi;
	@FXML
	private Button btnEvidentiraj;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
