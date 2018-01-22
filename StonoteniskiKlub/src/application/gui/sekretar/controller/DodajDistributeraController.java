package application.gui.sekretar.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dto.DistributerOpreme;
import application.util.AlertDisplay;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class DodajDistributeraController extends BaseController implements Initializable{
	@FXML
	private TextField txtNaziv;
	@FXML
	private TextField txtTelefon;
	@FXML
	private TextField txtAdresa;
	@FXML
	private TextField txtMail;
	@FXML
	private Button btnDodaj;
	
	private String povratnaVrijednost = "NO";
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnDodaj.disableProperty().bind(txtNaziv.textProperty().isEmpty());
		
	}
	
	public void provjeriParametre() {
		if(txtTelefon.getText().matches("[0-9][0-9][0-9]/[0-9][0-9][0-9]-[0-9][0-9][0-9]")) {
			povratnaVrijednost = "YES";
			primaryStage.close();
		}
		else {
			

			AlertDisplay.showError("Dodavanje","Broj telefona nije u dobrom formatu. Format je XXX/XXX-XXX." );
		}
	}
	
	public DistributerOpreme vratiDistributeraOpreme() {
		return new DistributerOpreme(null, txtNaziv.getText(), txtTelefon.getText(), txtAdresa.getText(), txtMail.getText());
	}
	
	public String getPovratnaVrijednost() {
		return povratnaVrijednost;
	}
}
