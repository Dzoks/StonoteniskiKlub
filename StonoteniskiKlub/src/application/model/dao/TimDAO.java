package application.model.dao;

import java.util.ArrayList;

import application.model.dto.TimDTO;
import application.model.dto.UcesnikPrijavaDTO;
import javafx.collections.ObservableList;

public interface TimDAO {

	public ObservableList<UcesnikPrijavaDTO> getSingle(Integer idTurnira,Integer idKategorije);
	public ArrayList<TimDTO> getSingleList(Integer idTurnira,Integer idKategorije);
	public ObservableList<UcesnikPrijavaDTO> getDouble(Integer idTurnira,Integer idKategorije);
	public ArrayList<TimDTO> getDoubleList(Integer idTurnira,Integer idKategorije);
	public String getSingleById(int id);
	public String getDoubleById(int id);
	public boolean insertSingle(Integer idPrvogUcesnika);
	public boolean insertDouble(Integer idPrvogUcesnika,Integer idDrugogUcesnika);
}
