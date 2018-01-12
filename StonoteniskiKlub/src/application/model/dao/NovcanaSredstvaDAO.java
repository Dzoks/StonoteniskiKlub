package application.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import application.model.dto.NovcanaSredstvaDTO;
import application.model.dto.TipTransakcijeDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public interface NovcanaSredstvaDAO {
	
	public ObservableList<String> getSezone();
	public  NovcanaSredstvaDTO getNSMaxId();
	public  NovcanaSredstvaDTO getBySezona(String sezona);
	public boolean INSERT(NovcanaSredstvaDTO ns);
	
	public  void dodajPrihode(double prihod);
	public  void dodajRashode(double rashod); 
}
