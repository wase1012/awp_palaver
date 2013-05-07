/**
 * Created by Elena W
 */
package de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellposition;

/**
 * Klasse BestellungpositionDAO. Die Klasse stellt für die Bestellung alle notwendigen
 * Methoden bereit um auf die Datenbank zuzugreifen.
 * 
 * @author Elena W
 * 
 */
public class BestellpositionDAO extends AbstractDAO{
	
	
	private static BestellpositionDAO instance = null;

	private final static String TABLE = "bestellposition";
	private final static String ID = "id";
	private final static String ARTIKEL_FK = "artikel_fk";
	private final static String BESTELLUNG_FK = "bestellung_fk";
	private final static String DURCHSCHNITT = "durchschnitt";
	private final static String KANTINE = "kantine";
	private final static String GESAMT = "gesamt";
	private final static String FREITAG = "freitag";
	private final static String MONTAG = "montag";
	private final static String LIEFERDATUM = "lieferdatum";

	private static final String GET_BESTELLPOSITION_BY_ID = "SELECT * FROM " + TABLE
			+ " WHERE " + ID + "= {0}";
	
	private static final String GET_BESTELLPOSITIONEN_BY_BESTELLUNGID = "SELECT * FROM " + TABLE
			+ " WHERE " + BESTELLUNG_FK + "= {0}";

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
	 * Die Methode getBestellpositionById liefert ein Ergebniss zurück bei der Suche
	 * nach einer Bestellposition in der Datenbank.
	 * 
	 * @param id
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public Bestellposition getBestellpositionById(Long id) throws ConnectException,
			DAOException, SQLException {

		Bestellposition bp = null;
		ResultSet set = getManaged(MessageFormat.format(GET_BESTELLPOSITION_BY_ID, id));

		while (set.next()) {
			bp = new Bestellposition(set.getLong(ID),
					ArtikelDAO.getInstance().getArtikelById(set.getLong(ARTIKEL_FK)),
					BestellungDAO.getInstance().getBestellungById(set.getLong(BESTELLUNG_FK)),
					set.getInt(DURCHSCHNITT), set.getInt(KANTINE), set.getInt(GESAMT), set.getInt(FREITAG), set.getInt(MONTAG), set.getDate(LIEFERDATUM));
		}

		return bp;
	}
	
	/**
	 * Die Methode getBestellpositionenByBestellungId liefert ein Ergebniss zurück bei der Suche
	 * nach einer Bestellposition anhang der BestellungId in der Datenbank.
	 * 
	 * @param id
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Bestellposition> getBestellpositionenByBestellungId(Long id)
			throws ConnectException, DAOException, SQLException {
		List<Bestellposition> list = new ArrayList<Bestellposition>();

		ResultSet set = getManaged(MessageFormat.format(GET_BESTELLPOSITIONEN_BY_BESTELLUNGID, id));

		while (set.next()) {
			list.add(new Bestellposition(set.getLong(ID),
					ArtikelDAO.getInstance().getArtikelById(set.getLong(ARTIKEL_FK)),
					BestellungDAO.getInstance().getBestellungById(set.getLong(BESTELLUNG_FK)),
					set.getInt(DURCHSCHNITT), set.getInt(KANTINE), set.getInt(GESAMT), set.getInt(FREITAG),set.getInt(MONTAG),set.getDate(LIEFERDATUM)));
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
	 * @throws ParseException 
	 */
	public void createBestellposition(Bestellposition bestellposition) throws ConnectException,
			DAOException, SQLException, ParseException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "("
				+ ARTIKEL_FK + "," + BESTELLUNG_FK + "," + DURCHSCHNITT + "," + KANTINE + "," + GESAMT + "," + FREITAG + "," + MONTAG + "," + LIEFERDATUM + ")"
				+ "VALUES" + "('" + bestellposition.getArtikel().getId() + "','"
				+ bestellposition.getBestellung().getId() + "','"
				+ bestellposition.getDurchschnitt() + "','"
				+ bestellposition.getKantine() + "','"
				+ bestellposition.getGesamt() + "','"
				+ bestellposition.getFreitag() + "','"
				+ bestellposition.getMontag() + "','"
				+ bestellposition.getLieferdatum() + "')";
		this.putManaged(INSERT_QUERY);
	}

	
	/**
	 * Die Methode aktualisiert eine Bestellposition in der Datenbank.
	 * 
	 * @param bestellposition
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	/* 
	 * änderung von Mihail Boehm: Anführungszeichen wurde gelöscht, 
	 * jetzt Update funktioniert
	 */
	public void updateBestellposition(Bestellposition bestellposition)
			throws ConnectException, DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + ARTIKEL_FK + "='"
				+ bestellposition.getArtikel().getId() + "'," + BESTELLUNG_FK
				+ "='" + bestellposition.getBestellung().getId() + "',"
				+ DURCHSCHNITT + "='" + bestellposition.getDurchschnitt()
				+ "'," + KANTINE + "='" + bestellposition.getKantine() + "',"
				+ GESAMT + "='" + bestellposition.getGesamt() + "'," + FREITAG
				+ "='" + bestellposition.getFreitag() + "'," + MONTAG + "='"
				+ bestellposition.getMontag() + "'," + LIEFERDATUM + "='"
				+ bestellposition.getLieferdatum() + "'" + " WHERE " + ID
				+ "='" + bestellposition.getId() + "'";
		this.putManaged(UPDATE_QUERY);
	}
}
