package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.model.dao.SkupstinaDAO;
import application.model.dto.SkupstinaDTO;
import application.util.ConnectionPool;
import application.util.ErrorLogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySQLSkupstinaDAO implements SkupstinaDAO {

	private static final String SQL_SELECT_ALL = "select * from SKUPSTINA where Aktivan=true";
	private static final String SQL_INSERT = "insert into SKUPSTINA values(null,?, true)";
	private static final String SQL_DELETE = "update SKUPSTINA set Aktivan=false where Id=?";
	
	@Override
	public ObservableList<SkupstinaDTO> selectAll() {
		ObservableList<SkupstinaDTO> result = FXCollections.observableArrayList();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(SQL_SELECT_ALL);
			while (resultSet.next()) {
				result.add(new SkupstinaDTO(resultSet.getInt("Id"),
						new java.sql.Date(resultSet.getDate("Datum").getTime()).toLocalDate(), null, null));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(connection);
			ConnectionPool.close(resultSet, statement);
		}
		return result;
	}

	@Override
	public boolean insert(SkupstinaDTO skupstina) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = ConnectionPool.prepareStatement(connection, SQL_INSERT, true,
					Date.valueOf(skupstina.getDatum()));
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				skupstina.setId(resultSet.getInt(1));
			}
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(connection);
			ConnectionPool.close(resultSet, statement);
		}
		return result;
	}

	public static MySQLSkupstinaDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MySQLSkupstinaDAO();
		}
		return INSTANCE;
	}

	protected MySQLSkupstinaDAO() {
	}

	private static MySQLSkupstinaDAO INSTANCE = null;

	@Override
	public boolean delete(SkupstinaDTO skupstina) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = ConnectionPool.prepareStatement(connection, SQL_DELETE, false, skupstina.getId());
			statement.executeUpdate();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(connection);
			ConnectionPool.close(statement);
		}
		return result;
	}
}
