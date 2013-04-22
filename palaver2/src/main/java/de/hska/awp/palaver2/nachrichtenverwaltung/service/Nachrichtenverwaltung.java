package de.hska.awp.palaver2.nachrichtenverwaltung.service;


import java.io.Serializable;

import javax.persistence.EntityManager;

import de.hska.awp.palaver2.nachrichtenverwaltung.service.NachrichtenverwaltungDao;
import de.hska.awp.palaver2.nachrichtenverwaltung.domain.Nachricht;

/**
 * @author PhilippT
 *
 */

public class Nachrichtenverwaltung implements Serializable {
	
	private static final long serialVersionUID = -5520738420154763865L;
	
	private NachrichtenverwaltungDao dao;
	
	public Nachricht findNachrichtById(Long id) {
		
		final Nachricht nachricht = dao.find(Nachricht.class, id);
		
		if(nachricht==null) {
			return null;
		}
		
		return nachricht;
	}
	
	public Nachricht createNachricht(Nachricht nachricht) {
		
		if(nachricht == null) {
			return null;
		}
		
		final Nachricht n = new Nachricht();
		n.setNachricht(nachricht.getNachricht());
		n.setMitarbeiterBySenderFk(nachricht.getMitarbeiterBySenderFk());
		n.setMitarbeiterByEmpfaengerFk(nachricht.getMitarbeiterByEmpfaengerFk());
		
		return n;
	}
	
	public void deleteNachricht(Nachricht nachricht) {
		
		if(nachricht == null) {
			return;
		}
		
		nachricht = findNachrichtById(nachricht.getId());
		
		if(nachricht == null) {
			return;
		}
		
		dao.delete(nachricht);
		
	}

}
