package application.model.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ZaposlenjeDTO {
	private IntegerProperty tipID;
	private StringProperty tipNaziv;
	private Date datumOd;
	private Date datumDo;
	private DoubleProperty plata;

	public ZaposlenjeDTO() {
	}

	public ZaposlenjeDTO(Integer tipID, String tipNaziv, Date datumOd, Date datumDo, Double plata) {
		this.tipID = tipID == null ? null : new SimpleIntegerProperty(tipID);
		this.tipNaziv = tipNaziv == null ? null : new SimpleStringProperty(tipNaziv);
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		this.plata = plata == null ? null : new SimpleDoubleProperty(plata);
	}

	// property metode

	public IntegerProperty tipIDProperty() {
		return tipID;
	}
	public StringProperty tipNazivProperty() {
		return tipNaziv;
	}
	public StringProperty datumOdProperty() {
		return new SimpleStringProperty(new SimpleDateFormat("yyyy-MM-dd").format(datumOd));
	}
	public StringProperty datumDoProperty() {
		return datumDo == null ? new SimpleStringProperty("-")
				: new SimpleStringProperty(new SimpleDateFormat("yyyy-MM-dd").format(datumDo));
	}
	public DoubleProperty plataProperty() {
		return plata;
	}
	// getteri
	public Integer getTipID(){
		return tipID == null ? null : tipID.get();
	}
	public String getTipNaziv(){
		return tipNaziv == null ? null : tipNaziv.get();
	}
	public Date getDatumOd() {
		return datumOd;
	}
	public Date getDatumDo() {
		return datumDo;
	}
	public Double getPlata(){
		return plata == null ? null : plata.get();
	}
	// setteri
	public void setTipID(Integer tipID) {
		this.tipID = new SimpleIntegerProperty(tipID);
	}

	public void setTipNaziv(String tipNaziv) {
		this.tipNaziv = new SimpleStringProperty(tipNaziv);
	}
	public void setDatumOd(Date datumOd) {
		this.datumOd = datumOd;
	}
	public void setDatumDo(Date datumDo) {
		this.datumDo = datumDo;
	}

	public void setPlata(Double plata) {
		this.plata = new SimpleDoubleProperty(plata);
	}
}
