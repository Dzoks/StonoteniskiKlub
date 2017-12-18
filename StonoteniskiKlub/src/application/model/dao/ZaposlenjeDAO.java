package application.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.model.dto.ZaposleniTipDTO;
import application.model.dto.ZaposlenjeDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ZaposlenjeDAO {
	private static final String SQL_SELECT_ALL = "SELECT * FROM ZAPOSLENJE";
	
	private static ObservableList<ZaposlenjeDTO> selectAll(){
		ObservableList<ZaposlenjeDTO> result = FXCollections.observableArrayList();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(SQL_SELECT_ALL);
			while (resultSet.next()) {
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			ConnectionPool.getInstance().checkIn(connection);
			ConnectionPool.close(resultSet, statement);
		}
		return result;
	}
}
