package application.model.dto;

import java.util.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TroskoviOpremaDTO extends TransakcijaDTO{ //prikazati samo narudzbe koje su stigle...
	private Narudzba narudzba;

	public TroskoviOpremaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TroskoviOpremaDTO(Integer id, Date datum, Double iznos, String opis, String tipTransakcije, Narudzba narudzba) {
		super(id, datum, iznos, opis, tipTransakcije);
		this.narudzba = narudzba;
		// TODO Auto-generated constructor stub
	}

	public Narudzba getNarudzba() {
		return narudzba;
	}
	public StringProperty narudzbaProperty() {
		return new SimpleStringProperty(narudzba.toString());
	}
	public StringProperty distributerProperty() {
		return new SimpleStringProperty(narudzba.getNazivDistributeraOpreme());
	}
	public void setNarudzba(Narudzba narudzba) {
		this.narudzba = narudzba;
	}
	
}
