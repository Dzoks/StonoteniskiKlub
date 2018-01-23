package application.model.dao;

import application.model.dto.ClanDTO;
import application.model.dto.KategorijaDTO;
import application.model.dto.RegistracijaDTO;
import javafx.collections.ObservableList;

public interface RegistracijaDAO {

	public ObservableList<RegistracijaDTO> getAllByMember(ClanDTO member);
	public ObservableList<RegistracijaDTO> getAllBySeason(String sezona, KategorijaDTO kategorija);
	public boolean update(RegistracijaDTO trening);
	public boolean insert(RegistracijaDTO registracija);
}
