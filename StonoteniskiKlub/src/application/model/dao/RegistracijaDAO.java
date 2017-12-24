package application.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;

import application.model.dto.ClanDTO;
import application.model.dto.KategorijaDTO;
import application.model.dto.RegistracijaDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RegistracijaDAO {

	public static final String[] tournaments= {"Plasman","A","A1","B","B1","C","C1","D","D1","E","E1","F","F1","Kup","Pr.Liga","Play-off","Ukupno"};
	private static final String SQL_GETALL_MEMBER = "select * from REGISTRACIJA where CLAN_Id=?;";
	private static final String SQL_GETALL_SEASON = "select * from REGISTRACIJA where Sezona=? and KATEGORIJA_Id=?;";
	private static final String SQL_UPDATE= "update REGISTRACIJA set"
			+ "Datum=?,"
			+ "Plasman=?,"
			+ "A=?,"
			+ "A1=?,"
			+ "B=?,"
			+ "B1=?,"
			+ "C=?,"
			+ "C1=?,"
			+ "D=?,"
			+ "D1=?,"
			+ "E=?,"
			+ "E1=?,"
			+ "F=?,"
			+ "F1=?,"
			+ "Kup=?,"
			+ "Pr.Liga=?,"
			+ "Play-Off=?,"
			+ "Ukupno=?"
			+ "where Sezona=? and CLAN_Id=? and KATEGORIJA_Id=?";
	public static ObservableList<RegistracijaDTO> getAllByMember(ClanDTO member) {
		ObservableList<RegistracijaDTO> list = FXCollections.observableArrayList();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = c.prepareStatement(SQL_GETALL_MEMBER);
			ps.setInt(1, member.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Integer> hashMap = new HashMap<>();
				hashMap.put(rs.getMetaData().getColumnName(4), rs.getInt(4));
				for (int i = 6; i <= rs.getMetaData().getColumnCount(); i++)
					hashMap.put(rs.getMetaData().getColumnName(i), rs.getInt(i));
				list.add(new RegistracijaDTO(member.getId(), rs.getString("Sezona"), rs.getInt("KATEGORIJA_Id"),
						rs.getDate("Datum").toLocalDate(), hashMap, member));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return list;
	}
	//20,21
	public static ObservableList<RegistracijaDTO> getAllBySeason(String sezona,KategorijaDTO kategorija) {
		ObservableList<RegistracijaDTO> list = FXCollections.observableArrayList();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = c.prepareStatement(SQL_GETALL_SEASON);
			ps.setString(1, sezona);
			ps.setInt(2, kategorija.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Integer> hashMap = new HashMap<>();
				hashMap.put(rs.getMetaData().getColumnName(4), rs.getInt(4));
				for (int i = 6; i <= rs.getMetaData().getColumnCount(); i++)
					hashMap.put(rs.getMetaData().getColumnName(i), rs.getInt(i));
				list.add(new RegistracijaDTO(rs.getInt("CLAN_Id"), sezona, kategorija.getId(),
						rs.getDate("Datum").toLocalDate(), hashMap, ClanDAO.getById(rs.getInt("CLAN_Id"))));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return list;
	}
	
	public static boolean update(RegistracijaDTO trening) {
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = c.prepareStatement(SQL_UPDATE);
			ps.setDate(1, Date.valueOf(trening.getDatum()));
			ps.setInt(21, trening.getKATEGORIJA_Id());
			ps.setString(19, trening.getSezona());
			ps.setInt(20, trening.getCLAN_Id());
			for (int i=0,j=3;i<tournaments.length;i++,j++) {
				Integer point=trening.getRezultati().get(tournaments[i]);
				if (point!=null)
					ps.setInt(j, point);
				else
					ps.setNull(j,Types.INTEGER);
			}
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
