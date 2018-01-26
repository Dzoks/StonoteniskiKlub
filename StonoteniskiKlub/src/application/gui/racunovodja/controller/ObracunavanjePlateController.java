package application.gui.racunovodja.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
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
import application.model.dao.DAOFactory;
import application.model.dto.ZaposleniDTO;
import application.util.ErrorLogger;
import javafx.beans.binding.BooleanBinding;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;

public class ObracunavanjePlateController extends BaseController{
	private static final Double doprinosPIO = 18.5;
	private static final Double doprinosZO = 12.0;
	private static final Double doprinosZaposljavanje = 1.0;
	private static final Double doprinosZaDjecijuZastitu = 1.5;
	private static final Double porezNaPlatu = 10.0;

	private static final String FONT = "times.ttf";
	private static final String FONTBOLD = "timesbd.ttf";
	@FXML
	private ComboBox comboBoxZaposleni;
	@FXML
	private TextField txtKoeficijent;
	@FXML
	private TextField txtCijenaRada;
	@FXML
	private TextField txtBolovanje;
	@FXML
	private Label lblPIOPostotak;
	@FXML
	private Label lblZOPostotak;
	@FXML
	private Label lblDoprinosiZaposljavanjePostotak;
	@FXML
	private Label lblDjecijaZastitaPostotak;
	@FXML
	private Label lblPorezPostotak;
	@FXML
	private Label lblDoprinosiUkupno;
	@FXML
	private Label lblPorezUkupno;
	@FXML
	private Label lblBruto;
	@FXML
	private Label lblNeto;
	@FXML
	private Label lblPIO;
	@FXML
	private Label lblZO;
	@FXML
	private Label lblDoprinosZaposljavanje;
	@FXML
	private Label lblDjecijaZastita;
	@FXML
	private Label lblPorez;
	@FXML
	private Button btnObracunaj;
	@FXML
	private Button btnIstampaj;

	// Event Listener on Button[#btnObracunaj].onAction
	@FXML
	public void istampaj() {
		WritableImage snapshot = this.getPrimaryStage().getScene().snapshot(null);
		BufferedImage bufferedImage = SwingFXUtils.fromFXImage(snapshot, null);
	}
	
