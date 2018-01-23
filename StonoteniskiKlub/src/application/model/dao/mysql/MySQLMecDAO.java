package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.model.dao.DAOFactory;
import application.model.dao.MecDAO;
import application.model.dto.MecDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySQLMecDAO implements MecDAO{
	private final static String SQL_GET_ALL="select * from MEC where RUNDA_ZRIJEB_Id=? and RUNDA_Broj=?";
	private final static String SQL_INSERT_SINGLE="update MEC set TIM1_Id=?,Rezultat='4-0' where RUNDA_ZRIJEB_Id=? and RUNDA_Broj=? and RedniBroj=?";
	private final static String SQL_INSERT="update MEC set TIM1_Id=?,TIM2_Id=? where RUNDA_ZRIJEB_Id=? and RUNDA_Broj=? and RedniBroj=?";
	private final static String SQL_INSERT_REZULTAT="update MEC set Rezultat=? where RUNDA_ZRIJEB_Id=? and RUNDA_Broj=? and RedniBroj=?";
	
	public ObservableList<MecDTO> getAllSingle(Integer idZrijeba,Integer brojRunde) {
		ObservableList<MecDTO> retVal = FXCollections.observableArrayList();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_GET_ALL;
			Object pom[] = { idZrijeba,brojRunde };
			
			ps = ConnectionPool.prepareStatement(c, query, false,pom);
			rs = ps.executeQuery();
			while (rs.next()){
				MecDTO mec=new MecDTO(idZrijeba, brojRunde, rs.getInt("RedniBroj"),rs.getString("Rezultat"), rs.getInt("TIM1_Id"), rs.getInt("TIM2_Id"));
				retVal.add(mec);
				mec.setPrikazMeca(DAOFactory.getDAOFactory().getTimDAO().getSingleById(mec.getIdPrvogTima())+"\n"+DAOFactory.getDAOFactory().getTimDAO().getSingleById(mec.getIdDrugogTima()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return retVal;
	}
	
	public ArrayList<MecDTO> getAllList(Integer idZrijeba,Integer brojRunde) {
		ArrayList<MecDTO> retVal = new ArrayList<>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_GET_ALL;
			Object pom[] = { idZrijeba,brojRunde };
			
			ps = ConnectionPool.prepareStatement(c, query, false,pom);
			rs = ps.executeQuery();
			while (rs.next()){
				MecDTO mec=new MecDTO(idZrijeba, brojRunde, rs.getInt("RedniBroj"),rs.getString("Rezultat"), rs.getInt("TIM1_Id"), rs.getInt("TIM2_Id"));
				retVal.add(mec);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return retVal;
	}
	
	public ObservableList<MecDTO> getAllDouble(Integer idZrijeba,Integer brojRunde) {
		ObservableList<MecDTO> retVal = FXCollections.observableArrayList();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_GET_ALL;
			Object pom[] = { idZrijeba,brojRunde};
			
			ps = ConnectionPool.prepareStatement(c, query, false,pom);
			rs = ps.executeQuery();
			while (rs.next()){
				MecDTO mec=new MecDTO(idZrijeba, brojRunde, rs.getInt("RedniBroj"),rs.getString("Rezultat"), rs.getInt("TIM1_Id"), rs.getInt("TIM2_Id"));
				retVal.add(mec);
				mec.setPrikazMeca(DAOFactory.getDAOFactory().getTimDAO().getDoubleById(mec.getIdPrvogTima())+"\n"+DAOFactory.getDAOFactory().getTimDAO().getDoubleById(mec.getIdDrugogTima()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(rs, ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return retVal;
	}
	
	public boolean insertRezultat(MecDTO mec){
		boolean retVal=false;
		Connection c = null;
		PreparedStatement ps=null;	
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_INSERT_REZULTAT;
			ps=c.prepareStatement(query);
			ps.setString(1, mec.getRezultat());
			ps.setInt(2, mec.getIdZrijeba());
			ps.setInt(3, mec.getBrojRunde());
			ps.setInt(4, mec.getRedniBroj());
			retVal=ps.executeUpdate()==1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return retVal;
	}
	
	public boolean insertSingle(Integer idTim1,Integer idZrijeba,Integer brojRunde,Integer redniBroj){
		boolean retVal=false;
		Connection c = null;
		PreparedStatement ps=null;	
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_INSERT_SINGLE;
			ps=c.prepareStatement(query);
			ps.setInt(1, idTim1);
			ps.setInt(2, idZrijeba);
			ps.setInt(3, brojRunde);
			ps.setInt(4, redniBroj);
			retVal=ps.executeUpdate()==1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return retVal;
	}
	
	public boolean insert(Integer idTim1,Integer idTim2,Integer idZrijeba,Integer brojRunde,Integer redniBroj){
		boolean retVal=false;
		Connection c = null;
		PreparedStatement ps=null;	
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_INSERT;
			ps=c.prepareStatement(query);
			ps.setInt(1, idTim1);
			ps.setInt(2, idTim2);
			ps.setInt(3, idZrijeba);
			ps.setInt(4, brojRunde);
			ps.setInt(5, redniBroj);
			retVal=ps.executeUpdate()==1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return retVal;
	}	
}
