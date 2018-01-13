package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import application.model.dao.DAOFactory;
import application.model.dao.PlataDAO;
import application.model.dto.PlataDTO;
import application.model.dto.ZaposleniDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MySQLPlataDAO implements PlataDAO{
	private static final String SQL_SELECT_ALL="select * from prikaz_plata";
	private static final String SQL_INSERT = "{call dodaj_platu(?,?,?,?,?)}";
	private static final String SQL_UPDATE = "{call update_platu(?,?,?,?,?)}";
	public  ObservableList<PlataDTO> SELECT_ALL() {
		ObservableList<PlataDTO> listaPlata = FXCollections.observableArrayList();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		
		try {
			c = ConnectionPool.getInstance().checkOut();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_ALL);
			while(rs.next()) {
				ZaposleniDTO zaposleni = new ZaposleniDTO(rs.getInt("OSOBA_Id"),
						rs.getString("Ime"),
						rs.getString("Prezime"), 
						rs.getString("ImeRoditelja"),
						rs.getString("JMB"),
						rs.getString("Pol").charAt(0),
						rs.getDate("DatumRodjenja"),
						rs.getBlob("Fotografija"),
						DAOFactory.getDAOFactory().getOsobaDAO().getTelefoni(rs.getInt("Id")),
						rs.getBoolean("Aktivan"), 
						DAOFactory.getDAOFactory().getZaposlenjeDAO().selectAllById(rs.getInt("OSOBA_Id")));
				listaPlata.add(new PlataDTO(rs.getInt("Id"), rs.getDate("Datum"), rs.getDouble("Iznos"), rs.getString("Opis"),rs.getString("Tip"),zaposleni));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(rs, s);
		}
		
		return listaPlata;
	}
	
	
	public  boolean INSERT(PlataDTO plata, ZaposleniDTO zaposleni) {
		Connection c = null;
		java.sql.CallableStatement cs = null;
		
		try{
			c = ConnectionPool.getInstance().checkOut();
			cs = c.prepareCall(SQL_INSERT);
			cs.setDate("inDatum", new java.sql.Date(plata.getDatum().getTime()));
			cs.setDouble("inIznos", plata.getIznos().doubleValue());
			cs.setString("inOpis",plata.getOpis().getValue());
			cs.setInt("inZaposleniId", zaposleni.getId());
			cs.registerOutParameter("outId", Types.INTEGER);
			cs.executeQuery();
			plata.setId(cs.getInt("outId"));
		}catch (SQLException e) {
			Alert alert = new Alert(AlertType.INFORMATION, "Neuspjesno dodavanje!");
			alert.showAndWait();
			return false;
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(cs);
		}
		return true;
	}
	
	public  void UPDATE(PlataDTO plata, ZaposleniDTO zaposleni) {//ne radi nijedan update kod novih
		//objekata je ne znam id
		Connection c = null;
		java.sql.CallableStatement cs = null;
		
		try{
			c = ConnectionPool.getInstance().checkOut();
			cs = c.prepareCall(SQL_UPDATE);
			System.out.println(plata.getId());
			if(plata.getId()==null)
				cs.setNull("inId",Integer.MAX_VALUE); //ne valja
			else
				cs.setInt("inId", plata.getId());
			cs.setDate("inDatum", new java.sql.Date(plata.getDatum().getTime()));
			cs.setDouble("inIznos", plata.getIznos().doubleValue());
			cs.setString("inOpis", plata.getOpis().getValue());
			cs.setInt("inOsobaId", zaposleni.getId());
			cs.executeQuery();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.getInstance().checkIn(c);
			ConnectionPool.close(cs);
		}
	}
	
}
