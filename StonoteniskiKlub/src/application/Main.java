package application;

import application.gui.controller.BaseController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		BaseController.changeScene("/application/PocetniView.fxml", primaryStage);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Rad sa opremom");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
