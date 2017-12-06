package application;

import application.gui.controller.BaseController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		BaseController.changeScene("/application/gui/administrator/view/LoginView.fxml", primaryStage);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Stonoteniski Klub");
		primaryStage.show();
		//aloooo
	}

	public static void main(String[] args) {
		launch(args);

	}

}
