package application.model.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import application.model.dto.OsobaDTO;
import application.util.ConnectionPool;

public class OsobaDAO {

	private static final String SQL_GET_BY_JMB = "SELECT * FROM OSOBA WHERE JMB=?";
	private static final String SQL_GET_TELEFON = "SELECT BrojTelefona FROM TELEFON WHERE OSOBA_Id=?";
	private static final String SQL_INSERT = "INSERT INTO OSOBA VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_INSERT_TELEFON = "INSERT INTO TELEFON VALUES (?, ?, ?)";
	private static final String SQL_SELECT_IDENTITY = "SELECT @@IDENTITY";
	private static final String SQL_GET_ID_BY_TELEFON = "SELECT OSOBA_ID FROM TELEFON WHERE BROJTELEFONA=?";
	private static final String SQL_DELETE_TELEFON = "DELETE FROM TELEFON WHERE OSOBA_ID=?";
	private static final String SQL_UPDATE = "UPDATE OSOBA SET IME=?, PREZIME=?, IMERODITELJA=?, POL=?, JMB=?, DATUMRODJENJA=?, FOTOGRAFIJA=? WHERE ID=?";
	private static final String SQL_SELECT_ALL_TIPOVI_POTVRDE = "SELECT * FROM POTVRDA_TIP";
	private static final String SQL_INSERT_POTVRDA = "INSERT INTO potvrda VALUES (?, ?, ?, ?, ?)";
	private final static String SQL_DOES_EXIST="{call postojiJmb(?,?,?,?)}";
	
	
	public static void insertPotvrda(int idClana, int idTipa, Date datum, Blob tekst) {
		PreparedStatement ps = null;
		Connection c = null;
	

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_INSERT_POTVRDA;
			Object pom[] = { null, idClana, idTipa, datum, tekst };

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
	
	public static List<String> getTipoviPotvrde() {
		List<String> retVal = new ArrayList<String>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_SELECT_ALL_TIPOVI_POTVRDE;
			Object pom[] = { };

			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			rs = ps.executeQuery();
			while (rs.next())
				retVal.add(rs.getString("Tip"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}

		return retVal;
	}
	
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
			if (osoba != null)
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

	public static int getIdByTelefon(String telefon) {
		int id = 0;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_GET_ID_BY_TELEFON;
			Object pom[] = { telefon };

			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			rs = ps.executeQuery();
			while (rs.next())
				id = rs.getInt("OSOBA_Id");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}

		return id;
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
			ps.close();//OVO JE DODANO JER DOLAZI DO CURENJA, DZOKS
			ps = ConnectionPool.prepareStatement(c, SQL_SELECT_IDENTITY, false, temp);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				osoba.setId(rs.getInt("@@identity"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
	}
	
	public static void update(OsobaDTO osoba) {
		PreparedStatement ps = null;
		Connection c = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_UPDATE;
			Object pom[] = { osoba.getIme(), osoba.getPrezime(), osoba.getImeRoditelja(),
					osoba.getPol().toString(), osoba.getJmb(), osoba.getDatumRodjenja(), osoba.getSlika(), osoba.getId() };

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
			for (String tel : osoba.getTelefoni()) {
				Object pom[] = { tel, osoba.getId(), null };
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

	public static void deleteTelefon(int id) {
		PreparedStatement ps = null;
		Connection c = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_DELETE_TELEFON;

			Object pom[] = { id };
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
	
	public static boolean doesExist(String jmb,Integer idTurnira,Integer idKategorije) {
		boolean retVal=false;
		Connection c = null;
		java.sql.CallableStatement cst=null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_DOES_EXIST;
			cst=c.prepareCall(query);
			cst.setString(1, jmb);
			cst.setInt(2, idTurnira);
			cst.setInt(3, idKategorije);
			cst.registerOutParameter(4, Types.BOOLEAN);
			cst.execute();
			retVal=cst.getBoolean(4);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionPool.close(cst);
			ConnectionPool.getInstance().checkIn(c);
		}
		return retVal;
	}
	
}