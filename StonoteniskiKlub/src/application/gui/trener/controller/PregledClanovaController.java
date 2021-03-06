package application.gui.trener.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.ClanDTO;
import application.model.dto.ClanarinaDTO;
import application.model.dto.RegistracijaDTO;
import application.model.helper.Rezultat;
import application.util.AlertDisplay;
import application.util.ErrorLogger;
import application.util.ListUpdater;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PregledClanovaController extends BaseController implements Initializable {

	@FXML
	private TableView<ClanDTO> twTabela;

	@FXML
	private TableColumn<ClanDTO, String> tcIme;

	@FXML
	private TableColumn<ClanDTO, String> tcPrezime;

	@FXML
	private TableColumn<ClanDTO, String> tcImeRoditelja;

	@FXML
	private TableColumn<ClanDTO, Boolean> tcAktivan;

	@FXML
	private TableColumn<ClanDTO, Boolean> tcRegistrovan;

	@FXML
	private MenuItem miIzmjeni;

	@FXML
	private MenuItem miIsclani;

	@FXML
	private TextField txtIme;

	@FXML
	private TextField txtPrezime;

	@FXML
	private ImageView ivFotografija;

	@FXML
	private Label lblIme;

	@FXML
	private Label lblPrezime;

	@FXML
	private Label lblImeRoditelja;

	@FXML
	private ListView<String> lvListaTelefona;

	@FXML
	private Label lblPol;

	@FXML
	private Label lblDatumRodjenja;

	@FXML
	private MenuItem miTreninzi;

	@FXML
	private Label lblTurniri;

	@FXML
	private TableView<Rezultat> tblTurniri;

	@FXML
	private TableColumn<Rezultat, String> clnTurnir;

	@FXML
	private TableColumn<Rezultat, Integer> clnBodovi;

	@FXML
	private Label lblBodoviTxt;

	@FXML
	private Label lblPozicijaTxt;

	@FXML
	private Label lblBodovi;

	@FXML
	private Label lblPozicija;

	@FXML
	private ChoiceBox<RegistracijaDTO> cbxSezona;
	@FXML
	private Button btnRegistracija;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnRegistracija.disableProperty().bind(twTabela.getSelectionModel().selectedItemProperty().isNull());
		tcIme.setCellFactory(TextFieldTableCell.forTableColumn());
		tcIme.setCellValueFactory(new PropertyValueFactory<ClanDTO, String>("ime"));
		
		tcPrezime.setCellFactory(TextFieldTableCell.forTableColumn());
		tcPrezime.setCellValueFactory(new PropertyValueFactory<ClanDTO, String>("prezime"));

		tcImeRoditelja.setCellFactory(TextFieldTableCell.forTableColumn());
		tcImeRoditelja.setCellValueFactory(new PropertyValueFactory<ClanDTO, String>("imeRoditelja"));

		tcAktivan.setCellValueFactory(new PropertyValueFactory<ClanDTO, Boolean>("aktivan"));
		tcAktivan.setVisible(false);
		tcAktivan.setCellFactory(col -> new TableCell<ClanDTO, Boolean>() {
			@Override
			protected void updateItem(Boolean item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? null : item ? "Da" : "Ne");
			}
		});

		tcRegistrovan.setCellValueFactory(new PropertyValueFactory<ClanDTO, Boolean>("registrovan"));
		tcRegistrovan.setVisible(false);
		tcRegistrovan.setCellFactory(col -> new TableCell<ClanDTO, Boolean>() {
			@Override
			protected void updateItem(Boolean item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? null : item ? "Da" : "Ne");
			}
		});

		listaClanova = FXCollections.observableArrayList();
		listaClanova.addAll(DAOFactory.getDAOFactory().getClanDAO().selectAll());
		twTabela.setItems(listaClanova);

		ivFotografija.setImage(new Image(getClass().getResourceAsStream("/avatar.png")));

		// registracija
		setVidljivostZaIgraca(false);
		clnTurnir.setCellValueFactory(new PropertyValueFactory<>("turnir"));
		clnBodovi.setCellValueFactory(new PropertyValueFactory<>("bodovi"));
		cbxSezona.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<RegistracijaDTO>() {

			@Override
			public void changed(ObservableValue<? extends RegistracijaDTO> observable, RegistracijaDTO oldValue,
					RegistracijaDTO newValue) {
				if (newValue == null) {
					lblBodovi.setText("0");
					lblPozicija.setText("Nepoznato");
					tblTurniri.setItems(FXCollections.observableArrayList());
				} else {
					ObservableList<Rezultat> list = FXCollections.observableArrayList();
					HashMap<String, Integer> rezultati = newValue.getRezultati();
					Set<Entry<String, Integer>> set = rezultati.entrySet();
					Integer plasman = rezultati.get("Plasman");
					Integer ukupno = rezultati.get("Ukupno");
					if (plasman != 0)
						lblPozicija.setText(plasman.toString());
					if (ukupno != 0)
						lblBodovi.setText(ukupno.toString());
					for (Entry<String, Integer> entry : set) {
						if (!entry.getKey().equals("Plasman") && !entry.getKey().equals("Ukupno")
								&& entry.getValue() != 0)
							list.add(new Rezultat(entry.getKey(), entry.getValue()));
					}
					tblTurniri.setItems(list);
				}

			}
		});
	}

	private void setVidljivostZaIgraca(boolean uslov) {
		lblBodovi.setVisible(uslov);
		lblBodoviTxt.setVisible(uslov);
		lblPozicija.setVisible(uslov);
		lblPozicijaTxt.setVisible(uslov);
		lblTurniri.setVisible(uslov);
		tblTurniri.setVisible(uslov);
		cbxSezona.setVisible(uslov);
	}

	@FXML
	void odjaviteSe(ActionEvent event) {
		try {
			BaseController.changeScene("/application/gui/administrator/view/LoginView.fxml", primaryStage);
		} catch (IOException e) {
			 ;
			new ErrorLogger().log(e);
		}
	}

	public void idiNaPregledOpreme() {
		Stage noviStage = new Stage();

		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getClassLoader().getResource("application/gui/trener/view/OpremaGlavniProzor.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root, 761, 484);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub");
			noviStage.show();
		} catch (IOException e) {
			 ;
			new ErrorLogger().log(e);
		}
	}

	public void idiNaPregledNarudzbi() {
		Stage noviStage = new Stage();

		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getClassLoader().getResource("application/gui/sekretar/view/NarudzbaGlavniProzor.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root, 761, 484);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub");
			noviStage.show();
		} catch (IOException e) {
			 ;
			new ErrorLogger().log(e);
		}
	}

	public void uclanjivanje() {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(
					getClass().getClassLoader().getResource("application/gui/trener/view/UclanjivanjeView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			UclanjivanjeController control = loader.<UclanjivanjeController>getController();
			control.setPrimaryStage(stage);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Stonoteniski klub");
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					event.consume();
					control.izlaz();
				}
			});
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
			ClanDTO clan = control.getClan();
			if (clan != null)
				listaClanova.add(clan);
		} catch (Exception e) {
			 ;
			new ErrorLogger().log(e);
		}
	}

	public void izmjeniClana() {
		ClanDTO clan = twTabela.getSelectionModel().getSelectedItem();
		if (clan == null)
			return;
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(
					getClass().getClassLoader().getResource("application/gui/trener/view/IzmjenaClanaView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			IzmjenaClanaController control = loader.<IzmjenaClanaController>getController();
			control.setClan(clan);
			control.popuniPolja();
			control.setPrimaryStage(stage);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Stonoteniski Klub");
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					event.consume();
					control.izlaz();
				}
			});
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();

		} catch (Exception e) {
			 ;
			new ErrorLogger().log(e);
		}
	}

	public void izvrsiIsclanjivanje() {
		ClanDTO clan = twTabela.getSelectionModel().getSelectedItem();
		if (clan == null)
			return;

		// provjriti da li je AKTIVAN, ako nije ERROR
		if (!clan.isAktivan()) {
			AlertDisplay.showError("Iščlanjivanje",
					"Odabrani član nije aktivan. Nemoguće je izvršiti njegovo iščlanjivanje.");
			return;
		}

		// provjeriti da li je uplatio sve clanarine do tad, ako nije ERROR
		List<ClanarinaDTO> list = DAOFactory.getDAOFactory().getClanarinaDAO().selectByClanID(clan.getId());
		if (list.size() == 0) {
			Optional<ButtonType> tmp = AlertDisplay.showWarning("Iščlanjivanje",
					"Odabrani član nije uplatio ni jednu članarinu. " + " Da li želite da nastavite?");
			if (tmp.isPresent() && tmp.get().getButtonData() == ButtonData.NO) {
				return;
			}
		} else {
			ClanarinaDTO max = list.get(0);
			for (int i = 1; i < list.size(); i++) {
				if (list.get(i).getDatum().after(max.getDatum()))
					max = list.get(i);
			}
			DateFormat df = new SimpleDateFormat("dd.MM.yyyy.");
			Optional<ButtonType> tmp = AlertDisplay.showWarning("Iščlanjivanje",
					"Posljednja uplata članarine od strane odabranog člana izvršena je za mjesec "
							+ max.getNazivMjeseca() + ", godine " + max.getGodina().getValue() + ". "
							+ " Da li želite da nastavite?");
			if (tmp.isPresent() && tmp.get().getButtonData() == ButtonData.NO) {
				return;
			}
		}

		// ako je sve u redu:
		// * resetovati AKTIVAN
		// * setovati DATUM_DO u clanstvu na trenutni datum
		clan.setAktivan(false);
		DAOFactory.getDAOFactory().getClanDAO().setAktivan(false, clan.getId());
		DAOFactory.getDAOFactory().getClanstvoDAO().update(clan.getId());
	}

	public void izdavanjePotvrda() {
		ClanDTO clan = twTabela.getSelectionModel().getSelectedItem();
		if (clan == null)
			return;
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(
					getClass().getClassLoader().getResource("application/gui/trener/view/IzdavanjePotvrdaView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			IzdavanjePotvrdaController control = loader.<IzdavanjePotvrdaController>getController();
			control.setClan(clan);
			control.setPrimaryStage(stage);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Stonoteniski klub");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();

		} catch (Exception e) {
			 ;
			new ErrorLogger().log(e);
		}
	}

	public void pretragaClanova() {
		String ime = txtIme.getText();
		String prezime = txtPrezime.getText();

		if ("".equals(ime) && "".equals(prezime)) {
			listaClanova = FXCollections.observableArrayList();
			listaClanova.addAll(DAOFactory.getDAOFactory().getClanDAO().selectAll());
			twTabela.setItems(listaClanova);
			return;
		}

		listaClanova = FXCollections.observableArrayList();
		listaClanova.addAll(DAOFactory.getDAOFactory().getClanDAO().selectAllByImePrezime(ime, prezime));
		twTabela.setItems(listaClanova);
		return;
	}

	@FXML
	void otvoriTreninge(ActionEvent event) {
		Stage trening = new Stage();
		TreningController controller = null;
		trening.setTitle("Stonoteniski klub");
		trening.initModality(Modality.APPLICATION_MODAL);

		try {
			controller = (TreningController) BaseController.changeScene("/application/gui/trener/view/TreningView.fxml",
					trening);
			controller.setClan(twTabela.getSelectionModel().getSelectedItem().getId());
			trening.setResizable(false);
			trening.showAndWait();
		} catch (IOException e) {
			new ErrorLogger().log(e);
			 ;
		}
	}

	public void prikaziDetaljeOClanu() {
		ClanDTO clan = twTabela.getSelectionModel().getSelectedItem();

		if (clan == null)
			return;
		// Provjera da li je igrac
		if (clan.isRegistrovan()) {
			cbxSezona.setItems(DAOFactory.getDAOFactory().getRegistracijaDAO().getAllByMember(clan));
			setVidljivostZaIgraca(true);
		} else {
			setVidljivostZaIgraca(false);
			cbxSezona.setItems(FXCollections.observableArrayList());
		}
		lblIme.setText(clan.getIme());
		lblPrezime.setText(clan.getPrezime());
		lblImeRoditelja.setText(clan.getImeRoditelja().equals("") ? "---" : clan.getImeRoditelja());
		lvListaTelefona.setItems(FXCollections.observableArrayList(clan.getTelefoni()));
		lblPol.setText(clan.getPol().equals('M') ? "Muški" : "Ženski");
		// lblDatumRodjenja.setText(clan.getDatumRodjenja().toString());

		DateFormat df = new SimpleDateFormat("dd.MM.yyyy.");
		lblDatumRodjenja.setText(df.format(clan.getDatumRodjenja()));

		try {
			java.sql.Blob blob = clan.getSlika();
			if (blob != null) {
				ivFotografija.setImage(new Image(blob.getBinaryStream()));
			} else {
				ivFotografija.setImage(new Image(getClass().getResourceAsStream("/avatar.png")));
			}
		} catch (SQLException e) {
			 ;
			new ErrorLogger().log(e);
		}
	}

	@FXML
	void registrujIgracaKlik(ActionEvent event) {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		RegistracijaController controller = null;
		try {
			controller = (RegistracijaController) BaseController
					.changeScene("/application/gui/trener/view/RegistracijaView.fxml", stage);
			controller.setClan(twTabela.getSelectionModel().getSelectedItem());
			stage.setResizable(false);
			stage.showAndWait();
			listaClanova = FXCollections.observableArrayList();
			listaClanova.addAll(DAOFactory.getDAOFactory().getClanDAO().selectAll());
			twTabela.setItems(listaClanova);
		} catch (IOException e) {
			new ErrorLogger().log(e);
			 ;
		}
	}

	public void preuzmiRezultate() {
		new Thread(new ListUpdater()).start();
	}

	private ObservableList<ClanDTO> listaClanova;
}
