package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import application.model.dao.OpremaDAO;
import application.model.dto.Oprema;
import application.util.ConnectionPool;
import application.util.ErrorLogger;

public class MySQLOpremaDAO implements OpremaDAO {

	private final static String SQL_UPDATE = "UPDATE oprema SET OPREMA_TIP_Id=?, Velicina=? WHERE Id=?";
	private final static String SQL_UPDATE_OBRISAN = "UPDATE oprema SET Obrisan=true WHERE Id=?";

	public void UPDATE(Oprema oprema, String velicina, Integer idOpreme) {
		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_UPDATE, false, idOpreme, velicina, oprema.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(ps);
		}
	}

	public void UPDATE_OBRISAN(Oprema oprema) {
		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_UPDATE_OBRISAN, false, oprema.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(ps);
		}
	}
}
