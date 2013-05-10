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
import de.bistrosoft.palaver.data.FussnoteDAO;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;
import de.bistrosoft.palaver.util.AbstractTest;

/**
 * @author Michael Marschall
 * 
 */
public class FussnoteTest extends AbstractTest {

	final String NAME = "vegan";
	final String NAME2 = "test";
	final String ABKUERZUNG = "t";
	final Long ID = Long.valueOf("2");
	final Long ID2 = Long.valueOf("8");
	private FussnoteDAO dao = new FussnoteDAO();

	/**
	 * Die Testmethode liefert alle Fussnoten aus der Datenbank zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */

	@Test
	public void findAllFussnote() {
		Boolean exception = false;
		List<Fussnote> zblist = null;
		try {
			zblist = dao.getAllFussnote();
		} catch (ConnectException | DAOException | SQLException e) {
			exception = true;
		}
		assertThat(zblist.isEmpty(), is(false));
		assertThat(exception, is(false));
	}

	/**
	 * Die Testmethode sucht nach einer Fussnote anhand einer Id
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */

	@Test
	public void findFussnoteById() throws ConnectException, DAOException,
			SQLException {
		Fussnote fussnote = new Fussnote();
		fussnote = dao.getFussnoteById(ID);
		assertThat(fussnote.getId(), is(ID));
	}

	/**
	 * Die Testmethode sucht nach einer Fussnote anhand eines Names
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */

	@Test
	public void findFussnoteByName() throws ConnectException, DAOException,
			SQLException {

		Boolean exception = false;
		Fussnote list = null;

		try {
			list = dao.getFussnoteByName(NAME);
		} catch (ConnectException | DAOException | SQLException e) {
			exception = true;
		}
		assertThat(list.getName(), is(NAME));
		assertThat(exception, is(false));
	}

	/**
	 * Die Testmethode erstellt eine Fussnote
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */

	@Test
	public void createFussnote() throws ConnectException, DAOException,
			SQLException {
		Fussnote fussnote = new Fussnote();
		fussnote.setName(NAME2);
		fussnote.setAbkuerzung(ABKUERZUNG);
		dao.createFussnote(fussnote);
	}

	/**
	 * Die Testmethode ändert eine Fussnote
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */

	@Test
	public void updateFussnote() throws ConnectException, DAOException,
			SQLException {
		Fussnote f = dao.getFussnoteById(ID);
		f.setName("Testt");
		f.setAbkuerzung("tt");
		dao.updateFussnote(f);
	}

	/**
	 * Diese Testmethoden löschen eine Fussnote über ID oder Name
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */

	@Test
	public void deleteFussnoteByName() throws ConnectException, DAOException,
			SQLException {
		String name = "Weizen_NEW";

		Fussnote zb = new Fussnote();
		zb = dao.getFussnoteByName(name);

		dao.deleteFussnoteByName(name);
	}

	@Ignore
	@Test
	public void deleteFussnoteById() throws ConnectException, DAOException,
			SQLException {

		Long id = Long.valueOf(7);
		Fussnote fussnote = new Fussnote();
		fussnote = dao.getFussnoteById(id);

		dao.deleteFussnoteById(fussnote.getId());

	}

}
