/**
 * 
 */
package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.artikelverwaltung.domain.Kategorie;

/**
 * @author Android
 * 
 */
public class KategorieDAO extends AbstractDAO {

	private final static String ID = "id";
	private final static String NAME = "name";

	private static KategorieDAO instance = null;
	Kategorie kategorie;
	private final static String TABLE = "kategorie";
	private final static String GET_ALL_KATEGORIES = "SELECT * FROM " + TABLE;
	private final static String GET_KATEGORIE_BY_ID = "SELECT * FROM " + TABLE
			+ " WHERE " + ID + "= {0}";

	/**
	 * Konstruktor
	 * 
	 * @author Mihail Boehm
	 * @datum 18.04.2013
	 */
	public KategorieDAO() {
		super();
	}

	/**
	 * @return instance
	 */
	public static KategorieDAO getInstance() {
		if (instance == null) {
			instance = new KategorieDAO();
		}
		return instance;
	}

	/**
	 * Die Methode getAllKategories liefert alle in der Datenbank befindlichen
	 * Kategorien zurück.
	 * 
	 * 
	 * @author Mihail Boehm
	 * @return KategorieListe
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 * @datum 18.04.2013
	 */
	public List<Kategorie> getAllKategories() throws ConnectException,
			DAOException, SQLException {
		List<Kategorie> list = new ArrayList<Kategorie>();
		ResultSet set = get(GET_ALL_KATEGORIES);
		while (set.next()) {
			list.add(new Kategorie(set.getLong(ID), set.getString(NAME)));
		}
		return list;
	}

	/**
	 * Die Methode getKategorieById liefert ein Ergebniss zurück bei der Suche
	 * nach einer Kategorie in der Datenbank.
	 * 
	 * 
	 * @author Mihail Boehm
	 * @param id
	 * @return kategorie (id + name)
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 * @datum 27.04.2013
	 */
	public Kategorie getKategorieById(Long id) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = get(MessageFormat.format(GET_KATEGORIE_BY_ID, id));
		while (set.next()) {
			kategorie = new Kategorie(set.getLong(ID), set.getString(NAME));
		}
		return kategorie;
	}

	/**
	 * Die Methode erzeugt eine Kategorie in der Datenbank.
	 * 
	 * @author Mihail Boehm
	 * @param kategorie
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 * @datum 19.04.2013
	 */
	public void createNewKategorie(Kategorie kategorie)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(name) VALUES('"
				+ kategorie.getName() + "')";
		this.put(INSERT_QUERY);
	}

	/**
	 * Die Methode aktualisiert eine Kategorie in der Datenbank.
	 * 
	 * @author Mihail Boehm
	 * @param kategorie
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 * @datum 19.04.2013
	 */
	public void updateKategorie(Kategorie kategorie) throws ConnectException,
			DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET name='"
				+ kategorie.getName() + "' WHERE id=" + kategorie.getId() + "";
		this.put(UPDATE_QUERY);
	}
}