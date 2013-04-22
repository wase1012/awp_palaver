package de.hska.awp.palaver2.nachrichtenverwaltung.service;

import java.util.List;

import de.hska.awp.palaver2.nachrichtenverwaltung.domain.Nachricht;
import de.hska.awp.palaver2.util.Dao;

/**
 * @author PhilippT
 *
 */

public class NachrichtenverwaltungDao extends Dao {
	private static final long serialVersionUID = -65787978978665L;

	public List<Nachricht> findAllNachricht() {
		
		List<Nachricht> nachrichten = null;
				
		nachrichten = find(Nachricht.class, Nachricht.FIND_ALL_Nachricht);
			
		return nachrichten;
	}
	
	public Nachricht findNachrichtById(Long id) {
		
		Nachricht nachricht = null;
		
		nachricht = find(Nachricht.class, id);
	
		return nachricht;
	}
	
	
}
