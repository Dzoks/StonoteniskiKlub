package application.model.dto;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import application.gui.organizator.controller.TurniriController;
import application.model.dao.TurnirDAO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UcesnikPrijavaDTO extends OsobaDTO{
	private IntegerProperty idPrijave;
	private IntegerProperty idTurnira;
	private IntegerProperty idKategorije;
	private Date datum;
	private StringProperty konvertovanDatumRodjenja;
	
	public UcesnikPrijavaDTO() {
		super();
	}

	public UcesnikPrijavaDTO(Integer idPrijave, Integer idTurnira, Integer idKategorije, Date datum) {
		super();
		this.idPrijave = new SimpleIntegerProperty(idPrijave);
		this.idTurnira = new SimpleIntegerProperty(idTurnira);
		this.idKategorije = new SimpleIntegerProperty(idKategorije);
		this.datum=datum;
	}
	
	public UcesnikPrijavaDTO(Integer id, String ime, String prezime, String jmb, Character pol,
			java.util.Date datumRodjenja,Integer idPrijave, Integer idTurnira, Integer idKategorije, Date datum) {
		super(id, ime, prezime, jmb, pol, datumRodjenja);
		this.idPrijave = new SimpleIntegerProperty(idPrijave);
		this.idTurnira = new SimpleIntegerProperty(idTurnira);
		this.idKategorije = new SimpleIntegerProperty(idKategorije);
		this.datum = datum;
		this.konvertovanDatumRodjenja=new SimpleStringProperty(TurniriController.konvertujIzSQLDate(datumRodjenja.toString()));
	}
	
	public UcesnikPrijavaDTO(Integer idOsobe, String ime, String prezime, String jmb, Character pol,
			Date datumRodjenja){
		super(idOsobe,ime,prezime,jmb,pol,datumRodjenja);
		this.konvertovanDatumRodjenja=new SimpleStringProperty(TurniriController.konvertujIzSQLDate(datumRodjenja.toString()));
	}

	public final IntegerProperty idTurniraProperty() {
		return this.idTurnira;
	}
	
	public final int getIdTurnira() {
		return this.idTurniraProperty().get();
	}
	
	public final void setIdTurnira(final int idTurnira) {
		this.idTurniraProperty().set(idTurnira);
	}
	
	public final IntegerProperty idKategorijeProperty() {
		return this.idKategorije;
	}
	
	public final int getIdKategorije() {
		return this.idKategorijeProperty().get();
	}
	
	public final void setIdKategorije(final int idKategorije) {
		this.idKategorijeProperty().set(idKategorije);
	}

	public final IntegerProperty idPrijaveProperty() {
		return this.idPrijave;
	}
	
	public final int getIdPrijave() {
		return this.idPrijaveProperty().get();
	}
	
	public final void setIdPrijave(final int idPrijave) {
		this.idPrijaveProperty().set(idPrijave);
	}

	public final StringProperty konvertovanDatumRodjenjaProperty() {
		return this.konvertovanDatumRodjenja;
	}
	
	@Override
	public String toString() { //dodala Helena
		// TODO Auto-generated method stub
		TurnirDTO turnir = TurnirDAO.getById(this.getIdTurnira());
		return super.toString() + " -"+turnir;
	}
}
