package application.model.dto;

import java.util.Date;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClanarinaDTO extends TransakcijaDTO{
	private IntegerProperty mjesec;
	private IntegerProperty godina;
	private StringProperty imeClana;
	private StringProperty prezimeClana;
	public ClanarinaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ClanarinaDTO(Integer id, String datum, Double iznos, String opis,
			String tipTransakcije,Integer mjesec, Integer godina, String imeClana,
			String prezimeClana) {
		super(id, datum, iznos, opis, tipTransakcije);
		// TODO Auto-generated constructor stub
		this.mjesec = mjesec==null ? null : new SimpleIntegerProperty(mjesec);
		this.godina = godina==null ? null : new SimpleIntegerProperty(godina);
		this.imeClana = imeClana==null ? null : new SimpleStringProperty(imeClana);
		this.prezimeClana = prezimeClana==null ? null : new SimpleStringProperty(prezimeClana);
	}
	
	public ClanarinaDTO(IntegerProperty mjesec, IntegerProperty godina, StringProperty imeClana,
			StringProperty prezimeClana) {
		this.mjesec = mjesec;
		this.godina = godina;
		this.imeClana = imeClana;
		this.prezimeClana = prezimeClana;
	}


	public IntegerProperty getMjesec() {
		return mjesec;
	}


	public void setMjesec(IntegerProperty mjesec) {
		this.mjesec = mjesec;
	}


	public IntegerProperty getGodina() {
		return godina;
	}


	public void setGodina(IntegerProperty godina) {
		this.godina = godina;
	}


	public StringProperty getImeClana() {
		return imeClana;
	}


	public void setImeClana(StringProperty imeClana) {
		this.imeClana = imeClana;
	}


	public StringProperty getPrezimeClana() {
		return prezimeClana;
	}


	public void setPrezimeClana(StringProperty prezimeClana) {
		this.prezimeClana = prezimeClana;
	}


	public StringProperty imeClanaProperty() {
		return imeClana;
	}
	public StringProperty prezimeClanaProperty() {
		return prezimeClana;
	}
	
	public IntegerProperty mjesecProperty() {
		return mjesec;
	}
	public IntegerProperty godinaProperty() {
		return godina;
	}
}
