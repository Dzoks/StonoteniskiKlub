package application.gui.sekretar.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dao.SponzorDAO;
import application.model.dao.UgovorDAO;
import application.model.dto.DonacijaDTO;
import application.model.dto.SponzorDTO;
import application.model.dto.UgovorDTO;
import application.util.AlertDisplay;
import application.util.InputValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RadSaSponzorimaController extends BaseController {
	@FXML
	private TableView<SponzorDTO> tblSponzori;
	@FXML
	private TableColumn<SponzorDTO, String> colNaziv;
	@FXML
	private TableColumn<SponzorDTO, String> colAdresa;
	@FXML
	private TableColumn<SponzorDTO, String> colMail;
	@FXML
	private TableColumn<SponzorDTO, String> colTelefon;
	@FXML
	private TableColumn<SponzorDTO, String> colAktivan;
	@FXML
	private RadioButton rbSvi;
	@FXML
	private ToggleGroup grpSvi;
	@FXML
	private RadioButton rbAktivni;
	@FXML
	private RadioButton rbNeaktivni;
	@FXML
	private Button btnPretrazi;
	@FXML
	private TextField txtPretraga;
	@FXML
	private ComboBox<String> cbPretraga;
	@FXML
	private Button btnUgovori;
	@FXML
	private Button btnDodajSponzora;
	@FXML
	private Button btnAzuriraj;
	@FXML
	private Button btnObrisi;
	@FXML
	private Button btnDodajUgovor;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Postavljanje kolona
		colNaziv.setCellValueFactory(new PropertyValueFactory<SponzorDTO, String>("naziv"));
		colAdresa.setCellValueFactory(new PropertyValueFactory<SponzorDTO, String>("adresa"));
		colMail.setCellValueFactory(new PropertyValueFactory<SponzorDTO, String>("email"));
		colTelefon.setCellValueFactory(new PropertyValueFactory<SponzorDTO, String>("telefon"));
		colAktivan.setCellValueFactory(new PropertyValueFactory<SponzorDTO, String>("aktivan"));
		// Ucitavanje inicijalne liste
		SponzorDAO sponzorDAO = DAOFactory.getDAOFactory().getSponzorDAO();
		UgovorDAO ugovorDAO = DAOFactory.getDAOFactory().getUgovorDAO();
		listaSponzora = sponzorDAO.selectAll();
		for (SponzorDTO sponzor : listaSponzora) {
			sponzor.setUgovori(ugovorDAO.selectAllById(sponzor.getId()));
			for(UgovorDTO ugovor : sponzor.getUgovori()){
				ObservableList<DonacijaDTO> donacije = DAOFactory.getDAOFactory().getDonacijaDAO().selectAllById(sponzor.getId(), ugovor.getRedniBroj());
				for(DonacijaDTO donacija : donacije){
					donacija.setSponzor(sponzor);
					donacija.setUgovor(ugovor);
				}
				ugovor.setDonacije(donacije);
			}
		}
		// Setovanje tabele
		tblSponzori.setItems(listaSponzora);
		cbPretraga.setItems(cbItems);
		cbPretraga.getSelectionModel().select(0);
		btnAzuriraj.disableProperty().bind(tblSponzori.getSelectionModel().selectedItemProperty().isNull());
		btnDodajUgovor.disableProperty().bind(tblSponzori.getSelectionModel().selectedItemProperty().isNull());
		btnObrisi.disableProperty().bind(tblSponzori.getSelectionModel().selectedItemProperty().isNull());
		btnUgovori.disableProperty().bind(tblSponzori.getSelectionModel().selectedItemProperty().isNull());
	}

	// Event Listener on RadioButton[#rbSvi].onAction
	@FXML
	public void prikaziSve(ActionEvent event) {
		tblSponzori.setItems(listaSponzora);
	}

	// Event Listener on RadioButton[#rbAktivni].onAction
	@FXML
	public void prikaziAktivne(ActionEvent event) {
		ObservableList<SponzorDTO> filtered = FXCollections.observableArrayList();
		for (SponzorDTO sponzor : listaSponzora) {
			if (sponzor.daLiJeAktivan()) {
				filtered.add(sponzor);
			}
		}
		tblSponzori.setItems(filtered);
	}

	// Event Listener on RadioButton[#rbNeaktivni].onAction
	@FXML
	public void prikaziNeaktivne(ActionEvent event) {
		ObservableList<SponzorDTO> filtered = FXCollections.observableArrayList();
		for (SponzorDTO sponzor : listaSponzora) {
			if (!sponzor.daLiJeAktivan()) {
				filtered.add(sponzor);
			}
		}
		tblSponzori.setItems(filtered);
	}

	// Event Listener on Button[#btnPretrazi].onAction
	@FXML
	public void pretrazi(ActionEvent event) {
		if (InputValidator.allEntered(txtPretraga.getText())) {
			String param = cbPretraga.getSelectionModel().getSelectedItem();
			if ("Naziv".equals(param)) {
				ObservableList<SponzorDTO> result = DAOFactory.getDAOFactory().getSponzorDAO()
						.getByNaziv(txtPretraga.getText());
				for (SponzorDTO sponzor : result) {
					System.out.println(sponzor.getId());
					sponzor.setUgovori(DAOFactory.getDAOFactory().getUgovorDAO().selectAllById(sponzor.getId()));
				}
				tblSponzori.setItems(result);
			}
		} else {
			AlertDisplay.showInformation("Greska", "", "Unesite pojam za pretragu!");
		}
	}

	// Event Listener on Button[#btnUgovori].onAction
	@FXML
	public void pregledajUgovore(ActionEvent event) {
		SponzorDTO sponzor = listaSponzora
				.get(listaSponzora.indexOf(tblSponzori.getSelectionModel().getSelectedItem()));
		Stage newStage = new Stage();
		RadSaUgovorimaController controller = null;
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader()
					.getResource("application/gui/sekretar/view/RadSaUgovorimaView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			controller = loader.<RadSaUgovorimaController>getController();
			Scene scene = new Scene(root, 710, 355);
			controller.setListaUgovora(sponzor.getUgovori());
			newStage.setScene(scene);
			newStage.setResizable(false);
			newStage.setTitle("Stonoteniski klub - Pregled ugovora");
			newStage.show();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	// Event Listener on Button[#btnDodajSponzora].onAction
	@FXML
	public void dodajSponzora(ActionEvent event) {
		rbSvi.setSelected(true);
		prikaziSve(event);
		Stage newStage = new Stage();
		DodavanjeSponzoraController controller = null;
		tblSponzori.getSelectionModel().clearSelection();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader()
					.getResource("application/gui/sekretar/view/DodavanjeSponzoraView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root, 410, 530);
			controller = loader.<DodavanjeSponzoraController>getController();
			controller.setParentController(this);
			controller.setTrenutniSponzor(null);
			newStage.setScene(scene);
			newStage.setResizable(false);
			newStage.setTitle("Stonoteniski klub - Dodavanje sponzora");
			newStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Event Listener on Button[#btnAzuriraj].onAction
	@FXML
	public void azuriraj(ActionEvent event) {
		SponzorDTO odabrani = listaSponzora
				.get(listaSponzora.indexOf(tblSponzori.getSelectionModel().getSelectedItem()));
		rbSvi.setSelected(true);
		prikaziSve(event);
		Stage newStage = new Stage();
		DodavanjeSponzoraController controller = null;
		tblSponzori.getSelectionModel().clearSelection();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader()
					.getResource("application/gui/sekretar/view/DodavanjeSponzoraView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			controller = loader.<DodavanjeSponzoraController>getController();
			Scene scene = new Scene(root, 410, 530);
			controller.setParentController(this);
			controller.setTrenutniSponzor(odabrani);
			newStage.setScene(scene);
			newStage.setResizable(false);
			newStage.setTitle("Stonoteniski klub - Azuriranje sponzora");
			newStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Event Listener on Button[#btnObrisi].onAction
	@FXML
	public void obrisi(ActionEvent event) {
	}

	// Event Listener on Button[#btnDodajUgovor].onAction
	@FXML
	public void dodajUgovor(ActionEvent event) {
		SponzorDTO odabrani = listaSponzora
				.get(listaSponzora.indexOf(tblSponzori.getSelectionModel().getSelectedItem()));
		Stage newStage = new Stage();
		DodavanjeUgovoraController controller = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader()
					.getResource("application/gui/sekretar/view/DodavanjeUgovoraView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			controller = loader.<DodavanjeUgovoraController>getController();
			Scene scene = new Scene(root, 600, 650);
			controller.setSponzor(odabrani);
			newStage.setScene(scene);
			newStage.setResizable(false);
			newStage.setTitle("Stonoteniski klub - Dodavanje ugovora");
			newStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void deselect() {
		tblSponzori.getSelectionModel().clearSelection();
	}

	public void dodajSponzora(SponzorDTO sponzor) {
		listaSponzora.add(sponzor);
	}

	public void zamijeni(SponzorDTO sponzor) {
		int index = listaSponzora.indexOf(sponzor);
		listaSponzora.remove(index);
		listaSponzora.add(index, sponzor);
	}

	private ObservableList<SponzorDTO> listaSponzora;
	private static ObservableList<String> cbItems = FXCollections.observableArrayList();
	static {
		cbItems.add("Naziv");
	}
}
