package application.gui.sekretar.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.DonacijaDTO;
import application.model.dto.OpremaTip;
import application.util.AlertDisplay;
import application.util.ErrorLogger;
import application.util.InputValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class RadSaDonacijamaController extends BaseController {
	@FXML
	private ComboBox<String> cbTipDonacije;
	@FXML
	private TableView<DonacijaDTO> tblDonacije;
	@FXML
	private TableColumn<DonacijaDTO, Integer> colRb;
	@FXML
	private TableColumn<DonacijaDTO, String> colTipDonacije;
	@FXML
	private TableColumn<DonacijaDTO, Double> colNovcaniIznos;
	@FXML
	private TableColumn<DonacijaDTO, String> colTipOpreme;
	@FXML
	private TableColumn<DonacijaDTO, Double> colKolicina;
	@FXML
	private TableColumn<DonacijaDTO, String> colObradjena;
	@FXML
	private TextArea taOpisDonacije;
	@FXML
	private RadioButton rbNovcane;
	@FXML
	private ToggleGroup grpPretraga;
	@FXML
	private RadioButton rbOprema;
	@FXML
	private TextField tfOdNovac;
	@FXML
	private TextField tfDoNovac;
	@FXML
	private ComboBox<OpremaTip> cbTipOpreme;
	@FXML
	private TextField tfOdKolicina;
	@FXML
	private TextField tfDoKolicina;
	@FXML
	private Button btnPretraga;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buildTable();
		populateComboBoxes();
		bindDisable();
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

	@FXML
	public void updateOpis(MouseEvent event) {
		DonacijaDTO donacija = tblDonacije.getSelectionModel().getSelectedItem();
		if (donacija != null) {
			taOpisDonacije.setText(donacija.getOpis());
		}
	}

	// Event Listener on AnchorPane.onMouseClicked
	@FXML
	public void deselect(MouseEvent event) {
		clearSelection();
	}

	// Event Listener on ComboBox[#cbTipDonacije].onAction
	@FXML
	public void filtrirajPoTipu(ActionEvent event) {
		clearSelection();
		String option = cbTipDonacije.getSelectionModel().getSelectedItem();
		if ("Sve".equals(option)) {
			tblDonacije.setItems(donacije);
		} else if ("Novčane".equals(option)) {
			ObservableList<DonacijaDTO> filtered = FXCollections.observableArrayList();
			for (DonacijaDTO donacija : donacije) {
				if (donacija.getNovcanaDonacija()) {
					filtered.add(donacija);
				}
			}
			tblDonacije.setItems(filtered);
		} else {
			ObservableList<DonacijaDTO> filtered = FXCollections.observableArrayList();
			for (DonacijaDTO donacija : donacije) {
				if (!donacija.getNovcanaDonacija()) {
					filtered.add(donacija);
				}
			}
			tblDonacije.setItems(filtered);
		}
	}

	// Event Listener on Button[#btnPretraga].onAction
	@FXML
	public void pretraga(ActionEvent event) {
		String odT = null;
		String doT = null;
		if (rbNovcane.isSelected()) {
			odT = tfOdNovac.getText();
			doT = tfDoNovac.getText();
		} else {
			odT = tfOdKolicina.getText();
			doT = tfDoKolicina.getText();
		}
		if (!((!"".equals(odT) && !InputValidator.validateDouble(odT))
				|| (!"".equals(doT) && !InputValidator.validateDouble(doT)))) {
			tblDonacije.setItems(donacije.filtered(new Predicate<DonacijaDTO>() {
				@Override
				public boolean test(DonacijaDTO t) {
					BigDecimal donjaGranica = null;
					BigDecimal gornjaGranica = null;
					TextField donjiTf = null;
					TextField gornjiTf = null;
					if (rbNovcane.isSelected()) {
						donjiTf = tfOdNovac;
						gornjiTf = tfDoNovac;
					} else {
						donjiTf = tfOdKolicina;
						gornjiTf = tfDoKolicina;
					}
					donjaGranica = "".equals(donjiTf.getText()) ? new BigDecimal(-1)
							: new BigDecimal(donjiTf.getText());
					gornjaGranica = "".equals(gornjiTf.getText()) ? new BigDecimal(Double.MAX_VALUE)
							: new BigDecimal(gornjiTf.getText());
					return (rbNovcane.isSelected() && t.getNovcanaDonacija()
							&& t.getNovcaniIznos().compareTo(donjaGranica) >= 0
							&& t.getNovcaniIznos().compareTo(gornjaGranica) <= 0)
							|| (rbOprema.isSelected() && !t.getNovcanaDonacija()
									&& t.getTipOpreme().getId()
											.equals(cbTipOpreme.getSelectionModel().getSelectedItem().getId())
									&& t.getKolicina().compareTo(donjaGranica) >= 0
									&& t.getKolicina().compareTo(gornjaGranica) <= 0);
				}
			}));
		} else{
			AlertDisplay.showError("Pretraga", "Format podataka nije ispravan");
		}
	}

	@FXML
	public void prikaziNovcane(ActionEvent event) {
		cbTipDonacije.getSelectionModel().select(1);
		cbTipDonacije.fireEvent(event);
	}

	@FXML
	public void prikaziOpremu(ActionEvent event) {
		cbTipDonacije.getSelectionModel().select(2);
		cbTipDonacije.fireEvent(event);
	}

	public void setDonacije(ObservableList<DonacijaDTO> donacije) {
		this.donacije = donacije;
		tblDonacije.setItems(donacije);
	}

	private ObservableList<DonacijaDTO> donacije;

	private void clearSelection() {
		taOpisDonacije.setText("");
		tblDonacije.getSelectionModel().clearSelection();
	}

	private static ObservableList<String> cbTipItems = FXCollections.observableArrayList();
	static {
		cbTipItems.add("Sve");
		cbTipItems.add("Novčane");
		cbTipItems.add("Oprema");
	}

	private void buildTable() {
		colRb.setCellValueFactory(new PropertyValueFactory<DonacijaDTO, Integer>("redniBroj"));
		colTipDonacije.setCellValueFactory(new PropertyValueFactory<DonacijaDTO, String>("tipDonacije"));
		colNovcaniIznos.setCellValueFactory(new PropertyValueFactory<DonacijaDTO, Double>("novcaniIznos"));
		colTipOpreme.setCellValueFactory(new PropertyValueFactory<DonacijaDTO, String>("tipOpreme"));
		colKolicina.setCellValueFactory(new PropertyValueFactory<DonacijaDTO, Double>("kolicina"));
		colObradjena.setCellValueFactory(new PropertyValueFactory<DonacijaDTO, String>("obradjeno"));
	}

	private void populateComboBoxes() {
		cbTipDonacije.setItems(cbTipItems);
		cbTipDonacije.getSelectionModel().select(0);
		cbTipOpreme.setItems(DAOFactory.getDAOFactory().getOpremaTipDAO().SELECT_ALL());
		cbTipOpreme.getSelectionModel().select(0);
	}

	private void bindDisable() {
		tfDoNovac.disableProperty().bind(rbOprema.selectedProperty());
		tfOdNovac.disableProperty().bind(rbOprema.selectedProperty());
		tfOdKolicina.disableProperty().bind(rbNovcane.selectedProperty());
		tfDoKolicina.disableProperty().bind(rbNovcane.selectedProperty());
		cbTipOpreme.disableProperty().bind(rbNovcane.selectedProperty());
		btnPretraga.disableProperty()
				.bind(rbNovcane.selectedProperty()
						.and(tfOdNovac.textProperty().isEmpty().and(tfDoNovac.textProperty().isEmpty()))
						.or(rbOprema.selectedProperty().and(
								tfOdKolicina.textProperty().isEmpty().and(tfDoKolicina.textProperty().isEmpty()))));
	}
}
