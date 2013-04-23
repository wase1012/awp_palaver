/**
 * Created by bach1014
 * 23.04.2013 - 11:27:25
 */
package de.hska.awp.palaver2.testklassen.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Test;

import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.util.AbstractTest;

/**
 * @author bach1014
 * Testklasse fuer den Lieferant
 */
public class LieferantTest extends AbstractTest {
	
		/**
		 * Testmethode findLieferantByName
		 * Suche nach einen Lieferant ueber einen Namen
		 */
		@Test
	    public void findLieferantByName() {
	    	
	    	final String name = "Edeka";

			final TypedQuery<Lieferant> query = em.createNamedQuery(
					Lieferant.FIND_LIEFERANT_BY_NAME, Lieferant.class);
			query.setParameter(Lieferant.PARAM_NAME, name);
			final List<Lieferant> liefantlist = query.getResultList();

			assertThat(liefantlist.isEmpty(), is(false));
	    }
	    
	 	/**
		 * Testmethode findLieferantById
		 * Suche nach einen Lieferant ueber eine Id
		 */
	    @Test
	    public void findLieferantById() {
	    	
	    	final Long id = Long.valueOf("1");

	    	Lieferant lieferant = em.find(Lieferant.class, id);

			assertThat(lieferant.getId(), is(id));
	    }
	    
	    /**
		 * Testmethode createLieferant
		 * Erzeugen eines Lieferants in der Datenbank
		 */
	    @Test
	    public void createLieferant() {
	    	
	    	Lieferant lieferant = new Lieferant();
	    	lieferant.setName("Testlieferant");
	    	lieferant.setTelefon("0175/55667788");
	    	
	    	em.persist(lieferant);
	    	em.getTransaction().commit();
	    }
	    
}
