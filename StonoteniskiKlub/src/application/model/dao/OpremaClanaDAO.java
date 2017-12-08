package application.model.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import application.model.dto.ClanDTO;
import application.model.dto.OpremaClanaDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OpremaClanaDAO {

	private static final String SQL_SELECT_ALL = "SELECT * FROM prikaz_opreme_clana";
	private static final String SQL_SELECT_AKTIVNE = "SELECT * FROM prikaz_clana WHERE Aktivan=true";
	private static final String SQL_SELECT_BY = "SELECT  * FROM prikaz_opreme_clana WHERE LOCATE(?, ";
	private static final String SQL_INSERT = "{call dodaj_opremu_clana(?,?,?,?,?,?)}";
	
	public static ObservableList<OpremaClanaDTO> SELECT_ALL() {
		ObservableList<OpremaClanaDTO> listaOpreme = FXCollections.observableArrayList();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_ALL);
			
			while(rs.next()) {
				listaOpreme.add(new OpremaClanaDTO(rs.getInt("Id"), rs.getInt("NARUDZBA_Id"), rs.getInt("OPREMA_TIP_Id"), rs.getInt("DONACIJA_Id"), rs.getBoolean("Donirana"), rs.getString("Velicina"), rs.getInt("CLAN_Id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, s);
		}
		
		return listaOpreme;
	}
	
	public static ObservableList<ClanDTO> SELECT_AKTIVNE(){
		ObservableList<ClanDTO> listClanova = FXCollections.observableArrayList();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_AKTIVNE);
			
			while(rs.next()) {
				listClanova.add(new ClanDTO(rs.getInt("Id"), rs.getString("Ime"), rs.getString("Prezime"), rs.getString("ImeRoditelja"), 
						rs.getString("JMB"), rs.getString("Pol").charAt(0), rs.getDate("DatumRodjenja"), rs.getBlob("Fotografija"), null,
						rs.getBoolean("Aktivan"), rs.getBoolean("Registrovan")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, s);
		}
		
		return listClanova;
	}
	
	public static ObservableList<OpremaClanaDTO> SELECT_BY(String tipPretrage, String rijec) {
		ObservableList<OpremaClanaDTO> listaOpreme = FXCollections.observableArrayList();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String upit = "";
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			upit = SQL_SELECT_BY + tipPretrage + ")>0";
			ps = ConnectionPool.prepareStatement(c, upit, false, rijec);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				listaOpreme.add(new OpremaClanaDTO(rs.getInt("Id"), rs.getInt("NARUDZBA_Id"), rs.getInt("OPREMA_TIP_Id"), rs.getInt("DONACIJA_Id"), rs.getBoolean("Donirana"), rs.getString("Velicina"), rs.getInt("CLAN_Id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, ps);
		}
		
		return listaOpreme;
	}
	
	public static void INSERT(OpremaClanaDTO oprema) {
		Connection c = null;
		CallableStatement cs = null;
		
		try{
			c = ConnectionPool.getInstance().checkOut();
			cs = c.prepareCall(SQL_INSERT);
			cs.setBoolean("inDonirana", oprema.getDonirana());
			cs.setInt("inOpremaTipId", oprema.getIdTipaOpreme());
			
			if(oprema.getIdNarudzbe() == null) {
				cs.setNull("inNarudzbaId", Types.INTEGER);
			}
			else {
				cs.setInt("inNarudzbaId", oprema.getIdNarudzbe());
			}
			if(oprema.getIdDonacije() == null) {
				cs.setNull("inDonacijaId", Types.INTEGER);
			}
			else {
				cs.setInt("inDonacijaId", oprema.getIdDonacije());
			}
			
			cs.setString("inVelicina", oprema.getVelicina());
			cs.setInt("inClanId", oprema.getIdClana());
			cs.executeQuery();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(cs);
		}
	}
}
