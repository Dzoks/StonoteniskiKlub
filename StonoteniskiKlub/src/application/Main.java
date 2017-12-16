package application;

import application.gui.controller.BaseController;
import javafx.application.Application;
import javafx.stage.Stage;
import application.gui.racunovodja.controller.PocetniProzorRacunovodjaController;
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		BaseController.changeScene("/application/gui/racunovodja/view/PocetniProzorRacunovodja.fxml", primaryStage);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Turniri");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);

	}

}
