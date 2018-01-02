package application.model.dao;

import application.model.dao.mysql.MySQLDAOFactory;

public abstract class DAOFactory {
	public abstract SponzorDAO getSponzorDAO();

	public abstract UgovorDAO getUgovorDAO();

	public abstract DonacijaDAO getDonacijaDAO();
	
	public abstract DistributerOpremeDAO getDistributerOpremeDAO();
	
	public abstract NarudzbaDAO getNarudzbaDAO();
	
	public abstract NarudzbaStavkaDAO getNarudzbaStavkaDAO();
	
	public abstract OpremaClanaDAO getOpremaClanaDAO();
	
	public abstract OpremaDAO getOpremaDAO();
	
	public abstract OpremaKlubaDAO getOpremaKlubaDAO();
	
	public abstract OpremaTipDAO getOpremaTipDAO();
	
	public static DAOFactory getDAOFactory(){
		return MySQLDAOFactory.getInstance();
	}
}
