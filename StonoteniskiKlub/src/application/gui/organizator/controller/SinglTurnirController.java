package application.gui.organizator.controller;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.TurnirDTO;
import application.model.dto.UcesnikPrijavaDTO;
import application.util.AlertDisplay;
import application.util.ErrorLogger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SinglTurnirController extends BaseController{
	@FXML
	private Label lblNaziv;
	@FXML
	private Label lblDatum;
	@FXML
	private Label lblKategorija;
	@FXML
	private Label lblBroj;
	@FXML
	private TableView<UcesnikPrijavaDTO> tblIgraci;
	@FXML
	private TableColumn<UcesnikPrijavaDTO,String> clnIme;
	@FXML
	private TableColumn<UcesnikPrijavaDTO,String> clnPrezime;
	@FXML
	private TableColumn<UcesnikPrijavaDTO,String> clnJMBG;
	@FXML
	private TableColumn<UcesnikPrijavaDTO,String> clnDatumRodjenja;
	@FXML
	private Button btnPrijavi;
	@FXML
	private Button btnIzmjeni;
	@FXML
	private Button btnZrijeb;
	@FXML
	private Button btnNazad;
	private String username;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private Integer idTurnira;
	private Integer idKategorije;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
	
	public void inicijalizuj(Integer idTurnira,Integer idKategorije){
		this.idTurnira=idTurnira;
		this.idKategorije=idKategorije;
		TurnirDTO turnir=DAOFactory.getDAOFactory().getTurnirDAO().getById(idTurnira);
		lblNaziv.setText(turnir.getNaziv());
		lblDatum.setText(TurniriController.konvertujIzSQLDate(turnir.getDatum().toString()));
		primaryStage.setTitle("Stonoteniski klub");
		lblKategorija.setText(DAOFactory.getDAOFactory().getKategorijaTurniraDAO().getById(idKategorije).toString());
		btnIzmjeni.disableProperty().bind(tblIgraci.getSelectionModel().selectedItemProperty().isNull());
		popuniTabelu();
	}
	
	public void popuniTabelu(){
		clnIme.setCellValueFactory(new PropertyValueFactory<>("ime"));
		clnPrezime.setCellValueFactory(new PropertyValueFactory<>("prezime"));
		clnJMBG.setCellValueFactory(new PropertyValueFactory<>("jmb"));
		clnDatumRodjenja.setCellValueFactory(new PropertyValueFactory<>("konvertovanDatumRodjenja"));
		tblIgraci.setItems(idKategorije<3?DAOFactory.getDAOFactory().getTimDAO().getSingle(idTurnira,idKategorije):DAOFactory.getDAOFactory().getTimDAO().getDouble(idTurnira, idKategorije));
		lblBroj.setText(String.valueOf(tblIgraci.getItems().size()));
	}
	
	public void prijaviIgraca(){
		if(DAOFactory.getDAOFactory().getZrijebDAO().doesExist(idTurnira, idKategorije)){
			AlertDisplay.showError("Prijava", "Nije moguće prijaviti igrača nakon izvršenog žrijebanja za dati turnir.");
		}
		else{
			if(tblIgraci.getItems().size()<32){
				Stage noviStage=new Stage();
				try {
					if(idKategorije<3){
						FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/organizator/view/SinglPrijavaView.fxml"));
						AnchorPane root = (AnchorPane) loader.load();
						Scene scene = new Scene(root);
						noviStage.setScene(scene);
						noviStage.setResizable(false);
						noviStage.setTitle("Singl prijava");
						noviStage.initModality(Modality.APPLICATION_MODAL);
						SinglPrijavaController controller=loader.<SinglPrijavaController>getController();
						controller.setPrimaryStage(noviStage);
						controller.inicijalizuj(idTurnira,idKategorije);
						noviStage.showAndWait();
					}
					else{
						FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/organizator/view/DublPrijavaView.fxml"));
						AnchorPane root = (AnchorPane) loader.load();
						Scene scene = new Scene(root);
						noviStage.setScene(scene);
						noviStage.setResizable(false);
						noviStage.setTitle("Stonoteniski klub");
						noviStage.initModality(Modality.APPLICATION_MODAL);
						DublPrijavaController controller=loader.<DublPrijavaController>getController();
						controller.setPrimaryStage(noviStage);
						controller.inicijalizuj(idTurnira,idKategorije);
						noviStage.showAndWait();
					}
				} catch (IOException e) {
					 ;
					new ErrorLogger().log(e);
				}
				popuniTabelu();
			}
			else{
				AlertDisplay.showError("Prijava", "Nije moguće prijaviti novog igrača, jer je prijavljen maksimalan broj igrača.");
				
			}
		}
	}
	
	public void izmjeniIgraca(){
		if(DAOFactory.getDAOFactory().getZrijebDAO().doesExist(idTurnira, idKategorije)){
			AlertDisplay.showError("Izmjena", "Nije moguće mijenjati podatke o igračima nakon izvršenog žrijebanja za dati turnir.");
		}
		else{
			Stage noviStage=new Stage();
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/organizator/view/SinglPrijavaView.fxml"));
				AnchorPane root = (AnchorPane) loader.load();
				Scene scene = new Scene(root);
				noviStage.setScene(scene);
				noviStage.setResizable(false);
				noviStage.setTitle("Stonoteniski klub");
				noviStage.initModality(Modality.APPLICATION_MODAL);
				SinglPrijavaController controller=loader.<SinglPrijavaController>getController();
				controller.setPrimaryStage(noviStage);
				controller.inicijalizujIzmjene(idTurnira,idKategorije,tblIgraci.getSelectionModel().getSelectedItem());
				noviStage.showAndWait();
			} catch (IOException e) {
				 ;
				new ErrorLogger().log(e);
			}
			popuniTabelu();
			tblIgraci.refresh();
		}
	}
	
	public void prikaziZrijeb(){
		if(DAOFactory.getDAOFactory().getZrijebDAO().doesExist(idTurnira, idKategorije)){
			Stage noviStage=new Stage();
			try {
				if(idKategorije<=2){
					FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/organizator/view/SinglZrijebView.fxml"));
					AnchorPane root = (AnchorPane) loader.load();
					Scene scene = new Scene(root);
					noviStage.setScene(scene);
					noviStage.setResizable(false);
					noviStage.setTitle("Stonoteniski klub");
					noviStage.initModality(Modality.APPLICATION_MODAL);
					SinglZrijebController controller=loader.<SinglZrijebController>getController();
					controller.setPrimaryStage(noviStage);
					controller.inicijalizuj(idTurnira, idKategorije);
					noviStage.showAndWait();
				}
				else{
					FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/organizator/view/DublZrijebView.fxml"));
					AnchorPane root = (AnchorPane) loader.load();
					Scene scene = new Scene(root);
					noviStage.setScene(scene);
					noviStage.setResizable(false);
					noviStage.setTitle("Stonoteniski klub");
					noviStage.initModality(Modality.APPLICATION_MODAL);
					DublZrijebController controller=loader.<DublZrijebController>getController();
					controller.setPrimaryStage(noviStage);
					controller.inicijalizuj(idTurnira, idKategorije);
					noviStage.showAndWait();
				}
			} catch (IOException e) {
				 ;
				new ErrorLogger().log(e);
			}
		}
		else{
			if(tblIgraci.getItems().size()<16){
				AlertDisplay.showError("Žrijeb","Minimalan potreban broj prijavljenih igrača na turniru je 16. "
						+ "Nije moguće kreiranje žrijeba za turnir na koji je prijavljeno manje igrača.");
			}
			else{				
				Optional<ButtonType> result = AlertDisplay.showConfirmation("Žrijeb", "Ukoliko izvršite žrijebanje za izabrani turnir,"
						+ " nećete biti u mogućnosti da ponovo prijavite igrače za taj turnir!"+"Da li ste sigurni da želite izvršiti žrijebanje za izabrani turnir?");
				if(result.get().getButtonData().equals(ButtonData.YES)){
					btnPrijavi.setDisable(true);
					Stage noviStage=new Stage();
					try {
						if(idKategorije<=2){
							FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/organizator/view/SinglZrijebView.fxml"));
							AnchorPane root = (AnchorPane) loader.load();
							Scene scene = new Scene(root);
							noviStage.setScene(scene);
							noviStage.setResizable(false);
							noviStage.setTitle("Stonoteniski Klub");
							noviStage.initModality(Modality.APPLICATION_MODAL);
							SinglZrijebController controller=loader.<SinglZrijebController>getController();
							controller.setPrimaryStage(noviStage);
							controller.inicijalizujPrvi(idTurnira, idKategorije,tblIgraci.getItems().size());
							noviStage.showAndWait();
						}
						else{
							FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/organizator/view/DublZrijebView.fxml"));
							AnchorPane root = (AnchorPane) loader.load();
							Scene scene = new Scene(root);
							noviStage.setScene(scene);
							noviStage.setResizable(false);
							noviStage.setTitle("Stonoteniski klub");
							noviStage.initModality(Modality.APPLICATION_MODAL);
							DublZrijebController controller=loader.<DublZrijebController>getController();
							controller.setPrimaryStage(noviStage);
							controller.inicijalizujPrvi(idTurnira, idKategorije,tblIgraci.getItems().size()/2);
							noviStage.showAndWait();
						}
					} catch (IOException e) {
						 ;
						new ErrorLogger().log(e);
					}
				}
			}
		}
	}
	
	public void vratiNazad(){
		try {
			BaseController bc=changeScene("/application/gui/organizator/view/TurniriView.fxml", primaryStage);
			bc.setUsername(username.split("\\,")[1]);
		} catch (IOException e) {
			 ;
			new ErrorLogger().log(e);
		}
	}
}
