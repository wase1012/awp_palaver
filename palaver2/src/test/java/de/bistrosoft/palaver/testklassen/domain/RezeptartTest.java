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
import de.bistrosoft.palaver.data.RezeptartDAO;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezeptart;
import de.bistrosoft.palaver.util.AbstractTest;

/**
 * @author Android
 * 
 */
public class RezeptartTest extends AbstractTest {
	public static String NAME = "Hauptgericht";
	public static Long ID = Long.valueOf("1");

	private RezeptartDAO dao = new RezeptartDAO();

	@Test
	public void getRezeptart() {
		Boolean exception = false;
		List<Rezeptart> rezeptartlist = null;
		try {
			rezeptartlist = dao.getAllRezeptart();
		} catch (ConnectException | DAOException | SQLException e) {
			exception = true;
		}
		assertThat(rezeptartlist.isEmpty(), is(false));
		assertThat(exception, is(false));
	}

	@Test
	public void getRezeptartByName() throws ConnectException, DAOException,
			SQLException {

		Boolean exception = false;
		List<Rezeptart> rezeptartlist = null;
		try {
			rezeptartlist = dao.getRezeptartByName(NAME);
		} catch (ConnectException | DAOException | SQLException e) {
			exception = true;
		}
		assertThat(rezeptartlist.isEmpty(), is(false));
		assertThat(exception, is(false));
	}

	@Test
	public void getRezeptartById() throws ConnectException, DAOException,
			SQLException {

		Rezeptart rezeptart = dao.getRezeptartById(ID);

		assertThat(rezeptart.getId(), is(ID));
	}

	 @Test
	 public void createRezeptart() throws ConnectException, DAOException,
	 SQLException {
	
	 Rezeptart rezeptart = new Rezeptart();
	 rezeptart.setName("TestRezeptar111t");
	 dao.createRezeptart(rezeptart);
	
	 }


}
