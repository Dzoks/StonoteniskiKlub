package application.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import application.model.dto.ClanDTO;
import application.model.dto.ClanarinaDTO;
import application.model.dto.PlataDTO;
import application.model.dto.ZaposleniDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface PlataDAO {
	
	public ObservableList<PlataDTO> SELECT_ALL(); 
	
	
	public void INSERT(PlataDTO plata, ZaposleniDTO zaposleni); 
	
	public void UPDATE(PlataDTO plata, ZaposleniDTO zaposleni); 
	
}
