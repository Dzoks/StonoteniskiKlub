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
		return "[" + pocetak.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " "
				+ kraj.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "] - " + tipDogadjaja + " - " + opis
				+ "; KREIRAO: " + korisnickiNalog;
	}

	private Integer id;
	private LocalDateTime pocetak;
	private LocalDateTime kraj;
	private String opis;
	private DogadjajTipDTO tipDogadjaja;
	private KorisnickiNalogDTO korisnickiNalog;
}
