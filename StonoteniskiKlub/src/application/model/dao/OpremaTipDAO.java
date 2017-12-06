package application.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.model.dto.OpremaTipDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OpremaTipDAO {

	private static final String SQL_SELECT_ALL = "SELECT * FROM oprema_tip";
	private static final String SQL_SELECT_TIP = "SELECT Tip FROM oprema_tip WHERE Id=?";
	private static final String SQL_SELECT_PROIZVODJAC = "SELECT Proizvodjac FROM oprema_tip WHERE Id=?";
	private static final String SQL_SELECT_MODEL = "SELECT Model FROM oprema_tip WHERE Id=?";
	private static final String SQL_INSERT = "INSERT INTO oprema_tip VALUES (null, ?, ?, ?)";
	
	public static ObservableList<OpremaTipDTO> SELECT_ALL() {
		ObservableList<OpremaTipDTO> listaTipovaOprema = FXCollections.observableArrayList();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_ALL);
			
			while(rs.next()) {
				listaTipovaOprema.add(new OpremaTipDTO(rs.getInt("Id"), rs.getString("Tip"), rs.getString("Proizvodjac"), rs.getString("Model")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, s);
		}
		
		return listaTipovaOprema;
	}
	
	public static void INSERT(OpremaTipDTO opremaTip) {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_INSERT, false, opremaTip.getTip() , opremaTip.getProizvodjac(), opremaTip.getModel());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(ps);
		}
	}
	
	public static String SELECT_TIP(Integer id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String rezultat = "";
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_SELECT_TIP, false, id);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				rezultat = rs.getString("Tip");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, ps);
		}
		
		return rezultat;
	}
	
	public static String SELECT_PROIZVODJAC(Integer id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String rezultat = "";
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_SELECT_PROIZVODJAC, false, id);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				rezultat = rs.getString("Proizvodjac");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, ps);
		}
		
		return rezultat;
	}
	
	public static String SELECT_MODEL(Integer id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String rezultat = "";
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_SELECT_MODEL, false, id);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				rezultat = rs.getString("Model");
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
