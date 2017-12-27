package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PocetniController extends BaseController implements Initializable{

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void idiNaPregledOpreme() {
		Stage noviStage = new Stage();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/trener/view/OpremaGlavniView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,761,526);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa opremom");
			noviStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void idiNaPregledNarudzbi() {
		Stage noviStage = new Stage();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/sekretar/view/NarudzbaGlavniView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,761,484);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa opremom");
			noviStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
