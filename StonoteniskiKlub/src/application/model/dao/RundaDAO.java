package application.model.dao;

import application.model.dto.RundaDTO;

public interface RundaDAO {

	public RundaDTO getByBroj(Integer idZrijeba,Integer brojRunde);
	public boolean insert(Integer idZrijeba,Integer brojRunde);
	public Integer numCompleted(Integer idZrijeba,Integer brojRunde);
}
