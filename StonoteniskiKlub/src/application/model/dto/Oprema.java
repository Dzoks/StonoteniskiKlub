package application.model.dto;

import application.model.dao.DAOFactory;
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
	protected StringProperty velicina;
	
	public Oprema() {
		super();
	}

	public Oprema(Integer id, Integer idNarudzbe, Integer idTipaOpreme, String velicina) {
		super();
		this.id = id==null ? null : new SimpleIntegerProperty(id);
		this.idNarudzbe = idNarudzbe==null ? null : new SimpleIntegerProperty(idNarudzbe);
		this.idTipaOpreme = idTipaOpreme==null ? null : new SimpleIntegerProperty(idTipaOpreme);
		this.tipOpreme = idTipaOpreme==null ? null : new SimpleStringProperty(DAOFactory.getDAOFactory().getOpremaTipDAO().SELECT_TIP(idTipaOpreme));
		this.tipProizvodjac = idTipaOpreme==null ? null : new SimpleStringProperty(DAOFactory.getDAOFactory().getOpremaTipDAO().SELECT_PROIZVODJAC(idTipaOpreme));
		this.tipModel = idTipaOpreme==null ? null : new SimpleStringProperty(DAOFactory.getDAOFactory().getOpremaTipDAO().SELECT_MODEL(idTipaOpreme));
		this.tipImaVelicinu = idTipaOpreme==null ? null : new SimpleBooleanProperty(DAOFactory.getDAOFactory().getOpremaTipDAO().SELECT_IMA_LI_VELICINU(idTipaOpreme));
		this.velicina = velicina==null ? null : new SimpleStringProperty(velicina);
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
}
