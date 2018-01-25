package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.model.dao.TransakcijaDAO;
import application.model.dto.TipTransakcijeDTO;
import application.model.dto.TransakcijaDTO;
import application.util.ConnectionPool;
import application.util.ErrorLogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MySQLTransakcijaDAO implements TransakcijaDAO {
	private static final String SQL_SELECT_ALL = "select * from prikaz_transakcija";
	private static final String SQL_INSERT = "insert into TRANSAKCIJA values (null,?,?,?,?,?,false)";
	private static final String SQL_UPDATE = "update TRANSAKCIJA set Datum=?, Iznos=?, Opis=?, TIP_TRANSAKCIJE_Id=?, jeUplata=? where Id=?";
	private static final String SQL_DELETE = "update TRANSAKCIJA set Obrisan=true where Id=?";

	public ObservableList<TransakcijaDTO> SELECT_ALL() {
		ObservableList<TransakcijaDTO> listaTransakcija = FXCollections.observableArrayList();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_ALL);
			while (rs.next()) {
				listaTransakcija.add(new TransakcijaDTO(rs.getInt("Id"), rs.getDate("Datum"), rs.getDouble("Iznos"),
						rs.getString("Opis"), rs.getString("Tip"), rs.getBoolean("jeUplata")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, s);
		}

		return listaTransakcija;
	}

	public int INSERT(TransakcijaDTO transakcija, TipTransakcijeDTO tip) { // radi,
																			// vidjeti
																			// povratnu
																			// vrijednost
		// da li je uspio insert

		Connection c = null;
		java.sql.PreparedStatement ps = null;
		int id = 0;
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_INSERT;
			Object pom[] = { new java.sql.Date(transakcija.getDatum().getTime()), transakcija.getIznos().doubleValue(),
					transakcija.getOpis().getValue(), transakcija.getJeUplata(), tip.getId() };
			ps = ConnectionPool.prepareStatement(c, query, true, pom);

			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
			}
			transakcija.setId(id);
		}catch (SQLException e) {
			Alert alert = new Alert(AlertType.INFORMATION, "Neuspjesno dodavanje");
			alert.showAndWait();
			return 0;
		} finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(ps);
		}
		return id;
	}

	public void UPDATE(TransakcijaDTO transakcija, TipTransakcijeDTO tip) {// ne
																			// radi
																			// nijedan
																			// update
																			// kod
																			// novih
		// objekata je ne znam id
		Connection c = null;
		java.sql.PreparedStatement ps = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_UPDATE;
			Object pom[] = { transakcija.getDatum(), transakcija.getIznos().doubleValue(),
					transakcija.getOpis().getValue(), tip.getId(), transakcija.getJeUplata(), transakcija.getId() };
			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(ps);
		}
	}

	public void delete(int id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_DELETE;
			Object pom[] = { id };
			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, ps);
		}

	}
}
