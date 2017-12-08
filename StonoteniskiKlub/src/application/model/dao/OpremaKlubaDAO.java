package application.model.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import application.model.dto.OpremaKlubaDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OpremaKlubaDAO {

	private static final String SQL_SELECT_ALL = "SELECT * FROM prikaz_opreme_kluba";
	private static final String SQL_SELECT_AKTIVNOST = "SELECT * FROM prikaz_opreme_kluba WHERE Aktivan=?";
	private static final String SQL_SELECT_BY = "SELECT  * FROM prikaz_opreme_kluba WHERE LOCATE(?, ";
	private static final String SQL_INSERT = "{call dodaj_instance_opreme_kluba(?,?,?,?,?,?)}";
	private final static String SQL_UPDATE_AKTIVNOST = "UPDATE oprema_klub SET Aktivan=? WHERE OPREMA_Id=?";
	
	public static ObservableList<OpremaKlubaDTO> SELECT_ALL() {
		ObservableList<OpremaKlubaDTO> listaOpreme = FXCollections.observableArrayList();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_ALL);
			
			while(rs.next()) {
				listaOpreme.add(new OpremaKlubaDTO(rs.getInt("Id"), rs.getInt("NARUDZBA_Id"), rs.getInt("OPREMA_TIP_Id"), rs.getInt("DONACIJA_Id"), rs.getBoolean("Donirana"), rs.getString("Opis"), rs.getBoolean("Aktivan")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, s);
		}
		
		return listaOpreme;
	}
	
	public static ObservableList<OpremaKlubaDTO> SELECT_AKTIVNOST(Boolean aktivan) {
		ObservableList<OpremaKlubaDTO> listaOpreme = FXCollections.observableArrayList();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			if(aktivan == null) {
				listaOpreme = OpremaKlubaDAO.SELECT_ALL();
			}
			else {
				c = ConnectionPool.getInstance().checkOut();
				ps = ConnectionPool.prepareStatement(c, SQL_SELECT_AKTIVNOST, false, aktivan);
				rs = ps.executeQuery();
				
				while(rs.next()) {
					listaOpreme.add(new OpremaKlubaDTO(rs.getInt("Id"), rs.getInt("NARUDZBA_Id"), rs.getInt("OPREMA_TIP_Id"), rs.getInt("DONACIJA_Id"), rs.getBoolean("Donirana"), rs.getString("Opis"), rs.getBoolean("Aktivan")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, ps);
		}
		
		return listaOpreme;
	}
	
	public static ObservableList<OpremaKlubaDTO> SELECT_BY(Boolean aktivan, String tipPretrage, String rijec) {
		ObservableList<OpremaKlubaDTO> listaOpreme = FXCollections.observableArrayList();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String upit = "";
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			upit = SQL_SELECT_BY + tipPretrage + ")>0";
			
			if(aktivan != null) {
				upit += " AND Aktivan=" + aktivan;
			}
			
			ps = ConnectionPool.prepareStatement(c, upit, false, rijec);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				listaOpreme.add(new OpremaKlubaDTO(rs.getInt("Id"), rs.getInt("NARUDZBA_Id"), rs.getInt("OPREMA_TIP_Id"), rs.getInt("DONACIJA_Id"), rs.getBoolean("Donirana"), rs.getString("Opis"), rs.getBoolean("Aktivan")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, ps);
		}
		
		return listaOpreme;
	}
	
	public static void INSERT(OpremaKlubaDTO oprema, Integer brojInstanci) {
		Connection c = null;
		CallableStatement cs = null;
		
		try{
			c = ConnectionPool.getInstance().checkOut();
			cs = c.prepareCall(SQL_INSERT);
			cs.setInt("inBrojInstanci", brojInstanci);
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
			
			cs.setString("inOpis", oprema.getOpis());
			cs.executeQuery();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(cs);
		}
	}
	
	public static void UPDATE(OpremaKlubaDTO oprema, Boolean aktivan) {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_UPDATE_AKTIVNOST, false, aktivan, oprema.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(ps);
		}
	}
}