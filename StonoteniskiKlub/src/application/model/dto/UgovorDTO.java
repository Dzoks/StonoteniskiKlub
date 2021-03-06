package application.model.dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import application.util.GUIUtility;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class UgovorDTO {
	private IntegerProperty redniBroj;
	private Date datumOd;
	private Date datumDo;
	private StringProperty opis;
	private ObservableList<DonacijaDTO> donacije;

	public UgovorDTO() {
	}

	public UgovorDTO(Integer redniBroj, Date datumOd, Date datumDo, String opis, ObservableList<DonacijaDTO> donacije) {
		this.redniBroj = redniBroj == null ? null : new SimpleIntegerProperty(redniBroj);
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		this.opis = opis == null ? null : new SimpleStringProperty(opis);
		this.donacije = donacije;
	}

	// Property metode
	public IntegerProperty redniBrojProperty() {
		return redniBroj;
	}

	public StringProperty datumOdProperty() {
		return new SimpleStringProperty(new SimpleDateFormat(GUIUtility.DEFAULT_DATE_FORMAT).format(datumOd));
	}

	public StringProperty datumDoProperty() {
		if(datumDo == null)
			return new SimpleStringProperty("-");
		return new SimpleStringProperty(new SimpleDateFormat(GUIUtility.DEFAULT_DATE_FORMAT).format(datumDo));
	}

	public StringProperty saDonacijomProperty() {
		return this.donacije == null ? new SimpleStringProperty("Ne")
				: this.donacije.size() > 0 ? new SimpleStringProperty("Da") : new SimpleStringProperty("Ne");
	}

	public StringProperty opisProperty() {
		return opis;
	}
	
	public boolean imaDonaciju(){
		return this.donacije == null ? false
				: this.donacije.size() > 0 ? true : false;
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

	public ObservableList<DonacijaDTO> getDonacije() {
		return donacije;
	}

	// Obicni setteri
	public void setRedniBroj(Integer redniBroj) {
		this.redniBroj = new SimpleIntegerProperty(redniBroj);
	}

	public void setDatumOd(Date datumOd) {
		this.datumOd = datumOd;
	}

	public void setDatumDo(Date datumDo) {
		this.datumDo = datumDo;
	}

	public void setDonacije(ObservableList<DonacijaDTO> donacije) {
		this.donacije = donacije;
	}

	public boolean daLiJeAktivan() {
		Date danas = Calendar.getInstance().getTime();
		return danas.compareTo(datumOd) > 0 && (datumDo != null && danas.compareTo(datumDo) < 0);
	}

	public void setOpis(String opis) {
		this.opis.set(opis);
	}

	@Override
	public String toString() {
		return redniBroj + " [" + datumOd + " - " + datumDo + "]" + ", " + opis;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((redniBroj == null) ? 0 : redniBroj.hashCode());
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
		UgovorDTO other = (UgovorDTO) obj;
		if (redniBroj == null) {
			if (other.redniBroj != null)
				return false;
		} else if (!Integer.valueOf(redniBroj.get()).equals(Integer.valueOf(other.redniBroj.get())))
			return false;
		return true;
	}
	
}
