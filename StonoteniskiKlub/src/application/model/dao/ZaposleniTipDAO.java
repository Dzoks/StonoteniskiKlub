package application.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.model.dto.ZaposleniTipDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ZaposleniTipDAO {

	private static final String SQL_SELECT_ALL = "select * from ZAPOSLENI_TIP";
	private static final String SQL_SELECT_TIP = "select Tip from ZAPOSLENI_TIP where Id=?";
	
	
	public static ObservableList<ZaposleniTipDTO> seletAll() {
		ObservableList<ZaposleniTipDTO> result = FXCollections.observableArrayList();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(SQL_SELECT_ALL);
			while (resultSet.next()) {
				result.add(new ZaposleniTipDTO(resultSet.getInt("Id"), resultSet.getString("Tip")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			ConnectionPool.getInstance().checkIn(connection);
			ConnectionPool.close(resultSet, statement);
		}
		return result;
	}
	
	public static String select_tip(Integer id){
		String result = "";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try{
			connection = ConnectionPool.getInstance().checkOut();
			statement =  ConnectionPool.prepareStatement(connection, SQL_SELECT_TIP, false, id);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				result = resultSet.getString("Tip");
			}
		} catch(SQLException e){
			e.printStackTrace();
		} finally{
			ConnectionPool.getInstance().checkIn(connection);
			ConnectionPool.close(resultSet, statement);
		}
		return result;
	}

}
