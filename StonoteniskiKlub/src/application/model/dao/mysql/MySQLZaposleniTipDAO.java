package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.model.dao.ZaposleniTipDAO;
import application.model.dto.ZaposleniTipDTO;
import application.util.ConnectionPool;
import application.util.ErrorLogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySQLZaposleniTipDAO implements ZaposleniTipDAO {

	private static final String SQL_SELECT_ALL = "select * from ZAPOSLENI_TIP";
	private static final String SQL_SELECT_TIP = "select * from ZAPOSLENI_TIP where Id=?";

	@Override
	public ObservableList<ZaposleniTipDTO> selectAll() {
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
			 ;
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(connection);
			ConnectionPool.close(resultSet, statement);
		}
		return result;
	}

	@Override
	public ZaposleniTipDTO getById(Integer id) {
		ZaposleniTipDTO result = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = ConnectionPool.prepareStatement(connection, SQL_SELECT_TIP, false, id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				result = new ZaposleniTipDTO(id, resultSet.getString("Tip"));
			}
		} catch (SQLException e) {
			 ;
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(connection);
			ConnectionPool.close(resultSet, statement);
		}
		return result;
	}

}
