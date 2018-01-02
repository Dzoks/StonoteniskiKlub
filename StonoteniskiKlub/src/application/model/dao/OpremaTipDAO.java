package application.model.dao;

import application.model.dto.OpremaTip;
import javafx.collections.ObservableList;

public interface OpremaTipDAO {

	ObservableList<OpremaTip> SELECT_ALL();
	void INSERT(OpremaTip opremaTip);
	String SELECT_TIP(Integer id);
	String SELECT_PROIZVODJAC(Integer id);
	String SELECT_MODEL(Integer id);
	Boolean SELECT_IMA_LI_VELICINU(Integer id);
}
