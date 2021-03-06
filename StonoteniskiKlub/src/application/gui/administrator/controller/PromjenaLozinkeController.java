package application.gui.administrator.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.mindrot.jbcrypt.BCrypt;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.util.AlertDisplay;
import application.util.ErrorLogger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

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
				DAOFactory.getDAOFactory().getKorisnickiNalogDAO().setLozinka(hashPassword(lozinkaTxt.getText()).getBytes(), LoginController.korisnickoIme);
				AlertDisplay.showInformation("Dodavanje", "Lozinka je uspješno postavljena.");
				try {
					BaseController.changeScene("/application/gui/administrator/view/LoginView.fxml",
							primaryStage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					 ;
					new ErrorLogger().log(e);
				}

			}else {
				AlertDisplay.showError("Dodavanje", "Lozinke se ne podudaraju. Pokušajte ponovo.");
				lozinkaPonovoTxt.clear();
				lozinkaTxt.clear();

			}
		}else {
			AlertDisplay.showError("Dodavanje", "Niste popunili sva polja. Pokušajte ponovo.");
;

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
			 ;
			new ErrorLogger().log(e);
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
