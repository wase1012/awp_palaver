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
import de.bistrosoft.palaver.data.GeschmackDAO;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import de.bistrosoft.palaver.util.AbstractTest;

/**
 * @author Michael Marschall
 * 
 */
public class GeschmackTest extends AbstractTest {

	final String NAME = "exotisch";
	final String NAME2 = "scharf";
	final Long ID = Long.valueOf("1");
	final Long ID2 = Long.valueOf("6");
	private GeschmackDAO dao = new GeschmackDAO();

	/**
	 * Die Testmethode liefert alle Geschmäcker aus der Datenbank zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */

	@Test
	public void findAllGeschmack() {
		Boolean exception = false;
		List<Geschmack> list = null;
		try {
			list = dao.getAllGeschmack();
		} catch (ConnectException | DAOException | SQLException e) {
			exception = true;
		}
		assertThat(list.isEmpty(), is(false));
		assertThat(exception, is(false));
	}

	/**
	 * Die Testmethode sucht nach einem Geschmack anhand einer Id
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */

	@Test
	public void findGeschmackById() throws ConnectException, DAOException,
			SQLException {
		Geschmack geschmack = new Geschmack();
		geschmack = dao.getGeschmackById(ID);
		assertThat(geschmack.getId(), is(ID));
	}

	/**
	 * Die Testmethode sucht nach einem Geschmack anhand eines Names
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */

	@Test
	public void findGeschmackByName() throws ConnectException, DAOException,
			SQLException {

		Boolean exception = false;
		List<Geschmack> list = null;

		try {
			list = dao.getGeschmackByName(NAME);
		} catch (ConnectException | DAOException | SQLException e) {
			exception = true;
		}
		assertThat(list.isEmpty(), is(false));
		assertThat(exception, is(false));
	}

	/**
	 * Die Testmethode erstellt einen Geschmack
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */

	@Test
	public void createGeschmack() throws ConnectException, DAOException,
			SQLException {
		Geschmack geschmack = new Geschmack();
		geschmack.setName(NAME2);
		dao.createGeschmack(geschmack);
	}

	/**
	 * Die Testmethode ändert einen Geschmack
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	
	@Ignore
	@Test
	public void updateGeschmack() throws ConnectException, DAOException,
			SQLException {
		Geschmack g = dao.getGeschmackById(ID2);
		g.setName("Kuehlschrank");
		dao.updateGeschmack(g);
	}

	/**
	 * Diese Testmethoden löschen einen Geschmack über ID oder Name
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */

	@Test
	public void deleteGeschmackByName() throws ConnectException,
			DAOException, SQLException {
		String name = "TestGeschmack";

		List<Geschmack> zb = new ArrayList<Geschmack>();
		zb = dao.getGeschmackByName(name);

		dao.deleteGeschmackByName(name);
	}
	
	@Ignore
	@Test
	public void deleteGeschmackById() throws ConnectException, DAOException,
			SQLException {

		Long id = Long.valueOf(6);
		Geschmack geschmack = new Geschmack();
		geschmack = dao.getGeschmackById(id);

		dao.deleteGeschmackById(geschmack.getId());

	}
}
