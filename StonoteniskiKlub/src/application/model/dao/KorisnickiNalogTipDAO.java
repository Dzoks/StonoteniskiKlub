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
	private static final String SQL_SELECT_ALL = "SELECT * FROM  korisnicki_nalog_tip";
	private static final String SQL_SELECT_ID = "SELECT Id FROM  dzoksrs_db.korisnicki_nalog_tip WHERE Naziv=?";
	private static final String SQL_INSERT = "INSERT INTO  dzoksrs_db.korisnicki_nalog_tip VALUES (null, ?)";
	
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
	
	public static Integer selectId(String naziv) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer rezultat=0;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_SELECT_ID, false, naziv);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				rezultat = rs.getInt("Id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, ps);
		}
		
		return rezultat;
	}
}
