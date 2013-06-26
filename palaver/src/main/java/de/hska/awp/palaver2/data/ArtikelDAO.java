/**
 * 	Sebastian Walz
 */
package de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver2.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.util.Util;

/**
 * Klasse ArtikelDAO. Die Klasse stellt für den Artikel alle notwendigen
 * Methoden bereit um auf die Datenbank zuzugreifen.
 * 
 */
public class ArtikelDAO extends AbstractDAO {
	private static ArtikelDAO instance = null;
	private final static String GET_ALL_ARTIKLES = "SELECT * FROM artikel";
	private final static String GET_ALL_ARTIKLES_BY_LIEFERANT_ID = "SELECT * FROM artikel where lieferant_fk = {0} ORDER BY artikel.name";
	private final static String GET_ARTIKEL_BY_ID = "SELECT * FROM artikel where id = {0}";
	private final static String GET_ARTIKEL_BY_NAME = "SELECT * FROM artikel where name like ";
	private final static String PUT_ARTIKEL = "INSERT INTO artikel(`artikelnr`,`name`,`bestellgroesse`,`mengeneinheit_fk`,`preis`,`lieferant_fk`,`bio`,`kategorie_fk`,`standard`,`grundbedarf`,`durchschnitt`,`lebensmittel`,`notiz`)VALUES({0})";
	private final static String GET_ARTIKEL_BY_GRUNDBEDARF = "SELECT * FROM artikel WHERE grundbedarf=1";
	private final static String GET_ARTIKEL_BY_STANDARDBEDARF = "SELECT * FROM artikel WHERE standard=1";
	private final static String GET_LIEFERANT_BY_ID = "SELECT * FROM lieferant WHERE id = {0}";
	private final static String GET_KATEGORIE_BY_ID = "SELECT * FROM kategorie WHERE id = {0}";
	private final static String GET_MENGENEINHEIT_BY_ID = "SELECT * FROM mengeneinheit WHERE id = {0}";
	private final static String GET_ARTIKEL_BY_LEBENSMITTEL = "SELECT * FROM artikel WHERE lebensmittel = '1'";
	private final static String GET_ALL_ARTIKLES_NAME = "SELECT id, name FROM artikel";

	public ArtikelDAO() {
		super();
	}

	/**
	 * @return instance
	 */
	public static ArtikelDAO getInstance() {
		if (instance == null) {
			instance = new ArtikelDAO();
		}
		return instance;
	}

	/**
	 * Die Methode getAllArtikel liefert alle in der Datenbank befindlichen
	 * Artikeln zurück.
	 * 
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Artikel> getAllArtikel() throws ConnectException, DAOException, SQLException {
		List<Artikel> list = new ArrayList<Artikel>();

		ResultSet set = getManaged(GET_ALL_ARTIKLES);

		openConnection();

		while (set.next()) {
			list.add(new Artikel(set.getLong("id"), getMengeneinheitById(set.getLong("mengeneinheit_fk")), getKategorieById(set
					.getLong("kategorie_fk")), getLieferantById(set.getLong("lieferant_fk")), set.getString("artikelnr"), set.getString("name"), set
					.getDouble("bestellgroesse"), set.getFloat("preis"), set.getBoolean("bio"), set.getBoolean("standard"), set
					.getBoolean("grundbedarf"), set.getInt("durchschnitt"), set.getBoolean("lebensmittel"), set.getString("notiz")));
		}

		closeConnection();
		return list;
	}

	public List<Artikel> getAllArtikelName() throws ConnectException, DAOException, SQLException {
		List<Artikel> list = new ArrayList<Artikel>();

		ResultSet set = getManaged(GET_ALL_ARTIKLES_NAME);

		while (set.next()) {
			list.add(new Artikel(set.getLong("id"), set.getString("name")));
		}

		return list;
	}

	/**
	 * Die Methode getAllArtikelByLieferantId liefert ein Ergebniss zurück bei
	 * der Suche nach einem Artikel anhang der LieferantId in der Datenbank.
	 * 
	 * @param id
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Artikel> getAllArtikelByLieferantId(Long id) throws ConnectException, DAOException, SQLException {
		List<Artikel> list = new ArrayList<Artikel>();
		ResultSet set = getManaged(MessageFormat.format(GET_ALL_ARTIKLES_BY_LIEFERANT_ID, id));
		openConnection();
		while (set.next()) {
			list.add(new Artikel(set.getLong("id"), getMengeneinheitById(set.getLong("mengeneinheit_fk")), getKategorieById(set
					.getLong("kategorie_fk")), getLieferantById(set.getLong("lieferant_fk")), set.getString("artikelnr"), set.getString("name"), set
					.getDouble("bestellgroesse"), set.getFloat("preis"), set.getBoolean("bio"), set.getBoolean("standard"), set
					.getBoolean("grundbedarf"), set.getInt("durchschnitt"), set.getBoolean("lebensmittel"), set.getString("notiz")));
		}
		closeConnection();
		return list;
	}

	/**
	 * Die Methode getArtikelById liefert eins Ergebniss zurück bei der Suche
	 * nach einem Artikel in der Datenbank.
	 * 
	 * @param id
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */

