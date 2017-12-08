package application.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.model.dto.ClanDTO;
import application.util.ConnectionPool;

public class ClanDAO {
	
	private final static String SQL_GET_BY_ID = "SELECT * FROM CLAN c left JOIN OSOBA o on c.OSOBA_ID=o.ID where c.OSOBA_ID=?";
	private final static String SQL_UPDATE_AKTIVAN = "UPDATE CLAN SET Aktivan=? WHERE OSOBA_Id=?";
	private final static String SQL_INSERT = "INSERT INTO CLAN VALUES (?, ?, ?)";
	private final static String SQL_SELECT_ALL = "SELECT * FROM CLAN c INNER JOIN OSOBA o ON c.OSOBA_ID = o.ID";
	
	
	public static List<ClanDTO> selectAllByImePrezime(String ime, String prezime) {
		ArrayList<ClanDTO> list = new ArrayList<>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_SELECT_ALL + " WHERE ";
			if(!"".equals(ime)) {
			 query += "o.IME LIKE '%"+ime+"%'";
			 if(!"".equals(prezime))
				 query += " AND o.PREZIME LIKE '%"+prezime+"%'";
			}
			else {
				query += "o.PREZIME LIKE '%"+prezime+"%'";
			}
			
			Object pom[] = { };
			
			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			rs = ps.executeQuery();
			while (rs.next()) {
				ClanDTO clan = new ClanDTO(rs.getInt("Id"), rs.getString("Ime"), rs.getString("Prezime"), rs.getString("ImeRoditelja"), 
						rs.getString("JMB"), rs.getString("Pol").charAt(0), rs.getDate("DatumRodjenja"), rs.getBlob("Fotografija"), null,
						rs.getBoolean("Aktivan"), rs.getBoolean("Registrovan"));
				list.add(clan);
				clan.setTelefoni(OsobaDAO.getTelefoni(clan.getId()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}

		return list;
	}
	
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
	
	public static List<ClanDTO> selectAll() {
		ArrayList<ClanDTO> list = new ArrayList<>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_SELECT_ALL;
			Object pom[] = { };
			
			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			rs = ps.executeQuery();
			while (rs.next()) {
				ClanDTO clan = new ClanDTO(rs.getInt("Id"), rs.getString("Ime"), rs.getString("Prezime"), rs.getString("ImeRoditelja"), 
						rs.getString("JMB"), rs.getString("Pol").charAt(0), rs.getDate("DatumRodjenja"), rs.getBlob("Fotografija"), null,
						rs.getBoolean("Aktivan"), rs.getBoolean("Registrovan"));
				list.add(clan);
				clan.setTelefoni(OsobaDAO.getTelefoni(clan.getId()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}

		return list;
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
