package de.bistrosoft.palaver.nachrichtenverwaltung.service;


import java.io.Serializable;
import java.util.List;

import de.bistrosoft.palaver.nachrichtenverwaltung.domain.Nachricht;

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
