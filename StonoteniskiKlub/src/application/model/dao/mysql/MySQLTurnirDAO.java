package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.model.dao.TurnirDAO;
import application.model.dto.TurnirDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySQLTurnirDAO implements TurnirDAO{
	private final static String SQL_GET_ALL="select * from TURNIR order by Id desc";
	private final static String SQL_GET_BY_ID="select * from TURNIR where Id=?";
	private final static String SQL_INSERT="insert into TURNIR (Naziv,Datum) values (?,?)";
	private final static String SQL_ZATVORI_TURNIR="update TURNIR set Zavrsen=1 where Id=?";	

	public ObservableList<TurnirDTO> getAll() {
		ObservableList<TurnirDTO> retVal = FXCollections.observableArrayList();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_GET_ALL;
			
			ps = ConnectionPool.prepareStatement(c, query, false);
			rs = ps.executeQuery();
			while (rs.next())
				retVal.add(new TurnirDTO(rs.getInt("Id"),rs.getString("Naziv"), rs.getDate("Datum"),rs.getBoolean("Zavrsen")));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return retVal;
	}
	
	public TurnirDTO getById(int id){
		TurnirDTO retVal=new TurnirDTO();
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
				retVal=new TurnirDTO(id, rs.getString("Naziv"), rs.getDate("Datum"));
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}		
		return retVal;
	}
	
	public boolean insert(String naziv,Date datum) {
		Connection c = null;
		PreparedStatement ps = null;		
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_INSERT;
			Object pom[] = { naziv,datum };
			
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
	
	public boolean zatvori(int id){
		Connection c = null;
		PreparedStatement ps = null;		
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_ZATVORI_TURNIR;
			Object pom[] = { id };
			
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
