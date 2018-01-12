package application.model.dao.mysql;

import application.model.dao.ClanarinaDAO;
import application.model.dao.DAOFactoryTransakcije;
import application.model.dao.NovcanaSredstvaDAO;
import application.model.dao.PlataDAO;
import application.model.dao.TipTransakcijeDAO;
import application.model.dao.TransakcijaDAO;
import application.model.dao.TroskoviOpremaDAO;
import application.model.dao.TroskoviTurnirDAO;
import application.model.dao.UplataZaTurnirDAO;

public class MySQLDAOFactoryTransakcije extends DAOFactoryTransakcije{
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
