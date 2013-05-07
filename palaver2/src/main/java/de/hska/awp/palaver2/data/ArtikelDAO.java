/**
 * 	Sebastian Walz
 */
package de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.util.converter.ConverterUtil;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver2.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.util.Util;

public class ArtikelDAO extends AbstractDAO
{	
	private static ArtikelDAO instance = null;
	private final static String			GET_ALL_ARTIKLES = "SELECT * FROM artikel";
	private final static String			GET_ALL_ARTIKLES_BY_LIEFERANT_ID = "SELECT * FROM artikel where lieferant_fk = {0}";
	private final static String			GET_ARTIKEL_BY_ID = "SELECT * FROM artikel where id = {0}";
	private final static String			GET_ARTIKEL_BY_NAME = "SELECT * FROM artikel where name like ";
	private final static String			PUT_ARTIKEL = "INSERT INTO artikel(`artikelnr`,`name`,`bestellgroesse`,`mengeneinheit_fk`,`preis`,`lieferant_fk`,`bio`,`kategorie_fk`,`standard`,`grundbedarf`,`durchschnitt`,`lebensmittel`)VALUES({0})";
	//private final static String			UPDATE_ARTIKEL = "UPDATE artikel SET `artikelnr` = {0},`name` = {1},`bestellgroesse` = {2},`mengeneinheit_fk` = {3},`preis` = {4},`lieferant_fk` = {5},`bio` = {6},`kategorie_fk` = {7},`standard` = {8},`grundbedarf` = {9},`durchschnitt` = {10},`lebensmittel` = {11} WHERE id = {12}";
	private final static String			GET_ARTIKEL_BY_GRUNDBEDARF = "SELECT * FROM artikel WHERE grundbedarf=1";
	private final static String			GET_ARTIKEL_BY_STANDARDBEDARF = "SELECT * FROM artikel WHERE standard=1";
	private static final String 		GET_LIEFERANT_BY_ID = "SELECT * FROM lieferant WHERE id = {0}";
	private final static String 		GET_KATEGORIE_BY_ID = "SELECT * FROM kategorie WHERE id = {0}";
	
	public ArtikelDAO()
	{
		super();
	}
	
	public static ArtikelDAO getInstance() {
		if (instance == null) {
			instance = new ArtikelDAO();
		}
		return instance;
	}
	
	public List<Artikel> getAllArtikel() throws ConnectException, DAOException, SQLException
	{
		List<Artikel> list = new ArrayList<Artikel>();
		
		ResultSet set = getManaged(GET_ALL_ARTIKLES);
		
		openConnection();
		
		while(set.next())
		{
			list.add(new Artikel(set.getLong("id"),
								new Mengeneinheit(),
								getKategorieById(set.getLong("kategorie_fk")),
								getLieferantById(set.getLong("lieferant_fk")),
								set.getString("artikelnr"),
								set.getString("name"),
								set.getDouble("bestellgroesse"),
								set.getFloat("preis"),
								set.getBoolean("bio"),
								set.getBoolean("standard"),
								set.getBoolean("grundbedarf"),
								set.getInt("durchschnitt"),
								set.getBoolean("lebensmittel")
								));
		}
		
		closeConnection();
		return list;
	}
	
