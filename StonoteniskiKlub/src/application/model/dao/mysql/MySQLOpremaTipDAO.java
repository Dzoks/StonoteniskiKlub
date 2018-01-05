package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.model.dao.OpremaTipDAO;
import application.model.dto.OpremaTip;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySQLOpremaTipDAO implements OpremaTipDAO{

	private static final String SQL_SELECT_ALL = "SELECT * FROM oprema_tip";
	private static final String SQL_SELECT_TIP = "SELECT Tip FROM oprema_tip WHERE Id=?";
	private static final String SQL_SELECT_PROIZVODJAC = "SELECT Proizvodjac FROM oprema_tip WHERE Id=?";
	private static final String SQL_SELECT_MODEL = "SELECT Model FROM oprema_tip WHERE Id=?";
	private static final String SQL_SELECT_IMA_LI_VELICINU = "SELECT ImaVelicinu FROM oprema_tip WHERE Id=?";
	private static final String SQL_INSERT = "INSERT INTO oprema_tip VALUES (null, ?, ?, ?, ?)";
	
	public ObservableList<OpremaTip> SELECT_ALL() {
		ObservableList<OpremaTip> listaTipovaOprema = FXCollections.observableArrayList();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_ALL);
			
			while(rs.next()) {
				listaTipovaOprema.add(new OpremaTip(rs.getInt("Id"), rs.getString("Tip"), rs.getString("Proizvodjac"), rs.getString("Model"), rs.getBoolean("ImaVelicinu")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, s);
		}
		
		return listaTipovaOprema;
	}
	
	public void INSERT(OpremaTip opremaTip) {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_INSERT, false, opremaTip.getTip() , opremaTip.getProizvodjac(), opremaTip.getModel(), opremaTip.getImaVelicinu());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(ps);
		}
	}
	
	public String SELECT_TIP(Integer id) {
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
	
	public String SELECT_PROIZVODJAC(Integer id) {
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
	
	public String SELECT_MODEL(Integer id) {
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
	
	public Boolean SELECT_IMA_LI_VELICINU(Integer id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Boolean rezultat = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_SELECT_IMA_LI_VELICINU, false, id);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				rezultat = rs.getBoolean("ImaVelicinu");
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
