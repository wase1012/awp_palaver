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

public class ArtikelDAO extends AbstractDAO
{	
	private final static String			GET_ALL_ARTIKLES = "SELECT * FROM artikel";
	private final static String			GET_ARTIKEL_BY_ID = "SELECT * FROM artikel where id = {0}";
	private final static String			GET_ARTIKEL_BY_NAME = "SELECT * FROM artikel where name like ";
	private final static String			PUT_ARTIKEL = "INSERT INTO artikel(`artikelnr`,`name`,`bestellgroesse`,`mengeneinheit_fk`,`preis`,`lieferant_fk`,`bio`,`kategorie_fk`,`standard`,`grundbedarf`,`durchschnitt`,`lebensmittel`)VALUES({0})";
	private final static String			UPDATE_ARTIKEL = "UPDATE artikel SET `artikelnr` = {0},`name` = {1},`bestellgroesse` = {2},`mengeneinheit_fk` = {3},`preis` = {4},`lieferant_fk` = {5},`bio` = {6},`kategorie_fk` = {7},`standard` = {8},`grundbedarf` = {9},`durchschnitt` = {10},`lebensmittel` = {11}WHERE id = {12}";
	
	public ArtikelDAO()
	{
		super();
	}
	
	public List<Artikel> getAllArtikel() throws ConnectException, DAOException, SQLException
	{
		List<Artikel> list = new ArrayList<Artikel>();
		
		ResultSet set = get(GET_ALL_ARTIKLES);
		
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
	
	public Artikel getArtikelById(Long id) throws ConnectException, DAOException, SQLException
	{
		Artikel result = null;
		
		ResultSet set = get(MessageFormat.format(GET_ARTIKEL_BY_ID, id));
		
		while(set.next())
		{
			result = new Artikel(set.getLong("id"),
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
								);
		}
		
		return result;
	}
	
	public List<Artikel> getArtikelByName(String name) throws ConnectException, DAOException, SQLException
	{
		List<Artikel> list = new ArrayList<Artikel>();
		
		ResultSet set = get(GET_ARTIKEL_BY_NAME + "'" + name + "'");
		
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
		put(MessageFormat.format(PUT_ARTIKEL,
				"'" + artikel.getArtikelnr() + "','" + artikel.getName() + "'," 
				+ artikel.getBestellgroesse() + "," + artikel.getMengeneinheit().getId() + "," 
				+ artikel.getPreis() + "," + artikel.getLieferant().getId() + "," + Util.convertBoolean(artikel.isBio()) 
				+ "," + artikel.getKategorie().getId() + "," + Util.convertBoolean(artikel.isStandard()) + ","
				+ Util.convertBoolean(artikel.isGrundbedarf()) + "," + artikel.getDurchschnitt() + "," + Util.convertBoolean(artikel.isLebensmittel())));
	}
	
	public void updateArtikel(Artikel artikel) throws ConnectException, DAOException
	{
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
								Util.convertBoolean(artikel.isLebensmittel())));
	}
}
