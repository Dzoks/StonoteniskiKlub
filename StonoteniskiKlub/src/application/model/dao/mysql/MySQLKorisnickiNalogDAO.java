package application.model.dao.mysql;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.model.dao.KorisnickiNalogDAO;
import application.model.dto.KorisnickiNalogDTO;
import application.util.ConnectionPool;
import application.util.ErrorLogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySQLKorisnickiNalogDAO implements KorisnickiNalogDAO {
	private final static String SQL_GET_BY_ID = "SELECT * FROM dzoksrs_db.KORISNICKI_NALOG c left JOIN OSOBA o on c.ZAPOSLENI_Id=o.Id where c.ZAPOSLENI_Id=1";
	private final static String SQL_UPDATE_AKTIVAN = "UPDATE dzoksrs_db.KORISNICKI_NALOG SET Aktivan=? WHERE Id=?";
	private final static String SQL_INSERT = "INSERT INTO dzoksrs_db.KORISNICKI_NALOG VALUES (?, ?, ?, ?, ?, ?, ?)";
	private final static String SQL_SELECT_NALOG = "select * from dzoksrs_db.KORISNICKI_NALOG where Id=?";
	private final static String SQL_GET_BY_USERNAME = "select LozinkaHash from dzoksrs_db.KORISNICKI_NALOG where KorisnickoIme=?";
	private final static String SQL_GET_LOZINKA = "select LozinkaHash from dzoksrs_db.KORISNICKI_NALOG where KorisnickoIme=?";
	private final static String SQL_UPDATE_LOZINKA = "UPDATE dzoksrs_db.KORISNICKI_NALOG SET LozinkaHash=? WHERE KorisnickoIme=?";
	private final static String SQL_SELECT_KORISNICKO_IME = "select KorisnickoIme from dzoksrs_db.KORISNICKI_NALOG where KorisnickoIme=?";
	private final static String SQL_SELECT_ALL = "SELECT * FROM dzoksrs_db.KORISNICKI_NALOG k  left join  dzoksrs_db.OSOBA o on ZAPOSLENI_Id=o.Id inner join dzoksrs_db.KORISNICKI_NALOG_TIP t where ULOGA_Id=t.Id and Aktivan=true";
	private final static String SQL_SELECT_ULOGA = "SELECT Naziv FROM  dzoksrs_db.KORISNICKI_NALOG k inner join dzoksrs_db.KORISNICKI_NALOG_TIP  t on ULOGA_id=t.Id and KorisnickoIme=?";
	private final static String SQL_SELECT_ID = "select Id from korisnicki_nalog where KorisnickoIme=?";
	private final static String SQL_SELECT_NALOG_BY_ID = "select * from korisnicki_nalog where Id=?";
	public KorisnickiNalogDTO getById(int id) {
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
				korisnickiNalog = new KorisnickiNalogDTO(rs.getString("KorisnickoIme"), rs.getBytes("LozinkaHash"),
						rs.getDate("DatumRegistracije"), rs.getBoolean("Aktivan"), rs.getInt("UlogaId"),
						rs.getInt("ZaposleniId"));
			}
		} catch (SQLException e) {
			 ;
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}

		return korisnickiNalog;
	}

	public void setAktivan(int flag, int korisnickiNalogId) {
		PreparedStatement ps = null;
		Connection c = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_UPDATE_AKTIVAN;
			Object pom[] = { flag, korisnickiNalogId };

			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			ps.executeUpdate();
		} catch (SQLException e) {
			 ;
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
	}

	public void setLozinka(byte[] lozinkaHash, String korisnickoIme) {
		PreparedStatement ps = null;
		Connection c = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_UPDATE_LOZINKA;
			Object pom[] = { lozinkaHash, korisnickoIme };

			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			ps.executeUpdate();
		} catch (SQLException e) {
			 ;
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
	}

	public boolean daLiPostoji(String korisnickoIme) {
		boolean daLiPostoji = false;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_SELECT_KORISNICKO_IME;
			Object pom[] = { korisnickoIme };

			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			rs = ps.executeQuery();
			daLiPostoji = rs.next();

		} catch (SQLException e) {
			 ;
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return daLiPostoji;
	}

	public boolean daLiPostojiLozinka(String korisnickoIme) {
		boolean daLiPostoji = false;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_GET_LOZINKA;
			Object pom[] = { korisnickoIme };

			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getBytes("LozinkaHash") != null)
					daLiPostoji = true;
			}

		} catch (SQLException e) {
			 ;
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}

		return daLiPostoji;
	}

	public void insert(KorisnickiNalogDTO nalog) {
		PreparedStatement ps = null;
		Connection c = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_INSERT;
			Object pom[] = { null, nalog.getKorisnickoIme(), nalog.getLozinkaHash(), nalog.getDatumRegistracije(),
					nalog.getAktivan(), nalog.getUlogaId(), nalog.getZaposleniId() };

			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			ps.executeUpdate();
		} catch (SQLException e) {
			 ;
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
	}

	public String getHashByUsername(String username) {
		byte[] hashBytes = null;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String hash = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_GET_BY_USERNAME;
			Object pom[] = { username };

			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			rs = ps.executeQuery();
			while (rs.next()) {
				hashBytes = rs.getBytes("LozinkaHash");
			}
		} catch (SQLException e) {
			 ;
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}

		try {
			hash = new String(hashBytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			 ;
			new ErrorLogger().log(e);
		}
		return hash;
	}

	public ObservableList<KorisnickiNalogDTO> selectAll() {
		ObservableList<KorisnickiNalogDTO> nalozi = FXCollections.observableArrayList();

		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_SELECT_ALL;

			ps = ConnectionPool.prepareStatement(c, query, false);
			rs = ps.executeQuery();
			while (rs.next()) {
				nalozi.add(new KorisnickiNalogDTO(rs.getString("KorisnickoIme"), rs.getString("Naziv"),
						rs.getString("Ime"), rs.getString("Prezime"), rs.getInt("Id")));
			}
		} catch (SQLException e) {
			 ;
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}

		return nalozi;
	}

	public String getUloga(String text) {
		String uloga = null;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_SELECT_ULOGA;
			Object pom[] = { text };

			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			rs = ps.executeQuery();
			while (rs.next()) {
				uloga = rs.getString("Naziv");
			}
		} catch (SQLException e) {
			 ;
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}

		return uloga;
	}

	@Override
	public Integer getId(String username) {
		Integer result = null;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_SELECT_ID;
			Object pom[] = { username };

			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			rs = ps.executeQuery();
			if(rs.next()){
				result = rs.getInt("Id");
			}
		} catch (SQLException e) {
			 ;
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return result;
	}

	@Override
	public KorisnickiNalogDTO getNalogById(Integer id) {
		KorisnickiNalogDTO result = null;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_SELECT_NALOG;
			Object pom[] = { id };

			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			rs = ps.executeQuery();
			if(rs.next()){
				result = new KorisnickiNalogDTO(rs.getString("KorisnickoIme"), id);
			}
		} catch (SQLException e) {
			 ;
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return result;
	}

}
