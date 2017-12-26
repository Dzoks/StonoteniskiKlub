package application.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import application.model.dto.ZrijebDTO;
import application.util.ConnectionPool;

public class ZrijebDAO {
	private final static String SQL_GET_BY_ID="select * from ZRIJEB where Id=?";
	private final static String SQL_INSERT="{call kreirajSinglZrijeb(?,?,?,?)}";
	private final static String SQL_GET_ZRIJEB="select * from ZRIJEB where TURNIR_Id=? and TURNIR_KATEGORIJA_Id=?";
	private final static String SQL_DOES_EXIST="{call postojiZrijeb(?,?,?)}";
	
	public static ZrijebDTO getById(int id){
		ZrijebDTO retVal=new ZrijebDTO();
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
				retVal=new ZrijebDTO(id, rs.getInt("TURNIR_Id"), rs.getInt("TURNIR_KATEGORIJA_Id"), rs.getInt("BrojTimova"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		
		return retVal;
	}
	
	public static boolean insert(Integer idTurnira,Integer idKategorije,Integer brojTimova) {
		boolean retVal=false;
		Connection c = null;
		java.sql.CallableStatement cst=null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_INSERT;
			cst=c.prepareCall(query);
			cst.setInt(1, idTurnira);
			cst.setInt(2, idKategorije);
			cst.setInt(3, brojTimova);
			cst.registerOutParameter(4, Types.BOOLEAN);
			cst.execute();
			retVal=cst.getBoolean(4);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionPool.close(cst);
			ConnectionPool.getInstance().checkIn(c);
		}
		return retVal;
	}
	
	public static ZrijebDTO getZrijeb(Integer idTurnira,Integer idKategorije){
		ZrijebDTO retVal=new ZrijebDTO();
		Connection c=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			c=ConnectionPool.getInstance().checkOut();
			String query=SQL_GET_ZRIJEB;
			Object pom[] = { idTurnira,idKategorije };
			
			ps=ConnectionPool.prepareStatement(c, query, false, pom);
			rs=ps.executeQuery();
			if(rs.next()){
				retVal=new ZrijebDTO(rs.getInt("Id"), rs.getInt("TURNIR_Id"), rs.getInt("TURNIR_KATEGORIJA_Id"), rs.getInt("BrojTimova"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		
		return retVal;
	}
	
	public static boolean doesExist(Integer idTurnira,Integer idKategorije) {
		boolean retVal=false;
		Connection c = null;
		java.sql.CallableStatement cst=null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_DOES_EXIST;
			cst=c.prepareCall(query);
			cst.setInt(1, idTurnira);
			cst.setInt(2, idKategorije);
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
