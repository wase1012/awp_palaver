/**
 * Created by bach1014
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
 * @author bach1014
 * 
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

	public List<Lieferant> findAllLieferanten() throws ConnectException,
			DAOException, SQLException {

		List<Lieferant> result = null;

		result = super.getAllLieferanten();

		return result;
	}

	public List<Lieferant> findLieferantenByName(String name)
			throws ConnectException, DAOException, SQLException {

		List<Lieferant> result = null;

		result = super.getLieferantenByName(name);

		return result;
	}

	public Lieferant findLieferantById(Long id) throws ConnectException,
			DAOException, SQLException {
		Lieferant lieferant = super.getLieferantById(id);
		return lieferant;
	}

	public void createLieferant(Lieferant lieferant) throws ConnectException,
			DAOException, SQLException {

		super.createLieferant(lieferant);
	}

	public void updateLieferant(Lieferant lieferant) throws ConnectException,
			DAOException, SQLException {

		super.updateLieferant(lieferant);

	}

}
