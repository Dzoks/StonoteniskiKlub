package application.model.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DistributerOpremeDTO {

	private IntegerProperty id;
	private StringProperty naziv;
	private StringProperty telefon;
	private StringProperty adresa;
	private StringProperty mail;
	
	public DistributerOpremeDTO() {
		super();
	}
	
	public DistributerOpremeDTO(Integer id, String naziv, String telefon, String adresa, String mail) {
		super();
		this.id = id==null ? null : new SimpleIntegerProperty(id);
		this.naziv = naziv==null ? null : new SimpleStringProperty(naziv);
		this.telefon = telefon==null ? null : new SimpleStringProperty(telefon);
		this.adresa = adresa==null ? null : new SimpleStringProperty(adresa);
		this.mail = mail==null ? null : new SimpleStringProperty(mail);
	}

	public Integer getId() {
		return id.get();
	}
	
	public void setId(Integer id) {
		this.id.set(id);
	}
	
	public String getNaziv() {
		return naziv.get();
	}
	
	public void setNaziv(String naziv) {
		this.naziv.set(naziv);;
	}
	
	public String getTelefon() {
		return telefon.get();
	}
	
	public void setTelefon(String telefon) {
		this.telefon.set(telefon);;
	}
	
	public String getAdresa() {
		return adresa.get();
	}
	
	public void setAdresa(String adresa) {
		this.adresa.set(adresa);;
	}
	
	public String getMail() {
		return mail.get();
	}
	
	public void setMail(String mail) {
		this.mail.set(mail);
	}

	@Override
	public String toString() {
		return naziv.get();
	}
}
