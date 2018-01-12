package application.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import application.model.dto.DogadjajDTO;
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

	public static void setTextF(TextFlow textFlow, List<DogadjajDTO> dogadjaji) {
		textFlow.getChildren().clear();
		if (dogadjaji != null && dogadjaji.size() > 0) {
			textFlow.setStyle(textFlow.getStyle().split("[;]")[0]);
			Collections.sort(dogadjaji, new Comparator<DogadjajDTO>() {

				@Override
				public int compare(DogadjajDTO o1, DogadjajDTO o2) {
					return o1.getPocetak().compareTo(o2.getPocetak());
				}
			});
			for (DogadjajDTO dogadjaj : dogadjaji) {
				System.out.println(dogadjaj);
				Text naslov = new Text("[" + dogadjaj.getPocetak().format(DateTimeFormatter.ofPattern("HH:mm")) + " - "
						+ dogadjaj.getKraj().format(DateTimeFormatter.ofPattern("HH:mm")) + "] - " + dogadjaj.getTipDogadjaja().getTip() + AlertDisplay.NL
						+ AlertDisplay.NL);
				naslov.setStyle("-fx-font-weight: bold");
				Text opis = new Text("    - " + dogadjaj.getOpis() + AlertDisplay.NL);
				Text potpis = new Text(
						"    - KREIRAO: " + dogadjaj.getKorisnickiNalog() + AlertDisplay.NL + AlertDisplay.NL);
				textFlow.getChildren().addAll(naslov, opis, potpis);
			}
		} else{
			Text naslov = new Text("Nema dogadjaja.");
			naslov.setStyle("-fx-font-weight: bold; -fx-font-size: 20");
			textFlow.setStyle(textFlow.getStyle() + "; -fx-text-alignment: center");
			textFlow.getChildren().add(naslov);
		}
	}

	public static Document createDocument(String path, Rectangle pageSize)
			throws FileNotFoundException, DocumentException {
		Document document = new Document(pageSize);
		PdfWriter.getInstance(document, new FileOutputStream(path));
		document.setMargins(90, 90, 70, 70);
		document.open();
		return document;
	}

	public static void closeDocument(Document document) {
		document.close();
	}

	public static void addHeader(Document document, Font font) throws DocumentException {
		String headerText = "Stonoteniski klub\n" + "\"BORAC\"\n" + "Ul. Krfska 80\n" + "78000 Banja Luka\n"
				+ "TRN kod Raiffeisen BANK:\n" + "1610450022990007\n"
				+ "Šifra djelatnosti: 92620 (ostale sportske djelatnosti)\n" + "Telefon: +387 (65) 968 206";
		Paragraph header = new Paragraph(headerText);
		header.setAlignment(Element.ALIGN_CENTER);
		document.add(header);
	}
}
