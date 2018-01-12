package application.model.dto;

import java.util.Date;

import javafx.beans.property.StringProperty;

public class TroskoviTurnirDTO extends TransakcijaDTO{
	private TurnirDTO turnir;

	public TroskoviTurnirDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TroskoviTurnirDTO(Integer id, Date datum, Double iznos, String opis, String tipTransakcije, TurnirDTO turnir) {
		super(id, datum, iznos, opis, tipTransakcije);
		// TODO Auto-generated constructor stub
		this.turnir = turnir;
	}

	public TroskoviTurnirDTO(TurnirDTO turnir) {
		super();
		this.turnir = turnir;
	}

	public TurnirDTO getTurnir() {
		return turnir;
	}

	public void setTurnir(TurnirDTO turnir) {
		this.turnir = turnir;
	}
	public StringProperty nazivTurniraProperty() {
		return turnir.nazivProperty();
	}
	
}
