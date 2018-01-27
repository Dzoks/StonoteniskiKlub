package application.gui.sekretar.controller;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import application.gui.controller.BaseController;
import application.model.dto.SkupstinaDTO;
import application.model.dto.UgovorDTO;
import application.util.AlertDisplay;
import application.util.ErrorLogger;
import application.util.GUIBuilder;
import application.util.InputValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RadSaUgovorimaController extends BaseController {
	@FXML
	private TableView<UgovorDTO> tblUgovori;
	@FXML
	private TableColumn<UgovorDTO, Integer> colRb;
	@FXML
	private TableColumn<UgovorDTO, String> colDatumOd;
	@FXML
	private TableColumn<UgovorDTO, String> colDatumDo;
	@FXML
	private TableColumn<UgovorDTO, String> colSaDonacijom;
	@FXML
	private ComboBox<String> cbTip;
	@FXML
	private DatePicker dpDatumOd;
	@FXML
	private DatePicker dpDatumDo;
	@FXML
	private Button btnPretrazi;
	@FXML
	private TextArea taOpis;
	@FXML
	private Button btnPregledajDonacije;
	@FXML
	private Button btnOsvjezi;
	@FXML
	void odjaviteSe(ActionEvent event) {
		try {
			BaseController.changeScene("/application/gui/administrator/view/LoginView.fxml", primaryStage);
		} catch (IOException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buildTable();
		populateComboBoxes();
		bindDisable();
	}

	// Event Listener on ComboBox[#cbTip].onAction
	@FXML
	public void filtriraj(ActionEvent event) {
		String option = cbTip.getSelectionModel().getSelectedItem();
		if ("Svi".equals(option)) {
			tblUgovori.setItems(listaUgovora);
		} else if ("Sa donacijama".equals(option)) {
			ObservableList<UgovorDTO> filtered = FXCollections.observableArrayList();
			for (UgovorDTO ugovor : listaUgovora) {
				if (ugovor.saDonacijomProperty().get().equals("Da")) {
					filtered.add(ugovor);
				}
			}
			tblUgovori.setItems(filtered);
		} else {
			ObservableList<UgovorDTO> filtered = FXCollections.observableArrayList();
			for (UgovorDTO ugovor : listaUgovora) {
				if (ugovor.saDonacijomProperty().get().equals("Ne")) {
					filtered.add(ugovor);
				}
			}
			tblUgovori.setItems(filtered);
		}
	}

	// Event Listener on Button[#btnPretrazi].onAction
	@FXML
	public void pretrazi(ActionEvent event) {
		ObservableList<UgovorDTO> sve = tblUgovori.getItems();
		tblUgovori.setItems(sve.filtered(new Predicate<UgovorDTO>() {
			@Override
			public boolean test(UgovorDTO t) {
				Date donjaGranica = dpDatumOd.getValue() != null ? java.sql.Date.valueOf(dpDatumOd.getValue()) : t.getDatumOd();
				Date gornjaGranica = dpDatumDo.getValue() != null ? java.sql.Date.valueOf(dpDatumDo.getValue()) : t.getDatumOd();
				return t.getDatumOd().compareTo(donjaGranica) >= 0 && t.getDatumOd().compareTo(gornjaGranica) <= 0;
			}
		}));
	}
	@FXML
	public void osvjezi(ActionEvent event){
		cbTip.getSelectionModel().select(0);
		cbTip.fireEvent(event);
	}
	// Event Listener on Button[#btnPregledajDonacije].onAction
	@FXML
	public void pregledajDonacije(ActionEvent event) {
		UgovorDTO ugovor = listaUgovora.get(listaUgovora.indexOf(tblUgovori.getSelectionModel().getSelectedItem()));
		if (ugovor.imaDonaciju()) {
			Stage newStage = new Stage();
			RadSaDonacijamaController controller = null;
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getClassLoader()
						.getResource("application/gui/sekretar/view/RadSaDonacijamaView.fxml"));
				AnchorPane root = (AnchorPane) loader.load();
				controller = loader.<RadSaDonacijamaController>getController();
				Scene scene = new Scene(root, 630, 490);
				controller.setDonacije(ugovor.getDonacije());
				newStage.setScene(scene);
				newStage.setResizable(false);
				newStage.setTitle("Stonoteniski klub");
				newStage.initModality(Modality.APPLICATION_MODAL);
				newStage.showAndWait();
			} catch (IOException e) {
				e.printStackTrace();
				new ErrorLogger().log(e);
			}
		} else {
			AlertDisplay.showInformation("Pregled", "Nema donacija u ugovoru.");// ŠTA
																				// OVDJE
																				// TREBA
																				// BITI???
																				// Dzoks
		}
	}

	public void setListaUgovora(ObservableList<UgovorDTO> listaUgovora) {
		this.listaUgovora = listaUgovora;
		tblUgovori.setItems(listaUgovora);
	}

	@FXML
	public void updateOpis(MouseEvent event) {
		UgovorDTO ugovorDTO = tblUgovori.getSelectionModel().getSelectedItem();
		if (ugovorDTO != null) {
			taOpis.setText(ugovorDTO.getOpis());
		}
	}

	@FXML
	public void deselect(MouseEvent event) {
		tblUgovori.getSelectionModel().clearSelection();
		taOpis.setText("");
	}

	private ObservableList<UgovorDTO> listaUgovora;
	private static ObservableList<String> cbItems = FXCollections.observableArrayList();
	static {
		cbItems.add("Svi");
		cbItems.add("Bez donacija");
		cbItems.add("Sa donacijama");
	}

	// Pomocne metode
	private void buildTable() {
		Map<String, TableColumn<UgovorDTO, Integer>> mapInteger = new HashMap<String, TableColumn<UgovorDTO, Integer>>();
		mapInteger.put("redniBroj", colRb);
		GUIBuilder.<UgovorDTO, Integer>initializeTableColumns(mapInteger);
		Map<String, TableColumn<UgovorDTO, String>> mapString = new HashMap<String, TableColumn<UgovorDTO, String>>();
		mapString.put("datumOd", colDatumOd);
		mapString.put("datumDo", colDatumDo);
		mapString.put("saDonacijom", colSaDonacijom);
		GUIBuilder.<UgovorDTO, String>initializeTableColumns(mapString);
	}

	private void populateComboBoxes() {
		cbTip.setItems(cbItems);
		cbTip.getSelectionModel().select(0);
	}

	private void bindDisable() {
		btnPregledajDonacije.disableProperty().bind(tblUgovori.getSelectionModel().selectedItemProperty().isNull());
		btnPretrazi.disableProperty().bind(dpDatumOd.valueProperty().isNull().and(dpDatumDo.valueProperty().isNull()));
	}
}
