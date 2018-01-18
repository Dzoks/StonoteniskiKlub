package application.gui.trener.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dto.TreningDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

public class DodajTreningController extends BaseController{
	@FXML
	private DatePicker dateDatum;
	@FXML
	private TextArea areaOpis;
	@FXML
	private Button btnAdd;
	@FXML
	private Button btnCancel;
	
	private TreningDTO trening;
	
	public TreningDTO getTrening() {
		return trening;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnAdd.disableProperty().bind(areaOpis.textProperty().isEmpty().or(dateDatum.valueProperty().isNull()));
		btnCancel.setOnAction(e->{
			primaryStage.close();
		});
		btnAdd.setOnAction(e->{
			LocalDate datum=dateDatum.getValue();
			if (datum.isAfter(LocalDate.now())) {
				Alert error=new Alert(AlertType.ERROR);
				error.setTitle("Greška");
				error.setContentText("Datum treninga ne smije biti u budućnosti");
				error.setHeaderText("Greška u datumu");
				error.showAndWait();
				return;
			}
			trening=new TreningDTO();
			trening.setDatum(datum);
			trening.setOpis(areaOpis.getText());
			primaryStage.close();
		});
	}

	
}
