package application.gui.trener.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.NarudzbaDAO;
import application.model.dao.NarudzbaStavkaDAO;
import application.model.dao.OpremaClanaDAO;
import application.model.dao.OpremaKlubaDAO;
import application.model.dto.Clan;
import application.model.dto.NarudzbaStavka;
import application.model.dto.OpremaClana;
import application.model.dto.OpremaKluba;
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

public class ObradiOpremuController extends BaseController implements Initializable{
	@FXML
	private Spinner<Integer> spinnerPristiglo;
	@FXML
	private ListView<Clan> listClanovi;
	@FXML
	private Button btnEvidentiraj;
	
	private Boolean opremaKluba = false;
	private NarudzbaStavka stavkaNarudzbe = null;
	private Boolean donirana = false;
	private Integer idDonacije = null;
	private Integer idSponzora = null;
	private Integer idUgovora = null;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		popuniListu();
	}
	
	public void popuniListu() {
		ObservableList<Clan> listaClanova = OpremaClanaDAO.SELECT_AKTIVNE();
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
			OpremaKluba opremaKluba = new OpremaKluba(null, stavkaNarudzbe.getIdNarudzbe(), stavkaNarudzbe.getIdTipaOpreme(), idSponzora, idUgovora, idDonacije, donirana, stavkaNarudzbe.getVelicina(), "Nova oprema.", true);
			OpremaKlubaDAO.INSERT(opremaKluba, spinnerPristiglo.getValue());
		}
		else {
			if(listClanovi.getSelectionModel().getSelectedItems().size() != spinnerPristiglo.getValue().intValue()) {
				new Alert(AlertType.ERROR, "Broj odabranih ljudi mora biti " + spinnerPristiglo.getValue() + ".", ButtonType.OK).show();
				return false;
			}
			ObservableList<Clan> listaClanova = listClanovi.getSelectionModel().getSelectedItems();
			for(Clan clan : listaClanova) {
				OpremaClana opremaClana = new OpremaClana(null, stavkaNarudzbe.getIdNarudzbe(), stavkaNarudzbe.getIdTipaOpreme(), idSponzora, idUgovora, idDonacije, donirana, stavkaNarudzbe.getVelicina(), clan.getId());
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

	public void setStavkaNarudzbe(NarudzbaStavka stavkaNarudzbe) {
		this.stavkaNarudzbe = stavkaNarudzbe;
	}

	public void setDonirana() {
		this.donirana = true;
	}

	public void setIdDonacije(Integer idDonacije) {
		this.idDonacije = idDonacije;
	}

	public void setIdSponzora(Integer idSponzora) {
		this.idSponzora = idSponzora;
	}

	public void setIdUgovora(Integer idUgovora) {
		this.idUgovora = idUgovora;
	}
	
}
