package application.model.dao;

import application.model.dto.KorisnickiNalogTipDTO;
import javafx.collections.ObservableList;

public interface KorisnickiNalogTipDAO {

	public ObservableList<KorisnickiNalogTipDTO> SELECT_ALL();
	public void insert(KorisnickiNalogTipDTO korisnickiNalogTip);
}
