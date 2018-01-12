package application.model.dao;

import application.model.dto.UcesnikPrijavaDTO;
import application.model.dto.UplataZaTurnirDTO;
import javafx.collections.ObservableList;

public interface UplataZaTurnirDAO {

	public ObservableList<UplataZaTurnirDTO> SELECT_ALL(); 
	public boolean INSERT(UplataZaTurnirDTO uplata, UcesnikPrijavaDTO ucesnik); 
	public void UPDATE(UplataZaTurnirDTO uplata, UcesnikPrijavaDTO ucesnik); 

}
