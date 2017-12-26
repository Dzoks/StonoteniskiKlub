package application.model.dao;

import java.util.List;

import application.model.dto.SponzorDTO;
import application.model.dto.UgovorDTO;
import javafx.collections.ObservableList;

public interface SponzorDAO {
	ObservableList<SponzorDTO> selectAll();
	SponzorDTO getById(Integer id);
	boolean insert(SponzorDTO sponzor, UgovorDTO ugovor);
}
