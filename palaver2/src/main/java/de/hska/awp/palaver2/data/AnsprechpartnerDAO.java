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

import de.hska.awp.palaver2.lieferantenverwaltung.domain.Ansprechpartner;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;

/**
 * Klasse AnsprechpartnerDAO. Die Klasse stellt für den Ansprechpartner alle
 * notwendigen Methoden bereit um auf die Datenbank zuzugreifen.
 * 
 * @author Christian Barth
 */
public class AnsprechpartnerDAO extends AbstractDAO {

	private static AnsprechpartnerDAO instance = null;

	private final static String TABLE = "ansprechpartner";
	private final static String ID = "id";
	private final static String NAME = "name";
	private final static String TELEFON = "telefon";
	private final static String HANDY = "handy";
	private final static String FAX = "fax";
	private final static String LIEFERANT_FK = "lieferant_fk";
	private final static String GET_ALL_ANSPRECHPARTNER = "SELECT * FROM "
			+ TABLE;
	private static final String GET_ANSPRECHPARTNER_BY_ID = "SELECT * FROM "
			+ TABLE + " WHERE " + ID + "= {0}";
	private static final String GET_ANSPRECHPARTNER_BY_NAME = "SELECT * FROM "
			+ TABLE + " WHERE " + NAME + " LIKE"+" '%";
	private static final String DELETE_NACHRICHT = "DELETE FROM " + TABLE
			+ " WHERE id = {0}";
	private static final String GET_ANSPRECHPARTNER_BY_LIEFERANT = "SELECT * FROM " 
			+ TABLE + " WHERE " + LIEFERANT_FK + "=";

	public AnsprechpartnerDAO() {
		super();
	}

	/**
	 * @return instance
	 */
	public static AnsprechpartnerDAO getInstance() {
		if (instance == null) {
			instance = new AnsprechpartnerDAO();
		}
		return instance;
	}

	/**
	 * Die Methode getAllAnsprechpartner liefert alle in der Datenbank
	 * befindlichen Ansprechpartner zurück.
	 * 
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Ansprechpartner> getAllAnsprechpartner()
			throws ConnectException, DAOException, SQLException {
		List<Ansprechpartner> list = new ArrayList<Ansprechpartner>();
		ResultSet set = get(GET_ALL_ANSPRECHPARTNER);
		while (set.next()) {
			list.add(new Ansprechpartner(set.getLong(ID),
					set.getString("name"), set.getString("telefon"), set
							.getString("handy"), set.getString("fax"),
					LieferantDAO.getInstance().getLieferantById(
							set.getLong("lieferant_fk"))));
		}
		return list;
	}

	/**
	 * Die Methode getAnsprechpartnerByName liefert eins bis mehrere Ergebnisse
	 * zurück bei der Suche nach einem Ansprechpartner in der Datenbank.
	 * 
	 * @param name
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Ansprechpartner> getAnsprechpartnerByName(String name)
			throws ConnectException, DAOException, SQLException {
		List<Ansprechpartner> list = new ArrayList<Ansprechpartner>();

		ResultSet set = get(GET_ANSPRECHPARTNER_BY_NAME + name + "%'");

		while (set.next()) {
			list.add(new Ansprechpartner(set.getLong(ID), set.getString(NAME),
					set.getString(TELEFON), set.getString(HANDY), set
							.getString(FAX), LieferantDAO.getInstance()
							.getLieferantById(set.getLong(LIEFERANT_FK))));
		}

		return list;
	}

	/**
	 * Die Methode getAnsprechpartnerById liefert eins Ergebnisse zurück bei der
	 * Suche nach einem Ansprechpartner in der Datenbank.
	 * 
	 * @param id
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public Ansprechpartner getAnsprechpartnerById(Long id)
			throws ConnectException, DAOException, SQLException {

		Ansprechpartner ansprechpartner = null;
		ResultSet set = get(MessageFormat.format(GET_ANSPRECHPARTNER_BY_ID, id));

		while (set.next()) {
			ansprechpartner = new Ansprechpartner(set.getLong(ID),
					set.getString(NAME), set.getString(TELEFON),
					set.getString(HANDY), set.getString(FAX), LieferantDAO
							.getInstance().getLieferantById(
									set.getLong(LIEFERANT_FK)));
		}

		return ansprechpartner;
	}

	/**
	 * Die Methode erzeugt einen Ansprechpartner in der Datenbank.
	 * 
	 * @param ansprechpartner
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void createAnsprechpartner(Ansprechpartner ansprechpartner)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(" + NAME + ","
				+ TELEFON + "," + HANDY + "," + FAX + "," + LIEFERANT_FK + ")"
				+ "VALUES" + "('" + ansprechpartner.getName() + "','"
				+ ansprechpartner.getTelefon() + "','"
				+ ansprechpartner.getHandy() + "','" + ansprechpartner.getFax()
				+ "','" + ansprechpartner.getLieferant().getId() + "')";
		this.put(INSERT_QUERY);
	}

	/**
	 * Die Methode aktualisiert einen Ansprechpartner in der Datenbank.
	 * 
	 * @param ansprechpartner
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void updateAnsprechpartner(Ansprechpartner ansprechpartner)
			throws ConnectException, DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + NAME + "='"
				+ ansprechpartner.getName() + "'," + TELEFON + "='"
				+ ansprechpartner.getTelefon() + "'," + HANDY + "='"
				+ ansprechpartner.getHandy() + "'," + FAX + "='"
				+ ansprechpartner.getFax() + "'," + LIEFERANT_FK + "='"
				+ ansprechpartner.getLieferant().getId() + "'" + "WHERE " + ID
				+ "='" + ansprechpartner.getId() + "'";
		this.put(UPDATE_QUERY);
	}

	/**
	 * Die Methode löscht einen Ansprechpartner in der Datenbank.
	 * 
	 * @param id
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void deleteAnsprechpartner(Long id) throws ConnectException,
			DAOException, SQLException {

		if (id == null) {
			throw new NullPointerException("kein Ansprechpartner übergeben");
		}
		put(MessageFormat.format(DELETE_NACHRICHT, id));
	}

	public List<Ansprechpartner> getAnsprechpartnerByLieferant(Lieferant lieferant) 
			throws ConnectException, DAOException, SQLException {
		List<Ansprechpartner> list = new ArrayList<Ansprechpartner>();

		ResultSet set = get(GET_ANSPRECHPARTNER_BY_LIEFERANT + lieferant.getId());

		while (set.next()) {
			list.add(new Ansprechpartner(set.getLong(ID), set.getString(NAME),
					set.getString(TELEFON), set.getString(HANDY), set
							.getString(FAX), LieferantDAO.getInstance()
							.getLieferantById(set.getLong(LIEFERANT_FK))));
		}

		return list;
	}
}
