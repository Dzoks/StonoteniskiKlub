package application.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.model.dto.OpremaKlubaDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OpremaKlubaDAO {

	private static final String SQL_SELECT_ALL = "SELECT * FROM prikaz_opreme_kluba WHERE Aktivan=true";
	
	public static ObservableList<OpremaKlubaDTO> SELECT_ALL() {
		ObservableList<OpremaKlubaDTO> listaOpreme = FXCollections.observableArrayList();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_ALL);
			
			while(rs.next()) {
				listaOpreme.add(new OpremaKlubaDTO(rs.getInt("Id"), rs.getInt("NARUDZBA_Id"), rs.getInt("OPREMA_TIP_Id"), rs.getInt("DONACIJA_Id"), rs.getBoolean("Donirana"), rs.getString("Opis"), rs.getBoolean("Aktivan")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, s);
		}
		
		return listaOpreme;
	}
}
