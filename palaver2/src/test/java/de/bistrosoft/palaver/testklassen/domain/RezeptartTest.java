/**
 * 
 */
package de.bistrosoft.palaver.test.testklassen.domain;


import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.RezeptartDAO;

/**
 * @author Android
 *
 */
public class RezeptartTest extends AbstractTest{
/**	public static String NAME = "Hauptgericht";
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
		rezeptart.setName("TestRezeptart");
		dao.createRezeptart(rezeptart);

	}
*/
}
