package application.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.model.dto.PlataDTO;
import application.model.dto.TipTransakcijeDTO;
import application.model.dto.TransakcijaDTO;
import application.model.dto.ZaposleniDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface TransakcijaDAO {
	

	public ObservableList<TransakcijaDTO> SELECT_ALL(); 
	
	
	public int INSERT(TransakcijaDTO transakcija, TipTransakcijeDTO tip); 
	//	private static final String SQL_UPDATE = "update TRANSAKCIJA set Datum=?, Iznos=?, Opis=?, TIP_TRANSAKCIJE_Id=?, jeUplata=? where Id=?";

	public void UPDATE(TransakcijaDTO transakcija, TipTransakcijeDTO tip); 
	public void delete(int id); 
}

/*
 * String query = SQL_UPDATE;
			Object pom[] = { osoba.getIme(), osoba.getPrezime(), osoba.getImeRoditelja(),
					osoba.getPol().toString(), osoba.getJmb(), osoba.getDatumRodjenja(), osoba.getSlika(), osoba.getId() };

			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			ps.executeUpdate();
*/