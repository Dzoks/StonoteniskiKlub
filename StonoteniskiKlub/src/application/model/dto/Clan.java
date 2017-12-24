package application.model.dto;

import java.sql.Blob;
import java.util.Date;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Clan extends Osoba{
	private BooleanProperty aktivan;
	private BooleanProperty registrovan;
	
	public Clan() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Clan(int id, String ime, String prezime, String imeRoditelja, String jmb, Character pol,
			Date datumRodjenja, Blob slika, List<String> telefoni) {
		super(id, ime, prezime, imeRoditelja, jmb, pol, datumRodjenja, slika, telefoni);
		// TODO Auto-generated constructor stub
	}
	public Clan(boolean aktivan, boolean registrovan) {
		super();
		this.aktivan = new SimpleBooleanProperty(aktivan);
		this.registrovan = new SimpleBooleanProperty(registrovan);
	}
	
	public Clan(int id, String ime, String prezime, String imeRoditelja, String jmb, Character pol,
			Date datumRodjenja, Blob slika, List<String> telefoni, boolean aktivan, boolean registrovan) {
		super(id, ime, prezime, imeRoditelja, jmb, pol, datumRodjenja, slika, telefoni);
		this.aktivan = new SimpleBooleanProperty(aktivan);
		this.registrovan = new SimpleBooleanProperty(registrovan);
	}
	
	public final BooleanProperty aktivanProperty() {
		return this.aktivan;
	}
	
	public final boolean isAktivan() {
		return this.aktivanProperty().get();
	}
	
	public final void setAktivan(final boolean aktivan) {
		this.aktivanProperty().set(aktivan);
	}
	
	public final BooleanProperty registrovanProperty() {
		return this.registrovan;
	}
	
	public final boolean isRegistrovan() {
		return this.registrovanProperty().get();
	}
	
	public final void setRegistrovan(final boolean registrovan) {
		this.registrovanProperty().set(registrovan);
	}
	@Override
	public String toString() {
		return jmb.get() + " - " + ime.get() + " - " + prezime.get();
	}
}
