package application.gui.trener.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.NarudzbaDAO;
import application.model.dao.NarudzbaStavkaDAO;
import application.model.dao.OpremaClanaDAO;
import application.model.dao.OpremaKlubaDAO;
import application.model.dto.ClanDTO;
import application.model.dto.NarudzbaStavkaDTO;
import application.model.dto.OpremaClanaDTO;
import application.model.dto.OpremaKlubaDTO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Alert.AlertType;

public class ObradiOpremuProzorController extends BaseController implements Initializable{
	@FXML
	private Spinner<Integer> spinnerPristiglo;
	@FXML
	private ListView<ClanDTO> listClanovi;
	@FXML
	private Button btnEvidentiraj;
	
	private Boolean opremaKluba = false;
	private NarudzbaStavkaDTO stavkaNarudzbe = null;
	private Boolean donirana = false;
	private Integer idDonacije = null;
	
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
	
	public void disableDugmeEvidentiraj() {
		if(!opremaKluba) {
			btnEvidentiraj.disableProperty().bind(listClanovi.getSelectionModel().selectedItemProperty().isNull());
		}
	}
	
	public Boolean ubaciUBazu() {
		if(opremaKluba) {
			OpremaKlubaDTO opremaKluba = new OpremaKlubaDTO(null, stavkaNarudzbe.getIdNarudzbe(), stavkaNarudzbe.getIdTipaOpreme(), idDonacije, donirana, "Nova oprema.", true);
			OpremaKlubaDAO.INSERT(opremaKluba, spinnerPristiglo.getValue());
		}
		else {
			if(listClanovi.getSelectionModel().getSelectedItems().size() != spinnerPristiglo.getValue().intValue()) {
				new Alert(AlertType.ERROR, "Broj odabranih ljudi mora biti " + spinnerPristiglo.getValue() + ".", ButtonType.OK).show();
				return false;
			}
			ObservableList<ClanDTO> listaClanova = listClanovi.getSelectionModel().getSelectedItems();
			for(ClanDTO clan : listaClanova) {
				OpremaClanaDTO opremaClana = new OpremaClanaDTO(null, stavkaNarudzbe.getIdNarudzbe(), stavkaNarudzbe.getIdTipaOpreme(), idDonacije, donirana, stavkaNarudzbe.getVelicina(), clan.getId());
				OpremaClanaDAO.INSERT(opremaClana);
			}
		}
		NarudzbaStavkaDAO.UPDATE_OBRADJENO(stavkaNarudzbe);
		NarudzbaDAO.UPDATE_OBRADJENO(stavkaNarudzbe.getIdNarudzbe());
		
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

	public void setStavkaNarudzbe(NarudzbaStavkaDTO stavkaNarudzbe) {
		this.stavkaNarudzbe = stavkaNarudzbe;
	}

	public void setDonirana() {
		this.donirana = true;
	}

	public void setIdDonacije(Integer idDonacije) {
		this.idDonacije = idDonacije;
	}
}
