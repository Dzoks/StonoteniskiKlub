package application.gui.sekretar.controller;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.DistributerOpreme;
import application.model.dto.Narudzba;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class NarudzbaGlavniController extends BaseController implements Initializable{

	@FXML 
	private Button btnIzmjeni;
	@FXML
	private Button btnDodaj;
	@FXML
	private Button btnDodajDistributera;
	@FXML
	private ComboBox<DistributerOpreme> comboBoxDistributer;
	@FXML
	private ComboBox<String> comboBoxVrsta;
	@FXML
	private TableView<Narudzba> tblNarudzbe;
	@FXML
	private TableColumn<Narudzba, Integer> id;
	@FXML
	private TableColumn<Narudzba, Date> datum;
	@FXML
	private TableColumn<Narudzba, String> nazivDistributera;
	@FXML
	private TableColumn<Narudzba, String> vrsta;
	@FXML
	private TableColumn<Narudzba, String> status;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnIzmjeni.setDisable(true);
		popuniTabelu();
		ucitajComboBoxeve();
		dodajKonteksniMeni();
	}
	
	public void dodajKonteksniMeni() {
		ContextMenu cm = new ContextMenu();
	    MenuItem obrisiNarudzbu = new MenuItem("Obrisi narudzbu");
	    
	    obrisiNarudzbu.setOnAction(new EventHandler<ActionEvent>() {
	    	
	        @Override
	        public void handle(ActionEvent t) {
	        	Narudzba selektovanaNarudzba = null;
	        	ObservableList<Narudzba> listaNarudzbi = tblNarudzbe.getItems();
	        	if(!listaNarudzbi.isEmpty()) {
	        		selektovanaNarudzba = listaNarudzbi.get(tblNarudzbe.getSelectionModel().getSelectedIndex());	        		
	        	}
	        	
	            if (selektovanaNarudzba != null){ 
	            	DAOFactory.getDAOFactory().getNarudzbaDAO().UPDATE_OBRISAN(selektovanaNarudzba);
	            	popuniTabelu();
	            	btnIzmjeni.setDisable(true);
	            }
	        }
	        
	    });
	    
	    tblNarudzbe.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent t) {
	        	cm.getItems().clear();
	        	Narudzba selektovanaNarudzba = tblNarudzbe.getSelectionModel().getSelectedItem();
	        	if(selektovanaNarudzba != null && !DAOFactory.getDAOFactory().getNarudzbaDAO().PROVJERA_STATUSA(selektovanaNarudzba.getId())) {
		    	    cm.getItems().add(obrisiNarudzbu);
		            if(t.getButton() == MouseButton.SECONDARY)
		            {
		            	cm.show(tblNarudzbe , t.getScreenX() , t.getScreenY());
		            }
		            else {
		            	cm.hide();
		            }
	        	}
	        }
	        
	    });
	}
	
	public void popuniTabelu() {
		id.setCellValueFactory(new PropertyValueFactory<Narudzba, Integer>("id"));
		datum.setCellValueFactory(new PropertyValueFactory<Narudzba, Date>("datum"));
		nazivDistributera.setCellValueFactory(new PropertyValueFactory<Narudzba, String>("nazivDistributeraOpreme"));
		vrsta.setCellValueFactory(new PropertyValueFactory<Narudzba, String>("vrsta"));
		status.setCellValueFactory(new PropertyValueFactory<Narudzba, String>("status"));
		
		ObservableList<Narudzba> listaNarudzbi = DAOFactory.getDAOFactory().getNarudzbaDAO().SELECT_ALL();
		
		tblNarudzbe.setItems(listaNarudzbi);
		tblNarudzbe.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Narudzba>() {

			@Override
			public void changed(ObservableValue<? extends Narudzba> observable, Narudzba oldValue,Narudzba newValue) {
				if (tblNarudzbe.getSelectionModel().getSelectedItem()!=null) {
					if(DAOFactory.getDAOFactory().getNarudzbaDAO().PROVJERA_STATUSA(tblNarudzbe.getSelectionModel().getSelectedItem().getId())) {
						btnIzmjeni.setDisable(true);
					}
					else {
						btnIzmjeni.setDisable(false);
					}
				}
				else {
					btnIzmjeni.setDisable(true);
				}
			}
			
		});
		
		tblNarudzbe.setOnMousePressed(new EventHandler<MouseEvent>() {
			
		    @Override 
		    public void handle(MouseEvent event) {
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
		            idiNaPregledNarudzbe(tblNarudzbe.getSelectionModel().getSelectedItem());                 
		        }
		    }
		    
		});
	}
	
	public void ucitajComboBoxeve() {
		ObservableList<DistributerOpreme> listaDistributera = DAOFactory.getDAOFactory().getDistributerOpremeDAO().SELECT_ALL();
		comboBoxDistributer.setItems(listaDistributera);
		comboBoxDistributer.getSelectionModel().selectFirst();
		
		ObservableList<String> listaVrsta = FXCollections.observableArrayList();
		listaVrsta.add("Oprema kluba");
		listaVrsta.add("Oprema clana");
		comboBoxVrsta.setItems(listaVrsta);
		comboBoxVrsta.getSelectionModel().selectFirst();
	}

	public void idiNaDodajNarudzbu() {
		Stage noviStage = new Stage();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/sekretar/view/DodajNarudzbuView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,761,548);
			DodajNarudzbuController controller = loader.<DodajNarudzbuController>getController();
			controller.setPrimaryStage(noviStage);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa opremom");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date trenutniDatum = Calendar.getInstance().getTime(); 
			String trenutniDatumString = df.format(trenutniDatum);
			Boolean opremaKluba =  "Oprema kluba".equals(comboBoxVrsta.getSelectionModel().getSelectedItem());
			
			if(opremaKluba) {
				controller.VelicinaOnemoguceno();
				controller.setOpremaKluba();
			}
			
			controller.disableDugme();
			Narudzba narudzba = null;
			try {
				narudzba = new Narudzba(DAOFactory.getDAOFactory().getNarudzbaDAO().SELECT_NEXT_ID(), df.parse(trenutniDatumString), opremaKluba, false, comboBoxDistributer.getSelectionModel().getSelectedItem().getId());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
  		  	controller.setNarudzba(narudzba);
			
			controller.setParametre(comboBoxDistributer.getSelectionModel().getSelectedItem().getNaziv(), trenutniDatumString, "-");
			noviStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				
		          public void handle(WindowEvent we) {
		        	  we.consume();
		              Alert alert = new Alert(AlertType.CONFIRMATION, "Da li zelite da zapamtite narudzbu?", ButtonType.YES, ButtonType.NO);
		              Optional<ButtonType> rezultat = alert.showAndWait();
		              if(ButtonType.YES.equals(rezultat.get())) {
		            	  controller.ubaciUBazu();
		            	  noviStage.close();
		              }
		              else if(ButtonType.NO.equals(rezultat.get())) {
		            	  noviStage.close();
		              }
		              
		          }});
			noviStage.showAndWait(); 
			popuniTabelu();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void idiNaIzmjenuNarudzbe() {
		Stage noviStage = new Stage();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/sekretar/view/IzmjenaNarudzbeView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,761,585);
			IzmjenaNarudzbeController controller = loader.<IzmjenaNarudzbeController>getController();
			controller.setPrimaryStage(noviStage);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa opremom");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			
			Narudzba selektovanaNarudzba = tblNarudzbe.getSelectionModel().selectedItemProperty().get();
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String datumNarudzbe = df.format(selektovanaNarudzba.getDatum());
			Boolean opremaKluba =  "Oprema kluba".equals(selektovanaNarudzba.getVrsta());
			if(opremaKluba) {
				controller.VelicinaOnemoguceno();
			}
  		  	controller.setNaroudzba(selektovanaNarudzba);
  		  	controller.ucitajComboBoxeve();
  		  	controller.disableDugme();
  		  	controller.setListaStavkiNarudzbe(selektovanaNarudzba.getListaStavki());
  		  	controller.popuniTabelu();
			
			controller.setParametre(comboBoxDistributer.getSelectionModel().getSelectedItem().getNaziv(), datumNarudzbe, selektovanaNarudzba.getId().toString());
			noviStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				
		          public void handle(WindowEvent we) {
		        	  we.consume();
		              Alert alert = new Alert(AlertType.CONFIRMATION, "Da li zelite da sacuvate izmjene?", ButtonType.YES, ButtonType.NO);
		              Optional<ButtonType> rezultat = alert.showAndWait();
		              if(ButtonType.YES.equals(rezultat.get())) {
		            	  controller.azurirajUBazi();
		            	  noviStage.close();
		              }
		              else if(ButtonType.NO.equals(rezultat.get())) {
		            	  noviStage.close();
		              }
		              
		          }});
			noviStage.showAndWait(); 
			popuniTabelu();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void idiNaDodajDistributera() {
		Stage noviStage = new Stage();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/sekretar/view/DodajDistributeraView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,265,219);
			DodajDistributeraController controller = loader.<DodajDistributeraController>getController();
			controller.setPrimaryStage(noviStage);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa opremom");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			noviStage.showAndWait();
			
			if("YES".equals(controller.getPovratnaVrijednost())) {
				DistributerOpreme noviDistributer = controller.vratiDistributeraOpreme();
				DAOFactory.getDAOFactory().getDistributerOpremeDAO().INSERT(noviDistributer);
				ucitajComboBoxeve();
				comboBoxDistributer.getSelectionModel().selectLast();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void idiNaPregledNarudzbe(Narudzba narudzba) {
		Stage noviStage = new Stage();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/sekretar/view/PregledNarudzbeView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,761,384);
			PregledNarudzbeController controller = loader.<PregledNarudzbeController>getController();
			controller.setPrimaryStage(noviStage);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa opremom");
			controller.setNarudzba(narudzba);
			controller.popuniPodatke();
			noviStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
