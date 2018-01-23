package application.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.model.dto.TreningDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TreningDAO {

	public static boolean deactivate(TreningDTO trening) {
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = c.prepareStatement("update TRENING set Aktivan=? where Id=?;");
			ps.setBoolean(1,false);
			ps.setInt(2, trening.getId());
			ps.executeUpdate();
			return true;
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			ConnectionPool.close( ps);
			ConnectionPool.getInstance().checkIn(c);
		}
	}
	public static ObservableList<TreningDTO> selectByMember(Integer CLAN_Id) {
		ObservableList<TreningDTO> list = FXCollections.observableArrayList();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = c.prepareStatement("select * from TRENING where CLAN_Id=? and Aktivan=true;");
			ps.setInt(1, CLAN_Id);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new TreningDTO(rs.getInt("Id"), rs.getString("Opis"), CLAN_Id,
						rs.getDate("Datum").toLocalDate(),rs.getBoolean("Aktivan")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(rs,ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return list;
	}
	
	public static boolean insert(TreningDTO trening) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = c.prepareStatement("insert into TRENING (Opis,Datum,CLAN_Id) values (?,?,?);",PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setInt(3, trening.getCLAN_Id());
			ps.setString(1, trening.getOpis());
			ps.setDate(2, Date.valueOf(trening.getDatum()));
			ps.executeUpdate();
			rs=ps.getGeneratedKeys();
			if (rs.next()) {
				trening.setId(rs.getInt(1));
			}
			return true;
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
	}
	
	public static boolean update(TreningDTO trening) {
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = c.prepareStatement("update TRENING set Opis=?,Datum=?,CLAN_Id=? where Id=?;");
			ps.setInt(3, trening.getCLAN_Id());
			ps.setString(1, trening.getOpis());
			ps.setDate(2, Date.valueOf(trening.getDatum()));
			ps.setInt(4, trening.getId());
			ps.executeUpdate();
			return true;
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			ConnectionPool.close( ps);
			ConnectionPool.getInstance().checkIn(c);
		}
	}
}
