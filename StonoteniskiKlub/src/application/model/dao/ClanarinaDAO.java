package application.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import application.model.dto.ClanDTO;
import application.model.dto.ClanarinaDTO;
import application.model.dto.OpremaTipDTO;
import application.util.ConnectionPool;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClanarinaDAO {
	
	private static final String SQL_SELECT_ALL = "select * from prikaz_clanarina";
	private static final String SQL_DELETE = "{call obrisi_clanarinu(?)}";
	private static final String SQL_INSERT = "{call dodaj_clanarinu(?,?,?,?,?,?)}";
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
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(cs);
		}
	}
	
}
