package application.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import application.model.dto.ClanDTO;
import application.model.dto.ClanarinaDTO;
import application.model.dto.Narudzba;
import application.model.dto.TroskoviOpremaDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public interface TroskoviOpremaDAO {
	
	public ObservableList<TroskoviOpremaDTO> SELECT_ALL(); 
	public boolean INSERT(TroskoviOpremaDTO troskovi, Narudzba narudzba); 
	public void UPDATE(TroskoviOpremaDTO trosak, Narudzba narudzba); 

}
