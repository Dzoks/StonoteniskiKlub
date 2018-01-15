package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import application.model.dao.TroskoviTurnirDAO;
import application.model.dto.TroskoviTurnirDTO;
import application.model.dto.TurnirDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MySQLTroskoviTurnirDAO implements TroskoviTurnirDAO{
	private static final String SQL_SELECT_ALL="select * from prikaz_troskovi_turnir";
	private static final String SQL_INSERT = "{call dodaj_troskovi_turnir(?,?,?,?,?)}";
	private static final String SQL_UPDATE = "{call update_troskovi_turnir(?,?,?,?)}";
	public  ObservableList<TroskoviTurnirDTO> SELECT_ALL() {
		ObservableList<TroskoviTurnirDTO> listaTroskoviTurnir = FXCollections.observableArrayList();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_ALL);
			while(rs.next()) {
				TurnirDTO turnir = new TurnirDTO(rs.getInt("turnirId"),rs.getString("naziv"),rs.getDate("turnirDatum"),false);
				listaTroskoviTurnir.add(new TroskoviTurnirDTO(rs.getInt("Id"), rs.getDate("Datum"), rs.getDouble("Iznos"), rs.getString("Opis"),rs.getString("Tip"),turnir));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, s);
		}
		
		return listaTroskoviTurnir;
	}
	public boolean INSERT(TroskoviTurnirDTO troskovi, TurnirDTO turnir) { //radi, vidjeti povratnu vrijednost
		//da li je uspio insert
		
		Connection c = null;
		java.sql.CallableStatement cs = null;
		
		try{
			c = ConnectionPool.getInstance().checkOut();
			cs = c.prepareCall(SQL_INSERT);
			cs.setDate("inDatum", new java.sql.Date(troskovi.getDatum().getTime()));
			cs.setDouble("inIznos", troskovi.getIznos().doubleValue());
			cs.setString("inOpis",troskovi.getOpis().getValue());
			cs.setInt("inTurnirId", turnir.getId());
			cs.registerOutParameter("outId", Types.INTEGER);
			cs.executeQuery();
			
			//while(rs.next())
			troskovi.setId(cs.getInt("outId"));
			//System.out.println("Id clanarine je "+id);
			
		}catch (SQLException e) {
			Alert alert = new Alert(AlertType.INFORMATION, e.getMessage());
			alert.showAndWait();
			return false;
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(cs);
		}
		return true;
	}
	public void UPDATE(TroskoviTurnirDTO trosak) {
		Connection c = null;
		java.sql.CallableStatement cs = null;
		
		try{
			c = ConnectionPool.getInstance().checkOut();
			cs = c.prepareCall(SQL_UPDATE);
			System.out.println(trosak.getId());
			if(trosak.getId()==null)
				cs.setNull("inId", Integer.MAX_VALUE);
			else
			cs.setInt("inId", trosak.getId());
			cs.setDate("inDatum", new java.sql.Date(trosak.getDatum().getTime()));
			cs.setDouble("inIznos", trosak.getIznos().doubleValue());
			cs.setString("inOpis",trosak.getOpis().getValue());
			cs.executeQuery();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(cs);
		}
	}
}
