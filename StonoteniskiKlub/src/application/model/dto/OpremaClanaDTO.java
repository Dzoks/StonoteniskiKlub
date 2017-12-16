package application.model.dto;

import application.model.dao.OpremaClanaDAO;
import application.model.dao.OpremaKlubaDAO;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OpremaClanaDTO extends OpremaDTO{

	private StringProperty velicina;
	private IntegerProperty idClana;
	private StringProperty jmbClana;
	private StringProperty imeClana;
	private StringProperty prezimeClana;
	private BooleanProperty aktivan;
	
	public OpremaClanaDTO() {
		super();
	}
	
	public OpremaClanaDTO(Integer id, Integer idNarudzbe, Integer idTipaOpreme, Integer idDonacije, Boolean donirana) {
		super(id, idNarudzbe, idTipaOpreme, idDonacije, donirana);
	}

	public OpremaClanaDTO(Integer id, Integer idNarudzbe, Integer idTipaOpreme, Integer idDonacije, Boolean donirana, String velicina, Integer idClana) {
		super(id, idNarudzbe, idTipaOpreme, idDonacije, donirana);
		this.velicina = velicina==null ? null : new SimpleStringProperty(velicina);
		this.idClana = idClana==null ? null : new SimpleIntegerProperty(idClana);
		this.jmbClana = idClana==null ? null : new SimpleStringProperty(OpremaClanaDAO.SELECT_JMB_CLAN(idClana));
		this.imeClana = idClana==null ? null : new SimpleStringProperty(OpremaClanaDAO.SELECT_IME_CLAN(idClana));
		this.prezimeClana = idClana==null ? null : new SimpleStringProperty(OpremaClanaDAO.SELECT_PREZIME_CLAN(idClana));
		this.aktivan = idClana==null ? null : new SimpleBooleanProperty(OpremaClanaDAO.SELECT_AKTIVAN_CLAN(idClana));
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

	public String getJmbClana() {
		return jmbClana.get();
	}

	public void setJmbClana(String jmbClana) {
		this.jmbClana.set(jmbClana);
	}

	public String getImeClana() {
		return imeClana.get();
	}

	public void setImeClana(String imeClana) {
		this.imeClana.set(imeClana);
	}

	public String getPrezimeClana() {
		return prezimeClana.get();
	}

	public void setPrezimeClana(String prezimeClana) {
		this.prezimeClana.set(prezimeClana);
	}
}