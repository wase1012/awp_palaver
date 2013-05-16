/**
 * 
 */
package de.bistrosoft.palaver.mitarbeiterverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.bistrosoft.palaver.data.MitarbeiterDAO;
import de.bistrosoft.palaver.mitarbeiterverwaltung.domain.Mitarbeiter;


/**
 * @author Michael Marschall
 * 
 */
public class Mitarbeiterverwaltung extends MitarbeiterDAO {

	private static Mitarbeiterverwaltung instance = null;

	private Mitarbeiterverwaltung() {
		super();
	}

	public static Mitarbeiterverwaltung getInstance() {
		if (instance == null) {
			instance = new Mitarbeiterverwaltung();
		}
		return instance;
	}

	public List<Mitarbeiter> getAllMitarbeiter() throws ConnectException,
			DAOException, SQLException {

		List<Mitarbeiter> result = null;

		result = super.getAllMitarbeiter();

		return result;
	}

	public List<Mitarbeiter> getMitarbeiterByName(String name)
			throws ConnectException, DAOException, SQLException {

		List<Mitarbeiter> result = null;

		result = super.getMitarbeiterByName(name);

		return result;
	}

	public Mitarbeiter getMitarbeiterById(Long id) throws ConnectException,
			DAOException, SQLException {
		Mitarbeiter mitarbeiter = super.getMitarbeiterById(id);
		return mitarbeiter;
	}

	public void createMitarbeiter(Mitarbeiter mitarbeiter)
			throws ConnectException, DAOException, SQLException {

		super.createMitarbeiter(mitarbeiter);
	}

}
