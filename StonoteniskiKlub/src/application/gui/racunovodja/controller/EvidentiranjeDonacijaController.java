package application.gui.racunovodja.controller;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.DonacijaDTO;
import application.model.dto.TipTransakcijeDTO;
import application.model.dto.TransakcijaDTO;
import application.util.ErrorLogger;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class EvidentiranjeDonacijaController extends BaseController{
	@FXML
	private AnchorPane pane;
	@FXML
	private TableView<DonacijaDTO> tableDonacije;
	@FXML
	private TableColumn<DonacijaDTO,String> tableColumnSponzor;
	@FXML
	private TableColumn<DonacijaDTO,Double> tableColumnIznos;
	@FXML
	private TableColumn<DonacijaDTO,String> tableColumnDatumUplate;
	@FXML
	private TableColumn<DonacijaDTO,String> tableColumnOpis;
	@FXML
	private TableColumn<DonacijaDTO,String> tableColumnObradjeno;
	@FXML
	private Label lblPrikazPo;
	@FXML
	private RadioButton radiobtnSve;
	@FXML
	private RadioButton radiobtnNeobradjene;
	@FXML
	private Label lblSponzor;
	
	@FXML
	private Button btnPrikazi;
	@FXML
	private Button btnObradi;
	@FXML
	private Button btnNeobradjeno;
	@FXML
	private TextField txtOpis;
	@FXML
	private DatePicker datePicker;
	private ObservableList<DonacijaDTO> listaDonacija;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listaDonacija = DAOFactory.getDAOFactory().getDonacijaDAO().neobradjene(true);
		postaviKolone();
		tableDonacije.setItems(listaDonacija);
		BooleanBinding binding = tableDonacije.getSelectionModel().selectedItemProperty().isNull().or(datePicker.valueProperty().isNull());
		btnObradi.disableProperty().bind(binding);
		BooleanBinding binding2 =  tableDonacije.getSelectionModel().selectedItemProperty().isNull();
		btnNeobradjeno.disableProperty().bind(binding2);
	}
	  @FXML
	    void odjaviteSe(ActionEvent event) {
	    	try {
				BaseController.changeScene("/application/gui/administrator/view/LoginView.fxml", primaryStage);
			} catch (IOException e) {
				e.printStackTrace();
				new ErrorLogger().log(e);
			}
	    }
	private void postaviKolone() {
		tableColumnIznos.setCellValueFactory(new PropertyValueFactory<DonacijaDTO,Double>("novcaniIznos"));
		tableColumnOpis.setCellValueFactory(new PropertyValueFactory<DonacijaDTO,String>("opisTransakcije"));
		tableColumnDatumUplate.setCellValueFactory(new PropertyValueFactory<DonacijaDTO,String>("datumUplate"));
		tableColumnObradjeno.setCellValueFactory(new PropertyValueFactory<DonacijaDTO,String>("obradjeno"));
		tableColumnSponzor.setCellValueFactory(new PropertyValueFactory<DonacijaDTO,String>("nazivSponzora"));
	}
	public void obradi() {
		//opis, iznos, datum -> nova transakcija
		//setuj id transakcije u donaciju sa odredjenim id
		//postavi na obradjeno -> Cilijeva metoda
		String opis = txtOpis.getText();
		LocalDate localDate = datePicker.getValue();
		Instant instant = null;
		Date datum = null;
		if(localDate!=null) {
			instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
			datum = Date.from(instant);
		}
		DonacijaDTO donacija = tableDonacije.getSelectionModel().getSelectedItem();
		Double iznos = donacija.getNovcaniIznos().doubleValue();
		TipTransakcijeDTO tip = DAOFactory.getDAOFactory().getTipTransakcijeDAO().getById(6);
		TransakcijaDTO transakcija = new TransakcijaDTO(null, datum, iznos, opis, tip.getTip(), true);
		int id = DAOFactory.getDAOFactory().getTransakcijaDAO().INSERT(transakcija,tip );
		DAOFactory.getDAOFactory().getDonacijaDAO().setObradjeno(donacija);
		transakcija.setId(id);
		donacija.setObradjeno(true);
		DAOFactory.getDAOFactory().getDonacijaDAO().setIdTransakcije(donacija, id);
		
		donacija.setTransakcija(transakcija);
		DAOFactory.getDAOFactory().getNovcanaSredstvaDAO().dodajPrihode(iznos);
		tableDonacije.refresh();
	}
	public void neObradi() { //obrisati
		DonacijaDTO donacija = tableDonacije.getSelectionModel().getSelectedItem();
		donacija.setObradjeno(false);
		tableDonacije.refresh();
		Double iznos = donacija.getNovcaniIznos().doubleValue();
		DAOFactory.getDAOFactory().getNovcanaSredstvaDAO().dodajPrihode(-iznos);
	}
	
}
