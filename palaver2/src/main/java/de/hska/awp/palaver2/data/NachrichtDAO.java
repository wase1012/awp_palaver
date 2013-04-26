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
	
	private static final String		TABLE = "nachrichten";
	private static final String		GET_ALL_NACHRICHTEN = "SELECT * FROM Nachrichten";
	private static final String		GET_NACHRICHT_BY_ID = "SELECT * FROM Nachrichten WHERE id = {0}";
	private static final String		GET_NACHRICHT_BY_Rolle = "SELECT * FROM Nachrichten WHERE empf_rolle_fk = {0}";
	private static final String		CREATE_NACHRICHT = "INSERT INTO Nachrichten (`id`,`nachricht`,`sender_fk`,`empf_rolle_fk`)VALUES({0})";

	public NachrichtDAO()
	{
		super();
	}
	
	public Nachricht getNachrichtById(Long id) throws ConnectException, DAOException, SQLException {
		
		if(id ==null) {
			return null;
		}
		Nachricht nachricht = null;
		ResultSet set = get(MessageFormat.format(GET_NACHRICHT_BY_ID, id));

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
		
		ResultSet set = get(MessageFormat.format(GET_NACHRICHT_BY_Rolle, rolle));

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
								new Mitarbeiter(),
								new Rollen()
								));
		}
		return list;
	}
	
	public void createNachricht(Nachricht nachricht)
			throws ConnectException, DAOException, SQLException {
		
		if(nachricht == null) {
			throw new NullPointerException("keine Nachricht übergeben");
		}

		put(MessageFormat.format(CREATE_NACHRICHT, nachricht.getId() + "," + nachricht.getNachricht() + "," + 
									nachricht.getMitarbeiterBySenderFk() + "," + nachricht.getEmpfaengerRolle()));

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
