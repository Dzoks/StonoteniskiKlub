package application.model.dto;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TransakcijaDTO {
	protected IntegerProperty id;
	protected StringProperty datum;
	protected DoubleProperty iznos;
	protected StringProperty opis;
	protected StringProperty tipTransakcije;
	public TransakcijaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TransakcijaDTO(Integer id, String datum, Double iznos, String opis, String tipTransakcije) {
		super();
		this.id = id==null ? null : new SimpleIntegerProperty(id);
		this.datum = datum==null ? null : new SimpleStringProperty(datum);
		System.out.println(this.datum);
		this.iznos = iznos==null ? null : new SimpleDoubleProperty(iznos);
		this.opis = opis==null ? null : new SimpleStringProperty(opis);
		System.out.println(this.opis);
		this.tipTransakcije = tipTransakcije==null ? null : new SimpleStringProperty(tipTransakcije);
	}
	public IntegerProperty getId() {
		return id;
	}
	public void setId(IntegerProperty id) {
		this.id = id;
	}
	public StringProperty getDatum() {
		return datum;
	}
	public void setDatum(StringProperty datum) {
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
	
	public IntegerProperty idProperty() {
		return id;
	}
	public StringProperty datumProperty() {
		return datum;
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
