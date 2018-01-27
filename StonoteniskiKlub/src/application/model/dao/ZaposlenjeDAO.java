package application.model.dao;

import application.model.dto.ZaposleniDTO;
import application.model.dto.ZaposlenjeDTO;
import javafx.collections.ObservableList;

public interface ZaposlenjeDAO {
	ObservableList<ZaposlenjeDTO> selectAllById(Integer id);
	boolean insert(ZaposleniDTO zaposleni, ZaposlenjeDTO zaposlenje);
	boolean zakljuci(ZaposleniDTO zaposleni, ZaposlenjeDTO zaposlenje);
}
