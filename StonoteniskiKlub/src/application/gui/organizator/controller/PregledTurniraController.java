package application.gui.organizator.controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import application.gui.controller.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;

public class PregledTurniraController extends BaseController{
	@FXML
	private Label lblNaziv;
	@FXML
	private Label lblDatum;
	@FXML
	private ComboBox cbKategorija;
	@FXML
	private Button btnNazad;
	@FXML
	private Button btnZrijeb;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void inicijalizuj(Integer id){
		primaryStage.setTitle("Pregled turnira");
	}
	
	public void vratiNazad(){
		try {
			changeScene("/application/gui/organizator/view/TurniriView.fxml", primaryStage);
			primaryStage.setTitle("Turniri");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prikaziZrijeb(){
		Stage noviStage=new Stage();
		try {
			//if(checkBox==Single)
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/organizator/view/SinglZrijebView.fxml"));
//			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/organizator/view/DublZrijebView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Žrijeb");
			noviStage.show();
			BaseController controller=loader.<BaseController>getController();
			controller.setPrimaryStage(noviStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
