package application.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.model.dto.Narudzba;
import application.model.dto.NarudzbaStavka;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NarudzbaDAO {

	private static final String SQL_SELECT_ALL = "SELECT * FROM narudzba WHERE Obrisan=false";
	private static final String SQL_SELECT_NEOBRADJENE = "SELECT * FROM narudzba WHERE Obrisan=false AND Obradjeno=false AND OpremaKluba=?";
	private static final String SQL_SELECT_NEXT_ID = "SELECT MAX(Id) FROM narudzba";
	private static final String SQL_INSERT = "INSERT INTO narudzba VALUES (?, ?, ?, ?, ?, false)";
	private static final String SQL_UPDATE = "UPDATE narudzba SET OpremaKluba=?, DISTRIBUTER_OPREME_Id=? WHERE Id=?";
	private final static String SQL_UPDATE_OBRADJENO = "UPDATE narudzba SET Obradjeno=true WHERE Id=?";
	private final static String SQL_UPDATE_OBRISAN = "UPDATE narudzba SET Obrisan=true WHERE Id=?";
	private static final String SQL_SELECT_OPREMA_KLUBA = "SELECT * FROM narudzba WHERE Obrisan=false AND OpremaKluba=true";
	
	
	
	
	public static ObservableList<Narudzba> SELECT_ALL() {
		ObservableList<Narudzba> listaNarudzbi = FXCollections.observableArrayList();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_ALL);
			
			while(rs.next()) {
				listaNarudzbi.add(new Narudzba(rs.getInt("Id"), rs.getDate("Datum"), rs.getBoolean("OpremaKluba"), rs.getBoolean("Obradjeno"), rs.getInt("DISTRIBUTER_OPREME_Id"), NarudzbaStavkaDAO.SELECT_BY_IDNARUDZBE(rs.getInt("Id"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, s);
		}
		
		return listaNarudzbi;
	}
	public static ObservableList<Narudzba> SELECT_OPREMA_KLUBA() {//dodala Helena
		ObservableList<Narudzba> listaNarudzbi = FXCollections.observableArrayList();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_OPREMA_KLUBA);
			
			while(rs.next()) {
				listaNarudzbi.add(new Narudzba(rs.getInt("Id"), rs.getDate("Datum"), rs.getBoolean("OpremaKluba"), rs.getBoolean("Obradjeno"), rs.getInt("DISTRIBUTER_OPREME_Id"), NarudzbaStavkaDAO.SELECT_BY_IDNARUDZBE(rs.getInt("Id"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, s);
		}
		
		return listaNarudzbi;
	}
	public static ObservableList<Narudzba> SELECT_NEOBRADJENE(Boolean opremaKluba) {
		ObservableList<Narudzba> listaNarudzbi = FXCollections.observableArrayList();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_SELECT_NEOBRADJENE, false, opremaKluba);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				listaNarudzbi.add(new Narudzba(rs.getInt("Id"), rs.getDate("Datum"), rs.getBoolean("OpremaKluba"), rs.getBoolean("Obradjeno"), rs.getInt("DISTRIBUTER_OPREME_Id"), NarudzbaStavkaDAO.SELECT_BY_IDNARUDZBE(rs.getInt("Id"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, ps);
		}
		
		return listaNarudzbi;
	}
	
	public static Integer SELECT_NEXT_ID() {
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		Integer rezultat = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_NEXT_ID);
			
			while(rs.next()) {
				rezultat = rs.getInt(1) + 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, s);
		}
		
		return rezultat;
	}
	
	public static Integer INSERT(Narudzba narudzba, Boolean vrstaOpreme) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer id = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_INSERT, true, narudzba.getId(), narudzba.getDatum(), vrstaOpreme, narudzba.getObradjeno(), narudzba.getIdDistributeraOpreme());
			ps.execute();
			rs = ps.getGeneratedKeys();
			rs.next();
	        id = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, ps);
		}
		
		return id;
	}
	
	public static void UPDATE(Narudzba narudzba, Boolean vrstaOpreme) {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_UPDATE, false, vrstaOpreme, narudzba.getIdDistributeraOpreme(), narudzba.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(ps);
		}
	}
	
	public static void UPDATE_OBRADJENO(Integer idNarudzbe) {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			ObservableList<NarudzbaStavka> listaStavki = NarudzbaStavkaDAO.SELECT_BY_IDNARUDZBE(idNarudzbe);
			for(NarudzbaStavka stavka : listaStavki) {
				if(stavka.getObradjeno().equals(false)) {
					return;
				}
			}
			ps = ConnectionPool.prepareStatement(c, SQL_UPDATE_OBRADJENO, false, idNarudzbe);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(ps);
		}
	}
	
	public static void UPDATE_OBRISAN(Narudzba narudzba) {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_UPDATE_OBRISAN, false, narudzba.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(ps);
		}
	}
	
	public static Boolean PROVJERA_STATUSA(Integer idNarudzbe) {
		ObservableList<NarudzbaStavka> listaStavki = NarudzbaStavkaDAO.SELECT_BY_IDNARUDZBE(idNarudzbe);
		if(listaStavki.isEmpty()) {
			return false;
		}
		for(NarudzbaStavka stavka : listaStavki) {
			if(stavka.getObradjeno().equals(true)) {
				return true;
			}
		}
		
		return false;
	}
}
