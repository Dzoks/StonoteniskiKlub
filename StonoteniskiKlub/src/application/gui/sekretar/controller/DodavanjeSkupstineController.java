package application.gui.sekretar.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.ResourceBundle;

import com.itextpdf.text.log.SysoCounter;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dao.StavkaSkupstinaDAO;
import application.model.dto.SkupstinaDTO;
import application.model.dto.StavkaSkupstinaDTO;
import application.util.AlertDisplay;
import application.util.InputValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.ListView;

import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

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
		btnUkloniStavku.disableProperty().bind(lstListaStavki.getSelectionModel().selectedItemProperty().isNull());
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
			newStage.setTitle("Dodavanje stavke dnevnog reda");
			newStage.show();
		} catch (IOException e) {
			e.printStackTrace();
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
		taTekstDnevnogReda.getChildren().clear();
		for (StavkaSkupstinaDTO s : stavke) {
			Text naslov = new Text(s.getRedniBroj() + ". " + s.getNaslov() + AlertDisplay.NL + AlertDisplay.NL);
			naslov.setStyle("-fx-font-weight: bold");
			Text tekst = new Text("    " + s.getTekst() + AlertDisplay.NL + AlertDisplay.NL);
			taTekstDnevnogReda.getChildren().addAll(naslov, tekst);
		}
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
			} if (uspjesno) {
					for (Iterator<StavkaSkupstinaDTO> it = stavke.iterator(); it.hasNext();) {
						StavkaSkupstinaDTO stavka = it.next();
						DAOFactory.getDAOFactory().getStavkaSkupstinaDAO().insert(skupstina, stavka, tip);
					}
					if(this.tip == StavkaSkupstinaDAO.IZVJESTAJ){
						this.skupstina.setStavkeIzvjestaja(stavke);
					} else{
						parentController.addItem(skupstina);
					}
					parentController.refresh();
					AlertDisplay.showInformation("Uspjesno", "", "Uspjesno dodavanje");
			}
			Stage stage = (Stage) btnSacuvaj.getScene().getWindow();
			stage.close();
		} else {
			AlertDisplay.showInformation("Greska", "", "Niste unijeli datum odrzavanja skupstine.");
		}
	}

	public void dodaj(StavkaSkupstinaDTO stavka) {
		int nextRb = stavke.size() + 1;
		stavka.setRedniBroj(nextRb);
		stavke.add(stavka);
		Text naslov = new Text(stavka.getRedniBroj() + ". " + stavka.getNaslov() + AlertDisplay.NL + AlertDisplay.NL);
		naslov.setStyle("-fx-font-weight: bold");
		Text tekst = new Text("    " + stavka.getTekst() + AlertDisplay.NL + AlertDisplay.NL);
		taTekstDnevnogReda.getChildren().addAll(naslov, tekst);
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
		}
	}
	
	public void setSkupstina(SkupstinaDTO skupstina){
		this.skupstina = skupstina;
	}
	
	private ObservableList<StavkaSkupstinaDTO> stavke = FXCollections.observableArrayList();
	private RadSaSkupstinamaController parentController;
	private int tip;
	private SkupstinaDTO skupstina;
}
