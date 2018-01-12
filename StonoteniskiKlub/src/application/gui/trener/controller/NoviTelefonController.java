package application.gui.trener.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class NoviTelefonController extends BaseController implements Initializable{

	@FXML
    private TextField txtNoviTelefon;
	
	private String text;
	private int retVal = -1;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void OKaction() {
		text = txtNoviTelefon.getText();
		if(text.matches("[0-9][0-9][0-9]/[0-9][0-9][0-9]-[0-9][0-9][0-9]")) {
			retVal = 1;
			primaryStage.close();
		}
		else {
			new Alert(AlertType.ERROR, "Broj telefona nije u dobrom formatu. Format je XXX/XXX-XXX.", ButtonType.OK).show();
			text = "";
			txtNoviTelefon.setText(text);
		}
	}
	
	public void cancleAction() {
		retVal = 0;
		text = "";
		primaryStage.close();
	}

	public String getNoviTelefon() {
		return text;
	}
	
	public int getReturnValue() {
		return retVal;
	}
}
