package application.gui.organizator.controller;
import java.net.URL;
import java.util.ResourceBundle;
import application.gui.controller.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class SinglTurnirController extends BaseController{
	@FXML
	private Label lblKategorija;
	@FXML
	private TableView tblIgraci;
	@FXML
	private TableColumn clnRedniBroj;
	@FXML
	private TableColumn clnIme;
	@FXML
	private TableColumn clnPrezime;
	@FXML
	private TableColumn clnJMBG;
	@FXML
	private Button btnPrijavi;
	@FXML
	private Button btnIzmjeni;
	@FXML
	private Button btnZrijeb;
	@FXML
	private Button btnNazad;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
