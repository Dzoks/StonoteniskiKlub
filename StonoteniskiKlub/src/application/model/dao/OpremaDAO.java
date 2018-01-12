package application.model.dao;

import application.model.dto.Oprema;

public interface OpremaDAO {

	void UPDATE(Oprema oprema, String velicina, Integer idOpreme);
	void UPDATE_OBRISAN(Oprema oprema);
}
