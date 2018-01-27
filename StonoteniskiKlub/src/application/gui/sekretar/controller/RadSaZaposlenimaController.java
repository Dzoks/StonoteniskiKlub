package application.gui.sekretar.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.ZaposleniDTO;
import application.util.AlertDisplay;
import application.util.Checker;
import application.util.ErrorLogger;
import application.util.Finder;
import application.util.ZaposleniImeChecker;
import application.util.ZaposleniJMBChecker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RadSaZaposlenimaController extends BaseController {
	@FXML
	private TableView<ZaposleniDTO> tblZaposleni;
	@FXML
	private TableColumn<ZaposleniDTO, String> colJMB;
	@FXML
	private TableColumn<ZaposleniDTO, String> colIme;
	@FXML
	private TableColumn<ZaposleniDTO, String> colImeRoditelja;
	@FXML
	private TableColumn<ZaposleniDTO, String> colPrezime;
	@FXML
	private TableColumn<ZaposleniDTO, String> colZaposlenOd;
	@FXML
	private TableColumn<ZaposleniDTO, String> colZaposlenDo;
	@FXML
	private TableColumn<ZaposleniDTO, Double> colPlata;
	@FXML
	private TableColumn<ZaposleniDTO, String> colRadnoMjesto;
	@FXML
	private ImageView imgFotografija;
	@FXML
	private Button btnDodajZaposlenje;
	@FXML
	private Button btnAzuriraj;
	@FXML
	private Button btnObrisi;
	@FXML
	private Button btnPregledZaposlenja;
	@FXML
	private Button btnDodajZaposlenog;
	@FXML
	private ComboBox<Checker<ZaposleniDTO>> cbTipPretrage;
	@FXML
	private TextField txtVrijednost;
	@FXML
	private Button btnPretrazi;
	@FXML
	private ComboBox<String> cbTip;
	@FXML
	private Button btnOsvjezi;
	@FXML
	private ListView<String> lstTelefoni;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buildTable();
		populateTable();
		populateComboBox();
		bindDisable();
		loadImage();
	}

	@FXML
	public void filtriraj(ActionEvent event) {
		String izbor = cbTip.getSelectionModel().getSelectedItem();
		if ("Svi".equals(izbor)) {
			tblZaposleni.setItems(DAOFactory.getDAOFactory().getZaposleniDAO().selectAll());
		} else if ("Aktivni".equals(izbor)) {
			tblZaposleni.setItems(DAOFactory.getDAOFactory().getZaposleniDAO().selectAktivni(true));
		} else {
			tblZaposleni.setItems(DAOFactory.getDAOFactory().getZaposleniDAO().selectAktivni(false));
		}
	}

	@FXML
	public void osvjezi(ActionEvent event) {
		cbTip.getSelectionModel().select(0);
		cbTip.fireEvent(event);
	}

	// Event Listener on AnchorPane.onMouseClicked
	@FXML
	public void deselect(MouseEvent event) {
		tblZaposleni.getSelectionModel().clearSelection();
	}

	@FXML
	void odjaviteSe(ActionEvent event) {
		try {
			BaseController.changeScene("/application/gui/administrator/view/LoginView.fxml", primaryStage);
		} catch (IOException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		}
	}

	// Event Listener on TableView[#tblZaposleni].onMouseClicked
	@FXML
	public void prikaziSliku(MouseEvent event) {
		ZaposleniDTO zap = tblZaposleni.getSelectionModel().getSelectedItem();
		if (zap != null) {
			setImage(zap.getSlika());
			setTelefoni(zap);
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void dodajZaposlenog(ActionEvent event) {
		Stage newStage = new Stage();
		DodavanjeZaposlenogController controller = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader()
					.getResource("application/gui/sekretar/view/DodavanjeZaposlenogView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root, 511, 479);
			controller = loader.<DodavanjeZaposlenogController>getController();
			controller.setParent(this);
			controller.setTip(DodavanjeZaposlenogController.DODAVANJE_ZAPOSLENOG);
			newStage.setScene(scene);
			newStage.setResizable(false);
			newStage.setTitle("Stonoteniski klub");
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		}
	}

	// Event Listener on Button[#btnDodajZaposlenje].onAction
	@FXML
	public void dodajZaposlenje(ActionEvent event) {
		ZaposleniDTO zaposleni = tblZaposleni.getSelectionModel().getSelectedItem();
		Stage newStage = new Stage();
		DodavanjeZaposlenogController controller = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader()
					.getResource("application/gui/sekretar/view/DodavanjeZaposlenogView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root, 511, 479);
			controller = loader.<DodavanjeZaposlenogController>getController();
			controller.setParent(this);
			controller.setTip(DodavanjeZaposlenogController.DODAVANJE_ZAPOSLENJA);
			controller.setZaposleni(zaposleni);
			newStage.setScene(scene);
			newStage.setResizable(false);
			newStage.setTitle("Stonoteniski klub");
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		}
	}

	// Event Listener on Button[#btnAzuriraj].onAction
	@FXML
	public void azuriraj(ActionEvent event) {
		ZaposleniDTO zaposleni = tblZaposleni.getSelectionModel().getSelectedItem();
		Stage newStage = new Stage();
		DodavanjeZaposlenogController controller = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader()
					.getResource("application/gui/sekretar/view/DodavanjeZaposlenogView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root, 511, 479);
			controller = loader.<DodavanjeZaposlenogController>getController();
			controller.setParent(this);
			controller.setTip(DodavanjeZaposlenogController.AZURIRANJE_ZAPOSLENOG);
			controller.setZaposleni(zaposleni);
			newStage.setScene(scene);
			newStage.setResizable(false);
			newStage.setTitle("Stonoteniski klub");
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		}
	}

	// Event Listener on Button[#btnObrisi].onAction
	@FXML
	public void obrisi(ActionEvent event) {
		ZaposleniDTO zaposleni = tblZaposleni.getSelectionModel().getSelectedItem();
		if(zaposleni != null){
			if(DAOFactory.getDAOFactory().getZaposleniDAO().delete(zaposleni)){
				int index = this.zaposleni.indexOf(zaposleni);
				if(index >= 0){
					this.zaposleni.remove(0);
				}
				index = tblZaposleni.getItems().indexOf(zaposleni);
				if(index >= 0){
					tblZaposleni.getItems().remove(index);
				}
				AlertDisplay.showInformation("Brisanje", "Brisanje uspješno!");
				zaposleni = tblZaposleni.getSelectionModel().getSelectedItem();
				if(zaposleni != null){
					setImage(zaposleni.getSlika());
				}
			}
		}
	}

	// Event Listener on Button[#btnPregledZaposlenja].onAction
	@FXML
	public void pregledajZaposlenja(ActionEvent event) {
		ZaposleniDTO zaposleni = tblZaposleni.getSelectionModel().getSelectedItem();
		Stage newStage = new Stage();
		RadSaZaposlenjimaController controller = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader()
					.getResource("application/gui/sekretar/view/RadSaZaposlenjimaView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root, 600, 300);
			controller = loader.<RadSaZaposlenjimaController>getController();
			controller.setZaposleni(zaposleni);
			newStage.setScene(scene);
			newStage.setResizable(false);
			newStage.setTitle("Stonoteniski klub - pregled zaposlenja");
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		}
	}

	@FXML
	public void pretrazi(ActionEvent event) {
		cbTipPretrage.getSelectionModel().getSelectedItem().setTarget(txtVrijednost.getText());
		List<ZaposleniDTO> result = Finder.findAll(tblZaposleni.getItems(),
				cbTipPretrage.getSelectionModel().getSelectedItem());
		tblZaposleni.getItems().clear();
		tblZaposleni.getItems().addAll(result);
	}

	public void dodajZaposlenog(ZaposleniDTO zaposleni) {
		if("Svi".equals(cbTip.getSelectionModel().getSelectedItem()) ||
				("Aktivni".equals(cbTip.getSelectionModel().getSelectedItem()) && zaposleni.isAktivan()) ||
				("Neaktivni".equals(cbTip.getSelectionModel().getSelectedItem()) && !zaposleni.isAktivan())){
			tblZaposleni.getItems().add(zaposleni);
		}
	}

	public void zamijeni(ZaposleniDTO zaposleni) {
		int index = tblZaposleni.getItems().indexOf(zaposleni);
		if (index >= 0) {
			tblZaposleni.getItems().remove(index);
			tblZaposleni.getItems().add(index, zaposleni);
			tblZaposleni.getSelectionModel().select(index);
			tblZaposleni.refresh();
			setImage(zaposleni.getSlika());
			setTelefoni(zaposleni);
		}
	}

	private Image defaultImage;
	private ObservableList<ZaposleniDTO> zaposleni;
	private static List<String> tipovi = new ArrayList<String>();
	static {
		tipovi.add("Svi");
		tipovi.add("Aktivni");
		tipovi.add("Neaktivni");
	}

	private void buildTable() {
		colJMB.setCellValueFactory(new PropertyValueFactory<ZaposleniDTO, String>("jmb"));
		colIme.setCellValueFactory(new PropertyValueFactory<ZaposleniDTO, String>("ime"));
		colImeRoditelja.setCellValueFactory(new PropertyValueFactory<ZaposleniDTO, String>("imeRoditelja"));
		colPrezime.setCellValueFactory(new PropertyValueFactory<ZaposleniDTO, String>("prezime"));
		colZaposlenOd.setCellValueFactory(new PropertyValueFactory<ZaposleniDTO, String>("datumOd"));
		colZaposlenDo.setCellValueFactory(new PropertyValueFactory<ZaposleniDTO, String>("datumDo"));
		colPlata.setCellValueFactory(new PropertyValueFactory<ZaposleniDTO, Double>("plata"));
		colRadnoMjesto.setCellValueFactory(new PropertyValueFactory<ZaposleniDTO, String>("radnoMjesto"));
	}

	private void populateTable() {
		this.zaposleni = DAOFactory.getDAOFactory().getZaposleniDAO().selectAll();
		tblZaposleni.setItems(this.zaposleni);
	}

	private void bindDisable() {
		btnAzuriraj.disableProperty().bind(tblZaposleni.getSelectionModel().selectedItemProperty().isNull());
		btnDodajZaposlenje.disableProperty().bind(tblZaposleni.getSelectionModel().selectedItemProperty().isNull());
		btnObrisi.disableProperty().bind(tblZaposleni.getSelectionModel().selectedItemProperty().isNull());
		btnPregledZaposlenja.disableProperty().bind(tblZaposleni.getSelectionModel().selectedItemProperty().isNull());
		btnPretrazi.disableProperty().bind(txtVrijednost.textProperty().isEmpty());
	}

	private void setImage(Blob image) {
		try {
			if (image != null) {
				imgFotografija.setImage(new Image(image.getBinaryStream()));
			} else {
				imgFotografija.setImage(defaultImage);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		}
	}

	private static List<Checker<ZaposleniDTO>> checkers = new ArrayList<Checker<ZaposleniDTO>>();
	static {
		checkers.add(new ZaposleniJMBChecker(""));
		checkers.add(new ZaposleniImeChecker(""));
	}

	private void populateComboBox() {
		ObservableList<Checker<ZaposleniDTO>> cbCheckers = FXCollections.observableArrayList();
		cbCheckers.addAll(checkers);
		ObservableList<String> tipoviOL = FXCollections.observableArrayList();
		tipoviOL.addAll(tipovi);
		this.cbTipPretrage.setItems(cbCheckers);
		this.cbTipPretrage.getSelectionModel().select(0);
		this.cbTip.setItems(tipoviOL);
		this.cbTip.getSelectionModel().select(0);
	}

	private void loadImage() {
		try {
			defaultImage = new Image(new FileInputStream("resources/avatar.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		}
	}
	private void setTelefoni(ZaposleniDTO zap){
		lstTelefoni.getItems().clear();
		ObservableList<String> telefoni = FXCollections.observableArrayList();
		telefoni.addAll(zap.getTelefoni());
		lstTelefoni.setItems(telefoni);
	}
}
