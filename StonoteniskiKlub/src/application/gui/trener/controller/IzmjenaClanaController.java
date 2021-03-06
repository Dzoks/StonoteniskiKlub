package application.gui.trener.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import com.mysql.jdbc.Blob;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.ClanDTO;
import application.model.dto.OsobaDTO;
import application.util.AlertDisplay;
import application.util.ConnectionPool;
import application.util.ErrorLogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

public class IzmjenaClanaController extends BaseController implements Initializable{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtIme;

    @FXML
    private TextField txtPrezime;

    @FXML
    private TextField txtImeRoditelja;

    @FXML
    private TextField txtJMB;

    @FXML
    private RadioButton rbMusko;

    @FXML
    private RadioButton rbZensko;

    @FXML
    private DatePicker dpDatumRodjenja;

    @FXML
    private Button btnUclani;

    @FXML
    private ImageView ivSlika;

    @FXML
    private TextField txtTelefon;

    @FXML
    private ListView<String> lvTelefoni;


    @FXML
	public
    void izlaz() {
    	primaryStage.close();
    }


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		ToggleGroup group = new ToggleGroup();
		rbMusko.setToggleGroup(group);
		rbZensko.setToggleGroup(group);
		
		dpDatumRodjenja.setConverter(new StringConverter<LocalDate>()
		{
		    private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd.MM.yyyy.");

		    @Override
		    public String toString(LocalDate localDate)
		    {
		        if(localDate==null)
		            return "";
		        return dateTimeFormatter.format(localDate);
		    }

		    @Override
		    public LocalDate fromString(String dateString)
		    {
		        if(dateString==null || dateString.trim().isEmpty())
		        {
		            return null;
		        }
		        return LocalDate.parse(dateString,dateTimeFormatter);
		    }
		});
		
		btnUclani.disableProperty().bind(txtIme.textProperty().isEmpty().or(
				txtPrezime.textProperty().isEmpty().or(
						txtJMB.textProperty().isEmpty().or(
								dpDatumRodjenja.valueProperty().isNull().or(
										group.selectedToggleProperty().isNull().or(
												lvTelefoni.itemsProperty().isNull()))))));
	}
	
	private ClanDTO clan;
	
	public void setClan(ClanDTO clan) {
		this.clan = clan;
	}
	
	public void dodajFotografiju() {
		FileChooser fileChoser = new FileChooser();
		fileChoser.setInitialDirectory(new File(System.getProperty("user.home").toString() + File.separator + "Pictures"));
		File slika = fileChoser.showOpenDialog(primaryStage);
		if(slika != null) {
			ivSlika.setImage(new Image(slika.toURI().toString()));
			slikaPromijenjena = true;
			fotografija = slika;
		}
	}
	
	public void obrisiFotografiju() {
		ivSlika.setImage(new Image(getClass().getResourceAsStream("/avatar.png")));
		slikaPromijenjena = true;
		fotografija = null;
	}
	
	@FXML
	public void sacuvajIzmjene(ActionEvent event) {
		String ime = txtIme.getText();
		String prezime = txtPrezime.getText();
		String imeRoditelja = txtImeRoditelja.getText();
		String jmb = txtJMB.getText();
		char pol = rbMusko.isSelected()?'M':'Ž';
		Date datumRodjenja = Date.from(dpDatumRodjenja.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		ArrayList<String> telefoni = new ArrayList<>();
		for(String s: listaTelefona) {
			telefoni.add(s);
		}
		
		if(!jmb.matches("[0-9]*") || !(jmb.length() == 13)) {
			AlertDisplay.showError("Izmjena", "Jedinstveni matični broj (JMB) nije dobro unesen.");
			return;
		}
		OsobaDTO osoba = DAOFactory.getDAOFactory().getOsobaDAO().getByJmb(jmb);
		if(osoba != null && osoba.getId() != clan.getId()) {
			AlertDisplay.showError("Izmjena", "Jedinstveni matični broj (JMB) već postoji u bazi podataka. Pokušajte ponovo.");
			return;
		}
		
		for(String tel : telefoni) {
			int id = DAOFactory.getDAOFactory().getOsobaDAO().getIdByTelefon(tel);
			if(id!=0 && id!=clan.getId()) {
				AlertDisplay.showError("Izmjena", "Broj: " + tel + " pripada nekom drugom.");
				return;
			}
		}
//		if(telefoni.size() == 0) {
//			AlertDisplay.showError("Izmjena", "Bar jedan broj telefona mora biti unesen.");
//			return;
//		}
		
		DAOFactory.getDAOFactory().getOsobaDAO().deleteTelefon(clan.getId());
		
		clan.setIme(ime);
		clan.setPrezime(prezime);
		clan.setImeRoditelja(imeRoditelja);
		clan.setTelefoni(telefoni);
		clan.setDatumRodjenja(datumRodjenja);
		clan.setJmb(jmb);
		clan.setPol(pol);
		if(slikaPromijenjena) {
			try {
				clan.setSlika(convertImageToBlob());
			} catch (IOException e) {
				new ErrorLogger().log(e);
				 ;
			}
		}
		
		DAOFactory.getDAOFactory().getOsobaDAO().insertTelefon(clan);
		DAOFactory.getDAOFactory().getOsobaDAO().update(clan);
		primaryStage.close();
	}
	
	private Blob convertImageToBlob() throws IOException {
		if(fotografija == null)
			return null;
		Connection conn;
		try {
			conn = ConnectionPool.getInstance().checkOut();
			Blob blob = (Blob) conn.createBlob();
			
			blob.setBytes(1, Files.readAllBytes(fotografija.toPath()));
			return blob;
		} catch (SQLException e) {
			 ;
			new ErrorLogger().log(e);
			return null;
		}
	}
	
	public void obrisiTelefon() {
		int index = lvTelefoni.getSelectionModel().getSelectedIndex();
		listaTelefona.remove(index);
	}
	
	public void sacuvajTelefon() {
		String noviTelefon = txtTelefon.getText();
		if(noviTelefon.matches("[0-9][0-9][0-9]/[0-9][0-9][0-9]-[0-9][0-9][0-9]")) {
			listaTelefona.add(noviTelefon);
			txtTelefon.clear();
		}
		else {
			AlertDisplay.showError("Izmjena", "Broj telefona nije u dobrom formatu. Format je XXX/XXX-XXX.");
		}
	}
	
	public void popuniPolja() {
		txtIme.setText(clan.getIme());
		txtPrezime.setText(clan.getPrezime());
		txtImeRoditelja.setText(clan.getImeRoditelja());
		txtJMB.setText(clan.getJmb());
		if(clan.getPol()=='M')
			rbMusko.setSelected(true);
		else
			rbZensko.setSelected(true);
		listaTelefona = FXCollections.observableArrayList(clan.getTelefoni());
		lvTelefoni.setItems(listaTelefona);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		formatter = formatter.withLocale( Locale.US );  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
		LocalDate date = null;
		try {
			date = LocalDate.parse(clan.getDatumRodjenja().toString(), formatter);
		}catch(DateTimeParseException e) {
			date = clan.getDatumRodjenja().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}
		dpDatumRodjenja.setValue(date);
		try {
			java.sql.Blob blob = clan.getSlika();
			if(blob != null) {
				ivSlika.setImage(new Image(blob.getBinaryStream()));
			}
			else {
				ivSlika.setImage(new Image(getClass().getResourceAsStream("/avatar.png")));
			}
		} catch (SQLException e) {
			new ErrorLogger().log(e);
			 ;
		}
	}
	
	private ObservableList<String> listaTelefona;
	private boolean slikaPromijenjena = false;
	private File fotografija;
}

