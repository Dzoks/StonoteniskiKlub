package application.model.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class TimDTO {
	private IntegerProperty id;
	private IntegerProperty idPrvogUcesnika;
	private IntegerProperty idDrugogUcesnika;
	
	public TimDTO() {
		super();
	}

	public TimDTO(Integer id, Integer idPrvogUcesnika, Integer idDrugogUcesnika) {
		super();
		this.id = new SimpleIntegerProperty(id);
		this.idPrvogUcesnika = new SimpleIntegerProperty(idPrvogUcesnika);
		this.idDrugogUcesnika = new SimpleIntegerProperty(idDrugogUcesnika);
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
	

	public final IntegerProperty idPrvogUcesnikaProperty() {
		return this.idPrvogUcesnika;
	}
	

	public final int getIdPrvogUcesnika() {
		return this.idPrvogUcesnikaProperty().get();
	}
	

	public final void setIdPrvogUcesnika(final int idPrvogUcesnika) {
		this.idPrvogUcesnikaProperty().set(idPrvogUcesnika);
	}
	

	public final IntegerProperty idDrugogUcesnikaProperty() {
		return this.idDrugogUcesnika;
	}
	

	public final int getIdDrugogUcesnika() {
		return this.idDrugogUcesnikaProperty().get();
	}
	

	public final void setIdDrugogUcesnika(final int idDrugogUcesnika) {
		this.idDrugogUcesnikaProperty().set(idDrugogUcesnika);
	}
}
