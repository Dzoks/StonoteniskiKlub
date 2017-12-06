package application.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.model.dto.NarudzbaStavkaDTO;
import application.model.dto.OpremaTipDTO;
import application.util.ConnectionPool;

public class NarudzbaStavkaDAO {

	private static final String SQL_INSERT = "INSERT INTO narudzba_stavka VALUES (?, ?, ?, ?, ?, ?, ?)";
	
	public static void INSERT(NarudzbaStavkaDTO narudzbaStavka) {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_INSERT, false, narudzbaStavka.getIdNarudzbe() , narudzbaStavka.getIdTipaOpreme(), narudzbaStavka.getVelicina(), narudzbaStavka.getKolicina(), narudzbaStavka.getCijena(), narudzbaStavka.getObradjeno());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(ps);
		}
	}
}
