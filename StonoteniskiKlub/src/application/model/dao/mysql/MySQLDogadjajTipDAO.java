package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

import application.model.dao.DogadjajTipDAO;
import application.model.dto.DogadjajTipDTO;
import application.util.ConnectionPool;

public class MySQLDogadjajTipDAO implements DogadjajTipDAO{
	
	private static final String MY_SQL_SELECT_ALL = "select * from DOGADJAJ_TIP";
	private static final String MY_SQL_SELECT_BY_ID = "select * from DOGADJAJ_TIP where Id=?";
	
	@Override
	public List<DogadjajTipDTO> selectAll() {
		List<DogadjajTipDTO> result = new ArrayList<DogadjajTipDTO>();
		Connection connection=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(MY_SQL_SELECT_ALL);
			while(resultSet.next()){
				result.add(new DogadjajTipDTO(resultSet.getInt("Id"), resultSet.getString("Tip")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().checkIn(connection);
			ConnectionPool.close(resultSet, statement);
		}
		return result;
	}

	@Override
	public DogadjajTipDTO selectById(Integer id) {
		DogadjajTipDTO result = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = ConnectionPool.prepareStatement(connection, MY_SQL_SELECT_BY_ID, false, id);
			resultSet = statement.executeQuery();
			if(resultSet.next()){
				result = new DogadjajTipDTO(resultSet.getInt("Id"), resultSet.getString("Tip"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().checkIn(connection);
			ConnectionPool.close(resultSet, statement);
		}
		return result;
	}
	
	public static MySQLDogadjajTipDAO getInstance(){
		if(INSTANCE == null){
			INSTANCE = new MySQLDogadjajTipDAO();
		}
		return INSTANCE;
	}
	
	protected MySQLDogadjajTipDAO() {
	}
	private static MySQLDogadjajTipDAO INSTANCE = null;
	
}
