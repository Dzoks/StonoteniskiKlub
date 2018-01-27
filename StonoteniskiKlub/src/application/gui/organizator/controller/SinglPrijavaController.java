package application.gui.organizator.controller;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.UcesnikPrijavaDTO;
import application.util.AlertDisplay;
import javafx.fxml.FXML;
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
		primaryStage.setTitle("Stonoteniski klub");
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
			AlertDisplay.showError("Prijava", "Nije moguće prijaviti učesnika sa imenom ili prezimenom dužim od 45 karaktera.");
		}
		else{
			if(txtJmbg.getText().length()!=13 || !txtJmbg.getText().matches("[0-9]*")){
				AlertDisplay.showError("Prijava","Potrebno je unijeti JMB dužine od 13 brojeva.");
			}
			else{
				if(dpDatumRodjenja.getValue().getYear()>(LocalDate.now().getYear()-5)){
					AlertDisplay.showError("Prijava","Nije moguće prijaviti učesnika mlađeg od 5 godina. "
							+ "Potrebno je da je učesnik prvog dana ove godine imao punih 5 godina.");
				}
				else{
					if(DAOFactory.getDAOFactory().getUcesnikPrijavaDAO().doesExist(txtJmbg.getText(), idTurnira, idKategorije)){
						AlertDisplay.showError("Prijava", "Ovaj učesnik je već prijavljen na ovaj turnir!");
					}
					else{
						if(DAOFactory.getDAOFactory().getOsobaDAO().doesExist(txtJmbg.getText(), idTurnira, idKategorije)){
							if(DAOFactory.getDAOFactory().getTimDAO().insertSingle(DAOFactory.getDAOFactory().getUcesnikPrijavaDAO().addNew(idTurnira,
									idKategorije, DAOFactory.getDAOFactory().getOsobaDAO().getByJmb(txtJmbg.getText()).getId(), Date.valueOf(LocalDate.now())))){
								primaryStage.close();
							}
							else{
								AlertDisplay.showError("Prijava", "Uneseni podaci nisu odgovarajući, ili nije moguće prijaviti učesnika.");
							}
						}
						else{
							if(DAOFactory.getDAOFactory().getTimDAO().insertSingle(DAOFactory.getDAOFactory()
									.getUcesnikPrijavaDAO().insert(txtJmbg.getText(), txtIme.getText(), txtPrezime.getText(),
									idKategorije%2==1?"M".charAt(0):"Ž".charAt(0), Date.valueOf(dpDatumRodjenja.getValue()), 
											idTurnira, idKategorije, Date.valueOf(LocalDate.now())))){
								primaryStage.close();
							}
							else{
								AlertDisplay.showError("Prijava", "Uneseni podaci nisu odgovarajući, ili nije moguće prijaviti učesnika.");
							}
						}
					}
				}
			}
		}
	}
	
	public void sacuvajIzmjene(){
		if(txtIme.getText().length()>45 || txtPrezime.getText().length()>45){
			AlertDisplay.showError("Prijava", "Nije moguće prijaviti učesnika sa imenom ili prezimenom dužim od 45 karaktera.");
		}
		else{
			if(txtJmbg.getText().length()!=13 || !txtJmbg.getText().matches("[0-9]*")){
				AlertDisplay.showError("Prijava","Potrebno je unijeti JMB dužine od 13 brojeva.");
			}
			else{
				if(dpDatumRodjenja.getValue().getYear()>(LocalDate.now().getYear()-5)){
					AlertDisplay.showError("Prijava","Nije moguće prijaviti učesnika mlađeg od 5 godina. "
							+ "Potrebno je da je učesnik prvog dana ove godine imao punih 5 godina.");
				}
				else{
					if(DAOFactory.getDAOFactory().getUcesnikPrijavaDAO().izmjeniUcesnika(idPrijave, txtIme.getText(),
							txtPrezime.getText(),Date.valueOf(dpDatumRodjenja.getValue()))){
						primaryStage.close();
					}
					else{
						AlertDisplay.showError("Prijava", "Uneseni podaci nisu odgovarajući, ili nije moguće prijaviti učesnika.");
					}
				}
			}
		}
	}
	
}
