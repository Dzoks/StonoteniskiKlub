package application.gui.trener.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.Clan;
import application.model.dto.NarudzbaStavka;
import application.model.dto.OpremaClana;
import application.model.dto.OpremaKluba;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class ObradiOpremuController extends BaseController implements Initializable{
	@FXML
	private Spinner<Integer> spinnerPristiglo;
	@FXML
	private ListView<Clan> listClanovi;
	@FXML
	private Button btnEvidentiraj;
	
	private Boolean opremaKluba = false;
	private NarudzbaStavka stavkaNarudzbe = null;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		popuniListu();
	}
	
	public void popuniListu() {
		ObservableList<Clan> listaClanova = DAOFactory.getDAOFactory().getOpremaClanaDAO().SELECT_AKTIVNE();
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
	
	public void disableDugmeEvidentiraj() {
		if(!opremaKluba) {
			btnEvidentiraj.disableProperty().bind(listClanovi.getSelectionModel().selectedItemProperty().isNull());
		}
	}
	
	public Boolean ubaciUBazu() {
		if(opremaKluba) {
			OpremaKluba opremaKluba = new OpremaKluba(null, stavkaNarudzbe.getIdNarudzbe(), stavkaNarudzbe.getIdTipaOpreme(), null, null, null, false, stavkaNarudzbe.getVelicina(), "Nova oprema.", true);
			DAOFactory.getDAOFactory().getOpremaKlubaDAO().INSERT(opremaKluba, spinnerPristiglo.getValue());
		}
		else {
			if(listClanovi.getSelectionModel().getSelectedItems().size() != spinnerPristiglo.getValue().intValue()) {
				new Alert(AlertType.ERROR, "Broj odabranih ljudi mora biti " + spinnerPristiglo.getValue() + ".", ButtonType.OK).show();
				return false;
			}
			ObservableList<Clan> listaClanova = listClanovi.getSelectionModel().getSelectedItems();
			for(Clan clan : listaClanova) {
				OpremaClana opremaClana = new OpremaClana(null, stavkaNarudzbe.getIdNarudzbe(), stavkaNarudzbe.getIdTipaOpreme(), stavkaNarudzbe.getVelicina(), clan.getId());
				DAOFactory.getDAOFactory().getOpremaClanaDAO().INSERT(opremaClana);
			}
		}
		
		DAOFactory.getDAOFactory().getNarudzbaStavkaDAO().UPDATE_OBRADJENO(stavkaNarudzbe);
		DAOFactory.getDAOFactory().getNarudzbaDAO().UPDATE_OBRADJENO(stavkaNarudzbe.getIdNarudzbe());
		
		return true;
	}
	
	public void evidentirajDodavanje() {
		if(ubaciUBazu()) {
			primaryStage.close();
		}
	}
	
	public void setOpremaKluba() {
		opremaKluba = true;
	}

	public void setStavkaNarudzbe(NarudzbaStavka stavkaNarudzbe) {
		this.stavkaNarudzbe = stavkaNarudzbe;
	}
}
