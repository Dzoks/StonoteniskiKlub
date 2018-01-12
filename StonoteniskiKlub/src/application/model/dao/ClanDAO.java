package application.model.dao;

import java.util.List;

import application.model.dto.ClanDTO;
import javafx.collections.ObservableList;

public interface ClanDAO {
	
	public List<ClanDTO> selectAllByImePrezime(String ime, String prezime);
	public ObservableList<ClanDTO> SELECT_ALL();
	public List<ClanDTO> selectAll();
	public void setAktivan(boolean flag, int clanId);
	public void insertAll(ClanDTO clan);
	public ClanDTO getById(int id);
	public void insert(ClanDTO clan);
}
