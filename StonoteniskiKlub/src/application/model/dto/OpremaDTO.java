package application.model.dto;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class OpremaDTO {

	protected IntegerProperty id;
	protected IntegerProperty idNarudzbe;
	protected IntegerProperty idTipaOpreme;
	protected IntegerProperty idDonacije;
	protected BooleanProperty donirana;
	
	public OpremaDTO() {
		super();
	}

	public OpremaDTO(Integer id, Integer idNarudzbe, Integer idTipaOpreme, Integer idDonacije, Boolean donirana) {
		super();
		this.id = new SimpleIntegerProperty(id);
		this.idNarudzbe = new SimpleIntegerProperty(idNarudzbe);
		this.idTipaOpreme = new SimpleIntegerProperty(idTipaOpreme);
		this.idDonacije = new SimpleIntegerProperty(idDonacije);
		this.donirana = new SimpleBooleanProperty(donirana);
	}

	public Integer getId() {
		return id.get();
	}

	public void setId(Integer id) {
		this.id.set(id);
	}

	public Integer getIdNarudzbe() {
		return idNarudzbe.get();
	}

	public void setIdNarudzbe(Integer idNarudzbe) {
		this.idNarudzbe.set(idNarudzbe);
	}

	public Integer getIdTipaOpreme() {
		return idTipaOpreme.get();
	}

	public void setIdTipaOpreme(Integer idTipaOpreme) {
		this.idTipaOpreme.setValue(idTipaOpreme);
	}

	public Integer getIdDonacije() {
		return idDonacije.get();
	}

	public void setIdDonacije(Integer idDonacije) {
		this.idDonacije.set(idDonacije);
	}

	public Boolean getDonirana() {
		return donirana.get();
	}

	public void setDonirana(Boolean donirana) {
		this.donirana.set(donirana);
	}
}
