package application.model.dao.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import application.model.dao.SponzorDAO;
import application.model.dto.SponzorDTO;
import application.model.dto.UgovorDTO;
import application.util.ConnectionPool;
import application.util.ErrorLogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySQLSponzorDAO implements SponzorDAO {

	private static final String SQL_SELECT_ALL = "select * from SPONZOR where Aktivan=true";
	private static final String SQL_INSERT = "{call dodaj_sponzora(?,?,?,?,?,?,?,?)}";
	private static final String SQL_GET_BY_ID = "select * from SPONZOR where Id=? and Aktivan=true";
	private static final String SQL_UPDATE = "update SPONZOR set Naziv=?, Adresa=?, Mail=? where Id=?";
	private static final String SQL_GET_BY_NAME = "select * from SPONZOR where Aktivan=true and Naziv like ?";
	private static final String SQL_GET_TELEFONI = "select * from TELEFON where SPONZOR_Id=?";
	private static final String SQL_DELETE_TELEFON = "delete from TELEFON where BrojTelefona=?";
	private static final String SQL_INSERT_TELEFON = "insert into TELEFON values(?,null,?)";
	private static final String SQL_DELETE = "update SPONZOR set Aktivan=false where Id=?";
	@Override
	public ObservableList<SponzorDTO> selectAll() {
		ObservableList<SponzorDTO> result = FXCollections.observableArrayList();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(SQL_SELECT_ALL);
			while (resultSet.next()) {
				result.add(new SponzorDTO(resultSet.getInt("Id"), resultSet.getString("Naziv"),
						resultSet.getString("Adresa"), resultSet.getString("Mail"), null, null));
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
						resultSet.getString("Adresa"), resultSet.getString("Mail"), null, null);
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
			statement.registerOutParameter("pId", Types.INTEGER);
			statement.registerOutParameter("pRedniBroj", Types.INTEGER);
			Integer id = -1;
			Integer redniBroj = -1;
			statement.execute();
			id = statement.getInt("pId");
			redniBroj = statement.getInt("pRedniBroj");
			if (!id.equals(Integer.valueOf(-1)) && !redniBroj.equals(Integer.valueOf(-1))) {
				sponzor.setId(id);
				ugovor.setRedniBroj(redniBroj);
				result = true;
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

	@Override
	public void update(SponzorDTO sponzor) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = ConnectionPool.prepareStatement(connection, SQL_UPDATE, false, sponzor.getNaziv(),
					sponzor.getAdresa(), sponzor.getEmail(), sponzor.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(connection);
			ConnectionPool.close(statement);
		}
	}

	@Override
	public ObservableList<SponzorDTO> getByNaziv(String naziv) {
		ObservableList<SponzorDTO> result = FXCollections.observableArrayList();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = ConnectionPool.prepareStatement(connection, SQL_GET_BY_NAME, false, "%" + naziv + "%");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				result.add(new SponzorDTO(resultSet.getInt("Id"), resultSet.getString("Naziv"),
						resultSet.getString("Adresa"), resultSet.getString("Mail"), null, null));
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
	public ObservableList<String> getTelefoni(SponzorDTO sponzor) {
		ObservableList<String> result = FXCollections.observableArrayList();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = ConnectionPool.prepareStatement(connection, SQL_GET_TELEFONI, false, sponzor.getId());
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				result.add(resultSet.getString("BrojTelefona"));
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
	public boolean deleteTelefon(String brojTelefona) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = ConnectionPool.prepareStatement(connection, SQL_DELETE_TELEFON, false, brojTelefona);
			statement.execute();
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

	@Override
	public boolean insertTelefon(SponzorDTO sponzor, String telefon) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = ConnectionPool.prepareStatement(connection, SQL_INSERT_TELEFON, true, telefon, sponzor.getId());
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				result = true;
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
	public boolean delete(SponzorDTO sponzor) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = ConnectionPool.prepareStatement(connection, SQL_DELETE, false, sponzor.getId());
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
