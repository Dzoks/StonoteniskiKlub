package application.model.dao;

import java.time.YearMonth;
import java.util.List;

import application.model.dto.DogadjajDTO;

public interface DogadjajDAO {
	List<DogadjajDTO> selectAll(YearMonth yearMonth);
	Integer insert(DogadjajDTO dogadjaj);
	boolean delete(DogadjajDTO dogadjaj);
}
