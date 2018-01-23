package application.gui.organizator.controller;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.util.AlertDisplay;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DublPrijavaController extends BaseController{
	@FXML
	private Label lblKategorija;
	@FXML
	private Button btnSacuvaj;
	@FXML
	private TextField txtIme1;
	@FXML
	private TextField txtPrezime1;
	@FXML
	private TextField txtJmbg1;
	@FXML
	private DatePicker dpDatumRodjenja1;
	@FXML
	private TextField txtIme2;
	@FXML
	private TextField txtPrezime2;
	@FXML
	private TextField txtJmbg2;
	@FXML
	private DatePicker dpDatumRodjenja2;

	private Integer idTurnira;
	private Integer idKategorije;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void inicijalizuj(Integer idTurnira,Integer idKategorije){
		primaryStage.setTitle("Stonoteniski klub");
		this.idTurnira=idTurnira;
		this.idKategorije=idKategorije;
		lblKategorija.setText(DAOFactory.getDAOFactory().getKategorijaTurniraDAO().getById(idKategorije).getKategorija());
		btnSacuvaj.disableProperty().bind(txtIme1.textProperty().isEmpty()
				.or(txtPrezime1.textProperty().isEmpty()
						.or(txtJmbg1.textProperty().isEmpty()
								.or(dpDatumRodjenja1.valueProperty().isNull()
										.or(txtIme2.textProperty().isEmpty()
												.or(txtPrezime2.textProperty().isEmpty()
														.or(txtJmbg2.textProperty().isEmpty()
																.or(dpDatumRodjenja2.valueProperty().isNull()))))))));
	}
	
	public void sacuvaj(){
		if(txtIme1.getText().length()>45 || txtPrezime1.getText().length()>45
				|| txtIme2.getText().length()>45 || txtPrezime2.getText().length()>45){
			AlertDisplay.showError("Prijava", "Nije moguće prijaviti učesnike sa imenom ili prezimenom dužim od 45 karaktera.");
		}
		else{
			if(txtJmbg1.getText().length()!=13 || txtJmbg2.getText().length()!=13
					|| !txtJmbg1.getText().matches("[0-9]*") || !txtJmbg2.getText().matches("[0-9]*")){
				AlertDisplay.showError("Prijava", "Potrebno je da obe unesene vrijednosti za JMBG budu dužine od 13 brojeva.");
			}
			else{
				if(dpDatumRodjenja1.getValue().isAfter(LocalDate.now()) || dpDatumRodjenja2.getValue().isAfter(LocalDate.now())){
					AlertDisplay.showError("Prijava", "Nije moguće prijaviti učesnika čiji je datum rođenja poslije današnjeg.");
				}
				else{
					if(DAOFactory.getDAOFactory().getOsobaDAO().doesExist(txtJmbg1.getText(), idTurnira, idKategorije) && 
							DAOFactory.getDAOFactory().getOsobaDAO().doesExist(txtJmbg2.getText(), idTurnira, idKategorije)){
						if(DAOFactory.getDAOFactory().getTimDAO().insertDouble(DAOFactory.getDAOFactory().getUcesnikPrijavaDAO().addNew(idTurnira,idKategorije,
								DAOFactory.getDAOFactory().getOsobaDAO().getByJmb(txtJmbg1.getText()).getId(), Date.valueOf(LocalDate.now())),
								DAOFactory.getDAOFactory().getUcesnikPrijavaDAO().addNew(idTurnira,idKategorije, 
										DAOFactory.getDAOFactory().getOsobaDAO().getByJmb(txtJmbg2.getText()).getId(), Date.valueOf(LocalDate.now())))){
							primaryStage.close();
						}
						else{
							AlertDisplay.showError("Prijava", "Uneseni podaci nisu odgovarajući, ili nije moguće prijaviti učesnika!");

							
						}
					}
					else if(DAOFactory.getDAOFactory().getOsobaDAO().doesExist(txtJmbg1.getText(), idTurnira, idKategorije)){
						if(DAOFactory.getDAOFactory().getTimDAO().insertDouble(DAOFactory.getDAOFactory().getUcesnikPrijavaDAO().addNew(idTurnira,idKategorije,
								DAOFactory.getDAOFactory().getOsobaDAO().getByJmb(txtJmbg1.getText()).getId(), Date.valueOf(LocalDate.now())),
								DAOFactory.getDAOFactory().getUcesnikPrijavaDAO().insert(txtJmbg2.getText(), txtIme2.getText(), txtPrezime2.getText(),
										idKategorije%2==1?"M".charAt(0):"Ž".charAt(0), Date.valueOf(dpDatumRodjenja2.getValue()),
												idTurnira, idKategorije, Date.valueOf(LocalDate.now())))){
							primaryStage.close();
						}
						else{
							AlertDisplay.showError("Prijava", "Uneseni podaci nisu odgovarajući, ili nije moguće prijaviti učesnika!");

						}
					}
					else if(DAOFactory.getDAOFactory().getOsobaDAO().doesExist(txtJmbg2.getText(), idTurnira, idKategorije)){
						if(DAOFactory.getDAOFactory().getTimDAO().insertDouble(DAOFactory.getDAOFactory().getUcesnikPrijavaDAO().insert(txtJmbg1.getText(), txtIme1.getText(), txtPrezime1.getText(),
								idKategorije%2==1?"M".charAt(0):"Ž".charAt(0),Date.valueOf(dpDatumRodjenja1.getValue()), 
										idTurnira, idKategorije, Date.valueOf(LocalDate.now())),
								DAOFactory.getDAOFactory().getUcesnikPrijavaDAO().addNew(idTurnira,idKategorije, 
										DAOFactory.getDAOFactory().getOsobaDAO().getByJmb(txtJmbg2.getText()).getId(), Date.valueOf(LocalDate.now())))){
							primaryStage.close();
						}
						else{
							AlertDisplay.showError("Prijava", "Uneseni podaci nisu odgovarajući, ili nije moguće prijaviti učesnika!");

						}
					}
					else{
						if(DAOFactory.getDAOFactory().getTimDAO().insertDouble(DAOFactory.getDAOFactory().getUcesnikPrijavaDAO().insert(txtJmbg1.getText(), txtIme1.getText(), txtPrezime1.getText(),
								idKategorije%2==1?"M".charAt(0):"Ž".charAt(0),Date.valueOf(dpDatumRodjenja1.getValue()), 
										idTurnira, idKategorije, Date.valueOf(LocalDate.now())), 
								DAOFactory.getDAOFactory().getUcesnikPrijavaDAO().insert(txtJmbg2.getText(), txtIme2.getText(), txtPrezime2.getText(),
										idKategorije%2==1?"M".charAt(0):"Ž".charAt(0), Date.valueOf(dpDatumRodjenja2.getValue()),
												idTurnira, idKategorije, Date.valueOf(LocalDate.now())))){
							primaryStage.close();
						}
						else{
							AlertDisplay.showError("Prijava", "Uneseni podaci nisu odgovarajući, ili nije moguće prijaviti učesnika!");

						}
					}
				}
			}
		}
	}
}
