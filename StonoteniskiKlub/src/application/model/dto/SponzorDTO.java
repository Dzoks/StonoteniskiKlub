package application.model.dto;

import java.util.Iterator;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SponzorDTO {
	private IntegerProperty id;
	private StringProperty naziv;
	private StringProperty adresa;
	private StringProperty email;
	private List<UgovorDTO> ugovori;

	public SponzorDTO() {
	}

	public SponzorDTO(Integer id, String naziv, String adresa, String email, List<UgovorDTO> ugovori) {
		this.id = id == null ? null : new SimpleIntegerProperty(id);
		this.naziv = naziv == null ? null : new SimpleStringProperty(naziv);
		this.adresa = adresa == null ? null : new SimpleStringProperty(adresa);
		this.email = email == null ? null : new SimpleStringProperty(email);
		this.ugovori = ugovori;
	}

	// Property metode
	public IntegerProperty idProperty() {
		return id;
	}

	public StringProperty nazivProperty() {
		return naziv;
	}

	public StringProperty adresaProperty() {
		return adresa;
	}

	public StringProperty emailProperty() {
		return email;
	}
	public StringProperty aktivanProperty(){
		return new SimpleStringProperty(this.daLiJeAktivan() ? "Da" : "Ne");
	}
	public StringProperty telefonProperty(){
		return new SimpleStringProperty("065/111-222");
	}
	
	// Obicni getteri
	public Integer getId() {
		return id.get();
	}

	public String getNaziv() {
		return naziv.get();
	}

	public String getAdresa() {
		return adresa.get();
	}

	public String getEmail() {
		return email.get();
	}

	// Obicni setteri
	public void setId(Integer id) {
		this.id.set(id);
	}

	public void setNaziv(String naziv) {
		this.naziv.set(naziv);
	}

	public void setAdresa(String adresa) {
		this.adresa.set(adresa);
	}

	public void setEmail(String email) {
		this.email.set(email);
	}

	public List<UgovorDTO> getUgovori() {
		return ugovori;
	}

	public void setUgovori(List<UgovorDTO> ugovori) {
		this.ugovori = ugovori;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SponzorDTO other = (SponzorDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public boolean daLiJeAktivan() {
		for (Iterator<UgovorDTO> it = ugovori.iterator(); it.hasNext();) {
			if (it.next().daLiJeAktivan()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return naziv.get() + ", " + adresa.get() + ", " + email.get();
	}
}
