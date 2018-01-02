package application.gui.sekretar.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class RadSaZaposlenimaController extends BaseController{
	@FXML
	private ComboBox cbFilter;
	@FXML
	private TextField txtFilter;
	@FXML
	private TableView tblZaposleni;
	@FXML
	private TableColumn colIme;
	@FXML
	private TableColumn colImeRoditelja;
	@FXML
	private TableColumn colPrezime;
	@FXML
	private TableColumn colJMB;
	@FXML
	private TableColumn colDatumRodjenja;
	@FXML
	private TableColumn colRadnoMjesto;
	@FXML
	private TableColumn colAktivan;
	@FXML
	private TableColumn colDetaljno;
	@FXML
	private Button btnAzuriraj;
	@FXML
	private Button btnObrisi;
	@FXML
	private RadioButton rbZaposleni;
	@FXML
	private ToggleGroup grpZaposleni;
	@FXML
	private RadioButton rbNezaposleni;
	@FXML
	private RadioButton rbSvi;
	@FXML
	private Button btnDodaj;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
				
	}

}
