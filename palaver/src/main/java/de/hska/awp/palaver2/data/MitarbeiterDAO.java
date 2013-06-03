package de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Rollen;
import de.hska.awp.palaver2.nachrichtenverwaltung.domain.Nachricht;

/**
 * Die Klasse stellt Methoden für den Datenbankzugriff für das Objekt
 * Mitarbeiter bereit.
 * 
 * @Author Christian Barth
 */
public class MitarbeiterDAO extends AbstractDAO {

	private final static String TABLE = "mitarbeiter";
	private final static String ID = "id";
	private final static String NAME = "name";
	private final static String VORNAME = "vorname";
	private final static String EMAIL = "email";
	private final static String PASSWORT = "passwort";
	private final static String EINTRITTSDATUM = "eintrittsdatum";
	private final static String AUSTRITTSDATUM = "austrittsdatum";
	private final static String BENUTZERNAME = "benutzername";

	private static final String GET_ALL_MITARBEITER = "SELECT * FROM mitarbeiter";
	private static final String GET_MITARBEITER_BY_ID = "SELECT * FROM mitarbeiter WHERE id = {0}";
	private static final String GET_MITARBEITER_BY_NAME = "SELECT * FROM " + TABLE + " WHERE " + NAME + " LIKE" + " '%";
	private static final String GET_MITARBEITER_BY_ROLLEN_ID = "SELECT * FROM mitarbeiter "
			+ "JOIN mitarbeiter_has_rollen on mitarbeiter.id = mitarbeiter_has_rollen.mitarbeiter_fk "
			+ "join rollen on mitarbeiter_has_rollen.rollen_fk = rollen.id where rollen.id = {0}";
	private static final String GET_MITARBEITER_BY_BENUTZERNAME = "SELECT * FROM " + TABLE + " WHERE " + BENUTZERNAME + " = '";

	private static final String GET_NACHRICHT_BY_ROLLE_ID = "SELECT * FROM nachrichten WHERE empf_rolle_fk = {0}";
	private final static String GET_ROLLEN_BY_MITARBEITER_ID = "SELECT rollen.id, rollen.name FROM rollen join mitarbeiter_has_rollen on "
			+ "rollen.id = mitarbeiter_has_rollen.rollen_fk where mitarbeiter_fk = {0}";

	private static MitarbeiterDAO instance = null;

	public static MitarbeiterDAO getInstance() {
		if (instance == null) {
			instance = new MitarbeiterDAO();
		}
		return instance;
	}

	public MitarbeiterDAO() {
		super();
	}

