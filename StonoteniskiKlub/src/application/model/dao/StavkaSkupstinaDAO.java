package application.model.dao;

import application.model.dto.SkupstinaDTO;
import application.model.dto.StavkaSkupstinaDTO;
import javafx.collections.ObservableList;

public interface StavkaSkupstinaDAO {
	
	public static final int DNEVNI_RED = 1;
	public static final int IZVJESTAJ = 2;
	
	ObservableList<StavkaSkupstinaDTO> selectAllById(Integer id, int type);
	boolean insert(SkupstinaDTO skupstina, StavkaSkupstinaDTO stavka, int type);
}
