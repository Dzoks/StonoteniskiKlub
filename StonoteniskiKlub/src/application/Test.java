package application;

import java.io.FileNotFoundException;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import application.model.dao.DAOFactory;
import application.model.dto.ClanDTO;
import application.util.TextUtility;

public class Test {
	public static void main(String[] args) {
		Font font = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12);
		Font bold = FontFactory.getFont(FONTBOLD, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12);
		String test = "test.pdf";
		List<ClanDTO> clanovi = DAOFactory.getDAOFactory().getClanDAO().selectAll();
		Document doc;
		try {
			doc = TextUtility.createDocument(test, PageSize.A4);
			TextUtility.addHeader(doc, font);
			Paragraph p = new Paragraph();
			addEmptyLine(p, 1);
			p.add("Zahtjev za registraciju igraca za sezonu 2017/2018:");
			addEmptyLine(p, 2);
			doc.add(p);
			PdfPTable table = new PdfPTable(6);
			table.setWidthPercentage(100);
			table.setTotalWidth(new float[] { 42, 35, 30, 30, 20, 30 });
			PdfPCell cellJmb = new PdfPCell(new Paragraph("JMB", bold));
			PdfPCell cellPrezime = new PdfPCell(new Paragraph("Prezime", bold));
			PdfPCell cellImeRoditelja = new PdfPCell(new Paragraph("Ime roditelja", bold));
			PdfPCell cellIme = new PdfPCell(new Paragraph("Ime", bold));
			PdfPCell cellPol = new PdfPCell(new Paragraph("Pol", bold));
			PdfPCell cellKategorija = new PdfPCell(new Paragraph("Kategorija", bold));
			cellJmb.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellPrezime.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellIme.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellImeRoditelja.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellPol.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellKategorija.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cellJmb);
			table.addCell(cellPrezime);
			table.addCell(cellImeRoditelja);
			table.addCell(cellIme);
			table.addCell(cellPol);
			table.addCell(cellKategorija);
			for (ClanDTO clan : clanovi) {
				if (!"Helena".equals(clan.getIme())) {
					table.addCell(clan.getJmb());
					table.addCell(clan.getPrezime());
					table.addCell(clan.getImeRoditelja());
					table.addCell(clan.getIme());
					table.addCell(clan.getPol().equals('M') ? "Muski" : "Zenski");
					table.addCell("Senior");
					
				}
				System.out.println(clan.getJmb());
			}
			doc.add(table);
			Paragraph footer = new Paragraph();
			addEmptyLine(footer, 2);
			footer.add("Dana 8.1.2017. godine.");
			doc.add(footer);
			Paragraph signature = new Paragraph("_____________________" + "\nSekretar kluba");
			signature.setAlignment(Element.ALIGN_RIGHT);
			doc.add(signature);
			TextUtility.closeDocument(doc);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	private static final String FONT = "times.ttf";
	private static final String FONTBOLD = "timesbd.ttf";
	private static int idTipa;
}
