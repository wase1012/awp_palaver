/**
 * Created by Christian Barth
 * 26.04.2013 - 08:32:35
 */
package de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.util.Util;

/**
 * Klasse LieferantDAO. Die Klasse stellt für den Lieferant alle notwendigen
 * Methoden bereit um auf die Datenbank zuzugreifen.
 * 
 * @author Christian Barth
 * 
 */
public class LieferantDAO extends AbstractDAO {

	private static LieferantDAO instance = null;

	private final static String TABLE = "lieferant";
	private final static String TABLE_ARTIKEL = "artikel";
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
	private final static String NOTIZ = "notiz";
	private final static String MEHRERELIEFERTERMINE = "mehrereliefertermine";

	private final static String GET_ALL_LIEFERANTEN = "SELECT * FROM " + TABLE;

	private static final String GET_LIEFERANT_BY_ID = "SELECT * FROM " + TABLE
			+ " WHERE " + ID + "= {0}";
	private static final String GET_LIEFERANT_BY_NAME = "SELECT * FROM "
			+ TABLE + " WHERE " + NAME + " LIKE"+" '%";
	
	private final static String GET_LIEFERANTEN_BY_ARTIKEL_ID = "SELECT * FROM " + TABLE +
			" join " + TABLE_ARTIKEL + " on " + TABLE +"." + ID + " = " + TABLE_ARTIKEL + ".lieferant_fk" +
			" where " + TABLE_ARTIKEL + "." + ID + " = {0}";

	public LieferantDAO() {
		super();
	}

	/**
	 * @return instance
	 */
	public static LieferantDAO getInstance() {
		if (instance == null) {
			instance = new LieferantDAO();
		}
		return instance;
	}

	/**
	 * Die Methode getAllLieferanten liefert alle in der Datenbank befindlichen
	 * Lieferanten zurück.
	 * 
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Lieferant> getAllLieferanten() throws ConnectException,
			DAOException, SQLException {
		List<Lieferant> list = new ArrayList<Lieferant>();

		ResultSet set = getManaged(GET_ALL_LIEFERANTEN);

		while (set.next()) {
			list.add(new Lieferant(set.getLong(ID), set.getString(NAME), set
					.getString(KUNDENNUMMER), set.getString(BEZEICHNUNG), set
					.getString(STRASSE), set.getString(PLZ),
					set.getString(ORT), set.getString(EMAIL), set
							.getString(TELEFON), set.getString(FAX), set.getString(NOTIZ), set.getBoolean(MEHRERELIEFERTERMINE)));
		}

		return list;
	}

	/**
	 * Die Methode getLieferantenByName liefert eins bis mehrere Ergebnisse
	 * zurück bei der Suche nach einem Lieferanten in der Datenbank.
	 * 
	 * @param name
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Lieferant> getLieferantenByName(String name)
			throws ConnectException, DAOException, SQLException {
		List<Lieferant> list = new ArrayList<Lieferant>();

		ResultSet set = getManaged(GET_LIEFERANT_BY_NAME + name + "%'");

		while (set.next()) {
			list.add(new Lieferant(set.getLong(ID), set.getString(NAME), set
					.getString(KUNDENNUMMER), set.getString(BEZEICHNUNG), set
					.getString(STRASSE), set.getString(PLZ),
					set.getString(ORT), set.getString(EMAIL), set
							.getString(TELEFON), set.getString(FAX), set.getString(NOTIZ), set.getBoolean(MEHRERELIEFERTERMINE)));
		}

		return list;
	}

	/**
	 * Die Methode getLieferantById liefert ein Ergebnisse zurück bei der Suche
	 * nach einem Lieferant in der Datenbank.
	 * 
	 * @param id
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public Lieferant getLieferantById(Long id) throws ConnectException,
			DAOException, SQLException {

		Lieferant lieferant = null;
		ResultSet set = getManaged(MessageFormat.format(GET_LIEFERANT_BY_ID, id));

		while (set.next()) {
			lieferant = new Lieferant(set.getLong(ID), set.getString(NAME),
					set.getString(KUNDENNUMMER), set.getString(BEZEICHNUNG),
					set.getString(STRASSE), set.getString(PLZ),
					set.getString(ORT), set.getString(EMAIL),
					set.getString(TELEFON), set.getString(FAX), set.getString(NOTIZ), set.getBoolean(MEHRERELIEFERTERMINE));
		}

		return lieferant;
	}
	
	/**
	 * Die Methode liefert ein Ergebnisse zurück bei der Suche
	 * nach einem Lieferant bei ArtikelId in der Datenbank.
	 * 
	 * @param id
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public Lieferant getLieferantByArtikelId(long id) throws ConnectException, DAOException, SQLException{
		Lieferant lieferant = null;
		ResultSet set = getManaged(MessageFormat.format(GET_LIEFERANTEN_BY_ARTIKEL_ID, id));
		while (set.next()) {
			lieferant = new Lieferant(set.getLong(ID), set.getString(NAME),
					set.getString(KUNDENNUMMER), set.getString(BEZEICHNUNG),
					set.getString(STRASSE), set.getString(PLZ),
					set.getString(ORT), set.getString(EMAIL),
					set.getString(TELEFON), set.getString(FAX), set.getString(NOTIZ), set.getBoolean(MEHRERELIEFERTERMINE));
		}
		return lieferant;
	}

	/**
	 * Die Methode erzeugt einen Lieferant in der Datenbank.
	 * 
	 * @param ansprechpartner
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void createLieferant(Lieferant lieferant) throws ConnectException,
			DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(" + NAME + ","
				+ KUNDENNUMMER + "," + BEZEICHNUNG + "," + STRASSE + "," + PLZ
				+ "," + ORT + "," + EMAIL + "," + TELEFON + "," + FAX + "," + NOTIZ + "," + MEHRERELIEFERTERMINE + ")"
				+ "VALUES" + "('" + lieferant.getName() + "','"
				+ lieferant.getKundennummer() + "','"
				+ lieferant.getBezeichnung() + "','" + lieferant.getStrasse()
				+ "','" + lieferant.getPlz() + "','" + lieferant.getOrt()
				+ "','" + lieferant.getEmail() + "','" + lieferant.getTelefon()
				+ "','" + lieferant.getFax() + "','" + lieferant.getNotiz() + "','"+ Util.convertBoolean(lieferant.getMehrereLiefertermine()) + "')";
		this.putManaged(INSERT_QUERY);
	}

	/**
	 * Die Methode aktualisiert einen Lieferant in der Datenbank.
	 * 
	 * @param ansprechpartner
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
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
				+ lieferant.getFax()+ "'," + NOTIZ + "='"
				+ lieferant.getNotiz()+ "'," + MEHRERELIEFERTERMINE	+ "='"
				+ Util.convertBoolean(lieferant.getMehrereLiefertermine()) + "' WHERE " + ID + "='"
				+ lieferant.getId() + "'";
		this.putManaged(UPDATE_QUERY);
	}
	
}
