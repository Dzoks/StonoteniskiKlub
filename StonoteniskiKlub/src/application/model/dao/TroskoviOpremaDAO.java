package application.model.dao;

import application.model.dto.Narudzba;
import application.model.dto.TroskoviOpremaDTO;
import javafx.collections.ObservableList;

public interface TroskoviOpremaDAO {
	
	public ObservableList<TroskoviOpremaDTO> SELECT_ALL(); 
	public boolean INSERT(TroskoviOpremaDTO troskovi, Narudzba narudzba); 
	public void UPDATE(TroskoviOpremaDTO trosak, Narudzba narudzba); 

}
