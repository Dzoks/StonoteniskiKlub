package application.model.dao.mysql;

import application.model.dao.DAOFactory;
import application.model.dao.DonacijaDAO;
import application.model.dao.SkupstinaDAO;
import application.model.dao.SponzorDAO;
import application.model.dao.StavkaSkupstinaDAO;
import application.model.dao.UgovorDAO;
import application.model.dao.ZaposleniDAO;
import application.model.dao.ZaposleniTipDAO;
import application.model.dao.ZaposlenjeDAO;

public class MySQLDAOFactory extends DAOFactory{
	
	private static MySQLDAOFactory instance = null;
	
	protected MySQLDAOFactory(){	
	}
	public static MySQLDAOFactory getInstance(){
		if(instance == null){
			instance = new MySQLDAOFactory();
		}
		return instance;
	}
	
	@Override
	public SponzorDAO getSponzorDAO() {
		return new MySQLSponzorDAO();
	}

	@Override
	public UgovorDAO getUgovorDAO() {
		return new MySQLUgovorDAO();
	}

	@Override
	public DonacijaDAO getDonacijaDAO() {
		return new MySQLDonacijaDAO();
	}
	@Override
	public ZaposleniTipDAO getZaposleniTipDAO() {
		return new MySQLZaposleniTipDAO();
	}
	@Override
	public ZaposlenjeDAO getZaposlenjeDAO() {
		return new MySQLZaposlenjeDAO();
	}
	@Override
	public ZaposleniDAO getZaposleniDAO() {
		return new MySQLZaposleniDAO();
	}
	@Override
	public StavkaSkupstinaDAO getStavkaSkupstinaDAO() {
		return MySQLStavkaSkupstinaDAO.getInstance();
	}
	@Override
	public SkupstinaDAO getSkupstinaDAO() {
		return MySQLSkupstinaDAO.getInstance();
	}
}
