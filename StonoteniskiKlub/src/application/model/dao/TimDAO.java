package application.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import application.model.dto.TimDTO;
import application.model.dto.UcesnikPrijavaDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TimDAO {
	private final static String SQL_GET_SINGLE_LIST="select * from TIM t inner join UCESNIK_PRIJAVA u on t.UCESNIK1_PRIJAVA_Id=u.Id"
			+ " where u.TURNIR_Id=? and u.TURNIR_KATEGORIJA_Id=? and isnull(t.UCESNIK2_PRIJAVA_Id)";
	private final static String SQL_GET_DOUBLE_LIST="select * from TIM t inner join UCESNIK_PRIJAVA u1 on t.UCESNIK1_PRIJAVA_Id=u1.Id"
			+ " inner join UCESNIK_PRIJAVA u2 on t.UCESNIK2_PRIJAVA_Id=u2.Id where u1.TURNIR_Id=? and u1.TURNIR_KATEGORIJA_Id=?";
	private final static String SQL_GET_SINGLE="select * from TIM t inner join UCESNIK_PRIJAVA u on t.UCESNIK1_PRIJAVA_Id=u.Id"
			+ " inner join OSOBA o on u.OSOBA_Id=o.Id where u.TURNIR_Id=? and u.TURNIR_KATEGORIJA_Id=? and isnull(t.UCESNIK2_PRIJAVA_Id)";
	private final static String SQL_GET_DOUBLE="select o1.Id,o1.Ime,o1.Prezime,o1.JMB,o1.Pol,o1.DatumRodjenja,"
			+ "o2.Id,o2.Ime,o2.Prezime,o2.JMB,o2.Pol,o2.DatumRodjenja,u1.Id,u2.Id,u1.Datum,u2.Datum from TIM t "
			+ "inner join UCESNIK_PRIJAVA u1 on t.UCESNIK1_PRIJAVA_Id=u1.Id inner join OSOBA o1 on u1.OSOBA_Id=o1.Id"
			+ " inner join UCESNIK_PRIJAVA u2 on t.UCESNIK2_PRIJAVA_Id=u2.Id"
			+ " inner join OSOBA o2 on u2.OSOBA_Id=o2.Id where u1.TURNIR_Id=? and u1.TURNIR_KATEGORIJA_Id=?";
	private final static String SQL_GET_SINGLE_BY_ID="select o.Ime,o.Prezime from TIM t inner join UCESNIK_PRIJAVA u on t.UCESNIK1_PRIJAVA_Id=u.Id"
			+ " inner join OSOBA o on u.OSOBA_Id=o.Id where t.Id=? and isnull(t.UCESNIK2_PRIJAVA_Id)";
	private final static String SQL_GET_DOUBLE_BY_ID="select o1.Ime,o1.Prezime,o2.Ime,o2.Prezime from TIM t "
			+ "inner join UCESNIK_PRIJAVA u1 on t.UCESNIK1_PRIJAVA_Id=u1.Id inner join OSOBA o1 on u1.OSOBA_Id=o1.Id"
			+ " inner join UCESNIK_PRIJAVA u2 on t.UCESNIK2_PRIJAVA_Id=u2.Id"
			+ " inner join OSOBA o2 on u2.OSOBA_Id=o2.Id where t.Id=?";
	private final static String SQL_INSERT_SINGLE="{call prijaviSinglTim(?,?)}";
	private final static String SQL_INSERT_DOUBLE="{call prijaviDublTim(?,?,?)}";
	
	public static ObservableList<UcesnikPrijavaDTO> getSingle(Integer idTurnira,Integer idKategorije) {
		ObservableList<UcesnikPrijavaDTO> retVal = FXCollections.observableArrayList();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_GET_SINGLE;
			Object pom[] = { idTurnira,idKategorije };
			
			ps = ConnectionPool.prepareStatement(c, query, false,pom);
			rs = ps.executeQuery();
			while (rs.next())
				retVal.add(new UcesnikPrijavaDTO(rs.getInt("OSOBA_Id"), rs.getString("Ime"), rs.getString("Prezime"), 
						rs.getString("JMB"), rs.getString("Pol").charAt(0), rs.getDate("DatumRodjenja"),
						rs.getInt("UCESNIK1_PRIJAVA_Id"),rs.getInt("TURNIR_Id"),rs.getInt("TURNIR_KATEGORIJA_Id"),
						rs.getDate("Datum")));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return retVal;
	}
	
	public static ArrayList<TimDTO> getSingleList(Integer idTurnira,Integer idKategorije) {
		ArrayList<TimDTO> retVal=new ArrayList<>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_GET_SINGLE_LIST;
			Object pom[] = { idTurnira,idKategorije };
			
			ps = ConnectionPool.prepareStatement(c, query, false,pom);
			rs = ps.executeQuery();
			while (rs.next())
				retVal.add(new TimDTO(rs.getInt("Id"), rs.getInt("UCESNIK1_PRIJAVA_Id"), rs.getInt("UCESNIK2_PRIJAVA_Id")));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return retVal;
	}
	
	public static ObservableList<UcesnikPrijavaDTO> getDouble(Integer idTurnira,Integer idKategorije) {
		ObservableList<UcesnikPrijavaDTO> retVal = FXCollections.observableArrayList();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_GET_DOUBLE;
			Object pom[] = { idTurnira,idKategorije };
			
			ps = ConnectionPool.prepareStatement(c, query, false,pom);
			rs = ps.executeQuery();
			while (rs.next()){
				retVal.add(new UcesnikPrijavaDTO(rs.getInt(1), rs.getString(2), rs.getString(3), 
						rs.getString(4), rs.getString(5).charAt(0), rs.getDate(6),rs.getInt(13),idTurnira,idKategorije,
						rs.getDate(15)));
				retVal.add(new UcesnikPrijavaDTO(rs.getInt(7), rs.getString(8), rs.getString(9), 
						rs.getString(10), rs.getString(11).charAt(0), rs.getDate(12),rs.getInt(14),idTurnira,idKategorije,
						rs.getDate(16)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return retVal;
	}
	
	public static ArrayList<TimDTO> getDoubleList(Integer idTurnira,Integer idKategorije) {
		ArrayList<TimDTO> retVal=new ArrayList<>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_GET_DOUBLE_LIST;
			Object pom[] = { idTurnira,idKategorije };
			
			ps = ConnectionPool.prepareStatement(c, query, false,pom);
			rs = ps.executeQuery();
			while (rs.next()){
				retVal.add(new TimDTO(rs.getInt("Id"), rs.getInt("UCESNIK1_PRIJAVA_Id"), rs.getInt("UCESNIK2_PRIJAVA_Id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return retVal;
	}
	
	public static String getSingleById(int id){
		String retVal=new String();
		Connection c=null;
		PreparedStatement ps=null;
		ResultSet rs=null;	
		try {
			c=ConnectionPool.getInstance().checkOut();
			String query=SQL_GET_SINGLE_BY_ID;
			Object pom[] = { id };
			
			ps=ConnectionPool.prepareStatement(c, query, false, pom);
			rs=ps.executeQuery();
			if(rs.next()){
				retVal=rs.getString("Ime")+" "+rs.getString("Prezime");
			}
			else{
				retVal="nema protivnika";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}	
		return retVal;
	}
	
	public static String getDoubleById(int id){
		String retVal=new String();
		Connection c=null;
		PreparedStatement ps=null;
		ResultSet rs=null;	
		try {
			c=ConnectionPool.getInstance().checkOut();
			String query=SQL_GET_DOUBLE_BY_ID;
			Object pom[] = { id };
			
			ps=ConnectionPool.prepareStatement(c, query, false, pom);
			rs=ps.executeQuery();
			if(rs.next()){
				retVal=rs.getString(1)+" "+rs.getString(2)+"/"+rs.getString(3)+" "+rs.getString(4);
			}
			else{
				retVal="nema protivnika";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}		
		return retVal;
	}
	
	public static boolean insertSingle(Integer idPrvogUcesnika) {
		boolean retVal=false;
		Connection c = null;
		java.sql.CallableStatement cst=null;	
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_INSERT_SINGLE;
			cst=c.prepareCall(query);
			cst.setInt(1, idPrvogUcesnika);
			cst.registerOutParameter(2, Types.BOOLEAN);
			cst.execute();
			retVal=cst.getBoolean(2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionPool.close(cst);
			ConnectionPool.getInstance().checkIn(c);
		}
		return retVal;
	}
	
	public static boolean insertDouble(Integer idPrvogUcesnika,Integer idDrugogUcesnika) {
		boolean retVal=false;
		Connection c = null;
		java.sql.CallableStatement cst=null;	
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_INSERT_DOUBLE;
			cst=c.prepareCall(query);
			cst.setInt(1, idPrvogUcesnika);
			cst.setInt(2, idDrugogUcesnika);
			cst.registerOutParameter(3, Types.BOOLEAN);
			cst.execute();
			retVal=cst.getBoolean(3);
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
