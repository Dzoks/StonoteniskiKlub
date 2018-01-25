package application.model.dto;

import java.util.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlataDTO extends TransakcijaDTO{
	private ZaposleniDTO zaposleni;

	public PlataDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlataDTO(Integer id, Date datum, Double iznos, String opis, String tipTransakcije,ZaposleniDTO zaposleni) {
		super(id, datum, iznos, opis, tipTransakcije);
		this.zaposleni = zaposleni;
		// TODO Auto-generated constructor stub
	}

	public ZaposleniDTO getZaposleni() {
		return zaposleni;
	}

	public void setZaposleni(ZaposleniDTO zaposleni) {
		this.zaposleni = zaposleni;
	}
	
	public StringProperty imeProperty() {
		return new SimpleStringProperty(zaposleni.getIme());
	}
	public StringProperty prezimeProperty() {
		return new SimpleStringProperty(zaposleni.getPrezime());
	}
	public StringProperty zaposlenjeProperty() {
		String s ="";
		for(ZaposlenjeDTO zap : zaposleni.getZaposljenja()) {
			s+=zap.getTipNaziv() + ",";
		}
		if(!s.isEmpty()) {
			s=s.substring(0,s.length()-1);
			System.out.println(s);
			return new SimpleStringProperty(s);
		}
		return new SimpleStringProperty("");

	}
}
