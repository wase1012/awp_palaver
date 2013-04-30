/**
 * Created by Elena W
 */
package de.hska.awp.palaver2.data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellposition;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellung;

/**
 * Klasse BestellpositionDAO. Die Klasse stellt für die Bestellposition alle notwendigen
 * Methoden bereit um auf die Datenbank zuzugreifen.
 * 
 * @author Elena W
 * 
 */

public class BestellpositionDAO extends AbstractDAO {
	
	private static BestellpositionDAO instance = null;
	private final static String TABLE = "bestellposition";
	private final static String ID = "id";
	private final static String ARTIKEL_FK = "artikel_fk";
	private final static String BESTELLUNG_FK = "bestellung_fk";
	private final static String MENGE = "menge";
	
	
	
	private final static String GET_ALL_BESTELLPOSITIONEN = "SELECT * FROM " + TABLE;
	private final static String GET_BESTELLPOSITIONEN_BY_ID = "SELECT * FROM " + TABLE
			+ " WHERE " + ID + "= {0}";

	public BestellpositionDAO() {
		super();
	}

	/**
	 * @return instance
	 */
	public static BestellpositionDAO getInstance() {
		if (instance == null) {
			instance = new BestellpositionDAO();
		}
		return instance;
	}
	
	/**
	 * Die Methode getAllBestellpositionen liefert alle in der Datenbank befindlichen
	 * Bestellungen zurück.
	 * 
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Bestellposition> getAllBestellpositionen() throws ConnectException,
			DAOException, SQLException {
		List<Bestellposition> list = new ArrayList<Bestellposition>();
		ResultSet set = get(GET_ALL_BESTELLPOSITIONEN);
		while (set.next()) {
			list.add(new Bestellposition(set.getLong(ID), set.getInt(MENGE), ArtikelDAO.getInstance()
					.getArtikelById(set.getLong(ARTIKEL_FK)), BestellungDAO.getInstance()
					.getBestellungById(set.getLong(BESTELLUNG_FK))));
		}
		return list;
	}
	
		
	/**
	 * Die Methode erzeugt eine BEstellposition in der Datenbank.
	 * 
	 * @param bestellposition
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void createBestellposition(Bestellposition bestellposition)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(" + MENGE + ","
				+ ARTIKEL_FK + "," + BESTELLUNG_FK + ")" + "VALUES" + "('"
				+ bestellposition.getMenge() + "','"
				+ bestellposition.getArtikel().getId() + ","
				+ bestellposition.getBestellung().getId() + "')";
		this.put(INSERT_QUERY);
	}
	
	/**
	 * Die Methode aktualisiert eine Bestellposition in der Datenbank.
	 * 
	 * @param bestellposition
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void updateBestellposition(Bestellposition bestellposition)
			throws ConnectException, DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + MENGE + "='"
				+ bestellposition.getMenge() + "'," + ARTIKEL_FK + "='"
				+ bestellposition.getArtikel().getId() + "'," + BESTELLUNG_FK + "='"
				+ bestellposition.getBestellung().getId() +	"' WHERE " + ID + "='"
				+ bestellposition.getId() + "'";
		this.put(UPDATE_QUERY);
	}

}
