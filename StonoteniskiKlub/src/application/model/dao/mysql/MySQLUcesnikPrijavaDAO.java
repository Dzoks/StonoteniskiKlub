package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import application.model.dao.UcesnikPrijavaDAO;
import application.model.dto.UcesnikPrijavaDTO;
import application.util.ConnectionPool;
import application.util.ErrorLogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySQLUcesnikPrijavaDAO implements UcesnikPrijavaDAO {
	private final static String SQL_GET_BY_JMB = "select * from UCESNIK_PRIJAVA u left join OSOBA o on u.OSOBA_Id=o.Id where o.JMB=?"
			+ " order by u.TURNIR_KATEGORIJA_Id desc limit 1";
	private final static String SQL_INSERT = "{call prijaviUcesnika(?,?,?,?,?,?,?,?,?)}";
	public static final String SQL_UPDATE = "update UCESNIK_PRIJAVA u inner join OSOBA o on u.OSOBA_Id = o.Id set o.Ime=?,"
			+ "o.Prezime=?,o.DatumRodjenja=? where u.Id=?";
	private final static String SQL_ADD_NEW = "{call dodajPrijavu(?,?,?,?,?)}";
	private final static String SQL_SELECT_ALL1 = "select * from UCESNIK_PRIJAVA u inner join OSOBA o where u.OSOBA_Id=o.Id";
	private final static String SQL_DOES_EXIST="select count(*) from UCESNIK_PRIJAVA u inner join OSOBA o on u.OSOBA_Id=o.Id " 
			+ "where o.JMB=? and u.TURNIR_Id=? and u.TURNIR_KATEGORIJA_Id=?";
	
	public UcesnikPrijavaDTO getByJmb(String jmb) {
		UcesnikPrijavaDTO retVal = new UcesnikPrijavaDTO();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_GET_BY_JMB;
			Object pom[] = { jmb };

			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			rs = ps.executeQuery();
			if (rs.next()) {
				retVal = new UcesnikPrijavaDTO(rs.getInt("Id"), rs.getInt("TURNIR_Id"),
						rs.getInt("TURNIR_KATEGORIJA_Id"), rs.getDate("Datum"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return retVal;
	}

	public Integer insert(String jmb, String ime, String prezime, Character pol, Date datumRodjenja, Integer idTurnira,
			Integer idKategorije, Date datum) {
		Integer retVal = 0;
		Connection c = null;
		java.sql.CallableStatement cst = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_INSERT;
			cst = c.prepareCall(query);
			cst.setString(1, jmb);
			cst.setString(2, ime);
			cst.setString(3, prezime);
			cst.setString(4, pol.toString());
			cst.setDate(5, datumRodjenja);
			cst.setInt(6, idTurnira);
			cst.setInt(7, idKategorije);
			cst.setDate(8, datum);
			cst.registerOutParameter(9, Types.INTEGER);
			cst.execute();
			retVal = cst.getInt(9);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.close(cst);
			ConnectionPool.getInstance().checkIn(c);
		}
		return retVal;
	}

	public boolean izmjeniUcesnika(Integer idPrijave, String ime, String prezime, Date datum) {
		boolean retVal = false;
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_UPDATE;
			ps = c.prepareStatement(query);
			ps.setString(1, ime);
			ps.setString(2, prezime);
			ps.setDate(3, datum);
			ps.setInt(4, idPrijave);
			retVal = ps.executeUpdate() == 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return retVal;
	}

	public Integer addNew(Integer idTurnira, Integer idKategorije, Integer idOsobe, Date datum) {
		Integer retVal = 0;
		Connection c = null;
		java.sql.CallableStatement cst = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_ADD_NEW;
			cst = c.prepareCall(query);
			cst.setInt(1, idTurnira);
			cst.setInt(2, idKategorije);
			cst.setInt(3, idOsobe);
			cst.setDate(4, datum);
			cst.registerOutParameter(5, Types.INTEGER);
			cst.execute();
			retVal = cst.getInt(5);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.close(cst);
			ConnectionPool.getInstance().checkIn(c);
		}
		return retVal;
	}

	public ObservableList<UcesnikPrijavaDTO> SELECT_ALL() {// dodala Helena
		ObservableList<UcesnikPrijavaDTO> retVal = FXCollections.observableArrayList();
		Connection c = null;
		java.sql.Statement s = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_ALL1);
			while (rs.next()) {
				retVal.add(new UcesnikPrijavaDTO(rs.getInt("OSOBA_Id"), rs.getString("Ime"), rs.getString("Prezime"),
						rs.getString("JMB"), rs.getString("Pol").charAt(0), rs.getDate("DatumRodjenja"),
						rs.getInt("Id"), rs.getInt("TURNIR_Id"), rs.getInt("TURNIR_KATEGORIJA_Id"),
						rs.getDate("Datum")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			ConnectionPool.close(rs, s);
			ConnectionPool.getInstance().checkIn(c);
		}

		return retVal;
	}
	
	public boolean doesExist(String jmb,Integer idTurnira,Integer idKategorije){ 
		boolean retVal=false;
		Connection c=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			c=ConnectionPool.getInstance().checkOut();
			String query=SQL_DOES_EXIST;
			Object pom[] = { jmb,idTurnira,idKategorije };
			ps=ConnectionPool.prepareStatement(c, query, false, pom);
			rs=ps.executeQuery();
			if(rs.next()){
				retVal=rs.getInt(1)>0; 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionPool.close(rs, ps); 
			ConnectionPool.getInstance().checkIn(c); 
		}		 
		return retVal; 
 	} 
}
