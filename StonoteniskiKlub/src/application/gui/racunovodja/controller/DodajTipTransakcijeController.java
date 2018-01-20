package application.gui.racunovodja.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dao.TipTransakcijeDAO;
import application.model.dto.TipTransakcijeDTO;
import javafx.event.ActionEvent;

public class DodajTipTransakcijeController extends BaseController{ //popraviti polja
	@FXML
	private Button btnDodaj;
	@FXML
	private TextField txtTip;
	
	@FXML
	public void dodaj(ActionEvent event) {
		String tip = txtTip.getText().trim();
		TipTransakcijeDTO tipTransakcije = new TipTransakcijeDTO(tip);
		DAOFactory.getDAOFactory().getTipTransakcijeDAO().INSERT(tipTransakcije);
		Alert alert = new Alert(AlertType.INFORMATION, "Uspješno dodavanje tipa transakcije.");
		alert.setTitle("Informacija");
		alert.setHeaderText("Dodavanje");
		alert.showAndWait();
		this.getPrimaryStage().close();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}
