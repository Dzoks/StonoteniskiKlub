package application.model.dto;

import java.util.Date;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class ZaposlenjeDTO {
	private IntegerProperty tipID;
	private StringProperty tipNaziv;
	private Date datumOd;
	private Date datumDo;
	private BooleanProperty plata;
	
	public ZaposlenjeDTO() {
	}
	
	public ZaposlenjeDTO(IntegerProperty tipID, StringProperty tipNaziv, Date datumOd, Date datumDo,
			BooleanProperty plata) {
		this.tipID = tipID;
		this.tipNaziv = tipNaziv;
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		this.plata = plata;
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
	public BooleanProperty getPlata() {
		return plata;
	}
	public void setPlata(BooleanProperty plata) {
		this.plata = plata;
	}
}
