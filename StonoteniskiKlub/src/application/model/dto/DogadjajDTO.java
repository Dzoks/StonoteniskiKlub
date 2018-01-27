package application.model.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DogadjajDTO {

	public DogadjajDTO() {
	}

	public DogadjajDTO(Integer id, LocalDateTime pocetak, LocalDateTime kraj, String opis, DogadjajTipDTO tipDogadjaja,
			KorisnickiNalogDTO korisnickiNalog) {
		super();
		this.id = id;
		this.pocetak = pocetak;
		this.kraj = kraj;
		this.opis = opis;
		this.tipDogadjaja = tipDogadjaja;
		this.korisnickiNalog = korisnickiNalog;
	}

	public LocalDateTime getPocetak() {
		return pocetak;
	}

	public void setPocetak(LocalDateTime pocetak) {
		this.pocetak = pocetak;
	}

	public LocalDateTime getKraj() {
		return kraj;
	}

	public void setKraj(LocalDateTime kraj) {
		this.kraj = kraj;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public DogadjajTipDTO getTipDogadjaja() {
		return tipDogadjaja;
	}

	public void setTipDogadjaja(DogadjajTipDTO tipDogadjaja) {
		this.tipDogadjaja = tipDogadjaja;
	}

	public KorisnickiNalogDTO getKorisnickiNalog() {
		return korisnickiNalog;
	}

	public void setKorisnickiNalog(KorisnickiNalogDTO korisnickiNalog) {
		this.korisnickiNalog = korisnickiNalog;
	}

	@Override
	public String toString() {
		return "[" + pocetak.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " - "
				+ kraj.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "]";
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
		DogadjajDTO other = (DogadjajDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	private Integer id;
	private LocalDateTime pocetak;
	private LocalDateTime kraj;
	private String opis;
	private DogadjajTipDTO tipDogadjaja;
	private KorisnickiNalogDTO korisnickiNalog;
}
