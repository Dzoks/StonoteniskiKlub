package application.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import application.model.dto.ClanarinaDTO;
import application.model.dto.OpremaTipDTO;
import application.util.ConnectionPool;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClanarinaDAO {
	
	public static final String SQL_SELECT_ALL = "select * from prikaz_clanarina";
	public static ObservableList<ClanarinaDTO> SELECT_ALL() {
		ObservableList<ClanarinaDTO> listaClanarina = FXCollections.observableArrayList();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_ALL);
			while(rs.next()) {
				listaClanarina.add(new ClanarinaDTO(rs.getInt("id"), new String(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("datum"))), rs.getDouble("iznos"), rs.getString("Opis"),rs.getString("tip"),rs.getInt("mjesec"),rs.getInt("godina"), rs.getString("ime"),rs.getString("prezime")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, s);
		}
		
		return listaClanarina;
	}
}
