/**
 * Created by Christian Barth
 * 17.04.2013 - 14:54:02
 */
package de.hska.awp.palaver2.lieferantenverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.data.LieferantDAO;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;

/**
 * Die Klasse ermöglicht die Verwaltung der Lieferanten und stellt für die GUI
 * Methoden bereit.
 * 
 * @author bach1014
 */
public class Lieferantenverwaltung extends LieferantDAO {

	private static Lieferantenverwaltung instance = null;

	private Lieferantenverwaltung() {
		super();
	}

	public static Lieferantenverwaltung getInstance() {
		if (instance == null) {
			instance = new Lieferantenverwaltung();
		}
		return instance;
	}

	/**
	 * Die Methode liefert alle Lieferanten zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	public List<Lieferant> getAllLieferanten() throws ConnectException,
			DAOException, SQLException {

		List<Lieferant> result = null;

		result = super.getAllLieferanten();

		return result;
	}

	/**
	 * Die Methode liefert einen oder mehrere Lieferanten anhand des Parameter
	 * name zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	public List<Lieferant> getLieferantenByName(String name)
			throws ConnectException, DAOException, SQLException {

		List<Lieferant> result = null;

		result = super.getLieferantenByName(name);

		return result;
	}

	/**
	 * Die Methode liefert einen Lieferanten anhand des Parameter id zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	public Lieferant getLieferantById(Long id) throws ConnectException,
			DAOException, SQLException {
		Lieferant lieferant = super.getLieferantById(id);
		return lieferant;
	}

	public Lieferant getLastLieferant() throws ConnectException, DAOException,
			SQLException {
		List<Lieferant> lieferanten = super.getAllLieferanten();
		return lieferanten.get(lieferanten.size() - 1);
	}
	

	/**
	 * Die Methode erzeugt einen Lieferant.
	 * @return 
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	public void createLieferant(Lieferant lieferant) throws ConnectException,
			DAOException, SQLException {

		super.createLieferant(lieferant);
	}

	/**
	 * Die Methode aktualisiert einen Lieferant.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	public void updateLieferant(Lieferant lieferant) throws ConnectException,
			DAOException, SQLException {

		super.updateLieferant(lieferant);

	}

}
