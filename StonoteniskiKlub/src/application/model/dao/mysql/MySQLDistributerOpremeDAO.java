package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.model.dao.DistributerOpremeDAO;
import application.model.dto.DistributerOpreme;
import application.util.ConnectionPool;
import application.util.ErrorLogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySQLDistributerOpremeDAO implements DistributerOpremeDAO {

	private static final String SQL_SELECT_ALL = "SELECT * FROM distributer_opreme";
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM distributer_opreme WHERE Id=?";
	private static final String SQL_INSERT = "INSERT INTO distributer_opreme VALUES (null, ?, ?, ?, ?)";

	public ObservableList<DistributerOpreme> SELECT_ALL() {
		ObservableList<DistributerOpreme> listaDistributera = FXCollections.observableArrayList();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_ALL);

			while (rs.next()) {
				listaDistributera.add(new DistributerOpreme(rs.getInt("Id"), rs.getString("Naziv"),
						rs.getString("Telefon"), rs.getString("Adresa"), rs.getString("Mail")));
			}
		} catch (SQLException e) {
			 ;
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, s);
		}

		return listaDistributera;
	}

	public String SELECT_BY_ID(Integer id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String rezultat = "";

		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_SELECT_BY_ID, false, id);
			rs = ps.executeQuery();

			while (rs.next()) {
				rezultat = rs.getString("Naziv");
			}
		} catch (SQLException e) {
			 ;
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, ps);
		}

		return rezultat;
	}

	public void INSERT(DistributerOpreme distributerOpreme) {
		Connection c = null;
		PreparedStatement ps = null;

		try {
			if ("".equals(distributerOpreme.getAdresa())) {
				distributerOpreme.setAdresa(null);
			}

			if ("".equals(distributerOpreme.getTelefon())) {
				distributerOpreme.setTelefon(null);
			}

			if ("".equals(distributerOpreme.getMail())) {
				distributerOpreme.setMail(null);
			}

			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_INSERT, false, distributerOpreme.getNaziv(),
					distributerOpreme.getTelefon(), distributerOpreme.getAdresa(), distributerOpreme.getMail());
			ps.execute();
		} catch (SQLException e) {
			 ;
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(ps);
		}
	}
}
