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

import de.hska.awp.palaver2.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver2.util.AbstractTest;

/**
 * @author bach1014
 * Testklasse fuer die Mengeneinheit
 */
public class MengeneinheitTest extends AbstractTest {
	
		/**
		 * Testmethode findMengeneinheitByName
		 * Suche nach einer Mengeneinheit ueber einen Namen
		 */
		@Test
	    public void findMengeneinheitByName() {
	    	
	    	final String name = "Kilogramm";

			final TypedQuery<Mengeneinheit> query = em.createNamedQuery(
					Mengeneinheit.FIND_MENGENEINHEIT_BY_NAME, Mengeneinheit.class);
			query.setParameter(Mengeneinheit.PARAM_NAME, name);
			final List<Mengeneinheit> melist = query.getResultList();

			assertThat(melist.isEmpty(), is(false));
	    }
	    
	 	/**
		 * Testmethode findMengeneinheitById
		 * Suche nach einer Mengeneinheit ueber eine Id
		 */
	    @Test
	    public void findMengeneinheitById() {
	    	
	    	final Long id = Long.valueOf("1");

	    	Mengeneinheit me = em.find(Mengeneinheit.class, id);

			assertThat(me.getId(), is(id));
	    }
	    
	    /**
		 * Testmethode createMengeneinheit
		 * Erzeugen einer Mengeneinheit in der Datenbank
		 */
	    @Test
	    public void createMengeneinheit() {
	    	
	    	Mengeneinheit me = new Mengeneinheit();
	    	me.setName("Tonnen");
	    	me.setKurz("T");
	    	
	    	em.persist(me);
	    	em.getTransaction().commit();
	    }
	    
}

