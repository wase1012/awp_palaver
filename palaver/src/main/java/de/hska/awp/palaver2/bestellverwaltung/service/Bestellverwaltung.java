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

import de.bistrosoft.palaver.menueplanverwaltung.domain.Menueplan;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueplanverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasArtikel;
import de.bistrosoft.palaver.util.Week;
//import de.bistrosoft.palaver.data.MenueplanDAO;
//import de.bistrosoft.palaver.menueplanverwaltung.domain.Menueplan;
//import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
//import de.bistrosoft.palaver.util.Week;
import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver2.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver2.artikelverwaltung.service.Mengeneinheitverwaltung;
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
	

	/**
	 * Die Methode liefert alle Bestellungen zurÃ¼ck.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 * @return List<Bestellung>
	 */

	public List<Bestellung> getAllBestellungen() throws ConnectException,
			DAOException, SQLException {

		List<Bestellung> result = null;

		result = super.getAllBestellungen();

		return result;
	}

	/**
	 * Die Methode liefert eine Bestellung anhand des Parameter id zurÃ¼ck.
	 * 
	 * @param id 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 * 
	 * @return Bestellung
	 */
	public Bestellung getBestellungById(Long id) throws ConnectException,
			DAOException, SQLException {
		Bestellung bestellung = super.getBestellungById(id);
		return bestellung;
	}

	//TODO
	// Aufteilung Menge auf Durchschnitt und Kantine inkl. Berechnung Gesamt
	// Umrechnung auf Gebindegroesse auf ganze StÃ¼ck
	// Aufteilung Menge auf Freitag und Montag
	//TODO Input Week week muss noch erfolgen
	public Bestellung generiereBestellungByLieferant(Lieferant lieferant) throws SQLException, ConnectException, DAOException{
		
		 //Input
		 List<Artikel> artikellist = ArtikelDAO.getInstance().getArtikelByGrundbedarf();
		 
//		 List<Artikel> menueartikellist = MenueplanDAO.getIInstance().getMenueplanByWeek(week);
		 
		 
		 //Bestellung anlegen
		 Bestellung bestellung = new Bestellung();
		 
		 //TODO Lieferdatum nur dann berechnen, wenn es sich nicht um Edeka oder Schenk handelt
		 // "17.05.2013" und "20.05.2013" ersetzen durch die genauen Liefertermine errechnet aus der week.
		 if(lieferant.getMehrereliefertermine()==true){
			 String text = "Freitag: " + "17.05.2013" + "Montag: " + "20.05.2013";
			 bestellung.setLieferdatum(text);
		 }
		 
		 //TODO Input sieht spÃ¤ter anders aus.
		 java.util.Date date2 = new java.util.Date();
		 Date date = new Date(date2.getTime());
		 bestellung.setDatum(date);
		 bestellung.setLieferant(lieferant);
		 
		 
		 //Bestellpositionen zusammenbauen aus den zwei Listen
		 List<Bestellposition> bestellpositionen = new ArrayList<Bestellposition>();
		 
		 for(int i = 0 ; i < artikellist.size(); i++){
			 Bestellposition bestellposition = new Bestellposition();
			 bestellposition.setArtikel(artikellist.get(i));
			 bestellposition.setDurchschnitt(artikellist.get(i).getDurchschnitt());
			 //TODO die Menge auf Freitag und Montag aufteilen laut MenÃ¼plan
//			 bestellposition.setFreitag(freitag);
//			 bestellposition.setMontag(montag);
			 //TODO die Menge vom MenÃ¼plan auslesen und setzen
//			 bestellposition.setKantine(kantine);
			 //TODO die Kantinemenge dazu addieren
			 bestellposition.setGesamt(artikellist.get(i).getDurchschnitt());
			
			 bestellpositionen.add(bestellposition);
			 
		 }
		 
		 bestellung.setBestellpositionen(bestellpositionen);
		 
		
		 return bestellung;
	 }
	

	
	public List<Artikel> getAllArtikelByLieferant(Lieferant lieferant)
			throws ConnectException, DAOException, SQLException {
		List<Artikel> list = null;
		ArtikelDAO adao = ArtikelDAO.getInstance();
		adao.getAllArtikelByLieferantId(lieferant.getId());
		return list;
	}

	/**
	 * Die Methode gibt eine Liste mit Lieferanten zurÃ¼ck anhand einer Artikelliste,
	 * wobei jedoch nur jeder Lieferant nur einmal in der Liste erscheint.
	 * @author Christian Barth
	 * @param artikellist
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Lieferant> getLieferantenWithArtikel() throws ConnectException, DAOException,
			SQLException {
		List<Lieferant> list = new ArrayList<Lieferant>();
		list = LieferantDAO.getInstance().getLieferantenWithArtikel();
		
		return list;
	}
	
	
	public List<Bestellposition> generateBestellpositionList(Week week) throws ConnectException, DAOException, SQLException
	{
		List<Bestellposition> list = new ArrayList<Bestellposition>();
//		//1. Menueplan holen
//		Menueplan mp = Menueplanverwaltung.getInstance().getMenueplanByWeekWithItems(week);
//		//TODO
//		//2. Das kleinsten Datum auslesen und setzen
//		Date kleinstedatum = null;
//		//3. Get List<RezeptHasArtikel> von Menueplan mit großten datum aus der Woche (Freitag)
//		//TODO
//		List<RezeptHasArtikel> rhafreitag = null;
//		//3.1 Liste aufbereiten und doppelt Artikel zusammenfügen und Menge addieren um später Rundungsfehler zu minimieren
//		List<RezeptHasArtikel> rha = mengeAddSameArtikel(rhafreitag);
//		
//		//4. Füge List<RezeptHasArtikel> von Menueplan mit kleinste datum in List<Bestellposition>
//		int leer = 0;
//		
//		Bestellposition bpo = new Bestellposition();
//		
//		bpo.setArtikel(rha.get(0).getArtikel());
//		bpo.setDurchschnitt(rha.get(0).getArtikel().getDurchschnitt());
//		bpo.setFreitag(convertMenge(rha.get(0)) + bpo.getDurchschnitt());
//		bpo.setKantine(convertMenge(rha.get(0)));
//		bpo.setGeliefert(false);
//		bpo.setMontag(leer);
//		bpo.setGesamt(bpo.getKantine() + bpo.getDurchschnitt());
//		list.add(bpo);
//		
//		
//		
//		for(int i = 0 ; i < rha.size() ; i++){
//			Bestellposition bp = new Bestellposition();
//			bp.setArtikel(rha.get(i).getArtikel());
//			//5. Umrechen der Menge Menge/Bestellgröße durch die Methode convertBestellmenge
//			bp.setDurchschnitt(rha.get(i).getArtikel().getDurchschnitt());
//			//6. Setzen der Menge auf Freitag und Durchschnitt hinzuaddieren
//			bp.setFreitag(convertMenge(rha.get(i))+bp.getDurchschnitt());
//			bp.setKantine(convertMenge(rha.get(i)));
//			bp.setGeliefert(false);
//			bp.setMontag(leer);
//			bp.setGesamt(bp.getKantine()+bp.getDurchschnitt());
//			
//			//7. Falls Liste leer dann hinzufügen, andernfalls prüfen ob Liste Artikel schon hat, 
//			//   wenn ja dann vorhandenen Bestellposition ändern, bei nein Bestellposition hinzufügen
//			if(list==null){
//				list.add(bp);
//			}
//			else {
//				boolean vorhanden = false;
//				for(int z = 0 ; z < list.size() ; z++){
//					if(bp.getArtikel().equals(list.get(z).getArtikel())){
//						vorhanden = true;
//						list.get(z).setFreitag(list.get(z).getFreitag()+bp.getFreitag());
//						list.get(z).setGesamt(list.get(z).getGesamt()+bp.getFreitag()-bp.getDurchschnitt());
//						list.get(z).setKantine(list.get(z).getKantine()+bp.getKantine());
//					}	
//				}
//				if(vorhanden == false){
//					list.add(bp);
//				}
//			}
//		}
//		
//		//8. Get List<RezeptHasArtikel> von Menueplan für Mo,Di,Mi,Do aus der Woche
//		//TODO
//		List<RezeptHasArtikel> rhamobisdo = null;
//		//8.1 
//		rha = mengeAddSameArtikel(rhamobisdo);
//		//9. Wenn Artikel vorhanden, dann vorhandenen verändern(Menge addieren), andersfalls
//		// neue Bestellposition hinzufügen
//		for(int i = 0 ; i < rha.size() ; i++){
//			boolean vorhanden = false;
//			for(int z = 0 ; z < list.size() ; z++){
//				
//				if(rha.get(i).getArtikel().equals(list.get(z).getArtikel())){
//					vorhanden = true;
//					list.get(z).setMontag(list.get(z).getMontag() + convertMenge(rha.get(i)));
//					list.get(z).setGesamt(list.get(z).getGesamt() + convertMenge(rha.get(i)));
//					list.get(z).setKantine(list.get(z).getKantine() + convertMenge(rha.get(i)));
//					
//				}	
//			}
//			if(vorhanden==false){
//				Bestellposition bp = new Bestellposition();
//				bp.setArtikel(rha.get(i).getArtikel());
//				//5. Umrechen der Menge Menge/Bestellgröße durch die Methode convertBestellmenge
//				bp.setDurchschnitt(rha.get(i).getArtikel().getDurchschnitt());
//				//6. Setzen der Menge auf Freitag und Durchschnitt hinzuaddieren
//				bp.setFreitag(convertMenge(rha.get(i))+bp.getDurchschnitt());
//				bp.setKantine(convertMenge(rha.get(i)));
//				bp.setGeliefert(false);
//				bp.setMontag(leer);
//				bp.setGesamt(bp.getKantine()+bp.getDurchschnitt());
//
//				list.add(bp);
//			}
//		}
//		
//		//10. Artikel mit Grundbedarf auslesen
//		List<Artikel> alist = Artikelverwaltung.getInstance().getArtikelByGrundbedarf();
//		
//		//11. Artikel überprüfen ob schon vorhanden in List<Bestellposition> list, wenn nein hinzufügen,
//		//falls ja nichts machen, da ja der durchschnitt schon vorhanden ist
//		for(int i = 0 ; i < alist.size() ; i++){
//			boolean vorhanden = false;
//			for(int z = 0 ; z < list.size() ; z++){
//				if(alist.get(i).equals(list.get(z).getArtikel())){
//					vorhanden = true;	
//				}
//			}
//			if(vorhanden = false){
//				Bestellposition bp = new Bestellposition();
//				bp.setArtikel(alist.get(i));
//				bp.setDurchschnitt(alist.get(i).getDurchschnitt());
//				bp.setFreitag(bp.getDurchschnitt());
//				bp.setKantine(leer);
//				bp.setGeliefert(false);
//				bp.setMontag(leer);
//				bp.setGesamt(bp.getDurchschnitt());
//				list.add(bp);
//			}
//			
//		}
//			
		return list;
	}
	
	private int convertMenge(RezeptHasArtikel rha){
		int bm = 0;
//		//Math.ceil wird immer auf ganze Zahl aufgerundet
//		bm = (int)Math.ceil(rha.getMenge()/rha.getArtikel().getBestellgroesse());
		return bm;
	}
	
	private List<RezeptHasArtikel> mengeAddSameArtikel(List<RezeptHasArtikel> rhalist){
		
		List<RezeptHasArtikel> list = new ArrayList<RezeptHasArtikel>();
//		list.add(rhalist.get(0));
//		
//		for(int i = 1 ; i < rhalist.size(); i++){
//			boolean vorhanden = false;
//			for(int z = 0 ; z < list.size(); z++){
//				if(list.get(z).getArtikel.equals(rhalist.get(i).getArtikel())){
//					list.get(z).setMenge(list.get(z).getMenge() + rhalist.get(i).getMenge());
//					vorhanden = true;
//				}
//				
//			}
//			if(vorhanden==false){
//				list.add(rhalist.get(i));
//			}
//		}
		
		return list;
	}
	
	
	public List<Lieferant> getAllLieferantByListOfBestellposition(List<Bestellposition> bplist){
		List<Lieferant> list = new ArrayList<Lieferant>();
		list.add(bplist.get(0).getArtikel().getLieferant());
		for(int i = 1 ; i < bplist.size(); i++){
			boolean vorhanden = false;
			for(int z = 0 ; z < list.size(); z++){
				if(bplist.get(i).getArtikel().getLieferant().equals(list.get(z))){
					vorhanden = true;
				}
				
			}
			if(vorhanden==false){
				list.add(bplist.get(i).getArtikel().getLieferant());
			}
		}

		return list;
	}
	
	public Bestellung generateBestellungByListOfBestellpositionAndByLieferant(Lieferant lieferant, List<Bestellposition> bplist ){
		
		Bestellung b = new Bestellung();
		b.setBestellt(false);
		b.setLieferant(lieferant);
		java.util.Date date2 = new java.util.Date();
		Date date = new Date(date2.getTime());
		b.setDatum(date);
		
		List<Bestellposition> list = new ArrayList<Bestellposition>();
		
		for(int i = 0 ; i < bplist.size(); i++){
			
			if(bplist.get(i).getArtikel().getLieferant().equals(lieferant)){
				list.add(bplist.get(i));
			}
		}
		b.setBestellpositionen(list);
		return b;
	}
	
}
