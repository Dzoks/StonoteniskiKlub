package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import application.model.dao.ClanarinaDAO;
import application.model.dto.ClanDTO;
import application.model.dto.ClanarinaDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MySQLClanarinaDAO implements ClanarinaDAO{
private static final String SQL_SELECT_ALL = "select * from prikaz_clanarina";
	
	private static final String SQL_INSERT = "{call dodaj_clanarinu(?,?,?,?,?,?,?)}";
	private static final String SQL_UPDATE = "{call update_clanarinu(?,?,?,?,?,?,?)}";
	public ObservableList<ClanarinaDTO> SELECT_ALL() {
		ObservableList<ClanarinaDTO> listaClanarina = FXCollections.observableArrayList();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_ALL);
			while(rs.next()) {
				listaClanarina.add(new ClanarinaDTO(rs.getInt("Id"), rs.getDate("Datum"), rs.getDouble("Iznos"), rs.getString("Opis"),rs.getString("Tip"),rs.getInt("Mjesec"),rs.getInt("Godina"), rs.getString("Ime"),rs.getString("Prezime"),rs.getInt("OSOBA_Id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, s);
		}
		
		return listaClanarina;
	}
	public  boolean INSERT(ClanarinaDTO clanarina, ClanDTO clan) { //radi, vidjeti povratnu vrijednost
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
			cs.registerOutParameter("outId", Types.INTEGER);
			try {
				cs.executeQuery();
			}catch(SQLException ex) {
				Alert alert = new Alert(AlertType.INFORMATION, ex.getMessage());
				alert.showAndWait();
				return false;
			}
			
			
			clanarina.setId(cs.getInt("outId"));
			System.out.println("Id clanarine je "+clanarina.getId());
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(cs);
		}
		return true;
	}
	public  void UPDATE(ClanarinaDTO clanarina, ClanDTO clan) {
		Connection c = null;
		java.sql.CallableStatement cs = null;
		
		try{
			c = ConnectionPool.getInstance().checkOut();
			cs = c.prepareCall(SQL_UPDATE);
			System.out.println(clanarina.getId());
			
			System.out.println(cs);
			System.out.println(clanarina);
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
