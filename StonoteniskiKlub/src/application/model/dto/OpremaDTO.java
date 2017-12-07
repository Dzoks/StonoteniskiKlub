package application.model.dto;

import application.model.dao.OpremaTipDAO;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OpremaDTO {

	protected IntegerProperty id;
	protected IntegerProperty idNarudzbe;
	protected IntegerProperty idTipaOpreme;
	private StringProperty tipOpreme;
	private StringProperty tipProizvodjac;
	private StringProperty tipModel;
	protected IntegerProperty idDonacije;
	protected BooleanProperty donirana;
	
	public OpremaDTO() {
		super();
	}

	public OpremaDTO(Integer id, Integer idNarudzbe, Integer idTipaOpreme, Integer idDonacije, Boolean donirana) {
		super();
		this.id = id==null ? null : new SimpleIntegerProperty(id);
		this.idNarudzbe = idNarudzbe==null ? null : new SimpleIntegerProperty(idNarudzbe);
		this.idTipaOpreme = idTipaOpreme==null ? null : new SimpleIntegerProperty(idTipaOpreme);
		this.tipOpreme = new SimpleStringProperty(OpremaTipDAO.SELECT_TIP(idTipaOpreme));
		this.tipProizvodjac = new SimpleStringProperty(OpremaTipDAO.SELECT_PROIZVODJAC(idTipaOpreme));
		this.tipModel = new SimpleStringProperty(OpremaTipDAO.SELECT_MODEL(idTipaOpreme));
		this.idDonacije = idDonacije==null ? null : new SimpleIntegerProperty(idDonacije);
		this.donirana = donirana==null ? null : new SimpleBooleanProperty(donirana);
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
