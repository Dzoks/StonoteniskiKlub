package application;

import application.gui.controller.BaseController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		//BaseController.changeScene("/application/gui/administrator/view/LoginView.fxml", primaryStage);
		BaseController.changeScene("/application/gui/racunovodja/view/PocetniProzorRacunovodja.fxml", primaryStage);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Stonoteniski klub");
		primaryStage.show();
		
	}

	public static void main(String[] args) throws Exception {
		launch(args);
	}
}
