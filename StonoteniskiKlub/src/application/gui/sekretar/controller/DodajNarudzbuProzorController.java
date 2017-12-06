package application.gui.sekretar.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.gui.trener.controller.DodajTipOpremeProzorController;
import application.model.dao.OpremaTipDAO;
import application.model.dto.NarudzbaStavkaDTO;
import application.model.dto.OpremaTipDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.Stage;
import javafx.scene.control.Label;

import javafx.scene.control.ComboBox;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DodajNarudzbuProzorController extends BaseController implements Initializable{
	@FXML
	private Label lblDistributer;
	@FXML
	private Label lblDatum;
	@FXML
	private Label lblId;
	@FXML
	private ComboBox<OpremaTipDTO> comboBoxTip;
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
	
	private Boolean opremaKluba = false;
	private Integer idNarudzbe; 
	private ObservableList<NarudzbaStavkaDTO> listaStavkiNarudzbe = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ucitajTipoveOpreme();
		
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1, 1);
		spinnerKolicina.setValueFactory(valueFactory);
	}
	
	public void popuniTabelu() {
		id.setCellValueFactory(new PropertyValueFactory<NarudzbaStavkaDTO, Integer>("idNarudzbe"));
		tipOpreme.setCellValueFactory(new PropertyValueFactory<NarudzbaStavkaDTO, String>("tipOpreme"));
		proizvodjacOpreme.setCellValueFactory(new PropertyValueFactory<NarudzbaStavkaDTO, String>("tipProizvodjac"));
		modelOpreme.setCellValueFactory(new PropertyValueFactory<NarudzbaStavkaDTO, String>("tipModel"));
		kolicina.setCellValueFactory(new PropertyValueFactory<NarudzbaStavkaDTO, Integer>("kolicina"));
		velicina.setCellValueFactory(new PropertyValueFactory<NarudzbaStavkaDTO, String>("velicina"));
		cijena.setCellValueFactory(new PropertyValueFactory<NarudzbaStavkaDTO, Double>("cijena"));
		
		tblNarudzbe.setItems(listaStavkiNarudzbe);
		
		
	}
	
	public void disableDugme() {
		if(opremaKluba) {
			btnDodajStavku.disableProperty().bind(txtCijena.textProperty().isEmpty());
		}
		else {
			btnDodajStavku.disableProperty().bind(txtCijena.textProperty().isEmpty().or(txtVelicina.textProperty().isEmpty()));
		}
	}
	
	public void ucitajTipoveOpreme() {
		ObservableList<OpremaTipDTO> listaTipovaOpreme = OpremaTipDAO.SELECT_ALL();
		comboBoxTip.setItems(listaTipovaOpreme);
		comboBoxTip.getSelectionModel().selectFirst();
	}
	
	public void provjeriParametre() {
		try {
			Double.valueOf(txtCijena.getText());
			Integer idTipaOpreme = comboBoxTip.getSelectionModel().getSelectedItem().getId();
			String velicina = "";
			if(opremaKluba) {
				velicina = "/";
			}
			else {
				velicina = txtVelicina.getText();
			}
			Integer kolicina = spinnerKolicina.getValue();
			Double cijena = Double.valueOf(txtCijena.getText());
			NarudzbaStavkaDTO narudzbaStavka = new NarudzbaStavkaDTO(this.idNarudzbe, idTipaOpreme, velicina, kolicina, cijena, false);
			if(listaStavkiNarudzbe.isEmpty()) {
				listaStavkiNarudzbe.add(narudzbaStavka);
			}
			else {
				Boolean postoji = false;
				for(NarudzbaStavkaDTO narudzbaStavkaTemp : listaStavkiNarudzbe) {
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
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/trener/view/DodajTipOpremeProzor.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,267,185);
			DodajTipOpremeProzorController controller = loader.<DodajTipOpremeProzorController>getController();
			controller.setPrimaryStage(noviStage);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa opremom");
			noviStage.showAndWait();
			if("YES".equals(controller.getPovratnaVrijednost())) {
				OpremaTipDTO noviDistributer = controller.vratiTipOpreme();
				OpremaTipDAO.INSERT(noviDistributer);
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
	
	public void setIdNarudzbe(Integer idNarudzbe) {
		this.idNarudzbe = idNarudzbe;
	}
}
