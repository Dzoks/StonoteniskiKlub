package application.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.model.dto.ZaposleniTipDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ZaposleniTipDAO {

	private static final String SQL_SELECT_ALL = "SELECT * FROM ZAPOSLENI_TIP";

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

}
