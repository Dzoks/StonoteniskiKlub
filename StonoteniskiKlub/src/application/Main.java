package application;

import application.gui.controller.BaseController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		BaseController.changeScene("/application/gui/sekretar/view/RadSaSponzorimaView.fxml", primaryStage);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Rad sa sponzorima");
		primaryStage.show();
		System.out.println("Test");
	}

	public static void main(String[] args) {
		launch(args);
	}

}
