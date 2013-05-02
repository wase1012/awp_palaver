package de.hska.awp.palaver2.testklassen.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.junit.Test;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellposition;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellung;
import de.hska.awp.palaver2.data.ArtikelDAO;
import de.hska.awp.palaver2.data.BestellpositionDAO;
import de.hska.awp.palaver2.data.BestellungDAO;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.util.AbstractTest;

public class BestellpositionTest extends AbstractTest {

	private BestellpositionDAO bpdao = new BestellpositionDAO();
	private BestellungDAO bdao = new BestellungDAO();
	private ArtikelDAO adao = new ArtikelDAO();

	final Long ID = Long.valueOf("2");
	final int MENGE = 5;
	final Float DURCHSCHNITT =  (float) 20;
	final Float KANTINE =  (float) 5;
	final Float GESAMT = (float) 5;

	@Test
	public void createBestellposition() throws ConnectException, DAOException,
			SQLException, ParseException {

		Bestellposition bestellposition = new Bestellposition();

		Long bid = Long.valueOf(1);
		Bestellung bestellung = null;
		bestellung = bdao.getBestellungById(bid);

		Long aid = Long.valueOf(1);
		Artikel artikel = null;
		artikel = adao.getArtikelById(aid);

		bestellposition.setBestellung(bestellung);
		bestellposition.setArtikel(artikel);
		bestellposition.setMenge(MENGE);
		bestellposition.setDurchschnitt(DURCHSCHNITT);
		bestellposition.setKantine(KANTINE);
		bestellposition.setGesamt(GESAMT);
		

		bpdao.createBestellposition(bestellposition);

	}

	@Test
	public void updateBestellposition() throws ConnectException, DAOException,
			SQLException, ParseException {

		Bestellposition bestellposition = bpdao.getBestellpositionById(ID);

		bestellposition.setMenge(MENGE);

		bpdao.updateBestellposition(bestellposition);

	}

	@Test
	public void getBestellpositionById() throws ConnectException, DAOException,
			SQLException {

		Bestellposition bestellposition = bpdao.getBestellpositionById(ID);

		assertThat(bestellposition.getId(), is(ID));
	}

	@Test
	public void getBestellpositionByBestellungId() throws ConnectException,
			DAOException, SQLException {

		Boolean exception = false;
		List<Bestellposition> bestellpositionlist = null;
		try {
			bestellpositionlist = bpdao.getBestellpositionenByBestellungId(ID);
		} catch (ConnectException | DAOException | SQLException e) {
			exception = true;
		}
		assertThat(bestellpositionlist.isEmpty(), is(false));
		assertThat(exception, is(false));
	}

}
