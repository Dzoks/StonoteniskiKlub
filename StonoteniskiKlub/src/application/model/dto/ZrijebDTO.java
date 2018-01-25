package application.model.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ZrijebDTO {
	private IntegerProperty id;
	private IntegerProperty idTurnira;
	private IntegerProperty idKategorije;
	private IntegerProperty brojTimova;
	
	public ZrijebDTO() {
		super();
	}

	public ZrijebDTO(Integer id, Integer idTurnira, Integer idKategorije,
			Integer brojTimova) {
		super();
		this.id = new SimpleIntegerProperty(id);
		this.idTurnira = new SimpleIntegerProperty(idTurnira);
		this.idKategorije = new SimpleIntegerProperty(idKategorije);
		this.brojTimova = new SimpleIntegerProperty(brojTimova);
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
	
	public final IntegerProperty idTurniraProperty() {
		return this.idTurnira;
	}
	
	public final int getIdTurnira() {
		return this.idTurniraProperty().get();
	}
	
	public final void setIdTurnira(final int idTurnira) {
		this.idTurniraProperty().set(idTurnira);
	}
	
	public final IntegerProperty idKategorijeProperty() {
		return this.idKategorije;
	}
	
	public final int getIdKategorije() {
		return this.idKategorijeProperty().get();
	}
	
	public final void setIdKategorije(final int idKategorije) {
		this.idKategorijeProperty().set(idKategorije);
	}
	
	public final IntegerProperty brojTimovaProperty() {
		return this.brojTimova;
	}
	
	public final int getBrojTimova() {
		return this.brojTimovaProperty().get();
	}
	
	public final void setBrojTimova(final int brojTimova) {
		this.brojTimovaProperty().set(brojTimova);
	}
	
}
