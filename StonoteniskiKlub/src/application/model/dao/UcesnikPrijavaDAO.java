package application.model.dao;

import java.sql.Date;

import application.model.dto.UcesnikPrijavaDTO;
import javafx.collections.ObservableList;

public interface UcesnikPrijavaDAO {

	public UcesnikPrijavaDTO getByJmb(String jmb);
	public Integer insert(String jmb,String ime,String prezime,Character pol,
			Date datumRodjenja,Integer idTurnira,Integer idKategorije,Date datum);
	public boolean izmjeniUcesnika(Integer idPrijave,String ime,String prezime,Date datum);
	public Integer addNew(Integer idTurnira,Integer idKategorije,Integer idOsobe,Date datum);
	public ObservableList<UcesnikPrijavaDTO> SELECT_ALL();
	public boolean doesExist(String jmb,Integer idTurnira,Integer idKategorije);
}
