/**
 * Created by Elena W
 */
package de.hska.awp.palaver2.testklassen.service;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import org.junit.Test;

import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellposition;
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
	final java.sql.Date datum = (java.sql.Date) new Date(0);
	
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
		List<Bestellposition> bestellpositionen = new ArrayList<Bestellposition>();
		 
		b.setBestellpositionen(bestellpositionen);
				
		try{
	    bv.createBestellung(b);
		}
		catch (ConnectException | DAOException | SQLException | ParseException e)
		{
			exception = true;
		}
		
		assertThat(b.getDatum(),is(datum));
	}
	
	 /**
	 * Testmethode updateBEstellung
	 * Aktualiesiert eine Bestellung in der Datenbank
     * @throws SQLException 
     * @throws DAOException 
     * @throws ConnectException 
	 */
@Test
	public void updateBestellung (){
	
		Bestellung b = new Bestellung();
		final String lieferdatum = "Heute";
	
		Boolean exception = false;
		final Long id = Long.valueOf(1);
		
	
		try{
		
			bv.getBestellungById(id);
	
		}
		catch (ConnectException | DAOException | SQLException e)
		{
			exception = true;
		}
		
		//b.setLieferant(lieferant);
		b.setLieferdatum(lieferdatum);
	
		try{
			bv.updateBestellung(b);
		}
		catch (ConnectException | DAOException| SQLException e)
		{
			exception = true;
		}
		assertThat(b.getLieferdatum(), is(lieferdatum));
	}
/**
 * Die Testmethode sucht nach einer Bestellung anhand einer Id
 * 
 * @throws SQLException
 * @throws DAOException
 * @throws ConnectException
 */
	@Test
	public void getBestellungById(){
	

		Bestellung b = null;
		Boolean exception = false;
		final Long id = Long.valueOf(2);
	
	
		try{
			b = bv.getBestellungById(id);
		}
		catch (ConnectException | DAOException | SQLException e)
		{
			exception = true;
		}
		assertThat(b.getId(),is(id));
	}

}