	/**
	 * Die Methode liefert alle Mitarbeiter aus der Datenbank zurück. author
	 * Christian Barth
	 * 
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Mitarbeiter> getAllMitarbeiter() throws ConnectException, DAOException, SQLException {
		List<Mitarbeiter> list = new ArrayList<Mitarbeiter>();
		ResultSet set = getManaged(GET_ALL_MITARBEITER);
		while (set.next()) {
			list.add(new Mitarbeiter(set.getLong("id"), set.getString("name"), set.getString("vorname"), set.getString("email"), set
					.getString("passwort"), set.getString("eintrittsdatum"), set.getString("austrittsdatum"), RollenDAO.getInstance()
					.getRollenByMitarbeiterId(set.getLong("id")), set.getString("benutzername")));
		}
		return list;
	}

	/**
	 * Die Methode liefert ein Mitarbeiter anhand seiner ID zurück.
	 * 
	 * @author Christian Barth
	 * @param id
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public Mitarbeiter getMitarbeiterById(Long id) throws ConnectException, DAOException, SQLException {

		if (id == null) {
			return null;
		}
		Mitarbeiter mitarbeiter = null;
		ResultSet set = getManaged(MessageFormat.format(GET_MITARBEITER_BY_ID, id));

		while (set.next()) {
			mitarbeiter = new Mitarbeiter(set.getLong("id"), set.getString("name"), set.getString("vorname"), set.getString("email"),
					set.getString("passwort"), set.getString("eintrittsdatum"), set.getString("austrittsdatum"),
					getRollenByMitarbeiterId(set.getLong("id")), set.getString("benutzername"));
		}

		return mitarbeiter;
	}

	/**
	 * Die Methode liefer alle Mitarbeiter anhand einer Rollen ID zurück.
	 * 
	 * @param id
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Mitarbeiter> getMitarbeiterByRollenId(Long id) throws ConnectException, DAOException, SQLException {

		List<Mitarbeiter> list = new ArrayList<Mitarbeiter>();

		ResultSet set = getManaged(MessageFormat.format(GET_MITARBEITER_BY_ROLLEN_ID, id));

		while (set.next()) {
			list.add(new Mitarbeiter(set.getLong("id"), set.getString("name"), set.getString("vorname"), set.getString("email"), set
					.getString("passwort"), set.getString("eintrittsdatum"), set.getString("austrittsdatum"), set.getString("benutzername")));

		}

		return list;

	}

	/**
	 * Die Methode liefer alle Mitarbeiter anhand einer Rollen ID zurück.
	 * 
	 * @param id
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public Mitarbeiter getMitarbeiterByIdForNachricht(Long id) throws ConnectException, DAOException, SQLException {

		if (id == null) {
			return null;
		}
		Mitarbeiter mitarbeiter = null;
		ResultSet set = getManaged(MessageFormat.format(GET_MITARBEITER_BY_ID, id));

		while (set.next()) {
			mitarbeiter = new Mitarbeiter(set.getLong("id"), set.getString("name"), set.getString("vorname"), set.getString("email"),
					set.getString("passwort"), set.getString("eintrittsdatum"), set.getString("austrittsdatum"), set.getString("benutzername"));
		}
		return mitarbeiter;

	}

	/**
	 * Die Methode liefert einen Mitarbeiter anhand seines Namen zurück.
	 * 
	 * @param name
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Mitarbeiter> getMitarbeiterByName(String name) throws ConnectException, DAOException, SQLException {

		List<Mitarbeiter> list = new ArrayList<Mitarbeiter>();

		ResultSet set = getManaged(GET_MITARBEITER_BY_NAME + name + "%'");

		while (set.next()) {
			list.add(new Mitarbeiter(set.getLong(ID), set.getString(NAME), set.getString(VORNAME), set.getString(EMAIL), set.getString(PASSWORT),
					set.getString(EINTRITTSDATUM), set.getString(AUSTRITTSDATUM), RollenDAO.getInstance().getRollenByMitarbeiterId(set.getLong(ID)),
					set.getString(BENUTZERNAME)));
		}

		return list;
	}

	/**
	 * Die Methode liefert einen Mitarbeiter anhand seines Benutzernamen zurück.
	 * 
	 * @param name
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public Mitarbeiter getMitarbeiterByBenutzername(String name) throws ConnectException, DAOException, SQLException {

		Mitarbeiter mitarbeiter = new Mitarbeiter();

		ResultSet set = getManaged(GET_MITARBEITER_BY_BENUTZERNAME + name + "'");

		while (set.next()) {
			mitarbeiter = new Mitarbeiter(set.getLong(ID), set.getString(NAME), set.getString(VORNAME), set.getString(EMAIL),
					set.getString(PASSWORT), set.getString(EINTRITTSDATUM), set.getString(AUSTRITTSDATUM),
					getRollenByMitarbeiterId(set.getLong(ID)), set.getString(BENUTZERNAME));
		}

		return mitarbeiter;
	}

	/**
	 * Die Methode erzeugt einen Mitarbeiter in der Datenbank.
	 * 
	 * @param mitarbeiter
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void createMitarbeiter(Mitarbeiter mitarbeiter) throws ConnectException, DAOException, SQLException {
		String INSERTQUERY = "INSERT INTO " + TABLE + "(" + NAME + "," + VORNAME + "," + EMAIL + "," + PASSWORT + "," + EINTRITTSDATUM + ","
				+ AUSTRITTSDATUM + "," + BENUTZERNAME + ")" + "VALUES" + "('" + mitarbeiter.getName() + "','" + mitarbeiter.getVorname() + "','"
				+ mitarbeiter.getEmail() + "','" + mitarbeiter.getPasswort() + "','" + mitarbeiter.getEintrittsdatum() + "','"
				+ mitarbeiter.getAustrittsdatum() + "','" + mitarbeiter.getBenutzername() + "')";
		this.putManaged(INSERTQUERY);

		List<Mitarbeiter> mitarbeiterlist = getAllMitarbeiter();

		Mitarbeiter mitarbeiterdb = getMitarbeiterById(mitarbeiterlist.get(mitarbeiterlist.size() - 1).getId());

		if (mitarbeiter.getRollen() != null) {
			for (int i = 0; i < mitarbeiter.getRollen().size(); i++) {

				MitarbeiterHasRollenDAO.getInstance().createMitarbeiterHasRollen(mitarbeiterdb, mitarbeiter.getRollen().get(i));

			}
		}
	}

	/**
	 * Die Methode aktualisiert einen Mitarbeiter mitsamt seiner dazugehörigen
	 * Rollen in der Datenbank.
	 * 
	 * @param mitarbeiter
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void updateMitarbeiter(Mitarbeiter mitarbeiter) throws ConnectException, DAOException, SQLException {
		String UPDATEQUERY = "UPDATE " + TABLE + " SET " + NAME + "='" + mitarbeiter.getName() + "'," + VORNAME + "='" + mitarbeiter.getVorname()
				+ "'," + EMAIL + "='" + mitarbeiter.getEmail() + "'," + PASSWORT + "='" + mitarbeiter.getPasswort() + "'," + EINTRITTSDATUM + "='"
				+ mitarbeiter.getEintrittsdatum() + "'," + AUSTRITTSDATUM + "='" + mitarbeiter.getAustrittsdatum() + "'," + BENUTZERNAME + "='"
				+ mitarbeiter.getBenutzername() + "' WHERE " + ID + "='" + mitarbeiter.getId() + "'";
		this.putManaged(UPDATEQUERY);
		if (mitarbeiter.getRollen() != null) {
			for (int i = 0; i < mitarbeiter.getRollen().size(); i++) {
				MitarbeiterHasRollenDAO.getInstance().deleteMitarbeiterHasRollen(mitarbeiter);

			}
			for (int i = 0; i < mitarbeiter.getRollen().size(); i++) {
				MitarbeiterHasRollenDAO.getInstance().createMitarbeiterHasRollen(mitarbeiter, mitarbeiter.getRollen().get(i));
			}
		}
	}

	/**
	 * Die Methode liefert die Rollen zur einer Mitarbeiter ID zurück.
	 * 
	 * @param id
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	private List<Rollen> getRollenByMitarbeiterId(Long id) throws ConnectException, DAOException, SQLException {

		List<Rollen> list = new ArrayList<Rollen>();

		ResultSet set = getManaged(MessageFormat.format(GET_ROLLEN_BY_MITARBEITER_ID, id));

		while (set.next()) {
			list.add(new Rollen(set.getLong("id"), set.getString("name"), getNachrichtByRolleId(set.getLong("id"))));

		}

		return list;

	}

	/**
	 * Methode um alle Nachrichten zu einer RolleId zu bekommen.
	 * 
	 * @param rolle
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	private List<Nachricht> getNachrichtByRolleId(Long rid) throws ConnectException, DAOException, SQLException {

		List<Nachricht> list = new ArrayList<Nachricht>();

		ResultSet set = getManaged(MessageFormat.format(GET_NACHRICHT_BY_ROLLE_ID, rid));

		while (set.next()) {
			list.add(new Nachricht(set.getLong("id"), set.getString("nachricht"), getMitarbeiterByIdForNachricht(set.getLong("sender_fk")),
					new Rollen()));
		}

		return list;
	}
}
