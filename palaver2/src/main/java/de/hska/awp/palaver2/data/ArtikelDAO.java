/**
 * 	Sebastian Walz
 */
package de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver2.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;

public class ArtikelDAO extends AbstractDAO
{	
	private final static String			GET_ALL_ARTIKLES = "SELECT * FROM artikel";
	
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
								new Lieferant(),
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
