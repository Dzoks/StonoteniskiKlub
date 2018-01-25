package application.model.dao;

import application.model.dto.KategorijaTurniraDTO;
import javafx.collections.ObservableList;

public interface KategorijaTurniraDAO {

	public ObservableList<KategorijaTurniraDTO> getAll();
	public KategorijaTurniraDTO getById(Integer id);
}
