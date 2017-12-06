package application.gui.trener.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class OpremaGlavniProzorController extends BaseController implements Initializable{
	@FXML
	private Button btnPretraziOpremaKluba;
	@FXML
	private Button btnDodajOpremaKluba;
	@FXML
	private Button btnPretraziOpremaClanova;
	@FXML
	private Button btnDodajOpremaClanova;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void idiNaDodajOpremu() {
		Stage noviStage = new Stage();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/trener/view/DodajOpremuProzor.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,761,576);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa opremom");
			noviStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
