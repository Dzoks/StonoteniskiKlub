package application.model.dto;

public class StavkaSkupstinaDTO {
	
	public StavkaSkupstinaDTO() {
	}
	public StavkaSkupstinaDTO(Integer redniBroj, String naslov, String tekst) {
		this.redniBroj = redniBroj;
		this.naslov = naslov;
		this.tekst = tekst;
	}
		
	public Integer getRedniBroj() {
		return redniBroj;
	}
	public void setRedniBroj(Integer redniBroj) {
		this.redniBroj = redniBroj;
	}
	public String getNaslov() {
		return naslov;
	}
	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}
	public String getTekst() {
		return tekst;
	}
	public void setTekst(String tekst) {
		this.tekst = tekst;
	}

	@Override
	public String toString() {
		return "Stavka " + redniBroj + " - " + naslov;
	}

	private Integer redniBroj;
	private String naslov;
	private String tekst;
}
