package application.model.dao;

import java.util.List;

import application.model.dto.DogadjajTipDTO;

public interface DogadjajTipDAO {
	List<DogadjajTipDTO> selectAll();
	DogadjajTipDTO selectById(Integer id);
}
