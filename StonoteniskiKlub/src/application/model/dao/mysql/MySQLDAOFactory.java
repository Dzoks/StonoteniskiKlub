package application.model.dao.mysql;

import application.model.dao.ClanDAO;
import application.model.dao.ClanarinaDAO;
import application.model.dao.ClanstvoDAO;
import application.model.dao.DAOFactory;
import application.model.dao.DistributerOpremeDAO;
import application.model.dao.DogadjajDAO;
import application.model.dao.DogadjajTipDAO;
import application.model.dao.DonacijaDAO;
import application.model.dao.NarudzbaDAO;
import application.model.dao.NarudzbaStavkaDAO;
import application.model.dao.NovcanaSredstvaDAO;
import application.model.dao.OpremaClanaDAO;
import application.model.dao.OpremaDAO;
import application.model.dao.OpremaKlubaDAO;
import application.model.dao.OpremaTipDAO;
import application.model.dao.OsobaDAO;
import application.model.dao.PlataDAO;
import application.model.dao.SkupstinaDAO;
import application.model.dao.SponzorDAO;
import application.model.dao.StavkaSkupstinaDAO;
import application.model.dao.TipTransakcijeDAO;
import application.model.dao.TransakcijaDAO;
import application.model.dao.TroskoviOpremaDAO;
import application.model.dao.TroskoviTurnirDAO;
import application.model.dao.UgovorDAO;
import application.model.dao.UplataZaTurnirDAO;
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
	@Override
	public DogadjajTipDAO getDogadjajTipDAO() {
		return MySQLDogadjajTipDAO.getInstance();
	}
	@Override
	public DogadjajDAO getDogadjajDAO() {
		return MySQLDogadjajDAO.getInstance();
	}
	@Override
	public ClanarinaDAO getClanarinaDAO() {
		return new MySQLClanarinaDAO();
	}
	@Override
	public PlataDAO getPlataDAO() {
		return new MySQLPlataDAO();
	}
	@Override
	public NovcanaSredstvaDAO getNovcanaSredstvaDAO() {
		// TODO Auto-generated method stub
		return new MySQLNovcanaSredstvaDAO();
	}
	@Override
	public TipTransakcijeDAO getTipTransakcijeDAO() {
		// TODO Auto-generated method stub
		return new MySQLTipTransakcijeDAO();
	}
	@Override
	public TransakcijaDAO getTransakcijaDAO() {
		// TODO Auto-generated method stub
		return new MySQLTransakcijaDAO();
	}
	@Override
	public TroskoviOpremaDAO getTroskoviOpremaDAO() {
		// TODO Auto-generated method stub
		return new MySQLTroskoviOpremaDAO();
	}
	@Override
	public TroskoviTurnirDAO getTroskoviTurnirDAO() {
		// TODO Auto-generated method stub
		return new MySQLTroskoviTurnirDAO();
	}
	@Override
	public UplataZaTurnirDAO getUplataZaTurnirDAO() {
		// TODO Auto-generated method stub
		return new MySQLUplataZaTurnirDAO();
	}
}
