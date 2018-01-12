package application.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import application.model.dto.Narudzba;
import application.model.dto.TroskoviOpremaDTO;
import application.model.dto.TroskoviTurnirDTO;
import application.model.dto.TurnirDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface TroskoviTurnirDAO {

	public  ObservableList<TroskoviTurnirDTO> SELECT_ALL(); 
	public  void INSERT(TroskoviTurnirDTO troskovi, TurnirDTO turnir); 
	public  void UPDATE(TroskoviTurnirDTO trosak, TurnirDTO turnir); 
	
}
