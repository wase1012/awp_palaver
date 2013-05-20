package main.java.de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import main.java.de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Rollen;

/**
 * 
 * @author Christian Barth
 *
 */
public class RollenDAO extends AbstractDAO {

	private final static String 		TABLE = "rollen";
	private final static String 		ID = "id";
	private final static String			NAME = "name";
	private final static String			GET_ALL_ROLLEN = "SELECT * FROM rollen";
	private static final String			GET_ROLLEN_BY_ID = "SELECT * FROM rollen WHERE id = {0}";
	private final static String			GET_ROLLEN_BY_MITARBEITER_ID = "SELECT rollen.id, rollen.name FROM rollen join mitarbeiter_has_rollen on rollen.id = mitarbeiter_has_rollen.rollen_fk where mitarbeiter_fk = {0}";
	
	private static RollenDAO instance = null;

	public static RollenDAO getInstance() {
		if (instance == null) {
			instance = new RollenDAO();
		}
		return instance;
	}
	
	public RollenDAO()
	{
		super();
	}
	
	public List<Rollen> getAllRollen() throws ConnectException, DAOException, SQLException
	{
		List<Rollen> list = new ArrayList<Rollen>();
		
		ResultSet set = getManaged(GET_ALL_ROLLEN);
		
		while(set.next())
		{
			list.add(new Rollen(set.getLong("id"),
								set.getString("name"),
								MitarbeiterDAO.getInstance().getMitarbeiterByRollenId(set.getLong("id"))));
		}
		
		return list;
	}
	
	public Rollen getRollenById(Long id) throws ConnectException, DAOException, SQLException {
		
		Rollen rolle = null;
		ResultSet set = getManaged(MessageFormat.format(GET_ROLLEN_BY_ID, id));

		while(set.next())
		{
			rolle = new Rollen(set.getLong("id"),
								set.getString("name"),
								MitarbeiterDAO.getInstance().getMitarbeiterByRollenId(set.getLong("id"))
								);
		}
		return rolle;
		 
	}
	
	public List<Rollen> getRollenByMitarbeiterId(Long id)
			throws ConnectException, DAOException, SQLException {

		List<Rollen> list = new ArrayList<Rollen>();

		ResultSet set = getManaged(MessageFormat.format(
				GET_ROLLEN_BY_MITARBEITER_ID, id));

		while (set.next()) {
			list.add(new Rollen(set.getLong("id"), set.getString("name")));

		}

		return list;

	}
	
	/**
	 * Die Methode erzeugt eine Rolle in der Datenbank.
	 * 
	 * @param ansprechpartner
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void createRollen(Rollen rolle) throws ConnectException,
			DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(" + NAME + ")"
				+ "VALUES" + "('" + rolle.getName() + "')";
		this.putManaged(INSERT_QUERY);
	}
	
	public void updateRollen(Rollen rolle) throws ConnectException,
			DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + NAME + "='"
				+ rolle.getName() + "' WHERE " + ID + "='"
				+ rolle.getId() + "'";
		this.putManaged(UPDATE_QUERY);
	}
	
}
