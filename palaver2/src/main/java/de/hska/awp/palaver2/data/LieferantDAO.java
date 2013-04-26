/**
 * Created by bach1014
 * 26.04.2013 - 08:32:35
 */
package de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;

/**
 * @author bach1014
 * 
 */
public class LieferantDAO extends AbstractDAO {

	private static LieferantDAO instance = null;

	private final static String TABLE = "lieferant";
	private final static String ID = "id";
	private final static String NAME = "name";
	private final static String KUNDENNUMMER = "kundennummer";
	private final static String BEZEICHNUNG = "bezeichnung";
	private final static String STRASSE = "strasse";
	private final static String PLZ = "plz";
	private final static String ORT = "ort";
	private final static String EMAIL = "email";
	private final static String TELEFON = "telefon";
	private final static String FAX = "fax";

	private final static String GET_ALL_LIEFERANTEN = "SELECT * FROM TABLE";

	private LieferantDAO() {
		super();
	}

	public static LieferantDAO getInstance() {
		if (instance == null) {
			instance = new LieferantDAO();
		}
		return instance;
	}

	public List<Lieferant> getAllLieferanten() throws ConnectException,
			DAOException, SQLException {
		List<Lieferant> list = new ArrayList<Lieferant>();

		ResultSet set = get(GET_ALL_LIEFERANTEN);

		while (set.next()) {
			list.add(new Lieferant(set.getLong(ID), set.getString(NAME),
					set.getString(KUNDENNUMMER),
					set.getString(BEZEICHNUNG), set.getString(STRASSE), set
							.getString(PLZ), set.getString(ORT), set
							.getString(EMAIL), set.getString(TELEFON), set
							.getString(FAX)));
		}

		return list;
	}

	public List<Lieferant> getLieferantenByName(String name)
			throws ConnectException, DAOException, SQLException {
		List<Lieferant> list = new ArrayList<Lieferant>();

		String GET_LIEFERANTEN_BY_NAME = "SELECT * FROM " + TABLE + " WHERE "
				+ NAME + " LIKE " + "'%" + name + "%'";
		ResultSet set = get(GET_LIEFERANTEN_BY_NAME);

		while (set.next()) {
			list.add(new Lieferant(set.getLong(ID), set.getString(NAME),
					set.getString(KUNDENNUMMER),
					set.getString(BEZEICHNUNG), set.getString(STRASSE), set
							.getString(PLZ), set.getString(ORT), set
							.getString(EMAIL), set.getString(TELEFON), set
							.getString(FAX)));
		}

		return list;
	}
	
	public Lieferant getLieferantById(Long id) throws ConnectException,
			DAOException, SQLException {

		Lieferant lieferant = null;
		String GET_LIEFERANT_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + ID
				+ "='" + id + "'";
		ResultSet set = get(GET_LIEFERANT_BY_ID);

		while (set.next()) {
			lieferant = new Lieferant(set.getLong(ID), set.getString(NAME),
					set.getString(KUNDENNUMMER),
					set.getString(BEZEICHNUNG), set.getString(STRASSE),
					set.getString(PLZ), set.getString(ORT),
					set.getString(EMAIL), set.getString(TELEFON),
					set.getString(FAX));
		}

		return lieferant;
	}

	public void createNewLieferant(Lieferant lieferant)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(" + NAME + ","
				+ KUNDENNUMMER + "," + BEZEICHNUNG + "," + STRASSE + "," + PLZ
				+ "," + ORT + "," + EMAIL + "," + TELEFON + "," + FAX + ")"
				+ "VALUES" + "('" + lieferant.getName() + "','"
				+ lieferant.getKundennummer() + "','"
				+ lieferant.getBezeichnung() + "','" + lieferant.getStrasse()
				+ "','" + lieferant.getPlz() + "','" + lieferant.getOrt()
				+ "','" + lieferant.getEmail() + "','" + lieferant.getTelefon()
				+ "','" + lieferant.getTelefon() + "')";
		this.put(INSERT_QUERY);
	}

	public void updateLieferant(Lieferant lieferant) throws ConnectException,
			DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + NAME + "='"
				+ lieferant.getName() + "'," + KUNDENNUMMER + "='"
				+ lieferant.getKundennummer() + "'," + BEZEICHNUNG + "='"
				+ lieferant.getBezeichnung() + "'," + STRASSE + "='"
				+ lieferant.getStrasse() + "'," + PLZ + "='"
				+ lieferant.getPlz() + "'," + ORT + "='" + lieferant.getOrt()
				+ "'," + EMAIL + "='" + lieferant.getEmail() + "'," + TELEFON
				+ "='" + lieferant.getTelefon() + "'," + FAX + "='"
				+ lieferant.getFax() + "'" + "WHERE id='" + lieferant.getId()
				+ "'";
		this.put(UPDATE_QUERY);
	}
}
