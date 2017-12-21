package application;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import application.model.dao.DAOFactory;
import application.model.dao.SponzorDAO;
import application.model.dao.UgovorDAO;
import application.model.dao.ZaposleniDAO;
import application.model.dto.SponzorDTO;
import application.model.dto.UgovorDTO;
import application.model.dto.ZaposleniDTO;
import application.model.dto.ZaposlenjeDTO;
import javafx.collections.ObservableList;

public class Test {
	public static void main(String[] args) {
		DAOFactory factory = DAOFactory.getDAOFactory();
		SponzorDAO sponzorDAO = factory.getSponzorDAO();
		UgovorDAO ugovorDAO = factory.getUgovorDAO();
		SponzorDTO sponzorDTO = new SponzorDTO(null, "Cvjecara Maja", "Kralja Petra bb, Kukulje", "info@maja.com", null);
		UgovorDTO ugovorDTO = new UgovorDTO(null, Calendar.getInstance().getTime(), null, "Ugovor o cvijecu.", null);
		if(sponzorDAO.insert(sponzorDTO, ugovorDTO))
			System.out.println("Dodan sponzor!");
		List<SponzorDTO> sponzori = sponzorDAO.selectAll();
		for(Iterator<SponzorDTO> it = sponzori.iterator() ; it.hasNext() ; ){
			SponzorDTO sponzor = it.next();
			sponzor.setUgovori(ugovorDAO.selectAllById(sponzor.getId()));
			System.out.println(sponzor);
		}
		System.out.println("Kraj");
	}
}
