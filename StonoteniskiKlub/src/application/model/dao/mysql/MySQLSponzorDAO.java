package application.model.dao.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import application.model.dao.SponzorDAO;
import application.model.dto.SponzorDTO;
import application.model.dto.UgovorDTO;
import application.util.ConnectionPool;

public class MySQLSponzorDAO implements SponzorDAO {

	private static final String SQL_SELECT_ALL = "select * from SPONZOR";
	private static final String SQL_INSERT = "{call dodaj_sponozra(?,?,?,?,?,?,?)}";
	private static final String SQL_GET_BY_ID = "select * from SPONZOR where Id=?";

	@Override
	public List<SponzorDTO> selectAll() {
		List<SponzorDTO> result = new ArrayList<SponzorDTO>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(SQL_SELECT_ALL);
			while (resultSet.next()) {
				result.add(new SponzorDTO(resultSet.getInt("Id"), resultSet.getString("Naziv"),
						resultSet.getString("Adresa"), resultSet.getString("Mail"), null));
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
	public SponzorDTO getById(Integer id) {
		SponzorDTO result = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = ConnectionPool.prepareStatement(connection, SQL_GET_BY_ID, false, id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				result = new SponzorDTO(resultSet.getInt("Id"), resultSet.getString("Naziv"),
						resultSet.getString("Adresa"), resultSet.getString("Mail"), null);
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
			statement.setString("pNaziv", sponzor.getNaziv());
			statement.setString("pAdresa", sponzor.getAdresa());
			statement.setString("pMail", sponzor.getEmail());
			statement.setDate("pDatumOd", new Date(ugovor.getDatumOd().getTime()));
			if (ugovor.getDatumDo() == null) {
				statement.setNull("pDatumDo", Types.DATE);
			} else {
				statement.setDate("pDatumDo", new Date(ugovor.getDatumDo().getTime()));
			}
			statement.setString("pOpis", ugovor.getOpis());
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
