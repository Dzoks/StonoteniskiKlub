package application.model.dto;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DonacijaStavka {

	private StringProperty tip;
	private StringProperty proizvodjac;
	private StringProperty model;
	private IntegerProperty kolicina;
	private StringProperty velicina;
	private StringProperty opis;
	private BooleanProperty imaLiVelicinu;
	
	public DonacijaStavka() {
		super();
	}

	public DonacijaStavka(String tip, String proizvodjac, String model, Integer kolicina, Boolean imaLiVelicinu, String velicina, String opis) {
		super();
		this.tip = new SimpleStringProperty(tip);
		this.proizvodjac = new SimpleStringProperty(proizvodjac);
		this.model = new SimpleStringProperty(model);
		this.kolicina = new SimpleIntegerProperty(kolicina);
		this.velicina = new SimpleStringProperty(velicina);
		this.imaLiVelicinu = new SimpleBooleanProperty(imaLiVelicinu);
		this.opis = new SimpleStringProperty(opis);
	}

	public String getTip() {
		return tip.get();
	}

	public void setTip(String tip) {
		this.tip.set(tip);
	}

	public String getProizvodjac() {
		return proizvodjac.get();
	}

	public void setProizvodjac(String proizvodjac) {
		this.proizvodjac.set(proizvodjac);
	}

	public String getModel() {
		return model.get();
	}

	public void setModel(String model) {
		this.model.set(model);
	}

	public Integer getKolicina() {
		return kolicina.get();
	}

	public void setKolicina(Integer kolicina) {
		this.kolicina.set(kolicina);
	}

	public String getVelicina() {
		return velicina.get();
	}

	public void setVelicina(String velicina) {
		this.velicina.set(velicina);
	}

	public Boolean getImaLiVelicinu() {
		return imaLiVelicinu.get();
	}

	public void setImaLiVelicinu(Boolean imaLiVelicinu) {
		this.imaLiVelicinu.set(true);
	}

	public String getOpis() {
		return opis.get();
	}

	public void setOpis(String opis) {
		this.opis.set(opis);
	}
}
