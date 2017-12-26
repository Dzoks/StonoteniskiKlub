package application.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.model.dto.RundaDTO;
import application.model.dto.ZrijebDTO;
import application.util.ConnectionPool;

public class RundaDAO {
	private final static String SQL_GET_BY_BROJ="select * from RUNDA where ZRIJEB_Id=? and Broj=?";
	private final static String SQL_INSERT="insert into RUNDA (ZRIJEB_Id,Broj) values (?,?)";
	private final static String SQL_NUM_COMPLETED="select count(*) from MEC where RUNDA_ZRIJEB_Id=? and RUNDA_Broj=? and not isnull(Rezultat)";
	
	public static RundaDTO getByBroj(Integer idZrijeba,Integer brojRunde){
		RundaDTO retVal=new RundaDTO();
		Connection c=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			c=ConnectionPool.getInstance().checkOut();
			String query=SQL_GET_BY_BROJ;
			Object pom[] = { idZrijeba,brojRunde };
			
			ps=ConnectionPool.prepareStatement(c, query, false, pom);
			rs=ps.executeQuery();
			if(rs.next()){
				retVal=new RundaDTO(idZrijeba, brojRunde);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		
		return retVal;
	}	
	
	public static boolean insert(Integer idZrijeba,Integer brojRunde) {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_INSERT;
			Object pom[] = { idZrijeba,brojRunde };
			
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
	
	public static Integer numCompleted(Integer idZrijeba,Integer brojRunde){
		Integer retVal=0;
		Connection c=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			c=ConnectionPool.getInstance().checkOut();
			String query=SQL_NUM_COMPLETED;
			Object pom[] = { idZrijeba,brojRunde };
			
			ps=ConnectionPool.prepareStatement(c, query, false, pom);
			rs=ps.executeQuery();
			if(rs.next()){
				retVal=rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		
		return retVal;
	}
}