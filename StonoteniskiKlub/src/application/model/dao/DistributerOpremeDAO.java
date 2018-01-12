package application.model.dao;

import application.model.dto.DistributerOpreme;
import javafx.collections.ObservableList;

public interface DistributerOpremeDAO {

	ObservableList<DistributerOpreme> SELECT_ALL();
	String SELECT_BY_ID(Integer id);
	void INSERT(DistributerOpreme distributerOpreme);
}
