package application.model.dao;

import application.model.dto.ZaposleniDTO;
import application.model.dto.ZaposleniTipDTO;
import application.model.dto.ZaposlenjeDTO;
import javafx.collections.ObservableList;

public interface ZaposleniDAO {
	ObservableList<ZaposleniDTO> selectAll();
	ObservableList<ZaposleniDTO> selectAktivni(boolean aktivan);
	Integer insert(ZaposleniDTO zaposleni, ZaposlenjeDTO zaposlenje, ZaposleniTipDTO zaposleniTip);
	ZaposleniDTO selectById(Integer id, boolean aktivan);
	boolean delete(ZaposleniDTO zaposleni, boolean aktivan); 
	boolean add(ZaposleniDTO zaposleni);
}
