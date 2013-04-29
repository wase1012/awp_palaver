/**
 * 
 */
package de.bistrosoft.palaver.testklassen.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

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

	final String NAME = "Weizen";
	final String ABKUERZUNG = "Abk";
	final Long ID = Long.valueOf("2");
	private FussnoteDAO dao = new FussnoteDAO();

	/**
	 * Die Testmethode liefert alle Fussnoten aus der Datenbank zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */

	@Test
	public void getFussnoten() {
		Boolean exception = false;
		List<Fussnote> fussnotelist = null;
		try {
			fussnotelist = dao.getAllFussnote();
		} catch (ConnectException | DAOException | SQLException e) {
			exception = true;
		}
		assertThat(fussnotelist.isEmpty(), is(false));
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
	public void getFussnoteById() throws ConnectException, DAOException,
			SQLException {
		Fussnote fussnote = new Fussnote();
		fussnote = dao.getFussnoteById(ID);
		assertThat(fussnote.getId(), is(ID));
	}

	@Test
	public void createFussnote() throws ConnectException, DAOException,
			SQLException {
		Fussnote fussnote = new Fussnote();
		fussnote.setName(NAME + "_NEW");
		fussnote.setAbkuerzung(ABKUERZUNG + "_NEW");
		dao.createNewFussnote(fussnote);
	}

}
