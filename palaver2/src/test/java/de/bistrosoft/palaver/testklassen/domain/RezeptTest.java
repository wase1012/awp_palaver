package de.bistrosoft.palaver.testklassen.domain;

import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import javax.persistence.TypedQuery;

import org.junit.Test;

import de.bistrosoft.palaver.lieferantenverwaltung.domain.Ansprechpartner;
import de.bistrosoft.palaver.lieferantenverwaltung.domain.Lieferant;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.util.AbstractTest;

/**
 * @author Michael Marschall
 * 
 */
public class RezeptTest extends AbstractTest {

	/**
	 * Testmethode findRezeptByName Suche nach ein Rezept ueber einen Namen
	 */

	@Test
	public void findRezeptByName() {
		final String name = "Lasagne";

		final TypedQuery<Rezept> query = em.createNamedQuery(
				Rezept.FIND_REZEPT_BY_NAME, Rezept.class);
		query.setParameter(Rezept.PARAM_NAME, name);
		final List<Rezept> rlist = query.getResultList();

		assertThat(rlist.isEmpty(), is(false));
	}

	/**
	 * Testmethode findRezeptById Suche nach einem Rezept ueber eine ID
	 */
	@Test
	public void findRezeptById() {

		final Long id = Long.valueOf("1");

		Rezept rzpt = em.find(Rezept.class, id);

		assertThat(rzpt.getId(), is(id));
	}

	// /**
	// * Testmethode createRezept
	// * Erzeugen eines Rezepts in der Datenbank
	// */
	// @Test
	// public void createRezept() {
	//
	// Rezept rzpt = new Rezept();
	// rzpt.setName("Pommes");
	//
	// em.persist(rzpt);
	// em.getTransaction().commit();
	// }

	/**
	 * Testmethode deleteRezept Loeschen eines Rezepts in der Datenbank
	 */
	@Test
	public void deleteRezept() {

		String name = "Pommes";

		final TypedQuery<Rezept> query = em.createNamedQuery(
				Rezept.FIND_REZEPT_BY_NAME, Rezept.class);
		query.setParameter(Rezept.PARAM_NAME, name);
		final List<Rezept> rlist = query.getResultList();

		for (int i = 0; i < rlist.size(); i++) {
			Rezept r = rlist.get(i);
			em.remove(r);

		}
		em.getTransaction().commit();
	}
}
