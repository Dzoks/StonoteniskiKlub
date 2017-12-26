package application.model.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UgovorDTO {
	private IntegerProperty redniBroj;
	private Date datumOd;
	private Date datumDo;
	private StringProperty opis;
	private ArrayList<DonacijaDTO> donacije;
	
	public UgovorDTO() {
	}
	public UgovorDTO(Integer redniBroj, Date datumOd, Date datumDo, String opis, ArrayList<DonacijaDTO> donacije) {
		this.redniBroj = redniBroj == null ? null : new SimpleIntegerProperty(redniBroj);
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		this.opis = opis == null ? null : new SimpleStringProperty(opis);
		this.donacije = donacije;
	}
	
	// Property metode
	public IntegerProperty redniBrojProperty(){
		return redniBroj;
	}
	public StringProperty datumOdProperty(){
		return new SimpleStringProperty(new SimpleDateFormat("dd.MM.YYYY").format(datumOd));
	}
	public StringProperty datumDoProperty(){
		return new SimpleStringProperty(new SimpleDateFormat("dd.MM.YYYY").format(datumDo));
	}
	public StringProperty opisProperty(){
		return opis;
	}
	
	// Obicni getteri
	public Integer getRedniBroj() {
		return redniBroj.get();
	}
	public Date getDatumOd() {
		return datumOd;
	}
	public Date getDatumDo() {
		return datumDo;
	}
	public String getOpis() {
		return opis.get();
	}
	public ArrayList<DonacijaDTO> getDonacije() {
		return donacije;
	}
	
	// Obicni setteri
	public void setRedniBroj(Integer redniBroj) {
		this.redniBroj.set(redniBroj);;
	}
	
	public void setDatumOd(Date datumOd) {
		this.datumOd = datumOd;
	}
	public void setDatumDo(Date datumDo) {
		this.datumDo = datumDo;
	}
	
	public void setDonacije(ArrayList<DonacijaDTO> donacije) {
		this.donacije = donacije;
	}
	public boolean daLiJeAktivan(){
		Date danas = Calendar.getInstance().getTime();
		return danas.compareTo(datumOd) > 0 && danas.compareTo(datumDo) < 0;
	}
	public void setOpis(String opis) {
		this.opis.set(opis);
	}
	@Override
	public String toString() {
		return redniBroj + " [" + datumOd + " - " + datumDo + "]" + ", " + opis;
	}
}
