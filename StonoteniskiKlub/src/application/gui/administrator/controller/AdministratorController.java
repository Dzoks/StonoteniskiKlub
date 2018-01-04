package application.gui.administrator.controller;

import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;

public class AdministratorController extends BaseController {
	@FXML
	private TableView tabelaNalog;
	@FXML
	private TableColumn kolonaIme;
	@FXML
	private TableColumn kolonaPrezime;
	@FXML
	private TableColumn kolonaUloga;
	@FXML
	private TableColumn kolonaKorisnickoIme;


	// Event Listener on Button.onAction
	@FXML
	public void dodajNalogKlik(ActionEvent event) {
		Stage stage=new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		try {
			BaseController.changeScene("/application/gui/administrator/view/DodajNalogView.fxml", stage);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	// Event Listener on Button.onAction
	@FXML
	public void obrisiNalogKlik(ActionEvent event) {
		// setuje na neaktivan?
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
