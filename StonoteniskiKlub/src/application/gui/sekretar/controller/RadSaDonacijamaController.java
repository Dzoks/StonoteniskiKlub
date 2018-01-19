package application.gui.sekretar.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.DonacijaDTO;
import application.model.dto.OpremaTip;
import application.util.AlertDisplay;
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
		} else if ("Novcane".equals(option)) {
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
		boolean odNovac = InputValidator.allEntered(tfOdNovac.getText());
		boolean doNovac = InputValidator.allEntered(tfDoNovac.getText());
		boolean odKolicina = InputValidator.allEntered(tfOdKolicina.getText());
		boolean doKolicina = InputValidator.allEntered(tfDoKolicina.getText());
		if ((rbNovcane.isSelected() && !odNovac && !doNovac) || (rbOprema.isSelected() && !odKolicina && !doKolicina)) {
			AlertDisplay.showInformation("Greška", "Greška prilikom pretrage", "Unesite odgovarajuće parametre pretrage!");
		} else {
			if (rbNovcane.isSelected()) {
				String odText = tfOdNovac.getText();
				String doText = tfDoNovac.getText();
				BigDecimal odN = "".equals(odText) ? null : new BigDecimal(odText);
				BigDecimal doN = "".equals(doText) ? null : new BigDecimal(doText);
				if (odNovac && doNovac) {
					ObservableList<DonacijaDTO> filtered = FXCollections.observableArrayList();
					for (DonacijaDTO donacija : donacije) {
						if (donacija.getNovcanaDonacija() && donacija.getNovcaniIznos().compareTo(odN) >= 0
								&& donacija.getNovcaniIznos().compareTo(doN) <= 0) {
							filtered.add(donacija);
						}
					}
					tblDonacije.setItems(filtered);
				} else if (odNovac) {
					ObservableList<DonacijaDTO> filtered = FXCollections.observableArrayList();
					for (DonacijaDTO donacija : donacije) {
						if (donacija.getNovcanaDonacija() && donacija.getNovcaniIznos().compareTo(odN) >= 0) {
							filtered.add(donacija);
						}
					}
					tblDonacije.setItems(filtered);
				} else {
					ObservableList<DonacijaDTO> filtered = FXCollections.observableArrayList();
					for (DonacijaDTO donacija : donacije) {
						if (donacija.getNovcanaDonacija() && donacija.getNovcaniIznos().compareTo(doN) <= 0) {
							filtered.add(donacija);
						}
					}
					tblDonacije.setItems(filtered);
				}
			} else {
				String odText = tfOdKolicina.getText();
				String doText = tfDoKolicina.getText();
				BigDecimal odN = "".equals(odText) ? null : new BigDecimal(odText);
				BigDecimal doN = "".equals(doText) ? null : new BigDecimal(doText);
				if (odKolicina && doKolicina) {
					ObservableList<DonacijaDTO> filtered = FXCollections.observableArrayList();
					for (DonacijaDTO donacija : donacije) {
						if (!donacija.getNovcanaDonacija()
								&& donacija.getTipOpreme().getId()
										.equals(cbTipOpreme.getSelectionModel().getSelectedItem().getId())
								&& donacija.getKolicina().compareTo(odN) >= 0
								&& donacija.getKolicina().compareTo(doN) <= 0) {
							filtered.add(donacija);
						}
					}
					tblDonacije.setItems(filtered);
				} else if (odKolicina) {
					ObservableList<DonacijaDTO> filtered = FXCollections.observableArrayList();
					for (DonacijaDTO donacija : donacije) {
						if (!donacija.getNovcanaDonacija()
								&& donacija.getTipOpreme().getId()
										.equals(cbTipOpreme.getSelectionModel().getSelectedItem().getId())
								&& donacija.getKolicina().compareTo(odN) >= 0) {
							filtered.add(donacija);
						}
					}
					tblDonacije.setItems(filtered);
				} else {
					ObservableList<DonacijaDTO> filtered = FXCollections.observableArrayList();
					for (DonacijaDTO donacija : donacije) {
						if (!donacija.getNovcanaDonacija()
								&& donacija.getTipOpreme().getId()
										.equals(cbTipOpreme.getSelectionModel().getSelectedItem().getId())
								&& donacija.getKolicina().compareTo(doN) <= 0) {
							filtered.add(donacija);
						}
					}
					tblDonacije.setItems(filtered);
				}
			}
		}
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
		cbTipItems.add("Novcane");
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
	}
}
