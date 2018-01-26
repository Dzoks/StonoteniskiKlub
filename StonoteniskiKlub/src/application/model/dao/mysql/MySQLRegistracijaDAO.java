package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;

import application.model.dao.DAOFactory;
import application.model.dao.RegistracijaDAO;
import application.model.dto.ClanDTO;
import application.model.dto.KategorijaDTO;
import application.model.dto.RegistracijaDTO;
import application.util.ConnectionPool;
import application.util.ErrorLogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySQLRegistracijaDAO implements RegistracijaDAO {

	public static final String[] tournaments = { "Plasman", "A", "A1", "B", "B1", "C", "C1", "D", "D1", "E", "E1", "F",
			"F1", "Kup", "PrLiga", "PlayOff", "Ukupno" };
	private static final String SQL_GETALL_MEMBER = "select * from REGISTRACIJA where CLAN_Id=?;";
	private static final String SQL_GETALL_SEASON = "select * from REGISTRACIJA where Sezona=? and KATEGORIJA_Id=?;";
	private static final String SQL_UPDATE = "update REGISTRACIJA set Datum=?," + "Plasman=?," + "A=?," + "A1=?,"
			+ "B=?," + "B1=?," + "C=?," + "C1=?," + "D=?," + "D1=?," + "E=?," + "E1=?," + "F=?," + "F1=?," + "Kup=?,"
			+ "`Pr.Liga`=?," + "`Play-off`=?," + "Ukupno=? where Sezona=? and CLAN_Id=? and KATEGORIJA_Id=?;";
	private static String SQL_INSERT = "insert into REGISTRACIJA (Sezona, Datum, KATEGORIJA_Id, CLAN_Id) values(?,?,?,?)";

	public ObservableList<RegistracijaDTO> getAllByMember(ClanDTO member) {
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
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return list;
	}

	// 20,21
	public ObservableList<RegistracijaDTO> getAllBySeason(String sezona, KategorijaDTO kategorija) {
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
				int clanId = rs.getInt("CLAN_Id");
				ClanDTO member = DAOFactory.getDAOFactory().getClanDAO().getById(clanId);
				if (member.isAktivan()) {
					HashMap<String, Integer> hashMap = new HashMap<>();
					hashMap.put(rs.getMetaData().getColumnName(4), rs.getInt(4));
					for (int i = 6; i <= rs.getMetaData().getColumnCount(); i++)
						hashMap.put(rs.getMetaData().getColumnName(i), rs.getInt(i));
					list.add(new RegistracijaDTO(rs.getInt("CLAN_Id"), sezona, kategorija.getId(),
							rs.getDate("Datum").toLocalDate(), hashMap, member));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return list;
	}

	public boolean update(RegistracijaDTO trening) {
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = c.prepareStatement(SQL_UPDATE);
			ps.setDate(1, Date.valueOf(trening.getDatum()));
			ps.setInt(21, trening.getKATEGORIJA_Id());
			ps.setString(19, trening.getSezona());
			ps.setInt(20, trening.getCLAN_Id());
			for (int i = 0, j = 2; i < tournaments.length; i++, j++) {
				Integer point = trening.getRezultati().get(tournaments[i]);
				if (point != null)
					ps.setInt(j, point);
				else
					ps.setNull(j, Types.INTEGER);
			}
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
			return false;
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
	}

	public boolean insert(RegistracijaDTO registracija) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = ConnectionPool.prepareStatement(connection, SQL_INSERT, false, registracija.getSezona(),
					Date.valueOf(registracija.getDatum()), registracija.getKATEGORIJA_Id(), registracija.getCLAN_Id());
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(connection);
			ConnectionPool.close(statement);
		}
		return false;
	}
}
