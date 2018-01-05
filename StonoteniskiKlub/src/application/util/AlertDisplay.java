package application.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertDisplay {
	public static void showInformation(String title, String headerText, String contentText){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}
}
