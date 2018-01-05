package application.model.dto;

import java.util.Iterator;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class SponzorDTO {
	private IntegerProperty id;
	private StringProperty naziv;
	private StringProperty adresa;
	private StringProperty email;
	private ObservableList<UgovorDTO> ugovori;

	public SponzorDTO() {
	}

	public SponzorDTO(Integer id, String naziv, String adresa, String email, ObservableList<UgovorDTO> ugovori) {
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

	public ObservableList<UgovorDTO> getUgovori() {
		return ugovori;
	}

	public void setUgovori(ObservableList<UgovorDTO> ugovori) {
		this.ugovori = ugovori;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getId() == null) ? 0 : getId().hashCode());
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
		} else if (!this.getId().equals(other.getId()))
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
		return naziv.get() + ", " + adresa.get();
	}
	
	public Integer getMaxUgovorId(){
		Integer max = 0;
		for(UgovorDTO ug : ugovori){
			if(ug.getRedniBroj().compareTo(max) > 0){
				max = ug.getRedniBroj();
			}
		}
		return max;
	}
}
