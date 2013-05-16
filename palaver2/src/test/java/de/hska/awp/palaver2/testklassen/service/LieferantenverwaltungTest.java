/**
 * Created by bach1014
 * 24.04.2013 - 12:05:27
 */
package de.hska.awp.palaver2.testklassen.service;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;


import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.lieferantenverwaltung.service.Lieferantenverwaltung;

/**
 * Testklasse für die Lieferantenverwaltung
 * @author Elena W
 *
 */
public class LieferantenverwaltungTest {

	Lieferantenverwaltung lv = Lieferantenverwaltung.getInstance();
	
	@Test
    public void getLieferantenByName() {
    	
    	final String name = "Edeka";
    	Boolean exception = false;
    	List<Lieferant> liefantlist = null;
	
    		try{
    			
    		 liefantlist = lv.getLieferantenByName(name);
    		
    		}
    		catch (ConnectException | DAOException | SQLException e)
    		{
    			exception = true;
    		}
    		for (Lieferant k : liefantlist) {
    			assertThat(k.getName(), is(name));
    		}
    	}
	
	@Test
	public void getLieferantById(){
		
	
		Lieferant lf = null;
		Boolean exception = false;
		final Long id = Long.valueOf(1);
		
		try{
		 lf = lv.getLieferantById(id);
		}
		catch (ConnectException | DAOException | SQLException e)
		{
			exception = true;
		}
		assertThat(lf.getId(),is(id));
	}

	 /**
		 * Testmethode createLieferant
		 * Erzeugt einen Lieferant in der Datenbank
		 * @throws SQLException 
		 * @throws DAOException 
		 * @throws ConnectException 
		 */
	@Test
	public void createLieferant(){
		
		Lieferant lf = new Lieferant();
		Boolean exception = false;
		
		final String name = "Testlv";
		final String tel = "123456789";
		final Boolean termine = true;
		lf.setName(name);
		lf.setTelefon(tel);
		lf.setMehrereliefertermine(termine);
		try{
	    lv.createLieferant(lf);
		}
		catch (ConnectException | DAOException | SQLException e)
		{
			exception = true;
		}
		
		assertThat(lf.getName(),is(name));
	}
	 /**
	 * Testmethode updateLieferant
	 * Update des Namens eines Lieferantes in der Datenbank
     * @throws SQLException 
     * @throws DAOException 
     * @throws ConnectException 
	 */
	
	@Test
	public void updateLieferant (){
		
		Lieferant lf = new Lieferant();
		
		Boolean exception = false;
		final Long id = Long.valueOf(1);
		final String neuerName = "Fleischlieferant";
		final Boolean termine = true;
		
		try{
			
		lv.getLieferantById(id);
		
		}
		catch (ConnectException | DAOException | SQLException e)
		{
			exception = true;
		}
		lf.setName(neuerName);
		lf.setMehrereliefertermine(termine);
		
		try{
		lv.updateLieferant(lf);
		}
		catch (ConnectException | DAOException | SQLException e)
		{
			exception = true;
		}
		assertThat(lf.getName(), is(neuerName));
	}
	
	
	/**
	 * Die Testmethode liefert alle Lieferanten zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	@Test 
	public void getAllLieferanten() {
	    	
		   	Boolean exception = false;
		   	List<Lieferant> llist = null;
		
	    		try{
	    			
	    			llist = lv.getAllLieferanten();
	    		
	    		}
	    		catch (ConnectException | DAOException | SQLException e)
	    		{
	    			exception = true;
	    		}
	    		assertThat(llist.isEmpty(), is(false));
	    	}
	    		
    }
	
