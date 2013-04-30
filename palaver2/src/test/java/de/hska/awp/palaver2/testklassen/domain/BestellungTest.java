package de.hska.awp.palaver2.testklassen.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellung;
import de.hska.awp.palaver2.data.BestellungDAO;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.data.LieferantDAO;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.util.AbstractTest;

public class BestellungTest extends AbstractTest {

	final Long ID = Long.valueOf("2");
	private BestellungDAO bdao = new BestellungDAO();
	private LieferantDAO ldao = new LieferantDAO();


	/**
	 * Die Testmethode erzeugt eine BEstellung in der Datenbank.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 * @throws ParseException
	 */
	@Test
	public void createBestellung() throws ConnectException, DAOException,
			SQLException, ParseException {

		Bestellung bestellung = new Bestellung();

		Long lid = Long.valueOf(1);
		Lieferant lieferant = null;
		lieferant = ldao.getLieferantById(lid);
		bestellung.setLieferant(lieferant);

		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());

		bestellung.setDatum(timestamp);

		bdao.createBestellung(bestellung);

	}
	
	@Test
	public void updateBestellung() throws ConnectException, DAOException,
			SQLException, ParseException {

		Bestellung bestellung = bdao.getBestellungById(ID);

		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());

		bestellung.setDatum(timestamp);

		bdao.updateBestellung(bestellung);

	}
	

	/**
	 * Die Testmethode liefert alle Bestellungen aus der Datenbank zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	@Test
	public void getBestellungen() {
		Boolean exception = false;
		List<Bestellung> bestellunglist = null;
		try {
			bestellunglist = bdao.getAllBestellungen();
		} catch (ConnectException | DAOException | SQLException e) {
			exception = true;
		}
		assertThat(bestellunglist.isEmpty(), is(false));
		assertThat(exception, is(false));
	}

	/**
	 * Die Testmethode sucht nach einer Bestellung anhand einer Id
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */

	@Test
	public void getBestellungById() throws ConnectException, DAOException,
			SQLException {

		Bestellung bestellung = bdao.getBestellungById(ID);

		assertThat(bestellung.getId(), is(ID));
	}

}
