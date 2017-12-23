package application.model.dto;

import java.math.BigDecimal;

public class DonacijaDTO {
	private SponzorDTO sponzor;
	private UgovorDTO ugovor;
	private Integer redniBroj;
	private String opis;
	private BigDecimal kolicina;
	private BigDecimal novcaniIznos;
	private Boolean novcanaDonacija;
	private Boolean obradjeno;
	private OpremaTipDTO tipOpreme;

	public DonacijaDTO() {
	}

	public DonacijaDTO(SponzorDTO sponzor, UgovorDTO ugovor, Integer redniBroj, String opis, BigDecimal kolicina,
			BigDecimal novcaniIznos, Boolean novcanaDonacija, Boolean obradjeno, OpremaTipDTO tipOpreme) {
		this.sponzor = sponzor;
		this.ugovor = ugovor;
		this.redniBroj = redniBroj;
		this.opis = opis;
		this.kolicina = kolicina;
		this.novcaniIznos = novcaniIznos;
		this.novcanaDonacija = novcanaDonacija;
		this.obradjeno = obradjeno;
		this.tipOpreme = tipOpreme;
	}

	public SponzorDTO getSponzor() {
		return sponzor;
	}

	public void setSponzor(SponzorDTO sponzor) {
		this.sponzor = sponzor;
	}

	public UgovorDTO getUgovor() {
		return ugovor;
	}

	public void setUgovor(UgovorDTO ugovor) {
		this.ugovor = ugovor;
	}

	public Integer getRedniBroj() {
		return redniBroj;
	}

	public void setRedniBroj(Integer redniBroj) {
		this.redniBroj = redniBroj;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public BigDecimal getKolicina() {
		return kolicina;
	}

	public void setKolicina(BigDecimal kolicina) {
		this.kolicina = kolicina;
	}

	public BigDecimal getNovcaniIznos() {
		return novcaniIznos;
	}

	public void setNovcaniIznos(BigDecimal novcaniIznos) {
		this.novcaniIznos = novcaniIznos;
	}

	public Boolean getNovcanaDonacija() {
		return novcanaDonacija;
	}

	public void setNovcanaDonacija(Boolean novcanaDonacija) {
		this.novcanaDonacija = novcanaDonacija;
	}

	public Boolean getObradjeno() {
		return obradjeno;
	}

	public void setObradjeno(Boolean obradjeno) {
		this.obradjeno = obradjeno;
	}

	public OpremaTipDTO getTipOpreme() {
		return tipOpreme;
	}

	public void setTipOpreme(OpremaTipDTO tipOpreme) {
		this.tipOpreme = tipOpreme;
	}
	
	@Override
	public String toString() {
		String result = sponzor.toString() + " - " + ugovor.toString() + " - ";
		result += opis + "; " + (novcanaDonacija ? "Novcani iznos: " + novcaniIznos : "Tip opreme: " + tipOpreme + ", kolicina: " + kolicina);
		return result;
	}
}
