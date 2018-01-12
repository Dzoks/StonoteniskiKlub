package application.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import application.model.dto.TroskoviTurnirDTO;
import application.model.dto.TurnirDTO;
import application.model.dto.UcesnikPrijavaDTO;
import application.model.dto.UplataZaTurnirDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public interface UplataZaTurnirDAO {

	public ObservableList<UplataZaTurnirDTO> SELECT_ALL(); 
	public boolean INSERT(UplataZaTurnirDTO uplata, UcesnikPrijavaDTO ucesnik); 
	public void UPDATE(UplataZaTurnirDTO uplata, UcesnikPrijavaDTO ucesnik); 

}
