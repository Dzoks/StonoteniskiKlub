package application.model.dao;

import java.util.List;

import application.model.dto.ClanstvoDTO;

public interface ClanstvoDAO {

	public List<ClanstvoDTO> getByClanId(int id);
	public boolean insert(int clanId);
	public void update(int clanId);
}
