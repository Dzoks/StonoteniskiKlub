package application.model.dao;

import application.model.dto.ZaposlenjeDTO;
import javafx.collections.ObservableList;

public interface ZaposlenjeDAO {
	ObservableList<ZaposlenjeDTO> selectAllById(Integer id);
}
