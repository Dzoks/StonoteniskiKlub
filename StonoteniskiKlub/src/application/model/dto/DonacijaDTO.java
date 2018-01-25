package application.model.dto;

import java.math.BigDecimal;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DonacijaDTO {
	private SponzorDTO sponzor;
	private UgovorDTO ugovor;
	private IntegerProperty redniBroj;
	private String opis;
	private DoubleProperty kolicina;
	private DoubleProperty novcaniIznos;
	private Boolean novcanaDonacija;
	private Boolean obradjeno;
	private OpremaTip tipOpreme;
	private TransakcijaDTO transakcija;
	public TransakcijaDTO getTransakcija() {
		return transakcija;
	}

	public void setTransakcija(TransakcijaDTO transakcija) {
		this.transakcija = transakcija;
	}
	public DonacijaDTO() {
	}

	public DonacijaDTO(SponzorDTO sponzor, UgovorDTO ugovor, Integer redniBroj, String opis, BigDecimal kolicina,
			BigDecimal novcaniIznos, Boolean novcanaDonacija, Boolean obradjeno, OpremaTip tipOpreme) {
		this.sponzor = sponzor;
		this.ugovor = ugovor;
		this.redniBroj = redniBroj == null ? null : new SimpleIntegerProperty(redniBroj);
		this.opis = opis;
		this.kolicina = kolicina == null ? null : new SimpleDoubleProperty(kolicina.doubleValue());
		this.novcaniIznos = novcaniIznos == null ? null : new SimpleDoubleProperty(novcaniIznos.doubleValue());
		this.novcanaDonacija = novcanaDonacija;
		this.obradjeno = obradjeno;
		this.tipOpreme = tipOpreme;
	}

	// proprety metode
	public IntegerProperty redniBrojProperty() {
		return redniBroj;
	}

	public StringProperty tipDonacijeProperty() {
		return novcanaDonacija ? new SimpleStringProperty("Novcana") : new SimpleStringProperty("Oprema");
	}

	public DoubleProperty novcaniIznosProperty() {
		return novcaniIznos == null ? new SimpleDoubleProperty(0) : novcaniIznos;
	}

	public StringProperty tipOpremeProperty() {
		return tipOpreme == null ? new SimpleStringProperty("-") : new SimpleStringProperty(tipOpreme.toString());
	}

	public DoubleProperty kolicinaProperty() {
		return kolicina == null ? new SimpleDoubleProperty(0) : kolicina;
	}

	public StringProperty obradjenoProperty() {
		return obradjeno ? new SimpleStringProperty("Da") : new SimpleStringProperty("Ne");
	}

	// getteri
	public SponzorDTO getSponzor() {
		return sponzor;
	}

	public UgovorDTO getUgovor() {
		return ugovor;
	}

	public Integer getRedniBroj() {
		return redniBroj.get();
	}

	public String getOpis() {
		return opis;
	}

	public BigDecimal getKolicina() {
		return kolicina == null ? null : new BigDecimal(kolicina.get());
	}

	public BigDecimal getNovcaniIznos() {
		return novcaniIznos == null ? null : new BigDecimal(novcaniIznos.get());
	}

	public Boolean getNovcanaDonacija() {
		return novcanaDonacija;
	}

	public Boolean getObradjeno() {
		return obradjeno;
	}

	public OpremaTip getTipOpreme() {
		return tipOpreme;
	}

	// setteri
	public void setSponzor(SponzorDTO sponzor) {
		this.sponzor = sponzor;
	}

	public void setUgovor(UgovorDTO ugovor) {
		this.ugovor = ugovor;
	}

	public void setRedniBroj(Integer redniBroj) {
		this.redniBroj = new SimpleIntegerProperty(redniBroj);
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public void setKolicina(BigDecimal kolicina) {
		this.kolicina = kolicina == null ? null : new SimpleDoubleProperty(kolicina.doubleValue());
	}

	public void setNovcaniIznos(BigDecimal novcaniIznos) {
		this.novcaniIznos = novcaniIznos == null ? null : new SimpleDoubleProperty(novcaniIznos.doubleValue());
	}

	public void setNovcanaDonacija(Boolean novcanaDonacija) {
		this.novcanaDonacija = novcanaDonacija;
	}

	public void setObradjeno(Boolean obradjeno) {
		this.obradjeno = obradjeno;
	}

	public void setTipOpreme(OpremaTip tipOpreme) {
		this.tipOpreme = tipOpreme;
	}

	@Override
	public String toString() {
		String result = "";
		if (novcanaDonacija) {
			result = "Novcana, iznos: " + novcaniIznos.getValue().toString() + " KM";
		} else {
			result = tipOpreme + ", komada: " + kolicina.getValue().toString();
		}
		return result;
	}
	
	public StringProperty opisTransakcijeProperty() { //ostaviti ovo, inace Heleni puca
		if(transakcija!=null)
			return transakcija.opisProperty();
		return new SimpleStringProperty("");
	}
	public StringProperty datumUplateProperty() {
		if(transakcija!=null)
			return transakcija.datumProperty();
		return new SimpleStringProperty("");
	}
	public StringProperty nazivSponzoraProperty() {
		if(sponzor!=null)
			return sponzor.nazivProperty();
		return new SimpleStringProperty("");
	}
}
