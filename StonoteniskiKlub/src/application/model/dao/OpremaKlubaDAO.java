package application.model.dao;

import application.model.dto.OpremaKluba;
import javafx.collections.ObservableList;

public interface OpremaKlubaDAO {

	ObservableList<OpremaKluba> SELECT_ALL();
	ObservableList<OpremaKluba> SELECT_AKTIVNOST(Boolean aktivan);
	ObservableList<OpremaKluba> SELECT_BY(Boolean aktivan, String tipPretrage, String rijec);
	void INSERT(OpremaKluba oprema, Integer brojInstanci);
	void UPDATE_AKTIVNOST(OpremaKluba oprema, Boolean aktivan);
	void UPDATE_OPIS(OpremaKluba oprema, String opis);
}
