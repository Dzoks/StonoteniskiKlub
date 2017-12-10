package application.gui.trener.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.NarudzbaDAO;
import application.model.dao.OpremaClanaDAO;
import application.model.dao.OpremaKlubaDAO;
import application.model.dao.OpremaTipDAO;
import application.model.dto.Clan;
import application.model.dto.Narudzba;
import application.model.dto.NarudzbaStavka;
import application.model.dto.OpremaClana;
import application.model.dto.OpremaKluba;
import application.model.dto.OpremaTip;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import javafx.scene.control.CheckBox;

public class DodajOpremuController extends BaseController implements Initializable{
	@FXML
	private CheckBox checkBoxDonirana;
	@FXML
	private ComboBox<OpremaTip> comboBoxTip;
	@FXML
	private Button btnDodajTipOpreme;
	@FXML
	private ComboBox comboBoxDonacija;
	@FXML
	private TextArea txtOpis;
	@FXML
	private TextField txtVelicina;
	@FXML
	private ComboBox<Clan> comboBoxClan;
	@FXML
	private ComboBox<Narudzba> comboBoxNarudzba;
	@FXML
	private Button btnDodaj;
	@FXML
	private TableView<NarudzbaStavka> tblNarudzbe;
	@FXML
	private TableColumn<NarudzbaStavka, Integer> id;
	@FXML
	private TableColumn<NarudzbaStavka, String> tipOpreme;
	@FXML
	private TableColumn<NarudzbaStavka, String> proizvodjacOpreme;
	@FXML
	private TableColumn<NarudzbaStavka, String> modelOpreme;
	@FXML
	private TableColumn<NarudzbaStavka, Integer> kolicina;
	@FXML
	private TableColumn<NarudzbaStavka, String> velicina;
	@FXML
	private TableColumn<NarudzbaStavka, Double> cijena;
	@FXML
	private TableColumn<NarudzbaStavka, String> obradjeno;
	
	private Boolean opremaKluba = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnDodaj.setDisable(true);
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
	
