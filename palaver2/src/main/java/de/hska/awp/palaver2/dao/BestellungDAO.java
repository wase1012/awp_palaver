/*
 * Elena Weiss
 */
package de.hska.awp.palaver2.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellung;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;

/**
 * The Class BestellungDAO.
 */
public class BestellungDAO extends AbstractDAO {

	/** The instance. */
	private static BestellungDAO instance = null;
	
	/** The Constant TABLE. */
	private final static String TABLE = "bestellung";
	
	/** The Constant GET_ALL_BESTELLUNGEN. */
	private final static String GET_ALL_BESTELLUNGEN = "SELECT * FROM " + TABLE;

	/**
	 * Instantiates a new bestellung dao.
	 */
	private BestellungDAO() {
		super();
	}
	
	/**
	 * Gets the single instance of BestellungDAO.
	 *
	 * @return single instance of BestellungDAO
	 */
	public static BestellungDAO getInstance() {
		if (instance == null) {
			instance = new BestellungDAO();
		}
		return instance;
	}
	
	public List<Bestellung> getAllBestellungen() throws ConnectException, 
	DAOException, SQLException {
		List<Bestellung> list = new ArrayList<Bestellung>();
		ResultSet set = get(GET_ALL_BESTELLUNGEN);
		while (set.next()) {
		list.add(new Bestellung(set.getLong("id"), set.getDate("datum"), null));
	}
			return list;
}
	/**
 * Creates the new bestellung.
 *
 * @param bestellung the bestellung
 * @throws ConnectException the connect exception
 * @throws DAOException the dAO exception
 * @throws SQLException the sQL exception
 */
public void createNewBestellung(Bestellung bestellung) throws ConnectException, 
	DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(datum, lieferant) VALUES('"
				+ bestellung.getDatum() + bestellung.getLieferant() + "')";
		this.put(INSERT_QUERY);
	}
	
	/**
	 * Update bestellung.
	 *
	 * @param bestellung the bestellung
	 * @throws ConnectException the connect exception
	 * @throws DAOException the dAO exception
	 * @throws SQLException the sQL exception
	 */
	public void updateBestellung(Bestellung bestellung) throws ConnectException, 
	DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET datum='"
		+ bestellung.getDatum()+ "', lieferant='"+ bestellung.getLieferant()
		+ "' WHERE id=" + bestellung.getId() + "";
		this.put(UPDATE_QUERY);
	}

	
}
