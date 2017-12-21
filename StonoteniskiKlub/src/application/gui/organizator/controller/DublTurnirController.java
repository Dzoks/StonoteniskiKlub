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
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;

public class DublTurnirController extends BaseController{
	@FXML
	private Label lblKategorija;
	@FXML
	private TableView tblEkipe;
	@FXML
	private TableColumn clnRedniBroj;
	@FXML
	private TableColumn clnPrezime1;
	@FXML
	private TableColumn clnJMBG1;
	@FXML
	private TableColumn clnPrezime2;
	@FXML
	private TableColumn clnJMBG2;
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
	
	public void inicijalizuj(Integer id){
		primaryStage.setTitle("Dubl turnir");
		lblKategorija.setText("�enski dubl");
	}
	
	public void prijaviEkipu(){
		Stage noviStage=new Stage();
		try {
			//if(checkBox==Single)
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/organizator/view/DublPrijavaView.fxml"));
//			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/organizator/view/DublZrijebView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Dubl prijava");
			noviStage.show();
			BaseController controller=loader.<BaseController>getController();
			controller.setPrimaryStage(noviStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void izmjeniEkipu(){
		Stage noviStage=new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/organizator/view/DublPrijavaView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Dubl prijava");
			noviStage.show();
			BaseController controller=loader.<BaseController>getController();
			controller.setPrimaryStage(noviStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prikaziZrijeb(){
		Stage noviStage=new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/organizator/view/DublZrijebView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("�rijeb");
			noviStage.show();
			BaseController controller=loader.<BaseController>getController();
			controller.setPrimaryStage(noviStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void vratiNazad(){
		try {
			changeScene("/application/gui/organizator/view/UrediTurnirView.fxml", primaryStage);
			primaryStage.setTitle("Pregled turnira");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
