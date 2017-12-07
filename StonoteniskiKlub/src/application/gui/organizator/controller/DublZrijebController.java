package application.gui.organizator.controller;
import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class DublZrijebController extends BaseController{
	@FXML
	private TableView tblRunda1;
	@FXML
	private TableColumn clnMec1;
	@FXML
	private TableColumn clnRezultat1;
	@FXML
	private TableView tblRunda4;
	@FXML
	private TableColumn clnMec4;
	@FXML
	private TableColumn clnRezultat4;
	@FXML
	private TableView tblRunda3;
	@FXML
	private TableColumn clnMec3;
	@FXML
	private TableColumn clnRezultat3;
	@FXML
	private TableView tblRunda2;
	@FXML
	private TableColumn clnMec2;
	@FXML
	private TableColumn clnRezultat2;
	@FXML
	private TextField txtPobjednici;
	@FXML
	private Button btnOk;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void inicijalizuj(Integer id){
		primaryStage.setTitle("Dubl žrijeb");
	}	
	
	public void ok(){
		primaryStage.close();
	}
}
