package application.model.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UgovorDTO {
	private Integer redniBroj;
	private Date datumOd;
	private Date datumDo;
	private String opis;
	private ArrayList<DonacijaDTO> donacije;
	
	public UgovorDTO() {
	}
	public UgovorDTO(Integer redniBroj, Date datumOd, Date datumDo, String opis, ArrayList<DonacijaDTO> donacije) {
		this.redniBroj = redniBroj;
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		this.opis = opis;
		this.donacije = donacije;
	}
	public Integer getRedniBroj() {
		return redniBroj;
	}
	public void setRedniBroj(Integer redniBroj) {
		this.redniBroj = redniBroj;
	}
	public Date getDatumOd() {
		return datumOd;
	}
	public void setDatumOd(Date datumOd) {
		this.datumOd = datumOd;
	}
	public Date getDatumDo() {
		return datumDo;
	}
	public void setDatumDo(Date datumDo) {
		this.datumDo = datumDo;
	}
	public ArrayList<DonacijaDTO> getDonacije() {
		return donacije;
	}
	public void setDonacije(ArrayList<DonacijaDTO> donacije) {
		this.donacije = donacije;
	}
	public boolean daLiJeAktivan(){
		Date danas = Calendar.getInstance().getTime();
		return danas.compareTo(datumOd) > 0 && danas.compareTo(datumDo) < 0;
	}
	
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	@Override
	public String toString() {
		return redniBroj + " [" + datumOd + " - " + datumDo + "]" + ", " + opis;
	}
}
