package application.model.dao.mysql;

import application.model.dao.ClanDAO;
import application.model.dao.ClanstvoDAO;
import application.model.dao.DAOFactory;
import application.model.dao.DistributerOpremeDAO;
import application.model.dao.DonacijaDAO;
import application.model.dao.OsobaDAO;
import application.model.dao.SponzorDAO;
import application.model.dao.UgovorDAO;

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
	public ClanDAO getClanDAO() {
		return new MySQLClanDAO();
	}
	@Override
	public ClanstvoDAO getClanstvoDAO() {
		return new MySQLClanstvoDAO();
	}
	@Override
	public OsobaDAO getOsobaDAO() {
		return new MySQLOsobaDAO();
	}
	
	@Override
	public DistributerOpremeDAO getDistributerOpremeDAO() {
		return new MySQLDistributerOpremeDAO();
	}
	
	@Override
	public NarudzbaDAO getNarudzbaDAO() {
		return new MySQLNarudzbaDAO();
	}
	
	@Override
	public NarudzbaStavkaDAO getNarudzbaStavkaDAO() {
		return new MySQLNarudzbaStavkaDAO();
	}
	
	@Override
	public OpremaClanaDAO getOpremaClanaDAO() {
		return new MySQLOpremaClanaDAO();
	}
	
	@Override
	public OpremaDAO getOpremaDAO() {
		return new MySQLOpremaDAO();
	}
	
	@Override
	public OpremaKlubaDAO getOpremaKlubaDAO() {
		return new MySQLOpremaKlubaDAO();
	}
	
	@Override
	public OpremaTipDAO getOpremaTipDAO() {
		return new MySQLOpremaTipDAO();
	}
}
