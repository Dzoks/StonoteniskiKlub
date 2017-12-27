package application.model.dao;

import java.util.List;

import application.model.dto.DonacijaDTO;
import application.model.dto.SponzorDTO;
import application.model.dto.UgovorDTO;

public interface DonacijaDAO {
	List<DonacijaDTO> selectAllById(Integer idSponzora, Integer rbUgovora);
	List<DonacijaDTO> neobradjene(boolean novcane);
	boolean insert(SponzorDTO sponzor, UgovorDTO ugovor, DonacijaDTO donacija);
}
