package application.model.dao;

import application.model.dto.TroskoviTurnirDTO;
import application.model.dto.TurnirDTO;
import javafx.collections.ObservableList;

public interface TroskoviTurnirDAO {

	public  ObservableList<TroskoviTurnirDTO> SELECT_ALL(); 
	public  void INSERT(TroskoviTurnirDTO troskovi, TurnirDTO turnir); 
	public  void UPDATE(TroskoviTurnirDTO trosak, TurnirDTO turnir); 
	
}
