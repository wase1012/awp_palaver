package de.hska.awp.palaver2.dao.old;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Rollen;
import de.hska.awp.palaver2.nachrichtenverwaltung.domain.Nachricht;


/**
 * 
 * @author PhilippT
 *
 */

public class NachrichtDAO extends AbstractDAO {
	
	private static final String		TABLE = "nachrichten";
	private static final String		GET_ALL_NACHRICHTEN = "SELECT * FROM Nachricht";
	private static final String		WERTE = "(nachricht, sender_fk, empf_rolle_fk)";
	private static NachrichtDAO 	instance = null;
	
	private NachrichtDAO()
	{
		super();
	}
	
	public static NachrichtDAO getInstance()
	{
		if (instance == null)
		{
			instance = new NachrichtDAO();
		}
		return instance;
	}
	
	public Nachricht getNachrichtById(Long id) throws ConnectException, DAOException, SQLException {
		
		if(id ==null) {
			return null;
		}
		Nachricht nachricht = null;
		String GET_NACHRICHT_BY_ID = "SELECT FROM"+ TABLE + "WHERE id ='" + id + "'";
		ResultSet set = get(GET_NACHRICHT_BY_ID);

		while (set.next()) {
			nachricht = new Nachricht(set.getLong("id"), 
							set.getString("nachricht"),
							((Nachricht) set).getMitarbeiterBySenderFk(),
							((Nachricht) set).getEmpfaengerRolle()
							);
		}
		return nachricht;
		 
	}
	
	public List<Nachricht> getNachrichtByRolle(Rollen rolle) throws ConnectException, DAOException, SQLException {
		
		if(rolle ==null) {
			return null;
		}
		List<Nachricht> list = new ArrayList<Nachricht>();
		String GET_NACHRICHT_BY_Rolle = "SELECT FROM"+ TABLE + "WHERE empf_rolle_fk ='" + rolle + "'";
		
		ResultSet set = get(GET_NACHRICHT_BY_Rolle);

		while(set.next())
		{
			list.add(new Nachricht(set.getLong("id"),
								set.getString("nachricht"),
								((Nachricht) set).getMitarbeiterBySenderFk(),
								((Nachricht) set).getEmpfaengerRolle()
								));
		}
		return list;
		 
	}
	
	public List<Nachricht> getAllNachricht() throws ConnectException, DAOException, SQLException
	{
		List<Nachricht> list = new ArrayList<Nachricht>();
		ResultSet set = get(GET_ALL_NACHRICHTEN);		
		while(set.next())
		{
			list.add(new Nachricht(set.getLong("id"),
								set.getString("nachricht"),
								((Nachricht) set).getMitarbeiterBySenderFk(),
								((Nachricht) set).getEmpfaengerRolle()
								));
		}
		return list;
	}
	
	public void createNachricht(Nachricht nachricht)
			throws ConnectException, DAOException, SQLException {
		
		if(nachricht == null) {
			throw new NullPointerException("keine Nachricht übergeben");
		}

		String INSERT_QUERY = "INSERT INTO " + TABLE + WERTE + " VALUES('"
				+ nachricht.getNachricht() + "', '" + nachricht.getMitarbeiterBySenderFk() + "', '" 
				+ nachricht.getEmpfaengerRolle() + "')";
		this.put(INSERT_QUERY);

	}
	
	public void deleteNachricht(Nachricht nachricht) 
			throws ConnectException, DAOException, SQLException {
		
		if(nachricht == null) {
			throw new NullPointerException("keine Nachricht übergeben");
		}		
		String DELETE_NACHRICHT = "DELETE FROM" + TABLE + "WHERE id ='" + nachricht.getId() + "'";
		this.put(DELETE_NACHRICHT);
	}
}
