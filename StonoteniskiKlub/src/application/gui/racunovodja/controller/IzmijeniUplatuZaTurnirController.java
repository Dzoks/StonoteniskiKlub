package application.gui.racunovodja.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.model.dao.DAOFactory;
import application.model.dto.TransakcijaDTO;
import application.model.dto.UcesnikPrijavaDTO;
import application.model.dto.UplataZaTurnirDTO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

public class IzmijeniUplatuZaTurnirController extends TransakcijaIzmijeniDecorater{
	@FXML
	private Label lblUcesnik;
	@FXML
	private Label lblGodinaUplate;
	@FXML
	private Button btnIzmijeni;
	@FXML
	private Button btnOtkazi;
	@FXML
	private Label lblIznos;
	@FXML
	private Label lblKM;
	@FXML
	private Label lblTurnir;

	@FXML
	private Label lblOpis;
	@FXML
	private ScrollPane scrollPane;
	private UcesnikPrijavaDTO ucesnik;
	
	public void setUcesnik(UcesnikPrijavaDTO ucesnik) {
		this.ucesnik = ucesnik;
	}
	private EvidentiranjeUplataZaTurnirController evidentiranjeController;
	private ObservableList<UplataZaTurnirDTO> listaUplate;
	private UplataZaTurnirDTO uplata;
	private ObservableList<UcesnikPrijavaDTO> listaUcesnika;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.setController(new TransakcijaIzmijeniController(txtIznos, datePicker, txtOpis));

	}
	public void setTxtIznos(String txtIznos) {
		this.txtIznos.setText(txtIznos);;
	}
	public void setTxtOpis(String txtOpis) {
		this.txtOpis.setText(txtOpis);;
	}
	
	public void setListaUplate(ObservableList<UplataZaTurnirDTO> listaUplate) {
		this.listaUplate = listaUplate;
	}
	public void setUplata(UplataZaTurnirDTO uplata) {
		this.uplata = uplata;
	}

	
	public void setEvidentiranjeController(EvidentiranjeUplataZaTurnirController evidentiranjeController) {
		this.evidentiranjeController = evidentiranjeController;
	}
	
	public TransakcijaDTO izmijeni() {
		
	TransakcijaDTO transakcija = super.izmijeni();
	if(transakcija==null)
		return null;
				
		String tipTransakcije = DAOFactory.getDAOFactory().getTipTransakcijeDAO().getById(4).getTip();
		UplataZaTurnirDTO trosak1 = new UplataZaTurnirDTO(uplata.getId(),transakcija.getDatum(), transakcija.getIznos().get(), transakcija.getOpis().get(), tipTransakcije,ucesnik);
		evidentiranjeController.getListaUplata().remove(trosak1);
		evidentiranjeController.getListaUplata().add(trosak1);
		DAOFactory.getDAOFactory().getUplataZaTurnirDAO().UPDATE(trosak1);
		DAOFactory.getDAOFactory().getNovcanaSredstvaDAO().dodajPrihode(trosak1.getIznos().get()-uplata.getIznos().get());

		this.getPrimaryStage().close();
		return trosak1;
	}
	public void otkazi() {
		this.getPrimaryStage().close();
	}
}
