package application.gui.administrator.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.KorisnickiNalogDAO;
import application.model.dao.KorisnickiNalogTipDAO;
import application.model.dao.ZaposleniDAO;
import application.model.dto.KorisnickiNalogDTO;
import application.model.dto.KorisnickiNalogTipDTO;
import application.model.dto.ZaposleniDTO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

	// Event Listener on Button.onAction
	@FXML
	public void dodajteNalogKlik(ActionEvent event) {
		if (!korisnickoIme.getText().isEmpty()
				&& !tabelaZaposleni.getSelectionModel().isEmpty()) {
				if(dodajNalog())
					new Alert(AlertType.INFORMATION, "Korisnički nalog je uspješno dodan.").show();
				else {
					new Alert(AlertType.INFORMATION, "Korisničko ime već postoji.").show();
				}
				korisnickoIme.clear();
		} else {
			if (korisnickoIme.getText().isEmpty())
				new Alert(AlertType.ERROR, "Unesite korisničko ime.").show();
			if (ulogaChoiceBox.getSelectionModel().getSelectedItem().getNaziv().isEmpty())
				new Alert(AlertType.ERROR, "Odaberite zaposlenog.").show();
		}
	}

	private boolean dodajNalog() {
		Integer ulogaId=KorisnickiNalogTipDAO.selectId(ulogaChoiceBox.getSelectionModel().getSelectedItem().getNaziv());
		Integer zaposleniId=tabelaZaposleni.getSelectionModel().getSelectedItem().getId();
		String ime=korisnickoIme.getText();
		java.sql.Date sqlDate = java.sql.Date.valueOf( LocalDate.now() );
		if(!KorisnickiNalogDAO.daLiPostoji(ime)) {
		KorisnickiNalogDTO nalog=new KorisnickiNalogDTO(ime,null,sqlDate,true,ulogaId,zaposleniId);
		KorisnickiNalogDAO.insert(nalog);
		return true;
		}else {
			return false;
		}
	}



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		popuniTabelu();
		popuniChoiceBox();

	}

	private void popuniChoiceBox() {
		ObservableList<KorisnickiNalogTipDTO> listaTipNaloga = KorisnickiNalogTipDAO.SELECT_ALL();
		ulogaChoiceBox.setItems(listaTipNaloga);
		ulogaChoiceBox.getSelectionModel().selectFirst();		
	}

	private void popuniTabelu() {
		kolonaJmbg.setCellValueFactory(new PropertyValueFactory<ZaposleniDTO, String>("jmb"));
		kolonaIme.setCellValueFactory(new PropertyValueFactory<ZaposleniDTO, String>("ime"));
		kolonaPrezime.setCellValueFactory(new PropertyValueFactory<ZaposleniDTO, String>("prezime"));

		ObservableList<ZaposleniDTO> listaZaposlenih =ZaposleniDAO.selectAll();

		tabelaZaposleni.setItems(listaZaposlenih);		
	}
}
