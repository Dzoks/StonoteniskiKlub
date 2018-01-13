package application.gui.racunovodja.controller;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dto.TransakcijaDTO;
import javafx.fxml.FXML;

public abstract class Controller extends BaseController{
	
	@FXML
	protected TextField txtIznos;
	@FXML
	protected DatePicker datePicker;
	@FXML
	protected TextArea txtOpis;
	@FXML
	protected RadioButton radiobtnSve;
	@FXML
	protected Button btnPrikazi;
	public Controller() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TextField getTxtIznos() {
		return txtIznos;
	}
	public void setTxtIznos(TextField txtIznos) {
		this.txtIznos = txtIznos;
	}
	public DatePicker getDatePicker() {
		return datePicker;
	}
	public void setDatePicker(DatePicker datePicker) {
		this.datePicker = datePicker;
	}
	public TextArea getTxtOpis() {
		return txtOpis;
	}
	public void setTxtOpis(TextArea txtOpis) {
		this.txtOpis = txtOpis;
	}
	public RadioButton getRadiobtnSve() {
		return radiobtnSve;
	}
	public void setRadiobtnSve(RadioButton radiobtnSve) {
		this.radiobtnSve = radiobtnSve;
	}
	public Button getBtnPrikazi() {
		return btnPrikazi;
	}
	public void setBtnPrikazi(Button btnPrikazi) {
		this.btnPrikazi = btnPrikazi;
	}
	public Controller(TextField txtIznos, DatePicker datePicker, TextArea txtOpis, RadioButton radiobtnSve,
			Button btnPrikazi) {
		super();
		this.txtIznos = txtIznos;
		this.datePicker = datePicker;
		this.txtOpis = txtOpis;
		this.radiobtnSve = radiobtnSve;
		this.btnPrikazi = btnPrikazi;
	}
	abstract void metoda();
	abstract TransakcijaDTO dodaj();
	abstract void obrisiPolja();
	abstract void uspjesnoDodavanje();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean poljaPrazna() {
		return txtIznos.getText().isEmpty() || datePicker.getValue()==null;
	}
}
