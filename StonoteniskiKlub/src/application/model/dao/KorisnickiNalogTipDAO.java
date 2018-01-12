package application.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.model.dto.KorisnickiNalogTipDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class KorisnickiNalogTipDAO {
	private static final String SQL_SELECT_ALL = "SELECT * FROM  KORISNICKI_NALOG_TIP";
	private static final String SQL_INSERT = "INSERT INTO  dzoksrs_db.KORISNICKI_NALOG_TIP VALUES (null, ?)";
	
	public static ObservableList<KorisnickiNalogTipDTO> SELECT_ALL() {
		ObservableList<KorisnickiNalogTipDTO> listaTipovaNaloga = FXCollections.observableArrayList();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_ALL);
			
			while(rs.next()) {
				listaTipovaNaloga.add(new KorisnickiNalogTipDTO(rs.getInt("Id"), rs.getString("Naziv")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, s);
		}
		
		return listaTipovaNaloga;
	}
	
	public static void insert(KorisnickiNalogTipDTO korisnickiNalogTip) {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_INSERT, false, korisnickiNalogTip.getNaziv());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(ps);
		}
	}	
}
