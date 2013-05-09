/**
 * Created by Christian Barth
 * 06.05.2013 - 10:43:40
 */
package de.hska.awp.palaver2.bestellverwaltung.service;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

//import de.bistrosoft.palaver.data.MenueplanDAO;
//import de.bistrosoft.palaver.menueplanverwaltung.domain.Menueplan;
//import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
//import de.bistrosoft.palaver.util.Week;
import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellposition;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellung;
import de.hska.awp.palaver2.data.ArtikelDAO;
import de.hska.awp.palaver2.data.BestellungDAO;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.data.LieferantDAO;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;

/**
 * @author Christian Barth
 * @author Mihail Boehm
 */
public class Bestellverwaltung extends BestellungDAO {

	private static Bestellverwaltung instance = null;

	public Bestellverwaltung() {
		super();
	}

	public static Bestellverwaltung getInstance() {
		if (instance == null) {
			instance = new Bestellverwaltung();
		}
		return instance;
	}

	public void createBestellung(Bestellung bestellung, String lieferdatum)
			throws ConnectException, DAOException, SQLException, ParseException {
			
			bestellung.setLieferdatum(lieferdatum);
			super.createBestellung(bestellung);
	}

	public void updateBestellung(Bestellung bestellung, String lieferdatum)
			throws ConnectException, DAOException, SQLException {
		
		bestellung.setLieferdatum(lieferdatum);
		super.updateBestellung(bestellung);

	}

	public List<Bestellung> getAllBestellungen() throws ConnectException,
			DAOException, SQLException {

		List<Bestellung> result = null;

		result = super.getAllBestellungen();

		return result;
	}

	public Bestellung getBestellungById(Long id) throws ConnectException,
			DAOException, SQLException {
		Bestellung bestellung = super.getBestellungById(id);
		return bestellung;
	}

	// TODO
	// Aufteilung Menge auf Durchschnitt und Kantine inkl. Berechnung Gesamt
	// Umrechnung auf Gebindegroesse auf ganze Stück
	// Aufteilung Menge auf Freitag und Montag
//	 public List<Artikel> getAllArtikelByMPAndGB(Week week) throws de.bistrosoft.palaver.data.ConnectException, de.bistrosoft.palaver.data.DAOException, SQLException, ConnectException, DAOException{
//		 List<Artikel> list = null;
//		 //1. Menueplan mit allen Artikeln und Mengen zurückliefern lassen
//		 Menueplan mp = MenueplanDAO.getInstance().getMenueplanByWeek(week);
//		 
//		 for(int i = 0 ; i < mp.getMenues().size() ; i++ ){
//			 
//		 //Fehlender Code von team1
////		 List<Rezept> rezeptlist = mp.getMenues().get(i).getRezepte();
//		 
//		 }
//		 
//		 List<Artikel> artikellist = ArtikelDAO.getInstance().getArtikelByGrundbedarf();
//		 return list;
//	 }
	
	public Bestellung generiereBestellungByArtikelByGB(Lieferant lieferant) throws SQLException, ConnectException, DAOException{
		 //TODO Input Week week muss noch erfolgen
		 List<Artikel> artikellist = ArtikelDAO.getInstance().getArtikelByGrundbedarf();
		 
		 Bestellung bestellung = new Bestellung();
		 
		 //TODO Lieferdatum nur dann berechnen, wenn es sich nicht um Edeka oder Schenk handelt
		 // "17.05.2013" und "20.05.2013" ersetzen durch die genauen Liefertermine errechnet aus der week.
		 if(lieferant.getMehrereliefertermine()==true){
			 String text = "Freitag: " + "17.05.2013" + "Montag: " + "20.05.2013";
			 bestellung.setLieferdatum(text);
		 }
		 
		 //TODO Input sieht später anders aus.
		 Date date2 = new Date(0);
		 Date timestamp = new Date(date2.getTime());
		 bestellung.setDatum(timestamp);
		 bestellung.setLieferant(lieferant);
		 
		 List<Bestellposition> bestellpositionen = new ArrayList<Bestellposition>();
		 
		 for(int i = 0 ; i < artikellist.size(); i++){
			 Bestellposition bestellposition = new Bestellposition();
			 bestellposition.setArtikel(artikellist.get(i));
			 bestellposition.setDurchschnitt(artikellist.get(i).getDurchschnitt());
			 //TODO die Menge auf Freitag und Montag aufteilen laut Menüplan
//			 bestellposition.setFreitag(freitag);
//			 bestellposition.setMontag(montag);
			 //TODO die Menge vom Menüplan auslesen und setzen
//			 bestellposition.setKantine(kantine);
			 //TODO die Kantinemenge dazu addieren
			 bestellposition.setGesamt(artikellist.get(i).getDurchschnitt());
			
			 bestellpositionen.add(bestellposition);
			 
		 }
		 
		 bestellung.setBestellpositionen(bestellpositionen);
		 
		
		 return bestellung;
	 }
	
	
	

	// TODO
	public List<Artikel> getAllArtikelByLieferant(Lieferant lieferant)
			throws ConnectException, DAOException, SQLException {
		List<Artikel> list = null;
		ArtikelDAO adao = ArtikelDAO.getInstance();
		adao.getAllArtikelByLieferantId(lieferant.getId());
		return list;
	}

	/**
	 * Die Methode gibt eine Liste mit Lieferanten zurück anhand einer Artikelliste,
	 * wobei jedoch nur jeder Lieferant nur einmal in der Liste erscheint.
	 * @author Christian Barth
	 * @param artikellist
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Lieferant> getAllLieferantenByArtikellist(
			List<Artikel> artikellist) throws ConnectException, DAOException,
			SQLException {
		List<Lieferant> list = new ArrayList<Lieferant>();
		Lieferant lieferant = null;
		LieferantDAO ldao = new LieferantDAO();
		
		for (Artikel e : artikellist) {
			lieferant = ldao.getLieferantByArtikelId(e.getId());
			if (list.isEmpty() == true) {
				list.add(lieferant);
			} 
			else {
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getId().equals(lieferant.getId()) == false) {
						list.add(lieferant);
					}
				}
			}

		}	
		return list;
	}
}
