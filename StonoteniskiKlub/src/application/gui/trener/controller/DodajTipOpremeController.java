package application.gui.trener.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dto.OpremaTip;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class DodajTipOpremeController extends BaseController implements Initializable{
	@FXML
	private TextField txtTip;
	@FXML
	private TextField txtProizvodjac;
	@FXML
	private TextField txtModel;
	@FXML
	private Button btnDodaj;
	@FXML
	private CheckBox checkBoxImaVelicinu;
	
	private String povratnaVrijednost = "NO";
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnDodaj.disableProperty().bind(txtTip.textProperty().isEmpty().or(txtProizvodjac.textProperty().isEmpty()).or(txtModel.textProperty().isEmpty()));
	}
	
	public void dodajTipOpreme() {
		povratnaVrijednost = "YES";
		primaryStage.close();
	}
	
	public OpremaTip vratiTipOpreme() {
		return new OpremaTip(null, txtTip.getText(), txtProizvodjac.getText(), txtModel.getText(), checkBoxImaVelicinu.isSelected());
	}
	
	public String getPovratnaVrijednost() {
		return povratnaVrijednost;
	}
}
