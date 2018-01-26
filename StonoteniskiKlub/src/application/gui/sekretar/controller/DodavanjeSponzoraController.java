package application.gui.sekretar.controller;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dao.SponzorDAO;
import application.model.dto.SponzorDTO;
import application.model.dto.UgovorDTO;
import application.util.AlertDisplay;
import application.util.ErrorLogger;
import application.util.InputValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DodavanjeSponzoraController extends BaseController {
	@FXML
	private TextField txtNaziv;
	@FXML
	private TextField txtAdresa;
	@FXML
	private TextField txtMail;
	@FXML
	private TextField tfTelefon;
	@FXML
	private DatePicker dpDatumOd;
	@FXML
	private DatePicker dpDatumDo;
	@FXML
	private Button btnDodajTelefon;
	@FXML
	private TextArea taOpis;
	@FXML
	private Button btnDodajSponzora;
	@FXML
	private ListView<String> lstTelefoni;
	@FXML
	private Button btnUkloniTelefon;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bindDisable();
	}

	// Event Listener on Button[#btnDodajTelefon].onAction
	@FXML
	public void dodajTelefon(ActionEvent event) {
		String brTelefona = tfTelefon.getText();
		if(InputValidator.validateTelefon(brTelefona)){
			ObservableList<String> telefoni = lstTelefoni.getItems();
			if(!telefoni.contains(brTelefona)){
				telefoni.add(brTelefona);
				if(!uklonjeniTelefoni.contains(brTelefona)){
					noviTelefoni.add(brTelefona);
				}
			}
			if(uklonjeniTelefoni.contains(brTelefona)){
				uklonjeniTelefoni.remove(telefoni);
			}
		}
	}
	@FXML
	public void ukloniTelefon(ActionEvent event){
		String telefon = lstTelefoni.getSelectionModel().getSelectedItem();
		lstTelefoni.getItems().remove(lstTelefoni.getSelectionModel().getSelectedIndex());
		uklonjeniTelefoni.add(telefon);
	}

	// Event Listener on Button[#btnDodajSponzora].onAction
	@FXML
	public void sacuvaj(ActionEvent event) {
		if (trenutniSponzor == null) {
			if (InputValidator.allEntered(txtNaziv.getText(), txtAdresa.getText(), txtMail.getText(),
					dpDatumOd.getValue(), taOpis.getText())) {
				DAOFactory factory = DAOFactory.getDAOFactory();
				SponzorDAO sponzorDAO = factory.getSponzorDAO();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date datumOd = null;
				Date datumDo = null;
				try {
					datumOd = formatter.parse(dpDatumOd.getValue().toString());
					if (dpDatumDo.getValue() != null) {
						datumDo = formatter.parse(dpDatumDo.getValue().toString());
					}
				} catch (ParseException e) {
					e.printStackTrace();
					new ErrorLogger().log(e);
				}
				SponzorDTO sponzor = new SponzorDTO(null, txtNaziv.getText(), txtAdresa.getText(), txtMail.getText(),
						null, null);
				UgovorDTO ugovor = new UgovorDTO(null, datumOd, datumDo, taOpis.getText(), null);
				if (sponzorDAO.insert(sponzor, ugovor)) {
					ObservableList<UgovorDTO> list = FXCollections.observableArrayList();
					sponzor.setUgovori(list);
					sponzor.getUgovori().add(ugovor);
					parent.dodajSponzora(sponzor);
					for(String telefon : noviTelefoni){
						DAOFactory.getDAOFactory().getSponzorDAO().insertTelefon(sponzor, telefon);
					}
					noviTelefoni.clear();
					sponzor.setTelefoni(lstTelefoni.getItems());
					AlertDisplay.showInformation("Dodavanje", "Sponzor uspješno dodat.");
				} else {
					AlertDisplay.showError("Dodavanje", "Dodavanje nije uspjelo.");
				}
			} else {
				AlertDisplay.showError("Dodavanja", "Niste unijeli sve podatke.");
			}
		}else{
			if(InputValidator.allEntered(txtAdresa.getText(), txtNaziv.getText(), txtMail.getText())){
				trenutniSponzor.setNaziv(txtNaziv.getText());
				trenutniSponzor.setAdresa(txtAdresa.getText());
				trenutniSponzor.setEmail(txtMail.getText());
				trenutniSponzor.setTelefoni(lstTelefoni.getItems());
				DAOFactory.getDAOFactory().getSponzorDAO().update(trenutniSponzor);
				for(String telefon : noviTelefoni){
					DAOFactory.getDAOFactory().getSponzorDAO().insertTelefon(trenutniSponzor, telefon);
				}
				parent.zamijeni(trenutniSponzor);
				noviTelefoni.clear();
				AlertDisplay.showInformation("Izmjena", "Sponzor uspješno ažuriran.");
				parent.refresh();
			}else {
				AlertDisplay.showError("Izmjena", "Niste unijeli sve podatke.");
			}
		}
		for(String telefon: uklonjeniTelefoni){
			DAOFactory.getDAOFactory().getSponzorDAO().deleteTelefon(telefon);
		}
		uklonjeniTelefoni.clear();
		Stage stage = (Stage)btnDodajSponzora.getScene().getWindow();
		stage.close();
	}

	public void setParentController(RadSaSponzorimaController parent) {
		this.parent = parent;
	}

	public void setTrenutniSponzor(SponzorDTO trenutniSponzor) {
		this.trenutniSponzor = trenutniSponzor;
		if (this.trenutniSponzor != null) {
			txtNaziv.setText(trenutniSponzor.getNaziv());
			txtAdresa.setText(trenutniSponzor.getAdresa());
			txtMail.setText(trenutniSponzor.getEmail());
			dpDatumOd.setDisable(true);
			dpDatumDo.setDisable(true);
			taOpis.setDisable(true);
			lstTelefoni.setItems(trenutniSponzor.getTelefoni());
		}
	}

	private RadSaSponzorimaController parent;
	private SponzorDTO trenutniSponzor;
	private ObservableList<String> uklonjeniTelefoni = FXCollections.observableArrayList();
	private ObservableList<String> noviTelefoni = FXCollections.observableArrayList();
	private void bindDisable(){
		btnUkloniTelefon.disableProperty().bind(lstTelefoni.getSelectionModel().selectedItemProperty().isNull());
	}
}
