package application.gui.organizator.controller;
import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.DatePicker;

public class SinglPrijavaController extends BaseController{
	@FXML
	private Label lblKategorija;
	@FXML
	private Button btnSacuvaj;
	@FXML
	private TextField txtIme;
	@FXML
	private TextField txtPrezime;
	@FXML
	private TextField txtJmbg;
	@FXML
	private DatePicker dpDatumRodjenja;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void inicijalizuj(Integer id){
		primaryStage.setTitle("Singl prijava");
	}

	public void sacuvaj(){
		primaryStage.close();
	}
}
