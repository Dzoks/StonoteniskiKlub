package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.model.dao.KategorijaTurniraDAO;
import application.model.dto.KategorijaTurniraDTO;
import application.util.ConnectionPool;
import application.util.ErrorLogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySQLKategorijaTurniraDAO implements KategorijaTurniraDAO {
	private final static String SQL_GET_ALL = "select * from TURNIR_KATEGORIJA";
	private final static String SQL_GET_BY_ID = "select * from TURNIR_KATEGORIJA where Id=?";

	public ObservableList<KategorijaTurniraDTO> getAll() {
		ObservableList<KategorijaTurniraDTO> retVal = FXCollections.observableArrayList();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_GET_ALL;

			ps = ConnectionPool.prepareStatement(c, query, false);
			rs = ps.executeQuery();
			while (rs.next())
				retVal.add(new KategorijaTurniraDTO(rs.getInt("Id"), rs.getString("Kategorija")));
		} catch (SQLException e) {
			 ;
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return retVal;
	}

	public KategorijaTurniraDTO getById(Integer id) {
		KategorijaTurniraDTO retVal = new KategorijaTurniraDTO();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_GET_BY_ID;
			Object pom[] = { id };

			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			rs = ps.executeQuery();
			if (rs.next()) {
				retVal = new KategorijaTurniraDTO(rs.getInt("Id"), rs.getString("Kategorija"));
			}
		} catch (SQLException e) {
			 ;
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return retVal;
	}
}
