package de.hska.awp.palaver2.testklassen.domain;

import org.junit.Test;

import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.nachrichtenverwaltung.domain.Nachricht;
import de.hska.awp.palaver2.util.AbstractTest;

/**
 * @author PhilippT
 *
 */

public class NachrichtTest extends AbstractTest {
	

	private static final String test_nachricht = "Ich bin die Testnachricht";	
	
	@Test
	public void createNachrichten() {
		
		final Nachricht n = new Nachricht();
		n.setNachricht(test_nachricht);
		
    	Long sid = Long.valueOf(1);
    	Mitarbeiter sender = em.find(Mitarbeiter.class, sid);
    	n.setMitarbeiterBySenderFk(sender);
    	
    	Long eid = Long.valueOf(2);   	
    	Mitarbeiter empfaenger = em.find(Mitarbeiter.class, eid);
		n.setMitarbeiterByEmpfaengerFk(empfaenger);    	
    	
    	em.persist(n);
    	em.getTransaction().commit();
		
	}
			
	
}