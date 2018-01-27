package application.model.dao;

import application.model.dto.ClanDTO;
import application.model.dto.OpremaClana;
import javafx.collections.ObservableList;

public interface OpremaClanaDAO {

	ObservableList<OpremaClana> SELECT_ALL();
	ObservableList<ClanDTO> SELECT_AKTIVNE();
	ObservableList<OpremaClana> SELECT_BY(String tipPretrage, String rijec);
	void INSERT(OpremaClana oprema);
	void UPDATE(OpremaClana oprema, Integer idClana);
}
