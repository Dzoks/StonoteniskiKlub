package application.model.dto;

import java.sql.Blob;
import java.util.Date;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class ZaposleniDTO extends OsobaDTO {
	private BooleanProperty aktivan;
	private ObservableList<ZaposlenjeDTO> zaposljenja;

	public ZaposleniDTO() {
	}

	public ZaposleniDTO(Integer id, String ime, String prezime, String imeRoditelja, String jmb, Character pol,
			Date datumRodjenja, Blob slika, List<String> telefoni, Boolean aktivan,
			ObservableList<ZaposlenjeDTO> zaposlenja) {
		super(id, ime, prezime, imeRoditelja, jmb, pol, datumRodjenja, slika, telefoni);
		this.aktivan = aktivan == null ? null : new SimpleBooleanProperty(aktivan);
		this.zaposljenja = zaposlenja;
	}

	public StringProperty aktivanProperty() {
		return aktivan.get() ? new SimpleStringProperty("Da") : new SimpleStringProperty("Ne");
	}

	public void setAktivan(Boolean aktivan) {
		this.aktivan = new SimpleBooleanProperty(aktivan);
	}
	public Boolean getAktivan(){
		return aktivan == null ? null : aktivan.get();
	}
	public ObservableList<ZaposlenjeDTO> getZaposljenja() {
		return zaposljenja;
	}

	public void setZaposljenja(ObservableList<ZaposlenjeDTO> zaposljenja) {
		this.zaposljenja = zaposljenja;
	}

}
