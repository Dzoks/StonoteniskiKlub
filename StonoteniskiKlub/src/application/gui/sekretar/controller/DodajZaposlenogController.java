package application.gui.sekretar.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.ZaposleniTipDTO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class DodajZaposlenogController extends BaseController{
	@FXML
	private TextField txtIme;
	@FXML
	private TextField txtPrezime;
	@FXML
	private TextField txtImeRoditelja;
	@FXML
	private TextField txtJMB;
	@FXML
	private TextField tfBrojTelefona;
	@FXML
	private Button btnDodajBrojTelefona;
	@FXML
	private DatePicker dpDatumRodjenja;
	@FXML
	private RadioButton rbMuskiPol;
	@FXML
	private ToggleGroup groupPol;
	@FXML
	private RadioButton rbZenskiPol;
	@FXML
	private Button btnDodajFotografiju;
	@FXML
	private DatePicker dpZaposlenOd;
	@FXML
	private DatePicker dpZaposlenDo;
	@FXML
	private ComboBox<ZaposleniTipDTO> cmbRadnoMjesto;
	@FXML
	private Button btnSacuvajPodatke;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setUp();
	}
	private void setUp(){
		ObservableList<ZaposleniTipDTO> radnaMjesta = DAOFactory.getDAOFactory().getZaposleniTipDAO().selectAll();
		cmbRadnoMjesto.setItems(radnaMjesta);
		cmbRadnoMjesto.getSelectionModel().selectFirst();
	}
}
