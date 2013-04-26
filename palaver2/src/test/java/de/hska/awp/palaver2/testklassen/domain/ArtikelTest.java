/**
 * Created by Sebastian Walz
 * 24.04.2013 17:00:01
 */
package de.hska.awp.palaver2.testklassen.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver2.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver2.data.ArtikelDAO;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.util.AbstractTest;

public class ArtikelTest extends AbstractTest
{
	private ArtikelDAO artikelDao = new ArtikelDAO();
	
	@Test
	public void getArtikel()
	{
		Boolean exception = false;
		List<Artikel> list = null;
		try
		{
			list = artikelDao.getAllArtikel();
		} 
		catch (ConnectException | DAOException | SQLException e)
		{
			exception = true;
		}
		assertThat(list.isEmpty(), is(false));
		assertThat(exception, is(false));
	}
	
	@Test
	public void getArtikelById() throws ConnectException, DAOException, SQLException
	{
		Long id = 1L;
		
		Artikel artikel = artikelDao.getArtikelById(id);
		
		assertThat(artikel.getId(), is(id));
	}
	
	@Test
	public void createArtikel() throws ConnectException, DAOException
	{
		Mengeneinheit mengeneinheit = new Mengeneinheit();
		mengeneinheit.setId(1L);
		Kategorie kategorie = new Kategorie(1L, "Test");
		Lieferant lieferant = new Lieferant();
		lieferant.setId(1L);
		
		Artikel artikel = new Artikel(mengeneinheit, kategorie, lieferant, "Test", "Test", 0.0, 0.0F, true, true, false, 0, true);
		
		artikelDao.createArtikel(artikel);
	}
}