	public List<Artikel> getAllArtikelByLieferantId(Long id) throws ConnectException, DAOException, SQLException
	{
		List<Artikel> list = new ArrayList<Artikel>();		
		ResultSet set = getManaged(MessageFormat.format(GET_ALL_ARTIKLES_BY_LIEFERANT_ID, id));		
		openConnection();		
		while(set.next())
		{
			list.add(new Artikel(set.getLong("id"),
								new Mengeneinheit(),
								getKategorieById(set.getLong("kategorie_fk")),
								getLieferantById(set.getLong("lieferant_fk")),
								set.getString("artikelnr"),
								set.getString("name"),
								set.getDouble("bestellgroesse"),
								set.getFloat("preis"),
								set.getBoolean("bio"),
								set.getBoolean("standard"),
								set.getBoolean("grundbedarf"),
								set.getInt("durchschnitt"),
								set.getBoolean("lebensmittel")
								));
		}		
		closeConnection();
		return list;
	}
	
	
	public Artikel getArtikelById(Long id) throws ConnectException, DAOException, SQLException
	{
		Artikel result = null;
		
		ResultSet set = getManaged(MessageFormat.format(GET_ARTIKEL_BY_ID, id));
		
		while(set.next())
		{
			result = new Artikel(set.getLong("id"),
								MengeneinheitDAO.getInstance().getMengeneinheitById(set.getLong("mengeneinheit_fk")),
								KategorieDAO.getInstance().getKategorieById(set.getLong("kategorie_fk")),
								LieferantDAO.getInstance().getLieferantById(set.getLong("lieferant_fk")),
								set.getString("artikelnr"),
								set.getString("name"),
								set.getDouble("bestellgroesse"),
								set.getFloat("preis"),
								set.getBoolean("bio"),
								set.getBoolean("standard"),
								set.getBoolean("grundbedarf"),
								set.getInt("durchschnitt"),
								set.getBoolean("lebensmittel")
								);
		}
		
		return result;
	}
	
	public List<Artikel> getArtikelByName(String name) throws ConnectException, DAOException, SQLException
	{
		List<Artikel> list = new ArrayList<Artikel>();
		
		ResultSet set = getManaged(GET_ARTIKEL_BY_NAME + "'" + name + "'");
		
		while(set.next())
		{
			list.add(new Artikel(set.getLong("id"),
								new Mengeneinheit(),
								KategorieDAO.getInstance().getKategorieById(set.getLong("kategorie_fk")),
								LieferantDAO.getInstance().getLieferantById(set.getLong("lieferant_fk")),
								set.getString("artikelnr"),
								set.getString("name"),
								set.getDouble("bestellgroesse"),
								set.getFloat("preis"),
								set.getBoolean("bio"),
								set.getBoolean("standard"),
								set.getBoolean("grundbedarf"),
								set.getInt("durchschnitt"),
								set.getBoolean("lebensmittel")
								));
		}
		
		return list;
	}
	
