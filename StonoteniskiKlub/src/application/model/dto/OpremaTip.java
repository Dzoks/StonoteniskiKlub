package application.model.dto;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OpremaTip {

	private IntegerProperty id;
	private StringProperty tip;
	private StringProperty proizvodjac;
	private StringProperty model;
	private BooleanProperty imaVelicinu;
	
	public OpremaTip() {
		super();
	}

	public OpremaTip(Integer id, String tip, String proizvodjac, String model, Boolean imaVelicinu) {
		super();
		this.id = id==null ? null : new SimpleIntegerProperty(id);
		this.tip = tip==null ? null : new SimpleStringProperty(tip);
		this.proizvodjac = proizvodjac==null ? null : new SimpleStringProperty(proizvodjac);
		this.model = model==null ? null : new SimpleStringProperty(model);
		this.imaVelicinu = imaVelicinu==null ? null : new SimpleBooleanProperty(imaVelicinu);
	}

	public Integer getId() {
		return id.get();
	}

	public void setId(Integer id) {
		this.id.set(id);
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

	public Boolean getImaVelicinu() {
		return imaVelicinu.get();
	}

	public void setImaVelicinu(Boolean imaVelicinu) {
		this.imaVelicinu.set(imaVelicinu);
	}

	@Override
	public String toString() {
		return tip.get().toUpperCase() + " - " + proizvodjac.get() + " - " + model.get();
	}
}
