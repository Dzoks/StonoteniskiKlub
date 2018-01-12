package application;

import java.time.YearMonth;
import java.util.List;

import com.itextpdf.text.log.SysoCounter;

import application.gui.controller.BaseController;
import application.gui.sekretar.controller.RadSaUgovorimaController;
import application.gui.trener.controller.RegistracijaController;
import application.model.dao.ClanDAO;
import application.model.dao.DAOFactory;
import application.model.dto.ClanDTO;
import application.model.dto.DogadjajDTO;
import application.model.dto.DogadjajTipDTO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		BaseController.changeScene("/application/gui/administrator/view/LoginView.fxml", primaryStage);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Stonoteniski klub");
		primaryStage.show();
		
	}

	public static void main(String[] args) throws Exception {
		launch(args);
	}
}
