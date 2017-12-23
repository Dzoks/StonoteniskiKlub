package application.model.dto;

import java.time.LocalDate;
import java.util.HashMap;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RegistracijaDTO {

	private IntegerProperty CLAN_Id;
	private StringProperty Sezona;
	private IntegerProperty KATEGORIJA_Id;
	private ObjectProperty<LocalDate> Datum;
	private HashMap<String, Integer> rezultati;
	private ClanDTO clan;
	
	
	public RegistracijaDTO(Integer cLAN_Id, String sezona, Integer kATEGORIJA_Id,
			LocalDate datum, HashMap<String, Integer> rezultati,ClanDTO clan) {
		super();
		CLAN_Id = new SimpleIntegerProperty(cLAN_Id);
		Sezona = new SimpleStringProperty(sezona);
		KATEGORIJA_Id = new SimpleIntegerProperty(kATEGORIJA_Id);
		Datum = new SimpleObjectProperty<>(datum);
		this.rezultati = rezultati;
		this.clan=clan;
	}

	public RegistracijaDTO() {
		CLAN_Id = new SimpleIntegerProperty();
		Sezona = new SimpleStringProperty();
		KATEGORIJA_Id = new SimpleIntegerProperty();
		Datum = new SimpleObjectProperty<>();
	}
	public HashMap<String, Integer> getRezultati() {
		return rezultati;
	}

	public void setRezultati(HashMap<String, Integer> rezultati) {
		this.rezultati = rezultati;
	}

	public ClanDTO getClan() {
		return clan;
	}

	public void setClan(ClanDTO clan) {
		this.clan = clan;
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
	
	public final StringProperty SezonaProperty() {
		return this.Sezona;
	}
	
	public final String getSezona() {
		return this.SezonaProperty().get();
	}
	
	public final void setSezona(final String Sezona) {
		this.SezonaProperty().set(Sezona);
	}
	
	public final IntegerProperty KATEGORIJA_IdProperty() {
		return this.KATEGORIJA_Id;
	}
	
	public final int getKATEGORIJA_Id() {
		return this.KATEGORIJA_IdProperty().get();
	}
	
	public final void setKATEGORIJA_Id(final int KATEGORIJA_Id) {
		this.KATEGORIJA_IdProperty().set(KATEGORIJA_Id);
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
	
	
}
