package application.model.dao;

import java.util.List;

import application.model.dto.ClanDTO;
import application.model.dto.ClanarinaDTO;
import javafx.collections.ObservableList;

public interface ClanarinaDAO {
	
	public ObservableList<ClanarinaDTO> SELECT_ALL();
	public boolean INSERT(ClanarinaDTO clanarina, ClanDTO clan);
	public void UPDATE(ClanarinaDTO clanarina);	
	//brada
	public List<ClanarinaDTO> selectByClanID(int clanID);
}
