package application.model.dao;

import java.sql.Date;

import application.model.dto.TurnirDTO;
import javafx.collections.ObservableList;

public interface TurnirDAO {

	public ObservableList<TurnirDTO> getAll();
	public TurnirDTO getById(int id);
	public boolean insert(String naziv,Date datum);
	public boolean zatvori(int id);
}
