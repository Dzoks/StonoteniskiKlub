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
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ListUpdater extends Task<Void> {

	@Override
	protected Void call() {
		try {
			ResourceBundle bundle = PropertyResourceBundle.getBundle("application.util.RangLista");
			String season = bundle.getString("Sezona");
			File folder = new File("rangListe");
			folder.mkdir();
			List<KategorijaDTO> categoryList = KategorijaDAO.getAll(true);
			for (KategorijaDTO category : categoryList) {
				List<RegistracijaDTO> registrationList = RegistracijaDAO.getAllBySeason(season, category);
				if (!registrationList.isEmpty()) {
					File xlsFile = Downloader.preuzmiListu(category.getLink(), category.getNaziv());
					Integer[] indexes = Parser.pocetniIndeksiTabele(xlsFile);
					Object[][] tournaments = Parser.dobaviTurnire(xlsFile, indexes);
					List<RegistracijaDTO> updatedRegistrations = Parser.nabaviRezultate(xlsFile, indexes, tournaments,
							registrationList);
					Files.delete(xlsFile.toPath());
					for (RegistracijaDTO registration : updatedRegistrations)
						RegistracijaDAO.update(registration);
				}
			}
			folder.delete();
			
			Platform.runLater(()->{
				Alert alert=new Alert(AlertType.INFORMATION);
				alert.setContentText("Rezultati su uspješno preuzeti i ažurirani u bazi podataka");
				alert.setHeaderText("Uspješno preuzimanje");
				alert.setTitle("Informacija");
				alert.showAndWait();
			});
		} catch (Exception e) {
			
			Platform.runLater(()->{
				Alert alert=new Alert(AlertType.ERROR);
				alert.setContentText("Nije uspjelo preuzimanje liste. Molimo pokušajte ponovo");
				alert.setHeaderText("Greška prilikom preuzimanja");
				alert.setTitle("Greška");
				alert.showAndWait();
			});		}
			return null;
	}
}
