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
import de.hska.awp.palaver2.data.ArtikelDAO;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
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
}
