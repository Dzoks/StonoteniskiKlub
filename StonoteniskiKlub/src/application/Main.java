package application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dao.SponzorDAO;
import application.model.dao.UgovorDAO;
import application.model.dto.SponzorDTO;
import application.model.dto.UgovorDTO;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage = new Stage();
		BaseController.changeScene("/application/gui/sekretar/view/RadSaZaposlenimaView.fxml", primaryStage);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Malina");
		primaryStage.show();
	}

	public static void main(String[] args) {
		DAOFactory factory = DAOFactory.getDAOFactory();
		SponzorDAO sponzorDAO = factory.getSponzorDAO();
		UgovorDAO ugovorDAO = factory.getUgovorDAO();
		SponzorDTO sponzorDTO = new SponzorDTO(null, "Knjizara Helena", "Savska bb, Srbac", "info@helena.com", null);
		UgovorDTO ugovorDTO = new UgovorDTO(null, Calendar.getInstance().getTime(), null, "Ugovor o knjigama.", null);
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
