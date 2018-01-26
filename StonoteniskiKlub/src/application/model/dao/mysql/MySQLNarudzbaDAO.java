package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

import application.model.dao.DAOFactory;
import application.model.dao.NarudzbaDAO;
import application.model.dto.Narudzba;
import application.model.dto.NarudzbaStavka;
import application.util.ConnectionPool;
import application.util.ErrorLogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySQLNarudzbaDAO implements NarudzbaDAO {

	private static final String SQL_SELECT_ALL = "SELECT * FROM narudzba WHERE Obrisan=false ";
	private static final String SQL_SELECT = "SELECT * FROM NARUDZBA WHERE Obrisan=false and OpremaKluba=true";
	private static final String SQL_SELECT_NEOBRADJENE = "SELECT * FROM narudzba WHERE Obrisan=false AND Obradjeno=false AND OpremaKluba=?";
	private static final String SQL_SELECT_NEXT_ID = "SELECT MAX(Id) FROM narudzba";
	private static final String SQL_INSERT = "INSERT INTO narudzba VALUES (?, ?, ?, ?, ?, false)";
	private static final String SQL_UPDATE = "UPDATE narudzba SET OpremaKluba=?, DISTRIBUTER_OPREME_Id=? WHERE Id=?";
	private final static String SQL_UPDATE_OBRADJENO = "UPDATE narudzba SET Obradjeno=true WHERE Id=?";
	private final static String SQL_UPDATE_OBRISAN = "UPDATE narudzba SET Obrisan=true WHERE Id=?";

	public ObservableList<Narudzba> SELECT_ALL() {
		ObservableList<Narudzba> listaNarudzbi = FXCollections.observableArrayList();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_ALL);

			while (rs.next()) {
				listaNarudzbi.add(new Narudzba(rs.getInt("Id"), rs.getDate("Datum"), rs.getBoolean("OpremaKluba"),
						rs.getBoolean("Obradjeno"), rs.getInt("DISTRIBUTER_OPREME_Id"),
						DAOFactory.getDAOFactory().getNarudzbaStavkaDAO().SELECT_BY_IDNARUDZBE(rs.getInt("Id"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} catch (ParseException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, s);
		}

		return listaNarudzbi;
	}

	public ObservableList<Narudzba> SELECT_OPREMA_KLUBA() {// dodala Helena, ne
															// brisite
		ObservableList<Narudzba> listaNarudzbi = FXCollections.observableArrayList();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT);

			while (rs.next()) {
				listaNarudzbi.add(new Narudzba(rs.getInt("Id"), rs.getDate("Datum"), rs.getBoolean("OpremaKluba"),
						rs.getBoolean("Obradjeno"), rs.getInt("DISTRIBUTER_OPREME_Id"),
						DAOFactory.getDAOFactory().getNarudzbaStavkaDAO().SELECT_BY_IDNARUDZBE(rs.getInt("Id"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} catch (ParseException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, s);
		}

		return listaNarudzbi;
	}

	public ObservableList<Narudzba> SELECT_NEOBRADJENE(Boolean opremaKluba) {
		ObservableList<Narudzba> listaNarudzbi = FXCollections.observableArrayList();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_SELECT_NEOBRADJENE, false, opremaKluba);
			rs = ps.executeQuery();

			while (rs.next()) {
				listaNarudzbi.add(new Narudzba(rs.getInt("Id"), rs.getDate("Datum"), rs.getBoolean("OpremaKluba"),
						rs.getBoolean("Obradjeno"), rs.getInt("DISTRIBUTER_OPREME_Id"),
						DAOFactory.getDAOFactory().getNarudzbaStavkaDAO().SELECT_BY_IDNARUDZBE(rs.getInt("Id"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} catch (ParseException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, ps);
		}

		return listaNarudzbi;
	}

	public Integer SELECT_NEXT_ID() {
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		Integer rezultat = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_NEXT_ID);

			while (rs.next()) {
				rezultat = rs.getInt(1) + 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, s);
		}

		return rezultat;
	}

	public Integer INSERT(Narudzba narudzba, Boolean vrstaOpreme) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer id = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_INSERT, true, narudzba.getId(), narudzba.getDatum(),
					vrstaOpreme, narudzba.getObradjeno(), narudzba.getIdDistributeraOpreme());
			ps.execute();
			rs = ps.getGeneratedKeys();
			rs.next();
			id = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, ps);
		}

		return id;
	}

	public void UPDATE(Narudzba narudzba, Boolean vrstaOpreme) {
		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_UPDATE, false, vrstaOpreme, narudzba.getIdDistributeraOpreme(),
					narudzba.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(ps);
		}
	}

	public void UPDATE_OBRADJENO(Integer idNarudzbe) {
		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			ObservableList<NarudzbaStavka> listaStavki = DAOFactory.getDAOFactory().getNarudzbaStavkaDAO()
					.SELECT_BY_IDNARUDZBE(idNarudzbe);

			for (NarudzbaStavka stavka : listaStavki) {
				if (stavka.getObradjeno().equals(false)) {
					return;
				}
			}

			ps = ConnectionPool.prepareStatement(c, SQL_UPDATE_OBRADJENO, false, idNarudzbe);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(ps);
		}
	}

	public void UPDATE_OBRISAN(Narudzba narudzba) {
		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_UPDATE_OBRISAN, false, narudzba.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(ps);
		}
	}

	public Boolean PROVJERA_STATUSA(Integer idNarudzbe) {
		ObservableList<NarudzbaStavka> listaStavki = DAOFactory.getDAOFactory().getNarudzbaStavkaDAO()
				.SELECT_BY_IDNARUDZBE(idNarudzbe);
		if (listaStavki.isEmpty()) {
			return false;
		}

		for (NarudzbaStavka stavka : listaStavki) {
			if (stavka.getObradjeno().equals(true)) {
				return true;
			}
		}

		return false;
	}
}
