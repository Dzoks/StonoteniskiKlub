package application.model.dto;

import java.util.Date;

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

	public IntegerProperty getTipID() {
		return tipID;
	}

	public void setTipID(IntegerProperty tipID) {
		this.tipID = tipID;
	}

	public StringProperty getTipNaziv() {
		return tipNaziv;
	}

	public void setTipNaziv(StringProperty tipNaziv) {
		this.tipNaziv = tipNaziv;
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

	public DoubleProperty getPlata() {
		return plata;
	}

	public void setPlata(DoubleProperty plata) {
		this.plata = plata;
	}
}
