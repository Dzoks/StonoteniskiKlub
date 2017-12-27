package application.model.dao;

import java.util.List;

import application.model.dto.DonacijaDTO;

public interface DonacijaDAO {
	List<DonacijaDTO> selectAllById(Integer idSponzora, Integer rbUgovora);
	List<DonacijaDTO> neobradjene(boolean novcane);
}
