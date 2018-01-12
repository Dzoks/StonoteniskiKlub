package application.model.dao;

import java.util.List;

import application.model.dto.DogadjajTipDTO;
import javafx.collections.ObservableList;

public interface DogadjajTipDAO {
	ObservableList<DogadjajTipDTO> selectAll();
	DogadjajTipDTO selectById(Integer id);
	boolean insert(DogadjajTipDTO dogadjaj);
}
