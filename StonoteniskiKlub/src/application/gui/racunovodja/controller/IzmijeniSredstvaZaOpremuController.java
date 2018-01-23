package application.gui.racunovodja.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.model.dao.DAOFactory;
import application.model.dto.Narudzba;
import application.model.dto.TransakcijaDTO;
import application.model.dto.TroskoviOpremaDTO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

public class IzmijeniSredstvaZaOpremuController extends TransakcijaIzmijeniDecorater{
	@FXML
	private Label lblDatum;
	
	@FXML
	private Button btnIzmijeni;
	@FXML
	private Button btnOtkazi;
	@FXML
	private Label lblIznos;
	
	@FXML
	private Label lblKM;
	@FXML
	private Label lblNarudzba;
	@FXML
	private Label lblOpis;
	@FXML
	private ScrollPane scrollPane;
	
	@FXML
	private ComboBox<Narudzba> comboBoxNarudzba;
	
	private EvidentiranjeSredstavaZaOpremuController evidentiranjeController;
	private ObservableList<TroskoviOpremaDTO> listaTroskovi;
	private TroskoviOpremaDTO trosak;
	private ObservableList<Narudzba> listaNarudzbe;

	public void setComboBoxNarudzba(ObservableList<Narudzba> lista, Narudzba narudzba) {
		this.comboBoxNarudzba.setItems(lista);
		comboBoxNarudzba.getSelectionModel().select(narudzba);
		
	}
	public void setListaTroskovi(ObservableList<TroskoviOpremaDTO> listaTroskovi) {
		this.listaTroskovi = listaTroskovi;
	}
	public void setTrosak(TroskoviOpremaDTO trosak) {
		this.trosak = trosak;
	}

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		super.setController(new TransakcijaIzmijeniController(txtIznos, datePicker, txtOpis));

	}
	
	public void setComboBoxNarudzba(ObservableList<Narudzba> lista) {
		this.comboBoxNarudzba.setItems(lista);
	}
	public void setEvidentiranjeController(EvidentiranjeSredstavaZaOpremuController evidentiranjeController) {
		this.evidentiranjeController = evidentiranjeController;
	}
	
	public TransakcijaDTO izmijeni() {
		TransakcijaDTO transakcija = super.izmijeni();
		if(transakcija==null)
			return null;
		
		Narudzba narudzba = comboBoxNarudzba.getValue();
		
		String tipTransakcije = DAOFactory.getDAOFactory().getTipTransakcijeDAO().getById(3).getTip();
		TroskoviOpremaDTO trosak1 = new TroskoviOpremaDTO(trosak.getId(), transakcija.getDatum(), transakcija.getIznos().get(), transakcija.getOpis().get(), tipTransakcije, narudzba);
		evidentiranjeController.getListaTroskovi().remove(trosak1);
		evidentiranjeController.getListaTroskovi().add(trosak1);
		DAOFactory.getDAOFactory().getTroskoviOpremaDAO().UPDATE(trosak1,narudzba);
		DAOFactory.getDAOFactory().getNovcanaSredstvaDAO().dodajRashode(trosak1.getIznos().get()-trosak.getIznos().get());
		this.getPrimaryStage().close();
		return trosak1;
	}
	public void otkazi() {
		this.getPrimaryStage().close();
	}
}
