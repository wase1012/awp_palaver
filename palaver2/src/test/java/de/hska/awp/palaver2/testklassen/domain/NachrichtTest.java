package de.hska.awp.palaver2.testklassen.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Test;

import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Rollen;
import de.hska.awp.palaver2.nachrichtenverwaltung.domain.Nachricht;
import de.hska.awp.palaver2.util.AbstractTest;

/**
 * @author PhilippT
 *
 */

public class NachrichtTest extends AbstractTest {
	

	private static final String test_nachricht = "Ich bin die Testnachricht";	
	
    @Test
    public void findNachrichtById() {
    	
    	Long id = Long.valueOf(2);

		Nachricht nachricht = em.find(Nachricht.class, id);

		assertThat(nachricht.getId(), is(id));
    }
    
	@Test
	public void createNachrichten() {
		
		final Nachricht n = new Nachricht();
		n.setNachricht(test_nachricht);
		
    	Long sid = Long.valueOf(1);
    	Mitarbeiter sender = em.find(Mitarbeiter.class, sid);
    	n.setMitarbeiterBySenderFk(sender);
    	
    	Long eid = Long.valueOf(1);   	
    	Rollen empfaenger = em.find(Rollen.class, eid);
		n.setEmpfaengerRolle(empfaenger);    	
    	
    	em.persist(n);
    	em.getTransaction().commit();
		
	}
	
    @Test
    public void deleteNachricht() {
    	
    	Long id = Long.valueOf(1);
    	
    	final TypedQuery<Nachricht> query = em.createNamedQuery(
				Nachricht.FIND_Nachricht_BY_ID, Nachricht.class);
		query.setParameter(Nachricht.PARAM_ID, id);
		final List<Nachricht> nachricht = query.getResultList();
    	
    	for(int i = 0; i < nachricht.size(); i++){
    		Nachricht nr = nachricht.get(i);
    		em.remove(nr);
	    	
    	}
    	em.getTransaction().commit();
    }
			
}