/**
 * Created by Elena W
 */

package de.hska.awp.palaver2.bestellverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.data.BestellpositionDAO;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellposition;

/**
 * Die Klasse ermöglicht die Verwaltung der BEstellpositionen und stellt für die GUI
 * Methoden bereit.
 * 
 * @author Elena W
 */

public class Bestellpositionverwaltung extends BestellpositionDAO {
	
	private static Bestellpositionverwaltung instance = null;

	private Bestellpositionverwaltung() {
		super();
	}

	public static Bestellpositionverwaltung getInstance() {
		if (instance == null) {
			instance = new Bestellpositionverwaltung();
		}
		return instance;
	}
	/**
	 * Die Methode erzeugt eine Bestellposition.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	public void createBestellposition(Bestellposition bestellposition) throws ConnectException,
			DAOException, SQLException {

		super.createBestellposition(bestellposition);
	}
	
	/**
	 * Die Methode aktualisiert eine Bestellposition.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	public void updateBestellposition(Bestellposition bestellposition) throws ConnectException,
			DAOException, SQLException {

		super.updateBestellposition(bestellposition);

	}
	/**
	 * Die Methode liefert eine Bestellposition anhand des Parameter id zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	public Bestellposition getBestellpositionById(Long id) throws ConnectException,
			DAOException, SQLException {
		Bestellposition bestellposition = super.getBestellpositionById(id);
		return bestellposition;
	}

}
