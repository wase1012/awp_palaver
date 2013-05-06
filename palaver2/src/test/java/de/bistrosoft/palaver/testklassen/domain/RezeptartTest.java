package de.bistrosoft.palaver.testklassen.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;
import de.bistrosoft.palaver.data.RezeptartDAO;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezeptart;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Zubereitung;
import de.bistrosoft.palaver.util.AbstractTest;

/**
 * @author Michael Marschall
 * 
 */
public class RezeptartTest extends AbstractTest {

	public static String NAME = "Hauptgericht";
	public static Long ID = Long.valueOf("1");
	private RezeptartDAO dao = new RezeptartDAO();

	/**
	 * Die Testmethode liefert alle Rezeptarten aus der Datenbank zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */

	@Test
	public void findAllRezeptart() {
		Boolean exception = false;
		List<Rezeptart> rlist = null;
		try {
			rlist = dao.getAllRezeptart();
		} catch (ConnectException | DAOException | SQLException e) {
			exception = true;
		}
		assertThat(rlist.isEmpty(), is(false));
		assertThat(exception, is(false));
	}

	/**
	 * Die Testmethode sucht nach einer Rezeptart anhand einer Id
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */

	@Test
	public void findRezeptartById() throws ConnectException, DAOException,
			SQLException {
		Rezeptart rezeptart = new Rezeptart();
		rezeptart = dao.getRezeptartById(ID);
		assertThat(rezeptart.getId(), is(ID));
	}

	/**
	 * Die Testmethode sucht nach einer Rezeptart anhand eines Names
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */

	@Test
	public void findRezeptartByName() throws ConnectException, DAOException,
			SQLException {

		Boolean exception = false;
		List<Rezeptart> list = null;

		try {
			list = dao.getRezeptartByName(NAME);
		} catch (ConnectException | DAOException | SQLException e) {
			exception = true;
		}
		assertThat(list.isEmpty(), is(false));
		assertThat(exception, is(false));
	}

	/**
	 * Die Testmethode erstellt eine Rezeptart
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	
	@Ignore
	@Test
	public void createRezeptart() throws ConnectException, DAOException,
			SQLException {
		Rezeptart rezeptart = new Rezeptart();
		rezeptart.setName(NAME);
		dao.createRezeptart(rezeptart);
	}

	/**
	 * Die Testmethode ändert eine Rezeptart
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */

	@Test
	public void updateRezeptart() throws ConnectException, DAOException,
			SQLException {
		Rezeptart art = dao.getRezeptartById(ID);
		art.setName("Hauptgericht");
		dao.updateRezeptart(art);
	}

	/**
	 * Diese Testmethoden löschen eine Rezeptart über ID oder Name
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */

	@Ignore
	@Test
	public void deleteRezeptartByName() throws ConnectException, DAOException,
			SQLException {
		String name = "TestRezeptar111t";

		List<Rezeptart> zb = new ArrayList<Rezeptart>();
		zb = dao.getRezeptartByName(name);

		dao.deleteRezeptartByName(name);
	}

	@Ignore
	@Test
	public void deleteRezeptartById() throws ConnectException, DAOException,
			SQLException {

		Long id = Long.valueOf(9);
		Rezeptart rezeptart = new Rezeptart();
		rezeptart = dao.getRezeptartById(id);

		dao.deleteRezeptartById(rezeptart.getId());

	}
}
