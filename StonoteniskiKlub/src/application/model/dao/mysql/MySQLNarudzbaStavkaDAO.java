package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.model.dao.NarudzbaStavkaDAO;
import application.model.dto.NarudzbaStavka;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySQLNarudzbaStavkaDAO implements NarudzbaStavkaDAO{

	private static final String SQL_SELECT_BY_IDNARUDZBE = "SELECT * FROM narudzba_stavka WHERE NARUDZBA_Id=?";
	private static final String SQL_INSERT = "INSERT INTO narudzba_stavka VALUES (?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE = "DELETE FROM narudzba_stavka WHERE NARUDZBA_Id=? AND OPREMA_TIP_Id=? AND Velicina=?";
	private final static String SQL_UPDATE_OBRADJENO = "UPDATE narudzba_stavka SET Obradjeno=true WHERE NARUDZBA_Id=? AND OPREMA_TIP_Id=? AND Velicina=?";
	
	public ObservableList<NarudzbaStavka> SELECT_BY_IDNARUDZBE(Integer id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ObservableList<NarudzbaStavka> listaStavkiNarudzbe = FXCollections.observableArrayList();
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_SELECT_BY_IDNARUDZBE, false, id);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				listaStavkiNarudzbe.add(new NarudzbaStavka(rs.getInt("NARUDZBA_Id"), rs.getInt("OPREMA_TIP_Id"), rs.getString("Velicina"), rs.getInt("Kolicina"), rs.getDouble("Cijena"), rs.getBoolean("Obradjeno")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, ps);
		}
		
		return listaStavkiNarudzbe;
	}
	
	public void INSERT(NarudzbaStavka narudzbaStavka) {
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
	
	public void DELETE(NarudzbaStavka narudzbaStavka) {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_DELETE, false, narudzbaStavka.getIdNarudzbe() , narudzbaStavka.getIdTipaOpreme(), narudzbaStavka.getVelicina());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(ps);
		}
	}
	
	public void UPDATE_OBRADJENO(NarudzbaStavka stavkaNarudzbe) {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = ConnectionPool.prepareStatement(c, SQL_UPDATE_OBRADJENO, false, stavkaNarudzbe.getIdNarudzbe(), stavkaNarudzbe.getIdTipaOpreme(), stavkaNarudzbe.getVelicina());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(ps);
		}
	}
}
