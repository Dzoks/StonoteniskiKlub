package application.gui.organizator.controller;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.KategorijaTurniraDTO;
import application.model.dto.TurnirDTO;
import application.util.AlertDisplay;
import application.util.ErrorLogger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TurniriController extends BaseController{
	@FXML
	private TableView<TurnirDTO> tblTurniri;
	@FXML
	private TableColumn<TurnirDTO,String> clnZatvoren;
	@FXML
	private TableColumn<TurnirDTO,String> clnNaziv;
	@FXML
	private TableColumn<TurnirDTO,String> clnDatum;
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
	private DatePicker dpDatum;
	@FXML
	private ChoiceBox<KategorijaTurniraDTO> cbKategorija;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnDodaj.disableProperty().bind(txtNaziv.textProperty().isEmpty().or(dpDatum.valueProperty().isNull()));
		cbKategorija.disableProperty().bind(tblTurniri.getSelectionModel().selectedItemProperty().isNull());
		cbKategorija.setItems(DAOFactory.getDAOFactory().getKategorijaTurniraDAO().getAll());
		btnUredi.setDisable(true);
		btnPregledaj.setDisable(true);
		btnZatvori.setDisable(true);
		popuniTabelu();
	}
	
	public void selektovan(){
		if(!tblTurniri.getSelectionModel().isEmpty() && "Ne".equals(tblTurniri.getSelectionModel().getSelectedItem().getZatvoren())){
			btnZatvori.setDisable(false);
			btnUredi.setDisable(false);
			btnPregledaj.setDisable(true);
		}
		else if(tblTurniri.getSelectionModel().isEmpty()){
			btnZatvori.setDisable(true);
			btnUredi.setDisable(true);
			btnPregledaj.setDisable(true);
		}
		else{
			btnZatvori.setDisable(true);
			btnUredi.setDisable(true);
			btnPregledaj.setDisable(false);
		}
	}
	
	public void popuniTabelu(){
		clnNaziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
		clnDatum.setCellValueFactory(new PropertyValueFactory<>("konvertovanDatum"));
		clnZatvoren.setCellValueFactory(new PropertyValueFactory<>("zatvoren"));
		tblTurniri.setItems(DAOFactory.getDAOFactory().getTurnirDAO().getAll());
	}
	
	public void odjaviSe() {
		try {
			changeScene("/application/gui/administrator/view/LoginView.fxml", primaryStage);
		} catch (IOException e) {
			 ;
			new ErrorLogger().log(e);
		}
	}
	
	public void zatvoriTurnir(){
		Optional<ButtonType> result =AlertDisplay.showConfirmation("Zatvaranje", "Ukoliko zatvorite izabrani turnir,"
				+ " nećete biti u mogućnosti da ponovo radite na tom turniru!"+" Da li ste sigurni da želite zatvoriti izabrani turnir?");
		if(result.get().getButtonData().equals(ButtonData.YES)){
			DAOFactory.getDAOFactory().getTurnirDAO().zatvori(tblTurniri.getSelectionModel().getSelectedItem().getId());
			popuniTabelu();
			btnUredi.setDisable(true);
			btnPregledaj.setDisable(true);
			btnZatvori.setDisable(true);
		}
	}
	
	public void urediTurnir(){
		if(cbKategorija.getSelectionModel().isEmpty()){
			AlertDisplay.showError("Izmjena", "Nije moguće pristupiti turniru, dok niste prethodno izabrali kategoriju turnira sa kojom"
					+ " želite da radite.");
		}
		else{
			try {
				SinglTurnirController noviStage=(SinglTurnirController)changeScene("/application/gui/organizator/view/SinglTurnirView.fxml",primaryStage);
				noviStage.inicijalizuj(tblTurniri.getSelectionModel().getSelectedItem().getId(),
						cbKategorija.getSelectionModel().getSelectedItem().getId());
				noviStage.setUsername(lblUsername.getText());
			} catch (IOException e) {
				 ;
				new ErrorLogger().log(e);
			}
		}
	}
	
	public void pregledajTurnir(){
		if(cbKategorija.getSelectionModel().isEmpty()){
			AlertDisplay.showError("Pregled", "Nije moguće pristupiti turniru, dok niste prethodno izabrali kategoriju turnira sa kojom"
					+ " želite da radite.");
		}
		else{
			if(DAOFactory.getDAOFactory().getZrijebDAO().doesExist(tblTurniri.getSelectionModel().getSelectedItem().getId(),
					cbKategorija.getSelectionModel().getSelectedItem().getId())){
				Stage noviStage=new Stage();
				try {
					FXMLLoader loader;
					if(cbKategorija.getSelectionModel().getSelectedItem().getId()<=2){
						loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/organizator/view/SinglZrijebView.fxml"));
						AnchorPane root = (AnchorPane) loader.load();
						Scene scene = new Scene(root);
						noviStage.setScene(scene);
						noviStage.setResizable(false);
						noviStage.setTitle("Stonoteniski klub");
						SinglZrijebController controller=loader.<SinglZrijebController>getController();
						controller.setPrimaryStage(noviStage);
						controller.inicijalizuj(tblTurniri.getSelectionModel().getSelectedItem().getId(),
								cbKategorija.getSelectionModel().getSelectedItem().getId());
						noviStage.show();
					}
					else{
						loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/organizator/view/DublZrijebView.fxml"));
						AnchorPane root = (AnchorPane) loader.load();
						Scene scene = new Scene(root);
						noviStage.setScene(scene);
						noviStage.setResizable(false);
						noviStage.setTitle("Stonoteniski klub");
						DublZrijebController controller=loader.<DublZrijebController>getController();
						controller.setPrimaryStage(noviStage);
						controller.inicijalizuj(tblTurniri.getSelectionModel().getSelectedItem().getId(),
								cbKategorija.getSelectionModel().getSelectedItem().getId());
						noviStage.show();
					}
				} catch (IOException e) {
					 ;
					new ErrorLogger().log(e);
				}
			}
			else{
				AlertDisplay.showInformation("Pregled", "Ne postoji žrijeb za dati turnir u ovoj kategoriji, jer se u njoj nije igralo.");
			}
		}
	}
	
	public void dodajTurnir(){
		if(txtNaziv.getText().length()<=45)
			if(dpDatum.getValue().isAfter(LocalDate.now()) || dpDatum.getValue().isEqual(LocalDate.now())){
				DAOFactory.getDAOFactory().getTurnirDAO().insert(txtNaziv.getText(), Date.valueOf(dpDatum.getValue()));
				popuniTabelu();
				txtNaziv.clear();
				dpDatum.setValue(null);
			}
			else{
				AlertDisplay.showError("Dodavanje", "Nije moguće dodati turnir čiji je datum prije današnjeg.");
				txtNaziv.clear();
				dpDatum.setValue(null);
			}
		else{

			AlertDisplay.showError("Dodavanje", "Nije moguće dodati turnir sa nazivom dužim od 45 karaktera.");
			txtNaziv.clear();
			dpDatum.setValue(null);
		}
	}
	
	public static String konvertujIzSQLDate(String sqlDatum){	//pomocna metoda za prikaz datuma u tabelama
		String datum;
		String[]niz=sqlDatum.split("-");
		datum=niz[2]+"."+niz[1]+"."+niz[0]+".";
		return datum;
	}
}
