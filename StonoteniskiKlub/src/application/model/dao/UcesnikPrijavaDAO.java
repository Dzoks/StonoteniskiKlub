package application.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import application.model.dto.UcesnikPrijavaDTO;
import application.util.ConnectionPool;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;

public class UcesnikPrijavaDAO {
	private final static String SQL_GET_BY_JMB = "select * from UCESNIK_PRIJAVA u left join OSOBA o on u.OSOBA_Id=o.Id where o.JMB=?"
			+ " order by u.TURNIR_KATEGORIJA_Id desc limit 1";
//	private final static String SQL_GET_BY_ID = "select * from UCESNIK_PRIJAVA u left join OSOBA o on u.OSOBA_Id=o.Id where u.Id=?";
	private final static String SQL_INSERT = "{call prijaviUcesnika(?,?,?,?,?,?,?,?,?)}";
//	private final static String SQL_SELECT_ALL = "select * from UCESNIK_PRIJAVA u inner join OSOBA o on u.OSOBA_Id = o.Id "
//			+ "where u.TURNIR_Id=? and u.TURNIR_KATEGORIJA_Id=?";
	public static final String SQL_UPDATE="update UCESNIK_PRIJAVA u inner join OSOBA o on u.OSOBA_Id = o.Id set o.Ime=?,"
			+ "o.Prezime=?,o.DatumRodjenja=? where u.Id=?";
	private final static String SQL_ADD_NEW = "{call dodajPrijavu(?,?,?,?,?)}";
//	
//	public static ObservableList<UcesnikPrijavaDTO> getAll(Integer idTurnira,Integer idKategorije) {
//		ObservableList<UcesnikPrijavaDTO> retVal = FXCollections.observableArrayList();
//		Connection c = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try {
//			c = ConnectionPool.getInstance().checkOut();
//			String query = SQL_SELECT_ALL;
//			Object pom[] = { idTurnira,idKategorije };
//			
//			ps = ConnectionPool.prepareStatement(c, query, false,pom);
//			rs = ps.executeQuery();
//			while (rs.next())
//				retVal.add(new UcesnikPrijavaDTO(rs.getInt("OSOBA_Id"), rs.getString("Ime"), rs.getString("Prezime"), 
//						rs.getString("JMB"), rs.getString("Pol").charAt(0), rs.getDate("DatumRodjenja"),
//						rs.getInt("Id"),rs.getInt("TURNIR_Id"),rs.getInt("TURNIR_KATEGORIJA_Id"),
//						rs.getDate("Datum")));
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			ConnectionPool.close(rs, ps);
//			ConnectionPool.getInstance().checkIn(c);
//		}
//		return retVal;
//	}
//	
//	public static UcesnikPrijavaDTO getById(int id){
//		UcesnikPrijavaDTO retVal=new UcesnikPrijavaDTO();
//		Connection c=null;
//		PreparedStatement ps=null;
//		ResultSet rs=null;	
//		try {
//			c=ConnectionPool.getInstance().checkOut();
//			String query=SQL_GET_BY_ID;
//			Object pom[] = { id };
//			
//			ps=ConnectionPool.prepareStatement(c, query, false, pom);
//			rs=ps.executeQuery();
//			if(rs.next()){
//				retVal=new UcesnikPrijavaDTO(rs.getInt("OSOBA_Id"), rs.getString("Ime"), rs.getString("Prezime"), 
//						rs.getString("JMB"), rs.getString("Pol").charAt(0), rs.getDate("DatumRodjenja"), 
//						id, rs.getInt("TURNIR_Id"), rs.getInt("TURNIR_KATEGORIJA_Id"),rs.getDate("Datum"));
//			}			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally {
//			ConnectionPool.close(rs, ps);
//			ConnectionPool.getInstance().checkIn(c);
//		}		
//		return retVal;
//	}
	
	public static UcesnikPrijavaDTO getByJmb(String jmb){
		UcesnikPrijavaDTO retVal=new UcesnikPrijavaDTO();
		Connection c=null;
		PreparedStatement ps=null;
		ResultSet rs=null;		
		try {
			c=ConnectionPool.getInstance().checkOut();
			String query=SQL_GET_BY_JMB;
			Object pom[] = { jmb };
			
			ps=ConnectionPool.prepareStatement(c, query, false, pom);
			rs=ps.executeQuery();
			if(rs.next()){
				retVal=new UcesnikPrijavaDTO(rs.getInt("Id"), rs.getInt("TURNIR_Id"),
						rs.getInt("TURNIR_KATEGORIJA_Id"),rs.getDate("Datum"));
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}		
		return retVal;
	}
	
	public static Integer insert(String jmb,String ime,String prezime,Character pol,
			Date datumRodjenja,Integer idTurnira,Integer idKategorije,Date datum) {
		Integer retVal=0;
		Connection c = null;
		java.sql.CallableStatement cst=null;		
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_INSERT;
			cst=c.prepareCall(query);
			cst.setString(1, jmb);
			cst.setString(2, ime);
			cst.setString(3, prezime);
			cst.setString(4, pol.toString());
			cst.setDate(5, datumRodjenja);
			cst.setInt(6, idTurnira);
			cst.setInt(7, idKategorije);
			cst.setDate(8, datum);
			cst.registerOutParameter(9, Types.INTEGER);
			cst.execute();
			retVal=cst.getInt(9);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionPool.close(cst);
			ConnectionPool.getInstance().checkIn(c);
		}
		return retVal;
	}
	
	public static boolean izmjeniUcesnika(Integer idPrijave,String ime,String prezime,Date datum){
		boolean retVal=false;
		Connection c = null;
		PreparedStatement ps=null;		
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_UPDATE;
			ps=c.prepareStatement(query);
			ps.setString(1, ime);
			ps.setString(2, prezime);
			ps.setDate(3, datum);
			ps.setInt(4, idPrijave);
			retVal=ps.executeUpdate()==1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return retVal;
	}
	
	public static Integer addNew(Integer idTurnira,Integer idKategorije,Integer idOsobe,Date datum) {
		Integer retVal=0;
		Connection c = null;
		java.sql.CallableStatement cst=null;		
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_ADD_NEW;
			cst=c.prepareCall(query);
			cst.setInt(1, idTurnira);
			cst.setInt(2, idKategorije);
			cst.setInt(3, idOsobe);
			cst.setDate(4, datum);
			cst.registerOutParameter(5, Types.INTEGER);
			cst.execute();
			retVal=cst.getInt(5);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionPool.close(cst);
			ConnectionPool.getInstance().checkIn(c);
		}
		return retVal;
	}
}
