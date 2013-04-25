package de.hska.awp.palaver2.testklassen.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import java.util.List;
import javax.persistence.TypedQuery;

import org.junit.Ignore;
import org.junit.Test;
import de.hska.awp.palaver2.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver2.util.AbstractTest;

/**
 * @author mihail boehm Testklasse fuer die Kategorie
 */
public class KategorieTest extends AbstractTest {

	final String NAME = "Milch";
	final Long ID = Long.valueOf("1");
	Kategorie kategorie = new Kategorie();

	/**
	 * Testmethode findKategorieByName Suche nach einer Kategorie ueber einen
	 * Namen
	 */
	@Ignore
	@Test
	public void findKategorieByName() {
		
		kategorie = em.find(Kategorie.class, NAME);
		assertThat(kategorie.getName(), is(NAME));
	}
	
	/**
	 * Testmethode findKategorieById. Suche nach einer Kategorie ueber eine Id
	 */
	@Test
	public void findKategorieById() {
		kategorie = em.find(Kategorie.class, ID);
		assertThat(kategorie.getId(), is(ID));
	}

		/**
	 * Testmethode updateKategorie Update des Namens einer Kategorie in der
	 * Datenbank
	 */
	@Test
	public void updateKategorie() {
		kategorie = em.find(Kategorie.class, ID);
		String name = kategorie.getName();
		kategorie.setName(name = name + "Update");
		em.merge(kategorie);
		em.getTransaction().commit();
	}
	
	 /**
	 * Testmethode createKategorie
	 * Erzeugen einer Kategorie in der Datenbank
	 */
    @Test
    public void createKategorie() {
    	kategorie.setName(NAME + "_NEW");   	
    	em.persist(kategorie);
    	em.getTransaction().commit();
    }
}
