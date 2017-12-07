package application.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.model.dto.OpremaClanaDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OpremaClanaDAO {

	private static final String SQL_SELECT_ALL = "SELECT * FROM prikaz_opreme_clana";
	private static final String SQL_SELECT_JMB_CLAN = "SELECT JMB FROM prikaz_clana WHERE Id=?";
	private static final String SQL_SELECT_IME_CLAN = "SELECT ime FROM prikaz_clana WHERE Id=?";
	private static final String SQL_SELECT_PREZIME_CLAN = "SELECT prezime FROM prikaz_clana WHERE Id=?";
	private static final String SQL_SELECT_AKTIVAN_CLAN = "SELECT Aktivan FROM prikaz_clana WHERE Id=?";
	
	public static ObservableList<OpremaClanaDTO> SELECT_ALL() {
		ObservableList<OpremaClanaDTO> listaOpreme = FXCollections.observableArrayList();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_ALL);
			
			while(rs.next()) {
				listaOpreme.add(new OpremaClanaDTO(rs.getInt("Id"), rs.getInt("NARUDZBA_Id"), rs.getInt("OPREMA_TIP_Id"), rs.getInt("DONACIJA_Id"), rs.getBoolean("Donirana"), rs.getString("Velicina"), rs.getInt("CLAN_Id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, s);
		}
		
		return listaOpreme;
	}
	
	public static String SELECT_JMB_CLAN(Integer id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String rezultat = "";
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_SELECT_JMB_CLAN, false, id);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				rezultat = rs.getString("JMB");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, ps);
		}
		
		return rezultat;
	}
	
	public static String SELECT_IME_CLAN(Integer id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String rezultat = "";
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_SELECT_IME_CLAN, false, id);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				rezultat = rs.getString("ime");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, ps);
		}
		
		return rezultat;
	}
	
	public static String SELECT_PREZIME_CLAN(Integer id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String rezultat = "";
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_SELECT_PREZIME_CLAN, false, id);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				rezultat = rs.getString("prezime");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, ps);
		}
		
		return rezultat;
	}
	
	public static Boolean SELECT_AKTIVAN_CLAN(Integer id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Boolean rezultat = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_SELECT_AKTIVAN_CLAN, false, id);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				rezultat = rs.getBoolean("Aktivan");
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
