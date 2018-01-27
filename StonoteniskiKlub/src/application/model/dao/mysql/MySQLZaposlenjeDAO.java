package application.model.dao.mysql;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import application.model.dao.DAOFactory;
import application.model.dao.ZaposleniTipDAO;
import application.model.dao.ZaposlenjeDAO;
import application.model.dto.ZaposleniDTO;
import application.model.dto.ZaposlenjeDTO;
import application.util.ConnectionPool;
import application.util.ErrorLogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySQLZaposlenjeDAO implements ZaposlenjeDAO {

	private static final String SQL_GET_BY_ID = "select * from ZAPOSLENJE where ZAPOSLENI_OSOBA_Id=? and Aktivan=true";
	private static final String SQL_INSERT = "{call dodaj_zaposlenje(?,?,?,?,?,?)}";
	private static final String SQL_UPDATE = "update ZAPOSLENJE set DatumDo=? where ZAPOSLENI_OSOBA_Id=? and ZAPOSLENI_TIP_Id=? and DatumOd=?";
	private static final String MY_SQL_DELETE = "update ZAPOSLENJE set Aktivan=false where ZAPOSLENI_OSOBA_Id=? and ZAPOSLENI_TIP_Id=? and DatumOd=?";
	@Override
	public ObservableList<ZaposlenjeDTO> selectAllById(Integer id) {
		ObservableList<ZaposlenjeDTO> result = FXCollections.observableArrayList();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = ConnectionPool.prepareStatement(connection, SQL_GET_BY_ID, false, id);
			resultSet = statement.executeQuery();
			ZaposleniTipDAO ztDAO = DAOFactory.getDAOFactory().getZaposleniTipDAO();
			while (resultSet.next()) {
				result.add(new ZaposlenjeDTO(resultSet.getInt("ZAPOSLENI_TIP_Id"),
						ztDAO.getById(resultSet.getInt("ZAPOSLENI_TIP_Id")).getTip(), resultSet.getDate("DatumOd"),
						resultSet.getDate("DatumDo"), resultSet.getDouble("Plata")));
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
	public boolean insert(ZaposleniDTO zaposleni, ZaposlenjeDTO zaposlenje) {
		boolean result = false;
		Connection connection = null;
		CallableStatement statement = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = connection.prepareCall(SQL_INSERT);
			statement.setInt("pZaposleni_tip_id", zaposlenje.getTipID());
			statement.setInt("pOsoba_id", zaposleni.getId());
			statement.setDate("pDatum_od", new Date(zaposlenje.getDatumOd().getTime()));
			if (zaposlenje.getDatumDo() == null) {
				statement.setNull("pDatum_do", Types.DATE);
			} else {
				statement.setDate("pDatum_do", new Date(zaposlenje.getDatumDo().getTime()));
			}
			statement.setBigDecimal("pPlata", new BigDecimal(zaposlenje.getPlata()));
			statement.registerOutParameter("pUspjesno", Types.BOOLEAN);
			statement.execute();
			result = statement.getBoolean("pUspjesno");
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
	public boolean zakljuci(ZaposleniDTO zaposleni, ZaposlenjeDTO zaposlenje) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = ConnectionPool.prepareStatement(connection, SQL_UPDATE, false, zaposlenje.getDatumDo(), zaposleni.getId(), zaposlenje.getTipID(), zaposlenje.getDatumOd());
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
	public boolean delete(ZaposleniDTO zaposleni, ZaposlenjeDTO zaposlenje) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = ConnectionPool.prepareStatement(connection, MY_SQL_DELETE, false, zaposleni.getId(), zaposlenje.getTipID(), zaposlenje.getDatumOd());
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
