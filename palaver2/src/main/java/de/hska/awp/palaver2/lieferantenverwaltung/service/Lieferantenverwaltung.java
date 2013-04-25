/**
 * Created by bach1014
 * 17.04.2013 - 14:54:02
 */
package de.hska.awp.palaver2.lieferantenverwaltung.service;

import java.io.Serializable;
import java.util.List;

import de.hska.awp.palaver2.lieferantenverwaltung.domain.Ansprechpartner;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;

/**
 * @author bach1014
 *
 */
public class Lieferantenverwaltung implements Serializable {
	
	/**
	 *
	 */
	private static final long serialVersionUID = -5676269878706221454L;

	private LieferantenverwaltungDao dao;
	
	private static Lieferantenverwaltung instance = null;
	
	private Lieferantenverwaltung()
	{
		super();
	}
	
	public static Lieferantenverwaltung getInstance()
	{
		if (instance == null)
		{
			instance = new Lieferantenverwaltung();
		}
		return instance;
	}
	
	public List<Ansprechpartner> findAllAnsprechpartner() {
		final List<Ansprechpartner> aplist = dao.findAllAnsprechpartner();
		return aplist;
	}
	
	public List<Ansprechpartner> findAnsprechpartnerByName(String name) {
		final List<Ansprechpartner> aplist = dao.findAnsprechpartnerByName(name);
		return aplist;
	}

	public Ansprechpartner findAnsprechpartnerById(Long id) {
		final Ansprechpartner ap = dao.findAnsprechpartnerById(id);
		return ap;
	}
	
	public Ansprechpartner createAnsprechpartner(Ansprechpartner ap) {
		
		if (ap == null) {
			return ap;
		}
		
		ap = (Ansprechpartner) dao.create(ap);
		
		return ap;
	}
	
	public Ansprechpartner updateAnsprechpartner(Ansprechpartner ap) {
		
		if (ap == null) {
			return null;
		}

		ap = (Ansprechpartner) dao.update(ap);
		return ap;
	}

	/**
	 */
	public void deleteAnsprechpartner(Ansprechpartner ap) {
		
		if (ap == null) {
			return;
		}

		dao.delete(ap);
	}
	
	public List<Lieferant> findAllLieferant() {
		final List<Lieferant> lieferantlist = dao.findAllLieferant();
		return lieferantlist;
	}
	
	public List<Lieferant> findLieferantByName(String name) {
		final List<Lieferant> lieferantlist = dao.findLieferantByName(name);
		return lieferantlist;
	}

	public Lieferant findLieferantById(Long id) {
		final Lieferant lieferant = dao.findLieferantById(id);
		return lieferant;
	}
	
	public Lieferant createLieferant(Lieferant lieferant) {
		
		if (lieferant == null) {
			return lieferant;
		}
		
		lieferant = (Lieferant) dao.create(lieferant);
		
		return lieferant;
	}
	
	public Lieferant updateLieferant(Lieferant lieferant) {
		
		if (lieferant == null) {
			return null;
		}

		lieferant = (Lieferant) dao.update(lieferant);
		return lieferant;
	}

}
