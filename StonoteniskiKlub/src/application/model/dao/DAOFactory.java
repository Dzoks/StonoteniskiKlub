package application.model.dao;

import application.model.dao.mysql.MySQLDAOFactory;

public abstract class DAOFactory {
	public abstract SponzorDAO getSponzorDAO();

	public abstract UgovorDAO getUgovorDAO();

	public abstract DonacijaDAO getDonacijaDAO();
	
	public static DAOFactory getDAOFactory(){
		return MySQLDAOFactory.getInstance();
	}
}
