package application;

import application.gui.controller.BaseController;
import javafx.application.Application;
import javafx.stage.Stage;
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		BaseController.changeScene("/application/gui/trener/view/PregledClanovaView.fxml", primaryStage);
		//BaseController.changeScene("/application/test/drugi.fxml", primaryStage);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Turniri");
		primaryStage.centerOnScreen();
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
