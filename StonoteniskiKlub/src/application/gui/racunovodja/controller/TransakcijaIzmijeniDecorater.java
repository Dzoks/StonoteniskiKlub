package application.gui.racunovodja.controller;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import application.model.dto.TransakcijaDTO;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TransakcijaIzmijeniDecorater extends IzmijeniController{//delegacija
	
	private IzmijeniController controller = new TransakcijaIzmijeniController();
	
	public IzmijeniController getController() {
		return controller;
	}
	public void setController(IzmijeniController controller) {
		this.controller = controller;
	}
	public TransakcijaIzmijeniDecorater() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TransakcijaIzmijeniDecorater(TextField txtIznos, DatePicker datePicker, TextArea txtOpis,
			IzmijeniController controller) {
		super(txtIznos, datePicker, txtOpis);
		this.controller = controller;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
	@Override
	public TransakcijaDTO izmijeni() {
		// TODO Auto-generated method stub
		return controller.izmijeni();
	}
	@Override
		public void setDatePicker(DatePicker datePicker) {
			// TODO Auto-generated method stub
			controller.setDatePicker(datePicker);
		}
	@Override
		public void setTxtIznos(TextField txtIznos) {
			// TODO Auto-generated method stub
			controller.setTxtIznos(txtIznos);
		}
	@Override
		public void setTxtOpis(TextArea txtOpis) {
			// TODO Auto-generated method stub
			controller.setTxtOpis(txtOpis);
		}
	
	@Override
	public void setTxtIznos(String txtIznos) {
		 controller.setTxtIznos(txtIznos);
	}
	@Override
	public void setTxtOpis(String txtOpis) {
		controller.setTxtOpis(txtOpis);
	}
	@Override
	public void setDatePicker(Date input) {
		controller.setDatePicker(input);
	}
	
}
