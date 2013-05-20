package de.bistrosoft.palaver.rezeptverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.bistrosoft.palaver.data.ZubereitungDAO;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Zubereitung;

/**
 * @author Michael Marschall
 * 
 */
public class Zubereitungverwaltung extends ZubereitungDAO {

	private static Zubereitungverwaltung instance = null;
	private ZubereitungDAO dao;

	private Zubereitungverwaltung() {
		super();
	}

	public static Zubereitungverwaltung getInstance() {
		if (instance == null) {
			instance = new Zubereitungverwaltung();
		}
		return instance;
	}

	public List<Zubereitung> getAllZubereitung() throws ConnectException,
			DAOException, SQLException {
		List<Zubereitung> result = null;
		result = super.getAllZubereitung();
		return result;
	}

	public Zubereitung getZubereitungById(Long id) throws ConnectException,
			DAOException, SQLException {
		Zubereitung result = null;
		result = super.getZubereitungById(id);
		return result;
	}

	public List<Zubereitung> getZubereitungByName(String name)
			throws ConnectException, DAOException, SQLException {
		List<Zubereitung> result = null;
		result = super.getZubereitungByName(name);
		return result;
	}

	public void createZubereitung(Zubereitung zubereitung)
			throws ConnectException, DAOException, SQLException {
		super.createZubereitung(zubereitung);
	}

	public void updateZubereitung(Zubereitung zubereitung)
			throws ConnectException, DAOException, SQLException {
		super.updateZubereitung(zubereitung);
	}

	public void deleteZubereitungByName(String name) throws ConnectException,
			DAOException, SQLException {
		super.deleteZubereitungByName(name);
	}

	public void deleteZubereitungById(Long id) throws ConnectException,
			DAOException, SQLException {
		super.deleteZubereitungById(id);
	}
}
