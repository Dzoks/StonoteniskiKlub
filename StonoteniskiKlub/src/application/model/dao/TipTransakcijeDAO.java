package application.model.dao;

import application.model.dto.TipTransakcijeDTO;
import javafx.collections.ObservableList;

public interface TipTransakcijeDAO {
	public ObservableList<TipTransakcijeDTO> SELECT_ALL();
	public void  UPDATE(TipTransakcijeDTO tip);
	public  void INSERT(TipTransakcijeDTO tip); 
	public  TipTransakcijeDTO getById(int id); 
}
