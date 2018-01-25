package application.model.dao;

import application.model.dto.TreningDTO;
import javafx.collections.ObservableList;

public interface TreningDAO {

	public boolean deactivate(TreningDTO trening);
	public ObservableList<TreningDTO> selectByMember(Integer CLAN_Id);
	public boolean insert(TreningDTO trening);
	public boolean update(TreningDTO trening);
}
