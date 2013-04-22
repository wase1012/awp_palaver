package de.hska.awp.palaver2.testklassen.domain;


	
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Test;

import de.hska.awp.palaver2.lieferantenverwaltung.domain.Ansprechpartner;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.util.AbstractTest;
	  
public class AnsprechpartnerTest extends AbstractTest  { 
	 
	 
	    @Test
	    public void findAnsprechpartnerByName() {
	    	
	    	final String name = "Max Mustermann";

			final TypedQuery<Ansprechpartner> query = em.createNamedQuery(
					Ansprechpartner.FIND_ANSPRECHPARTNER_BY_NAME, Ansprechpartner.class);
			query.setParameter(Ansprechpartner.PARAM_NAME, name);
			final List<Ansprechpartner> kunden = query.getResultList();

			assertThat(kunden.isEmpty(), is(false));
	    }
	    
	    @Test
	    public void findAnsprechpartnerById() {
	    	
	    	final Long id = Long.valueOf("1");

			Ansprechpartner ap = em.find(Ansprechpartner.class, id);

			assertThat(ap.getId(), is(id));
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
	    
	    @Test
	    public void deleteAnsprechpartner() {
	    	
	    	
	    	String name = "Testname";
	    	
	    	final TypedQuery<Ansprechpartner> query = em.createNamedQuery(
					Ansprechpartner.FIND_ANSPRECHPARTNER_BY_NAME, Ansprechpartner.class);
			query.setParameter(Ansprechpartner.PARAM_NAME, name);
			final List<Ansprechpartner> aplist = query.getResultList();
	    	
	    	for(int i = 0; i < aplist.size(); i++){
	    		Ansprechpartner ap = aplist.get(i);
	    		em.remove(ap);
		    	
	    	}
	    	em.getTransaction().commit();
	    }
	} 
	
