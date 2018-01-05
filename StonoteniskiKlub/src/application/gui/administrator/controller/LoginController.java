package application.gui.administrator.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.poi.ddf.EscherColorRef.SysIndexSource;
import org.mindrot.jbcrypt.BCrypt;

import com.itextpdf.text.log.SysoCounter;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.gui.controller.BaseController;
import application.model.dao.KorisnickiNalogDAO;
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
	void prijaviteSe(ActionEvent event) {

		if (!txtKorisnickoIme.getText().isEmpty()) {
			korisnickoIme = txtKorisnickoIme.getText();
			if (KorisnickiNalogDAO.daLiPostoji(txtKorisnickoIme.getText())) {
				if (!KorisnickiNalogDAO.daLiPostojiLozinka(txtKorisnickoIme.getText())) {
					try {
						BaseController.changeScene("/application/gui/administrator/view/PromjenaLozinkeView.fxml",
								primaryStage);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					if (checkPassword(new String(txtLozinka.getText()),
							KorisnickiNalogDAO.getHashByUsername(txtKorisnickoIme.getText()))) {
						try {
							String uloga = KorisnickiNalogDAO.getUloga(txtKorisnickoIme.getText());
							switch (uloga) {
							case "Administrator":
								BaseController.changeScene("/application/gui/administrator/view/AdministratorView.fxml",
										primaryStage);
								break;
							case "Organizator turnira":
								BaseController.changeScene("/application/gui/organizator/view/TurniriView.fxml",
										primaryStage);
								break;
							case "Trener":
								BaseController.changeScene("/application/gui/trener/view/OpremaGlavniView.fxml",
										primaryStage);
								break;
							case "Računovođa":
								BaseController.changeScene(
										"/application/gui/racunovodja/view/PocetniProzorRacunovodja.fxml",
										primaryStage);
								break;
							case "Sekretar":
								BaseController.changeScene("/application/gui/sekretar/view/RadSaZaposlenimaView.fxml",
										primaryStage);
								break;
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						new Alert(AlertType.ERROR, "Pogrešno korisničko ime ili lozinka.", ButtonType.OK).show();
						txtKorisnickoIme.clear();
						txtLozinka.clear();
					}
				}
			} else {
				new Alert(AlertType.ERROR, "Pogrešno korisničko ime ili lozinka.", ButtonType.OK).show();
				txtKorisnickoIme.clear();
				txtLozinka.clear();
			}
		} else {
			new Alert(AlertType.ERROR, "Popunite sva polja.", ButtonType.OK).show();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
