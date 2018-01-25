package application.gui.trener.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dao.mysql.MySQLRegistracijaDAO;
import application.model.dto.ClanDTO;
import application.model.dto.KategorijaDTO;
import application.model.dto.RegistracijaDTO;
import application.util.AlertDisplay;
import application.util.InputValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

public class RegistracijaController extends BaseController {
	@FXML
	private ImageView imgSlika;
	@FXML
	private ListView<String> lstBrojeviTelefona;
	@FXML
	private Label lblIme;
	@FXML
	private Label lblPrezime;
	@FXML
	private Label lblImeRoditelja;
	@FXML
	private Label lblJmb;
	@FXML
	private Label lblDatumR;
	@FXML
	private ComboBox<String> cbSezona;
	@FXML
	private ComboBox<KategorijaDTO> cbKategorija;
	@FXML
	private DatePicker dpDatum;
	@FXML
	private Button btnSacuvaj;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbSezona.setItems(sezone);
		cbSezona.getSelectionModel().select(0);
		cbKategorija.setItems(DAOFactory.getDAOFactory().getKategorijaDAO().getAllMySQL());
		cbKategorija.getSelectionModel().select(0);
		this.setClan(DAOFactory.getDAOFactory().getClanDAO().getById(29));
	}

	// Event Listener on Button[#btnSacuvaj].onAction
	@FXML
	public void sacuvaj(ActionEvent event) {
		if (InputValidator.allEntered(dpDatum.getValue())) {
			RegistracijaDTO registracijaDTO = new RegistracijaDTO(this.clan.getId(),
					cbSezona.getSelectionModel().getSelectedItem(),
					cbKategorija.getSelectionModel().getSelectedItem().getId(), dpDatum.getValue(), null, clan);
			if(DAOFactory.getDAOFactory().getRegistracijaDAO().insert(registracijaDTO)){
				DAOFactory.getDAOFactory().getClanDAO().setRegistrovan(true, this.clan.getId());
				AlertDisplay.showInformation("Registracija", "Registracija uspješna.");
			}
		} else {
			AlertDisplay.showError("Registracija", "Unesite datum.");
		}
	}

	private static ObservableList<String> sezone = FXCollections.observableArrayList();
	static {
		for (int i = 2017; i >= 2000; i--) {
			sezone.add(i + "/" + (i + 1));
		}
	}

	public void setClan(ClanDTO clan) {
		this.clan = clan;
		lblIme.setText(this.clan.getIme());
		lblPrezime.setText(this.clan.getPrezime());
		lblImeRoditelja.setText(this.clan.getImeRoditelja());
		lblJmb.setText(this.clan.getJmb());
		lblDatumR.setText(new SimpleDateFormat("dd.MM.yyyy").format(this.clan.getDatumRodjenja()));
		List<String> telefoni = DAOFactory.getDAOFactory().getOsobaDAO().getTelefoni(clan.getId());
		ObservableList<String> tels = FXCollections.observableArrayList();
		for (String tel : telefoni) {
			tels.add(tel);
		}
		lstBrojeviTelefona.setItems(tels);
	}

	private ClanDTO clan;
}
