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

public class ArtikelDAO extends AbstractDAO
{	
	private final static String			GET_ALL_ARTIKLES = "SELECT * FROM artikel";
	private final static String			GET_ARTIKEL_BY_ID = "SELECT * FROM artikel where id = {0}";
	private final static String			GET_ARTIKEL_BY_NAME = "SELECT * FROM artikel where name = '{0}'";
	
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
}
