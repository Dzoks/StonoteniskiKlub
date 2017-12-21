package application.model.dto;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OpremaKluba extends Oprema{
	
	private StringProperty opis;
	private BooleanProperty aktivan;
	
	public OpremaKluba() {
		super();
	}
	
	public OpremaKluba(Integer id, Integer idNarudzbe, Integer idTipaOpreme, Integer idDonacije, Boolean donirana, String velicina) {
		super(id, idNarudzbe, idTipaOpreme, idDonacije, donirana, velicina);
	}
	
	public OpremaKluba(Integer id, Integer idNarudzbe, Integer idTipaOpreme, Integer idDonacije, Boolean donirana, String velicina, String opis, Boolean aktivan) {
		super(id, idNarudzbe, idTipaOpreme, idDonacije, donirana, velicina);
		this.opis = opis==null ? null : new SimpleStringProperty(opis);
		this.aktivan = aktivan==null ? null : new SimpleBooleanProperty(aktivan);
	}
	
	public String getOpis() {
		return opis.get();
	}
	
	public void setOpis(String opis) {
		this.opis.set(opis);
	}
	
	public Boolean getAktivan() {
		return aktivan.get();
	}
	
	public void setAktivan(Boolean aktivan) {
		this.aktivan.set(aktivan);
	}
}
