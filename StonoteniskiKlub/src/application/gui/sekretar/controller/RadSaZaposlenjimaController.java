package application.gui.sekretar.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.ZaposleniDTO;
import application.model.dto.ZaposlenjeDTO;
import application.util.AlertDisplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RadSaZaposlenjimaController extends BaseController {
	@FXML
	private Label lblZaposleni;
	@FXML
	private TableView<ZaposlenjeDTO> tblZaposlenja;
	@FXML
	private TableColumn<ZaposlenjeDTO, String> colDatumOd;
	@FXML
	private TableColumn<ZaposlenjeDTO, String> colDatumDo;
	@FXML
	private TableColumn<ZaposlenjeDTO, String> colRadnoMjesto;
	@FXML
	private TableColumn<ZaposlenjeDTO, Double> colPlata;
	@FXML
	private DatePicker dpDatum;
	@FXML
	private Button btnZakljuci;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buildTable();
		bindDisable();
	}

	private void buildTable() {
		colDatumOd.setCellValueFactory(new PropertyValueFactory<ZaposlenjeDTO, String>("datumOd"));
		colDatumDo.setCellValueFactory(new PropertyValueFactory<ZaposlenjeDTO, String>("datumDo"));
		colRadnoMjesto.setCellValueFactory(new PropertyValueFactory<ZaposlenjeDTO, String>("tipNaziv"));
		colPlata.setCellValueFactory(new PropertyValueFactory<ZaposlenjeDTO, Double>("plata"));
	}

	public void setZaposleni(ZaposleniDTO zaposleni) {
		this.zaposleni = zaposleni;
		lblZaposleni.setText(zaposleni.getIme() + " (" + zaposleni.getImeRoditelja() + " ) " + zaposleni.getPrezime());
		tblZaposlenja.setItems(zaposleni.getZaposljenja());
	}

	@FXML
	public void zakljuci(ActionEvent event) {
		ZaposlenjeDTO zaposlenje = tblZaposlenja.getSelectionModel().getSelectedItem();
		if(zaposlenje != null){
			if(zaposlenje.getDatumDo() != null){
				AlertDisplay.showError("Ažuriranje", "Zaposlenje je već zaključeno");
			} else if(zaposlenje.getDatumOd().compareTo(java.sql.Date.valueOf(dpDatum.getValue())) >= 0){
				AlertDisplay.showError("Ažuriranje", "Datum zaključenja mora biti nakon datuma stupanja u radni odnos");
			} else{
				zaposlenje.setDatumDo(java.sql.Date.valueOf(dpDatum.getValue()));
				DAOFactory.getDAOFactory().getZaposlenjeDAO().zakljuci(zaposleni, zaposlenje);
				tblZaposlenja.refresh();
				AlertDisplay.showInformation("Ažuriranje", "Zaposlenje zaključeno");
			}
		}
	}

	private void bindDisable() {
		btnZakljuci.disableProperty().bind(
				tblZaposlenja.getSelectionModel().selectedItemProperty().isNull().or(dpDatum.valueProperty().isNull()));
	}
	private ZaposleniDTO zaposleni;
}
