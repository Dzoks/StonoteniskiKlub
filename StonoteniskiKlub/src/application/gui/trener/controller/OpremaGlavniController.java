package application.gui.trener.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.OpremaClana;
import application.model.dto.OpremaKluba;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class OpremaGlavniController extends BaseController implements Initializable {

	@FXML
	private Button btnPretraziOpremaKluba;
	@FXML
	private Button btnDodajOpremaKluba;
	@FXML
	private Button btnIzmjeniOpremuKluba;
	@FXML
	private Button btnPretraziOpremaClanova;
	@FXML
	private Button btnDodajOpremaClanova;
	@FXML
	private Button btnIzmjeniOpremuClana;
	@FXML
	private TableView<OpremaKluba> tblOpremaKluba;
	@FXML
	private TableColumn<OpremaKluba, Integer> idKlub;
	@FXML
	private TableColumn<OpremaKluba, String> tipKlub;
	@FXML
	private TableColumn<OpremaKluba, String> proizvodjacKlub;
	@FXML
	private TableColumn<OpremaKluba, String> modelKlub;
	@FXML
	private TableColumn<OpremaKluba, String> velicinaKlub;
	@FXML
	private TableColumn<OpremaKluba, String> doniranaKlub;
	@FXML
	private TableView<OpremaClana> tblOpremaClana;
	@FXML
	private TableColumn<OpremaClana, Integer> idClan;
	@FXML
	private TableColumn<OpremaClana, String> tipClan;
	@FXML
	private TableColumn<OpremaClana, String> proizvodjacClan;
	@FXML
	private TableColumn<OpremaClana, String> modelClan;
	@FXML
	private TableColumn<OpremaClana, String> velicinaClan;
	@FXML
	private TableColumn<OpremaClana, String> jmbClan;
	@FXML
	private TableColumn<OpremaClana, String> imeClan;
	@FXML
	private TableColumn<OpremaClana, String> prezimeClan;
	@FXML
	private RadioButton rdbtnTipClan;
	@FXML
	private RadioButton rdbtnProizvodjacClan;
	@FXML
	private RadioButton rdbtnModelClan;
	@FXML
	private RadioButton rdbtnJmb;
	@FXML
	private RadioButton rdbtnIme;
	@FXML
	private RadioButton rdbtnPrezime;
	@FXML
	private TextField txtTipClan;
	@FXML
	private TextField txtProizvodjacClan;
	@FXML
	private TextField txtModelClan;
	@FXML
	private TextField txtJmb;
	@FXML
	private TextField txtIme;
	@FXML
	private TextField txtPrezime;
	@FXML
	private RadioButton rdbtnAktivna;
	@FXML
	private RadioButton rdbtnNeaktivna;
	@FXML
	private RadioButton rdbtnSva;
	@FXML
	private RadioButton rdbtnTipKlub;
	@FXML
	private RadioButton rdbtnProizvodjacKlub;
	@FXML
	private RadioButton rdbtnModelKlub;
	@FXML
	private TextField txtTipKlub;
	@FXML
	private TextField txtProizvodjacKlub;
	@FXML
	private TextField txtModelKlub;
	@FXML
	private Tab pregledClanova;

	private ToggleGroup grupaClan = new ToggleGroup();
	private ToggleGroup grupaKlubVrsta = new ToggleGroup();
	private ToggleGroup grupaKlubTip = new ToggleGroup();
	@FXML
	private TabPane tabPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnIzmjeniOpremuClana.disableProperty()
				.bind(tblOpremaClana.getSelectionModel().selectedItemProperty().isNull());
		btnIzmjeniOpremuKluba.disableProperty()
				.bind(tblOpremaKluba.getSelectionModel().selectedItemProperty().isNull());
		popuniTabele();
		podesavanjeZaRdbtnClan();
		podesavanjaZaRdbtnKlub();
		podesavanjaZaTekstPolja();
		dodajKonteksniMeni();

	}

	public void dodajKonteksniMeni() {
		ContextMenu cm = new ContextMenu();
		MenuItem postaviUNeaktivno = new MenuItem("Postavite u neaktivno");
		MenuItem postaviUAktivno = new MenuItem("Postavite u aktivno");
		MenuItem pogledajOpis = new MenuItem("Pogledajte opis");
		MenuItem pogledajOpisDonacije = new MenuItem("Pogledajte opis donacije");
		MenuItem izmjeniOpis = new MenuItem("Izmjenite opis");
		MenuItem obrisiOpremuKluba = new MenuItem("Obriši opremu");
		MenuItem obrisiOpremuClana = new MenuItem("Obriši opremu");

		obrisiOpremuKluba.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				OpremaKluba selektovanaOprema = null;
				ObservableList<OpremaKluba> listaOpreme = tblOpremaKluba.getItems();

				if (!listaOpreme.isEmpty()) {
					selektovanaOprema = listaOpreme.get(tblOpremaKluba.getSelectionModel().getSelectedIndex());
				}

				if (selektovanaOprema != null) {
					DAOFactory.getDAOFactory().getOpremaDAO().UPDATE_OBRISAN(selektovanaOprema);
					popuniTabele();
					grupaKlubTip.selectToggle(null);
					grupaKlubVrsta.selectToggle(null);
					txtTipKlub.clear();
					txtProizvodjacKlub.clear();
					txtModelKlub.clear();
					txtTipKlub.setDisable(true);
					txtProizvodjacKlub.setDisable(true);
					txtModelKlub.setDisable(true);
				}
			}

		});

		obrisiOpremuClana.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				OpremaClana selektovanaOprema = null;
				ObservableList<OpremaClana> listaOpreme = tblOpremaClana.getItems();

				if (!listaOpreme.isEmpty()) {
					selektovanaOprema = listaOpreme.get(tblOpremaClana.getSelectionModel().getSelectedIndex());
				}

				if (selektovanaOprema != null) {
					DAOFactory.getDAOFactory().getOpremaDAO().UPDATE_OBRISAN(selektovanaOprema);
					popuniTabele();
					grupaClan.selectToggle(null);
					txtTipClan.clear();
					txtProizvodjacClan.clear();
					txtModelClan.clear();
					txtJmb.clear();
					txtIme.clear();
					txtPrezime.clear();
					txtTipClan.setDisable(true);
					txtProizvodjacClan.setDisable(true);
					txtModelClan.setDisable(true);
					txtJmb.setDisable(true);
					txtIme.setDisable(true);
					txtPrezime.setDisable(true);
				}
			}

		});

		postaviUNeaktivno.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				OpremaKluba selektovanaOprema = null;
				ObservableList<OpremaKluba> listaOpreme = tblOpremaKluba.getItems();

				if (!listaOpreme.isEmpty()) {
					selektovanaOprema = listaOpreme.get(tblOpremaKluba.getSelectionModel().getSelectedIndex());
				}

				if (selektovanaOprema != null) {
					DAOFactory.getDAOFactory().getOpremaKlubaDAO().UPDATE_AKTIVNOST(selektovanaOprema, false);
					popuniTabele();
					grupaKlubTip.selectToggle(null);
					grupaKlubVrsta.selectToggle(null);
					txtTipKlub.clear();
					txtProizvodjacKlub.clear();
					txtModelKlub.clear();
					txtTipKlub.setDisable(true);
					txtProizvodjacKlub.setDisable(true);
					txtModelKlub.setDisable(true);
				}
			}

		});

		postaviUAktivno.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				OpremaKluba selektovanaOprema = null;
				ObservableList<OpremaKluba> listaOpreme = tblOpremaKluba.getItems();

				if (!listaOpreme.isEmpty()) {
					selektovanaOprema = listaOpreme.get(tblOpremaKluba.getSelectionModel().getSelectedIndex());
				}

				if (selektovanaOprema != null) {
					DAOFactory.getDAOFactory().getOpremaKlubaDAO().UPDATE_AKTIVNOST(selektovanaOprema, true);
					popuniTabele();
					grupaKlubTip.selectToggle(null);
					grupaKlubVrsta.selectToggle(null);
					txtTipKlub.clear();
					txtProizvodjacKlub.clear();
					txtModelKlub.clear();
					txtTipKlub.setDisable(true);
					txtProizvodjacKlub.setDisable(true);
					txtModelKlub.setDisable(true);
				}
			}

		});

		pogledajOpis.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				OpremaKluba selektovanaOprema = null;
				ObservableList<OpremaKluba> listaOpreme = tblOpremaKluba.getItems();

				if (!listaOpreme.isEmpty()) {
					selektovanaOprema = listaOpreme.get(tblOpremaKluba.getSelectionModel().getSelectedIndex());
				}

				if (selektovanaOprema != null) {
					new Alert(AlertType.INFORMATION, selektovanaOprema.getOpis(), ButtonType.OK).show();
				}
			}

		});

		pogledajOpisDonacije.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				OpremaKluba selektovanaOprema = null;
				ObservableList<OpremaKluba> listaOpreme = tblOpremaKluba.getItems();

				if (!listaOpreme.isEmpty()) {
					selektovanaOprema = listaOpreme.get(tblOpremaKluba.getSelectionModel().getSelectedIndex());
				}

				if (selektovanaOprema != null) {
					new Alert(AlertType.INFORMATION, "Sponzor - " + DAOFactory.getDAOFactory().getSponzorDAO()
							.getById(selektovanaOprema.getIdSponzora()).getNaziv(), ButtonType.OK).show();
				}
			}

		});

		izmjeniOpis.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				OpremaKluba selektovanaOprema = null;
				ObservableList<OpremaKluba> listaOpreme = tblOpremaKluba.getItems();
				if (!listaOpreme.isEmpty()) {
					selektovanaOprema = listaOpreme.get(tblOpremaKluba.getSelectionModel().getSelectedIndex());
				}
				if (selektovanaOprema != null) {
					idiNaIzmjeniOpisOpreme(selektovanaOprema);
				}
			}

		});

		tblOpremaKluba.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent t) {
				cm.getItems().clear();
				OpremaKluba selektovanaOprema = tblOpremaKluba.getSelectionModel().getSelectedItem();

				if (selektovanaOprema != null) {
					if (selektovanaOprema.getAktivan()) {
						cm.getItems().add(postaviUNeaktivno);
					} else {
						cm.getItems().add(postaviUAktivno);
					}
					cm.getItems().add(pogledajOpis);

					if (selektovanaOprema.getDonirana()) {
						cm.getItems().add(pogledajOpisDonacije);
					}
					cm.getItems().add(izmjeniOpis);
					cm.getItems().add(obrisiOpremuKluba);

					if (t.getButton() == MouseButton.SECONDARY) {
						cm.show(tblOpremaKluba, t.getScreenX(), t.getScreenY());
					} else {
						cm.hide();
					}
				}
			}
		});

		tblOpremaClana.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent t) {
				cm.getItems().clear();
				OpremaClana selektovanaOprema = tblOpremaClana.getSelectionModel().getSelectedItem();

				if (selektovanaOprema != null) {
					cm.getItems().add(obrisiOpremuClana);
					if (t.getButton() == MouseButton.SECONDARY) {
						cm.show(tblOpremaClana, t.getScreenX(), t.getScreenY());
					} else {
						cm.hide();
					}
				}
			}

		});
	}

	public void podesavanjaZaTekstPolja() {
		txtTipKlub.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					pretragaKlub();
				}
			}

		});

		txtProizvodjacKlub.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					pretragaKlub();
				}
			}

		});

		txtModelKlub.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					pretragaKlub();
				}
			}

		});

		txtTipClan.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					pretragaClan();
				}
			}

		});

		txtProizvodjacClan.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					pretragaClan();
				}
			}

		});

		txtModelClan.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					pretragaClan();
				}
			}

		});

		txtJmb.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					pretragaClan();
				}
			}

		});

		txtIme.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					pretragaClan();
				}
			}

		});

		txtPrezime.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					pretragaClan();
				}
			}

		});
	}

	public void podesavanjaZaRdbtnKlub() {
		txtTipKlub.setDisable(true);
		txtProizvodjacKlub.setDisable(true);
		txtModelKlub.setDisable(true);

		rdbtnAktivna.setToggleGroup(grupaKlubVrsta);
		rdbtnNeaktivna.setToggleGroup(grupaKlubVrsta);
		rdbtnSva.setToggleGroup(grupaKlubVrsta);
		rdbtnTipKlub.setToggleGroup(grupaKlubTip);
		rdbtnProizvodjacKlub.setToggleGroup(grupaKlubTip);
		rdbtnModelKlub.setToggleGroup(grupaKlubTip);

		rdbtnAktivna.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					ObservableList<OpremaKluba> listaOpreme = DAOFactory.getDAOFactory().getOpremaKlubaDAO()
							.SELECT_AKTIVNOST(true);
					tblOpremaKluba.setItems(listaOpreme);
				}
			}

		});

		rdbtnNeaktivna.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					ObservableList<OpremaKluba> listaOpreme = DAOFactory.getDAOFactory().getOpremaKlubaDAO()
							.SELECT_AKTIVNOST(false);
					tblOpremaKluba.setItems(listaOpreme);
				}
			}

		});

		rdbtnSva.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					ObservableList<OpremaKluba> listaOpreme = DAOFactory.getDAOFactory().getOpremaKlubaDAO()
							.SELECT_AKTIVNOST(null);
					tblOpremaKluba.setItems(listaOpreme);
				}
			}

		});

		rdbtnTipKlub.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					txtTipKlub.setDisable(false);
					txtProizvodjacKlub.setDisable(true);
					txtModelKlub.setDisable(true);
				}
			}

		});

		rdbtnProizvodjacKlub.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					txtTipKlub.setDisable(true);
					txtProizvodjacKlub.setDisable(false);
					txtModelKlub.setDisable(true);
				}
			}

		});

		rdbtnModelKlub.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					txtTipKlub.setDisable(true);
					txtProizvodjacKlub.setDisable(true);
					txtModelKlub.setDisable(false);
				}
			}

		});

		btnPretraziOpremaKluba.disableProperty().bind(
				grupaKlubVrsta.selectedToggleProperty().isNull().or(grupaKlubTip.selectedToggleProperty().isNull()));
	}

	public void podesavanjeZaRdbtnClan() {
		txtTipClan.setDisable(true);
		txtProizvodjacClan.setDisable(true);
		txtModelClan.setDisable(true);
		txtJmb.setDisable(true);
		txtIme.setDisable(true);
		txtPrezime.setDisable(true);

		rdbtnTipClan.setToggleGroup(grupaClan);
		rdbtnProizvodjacClan.setToggleGroup(grupaClan);
		rdbtnModelClan.setToggleGroup(grupaClan);
		rdbtnJmb.setToggleGroup(grupaClan);
		rdbtnIme.setToggleGroup(grupaClan);
		rdbtnPrezime.setToggleGroup(grupaClan);

		rdbtnTipClan.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					txtTipClan.setDisable(false);
					txtProizvodjacClan.setDisable(true);
					txtModelClan.setDisable(true);
					txtJmb.setDisable(true);
					txtIme.setDisable(true);
					txtPrezime.setDisable(true);
				}
			}

		});

		rdbtnProizvodjacClan.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					txtTipClan.setDisable(true);
					txtProizvodjacClan.setDisable(false);
					txtModelClan.setDisable(true);
					txtJmb.setDisable(true);
					txtIme.setDisable(true);
					txtPrezime.setDisable(true);
				}
			}

		});

		rdbtnModelClan.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					txtTipClan.setDisable(true);
					txtProizvodjacClan.setDisable(true);
					txtModelClan.setDisable(false);
					txtJmb.setDisable(true);
					txtIme.setDisable(true);
					txtPrezime.setDisable(true);
				}
			}

		});

		rdbtnJmb.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					txtTipClan.setDisable(true);
					txtProizvodjacClan.setDisable(true);
					txtModelClan.setDisable(true);
					txtJmb.setDisable(false);
					txtIme.setDisable(true);
					txtPrezime.setDisable(true);
				}
			}

		});

		rdbtnIme.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					txtTipClan.setDisable(true);
					txtProizvodjacClan.setDisable(true);
					txtModelClan.setDisable(true);
					txtJmb.setDisable(true);
					txtIme.setDisable(false);
					txtPrezime.setDisable(true);
				}
			}

		});

		rdbtnPrezime.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					txtTipClan.setDisable(true);
					txtProizvodjacClan.setDisable(true);
					txtModelClan.setDisable(true);
					txtJmb.setDisable(true);
					txtIme.setDisable(true);
					txtPrezime.setDisable(false);
				}
			}

		});

		btnPretraziOpremaClanova.disableProperty().bind(grupaClan.selectedToggleProperty().isNull());
	}

	public void pretragaKlub() {
		RadioButton rdbtnVrsta = (RadioButton) grupaKlubVrsta.getSelectedToggle();
		RadioButton rdbtnTip = (RadioButton) grupaKlubTip.getSelectedToggle();
		String tipPretrage = "";
		String rijec = "";
		Boolean aktivan = null;

		if (rdbtnAktivna.equals(rdbtnVrsta)) {
			aktivan = true;
		} else if (rdbtnNeaktivna.equals(rdbtnVrsta)) {
			aktivan = false;
		}

		if (rdbtnTipKlub.equals(rdbtnTip)) {
			tipPretrage = "Tip";
			rijec = txtTipKlub.getText();
		} else if (rdbtnProizvodjacKlub.equals(rdbtnTip)) {
			tipPretrage = "Proizvodjac";
			rijec = txtProizvodjacKlub.getText();
		} else {
			tipPretrage = "Model";
			rijec = txtModelKlub.getText();
		}

		ObservableList<OpremaKluba> listaOpreme = DAOFactory.getDAOFactory().getOpremaKlubaDAO().SELECT_BY(aktivan,
				tipPretrage, rijec);
		tblOpremaKluba.setItems(listaOpreme);
	}

	public void pretragaClan() {
		RadioButton rdbtn = (RadioButton) grupaClan.getSelectedToggle();
		String tipPretrage = "";
		String rijec = "";

		if (rdbtnTipClan.equals(rdbtn)) {
			tipPretrage = "Tip";
			rijec = txtTipClan.getText();
		} else if (rdbtnProizvodjacClan.equals(rdbtn)) {
			tipPretrage = "Proizvodjac";
			rijec = txtProizvodjacClan.getText();
		} else if (rdbtnModelClan.equals(rdbtn)) {
			tipPretrage = "Model";
			rijec = txtModelClan.getText();
		} else if (rdbtnJmb.equals(rdbtn)) {
			tipPretrage = "JMB";
			rijec = txtJmb.getText();
		} else if (rdbtnIme.equals(rdbtn)) {
			tipPretrage = "Ime";
			rijec = txtIme.getText();
		} else {
			tipPretrage = "Prezime";
			rijec = txtPrezime.getText();
		}

		ObservableList<OpremaClana> listaOpreme = DAOFactory.getDAOFactory().getOpremaClanaDAO().SELECT_BY(tipPretrage,
				rijec);
		tblOpremaClana.setItems(listaOpreme);
	}

	public void popuniTabele() {

		tipKlub.setCellValueFactory(new PropertyValueFactory<OpremaKluba, String>("tipOpreme"));
		proizvodjacKlub.setCellValueFactory(new PropertyValueFactory<OpremaKluba, String>("tipProizvodjac"));
		modelKlub.setCellValueFactory(new PropertyValueFactory<OpremaKluba, String>("tipModel"));
		velicinaKlub.setCellValueFactory(new PropertyValueFactory<OpremaKluba, String>("velicina"));
		doniranaKlub.setCellValueFactory(new PropertyValueFactory<OpremaKluba, String>("status"));

		ObservableList<OpremaKluba> listaOpremeKluba = DAOFactory.getDAOFactory().getOpremaKlubaDAO().SELECT_ALL();

		tblOpremaKluba.setItems(listaOpremeKluba);


		tipClan.setCellValueFactory(new PropertyValueFactory<OpremaClana, String>("tipOpreme"));
		proizvodjacClan.setCellValueFactory(new PropertyValueFactory<OpremaClana, String>("tipProizvodjac"));
		modelClan.setCellValueFactory(new PropertyValueFactory<OpremaClana, String>("tipModel"));
		velicinaClan.setCellValueFactory(new PropertyValueFactory<OpremaClana, String>("velicina"));
		jmbClan.setCellValueFactory(new PropertyValueFactory<OpremaClana, String>("jmbClana"));
		imeClan.setCellValueFactory(new PropertyValueFactory<OpremaClana, String>("imeClana"));
		prezimeClan.setCellValueFactory(new PropertyValueFactory<OpremaClana, String>("prezimeClana"));

		ObservableList<OpremaClana> listaOpremeClana = DAOFactory.getDAOFactory().getOpremaClanaDAO().SELECT_ALL();

		tblOpremaClana.setItems(listaOpremeClana);
	}

	public void idiNaIzmjeniOpremuKluba() {
		Stage noviStage = new Stage();

		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getClassLoader().getResource("application/gui/trener/view/IzmjenaOpremeView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root, 273, 163);
			IzmjenaOpremeController controller = loader.<IzmjenaOpremeController>getController();
			controller.setPrimaryStage(noviStage);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub");
			noviStage.initModality(Modality.APPLICATION_MODAL);

			controller.setOpremaKluba();
			OpremaKluba selektovanaOprema = tblOpremaKluba.getSelectionModel().getSelectedItem();
			controller.setOprema(selektovanaOprema);
			controller.ucitajComboBoxeve();
			controller.provjeriParametre();
			controller.disable();

			noviStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				public void handle(WindowEvent we) {
					we.consume();
					Alert alert = new Alert(AlertType.CONFIRMATION, "Da li zelite da sacuvate izmjene?", ButtonType.YES,
							ButtonType.NO);
					Optional<ButtonType> rezultat = alert.showAndWait();

					if (ButtonType.YES.equals(rezultat.get())) {
						controller.azurirajUBazi();
						noviStage.close();
					} else if (ButtonType.NO.equals(rezultat.get())) {
						noviStage.close();
					}

				}
			});

			noviStage.showAndWait();
			popuniTabele();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void idiNaIzmjeniOpremuClana() {
		Stage noviStage = new Stage();

		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getClassLoader().getResource("application/gui/trener/view/IzmjenaOpremeView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root, 273, 163);
			IzmjenaOpremeController controller = loader.<IzmjenaOpremeController>getController();
			controller.setPrimaryStage(noviStage);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub");
			noviStage.initModality(Modality.APPLICATION_MODAL);

			OpremaClana selektovanaOprema = tblOpremaClana.getSelectionModel().getSelectedItem();
			controller.setOprema(selektovanaOprema);
			controller.ucitajComboBoxeve();
			controller.provjeriParametre();
			controller.disable();

			noviStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				public void handle(WindowEvent we) {
					we.consume();
					Alert alert = new Alert(AlertType.CONFIRMATION, "Da li zelite da sacuvate izmjene?", ButtonType.YES,
							ButtonType.NO);
					Optional<ButtonType> rezultat = alert.showAndWait();

					if (ButtonType.YES.equals(rezultat.get())) {
						controller.azurirajUBazi();
						noviStage.close();
					} else if (ButtonType.NO.equals(rezultat.get())) {
						noviStage.close();
					}

				}
			});

			noviStage.showAndWait();
			popuniTabele();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void idiNaDodajOpremuKluba() {
		Stage noviStage = new Stage();

		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getClassLoader().getResource("application/gui/trener/view/DodajOpremuKlubaView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root, 761, 670);
			DodajOpremuKlubaController controller = loader.<DodajOpremuKlubaController>getController();
			controller.setPrimaryStage(noviStage);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub");
			noviStage.initModality(Modality.APPLICATION_MODAL);

			controller.ucitajComboBoxeve();
			controller.checkBoxNijeSelektovan();
			controller.disableDodajDugme();

			noviStage.showAndWait();
			popuniTabele();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void idiNaIzmjeniOpisOpreme(OpremaKluba oprema) {
		Stage noviStage = new Stage();

		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getClassLoader().getResource("application/gui/trener/view/IzmjenaOpisaOpremeView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root, 305, 185);
			IzmjenaOpisaOpremeController controller = loader.<IzmjenaOpisaOpremeController>getController();
			controller.setPrimaryStage(noviStage);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			controller.setOprema(oprema);

			noviStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				public void handle(WindowEvent we) {
					we.consume();
					Alert alert = new Alert(AlertType.CONFIRMATION, "Da li zelite da sacuvate izmjene?", ButtonType.YES,
							ButtonType.NO);
					Optional<ButtonType> rezultat = alert.showAndWait();

					if (ButtonType.YES.equals(rezultat.get())) {
						controller.azurirajUBazi();
						noviStage.close();
					} else if (ButtonType.NO.equals(rezultat.get())) {
						noviStage.close();
					}

				}
			});
			noviStage.showAndWait();
			popuniTabele();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void idiNaDodajOpremuClana() {
		Stage noviStage = new Stage();

		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getClassLoader().getResource("application/gui/trener/view/DodajOpremuClanaView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root, 761, 280);
			DodajOpremuClanaController controller = loader.<DodajOpremuClanaController>getController();
			controller.setPrimaryStage(noviStage);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub");
			noviStage.initModality(Modality.APPLICATION_MODAL);

			controller.ucitajComboBoxeve();

			noviStage.showAndWait();
			popuniTabele();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
