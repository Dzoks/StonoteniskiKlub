package application.model.dao;

import java.util.List;

import application.model.dto.SponzorDTO;
import application.model.dto.UgovorDTO;

public interface SponzorDAO {
	List<SponzorDTO> selectAll();
	boolean insert(SponzorDTO sponzor, UgovorDTO ugovor);
}
