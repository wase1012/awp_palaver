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


import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.lieferantenverwaltung.service.Lieferantenverwaltung;

/**
 * @author bach1014
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

	@Test
	public void createLieferant(){
		
		Lieferant lf = new Lieferant();
		Boolean exception = false;
		
		final String name = "Testlv";
		final String tel = "123456789";
		lf.setName(name);
		lf.setTelefon(tel);
		
		try{
	    lv.createLieferant(lf);
		}
		catch (ConnectException | DAOException | SQLException e)
		{
			exception = true;
		}
		
		assertThat(lf.getName(),is(name));
	}
	
	@Test
	public void updateLieferant (){
		
		Lieferant lf = new Lieferant();
		
		Boolean exception = false;
		final Long id = Long.valueOf(1);
		final String neuerName = "Fleischlieferant";
		
		try{
			
		lv.getLieferantById(id);
		
		}
		catch (ConnectException | DAOException | SQLException e)
		{
			exception = true;
		}
		lf.setName(neuerName);
		
		try{
		lv.updateLieferant(lf);
		}
		catch (ConnectException | DAOException | SQLException e)
		{
			exception = true;
		}
		assertThat(lf.getName(), is(neuerName));
	}
	
    }
	
