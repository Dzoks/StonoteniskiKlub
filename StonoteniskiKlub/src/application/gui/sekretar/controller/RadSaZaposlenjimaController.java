package application.gui.sekretar.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dto.ZaposleniDTO;
import application.model.dto.ZaposlenjeDTO;
import javafx.fxml.FXML;

import javafx.scene.control.Label;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;

public class RadSaZaposlenjimaController extends BaseController{
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
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buildTable();
	}
	private void buildTable(){
		colDatumOd.setCellValueFactory(new PropertyValueFactory<ZaposlenjeDTO, String>("datumOd"));
		colDatumDo.setCellValueFactory(new PropertyValueFactory<ZaposlenjeDTO, String>("datumDo"));
		colRadnoMjesto.setCellValueFactory(new PropertyValueFactory<ZaposlenjeDTO, String>("tipNaziv"));
		colPlata.setCellValueFactory(new PropertyValueFactory<ZaposlenjeDTO, Double>("plata"));
	}
	public void setZaposleni(ZaposleniDTO zaposleni){
		lblZaposleni.setText(zaposleni.getIme() + " (" + zaposleni.getImeRoditelja() + " ) " + zaposleni.getPrezime());
		tblZaposlenja.setItems(zaposleni.getZaposljenja());
	}
}
