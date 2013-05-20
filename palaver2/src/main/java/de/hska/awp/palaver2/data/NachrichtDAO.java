package de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Rollen;
import de.hska.awp.palaver2.nachrichtenverwaltung.domain.Nachricht;


/**
 * 
 * @author PhilippT
 *
 */

public class NachrichtDAO extends AbstractDAO {
	
	private static NachrichtDAO instance = null;
	private static final String		GET_ALL_NACHRICHTEN = "SELECT * FROM Nachrichten";
	private static final String		GET_NACHRICHT_BY_ID = "SELECT * FROM Nachrichten WHERE id = {0}";
	private static final String		GET_NACHRICHT_BY_Rolle = "SELECT * FROM Nachrichten WHERE empf_rolle_fk = {0}";
	private static final String		CREATE_NACHRICHT = "Insert into Nachrichten (nachricht, sender_fk, empf_rolle_fk) values({0})";
	private static final String		DELETE_NACHRICHT = "DELETE FROM Nachrichten WHERE id = {0}";
	
	public NachrichtDAO()
	{
		super();
	}
	
	public static NachrichtDAO getInstance() {
		if (instance == null) {
			instance = new NachrichtDAO();
		}
		return instance;
	}
	
	/**
	 * Methode um eine Nachricht anhand der ID zu suchen
	 * @param id
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	
	public Nachricht getNachrichtById(Long id) throws ConnectException, DAOException, SQLException {
		
		if(id ==null) {
			return null;
		}
		Nachricht nachricht = null;
		ResultSet set = getManaged(MessageFormat.format(GET_NACHRICHT_BY_ID, id));

		while (set.next()) {
			nachricht = new Nachricht(set.getLong("id"), 
							set.getString("nachricht"),
							new Mitarbeiter(),
							new Rollen()
							);
		}
		return nachricht;
		 
	}
	
	/**
	 * Methode um eine Nachricht zu gegebener Rolle zu suchen
	 * @param rolle
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	
	public List<Nachricht> getNachrichtByRolle(Rollen rolle) throws ConnectException, DAOException, SQLException {
		
		if(rolle ==null) {
			return null;
		}
		List<Nachricht> list = new ArrayList<Nachricht>();
		
		ResultSet set = getManaged(MessageFormat.format(GET_NACHRICHT_BY_Rolle, rolle.getId()));

		while(set.next())
		{
			list.add(new Nachricht(set.getLong("id"),
								set.getString("nachricht"),
								new Mitarbeiter(),
								new Rollen()
								));
		}
		return list;
		 
	}
	
	/**
	 * Methode um alle Nachrichten zu suchen, ausgeben zu lassen
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	
	public List<Nachricht> getAllNachricht() throws ConnectException, DAOException, SQLException
	{
		List<Nachricht> list = new ArrayList<Nachricht>();
		ResultSet set = getManaged(GET_ALL_NACHRICHTEN);		
		while(set.next())
		{
			list.add(new Nachricht(set.getLong("id"),
								set.getString("nachricht"),
								new Mitarbeiter(),
								new Rollen()
								));
		}
		return list;
	}
	
	/**
	 * Methode um eine neue Nachricht anzulegen. Nachricht wird mitgeliefert
	 * @param nachricht
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	
	public void createNachricht(Nachricht nachricht)
			throws ConnectException, DAOException, SQLException {
		
		if(nachricht == null) {
			throw new NullPointerException("keine Nachricht übergeben");
		
		}

		if(nachricht.getEmpfaengerRolle() == null) {
			throw new NullPointerException("keine getEmpfaengerRolle übergeben");
		
		}
		
		if(nachricht.getMitarbeiterBySenderFk() == null) {
			throw new NullPointerException("keine getMitarbeiterBySenderFk übergeben");
		
		}
		
//		put(CREATE_NACHRICHT + nachricht.getNachricht() + "," + 
//									nachricht.getMitarbeiterBySenderFk().getId() + "," + nachricht.getEmpfaengerRolle().getId());
		
		String anlegen = "Insert into Nachrichten (nachricht, sender_fk, empf_rolle_fk) values('" + nachricht.getNachricht() + "'," 
							+ nachricht.getMitarbeiterBySenderFk().getId() + "," + nachricht.getEmpfaengerRolle().getId() + ")";
		this.putManaged(anlegen); 

	}
	
	/** 
	 * Methode um eine vorhandene Nachricht zu gegebener ID zu löschen
	 * @param id
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	
	public void deleteNachricht(Long id) 
			throws ConnectException, DAOException, SQLException {
		
		if(id == null) {
			throw new NullPointerException("keine Nachricht übergeben");
		}		
		putManaged(MessageFormat.format(DELETE_NACHRICHT, id));
	}
}
