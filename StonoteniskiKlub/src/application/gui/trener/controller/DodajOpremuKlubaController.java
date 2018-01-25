package application.gui.trener.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.DonacijaDTO;
import application.model.dto.DonacijaStavka;
import application.model.dto.Narudzba;
import application.model.dto.NarudzbaStavka;
import application.model.dto.OpremaKluba;
import application.util.AlertDisplay;
import application.util.ErrorLogger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class DodajOpremuKlubaController extends BaseController implements Initializable{
	@FXML
	private CheckBox checkBoxDonirana;
	@FXML
	private ComboBox<DonacijaDTO> comboBoxDonacija;
	@FXML
	private TextArea txtOpis;
	@FXML
	private Label lblTipOpreme;
	@FXML
	private Label lblKolicina;
	@FXML
	private TextField txtVelicina;
	@FXML
	private Spinner<Integer> spinnerKolicina;
	@FXML
	private ComboBox<Narudzba> comboBoxNarudzba;
	@FXML
	private Button btnDodaj;
	@FXML
	private Button btnDodajStavku;
	@FXML
	private TableView<DonacijaStavka> tblDonacije;
	@FXML
	private TableColumn<DonacijaStavka, String> tipOpremeDonacije;
	@FXML
	private TableColumn<DonacijaStavka, String> proizvodjacOpremeDonacije;
	@FXML
	private TableColumn<DonacijaStavka, String> modelOpremeDonacije;
	@FXML
	private TableColumn<DonacijaStavka, Integer> kolicinaDonacije;
	@FXML
	private TableColumn<DonacijaStavka, String> velicinaDonacije;
	@FXML
	private TableView<NarudzbaStavka> tblNarudzbe;
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
	
	private ObservableList<DonacijaStavka> listaStavkiDonacije = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnDodaj.setDisable(true);
		btnDodajStavku.setDisable(true);
		
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1, 1);
		spinnerKolicina.setValueFactory(valueFactory);
		
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
		dodajKonteksniMeni();
	}
	
	public void popuniTabelu(ObservableList<NarudzbaStavka> listaStavkiNarudzbe) {
		tipOpreme.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, String>("tipOpreme"));
		proizvodjacOpreme.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, String>("tipProizvodjac"));
		modelOpreme.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, String>("tipModel"));
		kolicina.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, Integer>("kolicina"));
		velicina.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, String>("velicina"));
		cijena.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, Double>("cijena"));
		obradjeno.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, String>("status"));
		
		tblNarudzbe.setItems(listaStavkiNarudzbe);
	}
	
	public void popuniTabeluDonacija() {
		tipOpremeDonacije.setCellValueFactory(new PropertyValueFactory<DonacijaStavka, String>("tip"));
		proizvodjacOpremeDonacije.setCellValueFactory(new PropertyValueFactory<DonacijaStavka, String>("proizvodjac"));
		modelOpremeDonacije.setCellValueFactory(new PropertyValueFactory<DonacijaStavka, String>("model"));
		kolicinaDonacije.setCellValueFactory(new PropertyValueFactory<DonacijaStavka, Integer>("kolicina"));
		velicinaDonacije.setCellValueFactory(new PropertyValueFactory<DonacijaStavka, String>("velicina"));
		
		tblDonacije.setItems(listaStavkiDonacije);
	}
	
	public void ucitajComboBoxeve() {
		ObservableList<DonacijaDTO> listaDonacija = DAOFactory.getDAOFactory().getDonacijaDAO().neobradjene(false);
		comboBoxDonacija.setItems(listaDonacija);
		comboBoxDonacija.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DonacijaDTO>() {

			@Override
			public void changed(ObservableValue<? extends DonacijaDTO> observable, DonacijaDTO oldValue, DonacijaDTO newValue) {
				if(newValue != null) {
					disableDodajStavkuDugme();
					ocitstiPoljaDonacija();
					if(newValue.getTipOpreme().getImaVelicinu()) {
						txtVelicina.setDisable(false);
					}
					else {
						txtVelicina.setDisable(true);
					}
					lblKolicina.setText("Količina: " + newValue.getKolicina().toString());
					lblTipOpreme.setText(newValue.getTipOpreme().toString());
				}
			}
			
		});
		
		ObservableList<Narudzba> listaNarudzbi = DAOFactory.getDAOFactory().getNarudzbaDAO().SELECT_NEOBRADJENE(true);
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
	}
	
	public void dodajKonteksniMeni() {
		ContextMenu cm = new ContextMenu();
	    MenuItem obrisiStavku = new MenuItem("Obrišite stavku");
	    obrisiStavku.setOnAction(new EventHandler<ActionEvent>() {
	    	
	        @Override
	        public void handle(ActionEvent t) {
	        	DonacijaStavka selektovanaStavkaDonacije = null;
	        	if(!listaStavkiDonacije.isEmpty()) {
	        		selektovanaStavkaDonacije = listaStavkiDonacije.get(tblDonacije.getSelectionModel().getSelectedIndex());	        		
	        	}
	            if (selektovanaStavkaDonacije != null){ 
	            	listaStavkiDonacije.remove(tblDonacije.getSelectionModel().getSelectedIndex());
	            	if(listaStavkiDonacije.size() == 0) {
	            		btnDodaj.setDisable(true);
	            	}
	            }
	        }
	        
	    });
	    cm.getItems().add(obrisiStavku);

	    tblDonacije.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent t) {
	            if(t.getButton() == MouseButton.SECONDARY)
	            {
	            	cm.show(tblDonacije , t.getScreenX() , t.getScreenY());
	            }
	            else {
	            	cm.hide();
	            }
	        }
	        
	    });
	}
	
	public void ocitstiPoljaDonacija() {
		txtOpis.setText(null);
		txtVelicina.setText(null);
		listaStavkiDonacije = FXCollections.observableArrayList();
		lblKolicina.setText(null);
		lblTipOpreme.setText(null);
		popuniTabeluDonacija();
		btnDodaj.setDisable(true);
	}
	
	public void checkBoxSelektovan() {
		comboBoxNarudzba.getSelectionModel().select(null);
		comboBoxNarudzba.setDisable(true);
    	tblNarudzbe.setDisable(true);
    	txtVelicina.setDisable(true);
    	spinnerKolicina.setDisable(false);
    	tblDonacije.setDisable(false);
    	comboBoxDonacija.setDisable(false);
    	txtOpis.setDisable(false);
    	btnDodaj.setDisable(true);
	}
	
	public void checkBoxNijeSelektovan() {
		comboBoxDonacija.getSelectionModel().select(null);
    	comboBoxDonacija.setDisable(true);
    	txtOpis.setText(null);
    	txtOpis.setDisable(true);
    	txtVelicina.setText(null);
    	txtVelicina.setDisable(true);
    	lblKolicina.setText(null);
		lblTipOpreme.setText(null);
    	spinnerKolicina.setDisable(true);
    	tblDonacije.setDisable(true);
    	comboBoxNarudzba.setDisable(false);
    	tblNarudzbe.setDisable(false);
    	btnDodaj.setDisable(true);
	}
	
	public void disableDodajStavkuDugme() {
		if(!comboBoxDonacija.getSelectionModel().getSelectedItem().getTipOpreme().getImaVelicinu()) {
			btnDodajStavku.disableProperty().bind(txtOpis.textProperty().isEmpty());
		}
		else {
			btnDodajStavku.disableProperty().bind(txtOpis.textProperty().isEmpty().or(txtVelicina.textProperty().isEmpty()));
		}
	}
	
	public void disableDodajDugme() {
		if(!checkBoxDonirana.isSelected()) {
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
	
	public void dodajStavku() {
		String velicina = "";
		Boolean imaLiVelicina = null;
		
		if(!comboBoxDonacija.getSelectionModel().getSelectedItem().getTipOpreme().getImaVelicinu()) {
			velicina = "-";
			imaLiVelicina = false;
		}
		else {
			velicina = txtVelicina.getText();
			imaLiVelicina = true;
		}
		
		Integer kolicina = spinnerKolicina.getValue();
		
		DonacijaStavka donacijaStavka = new DonacijaStavka(comboBoxDonacija.getSelectionModel().getSelectedItem().getTipOpreme().getTip(), comboBoxDonacija.getSelectionModel().getSelectedItem().getTipOpreme().getProizvodjac(), comboBoxDonacija.getSelectionModel().getSelectedItem().getTipOpreme().getModel(), kolicina,  imaLiVelicina, velicina, txtOpis.getText().trim());
		if(listaStavkiDonacije.isEmpty()) {
			listaStavkiDonacije.add(donacijaStavka);
		}
		else {
			Boolean postoji = false;
			for(DonacijaStavka donacijaStavkaTemp : listaStavkiDonacije) {
				if(donacijaStavkaTemp.getImaLiVelicinu()) {
					if(donacijaStavkaTemp.getVelicina().equals(donacijaStavka.getVelicina())) {
						postoji = true;
					}
				}
				else {
					postoji = true;
				}
			}
			if(!postoji) {
				listaStavkiDonacije.add(donacijaStavka);
			}
		}
		
		btnDodaj.setDisable(false);
		
		popuniTabeluDonacija();
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
				noviStage.setTitle("Stonoteniski klub");
				noviStage.initModality(Modality.APPLICATION_MODAL);
				
				controller.setOpremaKluba();
				controller.disableParametre();
				
				controller.setStavkaNarudzbe(tblNarudzbe.getSelectionModel().selectedItemProperty().get());
				controller.postaviSpiner();
				controller.disableDugmeEvidentiraj();
				noviStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					
			          public void handle(WindowEvent we) {
			        	  we.consume();
			              Optional<ButtonType> rezultat = AlertDisplay.showConfirmation("Dodavanje", "Da li želite da zapamtite dodavanje?");
			              if(ButtonData.YES.equals(rezultat.get().getButtonData())) {
			            	  if(controller.ubaciUBazu()) {
			            		  noviStage.close();
			            	  }
			              }
			              else if(ButtonData.NO.equals(rezultat.get().getButtonData())) {
			            	  noviStage.close();
			              }
			              
			          }});
				noviStage.showAndWait();
				ucitajComboBoxeve();
				tblNarudzbe.setItems(null);
			} catch (IOException e) {
				e.printStackTrace();
				new ErrorLogger().log(e);
			}
		}
		else {
			int ukupnaKolicina = 0;
			for(DonacijaStavka stavka : tblDonacije.getItems()) {
				ukupnaKolicina += stavka.getKolicina();
			}
			
			if(ukupnaKolicina == comboBoxDonacija.getSelectionModel().getSelectedItem().getKolicina().intValueExact()) {
				OpremaKluba opremaKluba = null;
				for(DonacijaStavka stavka : tblDonacije.getItems()) {
					if(stavka.getImaLiVelicinu()) {
						opremaKluba = new OpremaKluba(null, null, comboBoxDonacija.getSelectionModel().getSelectedItem().getTipOpreme().getId(), comboBoxDonacija.getSelectionModel().getSelectedItem().getSponzor().getId(), comboBoxDonacija.getSelectionModel().getSelectedItem().getUgovor().getRedniBroj(), comboBoxDonacija.getSelectionModel().getSelectedItem().getRedniBroj(), true, stavka.getVelicina(), stavka.getOpis(), true);
					}
					else {
						opremaKluba = new OpremaKluba(null, null, comboBoxDonacija.getSelectionModel().getSelectedItem().getTipOpreme().getId(), comboBoxDonacija.getSelectionModel().getSelectedItem().getSponzor().getId(), comboBoxDonacija.getSelectionModel().getSelectedItem().getUgovor().getRedniBroj(), comboBoxDonacija.getSelectionModel().getSelectedItem().getRedniBroj(), true, "-", stavka.getOpis(), true);
					}
					DAOFactory.getDAOFactory().getOpremaKlubaDAO().INSERT(opremaKluba, stavka.getKolicina());
				}
				
				DAOFactory.getDAOFactory().getDonacijaDAO().setObradjeno(comboBoxDonacija.getSelectionModel().getSelectedItem());
				
				primaryStage.close();
			}
			else {
				AlertDisplay.showError("Dodavanje", "Količina stavki se ne poklapa sa ukupnom količinom donacije.");
			}
		}
	}
}
