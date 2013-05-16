/**
 * Created by Christian Barth
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
 * Testklasse für die Lieferanten
 * @author Christian Barth
 *
 */
public class LieferantTest extends AbstractTest{
	

		public static String NAME = "Edeka";
		public static Long ID = Long.valueOf("1");
		public static String KUNDENNUMMER = "666666";
	
		private LieferantDAO dao = new LieferantDAO();
		
		/**
		 * Die Testmethode liefert alle Lieferanten aus der Datenbank zurück.
		 * @throws SQLException 
		 * @throws DAOException 
		 * @throws ConnectException
		 */
		@Test
		public void getLieferanten()
		{
			Boolean exception = false;
			List<Lieferant> liefantlist = null;
			try
			{
				liefantlist = dao.getAllLieferanten();
			} 
			catch (ConnectException | DAOException | SQLException e)
			{
				exception = true;
			}
			assertThat(liefantlist.isEmpty(), is(false));
			assertThat(exception, is(false));
		}
		
		@Test
		public void getLieferantenByArtikelId(){
			Boolean exception = false;
			Lieferant liefant = null;
			try
			{
				liefant = dao.getLieferantByArtikelId(1L);
			} 
			catch (ConnectException | DAOException | SQLException e)
			{
				exception = true;
			}
			assertThat(liefant.getId(), is(1L));
			assertThat(exception, is(false));
		}
		
		/**
		 * Die Testmethode sucht nach einen Lieferanten anhand eines Namen.
		 * @throws SQLException 
		 * @throws DAOException 
		 * @throws ConnectException 
		 */
		@Test
	    public void getLieferantByName() throws ConnectException, DAOException, SQLException {
			
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
		 * Die Testmethode sucht nach einen Lieferant anhand einer Id
	 	 * @throws SQLException 
	 	 * @throws DAOException 
	 	 * @throws ConnectException 
		 */
	    @Test
	    public void getLieferantById() throws ConnectException, DAOException, SQLException {

	    	Lieferant lieferant = dao.getLieferantById(ID);

			assertThat(lieferant.getId(), is(ID));
	    }
	    
	    /**
		 * Die Testmethode erzeugt ein Lieferant in der Datenbank.
	     * @throws SQLException 
	     * @throws DAOException 
	     * @throws ConnectException 
		 */
	    @Test
	    public void createLieferant() throws ConnectException, DAOException, SQLException {
	    	
	    	Lieferant lieferant = new Lieferant();
	    	lieferant.setName("Testlieferant");
	    	lieferant.setTelefon("0175/55667788");
	    	lieferant.setNotiz("test");
	    	lieferant.setMehrereliefertermine(false);
	    	dao.createLieferant(lieferant);
	    	
	    }
	    
	    /**
		 * Die Testmethode aktualisiert ein Lieferant in der Datenbank.
	     * @throws SQLException 
	     * @throws DAOException 
	     * @throws ConnectException 
		 */
	    @Test
	    public void updateLieferant() throws ConnectException, DAOException, SQLException {
	    	
	    	Lieferant lieferant = dao.getLieferantById(ID);
	    	lieferant.setKundennummer(KUNDENNUMMER);
	    	dao.updateLieferant(lieferant);
	    	
	    }
	    
}
