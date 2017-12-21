package application.gui.racunovodja.controller;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.ClanDAO;
import application.model.dao.ClanarinaDAO;
import application.model.dao.OpremaKlubaDAO;
import application.model.dto.ClanDTO;
import application.model.dto.ClanarinaDTO;
import application.model.dto.OpremaKlubaDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;

import javafx.scene.control.ComboBox;

import javafx.scene.control.TextArea;

import javafx.scene.layout.AnchorPane;

import javafx.scene.control.RadioButton;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableView;

import javafx.scene.control.DatePicker;

import javafx.scene.control.TableColumn;

public class EvidentiranjeClanarinaController extends BaseController{
	@FXML
	private AnchorPane pane;
	@FXML
	private TableView<ClanarinaDTO> tableClanarine;
	@FXML
	private TableColumn<ClanarinaDTO,String> tableColumnIme;
	@FXML
	private TableColumn<ClanarinaDTO,String> tableColumnPrezime;
	@FXML
	private TableColumn<ClanarinaDTO,Integer> tableColumnMjesec;
	@FXML
	private TableColumn<ClanarinaDTO,Integer> tableColumnGodina;
	@FXML
	private TableColumn<ClanarinaDTO,String> tableColumnDatumUplate;
	@FXML
	private TableColumn<ClanarinaDTO, Double> tableColumnIznos;
	@FXML
	private Label lblPrikazPo;
	@FXML
	private RadioButton radiobtnClan;
	@FXML
	private RadioButton radiobtnMjesec;
	@FXML
	private Label lblMjesec;
	@FXML
	private Spinner<Integer> spinnerMjesecPrikazi;
	@FXML
	private Label lblClan;
	@FXML
	private ComboBox<ClanDTO> comboBoxClanPrikazi;
	@FXML
	private Button btnDodaj;
	@FXML
	private Label lblMjesecDodaj;
	@FXML
	private Spinner<Integer> spinnerMjesecDodaj;
	@FXML
	private Label lblClanDodaj;
	@FXML
	private ComboBox<ClanDTO> comboBoxClanDodaj;
	@FXML
	private Label lblIznos;
	@FXML
	private TextField txtIznos;
	@FXML
	private Label lblKM;
	@FXML
	private Label lblDatumUplate;
	@FXML
	private DatePicker datePicker;
	@FXML
	private Button btnPrikazi;
	@FXML
	private Button btnObrisi;
	@FXML
	private Button btnIzmijeni;
	@FXML
	private Label lblGodina;
	@FXML
	private Spinner<Integer> spinnerGodina;
	@FXML
	private Label lblGodinaDodaj;
	@FXML
	private Spinner<Integer> spinnerGodinaDodaj;
	@FXML
	private Label lblOpis;
	@FXML
	private TextArea txtOpis;
	