	@FXML
	public void obracunaj(ActionEvent event) {
		Double koeficijent = Double.parseDouble(txtKoeficijent.getText());
		ZaposleniDTO zaposleni = (ZaposleniDTO)comboBoxZaposleni.getValue();
		Double cijenaRada = Double.parseDouble(txtCijenaRada.getText());
		Integer bolovanjeDana = Integer.parseInt(txtBolovanje.getText());
		Double netoPlataBezBolovanja = koeficijent*cijenaRada;
		Double plataPoDanu = netoPlataBezBolovanja / 25;
		Double netoPlata = netoPlataBezBolovanja - bolovanjeDana*plataPoDanu + bolovanjeDana*plataPoDanu*0.7;
		
		Double bruto = netoPlata/0.603;
		Double pio = bruto*doprinosPIO/100;
		Double zo = bruto*doprinosZO/100;
		Double zaposljavanje = bruto*doprinosZaposljavanje/100;
		Double djecijaZastita = bruto*doprinosZaDjecijuZastitu/100;
		Double porez = bruto*porezNaPlatu/100;
		
		lblPorez.setText(String.format("%.2f", porez));
		lblZO.setText(String.format("%.2f", zo));
		lblDjecijaZastita.setText(String.format("%.2f", djecijaZastita));
		lblDoprinosZaposljavanje.setText(String.format("%.2f", zaposljavanje));
		lblBruto.setText(String.format("%.2f", bruto));
		lblPIO.setText(String.format("%.2f", pio));
		lblNeto.setText(String.format("%.2f", netoPlata));
		lblDoprinosiUkupno.setText(String.format("%.2f", new Double(pio+zo+djecijaZastita+zaposljavanje)));
		this.stampaj(zaposleni,koeficijent,cijenaRada,bolovanjeDana,pio,zo,zaposljavanje,djecijaZastita,porez,new Double(pio+zo+djecijaZastita+zaposljavanje),bruto,netoPlata);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		comboBoxZaposleni.setItems(DAOFactory.getDAOFactory().getZaposleniDAO().selectAll());
		comboBoxZaposleni.getSelectionModel().select(0); //paziti na null ptr
		BooleanBinding binding = txtBolovanje.textProperty().isEmpty().or(txtCijenaRada.textProperty().isEmpty().or(txtKoeficijent.textProperty().isEmpty()));
		btnObracunaj.disableProperty().bind(binding);
	}
	private void stampaj(ZaposleniDTO zaposleni, double koeficijent,double cijenaRada, int bolovanje,double pio, double zo,double zap, double djecijaZastita, double porez, double doprinosi, double bruto,double neto) {
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
			p.add(new Paragraph(" "));
			
			Paragraph temp = new Paragraph("OBRACUN PLATE", FontFactory.getFont(FONTBOLD, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 14));
			temp.setAlignment(Element.ALIGN_CENTER);
			p.add(temp);
			p.add(new Paragraph(" "));
			temp = new Paragraph("Zaposleni: "+zaposleni.getIme()+" "+zaposleni.getPrezime(), FontFactory.getFont(FONTBOLD, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12));
			p.add(temp);
			temp = new Paragraph("___________________________________________________________________", font);
			p.add(temp);
			p.add(new Paragraph(" "));
			temp = new Paragraph("Koeficijent: "+koeficijent, font);
			p.add(temp);
			temp = new Paragraph("Cijena rada: "+cijenaRada, font);
			p.add(temp);
			temp = new Paragraph("Bolovanje(dani): "+bolovanje, font);
			p.add(temp);
			temp = new Paragraph("___________________________________________________________________", font);
			p.add(temp);
			p.add(new Paragraph(" "));

			temp = new Paragraph("Doprinosi za PIO(18.5%): "+String.format("%.2f", pio)+" KM", font);
			p.add(temp);
			temp = new Paragraph("Doprinosi za zdravstveno osiguranje(12.0%): "+String.format("%.2f", zo)+" KM", font);
			p.add(temp);
			temp = new Paragraph("Doprinosi za zaposljavanje(1.0%): "+String.format("%.2f", zap)+" KM", font);
			p.add(temp);
			temp = new Paragraph("Doprinosi za djeciju zastitu(1.5%): "+String.format("%.2f", djecijaZastita)+" KM", font);
			p.add(temp);
			temp = new Paragraph("Porez na platu(10.0%): "+String.format("%.2f", porez)+" KM", font);
			p.add(temp);
			temp = new Paragraph("___________________________________________________________________", font);
			p.add(temp);
			p.add(new Paragraph(" "));
			temp = new Paragraph("UKUPNO:", FontFactory.getFont(FONTBOLD, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 11));
			p.add(temp);
			p.add(new Paragraph(" "));
			temp = new Paragraph("Doprinosi: "+String.format("%.2f", doprinosi)+" KM", font);
			p.add(temp);
			temp = new Paragraph("Bruto iznos: "+String.format("%.2f", bruto)+" KM", font);
			p.add(temp);
			temp = new Paragraph("Neto iznos za isplatu: "+String.format("%.2f", neto)+" KM", font);
			p.add(temp);
			for(int i=0; i<3;i++)
				p.add(new Paragraph(" "));
			DateFormat df = new SimpleDateFormat("dd.MM.yyyy.");
			Date today = Calendar.getInstance().getTime();
			String reportDate = df.format(today);
			p.add("Banja Luka, " + reportDate + " god.                                             Stonoteniski klub\n");			
			temp = new Paragraph("\"BORAC\"                        ", font);
			temp.setAlignment(Element.ALIGN_RIGHT);
			p.add(temp);
			report.add(p);
			report.close();
			PDDocument doc = PDDocument.load(new BufferedInputStream(new FileInputStream(file)));
			java.awt.print.PrinterJob job = java.awt.print.PrinterJob.getPrinterJob();
			job.setPageable(new PDFPageable(doc));
			if (job.printDialog()) {
				job.print();
			}
			doc.close();
			fos.close();
			file.delete();

		} catch (Exception e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		}
	}
}
