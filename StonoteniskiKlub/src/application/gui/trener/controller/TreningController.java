package application.gui.trener.controller;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dao.mysql.MySQLTreningDAO;
import application.model.dto.TreningDTO;
import application.util.AlertDisplay;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TreningController extends BaseController {
	@FXML
	private TextField txtGodina;
	@FXML
	private TextField txtMjesec;
	@FXML
	private ListView<TreningDTO> listTrening;
	@FXML
	private Label lblDatum;
	@FXML
	private TextArea areaOpis;
	@FXML
	private Button btnIzmjenOpis;
	@FXML
	private Button btnDodajTrening;
	@FXML
	private Button btnSacuvajOpis;
	@FXML
    private Button btnObrisi;

	private Integer CLAN_Id;
	private ObservableList<TreningDTO> lista;

	public void setClan(Integer CLAN_Id) {
		this.CLAN_Id = CLAN_Id;
		lista = DAOFactory.getDAOFactory().getTreningDAO().selectByMember(CLAN_Id);
		listTrening.setItems(lista);
	}

	// Event Listener on TextField[#txtGodina].onInputMethodTextChanged
	@FXML
	public void filterList() {
		listTrening.setItems(lista.filtered(trening -> {
			String godina = trening.getDatum().getYear() + "";
			
			String mjesec = trening.getDatum().getMonthValue() + "";
			return godina.startsWith(txtGodina.getText()) && mjesec.startsWith(txtMjesec.getText());
		}));
	}

	// Event Listener on Button[#btnIzmjenOpis].onAction
	@FXML
	public void editTrening(ActionEvent event) {
		areaOpis.setEditable(true);
	}

	// Event Listener on Button[#btnDodajTrening].onAction
	@FXML
	public void addNew(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		stage.setResizable(false);
		stage.setTitle("Stonoteniski klub");
		stage.initModality(Modality.WINDOW_MODAL);
		DodajTreningController controller = (DodajTreningController) BaseController
				.changeScene("/application/gui/trener/view/DodajTreningView.fxml", stage);
		stage.showAndWait();
		if (controller.getTrening() != null) {
			TreningDTO noviTrening = controller.getTrening();
			noviTrening.setCLAN_Id(CLAN_Id);
			DAOFactory.getDAOFactory().getTreningDAO().insert(noviTrening);
			lista.add(noviTrening);
		}
	}

	@FXML
	void saveEdit(ActionEvent event) {
		TreningDTO trening = listTrening.getSelectionModel().getSelectedItem();
		trening.setOpis(areaOpis.getText());
		areaOpis.setEditable(false);
		DAOFactory.getDAOFactory().getTreningDAO().update(trening);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lblDatum.setText("");
		btnSacuvajOpis.visibleProperty().bind(areaOpis.editableProperty());
		btnIzmjenOpis.disableProperty().bind(listTrening.getSelectionModel().selectedItemProperty().isNull());
		btnObrisi.disableProperty().bind(listTrening.getSelectionModel().selectedItemProperty().isNull());
		listTrening.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreningDTO>() {

			@Override
			public void changed(ObservableValue<? extends TreningDTO> observable, TreningDTO oldValue,
					TreningDTO newValue) {
				if (newValue!=null){
				lblDatum.setText(newValue.getDatum().format(DateTimeFormatter.ofPattern("dd.MM.YYYY.")));
				areaOpis.setText(newValue.getOpis());
				}else {
					lblDatum.setText("");
					areaOpis.setText("");
				}
			}
		});

	}
	  @FXML
	    void obrisi(ActionEvent event) {
		  	Optional<ButtonType> result=AlertDisplay.showConfirmation("Brisanje", "Da li ste sigurni da želite da obrišete trening?");
		  	if (ButtonData.YES==result.get().getButtonData()) {
			TreningDTO trening = listTrening.getSelectionModel().getSelectedItem();
			lista.remove(trening);
			DAOFactory.getDAOFactory().getTreningDAO().deactivate(trening);
		  	}

	    }
}
