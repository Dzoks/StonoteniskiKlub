package application.model.dao;

import application.model.dto.ZrijebDTO;

public interface ZrijebDAO {

	public ZrijebDTO getById(int id);
	public boolean insert(Integer idTurnira,Integer idKategorije,Integer brojTimova);
	public ZrijebDTO getZrijeb(Integer idTurnira,Integer idKategorije);
	public boolean doesExist(Integer idTurnira,Integer idKategorije);
}
