package application.gui.organizator.controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import application.gui.controller.BaseController;
import application.model.dao.KategorijaTurniraDAO;
import application.model.dao.TurnirDAO;
import application.model.dao.UcesnikPrijavaDAO;
import application.model.dto.TurnirDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;

public class SinglTurnirController extends BaseController{
	@FXML
	private Label lblNaziv;
	@FXML
	private Label lblDatum;
	@FXML
	private Label lblKategorija;
	@FXML
	private TableView tblIgraci;
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
	
	private Integer id;
	private Integer idKategorije;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void inicijalizuj(Integer id,Integer idKategorije){
		this.id=id;
		this.idKategorije=idKategorije;
		TurnirDTO turnir=TurnirDAO.getById(id);
		lblNaziv.setText(turnir.getNaziv());
		lblDatum.setText(TurniriController.konvertujIzSQLDate(turnir.getDatum()));
		primaryStage.setTitle("Singl turnir");
		lblKategorija.setText(KategorijaTurniraDAO.getById(idKategorije).toString());
		btnIzmjeni.disableProperty().bind(tblIgraci.getSelectionModel().selectedItemProperty().isNull());
		popuniTabelu();
	}
	
	public void popuniTabelu(){
		clnIme.setCellValueFactory(new PropertyValueFactory<>("ime"));
		clnPrezime.setCellValueFactory(new PropertyValueFactory<>("prezime"));
		clnJMBG.setCellValueFactory(new PropertyValueFactory<>("jmb"));
		tblIgraci.setItems(UcesnikPrijavaDAO.getAll(id,idKategorije));
	}
	
	public void prijaviIgraca(){
		Stage noviStage=new Stage();
		try {
			//if(checkBox==Single)
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/organizator/view/SinglPrijavaView.fxml"));
//			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/organizator/view/DublZrijebView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Singl prijava");
			noviStage.show();
			BaseController controller=loader.<BaseController>getController();
			controller.setPrimaryStage(noviStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		popuniTabelu();
	}
	
	public void izmjeniIgraca(){
		Stage noviStage=new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/organizator/view/SinglPrijavaView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Singl prijava");
			noviStage.show();
			BaseController controller=loader.<BaseController>getController();
			controller.setPrimaryStage(noviStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		popuniTabelu();
	}
	
	public void prikaziZrijeb(){
		Stage noviStage=new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/organizator/view/SinglZrijebView.fxml"));
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
	
	public void vratiNazad(){
		try {
			changeScene("/application/gui/organizator/view/TurniriView.fxml", primaryStage);
			primaryStage.setTitle("Turniri");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
