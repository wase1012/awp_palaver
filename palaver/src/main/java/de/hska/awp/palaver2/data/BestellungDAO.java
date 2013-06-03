package de.hska.awp.palaver2.data;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellposition;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellung;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.util.Util;

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
	private final static String ID = "id";
	private final static String LIEFERANT_FK = "lieferant_fk";
	private final static String DATUM = "datum";
	private final static String LIEFERDATUM = "lieferdatum";
	private final static String LIEFERDATUM2 = "lieferdatum2";
	private final static String BESTELLT = "bestellt";
	
	private final static String TABLE2 = "bestellposition";
	private final static String ARTIKEL_FK = "artikel_fk";
	private final static String BESTELLUNG_FK = "bestellung_fk";
	private final static String DURCHSCHNITT = "durchschnitt";
	private final static String KANTINE = "kantine";
	private final static String GESAMT = "gesamt";
	private final static String FREITAG = "freitag";
	private final static String MONTAG = "montag";
	private final static String GELIEFERT = "geliefert";
	
	private final static String GET_LIEFERANT_BY_ID = "SELECT * FROM lieferant WHERE id = {0}";
	private static final String GET_BESTELLPOSITIONEN_BY_BESTELLUNGID = "SELECT * FROM " + TABLE2
			+ " WHERE " + BESTELLUNG_FK + "= {0}";
	
	private final static String GET_ALL_BESTELLUNGEN = "SELECT * FROM " + TABLE;
	private final static String GET_BESTELLUNG_BY_ID = "SELECT * FROM " + TABLE
			+ " WHERE " + ID + "= {0}";
	private final static String GET_ALL_BESTELLUNGEN_NOT_ORDERED = "SELECT * FROM " + TABLE
			+ " WHERE " + BESTELLT + "= '0'";

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
	 * Bestellungen zurück ohne Bestellpositionen.
	 * 
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Bestellung> getAllBestellungen() throws ConnectException,
			DAOException, SQLException {
		List<Bestellung> list = new ArrayList<Bestellung>();
		ResultSet set = getManaged(GET_ALL_BESTELLUNGEN);
		while (set.next()) {
			list.add(new Bestellung(set.getLong(ID), LieferantDAO.getInstance()
					.getLieferantById(set.getLong(LIEFERANT_FK)), set
					.getDate(DATUM), set.getDate(LIEFERDATUM),set.getDate(LIEFERDATUM2),
					set.getBoolean(BESTELLT)));
		}
		return list;
	}
	
	
	/**
	 * Die Methode liefert alle nicht bestellten Bestellungen zurück
	 * 
	 * @author Christian Barth
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Bestellung> getAllBestellungenNotOrdered() throws ConnectException,
			DAOException, SQLException {
		List<Bestellung> list = new ArrayList<Bestellung>();
		ResultSet set = getManaged(GET_ALL_BESTELLUNGEN_NOT_ORDERED);
		while (set.next()) {
			list.add(new Bestellung(set.getLong(ID), LieferantDAO.getInstance()
					.getLieferantById(set.getLong(LIEFERANT_FK)), set
					.getDate(DATUM), set.getDate(LIEFERDATUM), set
					.getDate(LIEFERDATUM2), set.getBoolean(BESTELLT)));
		}
		return list;
	}
	
	
	public List<Bestellung> getBestellungenLTWeeks() throws ConnectException, DAOException, SQLException {
		
		List<Bestellung> list = new ArrayList<Bestellung>();
		

		java.util.Date date2 = new java.util.Date();
		Date date = new Date(date2.getTime());
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -22);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		df.format(cal.getTime());
		
		String GET_BESTELLUNGENLTWEEKS = "select * from bestellung where datum > '" + df.format(cal.getTime()) + "'";
		System.out.println(GET_BESTELLUNGENLTWEEKS);
		ResultSet set = getManaged(GET_BESTELLUNGENLTWEEKS);
		while (set.next()) {
			list.add(new Bestellung(set.getLong(ID), LieferantDAO.getInstance()
					.getLieferantById(set.getLong(LIEFERANT_FK)), set
					.getDate(DATUM), set.getDate(LIEFERDATUM),set.getDate(LIEFERDATUM2),
					set.getBoolean(BESTELLT)));
		}
		return list;
	}

	/**
	 * Die Methode getBestellungById liefert ein Ergebnisse zurück bei der Suche
	 * nach einer Bestellung in der Datenbank inklusive Bestellpositionen.
	 * 
	 * @param id
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public Bestellung getBestellungById(Long id) throws ConnectException,
			DAOException, SQLException {

		Bestellung bestellung = null;
		ResultSet set = getManaged(MessageFormat.format(GET_BESTELLUNG_BY_ID,
				id));
		
		openConnection();
		
		while (set.next()) {
			bestellung = new Bestellung(set.getLong(ID), 
					getLieferantById(set.getLong(LIEFERANT_FK)),
					set.getDate(DATUM), 
					set.getDate(LIEFERDATUM), 
					set.getDate(LIEFERDATUM2),
					getBestellpositionen(set.getLong(ID)),
					set.getBoolean(BESTELLT));
		}
		closeConnection();
				
		return bestellung;
	}
	
	/**
	 * Die Methode getBestellungById liefert ein Ergebnisse zurück bei der Suche
	 * nach einer Bestellung in der Datenbank ohne Bestellpositionen.
	 * 
	 * @param id
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public Bestellung getBestellungByIdWithoutBP(Long id) throws ConnectException,
			DAOException, SQLException {

		Bestellung bestellung = null;
		ResultSet set = getManaged(MessageFormat.format(GET_BESTELLUNG_BY_ID,
				id));
		
		openConnection();
		
		while (set.next()) {
			bestellung = new Bestellung(set.getLong(ID), 
					getLieferantById(set.getLong(LIEFERANT_FK)),
					set.getDate(DATUM), 
					set.getDate(LIEFERDATUM), 
					set.getDate(LIEFERDATUM2),
					set.getBoolean(BESTELLT));
		}
		
		closeConnection();
		
		return bestellung;
	}

	/**
	 * Die Methode erzeugt eine Bestellung in der Datenbank inklusive den Bestellpositionen.
	 * 
	 * @param bestellung
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 * @throws ParseException 
	 */
	public void createBestellung(Bestellung bestellung)
			throws ConnectException, DAOException, SQLException, ParseException {
		
		if(bestellung.getBestellpositionen().size()== 0){
			return;
		}
		
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(" + LIEFERANT_FK + ","
				+ DATUM  + "," + LIEFERDATUM + ","+ LIEFERDATUM2 + "," + BESTELLT + ")" + "VALUES" + "('"
				+ bestellung.getLieferant().getId() + "','"
				+ bestellung.getDatum() + "','"
				+ bestellung.getLieferdatum() + "','"
				+ bestellung.getLieferdatum2() + "','"
				+ Util.convertBoolean(bestellung.isBestellt())
				+ "')";
		this.putManaged(INSERT_QUERY);
			
		List<Bestellung> bestellungen = getAllBestellungen();
		
		Bestellung bestell = getBestellungById(bestellungen.get(bestellungen.size() - 1).getId());
		
		for(int i = 0 ; i < bestellung.getBestellpositionen().size() ; i++){
			
			bestellung.getBestellpositionen().get(i).setBestellung(bestell);
			BestellpositionDAO.getInstance().createBestellposition(bestellung.getBestellpositionen().get(i));
		}
		
	}

	/**
	 * Die Methode aktualisiert eine Bestellung in der Datenbank.
	 * 
	 * @param bestellung
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 * @throws ParseException 
	 */
	public void updateBestellung(Bestellung bestellung)
			throws ConnectException, DAOException, SQLException, ParseException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + LIEFERANT_FK + "='"
				+ bestellung.getLieferant().getId() + "'," + DATUM + "='"
				+ bestellung.getDatum() + "'," + LIEFERDATUM + "='"
				+ bestellung.getLieferdatum() + "'," + LIEFERDATUM2 + "='"
						+ bestellung.getLieferdatum2() +  "'," + BESTELLT + "='"
				+ Util.convertBoolean(bestellung.isBestellt()) + "' WHERE " + ID + "='"
				+ bestellung.getId() + "'";
		this.putManaged(UPDATE_QUERY);
		
		List<Bestellposition> bplist = BestellpositionDAO.getInstance().getBestellpositionenByBestellungId(bestellung.getId());
		List<Bestellposition> bebplist = null;
		bebplist = bestellung.getBestellpositionen();
		
		for(int i = 0 ; i < bplist.size() ; i++){
			boolean vorhanden = false;
			for(int z = 0 ; z < bebplist.size() ; z++){
				if(bplist.get(i).getArtikel().getId().equals(bebplist.get(z).getArtikel().getId())){
					vorhanden = true;
					bebplist.get(z).setBestellung(bestellung);
					BestellpositionDAO.getInstance().updateBestellposition(bebplist.get(z));
					bebplist.remove(bebplist.get(z));
				}
				
			}
			if(vorhanden == false){
				BestellpositionDAO.getInstance().deleteBestellposition(bplist.get(i).getId());
			}
			
		}

		if(bebplist != null){
			for(Bestellposition bp : bebplist){
				bp.setBestellung(bestellung);
				BestellpositionDAO.getInstance().createBestellposition(bp);
				
			}
			
			
		}
		
	}

	/**
	 * Die Methode aktualisiert eine Bestellung in der Datenbank.
	 * 
	 * @param bestellung
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void updateBestellungOhneBP(Bestellung bestellung)
			throws ConnectException, DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + LIEFERANT_FK + "='"
				+ bestellung.getLieferant().getId() + "'," + DATUM + "='"
				+ bestellung.getDatum() + "'," + LIEFERDATUM + "='"
				+ bestellung.getLieferdatum() + "'," + LIEFERDATUM2 + "='"
						+ bestellung.getLieferdatum2() +  "'," + BESTELLT + "='"
				+ Util.convertBoolean(bestellung.isBestellt()) + "' WHERE " + ID + "='"
				+ bestellung.getId() + "'";
		this.putManaged(UPDATE_QUERY);
	
	}
	
	private Lieferant getLieferantById(Long id) throws SQLException
	{
		Lieferant lieferant = null;
		ResultSet set = get(MessageFormat.format(GET_LIEFERANT_BY_ID, id));

		while (set.next()) 
		{
			lieferant = new Lieferant(set.getLong("id"), set.getString("name"),
			set.getString("kundennummer"), set.getString("bezeichnung"),
			set.getString("strasse"), set.getString("plz"),
			set.getString("ort"), set.getString("email"),
			set.getString("telefon"), set.getString("fax"), set.getString("notiz"), set.getBoolean("mehrereliefertermine"));
		}

			return lieferant;
	}
	
	
	private List<Bestellposition> getBestellpositionen(Long id) throws SQLException, ConnectException, DAOException
	{
		List<Bestellposition> list = new ArrayList<Bestellposition>();

		ResultSet set = getManaged(MessageFormat.format(GET_BESTELLPOSITIONEN_BY_BESTELLUNGID, id));
		
		while (set.next()) {
			list.add(new Bestellposition(set.getLong(ID),
					ArtikelDAO.getInstance().getArtikelById(set.getLong(ARTIKEL_FK)),
					set.getInt(DURCHSCHNITT), set.getInt(KANTINE), set.getInt(GESAMT), set.getInt(FREITAG),set.getInt(MONTAG), set.getBoolean(GELIEFERT)));
		}
		return list;
	}
}
