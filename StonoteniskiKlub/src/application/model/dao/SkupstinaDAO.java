package application.model.dao;

import application.model.dto.SkupstinaDTO;
import javafx.collections.ObservableList;

public interface SkupstinaDAO {
	public ObservableList<SkupstinaDTO> selectAll();
	public boolean insert(SkupstinaDTO skupstina);
	public boolean delete(SkupstinaDTO skupstina);
}
