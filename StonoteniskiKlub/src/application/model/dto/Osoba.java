package application.model.dto;

import java.sql.Blob;
import java.util.Date;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Osoba {
	protected IntegerProperty id;
	protected StringProperty ime;
	protected StringProperty prezime;
	protected StringProperty imeRoditelja;
	protected StringProperty jmb;
	protected Character pol;
	protected Date datumRodjenja;
	protected Blob slika;
	protected List<String> telefoni;
	
	
	public Osoba() {
		super();
		this.id = new SimpleIntegerProperty();
		this.ime = new SimpleStringProperty();
		this.prezime = new SimpleStringProperty();
		this.imeRoditelja = new SimpleStringProperty();
		this.jmb = new SimpleStringProperty();
	}
	

	public List<String> getTelefoni() {
		return telefoni;
	}


	public void setTelefoni(List<String> telefoni) {
		this.telefoni = telefoni;
	}


	public Osoba(int id, String ime, String prezime, String imeRoditelja,
			String jmb, Character pol, Date datumRodjenja, Blob slika, List<String> telefoni) {
		super();
		this.id = new SimpleIntegerProperty(id);
		this.ime = new SimpleStringProperty(ime);
		this.prezime = new SimpleStringProperty(prezime);
		this.imeRoditelja = new SimpleStringProperty(imeRoditelja);
		this.jmb = new SimpleStringProperty(jmb);
		this.pol = pol;
		this.datumRodjenja = datumRodjenja;
		this.slika = slika;
		this.telefoni = telefoni;
	}


	public final IntegerProperty idProperty() {
		return this.id;
	}
	

	public final int getId() {
		return this.idProperty().get();
	}
	

	public final void setId(final int id) {
		this.idProperty().set(id);
	}
	

	public final StringProperty imeProperty() {
		return this.ime;
	}
	

	public final String getIme() {
		return this.imeProperty().get();
	}
	

	public final void setIme(final String ime) {
		this.imeProperty().set(ime);
	}
	

	public final StringProperty prezimeProperty() {
		return this.prezime;
	}
	

	public final String getPrezime() {
		return this.prezimeProperty().get();
	}
	

	public final void setPrezime(final String prezime) {
		this.prezimeProperty().set(prezime);
	}
	

	public final StringProperty imeRoditeljaProperty() {
		return this.imeRoditelja;
	}
	

	public final String getImeRoditelja() {
		return this.imeRoditeljaProperty().get();
	}
	

	public final void setImeRoditelja(final String imeRoditelja) {
		this.imeRoditeljaProperty().set(imeRoditelja);
	}
	

	public final StringProperty jmbProperty() {
		return this.jmb;
	}
	

	public final String getJmb() {
		return this.jmbProperty().get();
	}
	

	public final void setJmb(final String jmb) {
		this.jmbProperty().set(jmb);
	}
	
	public Character getPol() {
		return pol;
	}

	public void setPol(Character pol) {
		this.pol = pol;
	}

	public Date getDatumRodjenja() {
		return datumRodjenja;
	}

	public void setDatumRodjenja(Date datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}

	public Blob getSlika() {
		return slika;
	}

	public void setSlika(Blob slika) {
		this.slika = slika;
	}
	
	
	
}
