package application.gui.controller;

import java.io.IOException;

import application.util.ErrorLogger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import javafx.stage.Stage;

public abstract class BaseController implements Initializable{

	protected Stage primaryStage;
	
	@FXML
	protected Label lblUsername;

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public static BaseController changeScene(String path, Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader(BaseController.class.getResource(path));
		Parent pane = (Parent) loader.load();
		BaseController control = loader.<BaseController>getController();
		control.setPrimaryStage(primaryStage);
		control.getPrimaryStage().setScene(new Scene(pane));
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() -  primaryStage.getWidth()) / 2); 
        primaryStage.setY((primScreenBounds.getHeight() -  primaryStage.getHeight()) / 4);  
		control.primaryStage.setOnCloseRequest(event->{});
		return control;
	}
	
	public void setUsername(String username) {
		lblUsername.setText("Zdravo, "+username);
	}
	@FXML
	public void odjaviteSe() {
		try {
			changeScene("/application/gui/administrator/view/LoginView.fxml",primaryStage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new ErrorLogger().log(e);
		}
	}
}
