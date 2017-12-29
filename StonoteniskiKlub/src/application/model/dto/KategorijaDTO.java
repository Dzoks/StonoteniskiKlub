package application.model.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class KategorijaDTO {

	private IntegerProperty Id;
	private StringProperty Naziv;
	private StringProperty Link;
	
	
	public KategorijaDTO() {
		Id=new SimpleIntegerProperty();
		Naziv=new SimpleStringProperty();
		Link=new SimpleStringProperty();
	}
	public KategorijaDTO(Integer id,String naziv,String link) {
		Id=new SimpleIntegerProperty(id);
		Naziv=new SimpleStringProperty(naziv);
		Link=new SimpleStringProperty(link);
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
	public final StringProperty LinkProperty() {
		return this.Link;
	}
	
	public final String getLink() {
		return this.LinkProperty().get();
	}
	
	public final void setLink(final String Link) {
		this.LinkProperty().set(Link);
	}
	
	@Override
	public String toString() {
		return Naziv.get();
	}
	
}
