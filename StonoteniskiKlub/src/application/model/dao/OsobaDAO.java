package application.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.model.dto.OsobaDTO;
import application.util.ConnectionPool;

public class OsobaDAO {

	private static final String SQL_GET_BY_JMB = "SELECT * FROM OSOBA WHERE JMB=?";
	private static final String SQL_GET_TELEFON = "SELECT BrojTelefona FROM TELEFON WHERE OSOBA_Id=?";
	private static final String SQL_INSERT = "INSERT INTO OSOBA VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_INSERT_TELEFON = "INSERT INTO TELEFON VALUES (?, ?, ?, ?)";
	private static final String SQL_SELECT_IDENTITY = "SELECT @@IDENTITY";

	public static OsobaDTO getByJmb(String jmb) {
		OsobaDTO osoba = null;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_GET_BY_JMB;
			Object pom[] = { jmb };

			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			rs = ps.executeQuery();
			while (rs.next())
				osoba = new OsobaDTO(rs.getInt("Id"), rs.getString("Ime"), rs.getString("Prezime"),
						rs.getString("ImeRoditelja"), rs.getString("JMB"), rs.getString("Pol").charAt(0),
						rs.getDate("DatumRodjenja"), rs.getBlob("Fotografija"), null);
			if(osoba != null)
				osoba.setTelefoni(getTelefoni(osoba.getId()));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		
		return osoba;
	}

	public static List<String> getTelefoni(int idOsobe) {
		List<String> retVal = new ArrayList<String>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_GET_TELEFON;
			Object pom[] = { idOsobe };

			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			rs = ps.executeQuery();
			while (rs.next())
				retVal.add(rs.getString("BrojTelefona"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}

		return retVal;
	}

	public static void insert(OsobaDTO osoba) {
		PreparedStatement ps = null;
		Connection c = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_INSERT;
			Object pom[] = { null, osoba.getJmb(), osoba.getIme(), osoba.getImeRoditelja(), osoba.getPrezime(),
					osoba.getPol().toString(), osoba.getDatumRodjenja(), osoba.getSlika() };
			
			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			ps.executeUpdate();
			
			Object temp[] = {};
			
			ps = ConnectionPool.prepareStatement(c, SQL_SELECT_IDENTITY, false, temp);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
				osoba.setId(rs.getInt("@@identity"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
	}
	
	public static void insertSaTelefonom(OsobaDTO osoba) {
		insert(osoba);
		insertTelefon(osoba);
	}
	
	public static void insertTelefon(OsobaDTO osoba) {
		PreparedStatement ps = null;
		Connection c = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_INSERT_TELEFON;
			for(String tel : osoba.getTelefoni()) {
				Object pom[] = { tel, osoba.getId(), null, null };
				ps = ConnectionPool.prepareStatement(c, query, false, pom);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
	}
}
