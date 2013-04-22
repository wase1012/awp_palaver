/**
 * Created by bach1014
 * 22.04.2013 - 15:29:56
 */
package de.hska.awp.palaver2.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * @author bach1014
 *
 */
public class AbstractTest {

	protected static EntityManagerFactory emf;
	 
	protected static EntityManager em;
    
    @BeforeClass
    public static void initEmfAndEm() {
    	Logger.getLogger("org").setLevel(Level.ALL);
    	
    	 emf = Persistence.createEntityManagerFactory("awpPersistenceUnit");
         em = emf.createEntityManager();
    }
	
    @Before
    public void transbegin() {
    
    	em.getTransaction().begin();
    }
    
    @AfterClass
    public static void cleanup() {
        em.close();
    }
	
}
