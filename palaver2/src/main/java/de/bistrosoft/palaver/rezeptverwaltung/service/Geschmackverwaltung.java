/**
 * 
 */
package de.bistrosoft.palaver.rezeptverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;
import de.bistrosoft.palaver.data.GeschmackDAO;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;

/**
 * @author Android
 * 
 */
public class Geschmackverwaltung extends GeschmackDAO {

	private static Geschmackverwaltung instance = null;

	private Geschmackverwaltung() {
		super();
	}

	public static Geschmackverwaltung getInstance() {
		if (instance == null) {
			instance = new Geschmackverwaltung();
		}
		return instance;
	}

	public List<Geschmack> getAllLieferanten() throws ConnectException,
			DAOException, SQLException {

		List<Geschmack> result = null;

		result = super.getAllGeschmack();

		return result;
	}

	public List<Geschmack> getGeschmackByName(String name)
			throws ConnectException, DAOException, SQLException {

		List<Geschmack> result = null;

		result = super.getGeschmackByName(name);

		return result;
	}

	public Geschmack getGeschmackById(Long id) throws ConnectException,
			DAOException, SQLException {
		Geschmack geschmack = super.getGeschmackById(id);
		return geschmack;
	}

	public void createGeschmack(Geschmack geschmack) throws ConnectException,
			DAOException, SQLException {

		super.createGeschmack(geschmack);
	}
}
