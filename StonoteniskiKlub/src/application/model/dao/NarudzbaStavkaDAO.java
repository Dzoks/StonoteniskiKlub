package application.model.dao;

import application.model.dto.NarudzbaStavka;
import javafx.collections.ObservableList;

public interface NarudzbaStavkaDAO {

	ObservableList<NarudzbaStavka> SELECT_BY_IDNARUDZBE(Integer id);
	void INSERT(NarudzbaStavka narudzbaStavka);
	void DELETE(NarudzbaStavka narudzbaStavka);
	void UPDATE_OBRADJENO(NarudzbaStavka stavkaNarudzbe);
}
