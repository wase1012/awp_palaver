package de.hska.awp.palaver2.lieferantenverwaltung.domain;


	
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Test;

import de.hska.awp.palaver2.util.AbstractTest;
	  
public class AnsprechpartnerTest extends AbstractTest  { 
	 
	 
	    @Test
	    public void emptyTest() {
	    	
	    	final String name = "Max Mustermann";

			// When
			final TypedQuery<Ansprechpartner> query = em.createNamedQuery(
					Ansprechpartner.FIND_ANSPRECHPARTNER_BY_NAME, Ansprechpartner.class);
			query.setParameter(Ansprechpartner.PARAM_NAME, name);
			final List<Ansprechpartner> kunden = query.getResultList();

			// Then
			assertThat(kunden.isEmpty(), is(false));
			System.out.print(kunden);
	    }
	    
	    @Test
	    public void createAnsprechpartner() {
	    	
	    	Ansprechpartner ap = new Ansprechpartner();
	    	ap.setName("Testname");
	    	ap.setTelefon("0175/55667788");
	    	
	    	Long id = Long.valueOf(1);
	    	Lieferant lieferant = em.find(Lieferant.class, id);
	    	ap.setLieferant(lieferant);
	    	
	    	em.persist(ap);
	    	em.getTransaction().commit();
	    }
	} 
	
