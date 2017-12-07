package application.gui.organizator.controller;
import java.net.URL;
import java.util.ResourceBundle;
import application.gui.controller.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.DatePicker;

public class DublPrijavaController extends BaseController{
	@FXML
	private Label lblKategorija;
	@FXML
	private Button btnSacuvaj;
	@FXML
	private TextField txtIme1;
	@FXML
	private TextField txtPrezime1;
	@FXML
	private TextField txtJmbg1;
	@FXML
	private DatePicker dpDatumRodjenja1;
	@FXML
	private TextField txtIme2;
	@FXML
	private TextField txtPrezime2;
	@FXML
	private TextField txtJmbg2;
	@FXML
	private DatePicker dpDatumRodjenja2;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
