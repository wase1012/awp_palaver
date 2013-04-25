package de.hska.awp.palaver2.testklassen.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hska.awp.palaver2.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellung;

import org.hibernate.type.TimestampType;
import org.junit.Test;

import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.util.AbstractTest;

public class BestellungTest extends AbstractTest {
	
	final Long ID = Long.valueOf("1");
	Bestellung bestellung = new Bestellung();
	Date d = new Date();
	
	 /**
	 * Testmethode findBestellungById
	 * Suche nach einen Bestellung ueber eine ID
	 */
    @Test
    public void findBestellungById() {
    	bestellung = em.find(Bestellung.class, ID);
		assertThat( bestellung.getId(), is(ID));
	}
    /**
   	 * Testmethode createBestellung
   	 * Erzeugen einer Bestellung in der Datenbank
   	 */
       @Test
       public void createBestellung() {
    	
    	  bestellung.setDatum(d);    	  
    	  Long id = Long.valueOf(1);
    	  Lieferant lieferant = em.find(Lieferant.class, id);
	      bestellung.setLieferant(lieferant);
    	  em.persist(bestellung);
       	  em.getTransaction().commit();
       }
       

}
