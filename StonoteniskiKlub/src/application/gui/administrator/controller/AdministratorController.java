package application.gui.administrator.controller;

import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.KorisnickiNalogDAO;
import application.model.dao.ZaposleniDAO;
import application.model.dto.KorisnickiNalogDTO;
import application.model.dto.ZaposleniDTO;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;

public class AdministratorController extends BaseController {
	@FXML
	private TableView tabelaNalog;
	@FXML
	private TableColumn kolonaIme;
	@FXML
	private TableColumn kolonaPrezime;
	@FXML
	private TableColumn kolonaUloga;
	@FXML
	private TableColumn kolonaKorisnickoIme;

	@FXML
	private Button obrisiNalogDugme;

	@FXML
	public void dodajNalogKlik(ActionEvent event) {
		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		try {
			BaseController.changeScene("/application/gui/administrator/view/DodajNalogView.fxml", stage);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void obrisiNalogKlik(ActionEvent event) {
		if (tabelaNalog.getSelectionModel().getSelectedItem() != null) {
			KorisnickiNalogDAO.setAktivan(0,
					((KorisnickiNalogDTO) tabelaNalog.getSelectionModel().getSelectedItem()).getNalogId());
			new Alert(AlertType.INFORMATION, "Uspjesno ste obrisali nalog.", ButtonType.OK).show();

			popuniTabelu();
		} else {
			new Alert(AlertType.ERROR, "Odaberite korisnički nalog koji želite obrisati.", ButtonType.OK).show();
		}
	}

	@FXML
	void osvjeziDugmeKlik(ActionEvent event) {
		popuniTabelu();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		popuniTabelu();
	}

	private void popuniTabelu() {
		kolonaUloga.setCellValueFactory(new PropertyValueFactory<KorisnickiNalogDTO, String>("nazivUloge"));
		kolonaIme.setCellValueFactory(new PropertyValueFactory<KorisnickiNalogDTO, String>("ime"));
		kolonaPrezime.setCellValueFactory(new PropertyValueFactory<KorisnickiNalogDTO, String>("prezime"));
		kolonaKorisnickoIme.setCellValueFactory(new PropertyValueFactory<KorisnickiNalogDTO, String>("korisnickoIme"));

		ObservableList<KorisnickiNalogDTO> listaNaloga = KorisnickiNalogDAO.selectAll();

		tabelaNalog.setItems(listaNaloga);
		// obrisiNalogDugme.disableProperty().bind(Bindings.isEmpty(tabelaNalog.getSelectionModel().getSelectedItems()));

	}
}
