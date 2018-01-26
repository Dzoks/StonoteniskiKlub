package application.model.dao.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import application.model.dao.DAOFactory;
import application.model.dao.DogadjajDAO;
import application.model.dto.DogadjajDTO;
import application.util.ConnectionPool;
import application.util.ErrorLogger;

public class MySQLDogadjajDAO implements DogadjajDAO {

	private static final String SELECT_ALL_FOR_MONTH = "select * from DOGADJAJ where Pocetak between ? and ?";
	private static final String SQL_INSERT = "{call dodaj_dogadjaj(?,?,?,?,?,?)}";

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
				DogadjajDTO dogadjaj = new DogadjajDTO(resultSet.getInt("Id"),
						LocalDateTime.of(new java.sql.Date(resultSet.getTimestamp("Pocetak").getTime()).toLocalDate(),
								new java.sql.Time(resultSet.getTimestamp("Pocetak").getTime()).toLocalTime()),
						LocalDateTime.of(new java.sql.Date(resultSet.getTimestamp("Kraj").getTime()).toLocalDate(),
								new java.sql.Time(resultSet.getTimestamp("Kraj").getTime()).toLocalTime()),
						resultSet.getString("Opis"),
						DAOFactory.getDAOFactory().getDogadjajTipDAO().selectById(resultSet.getInt("DOGADJAJ_TIP_Id")),
						null);
				Integer nalogId = resultSet.getInt("KORISNICKI_NALOG_Id");
				
				if(nalogId != null){
					dogadjaj.setKorisnickiNalog(DAOFactory.getDAOFactory().getKorisnickiNalogDAO().getNalogById(nalogId));
				}
				result.add(dogadjaj);
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

	public static MySQLDogadjajDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MySQLDogadjajDAO();
		}
		return INSTANCE;
	}

	protected MySQLDogadjajDAO() {

	}

	private static MySQLDogadjajDAO INSTANCE = null;

	@Override
	public Integer insert(DogadjajDTO dogadjaj) {
		Integer result = -1;
		Connection connection = null;
		CallableStatement statement = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = connection.prepareCall(SQL_INSERT);
			statement.setString("pOpis", dogadjaj.getOpis());
			statement.setInt("pDogadjajTipId", dogadjaj.getTipDogadjaja().getId());
			statement.setInt("pKorisnickiNalogId", dogadjaj.getKorisnickiNalog().getNalogId());
			statement.setTimestamp("pPocetak", Timestamp.valueOf(dogadjaj.getPocetak()));
			statement.setTimestamp("pKraj", Timestamp.valueOf(dogadjaj.getKraj()));
			statement.registerOutParameter("pRezId", Types.INTEGER);
			statement.execute();
			result = statement.getInt("pRezId");
			if (result > 0) {
				dogadjaj.setId(result);
			}
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
