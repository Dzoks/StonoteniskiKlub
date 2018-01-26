package application.gui.sekretar.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dto.StavkaSkupstinaDTO;
import application.util.AlertDisplay;
import application.util.InputValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DodavanjeStavkeDnevnogRedaController extends BaseController{
	@FXML
	private TextField txtNaslov;
	@FXML
	private TextArea taTekst;
	@FXML
	private Button btnSacuvaj;

	// Event Listener on Button[#btnSacuvaj].onAction
	@FXML
	public void sacuvaj(ActionEvent event) {
		if(InputValidator.allEntered(txtNaslov.getText(), taTekst.getText())){
			this.stavka.setNaslov(txtNaslov.getText());
			this.stavka.setTekst(taTekst.getText());
			Stage stage = (Stage)btnSacuvaj.getScene().getWindow();
			parentController.dodaj(stavka);
			stage.close();
		} else{
			AlertDisplay.showError("Dodavanje", "Niste unijeli sve potrebne podatke.");
		}
	}
	
	public void setStavka(StavkaSkupstinaDTO stavka){
		this.stavka = stavka;
	}
	public void setParentController(DodavanjeSkupstineController parentController){
		this.parentController = parentController;
	}
	private StavkaSkupstinaDTO stavka;
	private DodavanjeSkupstineController parentController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bindDisable();
	}
	private void bindDisable(){
		btnSacuvaj.disableProperty().bind(taTekst.textProperty().isEmpty().or(txtNaslov.textProperty().isEmpty()));
	}
}
