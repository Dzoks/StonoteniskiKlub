package application.model.dto;

import java.sql.Date;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TurnirDTO {
	private IntegerProperty id;
	private StringProperty naziv;
	private Date datum;
	private BooleanProperty zavrsen;

	public TurnirDTO() {
		super();
	}

	public TurnirDTO(Integer id, String naziv, Date datum) {
		super();
		this.id = id==null ? null : new SimpleIntegerProperty(id);
		this.naziv = naziv==null ? null : new SimpleStringProperty(naziv);
		this.datum = datum;
		this.zavrsen=new SimpleBooleanProperty(false);
	}

	public Integer getId() {
		return id.get();
	}

	public void setId(Integer id) {
		this.id.set(id);
	}

	public String getNaziv() {
		return naziv.get();
	}

	public void setNaziv(String naziv) {
		this.naziv.set(naziv);
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public Boolean getZavrsen() {
		return zavrsen.get();
	}

	public void setZavrsen(Boolean zavrsen) {
		this.zavrsen.set(zavrsen);
	}

	@Override
	public String toString() {
		return id.get() + ", " + naziv.get() + ", " + datum;
	}
	
}
