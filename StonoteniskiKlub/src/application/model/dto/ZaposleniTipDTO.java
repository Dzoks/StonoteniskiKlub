package application.model.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ZaposleniTipDTO {
	private IntegerProperty id;
	private StringProperty tip;

	public ZaposleniTipDTO() {
	}

	public ZaposleniTipDTO(Integer id, String tip) {
		this.id = id == null ? null : new SimpleIntegerProperty(id);
		this.tip = tip == null ? null : new SimpleStringProperty(tip);
	}

	// property metode
	public IntegerProperty idProperty() {
		return id;
	}

	public StringProperty tipProperty() {
		return tip;
	}

	// getteri
	public Integer getId() {
		return id == null ? null : id.get();
	}

	public String getTip() {
		return tip == null ? null : tip.get();
	}

	// setteri
	public void setId(Integer id) {
		this.id = id == null ? null : new SimpleIntegerProperty(id);
	}

	public void setTip(String tip) {
		this.tip = tip == null ? null : new SimpleStringProperty(tip);
	}

	@Override
	public String toString() {
		return tip.get();
	}
}
