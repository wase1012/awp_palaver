package de.hska.awp.palaver2.testklassen.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import java.util.List;
import javax.persistence.TypedQuery;
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
	@Test
	public void findKategorieByName() {
		final TypedQuery<Kategorie> query = em.createNamedQuery(
				Kategorie.FIND_KATEGORIE_BY_NAME, Kategorie.class);
		query.setParameter(Kategorie.PARAM_NAME, NAME);
		final List<Kategorie> list = query.getResultList();
		assertThat(list.isEmpty(), is(false));
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
	public void updateMengeneinheit() {
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
