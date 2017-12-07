package application.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import application.model.dto.ClanDTO;
import application.util.ConnectionPool;

public class ClanDAO {
	
	private final static String SQL_GET_BY_ID = "SELECT * FROM CLAN c left JOIN OSOBA o on c.OSOBA_ID=o.ID where c.OSOBA_ID=?";
	private final static String SQL_UPDATE_AKTIVAN = "UPDATE CLAN SET Aktivan=? WHERE OSOBA_Id=?";
	private final static String SQL_INSERT = "INSERT INTO CLAN VALUES (?, ?, ?)";
	
	public static ClanDTO getById(int id) {
		ClanDTO clan = null;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_GET_BY_ID;
			Object pom[] = { id };
			
			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			rs = ps.executeQuery();
			while (rs.next())
				clan = new ClanDTO(rs.getInt("Id"), rs.getString("Ime"), rs.getString("Prezime"), rs.getString("ImeRoditelja"), 
						rs.getString("JMB"), rs.getString("Pol").charAt(0), rs.getDate("DatumRodjenja"), rs.getBlob("Fotografija"), null,
						rs.getBoolean("Aktivan"), rs.getBoolean("Registrovan"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}

		return clan;
	}
	
	public static void setAktivan(boolean flag, int clanId) {
		PreparedStatement ps = null;
		Connection c = null;
		
		try {
			c= ConnectionPool.getInstance().checkOut();
			String query = SQL_UPDATE_AKTIVAN;
			Object pom[] = { flag, clanId };
			
			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
	}
	
	public static void insert(ClanDTO clan) {
		PreparedStatement ps = null;
		Connection c = null;
		
		try {
			c= ConnectionPool.getInstance().checkOut();
			String query = SQL_INSERT;
			Object pom[] = { clan.isAktivan(), clan.isRegistrovan(), clan.getId()};
			
			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
	}
	
	public static void insertAll(ClanDTO clan) {
		OsobaDAO.insertSaTelefonom(clan);
		insert(clan);
	}
}
