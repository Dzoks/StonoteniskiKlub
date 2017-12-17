package application.gui.administrator.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.gui.controller.BaseController;
import application.gui.trener.controller.NoviTelefonController;
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
			if (txtLozinka.getText().equals("admin") && txtKorisnickoIme.getText().equals("admin")) {
				//ovo izmijeniti nakon merge-a
				Stage noviStage = new Stage();
				
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/administrator/view/AdministratorView.fxml"));
					AnchorPane root = (AnchorPane) loader.load();
					Scene scene = new Scene(root,761,484);
					noviStage.setScene(scene);
					noviStage.setResizable(false);
					noviStage.setTitle("Stonoteniski klub");
					noviStage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else{
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
