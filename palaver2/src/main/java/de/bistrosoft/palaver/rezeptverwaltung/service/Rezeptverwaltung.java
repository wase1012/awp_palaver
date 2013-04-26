/**
 * 
 */
package de.bistrosoft.palaver.rezeptverwaltung.service;

import java.io.Serializable;
import java.util.List;

import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;

/**
 * @author Michael Marschall
 * 
 */
public class Rezeptverwaltung implements Serializable {

	private static final long serialVersionUID = 2805858224490570505L;

	private RezeptverwaltungDao dao;

	public List<Rezept> findAllRezept() {
		final List<Rezept> rlist = dao.findAllRezept();
		return rlist;
	}

	public List<Rezept> findRezeptByName(String name) {
		final List<Rezept> rlist = dao.findRezeptByName(name);
		return rlist;
	}

	public Rezept findRezeptById(Long id) {
		final Rezept rzpt = dao.findRezeptById(id);
		return rzpt;
	}

	public Rezept createRezept(Rezept rzpt) {
		if (rzpt == null) {
			return rzpt;
		}

		rzpt = (Rezept) dao.create(rzpt);

		return rzpt;
	}

	public Rezept updateRezept(Rezept rzpt) {
		if (rzpt == null) {
			return null;
		}

		rzpt = (Rezept) dao.update(rzpt);
		return rzpt;
	}

	public void deleteRezept(Rezept rzpt) {
		if (rzpt == null) {
			return;
		}

		dao.delete(rzpt);
	}

}
