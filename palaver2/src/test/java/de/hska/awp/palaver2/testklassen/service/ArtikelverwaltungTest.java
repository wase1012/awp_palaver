/**
 * Created by Sebastian Walz
 * 24.04.2013 16:59:46
 */
package de.hska.awp.palaver2.testklassen.service;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;


public class ArtikelverwaltungTest {
	
Artikelverwaltung av = Artikelverwaltung.getInstance();		 
private static final Long ARTIKEL_ID_VORHANDEN = Long.valueOf(1);	


	@Test
	public void getArtikelById(){
		
	
		Artikel art = null;
		Boolean exception = false;
		final Long id = ARTIKEL_ID_VORHANDEN;
		
		try{
		 art = av.getArtikelById(id);
		}
		catch (ConnectException | DAOException | SQLException e)
		{
			exception = true;
		}
		assertThat(art.getId(),is(id));
	}
}

