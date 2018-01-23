package application.gui.organizator.controller;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.UcesnikPrijavaDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SinglPrijavaController extends BaseController{
	@FXML
	private Label lblKategorija;
	@FXML
	private Button btnSacuvaj;
	@FXML
	private Button btnSacuvajIzmjene;
	@FXML
	private TextField txtIme;
	@FXML
	private TextField txtPrezime;
	@FXML
	private TextField txtJmbg;
	@FXML
	private DatePicker dpDatumRodjenja;
	
	private Integer idTurnira;
	private Integer idKategorije;
	private Integer idPrijave;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void inicijalizuj(Integer idTurnira,Integer idKategorije){
		primaryStage.setTitle("Stonoteniski klub");
		this.idTurnira=idTurnira;
		this.idKategorije=idKategorije;
		btnSacuvajIzmjene.setVisible(false);
		lblKategorija.setText(DAOFactory.getDAOFactory().getKategorijaTurniraDAO().getById(idKategorije).getKategorija());
		btnSacuvaj.disableProperty().bind(txtIme.textProperty().isEmpty()
				.or(txtPrezime.textProperty().isEmpty()
						.or(txtJmbg.textProperty().isEmpty()
								.or(dpDatumRodjenja.valueProperty().isNull()))));
	}
	
	public void inicijalizujIzmjene(Integer idTurnira,Integer idKategorije,UcesnikPrijavaDTO ucesnik){
		primaryStage.setTitle("Izmjena prijave");
		this.idTurnira=idTurnira;
		this.idKategorije=idKategorije;
		this.idPrijave=ucesnik.getIdPrijave();
		btnSacuvaj.setVisible(false);
		lblKategorija.setText(DAOFactory.getDAOFactory().getKategorijaTurniraDAO().getById(idKategorije).getKategorija());
		btnSacuvajIzmjene.disableProperty().bind(txtIme.textProperty().isEmpty()
				.or(txtPrezime.textProperty().isEmpty()
						.or(txtJmbg.textProperty().isEmpty()
								.or(dpDatumRodjenja.valueProperty().isNull()))));
		
		txtIme.setText(ucesnik.getIme());
		txtPrezime.setText(ucesnik.getPrezime());
		txtJmbg.setText(ucesnik.getJmb());
		dpDatumRodjenja.setValue(LocalDate.parse(ucesnik.getDatumRodjenja().toString()));
		txtJmbg.setEditable(false);
	}

	public void sacuvaj(){
		if(txtIme.getText().length()>45 || txtPrezime.getText().length()>45){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Greška");
			alert.setHeaderText("Nepravilan unos!");
			alert.setContentText("Nije moguće prijaviti učesnika sa imenom ili prezimenom dužim od 45 karaktera.");
			alert.show();
		}
		else{
			if(txtJmbg.getText().length()!=13 || !txtJmbg.getText().matches("[0-9]*")){
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Greška");
				alert.setHeaderText("Pogrešan JMBG!");
				alert.setContentText("Potrebno je unijeti JMBG dužine od 13 brojeva.");
				alert.show();
			}
			else{
				if(dpDatumRodjenja.getValue().isAfter(LocalDate.now())){
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Greška");
					alert.setHeaderText("Nepravilan unos!");
					alert.setContentText("Nije moguće prijaviti učesnika čiji je datum rođenja poslije današnjeg.");
					alert.show();
				}
				else{
					if(DAOFactory.getDAOFactory().getOsobaDAO().doesExist(txtJmbg.getText(), idTurnira, idKategorije)){
						if(DAOFactory.getDAOFactory().getTimDAO().insertSingle(DAOFactory.getDAOFactory().getUcesnikPrijavaDAO().addNew(idTurnira,
								idKategorije, DAOFactory.getDAOFactory().getOsobaDAO().getByJmb(txtJmbg.getText()).getId(), Date.valueOf(LocalDate.now())))){
							primaryStage.close();
						}
						else{
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Greška");
							alert.setHeaderText("Nešto nije u redu!");
							alert.setContentText("Uneseni podaci nisu odgovarajući, ili nije moguće prijaviti učesnika!");
							alert.show();
						}
					}
					else{
						if(DAOFactory.getDAOFactory().getTimDAO().insertSingle(DAOFactory.getDAOFactory().getUcesnikPrijavaDAO().insert(txtJmbg.getText(), txtIme.getText(), txtPrezime.getText(),
								idKategorije%2==1?"M".charAt(0):"Ž".charAt(0), Date.valueOf(dpDatumRodjenja.getValue()), 
										idTurnira, idKategorije, Date.valueOf(LocalDate.now())))){
							primaryStage.close();
						}
						else{
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Greška");
							alert.setHeaderText("Nešto nije u redu!");
							alert.setContentText("Uneseni podaci nisu odgovarajući, ili nije moguće prijaviti učesnika!");
							alert.show();
						}
					}
				}
			}
		}
	}
	
	public void sacuvajIzmjene(){
		if(txtIme.getText().length()>45 || txtPrezime.getText().length()>45){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Greška");
			alert.setHeaderText("Nepravilan unos!");
			alert.setContentText("Nije moguće prijaviti učesnike sa imenom ili prezimenom dužim od 45 karaktera.");
			alert.show();
		}
		else{
			if(txtJmbg.getText().length()!=13 || !txtJmbg.getText().matches("[0-9]*")){
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Greška");
				alert.setHeaderText("Pogrešan JMBG!");
				alert.setContentText("Potrebno je unijeti JMBG dužine od 13 brojeva.");
				alert.show();
			}
			else{
				if(dpDatumRodjenja.getValue().isAfter(LocalDate.now())){
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Greška");
					alert.setHeaderText("Nepravilan unos!");
					alert.setContentText("Nije moguće prijaviti učesnika čiji je datum rođenja poslije današnjeg.");
					alert.show();
				}
				else{
					if(DAOFactory.getDAOFactory().getUcesnikPrijavaDAO().izmjeniUcesnika(idPrijave, txtIme.getText(),
							txtPrezime.getText(),Date.valueOf(dpDatumRodjenja.getValue()))){
						primaryStage.close();
					}
					else{
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Greška");
						alert.setHeaderText("Nešto nije u redu!");
						alert.setContentText("Uneseni podaci nisu odgovarajući, ili nije moguće prijaviti učesnika!");
						alert.show();
					}
				}
			}
		}
	}
	
}
