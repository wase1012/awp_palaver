package de.hska.awp.palaver2.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.lieferantenverwaltung.domain.Ansprechpartner;

/**
 * @author Mihail Boehm
 * @datum 19.04.2013
 * @version 1.0
 */
public class AnsprechpartnerDAO extends AbstractDAO {
	private static AnsprechpartnerDAO instance = null;
	private final static String TABLE = "ansprechpartner";
	private final static String GET_ALL_ANSPRECHPARTNER = "SELECT * FROM "
			+ TABLE;

	/**
	 * Konstruktor
	 * 
	 * @author Mihail Boehm
	 * @datum 19.04.2013
	 */
	private AnsprechpartnerDAO() {
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
	 * @author Mihail Boehm
	 * @return AnsprechpartnerListe
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 * @datum 19.04.2013
	 */
	public List<Ansprechpartner> getAllAnsprechpartner()
			throws ConnectException, DAOException, SQLException {
		List<Ansprechpartner> list = new ArrayList<Ansprechpartner>();
		ResultSet set = get(GET_ALL_ANSPRECHPARTNER);
		while (set.next()) {
			list.add(new Ansprechpartner(set.getLong("id"), set
					.getString("name"), set.getString("telefon"), set
					.getString("fax")));
		}
		return list;
	}

	/**
	 * @author Mihail Boehm
	 * @param ansprechpartner
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 * @datum 19.04.2013
	 */
	public void createNewAnsprechpartner(Ansprechpartner ap)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE
				+ "(name, telefon, fax) VALUES('" + ap.getName() + "', '"
				+ ap.getTelefon() + "', '" + ap.getFax() + "')";
		this.put(INSERT_QUERY);
	}

	/**
	 * @author Mihail Boehm
	 * @param ansprechpartner
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 * @datum 19.04.2013
	 */
	public void updateAnsprechpartner(Ansprechpartner ap)
			throws ConnectException, DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET name='" + ap.getName()
				+ "', telefon='" + ap.getTelefon() + "', fax = '" + ap.getFax()
				+ "' WHERE id=" + ap.getId() + "";
		this.put(UPDATE_QUERY);
	}
}
