package application.model.dto;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OpremaKluba extends Oprema{
	
	private StringProperty opis;
	private BooleanProperty aktivan;
	private IntegerProperty idSponzora;
	private IntegerProperty idUgovora;
	private IntegerProperty idDonacije;
	private BooleanProperty donirana;
	protected StringProperty status;
	
	public OpremaKluba() {
		super();
	}
	
	public OpremaKluba(Integer id, Integer idNarudzbe, Integer idTipaOpreme, String velicina) {
		super(id, idNarudzbe, idTipaOpreme, velicina);
		
	}
	
	public OpremaKluba(Integer id, Integer idNarudzbe, Integer idTipaOpreme, Integer idSponzora, Integer idUgovora, Integer idDonacije, Boolean donirana, String velicina, String opis, Boolean aktivan) {
		super(id, idNarudzbe, idTipaOpreme, velicina);
		this.opis = opis==null ? null : new SimpleStringProperty(opis);
		this.aktivan = aktivan==null ? null : new SimpleBooleanProperty(aktivan);
		this.idSponzora = idSponzora==null ? null : new SimpleIntegerProperty(idSponzora);
		this.idUgovora = idUgovora==null ? null : new SimpleIntegerProperty(idUgovora);
		this.idDonacije = idDonacije==null ? null : new SimpleIntegerProperty(idDonacije);
		this.donirana = donirana==null ? null : new SimpleBooleanProperty(donirana);
		
		if(donirana) {
			this.status = new SimpleStringProperty("DA");
		}
		else {
			this.status = new SimpleStringProperty("NE");
		}
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
	
	public Integer getIdDonacije() {
		if(idDonacije == null) {
			return null;
		}
		else {
			return idDonacije.get();
		}
	}

	public void setIdDonacije(Integer idDonacije) {
		this.idDonacije.set(idDonacije);
	}
	
	public Integer getIdSponzora() {
		if(idSponzora == null) {
			return null;
		}
		else {
			return idSponzora.get();
		}
	}

	public void setIdSponzora(Integer idSponzora) {
		this.idSponzora.set(idSponzora);
	}

	public Integer getIdUgovora() {
		if(idUgovora == null) {
			return null;
		}
		else {
			return idUgovora.get();
		}
	}

	public void setIdUgovora(Integer idUgovora) {
		this.idUgovora.set(idUgovora);
	}

	public Boolean getDonirana() {
		return donirana.get();
	}

	public void setDonirana(Boolean donirana) {
		this.donirana.set(donirana);
	}

	public String getStatus() {
		return status.get();
	}

	public void setStatus(String status) {
		this.status.set(status);
	}
}
