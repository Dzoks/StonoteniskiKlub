package application;

import com.itextpdf.text.log.SysoCounter;

import application.gui.controller.BaseController;
import application.gui.sekretar.controller.RadSaUgovorimaController;
import application.gui.trener.controller.RegistracijaController;
import application.model.dao.ClanDAO;
import application.model.dto.ClanDTO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		BaseController.changeScene("/application/gui/sekretar/view/RadSaZaposlenimaView.fxml", primaryStage);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Registracija");
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
