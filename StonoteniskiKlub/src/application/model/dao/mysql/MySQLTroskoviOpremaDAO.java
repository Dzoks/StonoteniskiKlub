package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import application.model.dao.TroskoviOpremaDAO;
import application.model.dto.Narudzba;
import application.model.dto.TroskoviOpremaDTO;
import application.util.ConnectionPool;
import application.util.ErrorLogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MySQLTroskoviOpremaDAO implements TroskoviOpremaDAO {
	private static final String SQL_SELECT_ALL = "select * from prikaz_troskovi_oprema";
	private static final String SQL_INSERT = "{call dodaj_troskovi_oprema(?,?,?,?,?)}";
	private static final String SQL_UPDATE = "{call update_troskovi_oprema(?,?,?,?,?)}";

	public ObservableList<TroskoviOpremaDTO> SELECT_ALL() {
		ObservableList<TroskoviOpremaDTO> listaTroskoviOprema = FXCollections.observableArrayList();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_ALL);
			while (rs.next()) {
				Narudzba narudzba = new Narudzba(rs.getInt("Id"), rs.getDate("nDatum"), true, true,
						rs.getInt("DISTRIBUTER_OPREME_Id"));
				listaTroskoviOprema.add(new TroskoviOpremaDTO(rs.getInt("TRANSAKCIJA_Id"), rs.getDate("Datum"),
						rs.getDouble("Iznos"), rs.getString("Opis"), rs.getString("Tip"), narudzba));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, s);
		}

		return listaTroskoviOprema;
	}

	public boolean INSERT(TroskoviOpremaDTO troskovi, Narudzba narudzba) { // radi,
																			// vidjeti
																			// povratnu
																			// vrijednost
		// da li je uspio insert

		Connection c = null;
		java.sql.CallableStatement cs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			cs = c.prepareCall(SQL_INSERT);
			cs.setDate("inDatum", new java.sql.Date(troskovi.getDatum().getTime()));
			cs.setDouble("inIznos", troskovi.getIznos().doubleValue());
			cs.setString("inOpis", troskovi.getOpis().getValue());
			cs.setInt("inNarudzbaId", narudzba.getId());
			cs.registerOutParameter("outId", Types.INTEGER);
			try {
				cs.executeQuery();
			} catch (SQLException ex) {
				Alert alert = new Alert(AlertType.INFORMATION, ex.getMessage());
				alert.showAndWait();
				return false;
			}

			troskovi.setId(cs.getInt("outId"));
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(cs);
		}
		return true;
	}

	public void UPDATE(TroskoviOpremaDTO trosak, Narudzba narudzba) {
		Connection c = null;
		java.sql.CallableStatement cs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			cs = c.prepareCall(SQL_UPDATE);
			if (trosak.getId() == null)
				cs.setNull("inId", Integer.MAX_VALUE);
			else
				cs.setInt("inId", trosak.getId());
			cs.setDate("inDatum", new java.sql.Date(trosak.getDatum().getTime()));
			cs.setDouble("inIznos", trosak.getIznos().doubleValue());
			cs.setString("inOpis", trosak.getOpis().getValue());
			cs.setInt("inNarudzbaId", narudzba.getId());
			cs.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(cs);
		}
	}
}
