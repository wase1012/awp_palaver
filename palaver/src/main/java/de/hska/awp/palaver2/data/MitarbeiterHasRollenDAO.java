package de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.MitarbeiterHasRollen;

public class MitarbeiterHasRollenDAO extends AbstractDAO{

	private static MitarbeiterHasRollenDAO instance = null;

	private final static String TABLE = "mitarbeiter_has_rollen";
//	private final static String ID = "id";
	private final static String MITARBEITER_FK = "mitarbeiter_fk";
	private final static String ROLLEN_FK = "rollen_fk";
	
	private final static String GET_ALL_MITARBEITER_HAS_ROLLEN = "SELECT * FROM mitarbeiter_has_rollen";
	private final static String DELETE_MITARBEITER_HAS_ROLLEN = "DELETE FROM mitarbeiter_has_rollen WHERE mitarbeiter_has_rollen.id= {0}";
	private final static String DELETE = "DELETE FROM mitarbeiter_has_rollen WHERE mitarbeiter_has_rollen.mitarbeiter_fk = {0} AND mitarbeiter_has_rollen.rollen_fk = {1}";
	
	
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
	
	
	public void createMitarbeiterHasRollen(MitarbeiterHasRollen mitarbeiterHasRollen) throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(" + MITARBEITER_FK + ","
				+ ROLLEN_FK + ")"
				+ "VALUES" + "('" + mitarbeiterHasRollen.getMitarbeiter_fk() + "','"
				+ mitarbeiterHasRollen.getRollen_fk() + "')";
		this.putManaged(INSERT_QUERY);
	}
	
//	public void updateMitarbeiterHasRollen(MitarbeiterHasRollen mitarbeiterHasRollen) throws ConnectException,
//		DAOException, SQLException {
//		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + MITARBEITER_FK + "='"
//				+ mitarbeiterHasRollen.getMitarbeiter_fk() + "'," + ROLLEN_FK + "='"
//				+ mitarbeiterHasRollen.getRollen_fk()
//			    + "' WHERE " + ID + "='" + mitarbeiterHasRollen.getId() + "'";
//		this.putManaged(UPDATE_QUERY);
//	}
	
	
	
//	public void deleteMitarbeiterHasRollen(Long id) throws ConnectException,
//			DAOException, SQLException {
//
//		if (id == null) {
//			throw new NullPointerException("keine MitarbeiterHasRollen ID übergeben");
//		}
//		putManaged(MessageFormat.format(DELETE_MITARBEITER_HAS_ROLLEN, id));
//	}
	
	
	public void deleteMitarbeiterHasRollen(Long idmitarbeiter, Long idrolle) throws ConnectException,
			DAOException, SQLException {

		if (idmitarbeiter == null|| idrolle == null) {
			throw new NullPointerException(
					"keine idmitarbeiter oder idrolle übergeben");
		}
		putManaged(MessageFormat.format(DELETE, idmitarbeiter, idrolle));
	}

}
