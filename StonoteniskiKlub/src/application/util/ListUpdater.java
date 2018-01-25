package application.util;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import application.model.dao.DAOFactory;
import application.model.dto.KategorijaDTO;
import application.model.dto.RegistracijaDTO;
import javafx.application.Platform;
import javafx.concurrent.Task;

public class ListUpdater extends Task<Void> {

	@Override
	protected Void call() {
		try {
			ResourceBundle bundle = PropertyResourceBundle.getBundle("Properties");
			String season = bundle.getString("Sezona");
			File folder = new File("rangListe");
			folder.mkdir();
			List<KategorijaDTO> categoryList = DAOFactory.getDAOFactory().getKategorijaDAO().getAll(true);
			for (KategorijaDTO category : categoryList) {
				List<RegistracijaDTO> registrationList = DAOFactory.getDAOFactory().getRegistracijaDAO().getAllBySeason(season, category);
				if (!registrationList.isEmpty()) {
					File xlsFile = Downloader.preuzmiListu(category.getLink(), category.getNaziv());
					Integer[] indexes = Parser.pocetniIndeksiTabele(xlsFile);
					Object[][] tournaments = Parser.dobaviTurnire(xlsFile, indexes);
					List<RegistracijaDTO> updatedRegistrations = Parser.nabaviRezultate(xlsFile, indexes, tournaments,
							registrationList);
					Files.delete(xlsFile.toPath());
					for (RegistracijaDTO registration : updatedRegistrations)
						DAOFactory.getDAOFactory().getRegistracijaDAO().update(registration);
				}
			}
			folder.delete();
			
			Platform.runLater(()->{
				AlertDisplay.showConfirmation("Preuzimanje", "Rezultati su uspješno preuzeti i ažurirani u bazi podataka");
			});
		} catch (Exception e) {
			
			Platform.runLater(()->{
				AlertDisplay.showError("Preuzimanje", "Nije uspjelo preuzimanje liste. Molimo pokušajte ponovo");
			});		}
			return null;
	}
}
