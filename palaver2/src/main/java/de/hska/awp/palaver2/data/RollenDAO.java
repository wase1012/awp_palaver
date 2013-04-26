package de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Rollen;

/*
 * @Author PhilippT
 */
public class RollenDAO extends AbstractDAO {

	private final static String			GET_ALL_ROLLEN = "SELECT * FROM rollen";
	private final static String			GET_ROLLE_BY_ID = "SELECT * FROM rollen where id = {0}";
	private final static String			GET_ROLLE_BY_NAME = "SELECT * FROM rollen where name = '{0}'";
	private final static String			PUT_ROLLE = "INSERT INTO rolle(`id`,`name`)VALUES({0})";
	
	public RollenDAO()
	{
		super();
	}
	
	public List<Rollen> getAllRollen() throws ConnectException, DAOException, SQLException
	{
		List<Rollen> list = new ArrayList<Rollen>();
		
		ResultSet set = get(GET_ALL_ROLLEN);
		
		while(set.next())
		{
			list.add(new Rollen(set.getLong("id"),
								set.getString("name")
								));
		}
		
		return list;
	}
	
	public Rollen getRollenById(Long id) throws ConnectException, DAOException, SQLException {
		
		if(id ==null) {
			return null;
		}
		Rollen rolle = null;
		ResultSet set = get(MessageFormat.format(GET_ROLLE_BY_ID, id));

		while(set.next())
		{
			rolle = new Rollen(set.getLong("id"),
								set.getString("name")
								);
		}
		return rolle;
		 
	}
	
}
