package application.gui.racunovodja.controller;

import application.model.dto.TransakcijaDTO;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public abstract class TransakcijaDecorater extends Controller{ //delegacija
	private Controller controller = new TransakcijaController();
	
	public Controller getController() {
		return controller;
	}
	public void setController(Controller controller) {
		this.controller = controller;
	}
	public void setTxtIznos(TextField txtIznos) {
		this.controller.setTxtIznos(txtIznos); 
	}
	public void setDatePicker(DatePicker datePicker) {
		this.controller.setDatePicker(datePicker);
	}
	public void setTxtOpis(TextArea txtOpis) {
		this.controller.setTxtOpis(txtOpis);
	}
	public void setRadiobtnSve(RadioButton radiobtnSve) {
		this.controller.setRadiobtnSve(radiobtnSve);
	}
	public void setBtnPrikazi(Button btnPrikazi) {
		this.controller.setBtnPrikazi(btnPrikazi);
	}
	public void metoda() {
		controller.metoda();
	}
	
	@Override
	public TransakcijaDTO dodaj() {
		return controller.dodaj();
	}
	
	@Override
	public void obrisiPolja() {
		controller.obrisiPolja();
	}
	@Override
	public void uspjesnoDodavanje() {
		controller.uspjesnoDodavanje();
	}

}
