package application.gui.racunovodja.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.model.dao.DAOFactory;
import application.model.dto.PlataDTO;
import application.model.dto.TransakcijaDTO;
import application.model.dto.ZaposleniDTO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

public class IzmijeniPlatuController extends TransakcijaIzmijeniDecorater {
	@FXML
	private Label lblZaposleni;
	@FXML
	private Label lblDatumUplate;
	
	@FXML
	private Button btnIzmijeni;
	@FXML
	private Button btnOtkazi;
	@FXML
	private Label lblIznos;
	
	@FXML
	private Label lblKM;
	@FXML
	private Label lblOpis;
	@FXML
	private ScrollPane scrollPane;
	
	
	private ObservableList<PlataDTO> listaPlata;
	private ObservableList<ZaposleniDTO> listaZaposlenih;
	private PlataDTO plata;
	private EvidentiranjeIsplacenihPlataController evidentiranjeController;
	public void setEvidentiranjeController(EvidentiranjeIsplacenihPlataController evidentiranjeController) {
		this.evidentiranjeController = evidentiranjeController;
	}
	public IzmijeniPlatuController() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ObservableList<PlataDTO> getListaPlata() {
		return listaPlata;
	}


	public void setListaPlata(ObservableList<PlataDTO> listaPlata) {
		this.listaPlata = listaPlata;
	}
	public PlataDTO getPlata() {
		return plata;
	}
	public void setPlata(PlataDTO plata) {
		this.plata = plata;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		super.setController(new TransakcijaIzmijeniController(txtIznos, datePicker, txtOpis));

	}
	public TransakcijaDTO izmijeni() {
		
		TransakcijaDTO transakcija = super.izmijeni();
		if(transakcija==null)
			return null;
		String tipTransakcije = DAOFactory.getDAOFactory().getTipTransakcijeDAO().getById(2).getTip();
		PlataDTO plata1 = new PlataDTO(plata.getId(), transakcija.getDatum(),transakcija.getIznos().get(), transakcija.getOpis().get(), tipTransakcije,plata.getZaposleni());
		evidentiranjeController.getListaPlata().remove(plata1);
		evidentiranjeController.getListaPlata().add(plata1);
		DAOFactory.getDAOFactory().getPlataDAO().UPDATE(plata1);
		DAOFactory.getDAOFactory().getNovcanaSredstvaDAO().dodajRashode(plata1.getIznos().get()-plata.getIznos().get());
		this.getPrimaryStage().close();
		return plata1;
	}
	public void otkazi() {
		this.getPrimaryStage().close();
	}
}
