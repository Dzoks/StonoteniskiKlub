package application.gui.sekretar.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dao.StavkaSkupstinaDAO;
import application.model.dto.SkupstinaDTO;
import application.model.dto.StavkaSkupstinaDTO;
import application.util.AlertDisplay;
import application.util.ErrorLogger;
import application.util.GUIUtility;
import application.util.InputValidator;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DodavanjeSkupstineController extends BaseController {
	@FXML
	private DatePicker dpDatumOdrzavanja;
	@FXML
	private TextFlow taTekstDnevnogReda;
	@FXML
	private Button btnDodajStavku;
	@FXML
	private ListView<StavkaSkupstinaDTO> lstListaStavki;
	@FXML
	private Button btnUkloniStavku;
	@FXML
	private Button btnSacuvaj;
	@FXML
	private Label lblNaslov;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lstListaStavki.setItems(stavke);
		bindDisable();
	}

	// Event Listener on Button[#btnDodajStavku].onAction
	@FXML
	public void dodajStavku(ActionEvent event) {
		Stage newStage = new Stage();
		DodavanjeStavkeDnevnogRedaController controller = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader()
					.getResource("application/gui/sekretar/view/DodavanjeStavkeDnevnogRedaView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			controller = loader.<DodavanjeStavkeDnevnogRedaController>getController();
			StavkaSkupstinaDTO stavka = new StavkaSkupstinaDTO();
			controller.setStavka(stavka);
			controller.setParentController(this);
			Scene scene = new Scene(root, 400, 400);
			newStage.setScene(scene);
			newStage.setResizable(false);
			newStage.setTitle("Stonoteniski klub");
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.show();
		} catch (IOException e) {
			 ;
			new ErrorLogger().log(e);
		}
	}

	// Event Listener on Button[#btnUkloniStavku].onAction
	@FXML
	public void ukloniStavku(ActionEvent event) {
		StavkaSkupstinaDTO stavka = lstListaStavki.getSelectionModel().getSelectedItem();
		int index = stavke.indexOf(stavka);
		stavke.remove(index);
		for (int i = index; i < stavke.size(); i++) {
			stavke.get(i).setRedniBroj(stavke.get(i).getRedniBroj() - 1);
		}
		GUIUtility.setTextFlow(taTekstDnevnogReda, stavke);
	}

	// Event Listener on Button[#btnSacuvaj].onAction
	@FXML
	public void sacuvaj(ActionEvent event) {
		if (this.tip == StavkaSkupstinaDAO.IZVJESTAJ || InputValidator.allEntered(dpDatumOdrzavanja.getValue())) {
			LocalDate datumOdrzavanja = null;
			SkupstinaDTO skupstina = this.skupstina;
			boolean uspjesno = true;
			if (this.tip == StavkaSkupstinaDAO.DNEVNI_RED) {
				datumOdrzavanja = dpDatumOdrzavanja.getValue();
				skupstina = new SkupstinaDTO(null, datumOdrzavanja, stavke, null);
				uspjesno = DAOFactory.getDAOFactory().getSkupstinaDAO().insert(skupstina);
			}
			if (uspjesno) {
				for (Iterator<StavkaSkupstinaDTO> it = stavke.iterator(); it.hasNext();) {
					StavkaSkupstinaDTO stavka = it.next();
					DAOFactory.getDAOFactory().getStavkaSkupstinaDAO().insert(skupstina, stavka, tip);
				}
				if (this.tip == StavkaSkupstinaDAO.IZVJESTAJ) {
					this.skupstina.setStavkeIzvjestaja(stavke);
				} else {
					parentController.addItem(skupstina);
				}
				parentController.refresh();
				AlertDisplay.showInformation("Dodavanje", "Uspješno dodavanje");
				Stage stage = (Stage) btnSacuvaj.getScene().getWindow();
				stage.close();
			} else {
				AlertDisplay.showWarning("Dodavanje", "Došlo je do greške.");
			}
		} else {
			AlertDisplay.showInformation("Dodavanje", "Niste unijeli datum održavanja skupštine.");
		}
	}

	public void dodaj(StavkaSkupstinaDTO stavka) {
		int nextRb = stavke.size() + 1;
		stavka.setRedniBroj(nextRb);
		stavke.add(stavka);
		GUIUtility.setTextFlow(taTekstDnevnogReda, stavke);
	}

	public void setParentController(RadSaSkupstinamaController parentController) {
		this.parentController = parentController;
	}

	public void setTitle(String title) {
		lblNaslov.setText(title);
	}

	public void setTip(int tip) {
		this.tip = tip;
		if (tip == StavkaSkupstinaDAO.IZVJESTAJ) {
			dpDatumOdrzavanja.setDisable(true);
			btnSacuvaj.disableProperty().bind((Bindings.isEmpty(lstListaStavki.getItems())));
		} else {
			btnSacuvaj.disableProperty()
					.bind((Bindings.isEmpty(lstListaStavki.getItems()).or(dpDatumOdrzavanja.valueProperty().isNull())));
		}
	}

	public void setSkupstina(SkupstinaDTO skupstina) {
		this.skupstina = skupstina;
	}

	private void bindDisable() {
		btnUkloniStavku.disableProperty().bind(lstListaStavki.getSelectionModel().selectedItemProperty().isNull());
	}

	private ObservableList<StavkaSkupstinaDTO> stavke = FXCollections.observableArrayList();
	private RadSaSkupstinamaController parentController;
	private int tip;
	private SkupstinaDTO skupstina;
}
