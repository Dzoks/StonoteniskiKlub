package application.gui.trener.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.ClanDAO;
import application.model.dto.ClanDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PregledClanovaController extends BaseController implements Initializable {

    @FXML
    private TableView<ClanDTO> twTabela;

    @FXML
    private TableColumn<ClanDTO, String> tcIme;

    @FXML
    private TableColumn<ClanDTO, String> tcPrezime;

    @FXML
    private TableColumn<ClanDTO, String> tcImeRoditelja;

    @FXML
    private TableColumn<ClanDTO, Boolean> tcAktivan;

    @FXML
    private TableColumn<ClanDTO, Boolean> tcRegistrovan;

    @FXML
    private MenuItem miIzmjeni;

    @FXML
    private MenuItem miIsclani;

    @FXML
    private TextField txtIme;

    @FXML
    private TextField txtPrezime;
    
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tcIme.setCellFactory(TextFieldTableCell.forTableColumn());
		tcIme.setCellValueFactory(new PropertyValueFactory<ClanDTO, String>("ime"));
		
		tcPrezime.setCellFactory(TextFieldTableCell.forTableColumn());
		tcPrezime.setCellValueFactory(new PropertyValueFactory<ClanDTO, String>("prezime"));
		
		tcImeRoditelja.setCellFactory(TextFieldTableCell.forTableColumn());
		tcImeRoditelja.setCellValueFactory(new PropertyValueFactory<ClanDTO, String>("imeRoditelja"));
		
		tcAktivan.setCellValueFactory(new PropertyValueFactory<ClanDTO, Boolean>("aktivan"));
		tcAktivan.setVisible(false);
		
		tcRegistrovan.setCellValueFactory(new PropertyValueFactory<ClanDTO, Boolean>("registrovan"));
		tcRegistrovan.setVisible(false);
		
		listaClanova = FXCollections.observableArrayList();
		listaClanova.addAll(ClanDAO.selectAll());
		twTabela.setItems(listaClanova);
	}
	
	public void idiNaPregledOpreme() {
		Stage noviStage = new Stage();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/trener/view/OpremaGlavniProzor.fxml"));
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
	
	public void idiNaPregledNarudzbi() {
		Stage noviStage = new Stage();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/sekretar/view/NarudzbaGlavniProzor.fxml"));
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

	public void uclanjivanje() {
		try {
    		Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/trener/view/UclanjivanjeView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			UclanjivanjeController control = loader.<UclanjivanjeController>getController();
			control.setPrimaryStage(stage);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Uclanjivanje");
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					event.consume();
					control.izlaz();
				}
			});
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
			ClanDTO clan = control.getClan();
			if(clan!=null)
				listaClanova.add(clan);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void izmjeniClana() {
		ClanDTO clan = twTabela.getSelectionModel().getSelectedItem();
		
		try {
    		Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/trener/view/IzmjenaClanaView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			IzmjenaClanaController control = loader.<IzmjenaClanaController>getController();
			control.setClan(clan);
			control.popuniPolja();
			control.setPrimaryStage(stage);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Izmjena");
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					event.consume();
					control.izlaz();
				}
			});
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private ObservableList<ClanDTO> listaClanova;
}

