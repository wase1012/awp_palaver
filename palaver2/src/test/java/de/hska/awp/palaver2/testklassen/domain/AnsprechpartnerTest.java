package de.hska.awp.palaver2.testklassen.domain;


	
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import de.hska.awp.palaver2.data.AnsprechpartnerDAO;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.data.LieferantDAO;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Ansprechpartner;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.util.AbstractTest;
	  
/**
 * @author bach1014
 * Testklasse fuer den Ansprechpartner
 */
public class AnsprechpartnerTest extends AbstractTest  { 
	
	public static String NAME = "Armin Maier";
	public static String TELEFON = "6666666";
	public static Long ID = Long.valueOf("1");
	public static Long LIEFERANT_ID = Long.valueOf("1");

	private AnsprechpartnerDAO dao = new AnsprechpartnerDAO();
	private LieferantDAO lieferantdao = new LieferantDAO();

	@Test
	public void getAnsprechpartner()
	{
		Boolean exception = false;
		List<Ansprechpartner> aplist = null;
		try
		{
			aplist = dao.getAllAnsprechpartner();
		} 
		catch (ConnectException | DAOException | SQLException e)
		{
			exception = true;
		}
		assertThat(aplist.isEmpty(), is(false));
		assertThat(exception, is(false));
	}
	
	/**
	 * Testmethode findLieferantenByName
	 * Suche nach einen Lieferanten ueber einen Namen
	 * @throws SQLException 
	 * @throws DAOException 
	 * @throws ConnectException 
	 */
	@Test
    public void findAnsprechpartnerByName() throws ConnectException, DAOException, SQLException {
		
		Boolean exception = false;
		List<Ansprechpartner> aplist = null;
		try
		{
			aplist = dao.getAnsprechpartnerByName(NAME);
		} 
		catch (ConnectException | DAOException | SQLException e)
		{
			exception = true;
		}
		assertThat(aplist.isEmpty(), is(false));
		assertThat(exception, is(false));
    }
    
 	/**
	 * Testmethode findLieferantById
	 * Suche nach einen Lieferant ueber eine Id
 	 * @throws SQLException 
 	 * @throws DAOException 
 	 * @throws ConnectException 
	 */
    @Test
    public void findAnsprechpartnerById() throws ConnectException, DAOException, SQLException {

    	Ansprechpartner ansprechpartner = dao.getAnsprechpartnerById(ID);

		assertThat(ansprechpartner.getId(), is(ID));
    }
    
    /**
	 * Testmethode createLieferant
	 * Erzeugen eines Lieferants in der Datenbank
     * @throws SQLException 
     * @throws DAOException 
     * @throws ConnectException 
	 */

    @Test
    public void createAnsprechpartner() throws ConnectException, DAOException, SQLException {
    	
    	Ansprechpartner ansprechpartner = new Ansprechpartner();
    	ansprechpartner.setName("TestAnsprechpartner");
    	ansprechpartner.setTelefon(TELEFON);
    	Lieferant lieferant = lieferantdao.getLieferantById(LIEFERANT_ID);
    	ansprechpartner.setLieferant(lieferant);
    	dao.createAnsprechpartner(ansprechpartner);
    	
    }
    
    @Test
    public void updateAnsprechpartner() throws ConnectException, DAOException, SQLException {
    	
    	Ansprechpartner ansprechpartner = dao.getAnsprechpartnerById(ID);
    	ansprechpartner.setTelefon(TELEFON);
    	dao.updateAnsprechpartner(ansprechpartner);
    	
    }
	 
//		/**
//		 * Testmethode findAnsprechpartnerByName
//		 * Suche nach einen Ansprechpartner ueber einen Namen
//		 */
//	    @Test
//	    public void findAnsprechpartnerByName() {
//	    	
//	    	final String name = "Armin Maier";
//
//			final TypedQuery<Ansprechpartner> query = em.createNamedQuery(
//					Ansprechpartner.FIND_ANSPRECHPARTNER_BY_NAME, Ansprechpartner.class);
//			query.setParameter(Ansprechpartner.PARAM_NAME, name);
//			final List<Ansprechpartner> aplist = query.getResultList();
//
//			assertThat(aplist.isEmpty(), is(false));
//	    }
//	    
//	    /**
//		 * Testmethode findAnsprechpartnerById
//		 * Suche nach einen Ansprechpartner ueber eine ID
//		 */
//	    @Test
//	    public void findAnsprechpartnerById() {
//	    	
//	    	final Long id = Long.valueOf("1");
//
//			Ansprechpartner ap = em.find(Ansprechpartner.class, id);
//
//			assertThat(ap.getId(), is(id));
//	    }
//	    
//	    /**
//		 * Testmethode createAnsprechpartner
//		 * Erzeugen eines Ansprechpartners in der Datenbank
//		 */
//	    @Test
//	    public void createAnsprechpartner() {
//	    	
//	    	Ansprechpartner ap = new Ansprechpartner();
//	    	ap.setName("Testname");
//	    	ap.setTelefon("0175/55667788");
//	    	
//	    	Long id = Long.valueOf(1);
//	    	Lieferant lieferant = em.find(Lieferant.class, id);
//	    	ap.setLieferant(lieferant);
//	    	
//	    	em.persist(ap);
//	    	em.getTransaction().commit();
//	    }
//	    
//	    /**
//		 * Testmethode deleteAnsprechpartner
//		 * Loeschen eines Ansprechpartners in der Datenbank
//		 */
//	    @Test
//	    public void deleteAnsprechpartner() {
//	    	
//	    	
//	    	String name = "Testname";
//	    	
//	    	final TypedQuery<Ansprechpartner> query = em.createNamedQuery(
//					Ansprechpartner.FIND_ANSPRECHPARTNER_BY_NAME, Ansprechpartner.class);
//			query.setParameter(Ansprechpartner.PARAM_NAME, name);
//			final List<Ansprechpartner> aplist = query.getResultList();
//	    	
//	    	for(int i = 0; i < aplist.size(); i++){
//	    		Ansprechpartner ap = aplist.get(i);
//	    		em.remove(ap);
//		    	
//	    	}
//	    	em.getTransaction().commit();
//	    }
	} 
	
