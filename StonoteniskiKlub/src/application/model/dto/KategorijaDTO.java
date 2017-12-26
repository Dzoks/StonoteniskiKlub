package application.model.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class KategorijaDTO {

	private IntegerProperty Id;
	private StringProperty Naziv;
	
	public KategorijaDTO() {
		Id=new SimpleIntegerProperty();
		Naziv=new SimpleStringProperty();
	}
	public KategorijaDTO(Integer id,String naziv) {
		Id=new SimpleIntegerProperty(id);
		Naziv=new SimpleStringProperty(naziv);
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
	
	public final StringProperty NazivProperty() {
		return this.Naziv;
	}
	
	public final String getNaziv() {
		return this.NazivProperty().get();
	}
	
	public final void setNaziv(final String Naziv) {
		this.NazivProperty().set(Naziv);
	}
	
	
}
