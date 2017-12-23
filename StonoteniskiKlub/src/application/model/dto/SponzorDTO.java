package application.model.dto;

import java.util.Iterator;
import java.util.List;

public class SponzorDTO {
	private Integer id;
	private String naziv;
	private String adresa;
	private String email;
	private List<UgovorDTO> ugovori;

	public SponzorDTO() {
	}

	public SponzorDTO(Integer id, String naziv, String adresa, String email, List<UgovorDTO> ugovori) {
		this.id = id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.email = email;
		this.ugovori = ugovori;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
		return naziv + ", " + adresa + ", " + email;
	}
}
