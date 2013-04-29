/**
 * 
 */
package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;

/**
 * @author Michael Marschall
 * 
 */
public class FussnoteDAO extends AbstractDAO {

	private final static String ID = "id";
	private final static String NAME = "name";
	private final static String ABKUERZUNG = "abkuerzung";

	private static FussnoteDAO instance = null;

	Fussnote fussnote;

	private final static String TABLE = "fussnote";
	private final static String GET_ALL_FUSSNOTE = "SELECT * FROM " + TABLE;
	private final static String GET_FUSSNOTE_BY_ID = "SELECT * FROM " + TABLE
			+ " WHERE " + ID + "={0}";

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

	public Fussnote getFussnoteById(Long id) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = get(MessageFormat.format(GET_FUSSNOTE_BY_ID, id));
		while (set.next()) {
			fussnote = new Fussnote(set.getLong(ID), set.getString(NAME),
					set.getString(ABKUERZUNG));
		}
		return fussnote;
	}

	public void createNewFussnote(Fussnote fussnote) throws ConnectException,
			DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE
				+ "(name,abkuerzung) VALUES('" + fussnote.getName() + "','"
				+ fussnote.getAbkuerzung() + "');";
		this.put(INSERT_QUERY);
	}

	// public void deleteFussnote{
	// ???
	// }

}
