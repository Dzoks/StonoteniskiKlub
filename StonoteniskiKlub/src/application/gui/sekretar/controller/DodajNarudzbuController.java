package application.gui.sekretar.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.gui.trener.controller.DodajTipOpremeController;
import application.model.dao.NarudzbaDAO;
import application.model.dao.NarudzbaStavkaDAO;
import application.model.dao.OpremaTipDAO;
import application.model.dto.Narudzba;
import application.model.dto.NarudzbaStavka;
import application.model.dto.OpremaTip;
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
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DodajNarudzbuController extends BaseController implements Initializable{
	@FXML
	private Label lblDistributer;
	@FXML
	private Label lblDatum;
	@FXML
	private Label lblId;
	@FXML
	private ComboBox<OpremaTip> comboBoxTip;
	@FXML
	private Spinner<Integer> spinnerKolicina;
	@FXML
	private TextField txtCijena;
	@FXML
	private TextField txtVelicina;
	@FXML
	private Button btnDodajStavku;
	@FXML
	private Button btnEvidentiraj;
	@FXML
	private Button btnDodajTipOpreme;
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
	
	private Boolean opremaKluba = false;
	private Narudzba naroudzba;
	private ObservableList<NarudzbaStavka> listaStavkiNarudzbe = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ucitajTipoveOpreme();
		
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1, 1);
		spinnerKolicina.setValueFactory(valueFactory);
		
		dodajKonteksniMeni();
	}
	
	public void popuniTabelu() {
		id.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, Integer>("idNarudzbe"));
		tipOpreme.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, String>("tipOpreme"));
		proizvodjacOpreme.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, String>("tipProizvodjac"));
		modelOpreme.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, String>("tipModel"));
		kolicina.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, Integer>("kolicina"));
		velicina.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, String>("velicina"));
		cijena.setCellValueFactory(new PropertyValueFactory<NarudzbaStavka, Double>("cijena"));
		
		tblNarudzbe.setItems(listaStavkiNarudzbe);
	}
	
	public void ubaciUBazu() {
		NarudzbaDAO.INSERT(naroudzba, opremaKluba);
		if(!listaStavkiNarudzbe.isEmpty()) {
			for(NarudzbaStavka narudzbaStavka : listaStavkiNarudzbe) {
				NarudzbaStavkaDAO.INSERT(narudzbaStavka);
			}
		}
	}
	
	public void azurirajUBazi() {
		if(!naroudzba.getListaStavki().isEmpty()) {
			for(NarudzbaStavka narudzbaStavka : naroudzba.getListaStavki()) {
				NarudzbaStavkaDAO.DELETE(narudzbaStavka);
			}
		}
		if(!listaStavkiNarudzbe.isEmpty()) {
			for(NarudzbaStavka narudzbaStavka : listaStavkiNarudzbe) {
				NarudzbaStavkaDAO.INSERT(narudzbaStavka);
			}
		}
	}
	
	public void evidentirajDodavanje() {
		ubaciUBazu();
		primaryStage.close();
	}
	
	public void dodajKonteksniMeni() {
		ContextMenu cm = new ContextMenu();
	    MenuItem obrisiStavku = new MenuItem("Obrisi stavku");
	    obrisiStavku.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent t) {
	        	NarudzbaStavka selektovanaNarudzba = null;
	        	if(!listaStavkiNarudzbe.isEmpty()) {
	        		selektovanaNarudzba = listaStavkiNarudzbe.get(tblNarudzbe.getSelectionModel().getSelectedIndex());	        		
	        	}
	            if (selektovanaNarudzba != null){ 
	            	listaStavkiNarudzbe.remove(tblNarudzbe.getSelectionModel().getSelectedIndex());
	            	NarudzbaStavkaDAO.DELETE(selektovanaNarudzba);
	            }
	        }
	    });
	    cm.getItems().add(obrisiStavku);

	    tblNarudzbe.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent t) {
	            if(t.getButton() == MouseButton.SECONDARY)
	            {
	            	cm.show(tblNarudzbe , t.getScreenX() , t.getScreenY());
	            }
	        }
	    });
	}
	
	public void disableDugme() {
		if(!comboBoxTip.getSelectionModel().getSelectedItem().getImaVelicinu()) {
			btnDodajStavku.disableProperty().bind(txtCijena.textProperty().isEmpty());
		}
		else {
			btnDodajStavku.disableProperty().bind(txtCijena.textProperty().isEmpty().or(txtVelicina.textProperty().isEmpty()));
		}
	}
	
	public void ucitajTipoveOpreme() {
		ObservableList<OpremaTip> listaTipovaOpreme = OpremaTipDAO.SELECT_ALL();
		comboBoxTip.setItems(listaTipovaOpreme);
		
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
					disableDugme();
				}
			}
		});
		
		comboBoxTip.getSelectionModel().selectFirst();
	}
	
	public void provjeriParametre() {
		try {
			Double.valueOf(txtCijena.getText());
			Integer idTipaOpreme = comboBoxTip.getSelectionModel().getSelectedItem().getId();
			String velicina = "";
			if(!comboBoxTip.getSelectionModel().getSelectedItem().getImaVelicinu()) {
				velicina = "-";
			}
			else {
				velicina = txtVelicina.getText();
			}
			Integer kolicina = spinnerKolicina.getValue();
			Double cijena = Double.valueOf(txtCijena.getText());
			NarudzbaStavka narudzbaStavka = new NarudzbaStavka(naroudzba.getId(), idTipaOpreme, velicina, kolicina, cijena, false);
			if(listaStavkiNarudzbe.isEmpty()) {
				listaStavkiNarudzbe.add(narudzbaStavka);
			}
			else {
				Boolean postoji = false;
				for(NarudzbaStavka narudzbaStavkaTemp : listaStavkiNarudzbe) {
					if(narudzbaStavkaTemp.getIdNarudzbe().equals(narudzbaStavka.getIdNarudzbe()) 
							&& narudzbaStavkaTemp.getIdTipaOpreme().equals(narudzbaStavka.getIdTipaOpreme()) 
							&& narudzbaStavkaTemp.getVelicina().equals(narudzbaStavka.getVelicina())){
						postoji = true;
					}
				}
				if(!postoji) {
					listaStavkiNarudzbe.add(narudzbaStavka);
				}
			}
			
			popuniTabelu();
			
		}catch(NumberFormatException e) {
			new Alert(AlertType.ERROR, "Cijena nije u dobrom formatu.", ButtonType.OK).show();
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
				ucitajTipoveOpreme();
				comboBoxTip.getSelectionModel().selectLast();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setParametre(String distributerOpreme, String datum, String idNarudzbe) {
		lblDistributer.setText(distributerOpreme);
		lblDatum.setText(datum);
		lblId.setText(idNarudzbe);
	}
	
	public void VelicinaOnemoguceno() {
		txtVelicina.setDisable(true);
	}
	
	public void setOpremaKluba() {
		opremaKluba = true;
	}
	
	public void setNarudzba(Narudzba narudzba) {
		this.naroudzba = narudzba;
	}

	public void setListaStavkiNarudzbe(ObservableList<NarudzbaStavka> listaStavkiNarudzbe) {
		this.listaStavkiNarudzbe = listaStavkiNarudzbe;
	}
}
