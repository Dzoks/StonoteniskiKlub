package application;

import application.gui.controller.BaseController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage = new Stage();
		BaseController.changeScene("/application/gui/sekretar/view/DodajZaposlenogView.fxml", primaryStage);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Malina");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);

	}

}
