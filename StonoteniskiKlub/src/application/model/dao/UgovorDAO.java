package application.model.dao;

import java.util.List;

import application.model.dto.UgovorDTO;

public interface UgovorDAO {
	List<UgovorDTO> selectAllById(Integer idSponzora);
	UgovorDTO selectOne(Integer idSponzora, Integer redniBroj);
}
