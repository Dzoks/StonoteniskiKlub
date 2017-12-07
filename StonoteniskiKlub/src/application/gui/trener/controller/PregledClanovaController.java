package application.gui.trener.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PregledClanovaController extends BaseController implements Initializable {

    @FXML
    private TableView<?> twTabela;

    @FXML
    private TableColumn<?, ?> tcIme;

    @FXML
    private TableColumn<?, ?> tcPrezime;

    @FXML
    private TableColumn<?, ?> tcImeRoditelja;

    @FXML
    private TableColumn<?, ?> tcAktivan;

    @FXML
    private TableColumn<?, ?> tcRegistrovan;

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
		// TODO Auto-generated method stub
		
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
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

