package application.gui.racunovodja.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.model.dao.DAOFactory;
import application.model.dto.ClanDTO;
import application.model.dto.ClanarinaDTO;
import application.model.dto.TransakcijaDTO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class IzmijeniClanarinuController extends TransakcijaIzmijeniDecorater{
	@FXML
	private Label lblClan;
	@FXML
	private Label lblMjesec;
	@FXML
	private Label lblGodina;
	@FXML
	private Label lblGodinaUplate;
	@FXML
	private Spinner<Integer> spinnerMjesec;
	@FXML
	private Spinner<Integer> spinnerGodina;
	
	@FXML
	private Button btnIzmijeni;
	@FXML
	private Button btnOtkazi;
	@FXML
	private Label lblIznos;
	
	private ClanDTO clan = new ClanDTO();
	private ClanarinaDTO clanarina;
	private EvidentiranjeClanarinaController evidentiranjeController;
	
	public EvidentiranjeClanarinaController getEvidentiranjeController() {
		return evidentiranjeController;
	}
	public void setEvidentiranjeController(EvidentiranjeClanarinaController evidentiranjeController) {
		this.evidentiranjeController = evidentiranjeController;
	}
	public ClanarinaDTO getClanarina() {
		return clanarina;
	}
	public void setClan(String ime, String prezime, int id) {
		this.clan.setIme(ime);
		this.clan.setPrezime(prezime);
		this.clan.setId(id);
	}
	public void setClanarina(ClanarinaDTO clanarina) {
		this.clanarina = clanarina;
	}
	
	public void setSpinnerMjesec(int spinnerMjesec) {
		this.spinnerMjesec.getValueFactory().setValue(spinnerMjesec);
	}
	public void setSpinnerGodina(int spinnerGodina) {
		this.spinnerGodina.getValueFactory().setValue(spinnerGodina);
	}

	public void setListaClanova(ObservableList<ClanDTO> listaClanova) {
		this.listaClanova = listaClanova;
	}
	@FXML
	private Label lblKM;
	@FXML
	private Label lblOpis;
	@FXML
	private ScrollPane scrollPane;
	
	
	
	private ObservableList<ClanDTO> listaClanova;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.setController(new TransakcijaIzmijeniController(txtIznos, datePicker, txtOpis));
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 1);
		SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(2010, 2018, 2014);
		spinnerMjesec.setValueFactory(valueFactory);
		spinnerGodina.setValueFactory(valueFactory1);
	}
	
	
	public TransakcijaDTO izmijeni() {
		TransakcijaDTO transakcija = super.izmijeni();
		if(transakcija==null)
			return null;
		
		Integer mjesec = spinnerMjesec.getValue();
		Integer godina = spinnerGodina.getValue();
		//ClanDTO clan = comboBoxClan.getValue();
		
		String tipTransakcije = DAOFactory.getDAOFactory().getTipTransakcijeDAO().getById(1).getTip();
		ClanarinaDTO clanarina1 = new ClanarinaDTO(clanarina.getId(), transakcija.getDatum(), transakcija.getIznos().doubleValue(), transakcija.getOpis().get(), tipTransakcije, mjesec, godina, clan.getIme(), clan.getPrezime(),clan.getId());
		evidentiranjeController.getListaClanarina().remove(clanarina1);
		evidentiranjeController.getListaClanarina().add(clanarina1);
		DAOFactory.getDAOFactory().getClanarinaDAO().UPDATE(clanarina1);
		DAOFactory.getDAOFactory().getNovcanaSredstvaDAO().dodajPrihode(clanarina1.getIznos().get()-clanarina.getIznos().get());
		this.getPrimaryStage().close();
		return clanarina1;
	}

	public void otkazi() {
		this.getPrimaryStage().close();
	}
	
}
