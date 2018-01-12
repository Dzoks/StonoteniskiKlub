package application.model.dao;

import application.model.dto.PlataDTO;
import application.model.dto.ZaposleniDTO;
import javafx.collections.ObservableList;

public interface PlataDAO {
	
	public ObservableList<PlataDTO> SELECT_ALL(); 
	
	
	public void INSERT(PlataDTO plata, ZaposleniDTO zaposleni); 
	
	public void UPDATE(PlataDTO plata, ZaposleniDTO zaposleni); 
	
}
