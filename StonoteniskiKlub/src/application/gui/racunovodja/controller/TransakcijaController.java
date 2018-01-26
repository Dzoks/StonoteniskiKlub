package application.gui.racunovodja.controller;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import application.model.dto.TransakcijaDTO;
import application.util.AlertDisplay;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class TransakcijaController extends Controller{
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	public TransakcijaController() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TransakcijaController(TextField txtIznos, DatePicker datePicker, TextArea txtOpis, RadioButton radiobtnSve,
			Button btnPrikazi) {
		super(txtIznos, datePicker, txtOpis, radiobtnSve, btnPrikazi);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public TransakcijaDTO dodaj() { 
		Double iznos = null;
		try {
			iznos = Double.parseDouble(txtIznos.getText());
			if(iznos<0)
				throw new NumberFormatException();
		}catch(NumberFormatException ex) {
			this.obrisiPolja();
			AlertDisplay.showError("Dodavanje", "Niste ispravno unijeli informaciju o iznosu.");
			return null;
		}
		String opis = txtOpis.getText();
		LocalDate localDate = datePicker.getValue();
		Instant instant = null;
		Date datum = null;
		if(localDate!=null) {
			instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
			datum = Date.from(instant);
		}
		TransakcijaDTO transakcija = new TransakcijaDTO(null, datum, iznos, opis, null);
		return transakcija;
	}
	@Override
	public void obrisiPolja() {
		txtIznos.setText("");
		txtOpis.setText("");
		datePicker.setValue(null);
	}

	@Override
		void uspjesnoDodavanje() {
			// TODO Auto-generated method stub
		AlertDisplay.showInformation("Dodavanje", "Uspješno dodavanje.");
		this.obrisiPolja();
		radiobtnSve.fire();
		btnPrikazi.fire();
		}
}
