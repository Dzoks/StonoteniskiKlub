package application.gui.administrator.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.mindrot.jbcrypt.BCrypt;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.util.AlertDisplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class LoginController extends BaseController {

	@FXML
	private JFXTextField txtKorisnickoIme;
	public static String korisnickoIme;

	@FXML
	private JFXPasswordField txtLozinka;
	@FXML
	private JFXButton prijaviteSeDugme;

	@FXML
	void prijaviteSe(ActionEvent event) {

		if (!txtKorisnickoIme.getText().isEmpty()) {
			korisnickoIme = txtKorisnickoIme.getText();
			if (DAOFactory.getDAOFactory().getKorisnickiNalogDAO().daLiPostoji(txtKorisnickoIme.getText())) {
				if (!DAOFactory.getDAOFactory().getKorisnickiNalogDAO().daLiPostojiLozinka(txtKorisnickoIme.getText())) {
					try {
						BaseController.changeScene("/application/gui/administrator/view/PromjenaLozinkeView.fxml",
								primaryStage);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					if (checkPassword(new String(txtLozinka.getText()),
							DAOFactory.getDAOFactory().getKorisnickiNalogDAO().getHashByUsername(txtKorisnickoIme.getText()))) {
						try {
							String uloga = DAOFactory.getDAOFactory().getKorisnickiNalogDAO().getUloga(txtKorisnickoIme.getText());
							BaseController bc = null;
							switch (uloga) {
							case "Administrator":
								bc = BaseController.changeScene(
										"/application/gui/administrator/view/AdministratorView.fxml", primaryStage);
								bc.setUsername(txtKorisnickoIme.getText());
								break;
							case "Organizator turnira":
								bc = BaseController.changeScene("/application/gui/organizator/view/TurniriView.fxml",
										primaryStage);
								bc.setUsername(txtKorisnickoIme.getText());
								break;
							case "Trener":
								bc = BaseController.changeScene("/application/gui/trener/view/OpremaGlavniView.fxml",
										primaryStage);
								bc.setUsername(txtKorisnickoIme.getText());
								break;
							case "Računovođa":
								bc = BaseController.changeScene(
										"/application/gui/racunovodja/view/RacunovodjaView.fxml", primaryStage);
								bc.setUsername(txtKorisnickoIme.getText());
								break;
							case "Sekretar":
								bc = BaseController.changeScene("/application/gui/sekretar/view/SekretarView.fxml",
										primaryStage);
								bc.setUsername(txtKorisnickoIme.getText());
								break;
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						AlertDisplay.showError("Prijavljivanje","Pogrešno korisničko ime ili lozinka.");
						txtKorisnickoIme.clear();
						txtLozinka.clear();
					}
				}
			} else {
				AlertDisplay.showError("Prijavljivanje","Pogrešno korisničko ime ili lozinka.");
				txtKorisnickoIme.clear();
				txtLozinka.clear();
			}
		} else {
			new Alert(AlertType.ERROR, "Popunite sva polja.", ButtonType.OK).show();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		prijaviteSeDugme.disableProperty().bind(txtKorisnickoIme.textProperty().isEmpty());
	}

	/*
	 * poklapanje plantext passworda sa sacuvanim u hesu
	 */
	public static boolean checkPassword(String password_plaintext, String stored_hash) {
		boolean password_verified = false;
		if (stored_hash == null)
			return false;

		if (!stored_hash.startsWith("$2a$"))
			throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

		password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

		return (password_verified);
	}

}
