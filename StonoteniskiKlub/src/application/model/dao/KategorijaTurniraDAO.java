package application.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.model.dto.KategorijaTurniraDTO;
import application.util.ConnectionPool;

public class KategorijaTurniraDAO {
	private final static String SQL_GET_BY_ID="select * from TURNIR_KATEGORIJA where Id=?";
	private final static String SQL_GET_BY_NAME="select * from TURNIR_KATEGORIJA where Kategorija=?";
	
	public static KategorijaTurniraDTO getById(int id){
		KategorijaTurniraDTO retVal=new KategorijaTurniraDTO();
		Connection c=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			c=ConnectionPool.getInstance().checkOut();
			String query=SQL_GET_BY_ID;
			Object pom[] = { id };
			
			ps=ConnectionPool.prepareStatement(c, query, false, pom);
			rs=ps.executeQuery();
			retVal=new KategorijaTurniraDTO(id, rs.getString("Kategorija"));
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		
		return retVal;
	}
	
	public static KategorijaTurniraDTO getByName(String kategorija){
		KategorijaTurniraDTO retVal=new KategorijaTurniraDTO();
		Connection c=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			c=ConnectionPool.getInstance().checkOut();
			String query=SQL_GET_BY_ID;
			Object pom[] = { kategorija };
			
			ps=ConnectionPool.prepareStatement(c, query, false, pom);
			rs=ps.executeQuery();
			retVal=new KategorijaTurniraDTO(rs.getInt("Id"), kategorija);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		
		return retVal;
	}
}
