package de.hska.awp.palaver2.nachrichtenverwaltung.service;


import java.sql.SQLException;
import java.util.List;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.data.NachrichtDAO;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Rollen;
import de.hska.awp.palaver2.nachrichtenverwaltung.domain.Nachricht;

/**
 * @author PhilippT
 *
 */

public class Nachrichtenverwaltung extends NachrichtDAO {
	
	private static final long serialVersionUID = -5520738420154763865L;
	
	private static Nachrichtenverwaltung 	instance = null;
	
	private Nachrichtenverwaltung()
	{
		super();
	}
	
	public static Nachrichtenverwaltung getInstance()
	{
		if (instance == null)
		{
			instance = new Nachrichtenverwaltung();
		}
		return instance;
	}
	
	public Nachricht findNachrichtById(Long id) throws ConnectException, DAOException, SQLException {
		
		final Nachricht nachricht = super.getNachrichtById(id);
		
		if(nachricht==null) {
			return null;
		}
		
		return nachricht;
	}
	
	public List<Nachricht> findNachrichtByRolle(Rollen rolle) throws ConnectException, DAOException, SQLException {
		if(rolle == null) {
			return null;
		}
		
		List<Nachricht> nachrichten = null;
		nachrichten = super.getNachrichtByRolle(rolle);
		
		return nachrichten;
		
	}
	
	public List<Nachricht> getAllNachricht() throws ConnectException, DAOException, SQLException {
		
		final List<Nachricht> nachrichten = super.getAllNachricht();
		
		if(nachrichten==null) {
			return null;
		}
		
		return nachrichten;
	}
	
	public void createNachricht(Nachricht nachricht) throws ConnectException, DAOException, SQLException {
		
		if(nachricht == null) {
			return;
		}			
		super.createNachricht(nachricht);
	}
	
	public void deleteNachricht(Nachricht nachricht) throws ConnectException, DAOException, SQLException {
		
		if(nachricht == null) {
			return;
		}
		nachricht = findNachrichtById(nachricht.getId());
		if(nachricht == null) {
			return;
		}
		super.deleteNachricht(nachricht.getId());
		
	}

}
