package application.gui.racunovodja.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import application.model.dao.DAOFactory;
import application.model.dto.TransakcijaDTO;
import application.model.dto.TroskoviTurnirDTO;
import application.model.dto.TurnirDTO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

public class IzmijeniSredstvaZaTurnirController extends TransakcijaIzmijeniDecorater{
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
	private Label lblTurnir;
	@FXML
	private Label lblOpis;
	@FXML
	private ScrollPane scrollPane;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		super.setController(new TransakcijaIzmijeniController(txtIznos, datePicker, txtOpis));
	}
	private EvidentiranjeSredstavaZaTurnireController evidentiranjeController;
	private ObservableList<TroskoviTurnirDTO> listaTroskovi;
	private TroskoviTurnirDTO trosak;
	private ObservableList<TurnirDTO> listaTurniri;
	
	public void setTxtIznos(String txtIznos) {
		this.txtIznos.setText(txtIznos);;
	}
	public void setTxtOpis(String txtOpis) {
		this.txtOpis.setText(txtOpis);;
	}

	public void setListaTroskovi(ObservableList<TroskoviTurnirDTO> listaTroskovi) {
		this.listaTroskovi = listaTroskovi;
	}
	public void setTrosak(TroskoviTurnirDTO trosak) {
		this.trosak = trosak;
	}


	public void setDatePicker(Date input) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(input);
		LocalDate date = LocalDate.of(cal.get(Calendar.YEAR),
		        cal.get(Calendar.MONTH) + 1,
		        cal.get(Calendar.DAY_OF_MONTH));
		this.datePicker.setValue(date);
	}
	
	public void setEvidentiranjeController(EvidentiranjeSredstavaZaTurnireController evidentiranjeController) {
		this.evidentiranjeController = evidentiranjeController;
	}
	
public TransakcijaDTO izmijeni() {
		
		TransakcijaDTO transakcija = super.izmijeni();
		if(transakcija==null)
			return null;
				
		String tipTransakcije = DAOFactory.getDAOFactory().getTipTransakcijeDAO().getById(5).getTip();
		TroskoviTurnirDTO trosak1 = new TroskoviTurnirDTO(trosak.getId(), transakcija.getDatum(), transakcija.getIznos().get(), transakcija.getOpis().get(), tipTransakcije, trosak.getTurnir());
		evidentiranjeController.getListaTroskovi().remove(trosak1);
		evidentiranjeController.getListaTroskovi().add(trosak1);
		DAOFactory.getDAOFactory().getTroskoviTurnirDAO().UPDATE(trosak1);
		DAOFactory.getDAOFactory().getNovcanaSredstvaDAO().dodajRashode(trosak1.getIznos().get()-trosak.getIznos().get());

		this.getPrimaryStage().close();
		return trosak1;
	}
	public void otkazi() {
		this.getPrimaryStage().close();
	}
}
