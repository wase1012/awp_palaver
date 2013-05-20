package de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;

/**
 * @Author Christian Barth
 */
public class MitarbeiterDAO extends AbstractDAO{
	
	private final static String 		TABLE = "mitarbeiter";
	private final static String 		ID = "id";
	private final static String			NAME = "name";
	private final static String 		VORNAME = "vorname";
	private final static String			EMAIL = "email";
	private final static String			PASSWORT = "passwort";
	private final static String			EINTRITTSDATUM = "eintrittsdatum";
	private final static String			AUSTRITTSDATUM = "austrittsdatum";
	
	private static final String		GET_ALL_MITARBEITER = "SELECT * FROM Mitarbeiter";
	private static final String		GET_MITARBEITER_BY_ID = "SELECT * FROM Mitarbeiter WHERE id = {0}";
	private static final String 	GET_MITARBEITER_BY_NAME = "SELECT * FROM "
			+ TABLE + " WHERE " + NAME + " LIKE"+" '%";
	private static final String GET_MITARBEITER_BY_ROLLEN_ID = "SELECT * FROM mitarbeiter " +
			"JOIN mitarbeiter_has_rollen on mitarbeiter.id = mitarbeiter_has_rollen.mitarbeiter_fk " +
			"join rollen on mitarbeiter_has_rollen.rollen_fk = rollen.id where rollen.id = {0}";
	
	private static MitarbeiterDAO instance = null;

	public static MitarbeiterDAO getInstance() {
		if (instance == null) {
			instance = new MitarbeiterDAO();
		}
		return instance;
	}

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
								set.getString("austrittsdatum"),
								RollenDAO.getInstance().getRollenByMitarbeiterId(set.getLong("id"))
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
								set.getString("austrittsdatum"),
								RollenDAO.getInstance().getRollenByMitarbeiterId(set.getLong("id"))
								);
		}
		return mitarbeiter;
		}
		
		public List<Mitarbeiter> getMitarbeiterByRollenId(Long id) throws ConnectException, DAOException, SQLException{
			
			List<Mitarbeiter> list = new ArrayList<Mitarbeiter>();
			
			ResultSet set = getManaged(MessageFormat.format(GET_MITARBEITER_BY_ROLLEN_ID, id));
			
			while (set.next()) {
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
		
		public List<Mitarbeiter> getMitarbeiterByName(String name) throws ConnectException, DAOException, SQLException{
			
			List<Mitarbeiter> list = new ArrayList<Mitarbeiter>();
			
			ResultSet set = getManaged(GET_MITARBEITER_BY_NAME + name + "%'");

			while (set.next()) {
				list.add(new Mitarbeiter(set.getLong(ID), set.getString(NAME), set
						.getString(VORNAME), set.getString(EMAIL), set
						.getString(PASSWORT), set.getString(EINTRITTSDATUM),
						set.getString(AUSTRITTSDATUM), RollenDAO.getInstance().getRollenByMitarbeiterId(set.getLong(ID))));
			}
			
			return list;
		}

		public void createMitarbeiter(Mitarbeiter mitarbeiter) throws ConnectException, DAOException, SQLException {
			String INSERT_QUERY = "INSERT INTO " + TABLE + "(" + NAME + ","
					+ VORNAME + "," + EMAIL + "," + PASSWORT + "," + EINTRITTSDATUM
					+ "," + AUSTRITTSDATUM + ")"
					+ "VALUES" + "('" + mitarbeiter.getName() + "','"
					+ mitarbeiter.getVorname() + "','"
					+ mitarbeiter.getEmail() + "','" + mitarbeiter.getPasswort()
					+ "','" + mitarbeiter.getEintrittsdatum() + "','" + mitarbeiter.getAustrittsdatum() + "')";
			this.putManaged(INSERT_QUERY);
			
			List<Mitarbeiter> mitarbeiterlist= getAllMitarbeiter();
			
			Mitarbeiter mitarbeiterdb = getMitarbeiterById(mitarbeiterlist.get(mitarbeiterlist.size() - 1).getId());
			
			if(mitarbeiter.getRollen()!=null){
				for(int i = 0; i < mitarbeiter.getRollen().size(); i++){
					
					MitarbeiterHasRollenDAO.getInstance().createMitarbeiterHasRollen(mitarbeiterdb, mitarbeiter.getRollen().get(i));
					
				}
			}
		}
		
		public void updateMitarbeiter(Mitarbeiter mitarbeiter) throws ConnectException,
			DAOException, SQLException {
			String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + NAME + "='"
					+ mitarbeiter.getName() + "'," + VORNAME + "='"
					+ mitarbeiter.getVorname() + "'," + EMAIL + "='"
					+ mitarbeiter.getEmail() + "'," + PASSWORT + "='"
					+ mitarbeiter.getPasswort() + "'," + EINTRITTSDATUM + "='"
					+ mitarbeiter.getEintrittsdatum() + "'," + AUSTRITTSDATUM + "='" + mitarbeiter.getAustrittsdatum()
				    + "' WHERE " + ID + "='" + mitarbeiter.getId() + "'";
			this.putManaged(UPDATE_QUERY);
			
			if(mitarbeiter.getRollen()!=null){
				for(int i = 0; i < mitarbeiter.getRollen().size(); i++){
					if(MitarbeiterHasRollenDAO.getInstance().getMitarbeiterHasRollenByMitarbeiterAndRolle(mitarbeiter, mitarbeiter.getRollen().get(i))==null){
						MitarbeiterHasRollenDAO.getInstance().createMitarbeiterHasRollen(mitarbeiter, mitarbeiter.getRollen().get(i));
					}
				}
			}
		}
	
}
