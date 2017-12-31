package application.gui.administrator.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.gui.controller.BaseController;
import application.gui.trener.controller.NoviTelefonController;
import application.model.dao.KorisnickiNalogDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginController extends BaseController {

	@FXML
	private JFXTextField txtKorisnickoIme;

	@FXML
	private JFXPasswordField txtLozinka;

	@FXML
	void prijaviteSe(ActionEvent event) {

		if (!txtLozinka.getText().isEmpty() && !txtLozinka.getText().isEmpty()) {
			if(!KorisnickiNalogDAO.daLiPostoji(txtKorisnickoIme.getText())){
			if(!KorisnickiNalogDAO.daLiPostojiLozinka(txtKorisnickoIme.getText())) {
				//ako ne postoji napravi je
				try {
					BaseController.changeScene("/application/gui/administrator/view/PromjenaLozinkeView.fxml", primaryStage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				
				try {
					BaseController.changeScene("/application/gui/administrator/view/AdministratorView.fxml", primaryStage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			} else {
				new Alert(AlertType.ERROR, "Pogrešno korisničko ime ili lozinka.", ButtonType.OK).show();
			}

		} else {
			new Alert(AlertType.ERROR, "Popunite sva polja.", ButtonType.OK).show();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
