package application.gui.administrator.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.KorisnickiNalogDTO;
import application.model.dto.KorisnickiNalogTipDTO;
import application.model.dto.ZaposleniDTO;
import application.util.AlertDisplay;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class DodajNalogController extends BaseController {
	@FXML
	private TableColumn<ZaposleniDTO, String> kolonaJmbg;
	@FXML
	private TableColumn<ZaposleniDTO, String> kolonaIme;
	@FXML
	private TableColumn<ZaposleniDTO, String> kolonaPrezime;
	@FXML
	private ChoiceBox<KorisnickiNalogTipDTO> ulogaChoiceBox;
    @FXML
    private TableView<ZaposleniDTO> tabelaZaposleni;
	@FXML
	private TextField korisnickoIme;
    @FXML
    private Button dodajteNalogDugme;
    

	// Event Listener on Button.onAction
	@FXML
	public void dodajteNalogKlik(ActionEvent event) {
		if (!korisnickoIme.getText().isEmpty()|| !tabelaZaposleni.getSelectionModel().isEmpty()) {
				if(dodajNalog())
						AlertDisplay.showInformation("Dodavanje", "Korisnički nalog je uspješno dodan.");
				else {
					AlertDisplay.showError("Dodavanje", "Korisničko ime već postoji.");
				}
				korisnickoIme.clear();
		} else {
			if (korisnickoIme.getText().isEmpty())
				new Alert(AlertType.ERROR, "Unesite korisničko ime.").show();
			if (ulogaChoiceBox.getSelectionModel().getSelectedItem().getNaziv().isEmpty())
				new Alert(AlertType.ERROR, "Odaberite tip naloga.").show();
		}
	}
	private boolean dodajNalog() {
		Integer ulogaId=ulogaChoiceBox.getSelectionModel().getSelectedItem().getId();
		Integer zaposleniId=null;
		if(!tabelaZaposleni.getSelectionModel().isEmpty())
		 zaposleniId=tabelaZaposleni.getSelectionModel().getSelectedItem().getId();
		
		String ime=korisnickoIme.getText();
		java.sql.Date sqlDate = java.sql.Date.valueOf( LocalDate.now() );
		if(!DAOFactory.getDAOFactory().getKorisnickiNalogDAO().daLiPostoji(ime)) {
		KorisnickiNalogDTO nalog=new KorisnickiNalogDTO(ime,null,sqlDate,true,ulogaId,zaposleniId);
		DAOFactory.getDAOFactory().getKorisnickiNalogDAO().insert(nalog);
		return true;
		}else {
			return false;
		}
	}



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		popuniTabelu();
		popuniChoiceBox();
		dodajteNalogDugme.disableProperty().bind(korisnickoIme.textProperty().isEmpty().or(ulogaChoiceBox.getSelectionModel().selectedItemProperty().isNull()));
		

	}

	private void popuniChoiceBox() {
		ObservableList<KorisnickiNalogTipDTO> listaTipNaloga = DAOFactory.getDAOFactory().getKorisnickiNalogTipDAO().SELECT_ALL();
		ulogaChoiceBox.setItems(listaTipNaloga);
		ulogaChoiceBox.getSelectionModel().selectFirst();		
	}

	private void popuniTabelu() {
		kolonaJmbg.setCellValueFactory(new PropertyValueFactory<ZaposleniDTO, String>("jmb"));
		kolonaIme.setCellValueFactory(new PropertyValueFactory<ZaposleniDTO, String>("ime"));
		kolonaPrezime.setCellValueFactory(new PropertyValueFactory<ZaposleniDTO, String>("prezime"));

		ObservableList<ZaposleniDTO> listaZaposlenih = DAOFactory.getDAOFactory().getZaposleniDAO().selectAll();

		tabelaZaposleni.setItems(listaZaposlenih);		
	}
}
