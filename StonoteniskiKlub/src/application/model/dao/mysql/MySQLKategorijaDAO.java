package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.model.dao.KategorijaDAO;
import application.model.dto.KategorijaDTO;
import application.util.ConnectionPool;
import application.util.ErrorLogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySQLKategorijaDAO implements KategorijaDAO {

	private static ObservableList<KategorijaDTO> list;

	public ObservableList<KategorijaDTO> getAll(boolean explicitQuery) {
		if (list == null || explicitQuery)
			list = getAllMySQL();
		return list;
	}

	public KategorijaDTO getById(Integer Id) {
		getAll(false);
		KategorijaDTO retValue = null;
		for (KategorijaDTO category : list)
			if (category.getId() == Id) {
				retValue = category;
				break;
			}
		return retValue;
	}

	public ObservableList<KategorijaDTO> getAllMySQL() {
		ObservableList<KategorijaDTO> list = FXCollections.observableArrayList();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = c.prepareStatement("select * from KATEGORIJA;");
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new KategorijaDTO(rs.getInt("Id"), rs.getString("Naziv"), rs.getString("Link")));
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

}
