package application.model.dao.mysql;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Iterator;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import application.model.dao.DAOFactory;
import application.model.dao.ZaposleniDAO;
import application.model.dao.ZaposlenjeDAO;
import application.model.dto.ZaposleniDTO;
import application.model.dto.ZaposleniTipDTO;
import application.model.dto.ZaposlenjeDTO;
import application.util.ConnectionPool;
import application.util.ErrorLogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySQLZaposleniDAO implements ZaposleniDAO {
	private static final String MY_SQL_SELECT_ALL = "select * from zaposleni z inner join osoba o on z.OSOBA_Id=o.Id where z.Aktivan=true";
	private static final String MY_SQL_INSERT = "{call dodaj_zaposlenog(?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String MY_SQL_SELECT_BY_ID = "select * from zaposleni z inner join osoba o on z.OSOBA_Id=o.Id where z.OSOBA_Id=? and z.Aktivan=?";
	private static final String MY_SQL_ADD = "insert into zaposleni values(true, ?)";
	private static final String MY_SQL_DELETE = "update zaposleni set Aktivan=? where OSOBA_Id=?";
	public static final int DUPLICATE_KEY = -2;

	@Override
	public ObservableList<ZaposleniDTO> selectAll() {
		ObservableList<ZaposleniDTO> result = FXCollections.observableArrayList();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(MY_SQL_SELECT_ALL);
			ZaposlenjeDAO zDAO = DAOFactory.getDAOFactory().getZaposlenjeDAO();
			while (resultSet.next()) {
				result.add(new ZaposleniDTO(resultSet.getInt("Id"), resultSet.getString("Ime"),
						resultSet.getString("Prezime"), resultSet.getString("ImeRoditelja"), resultSet.getString("JMB"),
						resultSet.getString("Pol").charAt(0), resultSet.getDate("DatumRodjenja"),
						resultSet.getBlob("Fotografija"),
						DAOFactory.getDAOFactory().getOsobaDAO().getTelefoni(resultSet.getInt("Id")),
						resultSet.getBoolean("Aktivan"), zDAO.selectAllById(resultSet.getInt("Id"))));
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
	public Integer insert(ZaposleniDTO zaposleni, ZaposlenjeDTO zaposlenje, ZaposleniTipDTO zaposleniTip) {
		Integer id = -1;
		Connection connection = null;
		CallableStatement statement = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = connection.prepareCall(MY_SQL_INSERT);
			statement.setString("pJmb", zaposleni.getJmb());
			statement.setString("pIme", zaposleni.getIme());
			statement.setString("pPrezime", zaposleni.getPrezime());
			statement.setString("pIme_roditelja", zaposleni.getImeRoditelja());
			char[] pol = { zaposleni.getPol() };
			statement.setString("pPol", new String(pol));
			statement.setDate("pDatum_rodjenja", new Date(zaposleni.getDatumRodjenja().getTime()));
			if (zaposleni.getSlika() == null) {
				statement.setNull("pFotografija", Types.BLOB);
			} else {
				statement.setBlob("pFotografija", zaposleni.getSlika());
			}
			statement.setInt("pZaposleni_tip_id", zaposleniTip.getId());
			statement.setDate("pDatum_od", new Date(zaposlenje.getDatumOd().getTime()));
			if (zaposlenje.getDatumDo() == null) {
				statement.setNull("pDatum_do", Types.DATE);
			} else {
				statement.setDate("pDatum_do", new Date(zaposlenje.getDatumDo().getTime()));
			}
			statement.setBigDecimal("pPlata", new BigDecimal(zaposlenje.getPlata()));
			statement.registerOutParameter("pId", Types.INTEGER);
			statement.execute();
			id = statement.getInt("pId");
			if (!id.equals(Integer.valueOf(-1))) {
				zaposleni.setId(id);
			}
		} catch (SQLException e) {
			if (e instanceof MySQLIntegrityConstraintViolationException) {
				id = DUPLICATE_KEY;
			}
			// e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(connection);
			ConnectionPool.close(statement);
		}
		return id;
	}

	@Override
	public ObservableList<ZaposleniDTO> selectAktivni(boolean aktivan) {
		ObservableList<ZaposleniDTO> result = selectAll();
		for (Iterator<ZaposleniDTO> it = result.iterator(); it.hasNext();) {
			if ((aktivan && !it.next().isAktivan()) || (!aktivan && it.next().isAktivan())) {
				it.remove();
			}
		}
		return result;
	}

	@Override
	public ZaposleniDTO selectById(Integer id, boolean aktivan) {
		ZaposleniDTO result = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = ConnectionPool.prepareStatement(connection, MY_SQL_SELECT_BY_ID, false, id, aktivan);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				result = new ZaposleniDTO(resultSet.getInt("Id"), resultSet.getString("Ime"),
						resultSet.getString("Prezime"), resultSet.getString("ImeRoditelja"), resultSet.getString("JMB"),
						resultSet.getString("Pol").charAt(0), resultSet.getDate("DatumRodjenja"),
						resultSet.getBlob("Fotografija"),
						DAOFactory.getDAOFactory().getOsobaDAO().getTelefoni(resultSet.getInt("Id")),
						resultSet.getBoolean("Aktivan"),
						DAOFactory.getDAOFactory().getZaposlenjeDAO().selectAllById(resultSet.getInt("Id")));
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
	public boolean add(ZaposleniDTO zaposleni) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = ConnectionPool.prepareStatement(connection, MY_SQL_ADD, false, zaposleni.getId());
			statement.executeUpdate();
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
	public boolean delete(ZaposleniDTO zaposleni, boolean aktivan) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = ConnectionPool.prepareStatement(connection, MY_SQL_DELETE, false, aktivan, zaposleni.getId());
			statement.executeUpdate();
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
}
