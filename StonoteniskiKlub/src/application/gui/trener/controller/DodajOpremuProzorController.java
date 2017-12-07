package application.gui.trener.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.gui.sekretar.controller.DodajNarudzbuProzorController;
import application.model.dao.DistributerOpremeDAO;
import application.model.dao.NarudzbaDAO;
import application.model.dao.OpremaTipDAO;
import application.model.dto.DistributerOpremeDTO;
import application.model.dto.NarudzbaDTO;
import application.model.dto.NarudzbaStavkaDTO;
import application.model.dto.OpremaTipDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
	@FXML
	private TableView<NarudzbaStavkaDTO> tblNarudzbe;
	@FXML
	private TableColumn<NarudzbaStavkaDTO, Integer> id;
	@FXML
	private TableColumn<NarudzbaStavkaDTO, String> tipOpreme;
	@FXML
	private TableColumn<NarudzbaStavkaDTO, String> proizvodjacOpreme;
	@FXML
	private TableColumn<NarudzbaStavkaDTO, String> modelOpreme;
	@FXML
	private TableColumn<NarudzbaStavkaDTO, Integer> kolicina;
	@FXML
	private TableColumn<NarudzbaStavkaDTO, String> velicina;
	@FXML
	private TableColumn<NarudzbaStavkaDTO, Double> cijena;
	@FXML
	private TableColumn<NarudzbaStavkaDTO, Boolean> obradjeno;
	
	private Boolean opremaKluba = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ucitajComboBoxeve();
		checkBoxDonirana.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		        if(checkBoxDonirana.isSelected()) {
		        	checkBoxSelektovan();
		        }
		        else {
		        	checkBoxNijeSelektovan();
		        }
		    }
		});
		checkBoxDonirana.setSelected(false);
		checkBoxNijeSelektovan();
	}
	
	public void ucitajComboBoxeve() {
		ObservableList<OpremaTipDTO> listaTiOpreme = OpremaTipDAO.SELECT_ALL();
		comboBoxTip.setItems(listaTiOpreme);
		comboBoxTip.getSelectionModel().selectFirst();
		
		//ucitati donacije i clanove
		
		ObservableList<NarudzbaDTO> listaNarudzbi = NarudzbaDAO.SELECT_OBRADJENE();
		comboBoxNarudzba.setItems(listaNarudzbi);
		comboBoxNarudzba.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<NarudzbaDTO>() {

			@Override
			public void changed(ObservableValue<? extends NarudzbaDTO> observable, NarudzbaDTO oldValue, NarudzbaDTO newValue) {
				ObservableList<NarudzbaStavkaDTO> listaStavki = newValue.getListaStavki();
			}
		});
		
	}
	
	public void checkBoxSelektovan() {
		comboBoxNarudzba.setDisable(true);
    	tblNarudzbe.setDisable(true);
    	comboBoxTip.setDisable(false);
    	comboBoxDonacija.setDisable(false);
    	if(opremaKluba) {
    		txtOpis.setDisable(false);
        	txtVelicina.setDisable(true);
        	comboBoxClan.setDisable(true);
    	}
    	else {
    		txtOpis.setDisable(true);
        	txtVelicina.setDisable(false);
        	comboBoxClan.setDisable(false);
    	}
  
    	btnDodajTipOpreme.setDisable(false);
	}
	
	public void checkBoxNijeSelektovan() {
		comboBoxTip.setDisable(true);
    	comboBoxDonacija.setDisable(true);
    	txtOpis.setDisable(true);
    	btnDodajTipOpreme.setDisable(true);
    	txtVelicina.setDisable(true);
    	comboBoxClan.setDisable(true);
    	comboBoxNarudzba.setDisable(false);
    	tblNarudzbe.setDisable(false);
	}
	
	public void idiNaDodajTipOpreme() {
		Stage noviStage = new Stage();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/trener/view/DodajTipOpremeProzor.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,267,185);
			DodajTipOpremeProzorController controller = loader.<DodajTipOpremeProzorController>getController();
			controller.setPrimaryStage(noviStage);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa opremom");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			noviStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setOpremaKluba() {
		opremaKluba = true;
	}
}
