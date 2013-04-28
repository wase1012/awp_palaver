package de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;



import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellung;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;

/**
 * Klasse BestellungDAO. Die Klasse stellt für die Bestellung alle notwendigen
 * Methoden bereit um auf die Datenbank zuzugreifen.
 * 
 * @author Elena W
 * 
 */
public class BestellungDAO extends AbstractDAO {

	private static BestellungDAO instance = null;
	private final static String TABLE = "bestellung";
	private final static String GET_ALL_BESTELLUNGEN = "SELECT * FROM " + TABLE;
	private static final String PREFIX = "Bestellung.";
	public static final String FIND_BESTELLUNG_BY_ID = PREFIX
			+ "findBestellungById";
	public static final String ID = "id";
	private final static String LIEFERANT_FK = "lieferant_fk";
	 Date datumAktuell = new Date();
     SimpleDateFormat myDateFormat = new SimpleDateFormat("dd.MM.yyyy (hh.mm");
     String datum = myDateFormat.format(datumAktuell)+ " Uhr)";
   

	public BestellungDAO() {
		super();
	}
	
	/**
	 * @return instance 
	 */
	public static BestellungDAO getInstance() {
		if (instance == null) {
			instance = new BestellungDAO();
		}
		return instance;
	}
	
	/**
	 * Die Methode getAllBestellungen liefert alle in der Datenbank befindlichen
	 * Bestellungen zurück.
	 * 
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Bestellung> getAllBestellungen() throws ConnectException, 
	DAOException, SQLException {
		List<Bestellung> list = new ArrayList<Bestellung>();
		ResultSet set = get(GET_ALL_BESTELLUNGEN);
		while (set.next()) {
		list.add(new Bestellung(set.getLong(ID), LieferantDAO.getInstance().getLieferantById(
				set.getLong(LIEFERANT_FK)), set.getDate(datum)));
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
	public Bestellung findBestellungById(Long id) throws ConnectException,
			DAOException, SQLException {

		Bestellung bestellung = null;
		ResultSet set = get(MessageFormat.format(FIND_BESTELLUNG_BY_ID, id));

		while (set.next()) {
			bestellung = new Bestellung(set.getLong(ID), LieferantDAO.getInstance().getLieferantById(
					set.getLong(LIEFERANT_FK)), set.getDate(datum));
		}

		return bestellung;
	}
	/**
 * Die Methode erzeugt eine BEstellung in der Datenbank.
 *
 * @param bestellung
 * @throws ConnectException 
 * @throws DAOException 
 * @throws SQLException 
 */
public void createNewBestellung(Bestellung bestellung) throws ConnectException, 
	DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(lieferant_fk, datum) VALUES('"
				+ bestellung.getLieferant() + bestellung.getDatum() +  "')";
		this.put(INSERT_QUERY);
	}
	
	/**
	  * Die Methode aktualisiert eine Bestellung in der Datenbank.
	 *
	 * @param bestellung 
	 * @throws ConnectException 
	 * @throws DAOException 
	 * @throws SQLException 
	 */
	public void updateBestellung(Bestellung bestellung) throws ConnectException, 
	DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET lieferant='" 
		+ bestellung.getLieferant()+ "', datum='"+ bestellung.getDatum()
		+ "' WHERE id=" + bestellung.getId() + "";
		this.put(UPDATE_QUERY);
	}

	
}
