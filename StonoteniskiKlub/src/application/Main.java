package application;

import application.gui.controller.BaseController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		BaseController.changeScene("/application/gui/sekretar/view/DodavanjeSponzoraView.fxml", primaryStage);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Dodavanje sponzora");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
