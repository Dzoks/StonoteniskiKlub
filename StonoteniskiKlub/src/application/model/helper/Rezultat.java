package application.model.helper;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Rezultat {
	private StringProperty turnir;
	private IntegerProperty bodovi;

	public Rezultat() {
		turnir = new SimpleStringProperty();
		bodovi = new SimpleIntegerProperty();
	}

	public Rezultat(String turnir, Integer bodovi) {
		this.turnir = new SimpleStringProperty(turnir);
		this.bodovi = new SimpleIntegerProperty(bodovi);
	}

	public final StringProperty turnirProperty() {
		return this.turnir;
	}

	public final String getTurnir() {
		return this.turnirProperty().get();
	}

	public final void setTurnir(final String turnir) {
		this.turnirProperty().set(turnir);
	}

	public final IntegerProperty bodoviProperty() {
		return this.bodovi;
	}

	public final int getBodovi() {
		return this.bodoviProperty().get();
	}

	public final void setBodovi(final int bodovi) {
		this.bodoviProperty().set(bodovi);
	}

}
