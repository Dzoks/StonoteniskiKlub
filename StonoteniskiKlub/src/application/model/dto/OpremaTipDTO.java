package application.model.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OpremaTipDTO {

	private IntegerProperty id;
	private StringProperty tip;
	private StringProperty proizvodjac;
	private StringProperty model;
	
	public OpremaTipDTO() {
		super();
	}

	public OpremaTipDTO(Integer id, String tip, String proizvodjac, String model) {
		super();
		this.id = id==null ? null : new SimpleIntegerProperty(id);
		this.tip = tip==null ? null : new SimpleStringProperty(tip);
		this.proizvodjac = proizvodjac==null ? null : new SimpleStringProperty(proizvodjac);
		this.model = model==null ? null : new SimpleStringProperty(model);
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

	@Override
	public String toString() {
		return tip.get().toUpperCase() + " - " + proizvodjac.get() + " - " + model.get();
	}
	
}
