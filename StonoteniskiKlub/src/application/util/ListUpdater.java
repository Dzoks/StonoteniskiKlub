package application.util;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import application.model.dao.KategorijaDAO;
import application.model.dao.RegistracijaDAO;
import application.model.dto.KategorijaDTO;
import application.model.dto.RegistracijaDTO;
import javafx.concurrent.Task;

public class ListUpdater extends Task<Void> {

	@Override
	protected Void call() throws Exception {

		ResourceBundle bundle = PropertyResourceBundle.getBundle("application.util.RangLista");
		String season = bundle.getString("Sezona");
		File folder = new File("rangListe");
		folder.mkdir();
		List<KategorijaDTO> categoryList = KategorijaDAO.getAll();
		for (KategorijaDTO category : categoryList) {
			List<RegistracijaDTO> registrationList = RegistracijaDAO.getAllBySeason(season, category);
			if (!registrationList.isEmpty()) {
				File xlsFile = Downloader.preuzmiListu(bundle.getString(category.getNaziv()), category.getNaziv());
				Integer[] indexes = Parser.pocetniIndeksiTabele(xlsFile);
				Object[][] tournaments = Parser.dobaviTurnire(xlsFile, indexes);
				List<RegistracijaDTO> updatedRegistrations = Parser.nabaviRezultate(xlsFile, indexes, tournaments,
						registrationList);
				Files.delete(xlsFile.toPath());
				// update ALL
			}
		}
		folder.delete();
		return null;
	}
}
