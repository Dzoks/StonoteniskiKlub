package application.model.dao;

import application.model.dao.mysql.MySQLDAOFactory;

public abstract class DAOFactory {
	public abstract SponzorDAO getSponzorDAO();

	public abstract UgovorDAO getUgovorDAO();

	public abstract DonacijaDAO getDonacijaDAO();

	public abstract ZaposleniTipDAO getZaposleniTipDAO();

	public abstract ZaposlenjeDAO getZaposlenjeDAO();

	public abstract ZaposleniDAO getZaposleniDAO();
	
	public abstract StavkaSkupstinaDAO getStavkaSkupstinaDAO();
	
	public abstract SkupstinaDAO getSkupstinaDAO();
	
	public abstract DogadjajTipDAO getDogadjajTipDAO();
	
	public abstract DogadjajDAO getDogadjajDAO();
	
	public static DAOFactory getDAOFactory() {
		return MySQLDAOFactory.getInstance();
	}
}
