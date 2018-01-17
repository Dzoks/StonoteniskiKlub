package application.model.dao;

import application.model.dto.Narudzba;
import javafx.collections.ObservableList;

public interface NarudzbaDAO {

	ObservableList<Narudzba> SELECT_ALL();
	ObservableList<Narudzba> SELECT_OPREMA_KLUBA();
	ObservableList<Narudzba> SELECT_NEOBRADJENE(Boolean opremaKluba);
	Integer SELECT_NEXT_ID();
	Integer INSERT(Narudzba narudzba, Boolean vrstaOpreme);
	void UPDATE(Narudzba narudzba, Boolean vrstaOpreme);
	void UPDATE_OBRADJENO(Integer idNarudzbe);
	void UPDATE_OBRISAN(Narudzba narudzba);
	Boolean PROVJERA_STATUSA(Integer idNarudzbe);
}
