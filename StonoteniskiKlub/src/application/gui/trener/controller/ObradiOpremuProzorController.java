package application.gui.trener.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.OpremaClanaDAO;
import application.model.dto.ClanDTO;
import application.model.dto.NarudzbaStavkaDTO;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class ObradiOpremuProzorController extends BaseController implements Initializable{
	@FXML
	private Spinner<Integer> spinnerPristiglo;
	@FXML
	private ListView<ClanDTO> listClanovi;
	@FXML
	private Button btnEvidentiraj;
	
	private Boolean opremaKluba = false;
	private NarudzbaStavkaDTO stavkaNarudzbe = null;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		popuniListu();
	}
	
	public void popuniListu() {
		ObservableList<ClanDTO> listaClanova = OpremaClanaDAO.SELECT_AKTIVNE();
		listClanovi.setItems(listaClanova);
		listClanovi.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}
	
	public void postaviSpiner() {
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, stavkaNarudzbe.getKolicina(), 1, 1);
		spinnerPristiglo.setValueFactory(valueFactory);
	}
	
	public void disableParametre() {
		if(opremaKluba) {
			listClanovi.setDisable(true);
		}
	}
	
	public void setOpremaKluba() {
		opremaKluba = true;
	}

	public void setStavkaNarudzbe(NarudzbaStavkaDTO stavkaNarudzbe) {
		this.stavkaNarudzbe = stavkaNarudzbe;
	}
}
