package application.model.dto;


import java.util.Date;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TransakcijaDTO {
	protected Integer id;
	protected Date datum;
	protected DoubleProperty iznos;
	protected StringProperty opis;
	protected StringProperty tipTransakcije;
	public TransakcijaDTO() {
		super();
	}
	public TransakcijaDTO(Integer id, Date datum, Double iznos, String opis, String tipTransakcije) {
		super();
		this.id = id;
		this.datum = datum;
		this.iznos = iznos==null ? null : new SimpleDoubleProperty(iznos);
		this.opis = opis==null ? null : new SimpleStringProperty(opis);
		this.tipTransakcije = tipTransakcije==null ? null : new SimpleStringProperty(tipTransakcije);
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDatum() {
		return datum;
	}
	public void setDatum(Date datum) {
		this.datum = datum;
	}
	public DoubleProperty getIznos() {
		return iznos;
	}
	public void setIznos(DoubleProperty iznos) {
		this.iznos = iznos;
	}
	public StringProperty getOpis() {
		return opis;
	}
	public void setOpis(StringProperty opis) {
		this.opis = opis;
	}
	public StringProperty getTipTransakcije() {
		return tipTransakcije;
	}
	public void setTipTransakcije(StringProperty tipTransakcije) {
		this.tipTransakcije = tipTransakcije;
	}
	
	
	
	public DoubleProperty iznosProperty() {
		return iznos;
	}
	public StringProperty opisProperty() {
		return opis;
	}
	public StringProperty tipTransakcijeProperty() {
		return tipTransakcije;
	}
}
