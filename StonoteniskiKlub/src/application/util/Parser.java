package application.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import application.model.dto.RegistracijaDTO;

public class Parser {

	public static Integer[] pocetniIndeksiTabele(File fajl) throws IOException {
		FileInputStream file = new FileInputStream(fajl);
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rows = sheet.rowIterator();
		boolean found = false;
		int startingRow = -1;
		int startingColumn = -1;
		while (rows.hasNext() && !found) {
			Row row = rows.next();
			Iterator<Cell> cells = row.iterator();
			while (cells.hasNext() & !found) {
				Cell cell = cells.next();
				if (CellType.STRING == cell.getCellTypeEnum()
						&& "PREZIME I IME".equals(cell.getStringCellValue().trim().toUpperCase())) {
					found = true;
					startingColumn = cell.getColumnIndex();
					startingRow = cell.getRowIndex();

				}
			}
		}
		file.close();
		workbook.close();
		Integer[] niz = { startingRow, startingColumn };
		return niz;
	}

	public static  Object[][] dobaviTurnire(File fajl,Integer[] indeksi) throws IOException {
		int startingRow=indeksi[0];
		int startingColumn=indeksi[1]+2;
		FileInputStream file = new FileInputStream(fajl);
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheetAt(0);
		ArrayList<String> imena = new ArrayList<>();
		ArrayList<Integer> pozicije = new ArrayList<>();
		Row redSaImenima=sheet.getRow(startingRow);
		int krajReda=redSaImenima.getLastCellNum();
		for(int i=startingColumn;i<krajReda;i++) {
			Cell turnir = redSaImenima.getCell(i);
			if (turnir.getCellTypeEnum() == CellType.STRING) {
				imena.add(turnir.getStringCellValue().trim());
				pozicije.add(i);
			}
		}
		file.close();
		workbook.close();
		Object[][] retValue=new Object[2][2];
		retValue[0]=imena.toArray(new String[imena.size()]);
		retValue[0][imena.size()-1]="Ukupno";
		retValue[1]=pozicije.toArray(new Integer[pozicije.size()]);
		return retValue;
	}

	public static List<RegistracijaDTO> nabaviRezultate(File fajl, Integer[] pozicije, Object[][] turniri,
			List<RegistracijaDTO> igraci) throws IOException {
		FileInputStream file = new FileInputStream(fajl);
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheetAt(0);
		int poslednjiRed = sheet.getLastRowNum();
		int zapisRedniBroj = pozicije[0] + 1;
		List<RegistracijaDTO> zavrseniIgraci = new ArrayList<>();
		while (!igraci.isEmpty() && zapisRedniBroj <= poslednjiRed) {
			Row zapisIgrac = sheet.getRow(zapisRedniBroj++);
			if (zapisIgrac.getCell(pozicije[1]).getCellTypeEnum() == CellType.STRING) {
				String prezimeIme = zapisIgrac.getCell(pozicije[1]).getStringCellValue().trim().toUpperCase().replaceAll("\\s+", " ");
				RegistracijaDTO igrac = null;
				for (int i = 0; igrac == null && i < igraci.size(); i++) {
					String prezimeImeIgraca=igraci.get(i).getClan().getPrezime().trim().toUpperCase() + " " + igraci.get(i).getClan().getIme().trim().toUpperCase();
					if (prezimeIme.startsWith(prezimeImeIgraca))
						igrac = igraci.remove(i);
				}
				if (igrac != null) {
					// POPUNJAVANJE
					for (int indeksImenaTurnira = 0; indeksImenaTurnira < turniri[0].length; indeksImenaTurnira++) {
						igrac.getRezultati().put((String) turniri[0][indeksImenaTurnira], (int) zapisIgrac
								.getCell((Integer) turniri[1][indeksImenaTurnira]).getNumericCellValue());
					}
					igrac.getRezultati().put("Plasman", (int)zapisIgrac.getCell(pozicije[1]-1).getNumericCellValue());
					zavrseniIgraci.add(igrac);
				}
			}
		}
		file.close();
		workbook.close();
		return zavrseniIgraci;
	}
}
