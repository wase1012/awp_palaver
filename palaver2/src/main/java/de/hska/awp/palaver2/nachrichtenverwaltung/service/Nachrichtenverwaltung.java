package de.hska.awp.palaver2.nachrichtenverwaltung.service;


import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import de.hska.awp.palaver2.dao.old.ConnectException;
import de.hska.awp.palaver2.dao.old.DAOException;
import de.hska.awp.palaver2.dao.old.NachrichtDAO;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Rollen;
import de.hska.awp.palaver2.nachrichtenverwaltung.service.NachrichtenverwaltungDao;
import de.hska.awp.palaver2.nachrichtenverwaltung.domain.Nachricht;

/**
 * @author PhilippT
 *
 */

public class Nachrichtenverwaltung implements Serializable {
	
	private static final long serialVersionUID = -5520738420154763865L;
	
	private NachrichtDAO dao;
	
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
		
		final Nachricht nachricht = dao.getNachrichtById(id);
		
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
		nachrichten = dao.getNachrichtByRolle(rolle);
		
		return nachrichten;
		
	}
	
	public List<Nachricht> findAllNachricht() throws ConnectException, DAOException, SQLException {
		final List<Nachricht> nachrichten = dao.getAllNachricht();
		
		if(nachrichten==null) {
			return null;
		}
		
		return nachrichten;
	}
	
	public Nachricht createNachricht(Nachricht nachricht) throws ConnectException, DAOException, SQLException {
		
		if(nachricht == null) {
			return null;
		}
			
		nachricht = (Nachricht) dao.createNachricht(nachricht);
		
		return nachricht;
	}
	
	public void deleteNachricht(Nachricht nachricht) throws ConnectException, DAOException, SQLException {
		
		if(nachricht == null) {
			return;
		}
		
		nachricht = findNachrichtById(nachricht.getId());
		
		if(nachricht == null) {
			return;
		}
		
		dao.deleteNachricht(nachricht);
		
	}

}
