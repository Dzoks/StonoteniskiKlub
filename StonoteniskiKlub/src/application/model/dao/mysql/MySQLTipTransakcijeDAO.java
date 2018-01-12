package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.model.dao.TipTransakcijeDAO;
import application.model.dto.TipTransakcijeDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySQLTipTransakcijeDAO implements TipTransakcijeDAO{
	private static final String SQL_SELECT_ALL = "select * from TIP_TRANSAKCIJE where Id>6";
	private static final String SQL_UPDATE = "update TIP_TRANSAKCIJE set Tip=? where Id=?";
	private static final String SQL_INSERT = "insert into TIP_TRANSAKCIJE values (null,?)";
	private static final String SQL_GET_BY_ID = "select * from TIP_TRANSAKCIJE where Id=?";
	public  ObservableList<TipTransakcijeDTO> SELECT_ALL(){
		ObservableList<TipTransakcijeDTO> list = FXCollections.observableArrayList();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_SELECT_ALL;
			Object pom[] = { };

			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new TipTransakcijeDTO(rs.getInt("Id"), rs.getString("Tip")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, ps);
		}
		
		return list;
	}
	public  void  UPDATE(TipTransakcijeDTO tip){
		Connection c = null;
		java.sql.PreparedStatement ps = null;
		
		try{
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_UPDATE;
			Object pom[] = {tip.getTip(), tip.getId()};
			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			ps.executeUpdate();;
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(ps);
		}
	}
	public void INSERT(TipTransakcijeDTO tip) { //radi, vidjeti povratnu vrijednost
		//da li je uspio insert
		 
		Connection c = null;
		java.sql.PreparedStatement ps = null;
		
		try{
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_INSERT;
			Object pom[] = {tip.getTip()};
			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			ps.executeUpdate();;
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(ps);
		}
	}
	public TipTransakcijeDTO getById(int id) {
		TipTransakcijeDTO tip = null;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_GET_BY_ID;
			Object pom[] = { id};

			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			rs = ps.executeQuery();
			while(rs.next()) {
				tip = new TipTransakcijeDTO(rs.getInt("Id"), rs.getString("Tip"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, ps);
		}
		
		return tip;
	}
}
