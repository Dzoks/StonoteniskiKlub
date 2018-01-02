package application.gui.trener.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.OpremaKluba;
import javafx.event.ActionEvent;

import javafx.scene.control.TextArea;

public class IzmjenaOpisaOpremeController extends BaseController implements Initializable{
	@FXML
	private TextArea txtOpis;
	@FXML
	private Button btnSacuvajIzmjene;
	
	private OpremaKluba oprema;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void azurirajUBazi() {
		DAOFactory.getDAOFactory().getOpremaKlubaDAO().UPDATE_OPIS(oprema, txtOpis.getText());
	}
	
	public void sacuvajIzmjene(ActionEvent event) {
		azurirajUBazi();
		primaryStage.close();
	}

	public void setOprema(OpremaKluba oprema) {
		this.oprema = oprema;
	}
}
