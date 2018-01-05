package application.model.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class KorisnickiNalogTipDTO {

	private Integer id;
	private String naziv;
	
	public KorisnickiNalogTipDTO(Integer id, String naziv) {
		super();
		this.id =id;
		this.naziv = naziv;
	}
	public KorisnickiNalogTipDTO() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	@Override
	public String toString() {
		return naziv;
	}
	
}
