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
import de.bistrosoft.palaver.data.GeschmackDAO;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import de.bistrosoft.palaver.util.AbstractTest;

/**
 * @author Android
 * 
 */
public class GeschmackTest extends AbstractTest {

	public static String NAME = "Exotisch";
	public static Long ID = Long.valueOf("1");

	private GeschmackDAO dao = new GeschmackDAO();

	@Test
	public void getGeschmack() {
		Boolean exception = false;
		List<Geschmack> geschmacklist = null;
		try {
			geschmacklist = dao.getAllGeschmack();
		} catch (ConnectException | DAOException | SQLException e) {
			exception = true;
		}
		assertThat(geschmacklist.isEmpty(), is(false));
		assertThat(exception, is(false));
	}

	@Test
	public void getGeschmackByName() throws ConnectException, DAOException,
			SQLException {

		Boolean exception = false;
		List<Geschmack> geschmacklist = null;
		try {
			geschmacklist = dao.getGeschmackByName(NAME);
		} catch (ConnectException | DAOException | SQLException e) {
			exception = true;
		}
		assertThat(geschmacklist.isEmpty(), is(false));
		assertThat(exception, is(false));
	}

	@Test
	public void getGeschmackById() throws ConnectException, DAOException,
			SQLException {

		Geschmack geschmack = dao.getGeschmackById(ID);

		assertThat(geschmack.getId(), is(ID));
	}

	@Test
	public void createGeschmack() throws ConnectException, DAOException,
			SQLException {

		Geschmack geschmack = new Geschmack();
		geschmack.setName("TestGeschmack");
		dao.createGeschmack(geschmack);

	}

}
