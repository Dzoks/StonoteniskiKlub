package application;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import application.model.dao.DAOFactory;
import application.model.dao.DonacijaDAO;
import application.model.dao.SponzorDAO;
import application.model.dao.UgovorDAO;
import application.model.dao.ZaposleniDAO;
import application.model.dao.mysql.MySQLDonacijaDAO;
import application.model.dto.DonacijaDTO;
import application.model.dto.SponzorDTO;
import application.model.dto.UgovorDTO;
import application.model.dto.ZaposleniDTO;
import application.model.dto.ZaposlenjeDTO;
import javafx.collections.ObservableList;

public class Test {
	public static void main(String[] args) {
		DAOFactory factory = DAOFactory.getDAOFactory();
		SponzorDAO sponzorDAO = factory.getSponzorDAO();
		// Izlistavanje sponzora
		List<SponzorDTO> sponzori = sponzorDAO.selectAll();
		for(Iterator<SponzorDTO> it= sponzori.iterator(); it.hasNext() ;){
			System.out.println(it.next());
		}
		// Daj sponzora sa id nekim
		System.out.println();
		SponzorDTO sponzor = sponzorDAO.getById(1);
		System.out.println(sponzor);
		sponzor = sponzorDAO.getById(3);
		System.out.println(sponzor);
		sponzor = sponzorDAO.getById(15);
		System.out.println(sponzor);
		System.out.println();
		// Daj ugovor
		UgovorDAO ugovorDAO = factory.getUgovorDAO();
		System.out.println(ugovorDAO.selectOne(1, 2));
		System.out.println(ugovorDAO.selectOne(2, 5));
		System.out.println(ugovorDAO.selectOne(3, 2));
		
		System.out.println();
		
		DonacijaDAO donacijaDAO = factory.getDonacijaDAO();
		for(DonacijaDTO donacija : donacijaDAO.neobradjene(false)){
			System.out.println(donacija);
		}
		System.out.println("Kraj");
	}
}
