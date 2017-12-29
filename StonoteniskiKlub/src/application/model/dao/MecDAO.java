package application.model.dao;

public class MecDAO {
	private final static String SQL_GET_ALL="select * from MEC";
	private final static String SQL_GET_BY_ID="select * from MEC where Id=?";
	private final static String SQL_INSERT="insert into MEC (Naziv,Datum) values (?,?)";
	
	
}
