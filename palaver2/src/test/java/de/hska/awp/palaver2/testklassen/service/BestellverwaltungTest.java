/**
 * Created by Elena W
 */
package de.hska.awp.palaver2.testklassen.service;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import de.hska.awp.palaver2.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellung;
import de.hska.awp.palaver2.bestellverwaltung.service.Bestellverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;

/**
 * Testklasse für die Bestellverwaltung
 * @author Elena W
 */

public class BestellverwaltungTest {
	Bestellverwaltung bv = Bestellverwaltung.getInstance();
	private static final Long BESTELLUNG_ID_VORHANDEN = Long.valueOf(1);
	final Lieferant lieferant = new Lieferant();
	final java.sql.Date datum = (java.sql.Date) new Date();
	
	/**
	 * Testmethode createBestellung
	 * Erzeugt eine Bestellung in der Datenbank
	 * @throws ParseException
	 * @throws SQLException 
	 * @throws DAOException 
	 * @throws ConnectException 
	 */
	@Test
	public void createBestellung(){
		
		Bestellung b = new Bestellung();
		Boolean exception = false;
				
		b.setDatum(datum);
		b.setLieferant(lieferant);
				
		try{
	    bv.createBestellung(b);
		}
		catch (ConnectException | DAOException | SQLException | ParseException e)
		{
			exception = true;
		}
		
		assertThat(b.getDatum(),is(datum));
	}

}
