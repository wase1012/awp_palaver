/**
 * 
 */
package de.bistrosoft.palaver.rezeptverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;
import de.bistrosoft.palaver.data.FussnoteDAO;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;

public class Fussnotenverwaltung extends FussnoteDAO {

	private static Fussnotenverwaltung instance = null;

	private Fussnotenverwaltung() {
		super();
	}

	public static Fussnotenverwaltung getInstance() {
		if (instance == null) {
			instance = new Fussnotenverwaltung();
		}
		return instance;
	}

	public List<Fussnote> getAllFussnote() throws ConnectException,
			DAOException, SQLException {
		List<Fussnote> result = null;

		result = super.getAllFussnote();

		return result;
	}

}
