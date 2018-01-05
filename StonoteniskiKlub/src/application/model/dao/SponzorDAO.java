package application.model.dao;

import application.model.dto.SponzorDTO;
import application.model.dto.UgovorDTO;
import javafx.collections.ObservableList;

public interface SponzorDAO {
	ObservableList<SponzorDTO> selectAll();
	SponzorDTO getById(Integer id);
	ObservableList<SponzorDTO> getByNaziv(String naziv);
	boolean insert(SponzorDTO sponzor, UgovorDTO ugovor);
	void update(SponzorDTO sponzor);
}
