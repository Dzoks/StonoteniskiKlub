package application.util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class AlertDisplay {
	
	public static final String NL = System.getProperty("line.separator");
	
	public static void showInformation(String headerText, String contentText){
		Alert alert = createAlert(headerText,contentText);
		alert.setTitle("Obavještenje");
		alert.showAndWait();
	}
	private static Alert createAlert(String headerText,String contentText) {
		Alert alert= new Alert(AlertType.INFORMATION);
		alert.setContentText(contentText);
		alert.setHeaderText(headerText);
		return alert;
	}
	public static void showError(String headerText,String contentText) {
		Alert alert=createAlert(headerText, contentText);
		alert.setAlertType(AlertType.ERROR);
		alert.setTitle("Greška");
		alert.showAndWait();
	}
	public static Optional<ButtonType> showConfirmation(String headerText,String contentText) {
		Alert alert=createAlert(headerText, contentText);
		alert.setTitle("Potvrda");
		alert.setAlertType(AlertType.CONFIRMATION);
		return alert.showAndWait();
	}
	public static void showWarning(String headerText,String contentText) {
		Alert alert=createAlert(headerText, contentText);
		alert.setAlertType(AlertType.WARNING);
		alert.setTitle("Upozorenje");
		alert.showAndWait();
	}
}