	public void popuniTabelu(ObservableList<NarudzbaStavka> listaStavkiNarudzbe) {
		id.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, Integer>("idNarudzbe"));
		tipOpreme.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, String>("tipOpreme"));
		proizvodjacOpreme.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, String>("tipProizvodjac"));
		modelOpreme.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, String>("tipModel"));
		kolicina.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, Integer>("kolicina"));
		velicina.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, String>("velicina"));
		cijena.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, Double>("cijena"));
		obradjeno.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, String>("status"));
		
		tblNarudzbe.setItems(listaStavkiNarudzbe);
	}
	
	public void ucitajComboBoxeve() {
		ObservableList<OpremaTip> listaTiOpreme = OpremaTipDAO.SELECT_ALL();
		comboBoxTip.setItems(listaTiOpreme);
		
		//ucitati donacije
		
		ObservableList<Clan> listaClanova = OpremaClanaDAO.SELECT_AKTIVNE();
		comboBoxClan.setItems(listaClanova);
		comboBoxClan.getSelectionModel().selectFirst();
		
		ObservableList<Narudzba> listaNarudzbi = NarudzbaDAO.SELECT_NEOBRADJENE(opremaKluba);
		comboBoxNarudzba.setItems(listaNarudzbi);
		comboBoxNarudzba.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Narudzba>() {

			@Override
			public void changed(ObservableValue<? extends Narudzba> observable, Narudzba oldValue, Narudzba newValue) {
				if(newValue != null) {
					ObservableList<NarudzbaStavka> listaStavkiNarudzbe = newValue.getListaStavki();
					popuniTabelu(listaStavkiNarudzbe);
				}
			}
		});
		
		comboBoxTip.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<OpremaTip>() {

			@Override
			public void changed(ObservableValue<? extends OpremaTip> observable, OpremaTip oldValue, OpremaTip newValue) {
				if(newValue != null) {
					if(newValue.getImaVelicinu()) {
						txtVelicina.setDisable(false);
					}
					else {
						txtVelicina.setDisable(true);
					}
					disableDodajDugme();
				}
			}
		});
		
		comboBoxTip.getSelectionModel().selectFirst();
	}
	
	public void checkBoxSelektovan() {
		comboBoxNarudzba.setDisable(true);
    	tblNarudzbe.setDisable(true);
    	comboBoxTip.setDisable(false);
    	comboBoxDonacija.setDisable(false);
    	if(opremaKluba) {
    		txtOpis.setDisable(false);
        	comboBoxClan.setDisable(true);
    	}
    	else {
    		txtOpis.setDisable(true);
        	comboBoxClan.setDisable(false);
    	}
    	if(comboBoxTip.getSelectionModel().getSelectedItem().getImaVelicinu()) {
    		txtVelicina.setDisable(false);
    	}
    	else {
    		txtVelicina.setDisable(true);
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
	
	public void disableDodajDugme() {
		if(checkBoxDonirana.isSelected()) {
			if(opremaKluba) {
				if(!comboBoxTip.getSelectionModel().getSelectedItem().getImaVelicinu()) {
					btnDodaj.disableProperty().bind(txtOpis.textProperty().isEmpty());
				}
				else {
					btnDodaj.disableProperty().bind(txtOpis.textProperty().isEmpty().or(txtVelicina.textProperty().isEmpty()));
				}
			}
			else {
				if(comboBoxTip.getSelectionModel().getSelectedItem().getImaVelicinu()) {
					btnDodaj.disableProperty().bind(txtVelicina.textProperty().isEmpty());
				}
				else {
					btnDodaj.setDisable(false);
				}
			}
		}
		else {
			tblNarudzbe.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			    if (newSelection != null && !newSelection.getObradjeno()) {
			        btnDodaj.setDisable(false);
			    }
			    else {
			    	btnDodaj.setDisable(true);
			    }
			});
		}
	}
	
	public void idiNaDodajTipOpreme() {
		Stage noviStage = new Stage();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/trener/view/DodajTipOpremeView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,267,207);
			DodajTipOpremeController controller = loader.<DodajTipOpremeController>getController();
			controller.setPrimaryStage(noviStage);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa opremom");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			noviStage.showAndWait();
			if("YES".equals(controller.getPovratnaVrijednost())) {
				OpremaTip noviTipOpreme = controller.vratiTipOpreme();
				OpremaTipDAO.INSERT(noviTipOpreme);
				ucitajComboBoxeve();
				comboBoxTip.getSelectionModel().selectLast();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void idiNaObradiOpremu() {
		if(!checkBoxDonirana.isSelected()) {
			Stage noviStage = new Stage();
			
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/trener/view/ObradiOpremuView.fxml"));
				AnchorPane root = (AnchorPane) loader.load();
				Scene scene = new Scene(root,366,396);
				ObradiOpremuController controller = loader.<ObradiOpremuController>getController();
				controller.setPrimaryStage(noviStage);
				noviStage.setScene(scene);
				noviStage.setResizable(false);
				noviStage.setTitle("Stonoteniski klub - rad sa opremom");
				noviStage.initModality(Modality.APPLICATION_MODAL);
				
				if(opremaKluba) {
					controller.setOpremaKluba();
					controller.disableParametre();
				}
				if(checkBoxDonirana.isSelected()) {
					controller.setDonirana();
					//controller.setIdDonacije(idDonacije);
				}
				
				controller.setStavkaNarudzbe(tblNarudzbe.getSelectionModel().selectedItemProperty().get());
				controller.postaviSpiner();
				controller.disableDugmeEvidentiraj();
				noviStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			          public void handle(WindowEvent we) {
			        	  we.consume();
			              Alert alert = new Alert(AlertType.CONFIRMATION, "Da li zelite da zapamtite dodavanje?", ButtonType.YES, ButtonType.NO);
			              Optional<ButtonType> rezultat = alert.showAndWait();
			              if(ButtonType.YES.equals(rezultat.get())) {
			            	  if(controller.ubaciUBazu()) {
			            		  noviStage.close();
			            	  }
			              }
			              else if(ButtonType.NO.equals(rezultat.get())) {
			            	  noviStage.close();
			              }
			          }});
				noviStage.showAndWait();
				ucitajComboBoxeve();
				tblNarudzbe.setItems(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			if(opremaKluba) {
				OpremaKluba opremaKluba = null;
				if(comboBoxTip.getSelectionModel().getSelectedItem().getImaVelicinu()) {
					// dodati idDonacije opremaKluba = new OpremaKluba(null, null, comboBoxTip.getSelectionModel().getSelectedItem().getId(), idDonacije, true, txtVelicina.getText(), txtOpis.getText(), true);
					
				}
				else {
					// dodati idDonacije opremaKluba = new OpremaKluba(null, null, comboBoxTip.getSelectionModel().getSelectedItem().getId(), idDonacije, true, "-", txtOpis.getText(), true);
				}
				OpremaKlubaDAO.INSERT(opremaKluba, 1);
			}
			else {
				OpremaClana opremaClana = null;
				if(comboBoxTip.getSelectionModel().getSelectedItem().getImaVelicinu()) {
					// dodati idDonacije opremaClana = new OpremaClana(null, null, comboBoxTip.getSelectionModel().getSelectedItem().getId(), idDonacije, true, txtVelicina.getText(), comboBoxClan.getSelectionModel().getSelectedItem().getId());
					
				}
				else {
					// dodati idDonacije opremaClana = new OpremaClana(null, null, comboBoxTip.getSelectionModel().getSelectedItem().getId(), idDonacije, true, "-", comboBoxClan.getSelectionModel().getSelectedItem().getId());
				}
				OpremaClanaDAO.INSERT(opremaClana);
			}
		}
	}
	
	public void setOpremaKluba() {
		opremaKluba = true;
	}
}
