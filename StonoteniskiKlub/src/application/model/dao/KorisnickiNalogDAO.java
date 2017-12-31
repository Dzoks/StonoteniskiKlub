package application.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.model.dto.Clan;
import application.model.dto.KorisnickiNalogDTO;
import application.util.ConnectionPool;

public class KorisnickiNalogDAO {
	private final static String SQL_GET_BY_ID = "SELECT * FROM KORISNICKI_NALOG c left JOIN OSOBA o on c.ZAPOSLENI_Id=o.Id where c.ZAPOSLENI_Id=1";
	private final static String SQL_UPDATE_AKTIVAN = "UPDATE KORISNICKI_NALOG SET Aktivan=? WHERE Zaposleni_Id=?";
	private final static String SQL_INSERT = "INSERT INTO KORISNICKI_NALOG VALUES (?, ?, ?, ?, ?, ?, ?)";
	private final static String SQL_SELECT_NALOG="select * from KORISNICKI_NALOG";
	
	private final static String SQL_GET_LOZINKA="select LozinkaHash from korisnicki_nalog where KorisnickoIme=?";

	private final static String SQL_SELECT_KORISNICKO_IME="select KorisnickoIme from korisnicki_nalog where KorisnickoIme=?";
	private final static String SQL_SELECT_ALL="SELECT * FROM KORISNICKI_NALOG k  left join  OSOBA o on ZAPOSLENI_Id=o.Id inner join KORISNICKI_NALOG_TIP t where ULOGA_Id=t.Id and Aktivan=true";
			
	

	public static KorisnickiNalogDTO getById(int id) {
		KorisnickiNalogDTO korisnickiNalog = null;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_GET_BY_ID;
			Object pom[] = { id };
			
			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			rs = ps.executeQuery();
			while (rs.next()) {
				rs.getInt("Id");
				korisnickiNalog= new KorisnickiNalogDTO( rs.getString("KorisnickoIme"), rs.getBytes("LozinkaHash"), rs.getDate("DatumRegistracije"), 
						rs.getBoolean("Aktivan"), rs.getInt("UlogaId"),rs.getInt("ZaposleniId"));}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}

		return korisnickiNalog;
	}
	
	public static void setAktivan(boolean flag, int korisnickiNalogId) {
		PreparedStatement ps = null;
		Connection c = null;
		
		try {
			c= ConnectionPool.getInstance().checkOut();
			String query = SQL_UPDATE_AKTIVAN;
			Object pom[] = { flag, korisnickiNalogId };
			
			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
	}
	
	public static boolean daLiPostoji(String korisnickoIme) {
		boolean daLiPostoji=false;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_SELECT_KORISNICKO_IME;
			Object pom[] = { korisnickoIme };
			
			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			rs = ps.executeQuery();
			daLiPostoji=rs.next();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}

		return daLiPostoji;
	}
	public static boolean daLiPostojiLozinka(String korisnickoIme) {
		boolean daLiPostoji=false;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_GET_LOZINKA;
			Object pom[] = { korisnickoIme };
			
			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			rs = ps.executeQuery();
			daLiPostoji=rs.next();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}

		return daLiPostoji;
	}
	
	public static void insert(KorisnickiNalogDTO nalog) {
		PreparedStatement ps = null;
		Connection c = null;
		
		try {
			c= ConnectionPool.getInstance().checkOut();
			String query = SQL_INSERT;
			Object pom[] = { null,nalog.getKorisnickoIme(),nalog.getLozinkaHash(),nalog.getDatumRegistracije(),
					nalog.getAktivan(),nalog.getUlogaId(),nalog.getZaposleniId()};
			
			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
	}

}
