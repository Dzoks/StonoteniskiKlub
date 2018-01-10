package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.sql.PreparedStatement;

import application.model.dao.DAOFactory;
import application.model.dao.DogadjajDAO;
import application.model.dao.KorisnickiNalogDAO;
import application.model.dto.DogadjajDTO;
import application.util.ConnectionPool;

public class MySQLDogadjajDAO implements DogadjajDAO {

	private static final String SELECT_ALL_FOR_MONTH = "select * from DOGADJAJ where Pocetak between ? and ?";

	@Override
	public List<DogadjajDTO> selectAll(YearMonth yearMonth) {
		List<DogadjajDTO> result = new ArrayList<DogadjajDTO>();
		LocalDate prviDan = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
		LocalDate zadnjiDan = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), yearMonth.lengthOfMonth());
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = ConnectionPool.prepareStatement(connection, SELECT_ALL_FOR_MONTH, false,
					java.sql.Date.valueOf(prviDan), java.sql.Date.valueOf(zadnjiDan));
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				result.add(new DogadjajDTO(resultSet.getInt("Id"),
						LocalDateTime.of(new java.sql.Date(resultSet.getTimestamp("Pocetak").getTime()).toLocalDate(),
								new java.sql.Time(resultSet.getTimestamp("Pocetak").getTime()).toLocalTime()),
						LocalDateTime.of(new java.sql.Date(resultSet.getTimestamp("Kraj").getTime()).toLocalDate(),
								new java.sql.Time(resultSet.getTimestamp("Kraj").getTime()).toLocalTime()),
						resultSet.getString("Opis"),
						DAOFactory.getDAOFactory().getDogadjajTipDAO().selectById(resultSet.getInt("DOGADJAJ_TIP_Id")),
						null));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().checkIn(connection);
			ConnectionPool.close(resultSet, statement);
		}
		return result;
	}

	public static MySQLDogadjajDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MySQLDogadjajDAO();
		}
		return INSTANCE;
	}

	protected MySQLDogadjajDAO() {

	}

	private static MySQLDogadjajDAO INSTANCE = null;
}
