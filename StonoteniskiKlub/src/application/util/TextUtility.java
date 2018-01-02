package application.util;

import java.util.List;

import application.model.dto.StavkaSkupstinaDTO;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class TextUtility {
	public static void setTextFlow(TextFlow textFlow, List<StavkaSkupstinaDTO> stavke) {
		textFlow.getChildren().clear();
		if (stavke != null) {
			for (StavkaSkupstinaDTO stavka : stavke) {
				Text naslov = new Text(
						stavka.getRedniBroj() + ". " + stavka.getNaslov() + AlertDisplay.NL + AlertDisplay.NL);
				naslov.setStyle("-fx-font-weight: bold");
				Text tekst = new Text("    " + stavka.getTekst() + AlertDisplay.NL + AlertDisplay.NL);
				textFlow.getChildren().addAll(naslov, tekst);
			}
		}
	}
}
