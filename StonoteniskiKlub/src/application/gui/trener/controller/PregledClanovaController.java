package application.gui.trener.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dao.RegistracijaDAO;
import application.model.dto.ClanDTO;
import application.model.dto.ClanarinaDTO;
import application.model.dto.RegistracijaDTO;
import application.model.helper.Rezultat;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.TableCell;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
		        super.updateItem(item, empty) ;
		        setText(empty ? null : item ? "Da" : "Ne" );
		    }
		});

		tcRegistrovan.setCellValueFactory(new PropertyValueFactory<ClanDTO, Boolean>("registrovan"));
		tcRegistrovan.setVisible(false);
		tcRegistrovan.setCellFactory(col -> new TableCell<ClanDTO, Boolean>() {
		    @Override
		    protected void updateItem(Boolean item, boolean empty) {
		        super.updateItem(item, empty) ;
		        setText(empty ? null : item ? "Da" : "Ne" );
		    }
		});

		listaClanova = FXCollections.observableArrayList();
		listaClanova.addAll(DAOFactory.getDAOFactory().getClanDAO().selectAll());
		twTabela.setItems(listaClanova);

		ivFotografija.setImage(new Image(getClass().getResourceAsStream("/resources/avatar.png")));

		// registracija
		setVidljivostZaIgraca(false);
		clnTurnir.setCellValueFactory(new PropertyValueFactory<>("turnir"));
		clnBodovi.setCellValueFactory(new PropertyValueFactory<>("bodovi"));
		cbxSezona.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<RegistracijaDTO>() {

			@Override
			public void changed(ObservableValue<? extends RegistracijaDTO> observable, RegistracijaDTO oldValue,
					RegistracijaDTO newValue) {
				if (newValue==null) {
					lblBodovi.setText("0");
					lblPozicija.setText("Nepoznato");
					tblTurniri.setItems(FXCollections.observableArrayList());
				}else {
					ObservableList<Rezultat> list=FXCollections.observableArrayList();
					HashMap<String,Integer> rezultati=newValue.getRezultati();
					Set<Entry<String,Integer>> set=rezultati.entrySet();
					Integer plasman=rezultati.get("Plasman");
					Integer ukupno=rezultati.get("Ukupno");
					if (plasman!=0)
						lblPozicija.setText(plasman.toString());
					if (ukupno!=0)
						lblBodovi.setText(ukupno.toString());
					for (Entry<String,Integer> entry:set) {
						if (!entry.getKey().equals("Plasman")&&!entry.getKey().equals("Ukupno")&&entry.getValue()!=0)
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
		}
	}

	public void izmjeniClana() {
		ClanDTO clan = twTabela.getSelectionModel().getSelectedItem();
		if(clan == null)
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
			e.printStackTrace();
		}
	}

	public void izvrsiIsclanjivanje() {
		ClanDTO clan = twTabela.getSelectionModel().getSelectedItem();
		if(clan == null)
			return;
		
		// provjriti da li je AKTIVAN, ako nije ERROR
		if(!clan.isAktivan()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Greška");
			alert.setHeaderText("Greška prilikom iščlanjivanja");
			alert.setContentText("Odabrani član nije aktivan. Nemoguće je izvršiti njegovo iščlanjivanje.");
			alert.getButtonTypes().clear();
			alert.getButtonTypes().add(ButtonType.OK);
			alert.getButtonTypes().add(ButtonType.CANCEL);
			alert.show();
			return;
		}
		
		// provjeriti da li je uplatio sve clanarine do tad, ako nije ERROR
		List<ClanarinaDTO> list = DAOFactory.getDAOFactory().getClanarinaDAO().selectByClanID(clan.getId());
		if(list.size() == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Upozorenje");
			alert.setHeaderText("Upozorenje prilikom iščlanjivanja");
			alert.setContentText("Odabrani član nije uplatio ni jednu članarinu. "
					+ " Da li želite da nastavite?");
			alert.getButtonTypes().clear();
			alert.getButtonTypes().add(ButtonType.YES);
			alert.getButtonTypes().add(ButtonType.NO);
			Optional<ButtonType> tmp = alert.showAndWait();
			if(tmp.isPresent() && tmp.get() == ButtonType.NO) {
				return;
			}
		}
		else {
			ClanarinaDTO max = list.get(0);
			for(int i = 1; i<list.size(); i++) {
				if(list.get(i).getDatum().after(max.getDatum()))
					max = list.get(i);
			}
			DateFormat df = new SimpleDateFormat("dd.MM.yyyy.");
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Upozorenje");
			alert.setHeaderText("Upozorenje prilikom učlanjivanja");
			alert.setContentText("Posljednja uplata članarine od strane odabranog člana izvršena je za mjesec "
					+ max.getNazivMjeseca() +
					", godine " + max.getGodina().getValue() + ". "
					+ " Da li želite da nastavite?");
			alert.getButtonTypes().clear();
			alert.getButtonTypes().add(ButtonType.YES);
			alert.getButtonTypes().add(ButtonType.NO);
			Optional<ButtonType> tmp = alert.showAndWait();
			if(tmp.isPresent() && tmp.get() == ButtonType.NO) {
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
		try {
			controller = (TreningController) BaseController.changeScene("/application/gui/trener/view/TreningView.fxml",
					trening);
			controller.setClan(twTabela.getSelectionModel().getSelectedItem().getId());
			trening.setResizable(false);
			trening.show();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void prikaziDetaljeOClanu() {
		ClanDTO clan = twTabela.getSelectionModel().getSelectedItem();
		
		if(clan == null)
			return;
		// Provjera da li je igrac
		if (clan.isRegistrovan()) {
			cbxSezona.setItems(RegistracijaDAO.getAllByMember(clan));
			setVidljivostZaIgraca(true);
		}else {
			setVidljivostZaIgraca(false);
			cbxSezona.setItems(FXCollections.observableArrayList());
		}
		lblIme.setText(clan.getIme());
		lblPrezime.setText(clan.getPrezime());
		lblImeRoditelja.setText(clan.getImeRoditelja().equals("") ? "---" : clan.getImeRoditelja());
		lvListaTelefona.setItems(FXCollections.observableArrayList(clan.getTelefoni()));
		lblPol.setText(clan.getPol().equals('M') ? "Muški" : "Ženski");
		// lblDatumRodjenja.setText(clan.getDatumRodjenja().toString());

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		formatter = formatter.withLocale(Locale.US); // Locale specifies human language for translating, and cultural
														// norms for lowercase/uppercase and abbreviations and such.
														// Example: Locale.US or Locale.CANADA_FRENCH
		LocalDate date = null;
		try {
			date = LocalDate.parse(clan.getDatumRodjenja().toString(), formatter);
		} catch (DateTimeParseException e) {
			date = clan.getDatumRodjenja().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}

		lblDatumRodjenja.setText(date.toString());

		try {
			java.sql.Blob blob = clan.getSlika();
			if (blob != null) {
				ivFotografija.setImage(new Image(blob.getBinaryStream()));
			} else {
				ivFotografija.setImage(new Image(getClass().getResourceAsStream("/resources/avatar.png")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    @FXML
    void registrujIgracaKlik(ActionEvent event) {
    	Stage stage = new Stage();
		RegistracijaController controller = null;
		try {
			controller = (RegistracijaController) BaseController.changeScene("/application/gui/trener/view/RegistracijaView.fxml",
					stage);
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
    


	public void preuzmiRezultate() {
		new Thread(new ListUpdater()).start();
	}
	private ObservableList<ClanDTO> listaClanova;
}
