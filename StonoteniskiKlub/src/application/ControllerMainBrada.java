package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.gui.sekretar.controller.IzdavanjePotvrdaController;
import application.gui.trener.controller.IzmjenaClanaController;
import application.gui.trener.controller.UclanjivanjeController;
import application.model.dao.ClanDAO;
import application.model.dto.ClanDTO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ControllerMainBrada extends BaseController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    void openPotvrde(ActionEvent event) {
    	try {
    		Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/sekretar/view/IzdavanjePotvrdaView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			IzdavanjePotvrdaController control = loader.<IzdavanjePotvrdaController>getController();
			control.setPrimaryStage(stage);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Potvrde");
			
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
}

