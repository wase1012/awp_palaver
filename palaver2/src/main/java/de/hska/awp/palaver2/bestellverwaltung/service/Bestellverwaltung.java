/**
 * Created by Christian Barth
 * 06.05.2013 - 10:43:40
 */
package de.hska.awp.palaver2.bestellverwaltung.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

//import de.bistrosoft.palaver.data.MenueplanDAO;
//import de.bistrosoft.palaver.menueplanverwaltung.domain.Menueplan;
//import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
//import de.bistrosoft.palaver.util.Week;
import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellung;
import de.hska.awp.palaver2.data.ArtikelDAO;
import de.hska.awp.palaver2.data.BestellungDAO;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.data.LieferantDAO;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.util.BestellungData;

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

	public void createBestellung(Bestellung bestellung)
			throws ConnectException, DAOException, SQLException, ParseException {

			super.createBestellung(bestellung);
	}

	public void updateBestellung(Bestellung bestellung)
			throws ConnectException, DAOException, SQLException {

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
	@SuppressWarnings("null")
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
