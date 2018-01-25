package application.gui.racunovodja.controller;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import application.gui.controller.BaseController;
import application.model.dto.TransakcijaDTO;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public abstract class IzmijeniController extends BaseController{
	@FXML
	protected TextField txtIznos;
	@FXML
	protected DatePicker datePicker;
	@FXML
	protected TextArea txtOpis;
	
	
	public IzmijeniController(TextField txtIznos, DatePicker datePicker, TextArea txtOpis) {
		super();
		this.txtIznos = txtIznos;
		this.datePicker = datePicker;
		this.txtOpis = txtOpis;
	}
	public IzmijeniController() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void setTxtIznos(String txtIznos) {
		this.txtIznos.setText(txtIznos);
	}
	public void setTxtIznos(TextField txtIznos) {
		this.txtIznos = txtIznos;
	}
	public void setDatePicker(DatePicker datePicker) {
		this.datePicker = datePicker;
	}
	public void setTxtOpis(TextArea txtOpis) {
		this.txtOpis = txtOpis;
	}
	public void setTxtOpis(String txtOpis) {
		this.txtOpis.setText(txtOpis);
	}
	public void setDatePicker(Date input) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(input);
		LocalDate date = LocalDate.of(cal.get(Calendar.YEAR),
		        cal.get(Calendar.MONTH) + 1,
		        cal.get(Calendar.DAY_OF_MONTH));
		this.datePicker.setValue(date);
	}
	public abstract TransakcijaDTO izmijeni();
}
