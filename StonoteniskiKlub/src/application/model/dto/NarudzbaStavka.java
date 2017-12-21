package application.model.dto;

import application.model.dao.OpremaTipDAO;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NarudzbaStavka {

	private IntegerProperty idNarudzbe;
	private IntegerProperty idTipaOpreme;
	private StringProperty tipOpreme;
	private StringProperty tipProizvodjac;
	private StringProperty tipModel;
	private StringProperty velicina;
	private IntegerProperty kolicina;
	private DoubleProperty cijena;
	private BooleanProperty obradjeno;
	private StringProperty status;
	
	public NarudzbaStavka() {
		super();
	}

	public NarudzbaStavka(Integer idNarudzbe, Integer idTipaOpreme, String velicina, Integer kolicina, Double cijena, Boolean obradjeno) {
		super();
		this.idNarudzbe = new SimpleIntegerProperty(idNarudzbe);
		this.idTipaOpreme = new SimpleIntegerProperty(idTipaOpreme);
		this.tipOpreme = new SimpleStringProperty(OpremaTipDAO.SELECT_TIP(idTipaOpreme));
		this.tipProizvodjac = new SimpleStringProperty(OpremaTipDAO.SELECT_PROIZVODJAC(idTipaOpreme));
		this.tipModel = new SimpleStringProperty(OpremaTipDAO.SELECT_MODEL(idTipaOpreme));
		this.velicina = new SimpleStringProperty(velicina);
		this.kolicina = new SimpleIntegerProperty(kolicina);
		this.cijena = new SimpleDoubleProperty(cijena);
		this.obradjeno = new SimpleBooleanProperty(obradjeno);
		if(obradjeno) {
			this.status = new SimpleStringProperty("DA");
		}
		else {
			this.status = new SimpleStringProperty("NE");
		}
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
		this.idTipaOpreme.set(idTipaOpreme);
	}

	public String getVelicina() {
		return velicina.get();
	}

	public void setVelicina(String velicina) {
		this.velicina.set(velicina);
	}

	public Integer getKolicina() {
		return kolicina.get();
	}

	public void setKolicina(Integer kolicina) {
		this.kolicina.set(kolicina);
	}

	public Double getCijena() {
		return cijena.get();
	}

	public void setCijena(Double cijena) {
		this.cijena.set(cijena);
	}

	public Boolean getObradjeno() {
		return obradjeno.get();
	}

	public void setObradjeno(Boolean obradjeno) {
		this.obradjeno.set(obradjeno);
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
		this.tipProizvodjac.set(tipProizvodjac);
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
}
