/**
 * 
 */
package de.bistrosoft.palaver.testklassen.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;
import de.bistrosoft.palaver.data.ZubereitungDAO;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Zubereitung;
import de.bistrosoft.palaver.util.AbstractTest;

/**
 * @author Michael Marschall
 * 
 */
public class ZubereitungTest extends AbstractTest {

	final String NAME = "Kühlschrank";
	final Long ID = Long.valueOf("2");
	private ZubereitungDAO dao = new ZubereitungDAO();

	/**
	 * Die Testmethode liefert alle Zubereitungen aus der Datenbank zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */

	@Test
	public void getZubereitungen() {
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
	public void getZubereitungById() throws ConnectException, DAOException,
			SQLException {
		Zubereitung zubereitung = new Zubereitung();
		zubereitung = dao.getZubereitungById(ID);
		assertThat(zubereitung.getId(), is(ID));
	}

	/**
	 * Die Testmethode erstellt eine Zubereitung
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	
	@Test
	public void createZubereitung() throws ConnectException, DAOException,
			SQLException {
		Zubereitung zubereitung = new Zubereitung();
		zubereitung.setName(NAME);
		dao.createZubereitung(zubereitung);
	}

}
