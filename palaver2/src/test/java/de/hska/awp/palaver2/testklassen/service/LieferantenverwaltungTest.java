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
 * @author bach1014
 *
 */
public class LieferantenverwaltungTest {

	Lieferantenverwaltung lv;
	
	@Ignore
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
    		// Then
    		for (Lieferant k : liefantlist) {
    			assertThat(k.getName(), is(name));
    		}
    	}

    }
	
