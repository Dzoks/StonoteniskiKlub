package application.model.dao;

import application.model.dto.ZaposleniDTO;
import application.model.dto.ZaposleniTipDTO;
import application.model.dto.ZaposlenjeDTO;
import javafx.collections.ObservableList;

public interface ZaposleniDAO {
	ObservableList<ZaposleniDTO> selectAll();
	ObservableList<ZaposleniDTO> selectAktivni();
	boolean insert(ZaposleniDTO zaposleni, ZaposlenjeDTO zaposlenje, ZaposleniTipDTO zaposleniTip);
}
