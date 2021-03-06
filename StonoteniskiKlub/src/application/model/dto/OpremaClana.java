package application.model.dto;

import application.model.dao.DAOFactory;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OpremaClana extends Oprema{

	private IntegerProperty idClana;
	private ClanDTO clan;
	private StringProperty jmbClana;
	private StringProperty imeClana;
	private StringProperty prezimeClana;
	private BooleanProperty aktivan;
	
	public OpremaClana() {
		super();
	}
	
	public OpremaClana(Integer id, Integer idNarudzbe, Integer idTipaOpreme, String velicina) {
		super(id, idNarudzbe, idTipaOpreme, velicina);
	}

	public OpremaClana(Integer id, Integer idNarudzbe, Integer idTipaOpreme, String velicina, Integer idClana) {
		super(id, idNarudzbe, idTipaOpreme, velicina);
		this.idClana = idClana==null ? null : new SimpleIntegerProperty(idClana);
		this.clan = idClana==null ? null : DAOFactory.getDAOFactory().getClanDAO().getById(idClana);
		this.jmbClana = idClana==null ? null : new SimpleStringProperty(clan.getJmb());
		this.imeClana = idClana==null ? null : new SimpleStringProperty(clan.getIme());
		this.prezimeClana = idClana==null ? null : new SimpleStringProperty(clan.getPrezime());
	}

	public Integer getIdClana() {
		return idClana.get();
	}

	public void setIdClana(Integer idClana) {
		this.idClana.set(idClana);
	}

	public ClanDTO getClan() {
		return clan;
	}

	public void setClan(ClanDTO clan) {
		this.clan = clan;
	}

	public Boolean getAktivan() {
		return aktivan.get();
	}

	public void setAktivan(Boolean aktivan) {
		this.aktivan.set(aktivan);
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
