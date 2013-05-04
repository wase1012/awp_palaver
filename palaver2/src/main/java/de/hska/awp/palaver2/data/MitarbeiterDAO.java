package de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;

/*
 * @Author PhilippT
 */

public class MitarbeiterDAO extends AbstractDAO{
	
	private static final String		GET_ALL_MITARBEITER = "SELECT * FROM Mitarbeiter";
	private static final String		GET_MITARBEITER_BY_ID = "SELECT * FROM Mitarbeiter WHERE id = {0}";
	private static final String		CREATE_MITARBEITER = "INSERT INTO Mitarbeiter (`id`,`name`,`vorname`," +
												"`email`,`passwort`,`eintrittsdatum`,`austrittsdatum`)VALUES({0})";

	public MitarbeiterDAO()
	{
		super();
	}
	
	public List<Mitarbeiter> getAllMitarbeiter() throws ConnectException, DAOException, SQLException
	{
		List<Mitarbeiter> list = new ArrayList<Mitarbeiter>();
		ResultSet set = getManaged(GET_ALL_MITARBEITER);		
		while(set.next())
		{
			list.add(new Mitarbeiter(set.getLong("id"),
								set.getString("name"),
								set.getString("vorname"),
								set.getString("email"),
								set.getString("passwort"),
								set.getString("eintrittsdatum"),
								set.getString("austrittsdatum")
								));
		}
		return list;
	}
	
	public Mitarbeiter getMitarbeiterById(Long id) throws ConnectException, DAOException, SQLException {
		
		if(id ==null) {
			return null;
		}
		Mitarbeiter mitarbeiter = null;
		ResultSet set = getManaged(MessageFormat.format(GET_MITARBEITER_BY_ID, id));

		while(set.next())
		{
			mitarbeiter = new Mitarbeiter(set.getLong("id"),
								set.getString("name"),
								set.getString("vorname"),
								set.getString("email"),
								set.getString("passwort"),
								set.getString("eintrittsdatum"),
								set.getString("austrittsdatum")
								);
		}
		return mitarbeiter;
		 
	}
	
}
