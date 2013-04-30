package de.bistrosoft.palaver.testklassen.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;
import de.bistrosoft.palaver.data.GeschmackDAO;
import de.bistrosoft.palaver.data.MitarbeiterDAO;
import de.bistrosoft.palaver.data.RezeptDAO;
import de.bistrosoft.palaver.data.RezeptartDAO;
import de.bistrosoft.palaver.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezeptart;
import de.bistrosoft.palaver.util.AbstractTest;

/**
 * @author Michael Marschall
 * 
 */
public class RezeptTest extends AbstractTest {

	private RezeptartDAO radao = new RezeptartDAO();
	private RezeptDAO rdao = new RezeptDAO();
	private GeschmackDAO gdao = new GeschmackDAO();
	private MitarbeiterDAO mdao = new MitarbeiterDAO();

	@Test
	public void getAllRezepte() {
		Boolean exception = false;
		List<Rezept> list = null;
		try {
			list = rdao.getAllRezepte();
		} catch (ConnectException | DAOException | SQLException e) {
			exception = true;
		}
		assertThat(list.isEmpty(), is(false));
		assertThat(exception, is(false));
	}

	// @Test
	// public void getRezeptById() throws ConnectException, DAOException,
	// SQLException
	// {
	// Long id = 1;
	//
	// Rezept rezept = rdao.getRezeptById(id);
	//
	// assertThat(rezept.getId(), is(id));
	// }
	//
//	@Test
//	public void getRezeptByName() {
//		Boolean exception = false;
//		List<Rezept> list = null;
//		try {
//			String name = "test";
//			list = rdao.getRezeptByName(name);
//		} catch (ConnectException | DAOException | SQLException e) {
//			exception = true;
//		}
//		assertThat(list.isEmpty(), is(false));
//		assertThat(exception, is(false));
//	}
	
//	@Test
//	public void getRezeptByName() throws ConnectException, DAOException, SQLException
//	{
//		String name = "test";
//
//		Rezept rezept = rdao.getRezeptByName(name);
//
//		assertThat(rezept.getName(), is(name));
//	}

	@Test
	public void createRezept() throws ConnectException, DAOException,
			SQLException {

		Rezept rezept = new Rezept();

		Long raid = Long.valueOf(1);
		Long gid = Long.valueOf(1);
		Long mid = Long.valueOf(1);
		Long rid = Long.valueOf(1);

		Rezeptart rezeptart = null;
		Geschmack geschmack = null;
		Mitarbeiter mitarbeiter = null;
		String name = "test";
		String kommentar = null;
		int portion = 30;

		rezeptart = radao.getRezeptartById(raid);
		geschmack = gdao.getGeschmackById(gid);
		mitarbeiter = mdao.getMitarbeiterById(mid);

		rezept.setRezeptart(rezeptart);
		rezept.setGeschmack(geschmack);
		rezept.setMitarbeiter(mitarbeiter);
		rezept.setName(name);
		rezept.setKommentar(kommentar);
		rezept.setPortion(portion);

		rdao.createRezept(rezept);
	}

}
