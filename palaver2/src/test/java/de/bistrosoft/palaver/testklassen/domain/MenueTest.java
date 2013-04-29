//package de.bistrosoft.palaver.testklassen.domain;
//
//import static org.hamcrest.core.Is.is;
//import static org.junit.Assert.assertThat;
//
//import java.sql.SQLException;
//import java.util.List;
//
//import org.junit.Test;
//
//import de.bistrosoft.palaver.dao.ConnectException;
//import de.bistrosoft.palaver.dao.DAOException;
//import de.bistrosoft.palaver.dao.MenueDAO;
//import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
//
////import test.de.bistrosoft.palaver.util.AbstractTest;
//
//public class MenueTest extends AbstractTest {
//
//	public static String NAME = "Edeka";
//	public static Long ID = Long.valueOf("1");
//	public static String KUNDENNUMMER = "666666";
//
//	private MenueDAO dao = new MenueDAO();
//
//	/**
//	 * Die Testmethode liefert alle Lieferanten aus der Datenbank zurück.
//	 * 
//	 * @throws SQLException
//	 * @throws DAOException
//	 * @throws ConnectException
//	 */
//	@Test
//	public void getMenues() {
////		Boolean exception = false;
////		List<Menue> liefantlist = null;
////		try {
////			liefantlist = dao.getAllMenues();
////		} catch (ConnectException | DAOException | SQLException e) {
////			exception = true;
////		}
////		assertThat(liefantlist.isEmpty(), is(false));
////		assertThat(exception, is(false));
//	}
//
////	/**
////	 * Die Testmethode sucht nach einen Lieferanten anhand eines Namen.
////	 * 
////	 * @throws SQLException
////	 * @throws DAOException
////	 * @throws ConnectException
////	 */
////	@Test
////	public void getLieferantByName() throws ConnectException, DAOException,
////			SQLException {
////
////		Boolean exception = false;
////		List<Menue> liefantlist = null;
////		try {
////			liefantlist = dao.getLieferantenByName(NAME);
////		} catch (ConnectException | DAOException | SQLException e) {
////			exception = true;
////		}
////		assertThat(liefantlist.isEmpty(), is(false));
////		assertThat(exception, is(false));
////	}
////
////	/**
////	 * Die Testmethode sucht nach einen Lieferant anhand einer Id
////	 * 
////	 * @throws SQLException
////	 * @throws DAOException
////	 * @throws ConnectException
////	 */
////	@Test
////	public void getLieferantById() throws ConnectException, DAOException,
////			SQLException {
////
////		Lieferant lieferant = dao.getLieferantById(ID);
////
////		assertThat(lieferant.getId(), is(ID));
////	}
////
////	/**
////	 * Die Testmethode erzeugt ein Lieferant in der Datenbank.
////	 * 
////	 * @throws SQLException
////	 * @throws DAOException
////	 * @throws ConnectException
////	 */
////	@Test
////	public void createLieferant() throws ConnectException, DAOException,
////			SQLException {
////
////		Lieferant lieferant = new Lieferant();
////		lieferant.setName("Testlieferant");
////		lieferant.setTelefon("0175/55667788");
////		dao.createLieferant(lieferant);
////
////	}
////
////	/**
////	 * Die Testmethode aktualisiert ein Lieferant in der Datenbank.
////	 * 
////	 * @throws SQLException
////	 * @throws DAOException
////	 * @throws ConnectException
////	 */
////	@Test
////	public void updateLieferant() throws ConnectException, DAOException,
////			SQLException {
////
////		Lieferant lieferant = dao.getLieferantById(ID);
////		lieferant.setKundennummer(KUNDENNUMMER);
////		dao.updateLieferant(lieferant);
////
////	}
//
//}
