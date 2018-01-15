package application.model.dao;

import java.sql.Blob;
import java.util.Date;
import java.util.List;

import application.model.dto.OsobaDTO;

public interface OsobaDAO {

	public void insertPotvrda(int idClana, int idTipa, Date datum, Blob tekst);
	public List<String> getTipoviPotvrde(); 
	public OsobaDTO getByJmb(String jmb); 
	public List<String> getTelefoni(int idOsobe);
	public int getIdByTelefon(String telefon); 
	public void insert(OsobaDTO osoba); 
	public void update(OsobaDTO osoba); 
	public void insertSaTelefonom(OsobaDTO osoba);
	public void deleteTelefon(int id); 
	public boolean doesExist(String jmb,Integer idTurnira,Integer idKategorije); 
	public void insertTelefon(OsobaDTO osoba);
	public boolean insertTel(String telefon, OsobaDTO osoba);
}