package application.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.model.dto.KategorijaDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class KategorijaDAO {

	private static ObservableList<KategorijaDTO> list;
	
	public static ObservableList<KategorijaDTO> getAll(boolean explicitQuery){
		if (list==null || explicitQuery)
			list=getAllMySQL();
		return list;
	}
	
	public static KategorijaDTO getById(Integer Id) {
		getAll(false);
		KategorijaDTO retValue=null;
		for (KategorijaDTO category:list)
			if (category.getId()==Id) {
				retValue=category;
				break;
			}
		return retValue;
	}
	
	public static ObservableList<KategorijaDTO> getAllMySQL() {
		ObservableList<KategorijaDTO> list = FXCollections.observableArrayList();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = c.prepareStatement("select * from KATEGORIJA;");
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new KategorijaDTO(rs.getInt("Id"), rs.getString("Naziv"),rs.getString("Link")));
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
