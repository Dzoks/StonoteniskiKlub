package application.gui.administrator.controller;

import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.mindrot.jbcrypt.BCrypt;

import application.gui.controller.BaseController;
import application.model.dao.KorisnickiNalogDAO;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;

public class PromjenaLozinkeController extends BaseController {
	@FXML
	private PasswordField lozinkaTxt;
	@FXML
	private PasswordField lozinkaPonovoTxt;

	// Event Listener on Button.onAction
	@FXML
	public void dugmeOkKlik(ActionEvent event) {
		if(!lozinkaTxt.getText().isEmpty()&& !lozinkaPonovoTxt.getText().isEmpty()) {
			if(lozinkaPonovoTxt.getText().equals(lozinkaTxt.getText())) {
				KorisnickiNalogDAO.setLozinka(hashPassword(lozinkaTxt.getText()).getBytes(), LoginController.korisnickoIme);
				new Alert(AlertType.INFORMATION, "Lozinka je uspješno postavljena.").show();
				try {
					BaseController.changeScene("/application/gui/administrator/view/LoginView.fxml",
							primaryStage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}else {
				new Alert(AlertType.ERROR, "Lozinke se ne podudaraju. Pokušajte ponovo.").show();
				lozinkaPonovoTxt.clear();
				lozinkaTxt.clear();

			}
		}else {
			new Alert(AlertType.INFORMATION, "Unesite sva polja.").show();

		}
	}
	// Event Listener on Button.onAction
	@FXML
	public void dugmeCancelKlik(ActionEvent event) {
		try {
			BaseController.changeScene("/application/gui/administrator/view/LoginView.fxml",
					primaryStage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	public static String hashPassword(String password_plaintext) {
		String salt = BCrypt.gensalt(12);
		String hashed_password = BCrypt.hashpw(password_plaintext, salt);
		return (hashed_password);
	}
}
