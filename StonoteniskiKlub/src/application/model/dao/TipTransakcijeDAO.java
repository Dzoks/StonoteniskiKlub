package application.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.model.dto.TipTransakcijeDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface TipTransakcijeDAO {
	public ObservableList<TipTransakcijeDTO> SELECT_ALL();
	public void  UPDATE(TipTransakcijeDTO tip);
	public  void INSERT(TipTransakcijeDTO tip); 
	public  TipTransakcijeDTO getById(int id); 
}
