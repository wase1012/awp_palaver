/**
 * Created by Sebastian Walz
 * 24.04.2013 17:00:01
 */
package de.hska.awp.palaver2.testklassen.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.util.AbstractTest;

public class ArtikelTest extends AbstractTest
{
	@Test
	public void getArtikelById()
	{
		final Long id = 1L;
		
		Artikel artikel = em.find(Artikel.class, id);
		
		assertThat(artikel.getId(), is(id));
	}
}
