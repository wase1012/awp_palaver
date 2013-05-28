package de.hska.awp.palaver2.excel;

import java.io.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;

import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellposition;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellung;
import de.hska.awp.palaver2.bestellverwaltung.service.Bestellpositionverwaltung;

public class CreateExcelFile {
	
	private static CreateExcelFile instance = null;

	public CreateExcelFile() {
		super();
	}

	public static CreateExcelFile getInstance() {
		if (instance == null) {
			instance = new CreateExcelFile();
		}
		return instance;
	}
	
	public String Create(Bestellung bestellung) {
		File file = null;
		
		try {
			String filename = "Bestellung_" + bestellung.getLieferant().getName() + "_id_" + bestellung.getId()+".xls";
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet("new sheet");

			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell((short) 0).setCellValue("Artikelnummer");
			rowhead.createCell((short) 1).setCellValue("Artikelname");
			rowhead.createCell((short) 2).setCellValue("WGR");
			rowhead.createCell((short) 3).setCellValue("Inh");
			rowhead.createCell((short) 4).setCellValue("VP");
			rowhead.createCell((short) 5).setCellValue("Preis");
			rowhead.createCell((short) 6).setCellValue("Freitag");
			rowhead.createCell((short) 7).setCellValue("Montag");
			
			int i = 1;
			for (Bestellposition bp : Bestellpositionverwaltung.getInstance()
					.getBestellpositionenByBestellungId(bestellung.getId())) 
			{
				HSSFRow row = sheet.createRow((short) i);
				row.createCell((short) 0).setCellValue(bp.getArtikel().getArtikelnr());
				row.createCell((short) 1).setCellValue(bp.getArtikelName());
				row.createCell((short) 2).setCellValue(bp.getArtikel().getKategorie().getName());
				row.createCell((short) 3).setCellValue(bp.getGesamt());
				row.createCell((short) 4).setCellValue(bp.getArtikel().getMengeneinheit().getKurz());
				row.createCell((short) 5).setCellValue(bp.getArtikel().getPreis());
				row.createCell((short) 6).setCellValue(bp.getFreitag());
				row.createCell((short) 7).setCellValue(bp.getMontag());
				
				++i;
			}

			FileOutputStream fileOut = new FileOutputStream(filename);
			file = new File(filename);
			hwb.write(fileOut);
			fileOut.close();
			System.out.println("Your excel file has been generated!");

		} catch (Exception ex) {
			System.out.println(ex);

		}
		return file.getAbsolutePath();
	}
}