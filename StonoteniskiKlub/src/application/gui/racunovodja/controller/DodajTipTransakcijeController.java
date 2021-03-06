package application.gui.racunovodja.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.TipTransakcijeDTO;
import application.util.AlertDisplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
		AlertDisplay.showInformation("Dodavanje",  "Uspješno dodavanje tipa transakcije.");
		this.getPrimaryStage().close();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}
