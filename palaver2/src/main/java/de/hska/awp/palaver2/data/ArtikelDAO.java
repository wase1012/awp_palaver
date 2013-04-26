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

public class ArtikelDAO extends AbstractDAO
{	
	private final static String			GET_ALL_ARTIKLES = "SELECT * FROM artikel";
	private final static String			GET_ARTIKEL_BY_ID = "SELECT * FROM artikel where id = {0}";
	private final static String			GET_ARTIKEL_BY_NAME = "SELECT * FROM artikel where name = '{0}'";
	private final static String			PUT_ARTIKEL = "INSERT INTO artikel(`id`,`artikelnr`,`name`,`bestellgroesse`,`mengeneinheit_fk`,`preis`,`lieferant_fk`,`bio`,`kategorie_fk`,`standard`,`grundbedarf`,`durchschnitt`,`lebensmittel`)VALUES({0})";
	private final static String			UPDATE_ARTIKEL = "UPDATE artikel SET `id` = {0},`artikelnr` = {1},`name` = {2},`bestellgroesse` = {3},`mengeneinheit_fk` = {4},`preis` = {5},`lieferant_fk` = {6},`bio` = {7},`kategorie_fk` = {8},`standard` = {9},`grundbedarf` = {10},`durchschnitt` = {11},`lebensmittel` = {12}WHERE id = {13}";
	
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
								new Kategorie(),
								LieferantDAO.getInstance().getLieferantById(set.getLong("lieferant_fk")),
								set.getString("artikelnummer"),
								set.getString("name"),
								set.getDouble("bestellgroesse"),
								set.getFloat("preis"),
								set.getBoolean("bio"),
								set.getBoolean("standard"),
								set.getBoolean("grundbedarf"),
								set.getInt("durchschnitt"),
								set.getBoolean("")
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
								new Kategorie(),
								LieferantDAO.getInstance().getLieferantById(set.getLong("lieferant_fk")),
								set.getString("artikelnummer"),
								set.getString("name"),
								set.getDouble("bestellgroesse"),
								set.getFloat("preis"),
								set.getBoolean("bio"),
								set.getBoolean("standard"),
								set.getBoolean("grundbedarf"),
								set.getInt("durchschnitt"),
								set.getBoolean("")
								);
		}
		
		return result;
	}
	
	public List<Artikel> getArtikelByName(String name) throws ConnectException, DAOException, SQLException
	{
		List<Artikel> list = new ArrayList<Artikel>();
		
		ResultSet set = get(MessageFormat.format(GET_ARTIKEL_BY_NAME, name));
		
		while(set.next())
		{
			list.add(new Artikel(set.getLong("id"),
								new Mengeneinheit(),
								new Kategorie(),
								LieferantDAO.getInstance().getLieferantById(set.getLong("lieferant_fk")),
								set.getString("artikelnummer"),
								set.getString("name"),
								set.getDouble("bestellgroesse"),
								set.getFloat("preis"),
								set.getBoolean("bio"),
								set.getBoolean("standard"),
								set.getBoolean("grundbedarf"),
								set.getInt("durchschnitt"),
								set.getBoolean("")
								));
		}
		
		return list;
	}
	
	public void createArtikel(Artikel artikel) throws ConnectException, DAOException
	{
		put(MessageFormat.format(PUT_ARTIKEL,
				artikel.getId() + ",'" + artikel.getArtikelnr() + "','" + artikel.getName() + "'," 
				+ artikel.getBestellgroesse() + "," + artikel.getMengeneinheit().getId() + "," 
				+ artikel.getPreis() + "," + artikel.getLieferant().getId() + ",'" + artikel.isBio() 
				+ "'," + artikel.getKategorie().getId() + ",'" + artikel.isStandard() + "','"
				+ artikel.isGrundbedarf() + "'," + artikel.getDurchschnitt() + ",'" + artikel.isLebensmittel() +"'"));
	}
	
	public void updateArtikel(Artikel artikel) throws ConnectException, DAOException
	{
		put(MessageFormat.format(UPDATE_ARTIKEL, 
								artikel.getId(),
								"'" + artikel.getArtikelnr() + "'",
								"'" + artikel.getName() + "'",
								artikel.getBestellgroesse(),
								artikel.getMengeneinheit().getId(),
								artikel.getPreis(),
								artikel.getLieferant().getId(),
								"'" + artikel.isBio() + "'",
								artikel.getKategorie().getId(),
								"'" + artikel.isStandard() + "'",
								"'" + artikel.isGrundbedarf() + "'",
								artikel.getDurchschnitt(),
								"'" + artikel.isLebensmittel() + "'"));
	}
}