	private ObservableList<ClanarinaDTO> listaClanarina;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ToggleGroup group = new ToggleGroup();
		radiobtnClan.setToggleGroup(group);
		radiobtnMjesec.setToggleGroup(group);
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 1);
		SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(2010, 2018, 2014);
		spinnerMjesecDodaj.setValueFactory(valueFactory);
		  spinnerMjesecPrikazi.setValueFactory(valueFactory);
		  spinnerGodinaDodaj.setValueFactory(valueFactory1);
		  spinnerGodina.setValueFactory(valueFactory1);
		this.popuniTabelu();
		this.popuniComboBox();
	}
	
	private void popuniTabelu() {
		tableColumnIme.setCellValueFactory(new PropertyValueFactory<ClanarinaDTO, String>("imeClana"));
		tableColumnPrezime.setCellValueFactory(new PropertyValueFactory<ClanarinaDTO, String>("prezimeClana"));
		tableColumnMjesec.setCellValueFactory(new PropertyValueFactory<ClanarinaDTO, Integer>("mjesec"));
		tableColumnGodina.setCellValueFactory(new PropertyValueFactory<ClanarinaDTO, Integer>("godina"));
		tableColumnIznos.setCellValueFactory(new PropertyValueFactory<ClanarinaDTO, Double>("iznos"));
		tableColumnDatumUplate.setCellValueFactory(new PropertyValueFactory<ClanarinaDTO,String>("datum"));
		
		listaClanarina = ClanarinaDAO.SELECT_ALL();
		tableClanarine.setItems(listaClanarina);
	}
	
	private void popuniComboBox() {
		ObservableList<ClanDTO> listaClanarina = ClanDAO.SELECT_ALL();
		comboBoxClanDodaj.setItems(listaClanarina);
		comboBoxClanDodaj.getSelectionModel().selectFirst();
		comboBoxClanPrikazi.setItems(listaClanarina);
		comboBoxClanPrikazi.getSelectionModel().selectFirst();
	}
	
	public void dodaj() {
		System.out.println("dodaj");
		//pokupi podatke sa polja i posalji to u proceduru koja ce napraviti transakciju i clanarinu
		Double iznos = Double.parseDouble(txtIznos.getText());
		Integer mjesec = spinnerMjesecDodaj.getValue();
		Integer godina = spinnerGodinaDodaj.getValue();
		ClanDTO clan = comboBoxClanDodaj.getValue();
		String opis = txtOpis.getText();
		LocalDate localDate = datePicker.getValue();
		Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
		Date datum = Date.from(instant);
		String tipTransakcije = "clanarina"; //hardcode, popraviti hashmap...!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		ClanarinaDTO clanarina = new ClanarinaDTO(null, datum, iznos, opis, tipTransakcije, mjesec, godina, clan.getIme(), clan.getPrezime(),clan.getId());
		ClanarinaDAO.INSERT(clanarina, clan);
		listaClanarina.add(clanarina);
	}
	public void obrisi() {
		ClanarinaDTO clanarina = tableClanarine.getSelectionModel().getSelectedItem();
		
	}
	
	public void prikazi() {
		System.out.println("prikazi");
		ObservableList<ClanarinaDTO> lista = FXCollections.observableArrayList();
		if(radiobtnClan.isSelected()) {
			ClanDTO clan = comboBoxClanPrikazi.getValue();
			for(ClanarinaDTO cl : listaClanarina) {
				if(cl.getClanId() == clan.getId()) {
					lista.add(cl);
				}
			}
			tableClanarine.setItems(lista);
		}else if(radiobtnMjesec.isSelected()) {
			System.out.println("mjesec");
			int mjesec = spinnerMjesecPrikazi.getValue();
			int godina = spinnerGodina.getValue();
			for(ClanarinaDTO cl : listaClanarina) {
				if(cl.getMjesec().get()==mjesec && cl.getGodina().get()==godina) {
					lista.add(cl);
				}
			}
			tableClanarine.setItems(lista);
		}
	}
}

/*
 * 
 private static final String SQL_INSERT = "{call dodaj_opremu_clana(?,?,?,?,?,?)}";
 * public static void INSERT(OpremaClana oprema) {
 
	Connection c = null;
	CallableStatement cs = null;
	
	try{
		c = ConnectionPool.getInstance().checkOut();
		cs = c.prepareCall(SQL_INSERT);
		cs.setBoolean("inDonirana", oprema.getDonirana());
		cs.setInt("inOpremaTipId", oprema.getIdTipaOpreme());
		
		if(oprema.getIdNarudzbe() == null) {
			cs.setNull("inNarudzbaId", Types.INTEGER);
		}
		else {
			cs.setInt("inNarudzbaId", oprema.getIdNarudzbe());
		}
		if(oprema.getIdDonacije() == null) {
			cs.setNull("inDonacijaId", Types.INTEGER);
		}
		else {
			cs.setInt("inDonacijaId", oprema.getIdDonacije());
		}
		
		cs.setString("inVelicina", oprema.getVelicina());
		cs.setInt("inClanId", oprema.getIdClana());
		cs.executeQuery();
	}catch (SQLException e) {
		e.printStackTrace();
	}finally {
		ConnectionPool.getInstance().checkIn(c);
		ConnectionPool.close(cs);
	}*/