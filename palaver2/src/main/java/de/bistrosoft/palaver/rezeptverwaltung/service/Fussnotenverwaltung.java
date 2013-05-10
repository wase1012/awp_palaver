package de.bistrosoft.palaver.rezeptverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;
import de.bistrosoft.palaver.data.FussnoteDAO;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;

/**
 * @author Michael Marschall
 * 
 */

public class Fussnotenverwaltung extends FussnoteDAO {

	private static Fussnotenverwaltung instance = null;
	private FussnoteDAO dao;

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

	public Fussnote getFussnoteById(Long id) throws ConnectException,
			DAOException, SQLException {
		Fussnote result = null;
		result = super.getFussnoteById(id);
		return result;
	}

	public Fussnote getFussnoteByName(String name)
			throws ConnectException, DAOException, SQLException {
		Fussnote result = null;
		result = super.getFussnoteByName(name);
		return result;
	}

	public void createFussnote(Fussnote fussnote) throws ConnectException,
			DAOException, SQLException {
		dao.createFussnote(fussnote);
	}

	public void updateFussnote(Fussnote fussnote) throws ConnectException,
			DAOException, SQLException {
		super.updateFussnote(fussnote);
	}

	public void deleteFussnoteByName(String name) throws ConnectException,
			DAOException, SQLException {
		super.deleteFussnoteByName(name);
	}

	public void deleteFussnoteById(Long id) throws ConnectException,
			DAOException, SQLException {
		super.deleteFussnoteById(id);
	}
}
