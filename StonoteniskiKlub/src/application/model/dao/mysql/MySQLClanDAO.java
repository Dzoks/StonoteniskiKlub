package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.model.dao.ClanDAO;
import application.model.dao.DAOFactory;
import application.model.dto.ClanDTO;
import application.util.ConnectionPool;
import application.util.ErrorLogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySQLClanDAO implements ClanDAO {

	private final static String SQL_GET_BY_ID = "SELECT * FROM CLAN c left JOIN OSOBA o on c.OSOBA_ID=o.ID where c.OSOBA_ID=?";
	private final static String SQL_UPDATE_AKTIVAN = "UPDATE CLAN SET Aktivan=? WHERE OSOBA_Id=?";
	private final static String SQL_INSERT = "INSERT INTO CLAN VALUES (?, ?, ?)";
	private final static String SQL_SELECT_ALL = "SELECT * FROM CLAN c INNER JOIN OSOBA o ON c.OSOBA_ID = o.ID";
	private final static String SQL_UPDATE_REGISTROVAN = "update CLAN set Registrovan=? where OSOBA_Id=?";

	public List<ClanDTO> selectAllByImePrezime(String ime, String prezime) {
		ArrayList<ClanDTO> list = new ArrayList<>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_SELECT_ALL + " WHERE ";
			if (!"".equals(ime)) {
				query += "o.IME LIKE '%" + ime + "%'";
				if (!"".equals(prezime))
					query += " AND o.PREZIME LIKE '%" + prezime + "%'";
			} else {
				query += "o.PREZIME LIKE '%" + prezime + "%'";
			}

			Object pom[] = {};

			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			rs = ps.executeQuery();
			while (rs.next()) {
				ClanDTO clan = new ClanDTO(rs.getInt("Id"), rs.getString("Ime"), rs.getString("Prezime"),
						rs.getString("ImeRoditelja"), rs.getString("JMB"), rs.getString("Pol").charAt(0),
						rs.getDate("DatumRodjenja"), rs.getBlob("Fotografija"), null, rs.getBoolean("Aktivan"),
						rs.getBoolean("Registrovan"));
				list.add(clan);
				clan.setTelefoni(DAOFactory.getDAOFactory().getOsobaDAO().getTelefoni(clan.getId()));
			}
		} catch (SQLException e) {
			 ;
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}

		return list;
	}

	public ObservableList<ClanDTO> SELECT_ALL() {// Helena dodala
		String select = "select * from clan c inner join osoba o where o.id=c.osoba_id";
		ObservableList<ClanDTO> listaClanova = FXCollections.observableArrayList();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		/*
		 * int id, String ime, String prezime, String imeRoditelja, String jmb,
		 * Character pol, Date datumRodjenja, Blob slika, List<String> telefoni,
		 * boolean aktivan, boolean registrovan
		 */
		try {
			c = ConnectionPool.getInstance().checkOut();
			s = c.createStatement();
			rs = s.executeQuery(select);
			while (rs.next()) {
				listaClanova.add(new ClanDTO(rs.getInt("id"), rs.getString("ime"), rs.getString("prezime"),
						rs.getString("imeRoditelja"), rs.getString("jmb"), rs.getString("Pol").charAt(0),
						rs.getDate("DatumRodjenja"), rs.getBlob("Fotografija"), null, rs.getBoolean("Aktivan"),
						rs.getBoolean("Registrovan")));
			}
		} catch (SQLException e) {
			 ;
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, s);
		}

		return listaClanova;
	}

	public ClanDTO getById(int id) {
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
				clan = new ClanDTO(rs.getInt("Id"), rs.getString("Ime"), rs.getString("Prezime"),
						rs.getString("ImeRoditelja"), rs.getString("JMB"), rs.getString("Pol").charAt(0),
						rs.getDate("DatumRodjenja"), rs.getBlob("Fotografija"), null, rs.getBoolean("Aktivan"),
						rs.getBoolean("Registrovan"));
		} catch (SQLException e) {
			 ;
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}

		return clan;
	}

	public List<ClanDTO> selectAll() {
		ArrayList<ClanDTO> list = new ArrayList<>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_SELECT_ALL;
			Object pom[] = {};

			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			rs = ps.executeQuery();
			while (rs.next()) {
				ClanDTO clan = new ClanDTO(rs.getInt("Id"), rs.getString("Ime"), rs.getString("Prezime"),
						rs.getString("ImeRoditelja"), rs.getString("JMB"), rs.getString("Pol").charAt(0),
						rs.getDate("DatumRodjenja"), rs.getBlob("Fotografija"), null, rs.getBoolean("Aktivan"),
						rs.getBoolean("Registrovan"));
				list.add(clan);
				clan.setTelefoni(DAOFactory.getDAOFactory().getOsobaDAO().getTelefoni(clan.getId()));
			}
		} catch (SQLException e) {
			 ;
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}

		return list;
	}

	public void setAktivan(boolean flag, int clanId) {
		PreparedStatement ps = null;
		Connection c = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_UPDATE_AKTIVAN;
			Object pom[] = { flag, clanId };

			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			 ;
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
	}

	public void insert(ClanDTO clan) {
		PreparedStatement ps = null;
		Connection c = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_INSERT;
			Object pom[] = { clan.isAktivan(), clan.isRegistrovan(), clan.getId() };

			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			 ;
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
	}

	public void insertAll(ClanDTO clan) {
		DAOFactory.getDAOFactory().getOsobaDAO().insertSaTelefonom(clan);
		insert(clan);
	}

	public void setRegistrovan(boolean flag, int clanId) {
		PreparedStatement ps = null;
		Connection c = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_UPDATE_REGISTROVAN;
			Object pom[] = { flag, clanId };

			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			 ;
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
	}
}
