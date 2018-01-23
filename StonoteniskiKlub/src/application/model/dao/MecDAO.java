package application.model.dao;

import java.util.ArrayList;

import application.model.dto.MecDTO;
import javafx.collections.ObservableList;

public interface MecDAO {

	public ObservableList<MecDTO> getAllSingle(Integer idZrijeba,Integer brojRunde);
	public ArrayList<MecDTO> getAllList(Integer idZrijeba,Integer brojRunde);
	public ObservableList<MecDTO> getAllDouble(Integer idZrijeba,Integer brojRunde);
	public boolean insertRezultat(MecDTO mec);
	public boolean insertSingle(Integer idTim1,Integer idZrijeba,Integer brojRunde,Integer redniBroj);
	public boolean insert(Integer idTim1,Integer idTim2,Integer idZrijeba,Integer brojRunde,Integer redniBroj);
}
