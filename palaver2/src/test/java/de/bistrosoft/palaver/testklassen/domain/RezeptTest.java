package de.bistrosoft.palaver.testklassen.domain;

import java.sql.SQLException;

import org.junit.Test;

import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;
import de.bistrosoft.palaver.data.GeschmackDAO;
import de.bistrosoft.palaver.data.MitarbeiterDAO;
import de.bistrosoft.palaver.data.RezeptDAO;
import de.bistrosoft.palaver.data.RezeptartDAO;
import de.bistrosoft.palaver.lieferantenverwaltung.domain.Lieferant;
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
	public void createRezept() throws ConnectException, DAOException,
			SQLException {

		Rezept rezept = new Rezept();
//		this.rezeptart = rezeptart;
//		this.geschmack = geschmack;
//		this.mitarbeiter = mitarbeiter;
//		this.name = name;
//		this.kommentar = kommentar;
//		this.portion = portion;
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
