package application.model.dto;

import application.model.dao.OpremaTipDAO;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Oprema {

	protected IntegerProperty id;
	protected IntegerProperty idNarudzbe;
	protected IntegerProperty idTipaOpreme;
	protected StringProperty tipOpreme;
	protected StringProperty tipProizvodjac;
	protected StringProperty tipModel;
	protected BooleanProperty tipImaVelicinu;
	protected IntegerProperty idSponzora;
	protected IntegerProperty idUgovora;
	protected IntegerProperty idDonacije;
	protected BooleanProperty donirana;
	protected StringProperty status;
	protected StringProperty velicina;
	
	public Oprema() {
		super();
	}

	public Oprema(Integer id, Integer idNarudzbe, Integer idTipaOpreme, Integer idSponzora, Integer idUgovora, Integer idDonacije, Boolean donirana, String velicina) {
		super();
		this.id = id==null ? null : new SimpleIntegerProperty(id);
		this.idNarudzbe = idNarudzbe==null ? null : new SimpleIntegerProperty(idNarudzbe);
		this.idTipaOpreme = idTipaOpreme==null ? null : new SimpleIntegerProperty(idTipaOpreme);
		this.tipOpreme = idTipaOpreme==null ? null : new SimpleStringProperty(OpremaTipDAO.SELECT_TIP(idTipaOpreme));
		this.tipProizvodjac = idTipaOpreme==null ? null : new SimpleStringProperty(OpremaTipDAO.SELECT_PROIZVODJAC(idTipaOpreme));
		this.tipModel = idTipaOpreme==null ? null : new SimpleStringProperty(OpremaTipDAO.SELECT_MODEL(idTipaOpreme));
		this.tipImaVelicinu = idTipaOpreme==null ? null : new SimpleBooleanProperty(OpremaTipDAO.SELECT_IMA_LI_VELICINU(idTipaOpreme));
		this.idSponzora = idSponzora==null ? null : new SimpleIntegerProperty(idSponzora);
		this.idUgovora = idUgovora==null ? null : new SimpleIntegerProperty(idUgovora);
		this.idDonacije = idDonacije==null ? null : new SimpleIntegerProperty(idDonacije);
		this.donirana = donirana==null ? null : new SimpleBooleanProperty(donirana);
		this.velicina = velicina==null ? null : new SimpleStringProperty(velicina);
		if(donirana) {
			this.status = new SimpleStringProperty("DA");
		}
		else {
			this.status = new SimpleStringProperty("NE");
		}
	}
	
	public String getVelicina() {
		return velicina.get();
	}

	public void setVelicina(String velicina) {
		this.velicina.set(velicina);
	}

	public Integer getId() {
		return id.get();
	}

	public void setId(Integer id) {
		this.id.set(id);
	}

	public Integer getIdNarudzbe() {
		if(idNarudzbe == null) {
			return null;
		}
		else {
			return idNarudzbe.get();
		}
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
		if(idDonacije == null) {
			return null;
		}
		else {
			return idDonacije.get();
		}
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

	public String getTipOpreme() {
		return tipOpreme.get();
	}

	public void setTipOpreme(String tipOpreme) {
		this.tipOpreme.set(tipOpreme);
	}

	public String getTipProizvodjac() {
		return tipProizvodjac.get();
	}

	public void setTipProizvodjac(String tipProizvodjac) {
		this.tipProizvodjac.set(tipProizvodjac);;
	}

	public String getTipModel() {
		return tipModel.get();
	}

	public void setTipModel(String tipModel) {
		this.tipModel.set(tipModel);
	}

	public String getStatus() {
		return status.get();
	}

	public void setStatus(String status) {
		this.status.set(status);
	}

	public Integer getIdSponzora() {
		if(idSponzora == null) {
			return null;
		}
		else {
			return idSponzora.get();
		}
	}

	public void setIdSponzora(Integer idSponzora) {
		this.idSponzora.set(idSponzora);
	}

	public Integer getIdUgovora() {
		if(idUgovora == null) {
			return null;
		}
		else {
			return idUgovora.get();
		}
	}

	public void setIdUgovora(Integer idUgovora) {
		this.idUgovora.set(idUgovora);
	}
}
