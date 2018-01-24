package application.model.dao.mysql;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

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
	private static final String SQL_SELECT_ALL = "select * from zaposleni z inner join osoba o on z.OSOBA_Id=o.Id";
	private static final String SQL_SELECT_ALL_AKTIVNI = "select * from AKTIVNI_ZAPOSLENI";
	private static final String SQL_INSERT = "{call dodaj_zaposlenog(?,?,?,?,?,?,?,?,?,?,?,?)}";

	@Override
	public ObservableList<ZaposleniDTO> selectAll() {
		ObservableList<ZaposleniDTO> result = FXCollections.observableArrayList();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(SQL_SELECT_ALL);
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
	public boolean insert(ZaposleniDTO zaposleni, ZaposlenjeDTO zaposlenje, ZaposleniTipDTO zaposleniTip) {
		Integer id = -1;
		boolean result = false;
		Connection connection = null;
		CallableStatement statement = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = connection.prepareCall(SQL_INSERT);
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
				statement.setDate("pDatum_do", new Date(zaposlenje.getDatumOd().getTime()));
			}
			statement.setBigDecimal("pPlata", new BigDecimal(zaposlenje.getPlata()));
			statement.registerOutParameter("pId", Types.INTEGER);
			statement.execute();
			id = statement.getInt("pId");
			if (!id.equals(Integer.valueOf(-1))) {
				result = true;
				zaposleni.setId(id);
			}
			System.out.println(id);
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
	public ObservableList<ZaposleniDTO> selectAktivni() {
		ObservableList<ZaposleniDTO> result = FXCollections.observableArrayList();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(SQL_SELECT_ALL_AKTIVNI);
			ZaposlenjeDAO zDAO = DAOFactory.getDAOFactory().getZaposlenjeDAO();
			while (resultSet.next()) {
				result.add(new ZaposleniDTO(resultSet.getInt("Id"), resultSet.getString("Ime"),
						resultSet.getString("Prezime"), resultSet.getString("ImeRoditelja"), resultSet.getString("JMB"),
						resultSet.getString("Pol").charAt(0), resultSet.getDate("DatumRodjenja"),
						resultSet.getBlob("Fotografija"),
						DAOFactory.getDAOFactory().getOsobaDAO().getTelefoni(resultSet.getInt("Id")), true,
						zDAO.selectAllById(resultSet.getInt("Id"))));
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

}
