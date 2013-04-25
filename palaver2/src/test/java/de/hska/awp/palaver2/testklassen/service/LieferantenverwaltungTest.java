/**
 * Created by bach1014
 * 24.04.2013 - 12:05:27
 */
package de.hska.awp.palaver2.testklassen.service;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

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
    public void findLieferantByName() {
    	
    	final String name = "Edeka";
	
    		// When
    		final List<Lieferant> liefantlist = lv.findLieferantByName(name);
    		
    		// Then
    		for (Lieferant k : liefantlist) {
    			assertThat(k.getName(), is(name));
    		}
    	}

    }
	
