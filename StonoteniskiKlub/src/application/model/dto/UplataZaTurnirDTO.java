package application.model.dto;

import java.util.Date;

import application.model.dao.TurnirDAO;
import javafx.beans.property.StringProperty;

public class UplataZaTurnirDTO extends TransakcijaDTO{
	private UcesnikPrijavaDTO ucesnik;

	public UcesnikPrijavaDTO getUcesnik() {
		return ucesnik;
	}

	public void setUcesnik(UcesnikPrijavaDTO ucesnik) {
		this.ucesnik = ucesnik;
	}

	public UplataZaTurnirDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UplataZaTurnirDTO(Integer id, Date datum, Double iznos, String opis, String tipTransakcije, UcesnikPrijavaDTO ucesnik) {
		super(id, datum, iznos, opis, tipTransakcije);
		this.ucesnik = ucesnik;
		// TODO Auto-generated constructor stub
	}

	public UplataZaTurnirDTO(UcesnikPrijavaDTO ucesnik) {
		super();
		this.ucesnik = ucesnik;
	}
	public StringProperty imeProperty() {
		return ucesnik.imeProperty();
	}
	public StringProperty prezimeProperty() {
		return ucesnik.prezimeProperty();
	}
	public StringProperty nazivTurniraProperty() {
		TurnirDTO turnir = TurnirDAO.getById(ucesnik.getIdTurnira());
		return turnir.nazivProperty();
	}
}
