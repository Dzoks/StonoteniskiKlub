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

	public IntegerProperty getId() {
		return id;
	}

	public void setId(IntegerProperty id) {
		this.id = id;
	}

	public StringProperty getTip() {
		return tip;
	}

	public void setTip(StringProperty tip) {
		this.tip = tip;
	}
	@Override
	public String toString() {
		return tip.get();
	}
}
