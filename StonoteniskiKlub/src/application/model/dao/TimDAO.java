package application.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.model.dto.TimDTO;
import application.util.ConnectionPool;

public class TimDAO {
	//PITATI
	private final static String SQL_GET_SINGLE="select * from TIM t inner join UCESNIK_PRIJAVA u on t.UCESNIK_PRIJAVA1_Id=u.Id"
			+ " where u.TURNIR_Id=? and u.TURNIR_KATEGORIJA_Id=?";
	private final static String SQL_GET_DOUBLE="select * from TIM t inner join UCESNIK_PRIJAVA u on t.UCESNIK_PRIJAVA1_Id=u.Id"
			+ " where u.TURNIR_Id=? and u.TURNIR_KATEGORIJA_Id=?";
	private final static String SQL_GET_BY_ID="select * from TIM where Id=?";
	private final static String SQL_INSERT_SINGLE="insert into TIM (UCESNIK1_PRIJAVA_Id) values (?)";
	private final static String SQL_INSERT_DOUBLE="insert into TIM (UCESNIK1_PRIJAVA_Id,UCESNIK2_PRIJAVA_Id) values (?,?)";
	
	public static TimDTO getById(int id){
		TimDTO retVal=new TimDTO();
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
				retVal=new TimDTO(id, rs.getInt("UCESNIK1_PRIJAVA_Id"), rs.getInt("UCESNIK2_PRIJAVA_Id"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		
		return retVal;
	}
	
	public static boolean insertSingle(Integer idPrvogTima) {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_INSERT_SINGLE;
			Object pom[] = { idPrvogTima };
			
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
	
	public static boolean insertDouble(Integer idPrvogTima,Integer idDrugogTima) {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_INSERT_DOUBLE;
			Object pom[] = { idPrvogTima,idDrugogTima };
			
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
