package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.model.dao.NovcanaSredstvaDAO;
import application.model.dto.NovcanaSredstvaDTO;
import application.util.ConnectionPool;
import application.util.ErrorLogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MySQLNovcanaSredstvaDAO implements NovcanaSredstvaDAO {
	private static final String SQL_SELECT_SEZONA = "select all(Sezona) from NOVCANA_SREDSTVA";
	private static final String SQL_SELECT_MAX_ID = "SELECT * from NOVCANA_SREDSTVA ORDER BY id DESC LIMIT 1";
	private static final String SQL_SELECT_BY_SEZONA = "select * from NOVCANA_SREDSTVA where Sezona=?";
	private static final String SQL_INSERT = "{call dodaj_budzet(?,?)}";
	private static final String SQL_UPDATE_PRIHODI = "update NOVCANA_SREDSTVA set Prihodi=? where Id=?";
	private static final String SQL_UPDATE_RASHODI = "update NOVCANA_SREDSTVA set Rashodi=? where Id=?";

	public ObservableList<String> getSezone() {
		ObservableList<String> list = FXCollections.observableArrayList();
		Integer zadnji = 0;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_SELECT_SEZONA;
			Object pom[] = {};

			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("Sezona"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, ps);
		}
		return list;
	}

	public NovcanaSredstvaDTO getNSMaxId() {
		NovcanaSredstvaDTO ns = null;
		Integer zadnji = 0;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_SELECT_MAX_ID;
			Object pom[] = {};

			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			rs = ps.executeQuery();
			while (rs.next()) {
				ns = new NovcanaSredstvaDTO(rs.getInt("Id"), rs.getString("Sezona"), rs.getDouble("Budzet"),
						rs.getDouble("Prihodi"), rs.getDouble("Rashodi"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, ps);
		}
		return ns;
	}

	public NovcanaSredstvaDTO getBySezona(String sezona) {
		NovcanaSredstvaDTO ns = null;
		Integer zadnji = 0;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_SELECT_BY_SEZONA;
			Object pom[] = { sezona };

			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			rs = ps.executeQuery();
			while (rs.next()) {
				ns = new NovcanaSredstvaDTO(rs.getInt("Id"), rs.getString("Sezona"), rs.getDouble("Budzet"),
						rs.getDouble("Prihodi"), rs.getDouble("Rashodi"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, ps);
		}
		return ns;
	}

	public boolean INSERT(NovcanaSredstvaDTO ns) {// TREBA BITI BOOLEAN
		Connection c = null;
		java.sql.CallableStatement cs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			cs = c.prepareCall(SQL_INSERT);

			cs.setString("inSezona", ns.getSezona());
			cs.setDouble("inIznos", ns.getBudzet());
			try {
				cs.executeQuery();
			} catch (SQLException ex) {
				Alert alert = new Alert(AlertType.INFORMATION, ex.getMessage());
				alert.showAndWait();
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(cs);
		}
		return true;
	}

	public boolean dodajPrihode(double prihod) {
		NovcanaSredstvaDTO ns = getNSMaxId();
		if (ns != null) {
			Connection c = null;
			PreparedStatement ps = null;

			try {
				c = ConnectionPool.getInstance().checkOut();
				ps = ConnectionPool.prepareStatement(c, SQL_UPDATE_PRIHODI, false, ns.getPrihodi() + prihod,
						ns.getId());
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				new ErrorLogger().log(e);
			} finally {
				ConnectionPool.getInstance().checkIn(c);
				ConnectionPool.close(ps);
			}
			return true;
		} else {
			Alert alert = new Alert(AlertType.INFORMATION,
					"Nije moguce dodati transakciju, jer trenutni budzet nije unesen.");
			alert.showAndWait();
			return false;
		}

	}

	public boolean dodajRashode(double rashod) {
		NovcanaSredstvaDTO ns = getNSMaxId();
		if (ns != null) {
			Connection c = null;
			PreparedStatement ps = null;
			try {
				c = ConnectionPool.getInstance().checkOut();
				ps = ConnectionPool.prepareStatement(c, SQL_UPDATE_RASHODI, false, ns.getRashodi() + rashod,
						ns.getId());
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				new ErrorLogger().log(e);
			} finally {
				ConnectionPool.getInstance().checkIn(c);
				ConnectionPool.close(ps);
			}
			return true;
		} else {
			Alert alert = new Alert(AlertType.INFORMATION,
					"Nije moguce dodati transakciju, jer trenutni budzet nije unesen.");
			alert.showAndWait();
			return false;
		}
	}
}
