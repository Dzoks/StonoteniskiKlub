package application.gui.sekretar.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dao.SponzorDAO;
import application.model.dao.UgovorDAO;
import application.model.dto.SponzorDTO;
import application.model.dto.UgovorDTO;
import application.util.AlertDisplay;
import application.util.InputValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		telefoni = new ArrayList<String>();
	}

	// Event Listener on Button[#btnDodajTelefon].onAction
	@FXML
	public void dodajTelefon(ActionEvent event) {
		String brTelefona = tfTelefon.getText();
		if (!telefoni.contains(brTelefona)) {
			telefoni.add(brTelefona);
			tfTelefon.setText("");
		} else {
			AlertDisplay.showInformation("Greska", "", "Broj telefona ranije unesen.");
		}
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
				}
				SponzorDTO sponzor = new SponzorDTO(null, txtNaziv.getText(), txtAdresa.getText(), txtMail.getText(),
						null);
				UgovorDTO ugovor = new UgovorDTO(null, datumOd, datumDo, taOpis.getText(), null);
				if (sponzorDAO.insert(sponzor, ugovor)) {
					ObservableList<UgovorDTO> list = FXCollections.observableArrayList();
					sponzor.setUgovori(list);
					sponzor.getUgovori().add(ugovor);
					parent.dodajSponzora(sponzor);
					AlertDisplay.showInformation("Uspjesno", "", "Sponzor uspjesno dodan.");
				} else {
					AlertDisplay.showInformation("Greska", "", "Dodavanje nije uspjelo.");
				}
			} else {
				AlertDisplay.showInformation("Greska", "", "Niste unijeli sve podatke.");
			}
		}else{
			if(InputValidator.allEntered(txtAdresa.getText(), txtNaziv.getText(), txtMail.getText())){
				trenutniSponzor.setNaziv(txtNaziv.getText());
				trenutniSponzor.setAdresa(txtAdresa.getText());
				trenutniSponzor.setEmail(txtMail.getText());
				DAOFactory.getDAOFactory().getSponzorDAO().update(trenutniSponzor);
				parent.zamijeni(trenutniSponzor);
				AlertDisplay.showInformation("Uspjesno", "", "Sponzor uspjesno azuriran.");
			}else {
				AlertDisplay.showInformation("Greska", "", "Niste unijeli sve podatke.");
			}
		}
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
		}
	}

	private List<String> telefoni;
	private RadSaSponzorimaController parent;
	private SponzorDTO trenutniSponzor;
}
