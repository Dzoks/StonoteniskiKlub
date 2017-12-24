package application.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.model.dto.OsobaDTO;
import application.model.dto.UcesnikPrijavaDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UcesnikPrijavaDAO {
	private final static String SQL_GET_BY_ID_OSOBE = "select * from UCESNIK_PRIJAVA u left join OSOBA o on u.OSOBA_Id=o.Id where u.OSOBA_Id=?";
	private final static String SQL_GET_BY_ID = "select * from UCESNIK_PRIJAVA u left join OSOBA o on u.OSOBA_Id=o.Id where u.Id=?";
	private final static String SQL_INSERT = "insert into UCESNIK_PRIJAVA (TURNIR_Id,TURNIR_KATEGORIJA_Id,OSOBA_Id,Datum) values (?,?,?,?)";
	private final static String SQL_SELECT_ALL = "select * from UCESNIK_PRIJAVA u inner join OSOBA o on u.OSOBA_Id = o.Id where u.TURNIR_Id=? and u.TURNIR_KATEGORIJA_Id=?";
	private final static String SQL_SELECT_ALL1="select * from UCESNIK_PRIJAVA u inner join OSOBA o where u.OSOBA_Id=o.Id";

	public static ObservableList<OsobaDTO> getAll(Integer idTurnira,Integer idKategorije) {
		ObservableList<OsobaDTO> retVal = FXCollections.observableArrayList();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_SELECT_ALL;
			Object pom[] = { idTurnira,idKategorije };
			
			ps = ConnectionPool.prepareStatement(c, query, false,pom);
			rs = ps.executeQuery();
			while (rs.next())
				retVal.add(new OsobaDTO(rs.getInt("Id"), rs.getString("Ime"), rs.getString("Prezime"), 
						rs.getString("JMB"), rs.getString("Pol").charAt(0), rs.getDate("DatumRodjenja")));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}

		return retVal;
	}
	
	public static ObservableList<UcesnikPrijavaDTO> SELECT_ALL() {//dodala Helena
		ObservableList<UcesnikPrijavaDTO> retVal = FXCollections.observableArrayList();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_ALL1);
			while (rs.next())
				retVal.add(new UcesnikPrijavaDTO(rs.getInt("OSOBA_Id"), rs.getString("Ime"), rs.getString("Prezime"), 
						rs.getString("JMB"), rs.getString("Pol").charAt(0), rs.getDate("DatumRodjenja"), 
						rs.getInt("Id"), rs.getInt("TURNIR_Id"), rs.getInt("TURNIR_KATEGORIJA_Id"),rs.getDate("Datum")));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(rs, s);
			ConnectionPool.getInstance().checkIn(c);
		}

		return retVal;
	}
	public static UcesnikPrijavaDTO getById(int id){
		UcesnikPrijavaDTO retVal=new UcesnikPrijavaDTO();
		Connection c=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			c=ConnectionPool.getInstance().checkOut();
			String query=SQL_GET_BY_ID;
			Object pom[] = { id };
			
			ps=ConnectionPool.prepareStatement(c, query, false, pom);
			rs=ps.executeQuery();
			if(rs.next()){
				retVal=new UcesnikPrijavaDTO(rs.getInt("OSOBA_Id"), rs.getString("Ime"), rs.getString("Prezime"), 
						rs.getString("JMB"), rs.getString("Pol").charAt(0), rs.getDate("DatumRodjenja"), 
						id, rs.getInt("TURNIR_Id"), rs.getInt("TURNIR_KATEGORIJA_Id"),rs.getDate("Datum"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		
		return retVal;
	}
	
	public static UcesnikPrijavaDTO getByIdOsobe(int id){
		UcesnikPrijavaDTO retVal=new UcesnikPrijavaDTO();
		Connection c=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			c=ConnectionPool.getInstance().checkOut();
			String query=SQL_GET_BY_ID_OSOBE;
			Object pom[] = { id };
			
			ps=ConnectionPool.prepareStatement(c, query, false, pom);
			rs=ps.executeQuery();
			if(rs.next()){
				retVal=new UcesnikPrijavaDTO(id, rs.getString("Ime"), rs.getString("Prezime"), 
						rs.getString("JMB"), rs.getString("Pol").charAt(0), rs.getDate("DatumRodjenja"), 
						rs.getInt("Id"), rs.getInt("TURNIR_Id"), rs.getInt("TURNIR_KATEGORIJA_Id"),rs.getDate("Datum"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		
		return retVal;
	}
	
	public static boolean insert(Integer idTurnira,Integer idKategorije,Integer idOsobe,Date datum) {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_INSERT;
			Object pom[] = { idTurnira,idKategorije,idOsobe,datum };
			
			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			return ps.executeUpdate()==1?true:false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return false;
	}
	
}
