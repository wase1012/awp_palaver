package de.hska.awp.palaver2.testklassen.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import de.hska.awp.palaver2.data.AnsprechpartnerDAO;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.data.LieferantDAO;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Ansprechpartner;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.util.AbstractTest;

/**
 * Testklasse für den Ansprechpartner
 * 
 * @author Christian Barth
 */
public class AnsprechpartnerTest extends AbstractTest {

	public static String NAME = "Armin Maier";
	public static String TELEFON = "6666666";
	public static Long ID = Long.valueOf("1");
	public static Long LIEFERANT_ID = Long.valueOf("1");

	private AnsprechpartnerDAO dao = new AnsprechpartnerDAO();
	private LieferantDAO lieferantdao = new LieferantDAO();

	/**
	 * Testmethode getAnsprechpartner
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	@Test
	public void getAnsprechpartner() throws ConnectException, DAOException,
			SQLException {
		Boolean exception = false;
		List<Ansprechpartner> aplist = null;
		try {
			aplist = dao.getAllAnsprechpartner();
		} catch (ConnectException | DAOException | SQLException e) {
			exception = true;
		}
		assertThat(aplist.isEmpty(), is(false));
		assertThat(exception, is(false));
	}

	/**
	 * Testmethode getAnsprechpartnerByName
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	@Test
	public void getAnsprechpartnerByName() throws ConnectException,
			DAOException, SQLException {

		Boolean exception = false;
		List<Ansprechpartner> aplist = null;
		try {
			aplist = dao.getAnsprechpartnerByName(NAME);
		} catch (ConnectException | DAOException | SQLException e) {
			exception = true;
		}
		assertThat(aplist.isEmpty(), is(false));
		assertThat(exception, is(false));
	}

	/**
	 * Testmethode getAnsprechpartnerById
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	@Test
	public void getAnsprechpartnerById() throws ConnectException, DAOException,
			SQLException {

		Ansprechpartner ansprechpartner = dao.getAnsprechpartnerById(ID);

		assertThat(ansprechpartner.getId(), is(ID));
	}

	/**
	 * Testmethode createAnsprechpartner
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	@Test
	public void createAnsprechpartner() throws ConnectException, DAOException,
			SQLException {

		Ansprechpartner ansprechpartner = new Ansprechpartner();
		ansprechpartner.setName("TestAnsprechpartner");
		ansprechpartner.setTelefon(TELEFON);
		Lieferant lieferant = lieferantdao.getLieferantById(LIEFERANT_ID);
		ansprechpartner.setLieferant(lieferant);
		dao.createAnsprechpartner(ansprechpartner);

	}

	/**
	 * Testmethode updateAnsprechpartner
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	@Test
	public void updateAnsprechpartner() throws ConnectException, DAOException,
			SQLException {

		Ansprechpartner ansprechpartner = dao.getAnsprechpartnerById(ID);
		ansprechpartner.setTelefon(TELEFON);
		dao.updateAnsprechpartner(ansprechpartner);

	}

	/**
	 * Die Methode löscht einen Ansprechpartner aus der Datenbank. Der Test
	 * läuft jedoch nur einmal, da das Objekt beim zweiten mal nicht mehr
	 * vorhanden ist.
	 * 
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	@Test
	public void deleteAnsprechpartner() throws ConnectException, DAOException,
			SQLException {

		Long id = Long.valueOf(3);
		Ansprechpartner ansprechpartner = new Ansprechpartner();
		ansprechpartner = dao.getAnsprechpartnerById(id);

		dao.deleteAnsprechpartner(ansprechpartner.getId());

	}

}
