package application.model.dto;

import java.math.BigDecimal;

public class DonacijaDTO {
	private Integer redniBroj;
	private String opis;
	private BigDecimal kolicina;
	private BigDecimal novcaniIznos;
	private Boolean novcanaDonacija;
	private Boolean obradjeno;
	private OpremaTipDTO tipOpreme;
}
