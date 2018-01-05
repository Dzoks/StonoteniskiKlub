package application.model.dao.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import application.model.dao.UgovorDAO;
import application.model.dto.SponzorDTO;
import application.model.dto.UgovorDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySQLUgovorDAO implements UgovorDAO {

	public static final String SQL_SELECT_ALL_BY_ID = "select * from UGOVOR_SPONZOR where SPONZOR_Id=?";
	public static final String SQL_SELECT_ONE = "select * from UGOVOR_SPONZOR where SPONZOR_Id=? and RedniBroj=?";
	public static final String SQL_INSERT = "{call dodaj_sponzorski_ugovor(?,?,?,?,?)}";
	
	
	@Override
	public ObservableList<UgovorDTO> selectAllById(Integer idSponzora) {
		ObservableList<UgovorDTO> result = FXCollections.observableArrayList();
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

	@Override
	public boolean insert(SponzorDTO sponzor, UgovorDTO ugovor) {
		boolean result = false;
		Connection connection = null;
		CallableStatement statement = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = connection.prepareCall(SQL_INSERT);
			statement.setInt("pSponzorId", sponzor.getId());
			statement.setDate("pDatumOd", new java.sql.Date(ugovor.getDatumOd().getTime()));
			statement.setString("pOpis", ugovor.getOpis());
			if (ugovor.getDatumDo() == null) {
				statement.setNull("pDatumDo", Types.DATE);
			} else {
				statement.setDate("pDatumDo", new Date(ugovor.getDatumDo().getTime()));
			}
			statement.registerOutParameter("pUspjesno", Types.BOOLEAN);
			statement.execute();
			result = statement.getBoolean("pUspjesno");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().checkIn(connection);
			ConnectionPool.close(statement);
		}
		return result;
	}

}
