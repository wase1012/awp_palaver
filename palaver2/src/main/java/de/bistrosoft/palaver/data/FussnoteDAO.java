package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;

/**
 * @author Michael Marschall
 * 
 */
public class FussnoteDAO extends AbstractDAO {

	private final static String ID = "id";
	private final static String NAME = "name";
	private static final String ABKUERZUNG = "abkuerzung";
	private final static String TABLE = "fussnote";

	private static FussnoteDAO instance = null;

	Fussnote fussnote;

	private final static String GET_ALL_FUSSNOTE = "SELECT * FROM " + TABLE;
	private final static String GET_FUSSNOTE_BY_ID = "SELECT * FROM " + TABLE
			+ " WHERE " + ID + "={0}";
	private final static String GET_FUSSNOTE_BY_NAME = "SELECT * FROM fussnote WHERE name = {0}";
	private static final String DELETE_FUSSNOTE_BY_NAME = "DELETE FROM "
			+ TABLE + " WHERE " + NAME + " LIKE" + " '%";
	private static final String DELETE_FUSSNOTE_BY_ID = "DELETE FROM " + TABLE
			+ " WHERE id = {0}";
	private final static String GET_FUSSNOTE_BY_MENUE = "Select fussnote.id, fussnote.name, fussnote.abkuerzung from fussnote JOIN menue_has_fussnote On menue_has_fussnote.fussnote_fk = fussnote.id WHERE menue_has_fussnote.menue_fk = {0}";

	public FussnoteDAO() {
		super();
	}

	public static FussnoteDAO getInstance() {
		if (instance == null) {
			instance = new FussnoteDAO();
		}

		return instance;
	}

	public List<Fussnote> getAllFussnote() throws ConnectException,
			DAOException, SQLException {
		List<Fussnote> list = new ArrayList<Fussnote>();
		ResultSet set = get(GET_ALL_FUSSNOTE);
		while (set.next()) {
			list.add(new Fussnote(set.getLong(ID), set.getString(NAME), set
					.getString(ABKUERZUNG)));
		}
		return list;
	}
	
	public List<Fussnote> getFussnoteByMenue(Long id) throws ConnectException,
	DAOException, SQLException {
List<Fussnote> list = new ArrayList<Fussnote>();
ResultSet set = get(MessageFormat.format(GET_FUSSNOTE_BY_MENUE, id));
while (set.next()) {
	list.add(new Fussnote(set.getLong(ID), set.getString(NAME), set
			.getString(ABKUERZUNG)));
}
return list;
}

	public Fussnote getFussnoteById(Long id) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = get(MessageFormat.format(GET_FUSSNOTE_BY_ID, id));
		while (set.next()) {
			fussnote = new Fussnote(set.getLong(ID), set.getString(NAME),
					set.getString(ABKUERZUNG));
		}
		return fussnote;
	}

//	public List<Fussnote> getFussnoteByName(String name)
//			throws ConnectException, DAOException, SQLException {
//		List<Fussnote> list = new ArrayList<Fussnote>();
//		ResultSet set = get(GET_FUSSNOTE_BY_NAME + name + "%'");
//		while (set.next()) {
//			list.add(new Fussnote(set.getLong(ID), set.getString(NAME), set
//					.getString(ABKUERZUNG)));
//		}
//		return list;
//	}

	public Fussnote getFussnoteByName(String fn) throws ConnectException,
	DAOException, SQLException {
Fussnote result = null;

ResultSet set = get(MessageFormat.format(GET_FUSSNOTE_BY_NAME, NAME));

while (set.next()) {
	result = new Fussnote(set.getLong("id"), set.getString("name"), set.getString("abkuerzung"));
}

return result;
}	
	
	
	
	
	public void createFussnote(Fussnote fussnote) throws ConnectException,
			DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE
				+ "(name, abkuerzung) VALUES('" + fussnote.getName() + "','"
				+ fussnote.getAbkuerzung() + "');";
		this.put(INSERT_QUERY);
	}

	public void updateFussnote(Fussnote fussnote) throws ConnectException,
			DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + NAME + "='"
				+ fussnote.getName() + "'," + ABKUERZUNG + "='"
				+ fussnote.getAbkuerzung() + "'" + " WHERE " + ID + "='"
				+ fussnote.getId() + "'";
		this.put(UPDATE_QUERY);
	}

	public void deleteFussnoteByName(String name) throws ConnectException,
			DAOException, SQLException {
		if (name == null) {
			throw new NullPointerException("keine Fussnote übergeben!");
		}
		put(DELETE_FUSSNOTE_BY_NAME + name + "%'");
	}

	public void deleteFussnoteById(Long id) throws ConnectException,
			DAOException, SQLException {
		if (id == null) {
			throw new NullPointerException("keine Fussnote übergeben!");
		}
		put(MessageFormat.format(DELETE_FUSSNOTE_BY_ID, id));
	}
}
