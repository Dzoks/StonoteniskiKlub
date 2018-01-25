package application.model.dao;

import application.model.dao.mysql.MySQLDAOFactory;

public abstract class DAOFactory {
	
	public static DAOFactory getDAOFactory(){
		return MySQLDAOFactory.getInstance();
	}
	
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
	
	public abstract ClanarinaDAO getClanarinaDAO();
	
	public abstract PlataDAO getPlataDAO();
	
	public abstract TransakcijaDAO getTransakcijaDAO();
	
	public abstract NovcanaSredstvaDAO getNovcanaSredstvaDAO();
	
	public abstract TipTransakcijeDAO getTipTransakcijeDAO();
	
	public abstract TroskoviOpremaDAO getTroskoviOpremaDAO();
	
	public abstract TroskoviTurnirDAO getTroskoviTurnirDAO();
	
	public abstract UplataZaTurnirDAO getUplataZaTurnirDAO();
	
	public abstract KategorijaDAO getKategorijaDAO();
	
	public abstract KategorijaTurniraDAO getKategorijaTurniraDAO();
	
	public abstract KorisnickiNalogDAO getKorisnickiNalogDAO();
	
	public abstract KorisnickiNalogTipDAO getKorisnickiNalogTipDAO();
	
	public abstract MecDAO getMecDAO();
	
	public abstract TimDAO getTimDAO();
	
	public abstract TurnirDAO getTurnirDAO();
	
	public abstract UcesnikPrijavaDAO getUcesnikPrijavaDAO();
	
	public abstract ZrijebDAO getZrijebDAO();
	
	public abstract RegistracijaDAO getRegistracijaDAO();
	
	public abstract RundaDAO getRundaDAO();
	
	public abstract TreningDAO getTreningDAO();
}
