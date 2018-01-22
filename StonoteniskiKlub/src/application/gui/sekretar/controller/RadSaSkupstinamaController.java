package application.gui.sekretar.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dao.StavkaSkupstinaDAO;
import application.model.dto.SkupstinaDTO;
import application.model.dto.StavkaSkupstinaDTO;
import application.util.AlertDisplay;
import application.util.InputValidator;
import application.util.TextUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class RadSaSkupstinamaController extends BaseController {
	@FXML
	private TableView<SkupstinaDTO> tblSkupstine;
	@FXML
	private TableColumn<SkupstinaDTO, String> colDatumOdrzavanja;
	@FXML
	private TableColumn<SkupstinaDTO, String> colImaIzvjestaj;
	@FXML
	private TextFlow taTekst;
	@FXML
	private RadioButton rbDnevniRed;
	@FXML
	private ToggleGroup grpTip;
	@FXML
	private RadioButton rbIzvjestaj;
	@FXML
	private DatePicker dpOd;
	@FXML
	private DatePicker dpDo;
	@FXML
	private Button btnPretrazi;
	@FXML
	private Button btnDodajIzvjestaj;
	@FXML
	private Button btnDodajSkupstinu;
	@FXML
	private Button btnOsvjezi;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buildTable();
		populateTable();
		bindDisable();
	}

	@FXML
	public void setTekst(MouseEvent event) {
		SkupstinaDTO skupstina = tblSkupstine.getSelectionModel().getSelectedItem();
		if (skupstina != null) {
			List<StavkaSkupstinaDTO> stavke = rbDnevniRed.isSelected() ? skupstina.getStavkeDnevnogReda()
					: skupstina.getStavkeIzvjestaja();
			TextUtility.setTextFlow(taTekst, stavke);
		}
	}
	  @FXML
	    void odjaviteSe(ActionEvent event) {
	    	try {
				BaseController.changeScene("/application/gui/administrator/view/LoginView.fxml", primaryStage);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }

	// Event Listener on RadioButton[#rbDnevniRed].onAction
	@FXML
	public void setTekstDnevnogReda(ActionEvent event) {
		SkupstinaDTO skupstina = tblSkupstine.getSelectionModel().getSelectedItem();
		if (skupstina != null) {
			TextUtility.setTextFlow(taTekst, skupstina.getStavkeDnevnogReda());
		}
	}

	// Event Listener on RadioButton[#rbIzvjestaj].onAction
	@FXML
	public void setTekstIzvjestaja(ActionEvent event) {
		SkupstinaDTO skupstina = tblSkupstine.getSelectionModel().getSelectedItem();
		if (skupstina != null) {
			TextUtility.setTextFlow(taTekst, skupstina.getStavkeIzvjestaja());
		}
	}

	// Event Listener on Button[#btnPretrazi].onAction
	@FXML
	public void pretrazi(ActionEvent event) {
		if (!InputValidator.allEntered(dpOd.getValue()) && !InputValidator.allEntered(dpDo.getValue())) {
			AlertDisplay.showError("Pretraga", "Niste unijeli parametre pretrage.");
		} else {
			int type = 0;
			ObservableList<SkupstinaDTO> filtered = FXCollections.observableArrayList();
			ObservableList<SkupstinaDTO> sve = tblSkupstine.getItems();
			if (dpOd.getValue() != null && dpDo.getValue() != null) {
				type = 1;
			} else if (dpOd.getValue() != null) {
				type = 2;
			} else {
				type = 3;
			}
			for (SkupstinaDTO skupstina : sve) {
				if (type == 1 && skupstina.getDatum().compareTo(dpOd.getValue()) >= 0
						&& skupstina.getDatum().compareTo(dpDo.getValue()) <= 0) {
					filtered.add(skupstina);
				} else if(type == 2 && skupstina.getDatum().compareTo(dpOd.getValue()) >=0){
					filtered.add(skupstina);
				} else if(type == 3 && skupstina.getDatum().compareTo(dpDo.getValue()) <=0){
					filtered.add(skupstina);
				}
			}
			tblSkupstine.setItems(filtered);
		}
	}

	// Event Listener on Button[#btnDodajIzvjestaj].onAction
	@FXML
	public void dodajIzvjestaj(ActionEvent event) {
		SkupstinaDTO skupstina = tblSkupstine.getSelectionModel().getSelectedItem();
		if (skupstina.getStavkeIzvjestaja() == null || skupstina.getStavkeIzvjestaja().size() == 0) {
			Stage newStage = new Stage();
			DodavanjeSkupstineController controller = null;
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getClassLoader()
						.getResource("application/gui/sekretar/view/DodavanjeSkupstineView.fxml"));
				AnchorPane root = (AnchorPane) loader.load();
				controller = loader.<DodavanjeSkupstineController>getController();
				controller.setParentController(this);
				controller.setSkupstina(skupstina);
				controller.setTitle("Stonoteniski klub");
				controller.setTip(StavkaSkupstinaDAO.IZVJESTAJ);
				Scene scene = new Scene(root, 700, 600);
				newStage.setScene(scene);
				newStage.setResizable(false);
				newStage.setTitle("Stoneteniski klub");
				newStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			AlertDisplay.showError("Dodavanje", "Izvještaj već postoji.");
		}
	}

	// Event Listener on Button[#btnDodajSkupstinu].onAction
	@FXML
	public void dodajSkupstinu(ActionEvent event) {
		Stage newStage = new Stage();
		DodavanjeSkupstineController controller = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader()
					.getResource("application/gui/sekretar/view/DodavanjeSkupstineView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			controller = loader.<DodavanjeSkupstineController>getController();
			controller.setParentController(this);
			controller.setTip(StavkaSkupstinaDAO.DNEVNI_RED);
			Scene scene = new Scene(root, 700, 600);
			newStage.setScene(scene);
			newStage.setResizable(false);
			newStage.setTitle("Stonoteniski klub");
			newStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@FXML
	public void osvjezi(ActionEvent event){
		populateTable();
	}
	public void addItem(SkupstinaDTO skupstina) {
		tblSkupstine.getItems().add(skupstina);
	}

	public void refresh() {
		tblSkupstine.refresh();
	}

	private void buildTable() {
		colDatumOdrzavanja.setCellValueFactory(new PropertyValueFactory<SkupstinaDTO, String>("datum"));
		colImaIzvjestaj.setCellValueFactory(new PropertyValueFactory<SkupstinaDTO, String>("imaIzvjestaj"));
	}

	private void populateTable() {
		ObservableList<SkupstinaDTO> skupstine = DAOFactory.getDAOFactory().getSkupstinaDAO().selectAll();
		for (SkupstinaDTO skupstina : skupstine) {
			skupstina.setStavkeDnevnogReda(DAOFactory.getDAOFactory().getStavkaSkupstinaDAO()
					.selectAllById(skupstina.getId(), StavkaSkupstinaDAO.DNEVNI_RED));
			skupstina.setStavkeIzvjestaja(DAOFactory.getDAOFactory().getStavkaSkupstinaDAO()
					.selectAllById(skupstina.getId(), StavkaSkupstinaDAO.IZVJESTAJ));
		}
		tblSkupstine.setItems(skupstine);
	}

	private void bindDisable() {
		btnDodajIzvjestaj.disableProperty().bind(tblSkupstine.getSelectionModel().selectedItemProperty().isNull());
	}
}
