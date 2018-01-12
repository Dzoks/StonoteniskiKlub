package application.gui.organizator.controller;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import application.gui.controller.BaseController;
import application.model.dao.KategorijaTurniraDAO;
import application.model.dao.TimDAO;
import application.model.dao.TurnirDAO;
import application.model.dao.ZrijebDAO;
import application.model.dto.TurnirDTO;
import application.model.dto.UcesnikPrijavaDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
	
	private Integer idTurnira;
	private Integer idKategorije;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
	
	public void inicijalizuj(Integer idTurnira,Integer idKategorije){
		this.idTurnira=idTurnira;
		this.idKategorije=idKategorije;
		TurnirDTO turnir=TurnirDAO.getById(idTurnira);
		lblNaziv.setText(turnir.getNaziv());
		lblDatum.setText(TurniriController.konvertujIzSQLDate(turnir.getDatum().toString()));
		primaryStage.setTitle(idKategorije<3?"Singl turnir":"Dubl turnir");
		lblKategorija.setText(KategorijaTurniraDAO.getById(idKategorije).toString());
		btnIzmjeni.disableProperty().bind(tblIgraci.getSelectionModel().selectedItemProperty().isNull());
		popuniTabelu();
	}
	
	public void popuniTabelu(){
		clnIme.setCellValueFactory(new PropertyValueFactory<>("ime"));
		clnPrezime.setCellValueFactory(new PropertyValueFactory<>("prezime"));
		clnJMBG.setCellValueFactory(new PropertyValueFactory<>("jmb"));
		clnDatumRodjenja.setCellValueFactory(new PropertyValueFactory<>("konvertovanDatumRodjenja"));
		tblIgraci.setItems(idKategorije<3?TimDAO.getSingle(idTurnira,idKategorije):TimDAO.getDouble(idTurnira, idKategorije));
		lblBroj.setText(String.valueOf(tblIgraci.getItems().size()));
	}
	
	public void prijaviIgraca(){
		if(ZrijebDAO.doesExist(idTurnira, idKategorije)){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Greška");
			alert.setHeaderText("Nije dozvoljeno prijavljivanje!");
			alert.setContentText("Nije moguće prijaviti igrača nakon izvršenog žrijebanja za dati turnir.");
			alert.show();
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
						noviStage.setTitle("Dubl prijava");
						noviStage.initModality(Modality.APPLICATION_MODAL);
						DublPrijavaController controller=loader.<DublPrijavaController>getController();
						controller.setPrimaryStage(noviStage);
						controller.inicijalizuj(idTurnira,idKategorije);
						noviStage.showAndWait();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				popuniTabelu();
			}
			else{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Greška");
				alert.setHeaderText("Nije dozvoljeno prijavljivanje!");
				alert.setContentText("Nije moguće prijaviti novog igrača, jer je prijavljen maksimalan broj igrača.");
				alert.show();
			}
		}
	}
	
	public void izmjeniIgraca(){
		if(ZrijebDAO.doesExist(idTurnira, idKategorije)){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Greška");
			alert.setHeaderText("Nije dozvoljena izmjena!");
			alert.setContentText("Nije moguće mijenjati podatke o igračima nakon izvršenog žrijebanja za dati turnir.");
			alert.show();
		}
		else{
			Stage noviStage=new Stage();
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/organizator/view/SinglPrijavaView.fxml"));
				AnchorPane root = (AnchorPane) loader.load();
				Scene scene = new Scene(root);
				noviStage.setScene(scene);
				noviStage.setResizable(false);
				noviStage.setTitle("Izmjena prijava");
				noviStage.initModality(Modality.APPLICATION_MODAL);
				SinglPrijavaController controller=loader.<SinglPrijavaController>getController();
				controller.setPrimaryStage(noviStage);
				controller.inicijalizujIzmjene(idTurnira,idKategorije,tblIgraci.getSelectionModel().getSelectedItem());
				noviStage.showAndWait();
			} catch (IOException e) {
				e.printStackTrace();
			}
			popuniTabelu();
		}
	}
	
	public void prikaziZrijeb(){
		if(ZrijebDAO.doesExist(idTurnira, idKategorije)){
			Stage noviStage=new Stage();
			try {
				if(idKategorije<=2){
					FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/organizator/view/SinglZrijebView.fxml"));
					AnchorPane root = (AnchorPane) loader.load();
					Scene scene = new Scene(root);
					noviStage.setScene(scene);
					noviStage.setResizable(false);
					noviStage.setTitle("Žrijeb");
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
					noviStage.setTitle("Žrijeb");
					noviStage.initModality(Modality.APPLICATION_MODAL);
					DublZrijebController controller=loader.<DublZrijebController>getController();
					controller.setPrimaryStage(noviStage);
					controller.inicijalizuj(idTurnira, idKategorije);
					noviStage.showAndWait();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			if(tblIgraci.getItems().size()<16){
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Greška");
				alert.setHeaderText("Nije moguće izvršiti žrijebanje!");
				alert.setContentText("Minimalan potreban broj prijavljenih igrača na turniru je 16. "
						+ "Nije moguće kreiranje žrijeba za turnir na koji je prijavleno manje igrača.");
				alert.show();
			}
			else{
				ButtonType buttonTypeDa=new ButtonType("Da");
				ButtonType buttonTypeNe=new ButtonType("Ne");
				Alert alert = new Alert(AlertType.CONFIRMATION,"Ukoliko izvršite žrijebanje za izabrani turnir,"
						+ " nećete biti u mogućnosti da ponovo prijavite igrače za taj turnir!",buttonTypeDa,buttonTypeNe,ButtonType.CANCEL);
				alert.setHeaderText("Da li ste sigurni da želite izvršiti žrijebanje za izabrani turnir?");
				alert.setTitle("Obavještenje");
				Optional<ButtonType> result = alert.showAndWait();
				if(result.get().equals(buttonTypeDa)){
					btnPrijavi.setDisable(true);
					Stage noviStage=new Stage();
					try {
						if(idKategorije<=2){
							FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/organizator/view/SinglZrijebView.fxml"));
							AnchorPane root = (AnchorPane) loader.load();
							Scene scene = new Scene(root);
							noviStage.setScene(scene);
							noviStage.setResizable(false);
							noviStage.setTitle("Žrijeb");
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
							noviStage.setTitle("Žrijeb");
							noviStage.initModality(Modality.APPLICATION_MODAL);
							DublZrijebController controller=loader.<DublZrijebController>getController();
							controller.setPrimaryStage(noviStage);
							controller.inicijalizujPrvi(idTurnira, idKategorije,tblIgraci.getItems().size()/2);
							noviStage.showAndWait();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
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
