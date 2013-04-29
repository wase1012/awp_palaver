/**
 * 
 */
package de.bistrosoft.palaver.rezeptverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;
import de.bistrosoft.palaver.data.RezeptartDAO;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezeptart;

/**
 * @author Android
 *
 */
public class Rezeptartverwaltung extends RezeptartDAO{
	private static Rezeptartverwaltung instance = null;

	private Rezeptartverwaltung() {
		super();
	}

	public static Rezeptartverwaltung getInstance() {
		if (instance == null) {
			instance = new Rezeptartverwaltung();
		}
		return instance;
	}

	public List<Rezeptart> getAllRezeptart() throws ConnectException,
			DAOException, SQLException {

		List<Rezeptart> result = null;

		result = super.getAllRezeptart();

		return result;
	}

	public List<Rezeptart> getGeschmackByName(String name)
			throws ConnectException, DAOException, SQLException {

		List<Rezeptart> result = null;

		result = super.getRezeptartByName(name);

		return result;
	}

	public Rezeptart getRezeptartById(Long id) throws ConnectException,
			DAOException, SQLException {
		Rezeptart rezeptart = super.getRezeptartById(id);
		return rezeptart;
	}

	public void createRezeptart(Rezeptart rezeptart) throws ConnectException,
			DAOException, SQLException {

		super.createRezeptart(rezeptart);
	}
}
