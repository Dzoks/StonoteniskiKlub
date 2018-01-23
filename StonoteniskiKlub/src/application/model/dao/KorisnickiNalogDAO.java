package application.model.dao;

import application.model.dto.KorisnickiNalogDTO;
import javafx.collections.ObservableList;

public interface KorisnickiNalogDAO {

	public KorisnickiNalogDTO getById(int id);
	public void setAktivan(int flag, int korisnickiNalogId);
	public void setLozinka(byte[] lozinkaHash, String korisnickoIme);
	public boolean daLiPostoji(String korisnickoIme);
	public boolean daLiPostojiLozinka(String korisnickoIme);
	public void insert(KorisnickiNalogDTO nalog);
	public String getHashByUsername(String username);
	public ObservableList<KorisnickiNalogDTO> selectAll();
	public String getUloga(String text);
}
