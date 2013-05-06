/**
 * Created by Christian Barth
 * 06.05.2013 - 10:43:40
 */
package de.hska.awp.palaver2.bestellverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellung;
import de.hska.awp.palaver2.data.BestellungDAO;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;

/**
 * @author Christian Barth
 *
 */
public class Bestellverwaltung extends BestellungDAO {
	
	private static Bestellverwaltung instance = null;

	private Bestellverwaltung() {
		super();
	}

	public static Bestellverwaltung getInstance() {
		if (instance == null) {
			instance = new Bestellverwaltung();
		}
		return instance;
	}

	
	//TODO
	public void createBestellung(Bestellung bestellung) throws ConnectException,
	DAOException, SQLException {

		super.createBestellung(bestellung);
	}
	
	//TODO
	public void updateBestellung(Bestellung bestellung) throws ConnectException,
	DAOException, SQLException {

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
	
	
	
	//TODO
	// Aufteilung Menge auf Durchschnitt und Kantine inkl. Berechnung Gesamt
	// Aufteilung Menge auf Freitag und Montag
//	public List<Artikel> getAllArtikelByMPAndGB(  Input KW     ){
//		
//	}
	
	//TODO
//	public List<Artikel> getAllArtikelByLieferant(Lieferant lieferant){
//		
//	}
	
	//TODO
//	public List<Lieferant> getAllLieferantenByArtikellist(List<Artikel> artikellist){
//		
//	}
	
	
	
}
