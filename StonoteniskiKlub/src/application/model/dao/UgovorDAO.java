package application.model.dao;

import java.util.List;

import application.model.dto.SponzorDTO;
import application.model.dto.UgovorDTO;
import javafx.collections.ObservableList;

public interface UgovorDAO {
	ObservableList<UgovorDTO> selectAllById(Integer idSponzora);
	UgovorDTO selectOne(Integer idSponzora, Integer redniBroj);
	boolean insert(SponzorDTO sponzor, UgovorDTO ugovor);
}
