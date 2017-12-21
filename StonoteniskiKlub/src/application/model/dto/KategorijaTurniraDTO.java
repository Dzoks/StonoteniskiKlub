package application.model.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class KategorijaTurniraDTO {
	private IntegerProperty id;
	private StringProperty kategorija;
	
	public KategorijaTurniraDTO() {
		super();
	}
	
	public KategorijaTurniraDTO(Integer id, String kategorija) {
		super();
		this.id = id==null ? null : new SimpleIntegerProperty(id);
		this.kategorija = kategorija==null ? null : new SimpleStringProperty(kategorija);
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
	

	public final StringProperty kategorijaProperty() {
		return this.kategorija;
	}
	

	public final String getKategorija() {
		return this.kategorijaProperty().get();
	}
	

	public final void setKategorija(final String kategorija) {
		this.kategorijaProperty().set(kategorija);
	}
}
