package application.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import application.model.dto.ClanDTO;
import application.model.dto.ClanarinaDTO;

import application.util.ConnectionPool;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClanarinaDAO {
	private static final String SQL_SELECT_IDENTITY = "SELECT @outId";
	private static final String SQL_SELECT_ALL = "select * from prikaz_clanarina";
	private static final String SQL_DELETE = "{call obrisi_clanarinu(?)}";
	private static final String SQL_INSERT = "{call dodaj_clanarinu(?,?,?,?,?,?)}";
	private static final String SQL_UPDATE = "{call update_clanarinu(?,?,?,?,?,?,?)}";
	public static ObservableList<ClanarinaDTO> SELECT_ALL() {
		ObservableList<ClanarinaDTO> listaClanarina = FXCollections.observableArrayList();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_ALL);
			while(rs.next()) {
				listaClanarina.add(new ClanarinaDTO(rs.getInt("id"), rs.getDate("datum"), rs.getDouble("iznos"), rs.getString("Opis"),rs.getString("tip"),rs.getInt("mjesec"),rs.getInt("godina"), rs.getString("ime"),rs.getString("prezime"),rs.getInt("OSOBA_Id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, s);
		}
		
		return listaClanarina;
	}
	public static void INSERT(ClanarinaDTO clanarina, ClanDTO clan) { //radi, vidjeti povratnu vrijednost
		//da li je uspio insert
		 
		Connection c = null;
		java.sql.CallableStatement cs = null;
		
		try{
			c = ConnectionPool.getInstance().checkOut();
			cs = c.prepareCall(SQL_INSERT);
			
			cs.setDate("inDatum", new java.sql.Date(clanarina.getDatum().getTime()));
			cs.setDouble("inIznos", clanarina.getIznos().doubleValue());
			cs.setString("inOpis",clanarina.getOpis().getValue());
			cs.setInt("inMjesec", clanarina.getMjesec().intValue());
			cs.setInt("inGodina",clanarina.getGodina().intValue());
			cs.setInt("inClanId", clan.getId());
			cs.executeQuery();
			Object temp[] = {};

			PreparedStatement ps = ConnectionPool.prepareStatement(c,SQL_SELECT_IDENTITY, false, temp);
			ps.executeUpdate();
			Object tem[] = {};
			
			ps = ConnectionPool.prepareStatement(c, SQL_SELECT_IDENTITY, false, tem);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
				clanarina.setId(rs.getInt("@outId"));
			//System.out.println("Id clanarine je "+id);
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(cs);
		}
	}
	public static void UPDATE(ClanarinaDTO clanarina, ClanDTO clan) {
		Connection c = null;
		java.sql.CallableStatement cs = null;
		
		try{
			c = ConnectionPool.getInstance().checkOut();
			cs = c.prepareCall(SQL_UPDATE);
			System.out.println(clanarina.getId());
			if(clanarina.getId()==null)
				cs.setNull("inId", Integer.MAX_VALUE);
			else
				cs.setInt("inId", clanarina.getId());
			cs.setDate("inDatum", new java.sql.Date(clanarina.getDatum().getTime()));
			cs.setDouble("inIznos", clanarina.getIznos().doubleValue());
			cs.setString("inOpis",clanarina.getOpis().getValue());
			cs.setInt("inMjesec", clanarina.getMjesec().intValue());
			cs.setInt("inGodina",clanarina.getGodina().intValue());
			cs.setInt("inOsobaId", clan.getId());
			cs.executeQuery();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(cs);
		}
	}
}
