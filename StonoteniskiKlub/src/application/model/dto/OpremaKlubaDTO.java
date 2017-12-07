package application.model.dto;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OpremaKlubaDTO extends OpremaDTO{
	
	private StringProperty opis;
	private BooleanProperty aktivan;
	
	public OpremaKlubaDTO() {
		super();
	}
	
	public OpremaKlubaDTO(Integer id, Integer idNarudzbe, Integer idTipaOpreme, Integer idDonacije, Boolean donirana) {
		super(id, idNarudzbe, idTipaOpreme, idDonacije, donirana);
	}
	
	public OpremaKlubaDTO(Integer id, Integer idNarudzbe, Integer idTipaOpreme, Integer idDonacije, Boolean donirana, String opis, Boolean aktivan) {
		super(id, idNarudzbe, idTipaOpreme, idDonacije, donirana);
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
