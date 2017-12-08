package application.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.model.dto.NarudzbaDTO;
import application.model.dto.NarudzbaStavkaDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NarudzbaDAO {

	private static final String SQL_SELECT_ALL = "SELECT * FROM narudzba";
	private static final String SQL_SELECT_NEOBRADJENE = "SELECT * FROM narudzba WHERE Obradjeno=false AND OpremaKluba=?";
	private static final String SQL_SELECT_NEXT_ID = "SELECT MAX(Id) FROM narudzba";
	private static final String SQL_INSERT = "INSERT INTO narudzba VALUES (?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE = "UPDATE narudzba SET OpremaKluba=?, DISTRIBUTER_OPREME_Id=? WHERE Id=?";
	private final static String SQL_UPDATE_OBRADJENO = "UPDATE narudzba SET Obradjeno=true WHERE Id=?";
	
	public static ObservableList<NarudzbaDTO> SELECT_ALL() {
		ObservableList<NarudzbaDTO> listaNarudzbi = FXCollections.observableArrayList();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_ALL);
			
			while(rs.next()) {
				listaNarudzbi.add(new NarudzbaDTO(rs.getInt("Id"), rs.getDate("Datum"), rs.getBoolean("OpremaKluba"), rs.getBoolean("Obradjeno"), rs.getInt("DISTRIBUTER_OPREME_Id"), NarudzbaStavkaDAO.SELECT_BY_IDNARUDZBE(rs.getInt("Id"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, s);
		}
		
		return listaNarudzbi;
	}
	
	public static ObservableList<NarudzbaDTO> SELECT_NEOBRADJENE(Boolean opremaKluba) {
		ObservableList<NarudzbaDTO> listaNarudzbi = FXCollections.observableArrayList();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_SELECT_NEOBRADJENE, false, opremaKluba);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				listaNarudzbi.add(new NarudzbaDTO(rs.getInt("Id"), rs.getDate("Datum"), rs.getBoolean("OpremaKluba"), rs.getBoolean("Obradjeno"), rs.getInt("DISTRIBUTER_OPREME_Id"), NarudzbaStavkaDAO.SELECT_BY_IDNARUDZBE(rs.getInt("Id"))));
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
	
	public static Integer INSERT(NarudzbaDTO narudzba, Boolean vrstaOpreme) {
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
	
	public static void UPDATE(NarudzbaDTO narudzba, Boolean vrstaOpreme) {
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
			ObservableList<NarudzbaStavkaDTO> listaStavki = NarudzbaStavkaDAO.SELECT_BY_IDNARUDZBE(idNarudzbe);
			for(NarudzbaStavkaDTO stavka : listaStavki) {
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
	
	public static Boolean PROVJERA_STATUSA(Integer idNarudzbe) {
		ObservableList<NarudzbaStavkaDTO> listaStavki = NarudzbaStavkaDAO.SELECT_BY_IDNARUDZBE(idNarudzbe);
		if(listaStavki.isEmpty()) {
			return false;
		}
		for(NarudzbaStavkaDTO stavka : listaStavki) {
			if(stavka.getObradjeno().equals(true)) {
				return true;
			}
		}
		
		return false;
	}
}
