/**
 * 
 */
package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.artikelverwaltung.domain.Artikel;
import de.bistrosoft.palaver.artikelverwaltung.domain.Mengeneinheit;

/**
 * @author Android
 * 
 */
public class ArtikelDAO extends AbstractDAO {

	private static ArtikelDAO instance = null;
	private final static String GET_ALL_ARTIKLES = "SELECT * FROM artikel";
	private final static String GET_ARTIKEL_BY_ID = "SELECT * FROM artikel where id = {0}";
	private final static String GET_ARTIKEL_BY_NAME = "SELECT * FROM artikel where name like ";
	private final static String PUT_ARTIKEL = "INSERT INTO artikel(`artikelnr`,`name`,`bestellgroesse`,`mengeneinheit_fk`,`preis`,`lieferant_fk`,`bio`,`kategorie_fk`,`standard`,`grundbedarf`,`durchschnitt`,`lebensmittel`)VALUES({0})";
	private final static String UPDATE_ARTIKEL = "UPDATE artikel SET `artikelnr` = {0},`name` = {1},`bestellgroesse` = {2},`mengeneinheit_fk` = {3},`preis` = {4},`lieferant_fk` = {5},`bio` = {6},`kategorie_fk` = {7},`standard` = {8},`grundbedarf` = {9},`durchschnitt` = {10},`lebensmittel` = {11}WHERE id = {12}";
	private final static String GET_ARTIKEL_BY_GRUNDBEDARF = "SELECT * FROM artikel WHERE grundbedarf=1";
	private final static String GET_ARTIKEL_BY_STANDARDBEDARF = "SELECT * FROM artikel WHERE standard=1";

	public ArtikelDAO() {
		super();
	}

	public static ArtikelDAO getInstance() {
		if (instance == null) {
			instance = new ArtikelDAO();
		}
		return instance;
	}

	public List<Artikel> getAllArtikel() throws ConnectException, DAOException,
			SQLException {
		List<Artikel> list = new ArrayList<Artikel>();

		ResultSet set = get(GET_ALL_ARTIKLES);

		while (set.next()) {
			list.add(new Artikel(set.getLong("id"), new Mengeneinheit(),
					KategorieDAO.getInstance().getKategorieById(
							set.getLong("kategorie_fk")), LieferantDAO
							.getInstance().getLieferantById(
									set.getLong("lieferant_fk")), set
							.getString("artikelnr"), set.getString("name"), set
							.getDouble("bestellgroesse"),
					set.getFloat("preis"), set.getBoolean("bio"), set
							.getBoolean("standard"), set
							.getBoolean("grundbedarf"), set
							.getInt("durchschnitt"), set
							.getBoolean("lebensmittel")));
		}

		return list;
	}

	public Artikel getArtikelById(Long id) throws ConnectException,
			DAOException, SQLException {
		Artikel result = null;

		ResultSet set = get(MessageFormat.format(GET_ARTIKEL_BY_ID, id));

		while (set.next()) {
			result = new Artikel(set.getLong("id"), new Mengeneinheit(),
					KategorieDAO.getInstance().getKategorieById(
							set.getLong("kategorie_fk")), LieferantDAO
							.getInstance().getLieferantById(
									set.getLong("lieferant_fk")),
					set.getString("artikelnr"), set.getString("name"),
					set.getDouble("bestellgroesse"), set.getFloat("preis"),
					set.getBoolean("bio"), set.getBoolean("standard"),
					set.getBoolean("grundbedarf"), set.getInt("durchschnitt"),
					set.getBoolean("lebensmittel"));
		}

		return result;
	}

	public List<Artikel> getArtikelByName(String name) throws ConnectException,
			DAOException, SQLException {
		List<Artikel> list = new ArrayList<Artikel>();

		ResultSet set = get(GET_ARTIKEL_BY_NAME + "'" + name + "'");

		while (set.next()) {
			list.add(new Artikel(set.getLong("id"), new Mengeneinheit(),
					KategorieDAO.getInstance().getKategorieById(
							set.getLong("kategorie_fk")), LieferantDAO
							.getInstance().getLieferantById(
									set.getLong("lieferant_fk")), set
							.getString("artikelnr"), set.getString("name"), set
							.getDouble("bestellgroesse"),
					set.getFloat("preis"), set.getBoolean("bio"), set
							.getBoolean("standard"), set
							.getBoolean("grundbedarf"), set
							.getInt("durchschnitt"), set
							.getBoolean("lebensmittel")));
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
	public List<Artikel> getArtikelByGrundbedarf() throws ConnectException,
			DAOException, SQLException {
		List<Artikel> list = new ArrayList<Artikel>();

		ResultSet set = get(GET_ARTIKEL_BY_GRUNDBEDARF);

		while (set.next()) {
			list.add(new Artikel(set.getLong("id"), new Mengeneinheit(),
					KategorieDAO.getInstance().getKategorieById(
							set.getLong("kategorie_fk")), LieferantDAO
							.getInstance().getLieferantById(
									set.getLong("lieferant_fk")), set
							.getString("artikelnr"), set.getString("name"), set
							.getDouble("bestellgroesse"),
					set.getFloat("preis"), set.getBoolean("bio"), set
							.getBoolean("standard"), set
							.getBoolean("grundbedarf"), set
							.getInt("durchschnitt"), set
							.getBoolean("lebensmittel")));
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
	public List<Artikel> getArtikelByStandardbedarf() throws ConnectException,
			DAOException, SQLException {
		List<Artikel> list = new ArrayList<Artikel>();

		ResultSet set = get(GET_ARTIKEL_BY_STANDARDBEDARF);

		while (set.next()) {
			list.add(new Artikel(set.getLong("id"), new Mengeneinheit(),
					KategorieDAO.getInstance().getKategorieById(
							set.getLong("kategorie_fk")), LieferantDAO
							.getInstance().getLieferantById(
									set.getLong("lieferant_fk")), set
							.getString("artikelnr"), set.getString("name"), set
							.getDouble("bestellgroesse"),
					set.getFloat("preis"), set.getBoolean("bio"), set
							.getBoolean("standard"), set
							.getBoolean("grundbedarf"), set
							.getInt("durchschnitt"), set
							.getBoolean("lebensmittel")));
		}

		return list;
	}

}
