/**
 * Created by Christian Barth
 * 18.05.2013 - 10:14:28
 */
package de.hska.awp.palaver2.mitarbeiterverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.data.RollenDAO;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Rollen;

/**
 * @author Christian Barth
 *
 */
public class Rollenverwaltung extends RollenDAO{
	
	private static Rollenverwaltung instance = null;

	private Rollenverwaltung() {
		super();
	}

	public static Rollenverwaltung getInstance() {
		if (instance == null) {
			instance = new Rollenverwaltung();
		}
		return instance;
	}
	
	public List<Rollen> getAllRollen() throws ConnectException,
	DAOException, SQLException {

		List<Rollen> result = null;

		result = super.getAllRollen();

		return result;
	}

	public Rollen getRollenById(Long id) throws ConnectException,
			DAOException, SQLException {
		Rollen rolle = super.getRollenById(id);
		return rolle;
	}

	public List<Rollen> getRollenByMitarbeiterId(Long id)
			throws ConnectException, DAOException, SQLException {

		List<Rollen> result = null;

		result = super.getRollenByMitarbeiterId(id);

		return result;
	}	

	public void createRollen(Rollen rolle)
			throws ConnectException, DAOException, SQLException {

		super.createRollen(rolle);
	}

	public void updateRollen(Rollen rolle)
			throws ConnectException, DAOException, SQLException {

		super.updateRollen(rolle);
	}


}
