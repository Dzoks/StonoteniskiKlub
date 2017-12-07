package application.gui.organizator.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
	private TextField txtDatum;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