	/**
	 * Die Methode liefert alle Artikel zurück die Grundbedarf sind.
	 * Als Grundbedarf gilt z.B. Salami für die Belegte Brötchen auf der Menükarte.
	 * @author Christian Barth
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Artikel> getArtikelByGrundbedarf() throws ConnectException, DAOException, SQLException
	{
		List<Artikel> list = new ArrayList<Artikel>();
		
		ResultSet set = getManaged(GET_ARTIKEL_BY_GRUNDBEDARF);
		
		while(set.next())
		{
			list.add(new Artikel(set.getLong("id"),
								new Mengeneinheit(),
								KategorieDAO.getInstance().getKategorieById(set.getLong("kategorie_fk")),
								LieferantDAO.getInstance().getLieferantById(set.getLong("lieferant_fk")),
								set.getString("artikelnr"),
								set.getString("name"),
								set.getDouble("bestellgroesse"),
								set.getFloat("preis"),
								set.getBoolean("bio"),
								set.getBoolean("standard"),
								set.getBoolean("grundbedarf"),
								set.getInt("durchschnitt"),
								set.getBoolean("lebensmittel")
								));
		}
		
		return list;
	}
	
	/**
	 * Die Methode liefert alle Artikel zurück die Standardbedarf sind.
	 * Als Standardbedarf gilt z.B. Pfeffer oder Salz.
	 * @author Christian Barth
	 * @return
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Artikel> getArtikelByStandardbedarf() throws ConnectException, DAOException, SQLException
	{
		List<Artikel> list = new ArrayList<Artikel>();
		
		ResultSet set = getManaged(GET_ARTIKEL_BY_STANDARDBEDARF);
		
		while(set.next())
		{
			list.add(new Artikel(set.getLong("id"),
								new Mengeneinheit(),
								KategorieDAO.getInstance().getKategorieById(set.getLong("kategorie_fk")),
								LieferantDAO.getInstance().getLieferantById(set.getLong("lieferant_fk")),
								set.getString("artikelnr"),
								set.getString("name"),
								set.getDouble("bestellgroesse"),
								set.getFloat("preis"),
								set.getBoolean("bio"),
								set.getBoolean("standard"),
								set.getBoolean("grundbedarf"),
								set.getInt("durchschnitt"),
								set.getBoolean("lebensmittel")
								));
		}
		
		return list;
	}
	
	public void createArtikel(Artikel artikel) throws ConnectException, DAOException
	{
		putManaged(MessageFormat.format(PUT_ARTIKEL,
				"'" + artikel.getArtikelnr() + "','" + artikel.getName() + "'," 
				+ artikel.getBestellgroesse() + "," + artikel.getMengeneinheit().getId() + "," 
				+ artikel.getPreis() + "," + artikel.getLieferant().getId() + "," + Util.convertBoolean(artikel.isBio()) 
				+ "," + artikel.getKategorie().getId() + "," + Util.convertBoolean(artikel.isStandard()) + ","
				+ Util.convertBoolean(artikel.isGrundbedarf()) + "," + artikel.getDurchschnitt() + "," + Util.convertBoolean(artikel.isLebensmittel())));
	}
	
	public void updateArtikel(Artikel artikel) throws ConnectException, DAOException
	{
		putManaged("UPDATE artikel SET `artikelnr` = '"+ artikel.getArtikelnr() +"'," +
				"`name` = '" + artikel.getName() + "'," +
				"`bestellgroesse` = " + artikel.getBestellgroesse() + "," +
				"`mengeneinheit_fk` = " + artikel.getMengeneinheit().getId() + "," +
				"`preis` = " + artikel.getPreis() + "," +
				"`lieferant_fk` = "+artikel.getLieferant().getId()+"," +
				"`bio` = "+Util.convertBoolean(artikel.isBio())+"," +
				"`kategorie_fk` = "+artikel.getKategorie().getId()+"," +
				"`standard` = "+Util.convertBoolean(artikel.isStandard())+"," +
				"`grundbedarf` = "+Util.convertBoolean(artikel.isGrundbedarf())+"," +
				"`durchschnitt` = "+artikel.getDurchschnitt()+"," +
				"`lebensmittel` = "+Util.convertBoolean(artikel.isLebensmittel())+
				" WHERE id = "+artikel.getId());
		
		/*
		 * float und double werden in String mit ',' konvertiert
		 * Error by update
		 * 
		put(MessageFormat.format(UPDATE_ARTIKEL, 
								"'" + artikel.getArtikelnr() + "'",
								"'" + artikel.getName() + "'",
								artikel.getBestellgroesse(),
								artikel.getMengeneinheit().getId(),
								artikel.getPreis(),
								artikel.getLieferant().getId(),
								Util.convertBoolean(artikel.isBio()),
								artikel.getKategorie().getId(),
								Util.convertBoolean(artikel.isStandard()),
								Util.convertBoolean(artikel.isGrundbedarf()),
								artikel.getDurchschnitt(),
								Util.convertBoolean(artikel.isLebensmittel()),
								artikel.getId()));
								*/
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
			set.getString("telefon"), set.getString("fax"));
		}

			return lieferant;
	}
	
	private Kategorie getKategorieById(Long id) throws ConnectException,
													DAOException, SQLException {
		Kategorie kategorie = null;
		ResultSet set = get(MessageFormat.format(GET_KATEGORIE_BY_ID, id));
		while (set.next()) {
			kategorie = new Kategorie(set.getLong("id"), set.getString("name"));
		}
		return kategorie;
	}
}
