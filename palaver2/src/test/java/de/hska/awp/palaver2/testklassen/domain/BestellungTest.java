package de.hska.awp.palaver2.testklassen.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.text.ParseException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellposition;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellung;
import de.hska.awp.palaver2.data.ArtikelDAO;
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
	private ArtikelDAO adao = new ArtikelDAO();


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

		//Bestellung erzeugen
		Bestellung bestellung = new Bestellung();

		//Bestellung Lieferant setzen
		Long lid = Long.valueOf(1);
		Lieferant lieferant = null;
		lieferant = ldao.getLieferantById(lid);
		bestellung.setLieferant(lieferant);
		
		//Bestellung Datum setzen
		Date date2 = new Date(0);
		Date timestamp = new Date(date2.getTime());
		bestellung.setDatum(timestamp);
		bestellung.setLieferdatum("17.05.1999");
		//Bestellpositionliste zusammenbauen
		//Bestellposition Bestellung setzen
		Bestellposition bp = new Bestellposition();
		bp.setArtikel(adao.getArtikelById(Long.valueOf("1")));
// wird in der BestellungDAO schon gemacht.
//		bp.setBestellung(bestellung); 
		


		//Bestellung Bestellposition setzen
		bestellung.addBestellposition(bp);

		bdao.createBestellung(bestellung);

	}
	@Ignore
	@Test
	public void updateBestellung() throws ConnectException, DAOException,
			SQLException, ParseException {
		
		Bestellung bestellung = bdao.getBestellungById(ID);
		
		Bestellposition bp = new Bestellposition();
		bp.setArtikel(adao.getArtikelById(Long.valueOf("1")));
		bp.setBestellung(bestellung);
		
		List<Bestellposition> bestellpositionen = new ArrayList <Bestellposition>();
		bestellpositionen.add(bp);

		bestellung.setBestellpositionen(bestellpositionen);
		
		Date date = new Date(0);
		Date timestamp = new Date(date.getTime());

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
