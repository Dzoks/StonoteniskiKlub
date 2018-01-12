package application.model.dao;

import application.model.dao.mysql.MySQLDAOFactory;

public abstract class DAOFactory {
	
	
	public abstract ClanDAO getClanDAO();
	
	public abstract ClanstvoDAO getClanstvoDAO();
	
	public abstract OsobaDAO getOsobaDAO();
	
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
}
