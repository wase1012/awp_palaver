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

import de.hska.awp.palaver2.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.data.MengeneinheitDAO;
import de.hska.awp.palaver2.util.AbstractTest;

/**
 * @author bach1014
 * Testklasse fuer die Mengeneinheit
 */
public class MengeneinheitTest extends AbstractTest {
	
		/**
		 * Testmethode findMengeneinheitByName
		 * Suche nach einer Mengeneinheit ueber einen Namen
		 * 
		 */
	
	private MengeneinheitDAO mdao = new MengeneinheitDAO();
	private String name = "Kilogramm";
	
	
	@Test
    public void findMengeneinheitByName() throws ConnectException, DAOException, SQLException {
		
		Boolean exception = false;
		List<Mengeneinheit> list = null;
		
		try
		{
    	list = mdao.getMengeneinheitByName(name);
		} 
		catch (ConnectException | DAOException | SQLException e)
		{
			exception = true;
		}
		assertThat(list.isEmpty(), is(false));
		assertThat(exception, is(false));
    }
	    
	 	/**
		 * Testmethode findMengeneinheitById
		 * Suche nach einer Mengeneinheit ueber eine Id
		 */
	    @Test
	    public void findMengeneinheitById() {
	    	
	    	final Long id = Long.valueOf("1");
	    	
	    	Mengeneinheit me;
			try {
				me = mdao.getMengeneinheitById(id);
			} catch (ConnectException | DAOException | SQLException e) {
				throw new NullPointerException();
			}

			assertThat(me.getId(), is(id));
	    }
	    
	    @Test
	    public void createMengeneinheit() throws ConnectException, DAOException, SQLException {
	    	
	    	Mengeneinheit me  = new Mengeneinheit();
	    	me.setName("Steine");
	    	me.setKurz("Sn");
	    	mdao.createNewMengeneinheit(me);
	    	
	    }
	    
	    /**
		 * Testmethode updateMengeneinheit
		 * Update des Namens einer Mengeneinheit in der Datenbank
	     * @throws SQLException 
	     * @throws DAOException 
	     * @throws ConnectException 
		 */
	    @Test
	    public void updateMengeneinheit() throws ConnectException, DAOException, SQLException {
	    	
	    	final Long id = Long.valueOf("11");	    	
	    	Mengeneinheit me = mdao.getMengeneinheitById(id);
	    	me.setName("Stones");
	    	mdao.updateMengeneinheit(me);
	    }
}

