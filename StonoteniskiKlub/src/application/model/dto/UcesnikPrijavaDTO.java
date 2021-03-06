package application.model.dto;

import java.sql.Date;

import application.gui.organizator.controller.TurniriController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class UcesnikPrijavaDTO extends OsobaDTO{
	private IntegerProperty idPrijave;
	private IntegerProperty idTurnira;
	private IntegerProperty idKategorije;
	private Date datum;
	private String konvertovanDatumRodjenja;
	
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
		this.idKategorije = idKategorije == null ? null :new SimpleIntegerProperty(idKategorije); //ostaviti ovako, inace Heleni puca
		this.datum = datum;
		if(datumRodjenja!=null) //Helena morala dodati, jer ne vuce iz baze datumRodjenja, pa bude nullptr
		this.konvertovanDatumRodjenja= TurniriController.konvertujIzSQLDate(datumRodjenja.toString()); //ovo ostaviti, Gaji odgovara ovako
	}
	
	public UcesnikPrijavaDTO(Integer idOsobe, String ime, String prezime, String jmb, Character pol,
			Date datumRodjenja){
		super(idOsobe,ime,prezime,jmb,pol,datumRodjenja);
		this.konvertovanDatumRodjenja=TurniriController.konvertujIzSQLDate(datumRodjenja.toString());
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

	public String getKonvertovanDatumRodjenja() {
		return konvertovanDatumRodjenja;
	}

	public void setKonvertovanDatumRodjenja(String konvertovanDatumRodjenja) {
		this.konvertovanDatumRodjenja = konvertovanDatumRodjenja;
	}
	
}
