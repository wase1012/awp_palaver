package de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.MitarbeiterHasRollen;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Rollen;

public class MitarbeiterHasRollenDAO extends AbstractDAO{

	private static MitarbeiterHasRollenDAO instance = null;

	private final static String TABLE = "mitarbeiter_has_rollen";
	private final static String MITARBEITER_FK = "mitarbeiter_fk";
	private final static String ROLLEN_FK = "rollen_fk";
	private final static String GET_ALL_MITARBEITER_HAS_ROLLEN = "SELECT * FROM mitarbeiter_has_rollen";
	private final static String DELETE = "DELETE FROM mitarbeiter_has_rollen WHERE mitarbeiter_has_rollen.mitarbeiter_fk = {0} AND mitarbeiter_has_rollen.rollen_fk = {1}";
	private final static String GET_MITARBEITER_HAS_ROLLEN_BY_MITARBEITER_AND_ROLLE = "SELECT * FROM mitarbeiter_has_rollen WHERE mitarbeiter_has_rollen.mitarbeiter_fk = {0} AND mitarbeiter_has_rollen.rollen_fk = {1}";
	
	public MitarbeiterHasRollenDAO() {
		super();
	}

	public static MitarbeiterHasRollenDAO getInstance() {
		if (instance == null) {
			instance = new MitarbeiterHasRollenDAO();
		}
		return instance;
	}
	
	public List<MitarbeiterHasRollen> getAllMitarbeiterHasRollen() throws ConnectException, DAOException, SQLException
	{
		List<MitarbeiterHasRollen> list = new ArrayList<MitarbeiterHasRollen>();
		ResultSet set = getManaged(GET_ALL_MITARBEITER_HAS_ROLLEN);		
		while(set.next())
		{
			list.add(new MitarbeiterHasRollen(set.getLong("mitarbeiter_fk"),
					set.getLong("rollen_fk")
								));
		}
		return list;
	}
	
	public MitarbeiterHasRollen getMitarbeiterHasRollenByMitarbeiterAndRolle(Mitarbeiter mitarbeiter, Rollen rolle) throws ConnectException, DAOException, SQLException
	{
		MitarbeiterHasRollen mitarbeiterhasrollen = new MitarbeiterHasRollen();
		ResultSet set = getManaged(MessageFormat.format(GET_MITARBEITER_HAS_ROLLEN_BY_MITARBEITER_AND_ROLLE, mitarbeiter.getId(), rolle.getId()));		
		while(set.next())
		{
			mitarbeiterhasrollen = new MitarbeiterHasRollen(set.getLong("mitarbeiter_fk"),
					set.getLong("rollen_fk")
								);
		}
		return mitarbeiterhasrollen;
	}
	
	
	public void createMitarbeiterHasRollen(Mitarbeiter mitarbeiter, Rollen rolle) throws ConnectException, DAOException, SQLException {
		
		
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(" + MITARBEITER_FK + ","
				+ ROLLEN_FK + ")"
				+ "VALUES" + "('" + mitarbeiter.getId() + "','"
				+ rolle.getId() + "')";
		this.putManaged(INSERT_QUERY);
	}	
	
	public void deleteMitarbeiterHasRollen(Mitarbeiter mitarbeiter, Rollen rolle) throws ConnectException,
			DAOException, SQLException {

		if (mitarbeiter == null|| rolle == null) {
			throw new NullPointerException(
					"Es wurde kein Mitarbeiter und/oder eine Rolle übergeben");
		}
		putManaged(MessageFormat.format(DELETE, mitarbeiter.getId(), rolle.getId()));
	}

}
