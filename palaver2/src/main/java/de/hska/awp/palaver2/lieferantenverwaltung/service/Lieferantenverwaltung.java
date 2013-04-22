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
	
	public Lieferant createLieferant(Lieferant lieferant) {
		
		if (lieferant == null) {
			return lieferant;
		}
		
		lieferant = (Lieferant) dao.create(lieferant);
		
		return lieferant;
	}
	

}
