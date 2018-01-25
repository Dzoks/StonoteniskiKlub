package application.model.dao;

import application.model.dto.KategorijaDTO;
import javafx.collections.ObservableList;

public interface KategorijaDAO {

	public ObservableList<KategorijaDTO> getAll(boolean explicitQuery);
	public KategorijaDTO getById(Integer Id);
	public ObservableList<KategorijaDTO> getAllMySQL();
	
}
