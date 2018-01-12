package application.model.dao;

import application.model.dao.mysql.MySQLDAOFactoryTransakcije;

public abstract class DAOFactoryTransakcije {
	
	private static MySQLDAOFactoryTransakcije factory;
	public abstract ClanarinaDAO getClanarinaDAO();
	public abstract PlataDAO getPlataDAO();
	public abstract TransakcijaDAO getTransakcijaDAO();
	public abstract NovcanaSredstvaDAO getNovcanaSredstvaDAO();
	public abstract TipTransakcijeDAO getTipTransakcijeDAO();
	public abstract TroskoviOpremaDAO getTroskoviOpremaDAO();
	public abstract TroskoviTurnirDAO getTroskoviTurnirDAO();
	public abstract UplataZaTurnirDAO getUplataZaTurnirDAO();
	public static DAOFactoryTransakcije getDAOFactory() {
		if(factory==null) {
			factory = new MySQLDAOFactoryTransakcije();
		}
		return factory;
	}
}
