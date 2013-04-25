package de.hska.awp.palaver2.nachrichtenverwaltung.service;


import java.io.Serializable;
import java.util.List;

import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Rollen;
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
	
	public List<Nachricht> findNachrichtByRolle(Rollen rolle) {
		if(rolle == null) {
			return null;
		}
		
		List<Nachricht> nachrichten = null;
		nachrichten = dao.findNachrichtByRolle(Nachricht.class, rolle);
		
		return nachrichten;
		
	}
	
	public List<Nachricht> findAllNachricht() {
		final List<Nachricht> nachrichten = dao.findAllNachricht();
		
		if(nachrichten==null) {
			return null;
		}
		
		return nachrichten;
	}
	
	public Nachricht createNachricht(Nachricht nachricht) {
		
		if(nachricht == null) {
			return null;
		}
			
		nachricht = (Nachricht) dao.create(nachricht);
		
		return nachricht;
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
