/**
 * 
 */
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
import de.bistrosoft.palaver.data.ZubereitungDAO;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Zubereitung;
import de.bistrosoft.palaver.util.AbstractTest;

/**
 * @author Michael Marschall
 * 
 */
public class ZubereitungTest extends AbstractTest {

	final String NAME = "Kühlschrank";
	final Long ID = Long.valueOf("7");
	private ZubereitungDAO dao = new ZubereitungDAO();

	/**
	 * Die Testmethode liefert alle Zubereitungen aus der Datenbank zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */

	@Test
	public void findAllZubereitungen() {
		Boolean exception = false;
		List<Zubereitung> zblist = null;
		try {
			zblist = dao.getAllZubereitung();
		} catch (ConnectException | DAOException | SQLException e) {
			exception = true;
		}
		assertThat(zblist.isEmpty(), is(false));
		assertThat(exception, is(false));
	}

	/**
	 * Die Testmethode sucht nach einer Zubereitung anhand einer Id
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */

	@Test
	public void findZubereitungById() throws ConnectException, DAOException,
			SQLException {
		Zubereitung zubereitung = new Zubereitung();
		zubereitung = dao.getZubereitungById(ID);
		assertThat(zubereitung.getId(), is(ID));
	}

	/**
	 * Die Testmethode sucht nach einer Zubereitung anhand eines Names
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */

	@Test
	public void findZubereitungByName() throws ConnectException, DAOException,
			SQLException {

		Boolean exception = false;
		List<Zubereitung> list = null;

		try {
			list = dao.getZubereitungByName(NAME);
		} catch (ConnectException | DAOException | SQLException e) {
			exception = true;
		}
		assertThat(list.isEmpty(), is(false));
		assertThat(exception, is(false));
	}

	/**
	 * Die Testmethode erstellt eine Zubereitung
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */

	@Ignore
	@Test
	public void createZubereitung() throws ConnectException, DAOException,
			SQLException {
		Zubereitung zubereitung = new Zubereitung();
		zubereitung.setName(NAME);
		dao.createZubereitung(zubereitung);
	}

	/**
	 * Die Testmethode ändert eine Zubereitung
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */

	@Test
	public void updateZubereitung() throws ConnectException, DAOException,
			SQLException {
		Zubereitung zb = dao.getZubereitungById(ID);
		zb.setName("Kuehlschrank");
		dao.updateZubereitung(zb);
	}

	/**
	 * Diese Testmethoden löschen eine Zubereitung über ID oder Name
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */

	@Ignore
	@Test
	public void deleteZubereitungByName() throws ConnectException,
			DAOException, SQLException {
		String name = "Kuehlschrank";

		List<Zubereitung> zb = new ArrayList<Zubereitung>();
		zb = dao.getZubereitungByName(name);

		dao.deleteZubereitungByName(name);
	}
	
	@Test
	public void deleteZubereitungById() throws ConnectException, DAOException,
			SQLException {

		Long id = Long.valueOf(7);
		Zubereitung zubereitung = new Zubereitung();
		zubereitung = dao.getZubereitungById(id);

		dao.deleteZubereitungById(zubereitung.getId());

	}
}
