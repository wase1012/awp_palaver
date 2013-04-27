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
	private String NAME = "Kilogramm";
	private String KURZ = "Kg";
	final Long ID = Long.valueOf("3");
	
	
	@Test
    public void findMengeneinheitByName() throws ConnectException, DAOException, SQLException {
		
		Boolean exception = false;
		List<Mengeneinheit> list = null;
		
		try
		{
    	list = mdao.getMengeneinheitByName(NAME);
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
	    	Mengeneinheit me;
			try {
				me = mdao.getMengeneinheitById(ID);
			} catch (ConnectException | DAOException | SQLException e) {
				throw new NullPointerException();
			}

			assertThat(me.getId(), is(ID));
	    }
	    
	    @Test
	    public void createMengeneinheit() throws ConnectException, DAOException, SQLException {	    	
	    	Mengeneinheit me  = new Mengeneinheit();
	    	me.setName(NAME + "_new");
	    	me.setKurz(KURZ + "_new");
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
   	    	Mengeneinheit me = mdao.getMengeneinheitById(ID);
	    	me.setName(NAME + "_update");
	    	mdao.updateMengeneinheit(me);
	    }
}

