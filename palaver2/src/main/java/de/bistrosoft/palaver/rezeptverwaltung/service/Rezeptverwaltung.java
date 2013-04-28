/**
 * 
 */
package de.bistrosoft.palaver.rezeptverwaltung.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;
import de.bistrosoft.palaver.data.RezeptDAO;
import de.bistrosoft.palaver.data.RezeptartDAO;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;

/**
 * @author Michael Marschall
 * 
 */
//public class Rezeptverwaltung implements Serializable {
public class Rezeptverwaltung extends RezeptDAO {

//	private static final long serialVersionUID = 2805858224490570505L;

//	private Rezeptverwaltung dao;

	private static Rezeptverwaltung instance = null;

	private Rezeptverwaltung() {
		super();
	}

	public static Rezeptverwaltung getInstance() {
		if (instance == null) {
			instance = new Rezeptverwaltung();
		}
		return instance;
	}

	public List<Rezept> getAllRezepts() throws ConnectException, DAOException,
			SQLException {
		List<Rezept> result = null;

		result = super.getAllRezepts();

		return result;
	}

//	public Rezept getRezeptById(Long id) throws ConnectException,
//			DAOException, SQLException {
//		Rezept result = null;
//
//		result = super.getRezeptById(id);
//
//		return result;
//	}
//
//	public List<Rezept> getRezeptByName(String name) throws ConnectException,
//			DAOException, SQLException {
//		List<Rezept> result = null;
//
//		result = super.getRezeptByName(name);
//
//		return result;
//	}
//
//	public void createRezept(Rezept rezept) throws ConnectException,
//			DAOException {
//		super.createRezept(rezept);
//	}
//
//	public void updateRezept(Rezept rezept) throws ConnectException,
//			DAOException {
//		super.updateRezept(rezept);
//	}

	/**
	 * public List<Rezept> findAllRezept() { final List<Rezept> rlist =
	 * dao.findAllRezept(); return rlist; }
	 * 
	 * public List<Rezept> findRezeptByName(String name) { final List<Rezept>
	 * rlist = dao.findRezeptByName(name); return rlist; }
	 * 
	 * public Rezept findRezeptById(Long id) { final Rezept rzpt =
	 * dao.findRezeptById(id); return rzpt; }
	 * 
	 * public Rezept createRezept(Rezept rzpt) { if (rzpt == null) { return
	 * rzpt; }
	 * 
	 * rzpt = (Rezept) dao.createRezept(rzpt);
	 * 
	 * return rzpt; }
	 * 
	 * public Rezept updateRezept(Rezept rzpt) { if (rzpt == null) { return
	 * null; }
	 * 
	 * rzpt = (Rezept) dao.updateRezept(rzpt); return rzpt; }
	 * 
	 * public void deleteRezept(Rezept rzpt) { if (rzpt == null) { return; }
	 * 
	 * dao.deleteRezept(rzpt); }
	 */

}
