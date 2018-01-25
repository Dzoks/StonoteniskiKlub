package application.gui.racunovodja.controller;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import application.model.dto.TransakcijaDTO;
import application.util.AlertDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TransakcijaIzmijeniController extends IzmijeniController{ //realizacija
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub	
	}
	public TransakcijaIzmijeniController() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TransakcijaIzmijeniController(TextField txtIznos, DatePicker datePicker, TextArea txtOpis) {
		super(txtIznos, datePicker, txtOpis);
		// TODO Auto-generated constructor stub
	}
	@Override
	public TransakcijaDTO izmijeni() {//
		Double iznos = null;
		try {
			iznos = Double.parseDouble(txtIznos.getText());
			if(iznos<0)
				throw new NumberFormatException();
		}catch(NumberFormatException ex) {
			AlertDisplay.showError("Izmjena", "Niste ispravno unijeli informaciju o iznosu.");
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
	
}
