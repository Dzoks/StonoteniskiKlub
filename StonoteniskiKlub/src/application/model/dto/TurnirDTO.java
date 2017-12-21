package application.model.dto;

import java.sql.Date;

import application.gui.organizator.controller.TurniriController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TurnirDTO {
	private IntegerProperty id;
	private StringProperty naziv;
	private ObjectProperty<Date> datum;
	private StringProperty zatvoren;
	private String konvertovanDatum;

	public TurnirDTO() {
		super();
	}

	public TurnirDTO(Integer id, String naziv, Date datum) {
		super();
		this.id = id==null ? null : new SimpleIntegerProperty(id);
		this.naziv = naziv==null ? null : new SimpleStringProperty(naziv);
		this.datum = datum==null ? null : new SimpleObjectProperty<Date>(datum);
		this.zatvoren=new SimpleStringProperty("Ne");
		
		konvertovanDatum=TurniriController.konvertujIzSQLDate(datum);
	}
	
	public TurnirDTO(Integer id, String naziv, Date datum,Boolean zavrsen) {
		super();
		this.id = id==null ? null : new SimpleIntegerProperty(id);
		this.naziv = naziv==null ? null : new SimpleStringProperty(naziv);
		this.datum = datum==null ? null : new SimpleObjectProperty<Date>(datum);
		this.zatvoren = zavrsen==true ? new SimpleStringProperty("Da") : new SimpleStringProperty("Ne");

		konvertovanDatum=TurniriController.konvertujIzSQLDate(datum);
	}

	@Override
	public String toString() {
		return id.get() + ", " + naziv.get() + ", " + konvertovanDatum;
	}

	public final ObjectProperty<Date> datumProperty() {
		return this.datum;
	}
	

	public final Date getDatum() {
		return this.datumProperty().get();
	}
	

	public final void setDatum(final Date datum) {
		this.datumProperty().set(datum);
	}

	public final IntegerProperty idProperty() {
		return this.id;
	}
	

	public final int getId() {
		return this.idProperty().get();
	}
	

	public final void setId(final int id) {
		this.idProperty().set(id);
	}
	

	public final StringProperty nazivProperty() {
		return this.naziv;
	}
	

	public final String getNaziv() {
		return this.nazivProperty().get();
	}
	

	public final void setNaziv(final String naziv) {
		this.nazivProperty().set(naziv);
	}

	public final StringProperty zatvorenProperty() {
		return this.zatvoren;
	}
	

	public final String getZatvoren() {
		return this.zatvorenProperty().get();
	}
	

	public final void setZatvoren(final String zatvoren) {
		this.zatvorenProperty().set(zatvoren);
	}

	public String getKonvertovanDatum() {
		return konvertovanDatum;
	}

	public void setKonvertovanDatum(String konvertovanDatum) {
		this.konvertovanDatum = konvertovanDatum;
	}
	
}
