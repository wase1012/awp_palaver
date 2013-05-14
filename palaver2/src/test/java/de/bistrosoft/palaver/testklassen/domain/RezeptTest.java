package de.bistrosoft.palaver.testklassen.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.junit.Ignore;
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

	@Ignore
	@Test
	public void getRezeptByName() throws ConnectException, DAOException,
			SQLException {
		String rezeptname = "Lasagne";

		Rezept rezept = RezeptDAO.getInstance().getRezeptByName(rezeptname);

		assertThat(rezept.getName(), is(rezeptname));
	}

	@Test
	public void createRezept() throws ConnectException, DAOException,
			SQLException {

		// Rezept erzeugen
		Rezept rezept = new Rezept();

		Long raid = Long.valueOf(6);
		Long gid = Long.valueOf(5);
		Long mid = Long.valueOf(1);

		Rezeptart rezeptart = radao.getRezeptartById(raid);
		Geschmack geschmack = gdao.getGeschmackById(gid);
		Mitarbeiter mitarbeiter =  mdao.getMitarbeiterById(mid);
		String name = "Gummibaerchen";
		String kommentar = "lalalala";
		int portion = 30;

		rezept.setRezeptart(rezeptart);
		rezept.setGeschmack(geschmack);
		rezept.setMitarbeiter(mitarbeiter);
		rezept.setName(name);
		rezept.setKommentar(kommentar);
		rezept.setPortion(portion);
		rezept.setAufwand(false);
		rezept.setFavorit(true);

		java.util.Date date2 = new java.util.Date();
		Date date = new Date(date2.getTime());
		rezept.setErstellt(date);

		rdao.createRezept(rezept);
	}

}
