package application.model.dao;

import application.model.dto.NovcanaSredstvaDTO;
import javafx.collections.ObservableList;

public interface NovcanaSredstvaDAO {
	
	public ObservableList<String> getSezone();
	public  NovcanaSredstvaDTO getNSMaxId();
	public  NovcanaSredstvaDTO getBySezona(String sezona);
	public boolean INSERT(NovcanaSredstvaDTO ns);
	
	public  void dodajPrihode(double prihod);
	public  void dodajRashode(double rashod); 
}
