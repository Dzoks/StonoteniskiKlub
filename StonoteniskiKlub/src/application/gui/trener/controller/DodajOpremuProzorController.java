package application.gui.trener.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dto.NarudzbaDTO;
import application.model.dto.OpremaTipDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;

import javafx.scene.control.TextArea;

import javafx.scene.control.CheckBox;

public class DodajOpremuProzorController extends BaseController implements Initializable{
	@FXML
	private CheckBox checkBoxDonirana;
	@FXML
	private ComboBox<OpremaTipDTO> comboBoxTip;
	@FXML
	private Button btnDodajTipOpreme;
	@FXML
	private ComboBox comboBoxDonacija;
	@FXML
	private TextArea txtOpis;
	@FXML
	private TextField txtVelicina;
	@FXML
	private ComboBox comboBoxClan;
	@FXML
	private ComboBox<NarudzbaDTO> comboBoxNarudzba;
	@FXML
	private Button btnDodaj;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void idiNaDodajTipOpreme() {
		Stage noviStage = new Stage();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/trener/view/DodajTipOpremeProzor.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,267,185);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa opremom");
			noviStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
