package test.java.de.hska.awp.lieferantenverwaltung.domain;


	
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.hska.awp.palaver2.lieferantenverwaltung.domain.Ansprechpartner;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
	  
public class AnsprechpartnerTest { 
	
		private EntityManagerFactory emf;
	 
	    private EntityManager em;
	 
	    @Before
	    public void initEmfAndEm() {
	       
	        Logger.getLogger("org").setLevel(Level.ALL);
	 
	        emf = Persistence.createEntityManagerFactory("awpPersistenceUnit");
	        em = emf.createEntityManager();
	    }
	 
	    @After
	    public void cleanup() {
	        em.close();
	    }
	 
	    @Test
	    public void emptyTest() {
	    	
	    	em.getTransaction().begin();
	    	
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
	    	
	    	em.getTransaction().begin();
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
	
