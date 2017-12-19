package application.gui.racunovodja.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.ClanarinaDAO;
import application.model.dao.OpremaKlubaDAO;
import application.model.dto.ClanDTO;
import application.model.dto.ClanarinaDTO;
import application.model.dto.OpremaKlubaDTO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;

import javafx.scene.control.ComboBox;

import javafx.scene.control.TextArea;

import javafx.scene.layout.AnchorPane;

import javafx.scene.control.RadioButton;

import javafx.scene.control.Spinner;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
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
		
		ObservableList<ClanarinaDTO> listaClanarina = ClanarinaDAO.SELECT_ALL();
		tableClanarine.setItems(listaClanarina);
	}
	
	private void popuniComboBox() {
		//ObservableList<ClanaDTO> listaClanarina = ClanDAO.SELECT_ALL();
	}
}

/*public static void INSERT(OpremaClana oprema) {
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