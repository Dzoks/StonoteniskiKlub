package application.gui.trener.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.ClanDAO;
import application.model.dto.ClanDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class IzmjenaClanaController extends BaseController implements Initializable{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtIme;

    @FXML
    private TextField txtPrezime;

    @FXML
    private TextField txtImeRoditelja;

    @FXML
    private TextField txtJMB;

    @FXML
    private RadioButton rbMusko;

    @FXML
    private RadioButton rbZensko;

    @FXML
    private DatePicker dpDatumRodjenja;

    @FXML
    private Button btnUclani;

    @FXML
    private ImageView ivSlika;

    @FXML
    private ComboBox<String> cbTelefon;

    @FXML
    public void dodajFotografiju(ActionEvent event) {
    	
    }

    @FXML
	public
    void izlaz() {
    	primaryStage.close();
    }

    @FXML
    public void izvrsiUclanjivanje(ActionEvent event) {

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
}