	public Artikel getArtikelById(Long id) throws ConnectException, DAOException, SQLException {
		Artikel result = null;

		ResultSet set = getManaged(MessageFormat.format(GET_ARTIKEL_BY_ID, id));
		openConnection();
		while (set.next()) {
			result = new Artikel(set.getLong("id"), getMengeneinheitById(set.getLong("mengeneinheit_fk")),
					getKategorieById(set.getLong("kategorie_fk")), getLieferantById(set.getLong("lieferant_fk")), set.getString("artikelnr"),
					set.getString("name"), set.getDouble("bestellgroesse"), set.getFloat("preis"), set.getBoolean("bio"),
					set.getBoolean("standard"), set.getBoolean("grundbedarf"), set.getInt("durchschnitt"), set.getBoolean("lebensmittel"),
					set.getString("notiz"));
		}
		closeConnection();
		return result;
	}

	/**
	 * Die Methode getArtikelByName liefert eins bis mehrere Ergebnisse zurück
	 * bei der Suche nach einem Artikel in der Datenbank.
	 * 
	 * @param name
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Artikel> getArtikelByName(String name) throws ConnectException, DAOException, SQLException {
		List<Artikel> list = new ArrayList<Artikel>();

		ResultSet set = getManaged(GET_ARTIKEL_BY_NAME + "'" + name + "'");

		while (set.next()) {
			list.add(new Artikel(set.getLong("id"), new Mengeneinheit(), KategorieDAO.getInstance().getKategorieById(set.getLong("kategorie_fk")),
					LieferantDAO.getInstance().getLieferantById(set.getLong("lieferant_fk")), set.getString("artikelnr"), set.getString("name"), set
							.getDouble("bestellgroesse"), set.getFloat("preis"), set.getBoolean("bio"), set.getBoolean("standard"), set
							.getBoolean("grundbedarf"), set.getInt("durchschnitt"), set.getBoolean("lebensmittel"), set.getString("notiz")));
		}

		return list;
	}

	/**
	 * Die Methode liefert alle Artikel zurück die Grundbedarf sind. Als
	 * Grundbedarf gilt z.B. Salami für die Belegte Brötchen auf der Menükarte.
	 * 
	 * @author Christian Barth
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Artikel> getArtikelByGrundbedarf() throws ConnectException, DAOException, SQLException {
		List<Artikel> list = new ArrayList<Artikel>();

		ResultSet set = getManaged(GET_ARTIKEL_BY_GRUNDBEDARF);

		while (set.next()) {
			list.add(new Artikel(set.getLong("id"), new Mengeneinheit(), KategorieDAO.getInstance().getKategorieById(set.getLong("kategorie_fk")),
					LieferantDAO.getInstance().getLieferantById(set.getLong("lieferant_fk")), set.getString("artikelnr"), set.getString("name"), set
							.getDouble("bestellgroesse"), set.getFloat("preis"), set.getBoolean("bio"), set.getBoolean("standard"), set
							.getBoolean("grundbedarf"), set.getInt("durchschnitt"), set.getBoolean("lebensmittel"), set.getString("notiz")));
		}

		return list;
	}

	/**
	 * Die Methode liefert alle Artikel zurück die Standardbedarf sind. Als
	 * Standardbedarf gilt z.B. Pfeffer oder Salz.
	 * 
	 * @author Christian Barth
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Artikel> getArtikelByStandardbedarf() throws ConnectException, DAOException, SQLException {
		List<Artikel> list = new ArrayList<Artikel>();

		ResultSet set = getManaged(GET_ARTIKEL_BY_STANDARDBEDARF);

		while (set.next()) {
			list.add(new Artikel(set.getLong("id"), new Mengeneinheit(), KategorieDAO.getInstance().getKategorieById(set.getLong("kategorie_fk")),
					LieferantDAO.getInstance().getLieferantById(set.getLong("lieferant_fk")), set.getString("artikelnr"), set.getString("name"), set
							.getDouble("bestellgroesse"), set.getFloat("preis"), set.getBoolean("bio"), set.getBoolean("standard"), set
							.getBoolean("grundbedarf"), set.getInt("durchschnitt"), set.getBoolean("lebensmittel"), set.getString("notiz")));
		}

		return list;
	}

	/**
	 * Die Methode erzeugt einen Artikel in der Datenbank.
	 * 
	 * @param ansprechpartner
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void createArtikel(Artikel artikel) throws ConnectException, DAOException {
		putManaged(MessageFormat.format(
				PUT_ARTIKEL,
				"'" + artikel.getArtikelnr() + "','" + artikel.getName() + "'," + artikel.getBestellgroesse() + ","
						+ artikel.getMengeneinheit().getId() + "," + artikel.getPreis() + "," + artikel.getLieferant().getId() + ","
						+ Util.convertBoolean(artikel.isBio()) + "," + artikel.getKategorie().getId() + ","
						+ Util.convertBoolean(artikel.isStandard()) + "," + Util.convertBoolean(artikel.isGrundbedarf()) + ","
						+ artikel.getDurchschnitt() + "," + Util.convertBoolean(artikel.isLebensmittel()) + ",'" + artikel.getNotiz() + "'"));
	}

	/**
	 * Die Methode aktualisiert einen Artikel in der Datenbank.
	 * 
	 * @param ansprechpartner
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void updateArtikel(Artikel artikel) throws ConnectException, DAOException {
		putManaged("UPDATE artikel SET `artikelnr` = '" + artikel.getArtikelnr() + "'," + "`name` = '" + artikel.getName() + "',"
				+ "`bestellgroesse` = " + artikel.getBestellgroesse() + "," + "`mengeneinheit_fk` = " + artikel.getMengeneinheit().getId() + ","
				+ "`preis` = " + artikel.getPreis() + "," + "`lieferant_fk` = " + artikel.getLieferant().getId() + "," + "`bio` = "
				+ Util.convertBoolean(artikel.isBio()) + "," + "`kategorie_fk` = " + artikel.getKategorie().getId() + "," + "`standard` = "
				+ Util.convertBoolean(artikel.isStandard()) + "," + "`grundbedarf` = " + Util.convertBoolean(artikel.isGrundbedarf()) + ","
				+ "`durchschnitt` = " + artikel.getDurchschnitt() + "," + "`lebensmittel` = " + Util.convertBoolean(artikel.isLebensmittel()) + ","
				+ "`notiz` = '" + artikel.getNotiz() + "' WHERE id = " + artikel.getId());

	}

	/**
	 * Die Methode gibt alle Artikel zurÃ¼ck bei denen es sich um Lebensmittel
	 * handelt.
	 * 
	 * @author Christian Barth
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Artikel> getArtikelByLebensmittel() throws ConnectException, DAOException, SQLException {

		List<Artikel> list = new ArrayList<Artikel>();

		ResultSet set = getManaged(GET_ARTIKEL_BY_LEBENSMITTEL);

		while (set.next()) {
			list.add(new Artikel(set.getLong("id"), new Mengeneinheit(), KategorieDAO.getInstance().getKategorieById(set.getLong("kategorie_fk")),
					LieferantDAO.getInstance().getLieferantById(set.getLong("lieferant_fk")), set.getString("artikelnr"), set.getString("name"), set
							.getDouble("bestellgroesse"), set.getFloat("preis"), set.getBoolean("bio"), set.getBoolean("standard"), set
							.getBoolean("grundbedarf"), set.getInt("durchschnitt"), set.getBoolean("lebensmittel"), set.getString("notiz")));
		}

		return list;
	}

	private Lieferant getLieferantById(Long id) throws SQLException {
		Lieferant lieferant = null;
		ResultSet set = get(MessageFormat.format(GET_LIEFERANT_BY_ID, id));

		while (set.next()) {
			lieferant = new Lieferant(set.getLong("id"), set.getString("name"), set.getString("kundennummer"), set.getString("bezeichnung"),
					set.getString("strasse"), set.getString("plz"), set.getString("ort"), set.getString("email"), set.getString("telefon"),
					set.getString("fax"), set.getString("notiz"), set.getBoolean("mehrereliefertermine"));
		}

		return lieferant;
	}

	private Kategorie getKategorieById(Long id) throws ConnectException, DAOException, SQLException {
		Kategorie kategorie = null;
		ResultSet set = get(MessageFormat.format(GET_KATEGORIE_BY_ID, id));
		while (set.next()) {
			kategorie = new Kategorie(set.getLong("id"), set.getString("name"));
		}
		return kategorie;
	}

	private Mengeneinheit getMengeneinheitById(Long id) throws ConnectException, DAOException, SQLException {

		Mengeneinheit me = null;
		ResultSet set = get(MessageFormat.format(GET_MENGENEINHEIT_BY_ID, id));

		while (set.next()) {
			me = new Mengeneinheit(set.getLong("id"), set.getString("name"), set.getString("kurz"));
		}

		return me;
	}
}
