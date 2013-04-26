package de.hska.awp.palaver2.testklassen.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Test;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.data.MitarbeiterDAO;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.util.AbstractTest;


public class MitarbeiterTest extends AbstractTest{

	private MitarbeiterDAO mdao = new MitarbeiterDAO();
	
    @Test
    public void findMitarbeiterById() {
    	
    	Long id = Long.valueOf(2);

		Mitarbeiter mitarbeiter = null;;
		try {
			mitarbeiter = mdao.getMitarbeiterById(id);
		} catch (ConnectException | DAOException | SQLException e) {
			throw new NullPointerException("error");
		} 

		assertThat(mitarbeiter.getId(), is(id));
    }
}
