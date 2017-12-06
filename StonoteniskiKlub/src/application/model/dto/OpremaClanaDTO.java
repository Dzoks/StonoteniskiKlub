package application.model.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class OpremaClanaDTO extends OpremaDTO{

	private StringProperty velicina;
	private IntegerProperty idClana;
	
	public OpremaClanaDTO() {
		super();
	}
	
	public OpremaClanaDTO(Integer id, Integer idNarudzbe, Integer idTipaOpreme, Integer idDonacije, Boolean donirana) {
		super(id, idNarudzbe, idTipaOpreme, idDonacije, donirana);
	}

	public String getVelicina() {
		return velicina.get();
	}

	public void setVelicina(String velicina) {
		this.velicina.set(velicina);
	}

	public Integer getIdClana() {
		return idClana.get();
	}

	public void setIdClana(Integer idClana) {
		this.idClana.set(idClana);
	}
}
