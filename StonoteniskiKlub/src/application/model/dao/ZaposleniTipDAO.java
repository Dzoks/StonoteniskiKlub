package application.model.dao;

import application.model.dto.ZaposleniTipDTO;
import javafx.collections.ObservableList;

public interface ZaposleniTipDAO {
	ObservableList<ZaposleniTipDTO> selectAll();
	ZaposleniTipDTO getById(Integer id);
}
