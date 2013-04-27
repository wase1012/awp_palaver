/**
 * Created by bach1014
 * 22.04.2013 - 15:29:56
 */
package test.java.de.bistrosoft.palaver.util;

import java.util.logging.Level;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import de.bistrosoft.palaver.test.util.*;
import de.bistrosoft.palaver.test.*;

/**
 * @author bach1014
 * 
 */
public class AbstractTest {

	protected static Connection connection;

	protected static Statement stmt;

	protected static Logger log = Logger.getLogger("TestLog");

	@BeforeClass
	public static void initEmfAndEm() {
		try {
			connection = new Driver().connect(IConstants.DB_CONNECTION_URL,
					new Properties());
			stmt = connection.createStatement();
		} catch (SQLException e) {
			log.severe(e.toString());
		}
	}

	@Before
	public void transbegin() {

	}

	@AfterClass
	public static void cleanup() {
		try {
			stmt.close();
			connection.close();
		} catch (SQLException e) {
			log.severe(e.toString());
		}
	}

	// protected static EntityManagerFactory emf;
	//
	// protected static EntityManager em;
	//
	// @BeforeClass
	// public static void initEmfAndEm() {
	// Logger.getLogger("org").setLevel(Level.ALL);
	//
	// emf = Persistence.createEntityManagerFactory("awpPersistenceUnit");
	// em = emf.createEntityManager();
	// }
	//
	// @Before
	// public void transbegin() {
	//
	// if(em.getTransaction().isActive() == false){
	// em.getTransaction().begin();
	// }
	// }
	//
	// @AfterClass
	// public static void cleanup() {
	// em.close();
	// }

}
