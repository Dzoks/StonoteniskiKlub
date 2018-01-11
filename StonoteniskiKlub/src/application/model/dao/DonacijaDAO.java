package application.model.dao;

import application.model.dto.DonacijaDTO;
import application.model.dto.SponzorDTO;
import application.model.dto.UgovorDTO;
import javafx.collections.ObservableList;

public interface DonacijaDAO {
	ObservableList<DonacijaDTO> selectAllById(Integer idSponzora, Integer rbUgovora);
	ObservableList<DonacijaDTO> neobradjene(boolean novcane);
	boolean insert(SponzorDTO sponzor, UgovorDTO ugovor, DonacijaDTO donacija);
	void setObradjeno(DonacijaDTO donacija);
	void setIdTransakcije(DonacijaDTO donacija, int id);
}
