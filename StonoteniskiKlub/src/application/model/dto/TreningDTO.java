package application.model.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TreningDTO {

	private IntegerProperty Id;
	private StringProperty Opis;
	private IntegerProperty CLAN_Id;
	private ObjectProperty<LocalDate> Datum;
	
	
	@Override
	public String toString() {
		return Datum.get().format(DateTimeFormatter.ofPattern("YYYY.MM.dd"));
	}

	public final IntegerProperty IdProperty() {
		return this.Id;
	}
	
	public final int getId() {
		return this.IdProperty().get();
	}
	
	public final void setId(final int Id) {
		this.IdProperty().set(Id);
	}
	
	public final StringProperty OpisProperty() {
		return this.Opis;
	}
	
	public final String getOpis() {
		return this.OpisProperty().get();
	}
	
	public final void setOpis(final String Opis) {
		this.OpisProperty().set(Opis);
	}
	
	public final IntegerProperty CLAN_IdProperty() {
		return this.CLAN_Id;
	}
	
	public final int getCLAN_Id() {
		return this.CLAN_IdProperty().get();
	}
	
	public final void setCLAN_Id(final int CLAN_Id) {
		this.CLAN_IdProperty().set(CLAN_Id);
	}
	
	public final ObjectProperty<LocalDate> DatumProperty() {
		return this.Datum;
	}
	
	public final LocalDate getDatum() {
		return this.DatumProperty().get();
	}
	
	public final void setDatum(final LocalDate Datum) {
		this.DatumProperty().set(Datum);
	}

	public TreningDTO() {
		Id = new SimpleIntegerProperty();
		Opis = new SimpleStringProperty();
		CLAN_Id = new SimpleIntegerProperty();
		Datum = new SimpleObjectProperty<LocalDate>();
	}
	public TreningDTO(Integer id, String opis, Integer cLAN_Id,
			LocalDate datum) {
		
		Id = new SimpleIntegerProperty(id);
		Opis = new SimpleStringProperty(opis);
		CLAN_Id = new SimpleIntegerProperty(cLAN_Id);
		Datum = new SimpleObjectProperty<LocalDate>(datum);
	}
	
	
}
