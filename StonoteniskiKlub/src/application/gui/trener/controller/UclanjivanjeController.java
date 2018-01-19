package application.gui.trener.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.ClanDTO;
import application.model.dto.OsobaDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

public class UclanjivanjeController extends BaseController implements Initializable {

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
    private ImageView ivSlika;
    
    @FXML
    private Button btnUclani;
    
    @FXML
    private TextField txtTelefon;

    @FXML
    private ListView<String> lvTelefoni;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ToggleGroup group = new ToggleGroup();
		rbMusko.setToggleGroup(group);
		rbZensko.setToggleGroup(group);
		
		dpDatumRodjenja.setConverter(new StringConverter<LocalDate>()
		{
		    private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
		
		lvTelefoni.setItems(listaTelefona);
		
		btnUclani.disableProperty().bind(txtIme.textProperty().isEmpty().or(
				txtPrezime.textProperty().isEmpty().or(
						txtJMB.textProperty().isEmpty().or(
								dpDatumRodjenja.valueProperty().isNull().or(
										group.selectedToggleProperty().isNull().or(
												lvTelefoni.itemsProperty().isNull()))))));
	}
	
	public void dodajFotografiju() {
		FileChooser fileChoser = new FileChooser();
		fileChoser.setInitialDirectory(new File(System.getProperty("user.home").toString() + File.separator + "Pictures"));
		File slika = fileChoser.showOpenDialog(primaryStage);
		if(slika != null) {
			ivSlika.setImage(new Image(slika.toURI().toString()));
			slikaOdabrana = true;
			fotografija = slika;
		}
	}
	
	public void obrisiFotografiju() {
		ivSlika.setImage(new Image(getClass().getResourceAsStream("/resources/avatar.png")));
		slikaOdabrana = false;
		fotografija = null;
	}
	
	public void sacuvajTelefon() {
		String noviTelefon = txtTelefon.getText();
		if(noviTelefon.matches("[0-9][0-9][0-9]/[0-9][0-9][0-9]-[0-9][0-9][0-9]")) {
			listaTelefona.add(noviTelefon);
			txtTelefon.clear();
		}
		else {
			Alert alert=new Alert(AlertType.ERROR, "Broj telefona nije u dobrom formatu. Format je XXX/XXX-XXX.", ButtonType.OK);
			alert.setTitle("Greška");
			alert.setHeaderText("Greška prilikom dodavanja");
		}
	}
	
	public void obrisiTelefon() {
//		List<Integer> lista = lvTelefoni.getSelectionModel().getSelectedIndices();
//		List<String> telefoni = new ArrayList<String>();
//		for(int index : lista) {
//			telefoni.add(listaTelefona.get(index));
//		}
//		listaTelefona.removeAll(telefoni);
		int index = lvTelefoni.getSelectionModel().getSelectedIndex();
		listaTelefona.remove(index);
	}
	
	public void izlaz() {
		if(!"".equals(txtIme.getText()) || !"".equals(txtImeRoditelja.getText()) 
				|| !"".equals(txtJMB.getText()) || !"".equals(txtPrezime.getText()) 
				|| !(listaTelefona.size()==0) || slikaOdabrana) {
			Alert alert = new Alert(AlertType.WARNING, "Uneseni podaci će biti izgubljeni. Da li želite da nastavite?", ButtonType.YES, ButtonType.NO);
			alert.setTitle("Upozorenje");
			alert.setHeaderText("");
			alert.showAndWait();
			if(ButtonType.YES.equals(alert.getResult())) {
				primaryStage.close();
				return;
			}
			return;
		}
		primaryStage.close();
	}
	
	private Blob convertImageToBlob() throws IOException {
		if(fotografija == null)
			return null;
		Connection conn;
		try {
			conn = ConnectionPool.getInstance().checkOut();
			Blob blob = conn.createBlob();
			
			blob.setBytes(1, Files.readAllBytes(fotografija.toPath()));
			return blob;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void izvrsiUclanjivanje() {
		String ime = txtIme.getText();
		String prezime = txtPrezime.getText();
		String imeRoditelja = txtImeRoditelja.getText();
		String jmb = txtJMB.getText();
		
		if(!jmb.matches("[0-9]*") || !(jmb.length() == 13)) {
			
			Alert alert=new Alert(AlertType.ERROR, "Jedinstveni matični broj (JMB) nije dobro unesen.", ButtonType.OK);
			alert.setTitle("Greška");
			alert.setHeaderText("Greška prilikom dodavanja");
			alert.showAndWait();
			return;
		}
		
		char pol = rbMusko.isSelected()?'M':'Ž';
		Date datumRodjenja = Date.from(dpDatumRodjenja.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		ArrayList<String> telefoni = new ArrayList<>();
		for(String s: listaTelefona) {
			telefoni.add(s);
		}
		
		OsobaDTO osoba = DAOFactory.getDAOFactory().getOsobaDAO().getByJmb(jmb);
		ClanDTO clan = null;
		if(osoba != null) {
			clan = DAOFactory.getDAOFactory().getClanDAO().getById(osoba.getId());
			if(clan != null) {
//				ako postoji i aktivan je, ne mozemo ga dodati
				if(clan.isAktivan()) {
					Alert alert=new Alert(AlertType.ERROR, "Greška prilikom dodavanja člana. Član već postoji.", ButtonType.OK);
					alert.setTitle("Greška");
					alert.setHeaderText("Greška prilikom dodavanja");
					alert.showAndWait();
					return;
				}
//				ako postoji unos u bazi, a nije aktivan, dodati novo clanstvo u tabeli CLANSTVO od danasnjeg datuma
//				i azurirati fleg aktivan
				DAOFactory.getDAOFactory().getClanDAO().setAktivan(true, clan.getId());
				DAOFactory.getDAOFactory().getClanstvoDAO().insert(clan.getId());
				
				Alert alert=new Alert(AlertType.INFORMATION, "Bivši član je ponovo aktivan. Neke informacije je možda potrebno izmjeniti.", ButtonType.OK);
				alert.setTitle("Informacija");
				alert.setHeaderText("Dodavanje člana");
				alert.showAndWait();
				primaryStage.close();
				return;
			}
			
//			Ako postoji u osobi a ne postoji u clanu, dodati novi zapis u CLAN, kao i u CLANSTVO
//			Setovati aktivan na TRUE, registrovan na FALSE
			clan = new ClanDTO(true, false);
			clan.setId(osoba.getId());
//			MOZDA TREBA OBRISATI
			retClan = clan;
//			MOZDA TREBA OBRISATI
			DAOFactory.getDAOFactory().getClanDAO().insert(clan);
			DAOFactory.getDAOFactory().getClanstvoDAO().insert(clan.getId());
			Alert alert=new Alert(AlertType.INFORMATION, "Novi član. Neke informacije je možda potrebno izmjeniti.", ButtonType.OK);
			alert.setTitle("Informacija");
			alert.setHeaderText("Dodavanje člana");
			alert.showAndWait();
			primaryStage.close();
			return;
		}
		
//		Ako ne postoji u osobi, dodati novi zapis u OSOBA, CLAN i CLANSTVO
//		Setovati aktivan na TRUE, registrovan na FALSE
//		dodati zapise u TELEFON
//		RASPITATI SE DA LI DA SE CUVA AVATAR U BAZI ILI NA SISTEMU
		try {
			clan = new ClanDTO(0, ime, prezime, imeRoditelja, jmb, pol, datumRodjenja, convertImageToBlob(), telefoni, true, false);
			DAOFactory.getDAOFactory().getClanDAO().insertAll(clan);
			DAOFactory.getDAOFactory().getClanstvoDAO().insert(clan.getId());
			retClan = clan;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		Alert alert=new Alert(AlertType.INFORMATION, "Novi član uspiješno dodat.", ButtonType.OK);
		alert.setTitle("Informacija");
		alert.setHeaderText("Dodavanje člana");
		alert.showAndWait();
		primaryStage.close();
	}
	
	public ClanDTO getClan() {
		return retClan;
	}
	
	private boolean slikaOdabrana = false;
	private File fotografija;
	private ObservableList<String> listaTelefona = FXCollections.observableArrayList();
	private ClanDTO retClan = null;
}

