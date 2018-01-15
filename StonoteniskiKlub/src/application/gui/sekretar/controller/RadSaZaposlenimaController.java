package application.gui.sekretar.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.ZaposleniDTO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buildTable();
		populateTable();
		bindDisable();
		try {
			defaultImage = new Image(new FileInputStream("src/resources/avatar.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Event Listener on AnchorPane.onMouseClicked
	@FXML
	public void deselect(MouseEvent event) {
		tblZaposleni.getSelectionModel().clearSelection();
	}

	// Event Listener on TableView[#tblZaposleni].onMouseClicked
	@FXML
	public void prikaziSliku(MouseEvent event) {
		ZaposleniDTO zap = tblZaposleni.getSelectionModel().getSelectedItem();
		if (zap != null) {
			setImage(zap.getSlika());
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
			newStage.setTitle("Stonoteniski klub - Dodavanje zaposlenog");
			newStage.show();
		} catch (IOException e) {
			e.printStackTrace();
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
			newStage.setTitle("Stonoteniski klub - Dodavanje zaposlenja");
			newStage.show();
		} catch (IOException e) {
			e.printStackTrace();
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
			newStage.setTitle("Stonoteniski klub - Azuriranje zaposlenog");
			newStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Event Listener on Button[#btnObrisi].onAction
	@FXML
	public void obrisi(ActionEvent event) {
		// TODO Autogenerated
	}

	// Event Listener on Button[#btnPregledZaposlenja].onAction
	@FXML
	public void pregledajZaposlenja(ActionEvent event) {
		// TODO Autogenerated
	}

	public void dodajZaposlenog(ZaposleniDTO zaposleni) {
		tblZaposleni.getItems().add(zaposleni);
	}

	public void zamijeni(ZaposleniDTO zaposleni) {
		int index = tblZaposleni.getItems().indexOf(zaposleni);
		if (index >= 0) {
			tblZaposleni.getItems().remove(index);
			tblZaposleni.getItems().add(index, zaposleni);
			tblZaposleni.getSelectionModel().select(index);
			tblZaposleni.refresh();
			setImage(zaposleni.getSlika());
		}
	}

	private Image defaultImage;
	private ObservableList<ZaposleniDTO> zaposleni;

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
		tblZaposleni.setItems(DAOFactory.getDAOFactory().getZaposleniDAO().selectAktivni());
	}

	private void bindDisable() {
		btnAzuriraj.disableProperty().bind(tblZaposleni.getSelectionModel().selectedItemProperty().isNull());
		btnDodajZaposlenje.disableProperty().bind(tblZaposleni.getSelectionModel().selectedItemProperty().isNull());
		btnObrisi.disableProperty().bind(tblZaposleni.getSelectionModel().selectedItemProperty().isNull());
		btnPregledZaposlenja.disableProperty().bind(tblZaposleni.getSelectionModel().selectedItemProperty().isNull());
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
		}
	}
}
