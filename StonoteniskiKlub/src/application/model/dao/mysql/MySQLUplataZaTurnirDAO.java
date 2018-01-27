package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.model.dao.UplataZaTurnirDAO;
import application.model.dto.UcesnikPrijavaDTO;
import application.model.dto.UplataZaTurnirDTO;
import application.util.ConnectionPool;
import application.util.ErrorLogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MySQLUplataZaTurnirDAO implements UplataZaTurnirDAO{
	private static final String SQL_SELECT_ALL="select * from prikaz_uplata_turnir";
	private static final String SQL_INSERT = "{call dodaj_uplatu_turnir(?,?,?,?,?)}";
	private static final String SQL_UPDATE = "{call update_uplatu_turnir(?,?,?,?)}";
	public  ObservableList<UplataZaTurnirDTO> SELECT_ALL() {
		ObservableList<UplataZaTurnirDTO> listaUplataZaTurnir = FXCollections.observableArrayList();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_ALL);
			while(rs.next()) {
				UcesnikPrijavaDTO turnir = new UcesnikPrijavaDTO(rs.getInt("osobaId"), rs.getString("Ime"), rs.getString("Prezime"), null,null,null,rs.getInt("prijavaId"),rs.getInt("TURNIR_Id"),null,null);
				listaUplataZaTurnir.add(new UplataZaTurnirDTO(rs.getInt("transakcijaId"), rs.getDate("Datum"), rs.getDouble("Iznos"), rs.getString("Opis"),rs.getString("Tip"),turnir));
			}
		} catch (SQLException e) {
			 ;new ErrorLogger().log(e);
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, s);
		}
		
		return listaUplataZaTurnir;
	}
	public  boolean INSERT(UplataZaTurnirDTO uplata, UcesnikPrijavaDTO ucesnik) { //radi, vidjeti povratnu vrijednost
		//da li je uspio insert
		 
		Connection c = null;
		java.sql.CallableStatement cs = null;
		
		try{
			c = ConnectionPool.getInstance().checkOut();
			cs = c.prepareCall(SQL_INSERT);
			cs.setDate("inDatum", new java.sql.Date(uplata.getDatum().getTime()));
			cs.setDouble("inIznos", uplata.getIznos().doubleValue());
			cs.setString("inOpis",uplata.getOpis().getValue());
			cs.setInt("inPrijavaId", ucesnik.getIdPrijave());
			try {
				cs.executeQuery();
			}catch(SQLException ex) {
				Alert alert = new Alert(AlertType.INFORMATION, ex.getMessage());
				alert.showAndWait();
				return false;
			}
			uplata.setId(cs.getInt("outId"));
		}catch (SQLException e) {
			 ;new ErrorLogger().log(e);
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(cs);
		}
		return true;
	}
	public  void UPDATE(UplataZaTurnirDTO uplata) {
		Connection c = null;
		java.sql.CallableStatement cs = null;
		
		try{
			c = ConnectionPool.getInstance().checkOut();
			cs = c.prepareCall(SQL_UPDATE);
			
			if(uplata.getId()==null) //rijesitiiiiiiiiiiiiiiiiiii
				cs.setNull("inId", Integer.MAX_VALUE);
			else
			cs.setInt("inId", uplata.getId());
			cs.setDate("inDatum", new java.sql.Date(uplata.getDatum().getTime()));
			cs.setDouble("inIznos", uplata.getIznos().doubleValue());
			cs.setString("inOpis",uplata.getOpis().getValue());
			cs.executeQuery();
		}catch (SQLException e) {
			 ;
			new ErrorLogger().log(e);
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(cs);
		}
	}
}
