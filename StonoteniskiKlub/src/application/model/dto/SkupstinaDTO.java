package application.model.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import application.util.GUIUtility;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class SkupstinaDTO {

	public SkupstinaDTO() {
	}

	public SkupstinaDTO(Integer id, LocalDate datum, ObservableList<StavkaSkupstinaDTO> stavkeDnevnogReda,
			ObservableList<StavkaSkupstinaDTO> stavkeIzvjestaja) {
		this.id = id == null ? null : new SimpleIntegerProperty(id);
		this.datum = datum;
		this.stavkeDnevnogReda = stavkeDnevnogReda;
		this.stavkeIzvjestaja = stavkeIzvjestaja;
	}

	public IntegerProperty idProperty() {
		return id;
	}

	public StringProperty imaIzvjestajProperty() {
		return stavkeIzvjestaja == null ? new SimpleStringProperty("Ne")
				: stavkeIzvjestaja.size() == 0 ? new SimpleStringProperty("Ne") : new SimpleStringProperty("Da");
	}

	public BooleanProperty izvjestajProperty() {
		return stavkeIzvjestaja == null ? new SimpleBooleanProperty(false)
				: stavkeIzvjestaja.size() == 0 ? new SimpleBooleanProperty(false) : new SimpleBooleanProperty(true);
	}

	public StringProperty datumProperty() {
		return new SimpleStringProperty(datum.format(DateTimeFormatter.ofPattern(GUIUtility.DEFAULT_DATE_FORMAT)));
	}

	public Integer getId() {
		return id == null ? null : id.get();
	}

	public void setId(Integer id) {
		this.id = new SimpleIntegerProperty(id);
	}

	public LocalDate getDatum() {
		return datum;
	}

	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}

	public ObservableList<StavkaSkupstinaDTO> getStavkeDnevnogReda() {
		return stavkeDnevnogReda;
	}

	public void setStavkeDnevnogReda(ObservableList<StavkaSkupstinaDTO> stavkeDnevnogReda) {
		this.stavkeDnevnogReda = stavkeDnevnogReda;
	}

	public ObservableList<StavkaSkupstinaDTO> getStavkeIzvjestaja() {
		return stavkeIzvjestaja;
	}

	public void setStavkeIzvjestaja(ObservableList<StavkaSkupstinaDTO> stavkeIzvjestaja) {
		this.stavkeIzvjestaja = stavkeIzvjestaja;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SkupstinaDTO other = (SkupstinaDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!Integer.valueOf(id.get()).equals(Integer.valueOf(other.id.get())))
			return false;
		return true;
	}

	private IntegerProperty id;
	private LocalDate datum;
	private ObservableList<StavkaSkupstinaDTO> stavkeDnevnogReda;
	private ObservableList<StavkaSkupstinaDTO> stavkeIzvjestaja;
}
