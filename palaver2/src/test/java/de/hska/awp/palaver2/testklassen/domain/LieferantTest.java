/**
 * Created by bach1014
 * 23.04.2013 - 11:27:25
 */
package de.hska.awp.palaver2.testklassen.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.data.LieferantDAO;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.util.AbstractTest;

/**
 * @author bach1014
 * Testklasse fuer den Lieferant
 */
public class LieferantTest extends AbstractTest{
	

		public static String NAME = "Edeka";
		public static Long ID = Long.valueOf("1");
		public static String KUNDENNUMMER = "666666";
	
		private LieferantDAO dao = new LieferantDAO();
		

		@Test
		public void getLieferanten()
		{
			Boolean exception = false;
			List<Lieferant> list = null;
			try
			{
				list = dao.getAllLieferanten();
			} 
			catch (ConnectException | DAOException | SQLException e)
			{
				exception = true;
			}
			assertThat(list.isEmpty(), is(false));
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
	    public void findLieferantByName() throws ConnectException, DAOException, SQLException {
			
			Boolean exception = false;
			List<Lieferant> liefantlist = null;
			try
			{
	    	liefantlist = dao.getLieferantenByName(NAME);
			} 
			catch (ConnectException | DAOException | SQLException e)
			{
				exception = true;
			}
			assertThat(liefantlist.isEmpty(), is(false));
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
	    public void findLieferantById() throws ConnectException, DAOException, SQLException {

	    	Lieferant lieferant = dao.getLieferantById(ID);

			assertThat(lieferant.getId(), is(ID));
	    }
//	    
//	    /**
//		 * Testmethode createLieferant
//		 * Erzeugen eines Lieferants in der Datenbank
//	     * @throws SQLException 
//	     * @throws DAOException 
//	     * @throws ConnectException 
//		 */
//
//	    @Test
//	    public void createLieferant() throws ConnectException, DAOException, SQLException {
//	    	
//	    	Lieferant lieferant = new Lieferant();
//	    	lieferant.setName("Testlieferant");
//	    	lieferant.setTelefon("0175/55667788");
//	    	dao.createLieferant(lieferant);
//	    	
//	    }
//	    
//	    @Test
//	    public void updateLieferant() throws ConnectException, DAOException, SQLException {
//	    	
//	    	Lieferant lieferant = dao.getLieferantById(ID);
//	    	lieferant.setKundennummer(KUNDENNUMMER);
//	    	dao.createLieferant(lieferant);
//	    	
//	    }
	    
}
