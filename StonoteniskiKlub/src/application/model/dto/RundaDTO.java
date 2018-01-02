package application.model.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class RundaDTO {
	private IntegerProperty idZrijeba;
	private IntegerProperty brojRunde;
	
	public RundaDTO() {
		super();
	}

	public RundaDTO(Integer idZrijeba, Integer brojRunde) {
		super();
		this.idZrijeba = new SimpleIntegerProperty(idZrijeba);
		this.brojRunde = new SimpleIntegerProperty(brojRunde);
	}

	public final IntegerProperty idZrijebaProperty() {
		return this.idZrijeba;
	}
	

	public final int getIdZrijeba() {
		return this.idZrijebaProperty().get();
	}
	

	public final void setIdZrijeba(final int idZrijeba) {
		this.idZrijebaProperty().set(idZrijeba);
	}
	

	public final IntegerProperty brojRundeProperty() {
		return this.brojRunde;
	}
	

	public final int getBrojRunde() {
		return this.brojRundeProperty().get();
	}
	

	public final void setBrojRunde(final int brojRunde) {
		this.brojRundeProperty().set(brojRunde);
	}
	
}
