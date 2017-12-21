package application;

import application.model.dao.ZaposleniDAO;
import application.model.dto.ZaposleniDTO;
import application.model.dto.ZaposlenjeDTO;
import javafx.collections.ObservableList;

public class Test {
	public static void main(String[] args) {
		ObservableList<ZaposleniDTO> zaposleni = ZaposleniDAO.selectAll();
		for(ZaposleniDTO zap : zaposleni){
			System.out.println(zap.getIme() + " " + zap.getPrezime());
			for(ZaposlenjeDTO zapo : zap.getZaposljenja()){
				System.out.println(" " + zapo.getTipNaziv().get());
			}
		}
	}
}
