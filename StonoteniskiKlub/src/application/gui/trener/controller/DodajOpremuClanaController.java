package application.gui.trener.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.Narudzba;
import application.model.dto.NarudzbaStavka;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class DodajOpremuClanaController extends BaseController{
	@FXML
	private ComboBox<Narudzba> comboBoxNarudzba;
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
	@FXML
	private Button btnDodaj;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnDodaj.setDisable(true);
		
		tblNarudzbe.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent t) {
	        	NarudzbaStavka selektovanaStavka = tblNarudzbe.getSelectionModel().getSelectedItem();
	        	if(selektovanaStavka != null) {
		    	    if(selektovanaStavka.getObradjeno()) {
		    	    	btnDodaj.setDisable(true);
		    	    }
		    	    else {
		    	    	btnDodaj.setDisable(false);
		    	    }
	        	}
	        }
	        
	    });
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
		ObservableList<Narudzba> listaNarudzbi = DAOFactory.getDAOFactory().getNarudzbaDAO().SELECT_NEOBRADJENE(false);
		comboBoxNarudzba.setItems(listaNarudzbi);
		comboBoxNarudzba.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Narudzba>() {

			@Override
			public void changed(ObservableValue<? extends Narudzba> observable, Narudzba oldValue, Narudzba newValue) {
				if(newValue != null) {
					ObservableList<NarudzbaStavka> listaStavkiNarudzbe = newValue.getListaStavki();
					btnDodaj.setDisable(true);
					popuniTabelu(listaStavkiNarudzbe);
				}
			}
			
		});
	}

	public void idiNaObradiOpremu() {
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
			btnDodaj.setDisable(true);
			tblNarudzbe.setItems(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
