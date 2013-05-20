package de.bistrosoft.palaver.rezeptverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.bistrosoft.palaver.data.GeschmackDAO;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;

/**
 * @author Michael Marschall
 * 
 */
public class Geschmackverwaltung extends GeschmackDAO {

	private static Geschmackverwaltung instance = null;
	private GeschmackDAO dao;

	private Geschmackverwaltung() {
		super();
	}

	public static Geschmackverwaltung getInstance() {
		if (instance == null) {
			instance = new Geschmackverwaltung();
		}
		return instance;
	}

	public List<Geschmack> getAllGeschmack() throws ConnectException,
			DAOException, SQLException {
		List<Geschmack> result = null;
		result = super.getAllGeschmack();
		return result;
	}

	public List<Geschmack> getAllGeschmackAktiv() throws ConnectException,
			DAOException, SQLException {
		List<Geschmack> result = null;
		result = super.getAllGeschmackAktiv();
		return result;
	}

	public Geschmack getGeschmackById(Long id) throws ConnectException,
			DAOException, SQLException {
		Geschmack result = null;
		result = super.getGeschmackById(id);
		return result;
	}

	public List<Geschmack> getGeschmackByName(String name)
			throws ConnectException, DAOException, SQLException {
		List<Geschmack> result = null;
		result = super.getGeschmackByName(name);
		return result;
	}

	public void createGeschmack(Geschmack geschmack) throws ConnectException,
			DAOException, SQLException {
		super.createGeschmack(geschmack);
	}

	public void updateGeschmack(Geschmack geschmack) throws ConnectException,
			DAOException, SQLException {
		super.updateGeschmack(geschmack);
	}

	public void deleteGeschmackByName(String name) throws ConnectException,
			DAOException, SQLException {
		super.deleteGeschmackByName(name);
	}

	public void deleteGeschmackById(Long id) throws ConnectException,
			DAOException, SQLException {
		super.deleteGeschmackById(id);
	}

}
