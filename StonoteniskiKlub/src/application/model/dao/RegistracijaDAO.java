package application.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import application.model.dto.ClanDTO;
import application.model.dto.KategorijaDTO;
import application.model.dto.RegistracijaDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RegistracijaDAO {

	private static final String SQL_GETALL_MEMBER = "select * from REGISTRACIJA where CLAN_Id=?;";
	private static final String SQL_GETALL_SEASON = "select * from REGISTRACIJA where Sezona=? and KATEGORIJA_Id=?;";
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
}
