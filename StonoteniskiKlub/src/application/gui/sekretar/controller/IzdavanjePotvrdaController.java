package application.gui.sekretar.controller;

import java.awt.print.PrinterJob;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import application.gui.controller.BaseController;
import application.model.dao.ClanDAO;
import application.model.dao.OsobaDAO;
import application.model.dto.ClanDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class IzdavanjePotvrdaController extends BaseController implements Initializable {


	@FXML
	private TextArea txtSadrzaj;
	
	@FXML
	private ComboBox<String> cbVrstaPotvrde;
	
	private static final String FONT = "times.ttf";
	private static final String FONTBOLD = "timesbd.ttf";
	private static int idTipa;

	// clan bi trebao da se preuzme nekako iz prethodne forme tako da sam ovo uzeo
	// samo za testiranje
	// Potreban samo radi ID
	ClanDTO clan = ClanDAO.getById(1);

	ObservableList<String> listaTipova = FXCollections.observableArrayList(OsobaDAO.getTipoviPotvrde());
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbVrstaPotvrde.setItems(listaTipova);
		
		
	}
	
	public void odaberiTip() {
		String odabraniTip = cbVrstaPotvrde.getSelectionModel().getSelectedItem();
		String imeClana = clan.getIme() + " (" + clan.getImeRoditelja() + ") "+clan.getPrezime();
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy.");
		Date today = Calendar.getInstance().getTime();
		String danasnjiDatum = df.format(today);
		
		
		if("Uclanjivanje".equals(odabraniTip)) {
			txtSadrzaj.setText("Potvrđujemo da je " + imeClana + " postao član Stonoteniskog kluba \"BORAC\" Banja Luka "
					+ "na datum " + danasnjiDatum);
			idTipa = listaTipova.indexOf("Uclanjivanje") + 1;
			return;
		}
		if("Isclanjivanje".equals(odabraniTip)) {
			txtSadrzaj.setText("Potvrđujemo da je IME (IME RODITELJA) PREZIME izvršio iščlanjivanje is Stonoteniskog kluba \"BORAC\" Banja Luka "
					+ "na datum " + danasnjiDatum + " i od tada nije više član gore pomenutog kluba.");
			idTipa = listaTipova.indexOf("Isclanjivanje") + 1;
			return;
		}
		if("Pravdanje".equals(odabraniTip)) {
			txtSadrzaj.setText("\n\nU nadi da ćete prihvatiti našu potvrdu i opravdati izostanak sa nastave, unaprijed "
					+ "se zahvaljujemo i srdačno Vas pozdravljamo.");
			idTipa = listaTipova.indexOf("Pravdanje") + 1;
			return;
		}
		if("Aktivan clan".equals(odabraniTip)) {
			txtSadrzaj.setText("Stonoteniski klub \"Borac Raiffeisen Bank\" ovim pute potvrđuje da je " + imeClana + ", "
					+ "aktivan član našeg kluba i da je u protekloj godini postigao više zapaženih rezultata na svim takmičenjima "
					+ "u Republici Srpskoj i Bosni i Hercegovini.\n"
					+ "Potvrda se izdaje u svrhu ");
			idTipa = listaTipova.indexOf("Aktivan clan") + 1;
			return;
		}
		txtSadrzaj.setText("");
		idTipa = listaTipova.indexOf("Default") + 1;
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
	
	public void stampaj() {
		File file = new File("tmp.pdf");
		try {

			Font font = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12);
			Document report = new Document();
			report.setMargins(90, 90, 70, 70);
			FileOutputStream fos = new FileOutputStream(file);
			PdfWriter.getInstance(report, fos);
			report.open();

			String header = "Stonoteniski klub\n" + "\"BORAC\"\n" + "Ul. Krfska 80\n" + "78000 Banja Luka\n"
					+ "TRN kod Raiffeisen BANK:\n" + "1610450022990007\n"
					+ "Šifra djelatnosti: 92620 (ostale sportske djelatnosti)\n" + "Telefon: +387 (65) 968 206";

			Paragraph p = new Paragraph(header, font);

			addEmptyLine(p, 4);
			Paragraph temp = new Paragraph("POTVRDA", FontFactory.getFont(FONTBOLD, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 14));
			temp.setAlignment(Element.ALIGN_CENTER);
			p.add(temp);
			addEmptyLine(p, 2);

			temp = new Paragraph(txtSadrzaj.getText(), font);
			temp.setAlignment(Element.ALIGN_JUSTIFIED);
			p.add(temp);
			addEmptyLine(p, 4);

			DateFormat df = new SimpleDateFormat("dd.MM.yyyy.");
			Date today = Calendar.getInstance().getTime();
			String reportDate = df.format(today);
			p.add("Banja Luka, " + reportDate + " dog.                                             Stonoteniski klub\n");
			
			
			temp = new Paragraph("\"BORAC\"                        ", font);
			temp.setAlignment(Element.ALIGN_RIGHT);
			p.add(temp);
			
			addEmptyLine(p, 1);
			
			temp = new Paragraph("______________________________   ", font);
			temp.setAlignment(Element.ALIGN_RIGHT);
			p.add(temp);
			
			temp = new Paragraph("(Predsjednik kluba: Miodrag Malinović)", font);
			temp.setAlignment(Element.ALIGN_RIGHT);
			p.add(temp);

			report.add(p);

			report.close();
			
			// zavrseno printanje u pdf
			PDDocument doc = PDDocument.load(new BufferedInputStream(new FileInputStream(file)));
			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPageable(new PDFPageable(doc));

			if (job.printDialog()) {
				job.print();
			}
			doc.close();
			fos.close();
			
			OsobaDAO.insertPotvrda(clan.getId(), idTipa, new Date(), convertFileToBlob(file));
			
			file.delete();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Blob convertFileToBlob(File file) throws IOException {
		if(file == null)
			return null;
		Connection conn;
		try {
			conn = ConnectionPool.getInstance().checkOut();
			Blob blob = conn.createBlob();
			
			blob.setBytes(1, Files.readAllBytes(file.toPath()));
			return blob;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}

