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
import application.gui.sekretar.controller.DodajNarudzbuProzorController;
import application.model.dao.DistributerOpremeDAO;
import application.model.dao.NarudzbaDAO;
import application.model.dto.DistributerOpremeDTO;
import application.model.dto.NarudzbaDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class NarudzbaGlavniProzorController extends BaseController implements Initializable{

	@FXML 
	private Button btnIzmjeni;
	@FXML
	private Button btnDodaj;
	@FXML
	private Button btnDodajDistributera;
	@FXML
	private ComboBox<DistributerOpremeDTO> comboBoxDistributer;
	@FXML
	private ComboBox<String> comboBoxVrsta;
	@FXML
	private TableView<NarudzbaDTO> tblNarudzbe;
	@FXML
	private TableColumn<NarudzbaDTO, Integer> id;
	@FXML
	private TableColumn<NarudzbaDTO, Date> datum;
	@FXML
	private TableColumn<NarudzbaDTO, String> nazivDistributera;
	@FXML
	private TableColumn<NarudzbaDTO, String> vrsta;
	@FXML
	private TableColumn<NarudzbaDTO, Boolean> obradjena;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnIzmjeni.setDisable(true);
		popuniTabelu();
		ucitajComboBoxeve();
	}
	
	public void popuniTabelu() {
		id.setCellValueFactory(new PropertyValueFactory<NarudzbaDTO, Integer>("id"));
		datum.setCellValueFactory(new PropertyValueFactory<NarudzbaDTO, Date>("datum"));
		nazivDistributera.setCellValueFactory(new PropertyValueFactory<NarudzbaDTO, String>("nazivDistributeraOpreme"));
		vrsta.setCellValueFactory(new PropertyValueFactory<NarudzbaDTO, String>("vrsta"));
		obradjena.setCellValueFactory(new PropertyValueFactory<NarudzbaDTO, Boolean>("obradjeno"));
		
		ObservableList<NarudzbaDTO> listaNarudzbi = NarudzbaDAO.SELECT_ALL();
		
		tblNarudzbe.setItems(listaNarudzbi);
		tblNarudzbe.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<NarudzbaDTO>() {

			@Override
			public void changed(ObservableValue<? extends NarudzbaDTO> observable, NarudzbaDTO oldValue,NarudzbaDTO newValue) {
				if (tblNarudzbe.getSelectionModel().getSelectedItem()!=null) {
					if(NarudzbaDAO.PROVJERA_STATUSA(tblNarudzbe.getSelectionModel().getSelectedItem().getId())) {
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
		ObservableList<DistributerOpremeDTO> listaDistributera = DistributerOpremeDAO.SELECT_ALL();
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
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/sekretar/view/DodajNarudzbuProzor.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,761,548);
			DodajNarudzbuProzorController controller = loader.<DodajNarudzbuProzorController>getController();
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
			NarudzbaDTO narudzba = null;
			try {
				narudzba = new NarudzbaDTO(NarudzbaDAO.SELECT_NEXT_ID(), df.parse(trenutniDatumString), opremaKluba, false, comboBoxDistributer.getSelectionModel().getSelectedItem().getId());
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
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/sekretar/view/DodajNarudzbuProzor.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,761,548);
			DodajNarudzbuProzorController controller = loader.<DodajNarudzbuProzorController>getController();
			controller.setPrimaryStage(noviStage);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa opremom");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			
			NarudzbaDTO selektovanaNarudzba = tblNarudzbe.getSelectionModel().selectedItemProperty().get();
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String datumNarudzbe = df.format(selektovanaNarudzba.getDatum());
			Boolean opremaKluba =  "Oprema kluba".equals(selektovanaNarudzba.getVrsta());
			if(opremaKluba) {
				controller.VelicinaOnemoguceno();
				controller.setOpremaKluba();
			}
			controller.disableDugme();
  		  	controller.setNarudzba(selektovanaNarudzba);
  		  	controller.setZaEditovanje();
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
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/sekretar/view/DodajDistributeraProzor.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,265,219);
			DodajDistributeraProzorController controller = loader.<DodajDistributeraProzorController>getController();
			controller.setPrimaryStage(noviStage);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa opremom");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			noviStage.showAndWait();
			if("YES".equals(controller.getPovratnaVrijednost())) {
				DistributerOpremeDTO noviDistributer = controller.vratiDistributeraOpreme();
				DistributerOpremeDAO.INSERT(noviDistributer);
				ucitajComboBoxeve();
				comboBoxDistributer.getSelectionModel().selectLast();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void idiNaPregledNarudzbe(NarudzbaDTO narudzba) {
		Stage noviStage = new Stage();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/sekretar/view/PregledNarudzbeProzor.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,761,384);
			PregledNarudzbeProzorController controller = loader.<PregledNarudzbeProzorController>getController();
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
