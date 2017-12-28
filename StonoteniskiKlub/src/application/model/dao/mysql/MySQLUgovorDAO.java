package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.model.dao.UgovorDAO;
import application.model.dto.UgovorDTO;
import application.util.ConnectionPool;

public class MySQLUgovorDAO implements UgovorDAO {

	public static final String SQL_SELECT_ALL_BY_ID = "select * from UGOVOR_SPONZOR where SPONZOR_Id=?";
	public static final String SQL_SELECT_ONE = "select * from UGOVOR_SPONZOR where SPONZOR_Id=? and RedniBroj=?";

	@Override
	public List<UgovorDTO> selectAllById(Integer idSponzora) {
		List<UgovorDTO> result = new ArrayList<UgovorDTO>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = ConnectionPool.prepareStatement(connection, SQL_SELECT_ALL_BY_ID, false, idSponzora);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				result.add(new UgovorDTO(resultSet.getInt("RedniBroj"), resultSet.getDate("DatumOd"),
						resultSet.getDate("DatumDo"), resultSet.getString("Opis"), null));
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
	public UgovorDTO selectOne(Integer idSponzora, Integer redniBroj) {
		UgovorDTO result = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = ConnectionPool.prepareStatement(connection, SQL_SELECT_ONE, false, idSponzora, redniBroj);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				result = new UgovorDTO(resultSet.getInt("RedniBroj"), resultSet.getDate("DatumOd"),
						resultSet.getDate("DatumDo"), resultSet.getString("Opis"), null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().checkIn(connection);
			ConnectionPool.close(resultSet, statement);
		}
		return result;
	}

}
