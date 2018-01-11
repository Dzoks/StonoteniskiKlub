package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import application.model.dao.ClanstvoDAO;
import application.model.dto.ClanstvoDTO;
import application.util.ConnectionPool;

public class MySQLClanstvoDAO implements ClanstvoDAO{
	private final static String SQL_GET_BY_ID = "SELECT * FROM CLANSTVO WHERE CLAN_OSOBA_ID=?";
	private final static String SQL_INSERT = "INSERT INTO CLANSTVO VALUES(?, ?, ?)";
	
	public List<ClanstvoDTO> getByClanId(int id) {
		ArrayList<ClanstvoDTO> retVal = new ArrayList<>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_GET_BY_ID;
			Object pom[] = { id };
			
			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			rs = ps.executeQuery();
			while (rs.next())
				retVal.add(new ClanstvoDTO(rs.getDate("DatumOd"), rs.getDate("DatumDo"), rs.getInt("CLAN_OSOBA_Id")));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}

		return retVal;
	}
	
	public boolean insert(int clanId) {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_INSERT;
			Object pom[] = { new Date(), null, clanId };
			
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
