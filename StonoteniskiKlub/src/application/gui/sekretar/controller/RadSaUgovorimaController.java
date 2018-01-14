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

import application.gui.controller.BaseController;
import application.model.dto.UgovorDTO;
import application.util.AlertDisplay;
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
		Date datumOd = null;
		Date datumDo = null;
		LocalDate localDatumOd = dpDatumOd.getValue();
		LocalDate localDatumDo = dpDatumDo.getValue();
		try {
			if (localDatumOd != null) {
				datumOd = new SimpleDateFormat("yyyy-MM-dd").parse(localDatumOd.toString());
			}
			if (localDatumDo != null) {
				datumDo = new SimpleDateFormat("yyyy-MM-dd").parse(localDatumDo.toString());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		boolean dOd = InputValidator.allEntered(dpDatumOd.getValue());
		boolean dDo = InputValidator.allEntered(dpDatumDo.getValue());
		ObservableList<UgovorDTO> tableList = tblUgovori.getItems();
		if (!dOd && !dDo) {
			AlertDisplay.showInformation("Greska", "", "Niste unijeli datum");
		} else if (dOd && dDo) {
			ObservableList<UgovorDTO> filtered = FXCollections.observableArrayList();
			for (UgovorDTO ugovor : tableList) {
				if (ugovor.getDatumOd().compareTo(datumOd) >= 0 && ugovor.getDatumOd().compareTo(datumDo) <= 0) {
					filtered.add(ugovor);
				}
			}
			tblUgovori.setItems(filtered);
		} else if (dOd) {
			ObservableList<UgovorDTO> filtered = FXCollections.observableArrayList();
			for (UgovorDTO ugovor : tableList) {
				if (ugovor.getDatumOd().compareTo(datumOd) >= 0) {
					filtered.add(ugovor);
				}
			}
			tblUgovori.setItems(filtered);
		} else {
			ObservableList<UgovorDTO> filtered = FXCollections.observableArrayList();
			for (UgovorDTO ugovor : tableList) {
				if (ugovor.getDatumOd().compareTo(datumDo) <= 0) {
					filtered.add(ugovor);
				}
			}
			tblUgovori.setItems(filtered);
		}
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
				newStage.setTitle("Stonoteniski klub - Pregled donacija");
				newStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else{
			AlertDisplay.showInformation("Rezultat", "", "Nema donacija u ugovoru.");
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
	public void deselect() {
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
		}
}
