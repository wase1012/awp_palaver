/**
 * 	Sebastian Walz
 */
package de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;

public class ArtikelDAO extends AbstractDAO
{
	private static ArtikelDAO 			instance = null;
	
	private final static String			GET_ALL_ARTIKLES = "SELECT * FROM artikel";
	
	private ArtikelDAO()
	{
		super();
	}
	
	public static ArtikelDAO getInstance()
	{
		if(instance == null)
		{
			instance = new ArtikelDAO();
		}
		return instance;
	}
	
//	public List<Artikel> getAllArtikel() throws ConnectException, DAOException, SQLException
//	{
//		List<Artikel> list = new ArrayList<Artikel>();
//		
//		ResultSet set = get(GET_ALL_ARTIKLES);
//		
//		while(set.next())
//		{
//			list.add(new Artikel(set.getLong("id"),
//								set.getString("name"),
//								set.getFloat("preis"),
//								set.getBoolean("bio"),
//								set.getString("artikelnummer"),
//								set.getBoolean("standard"),
//								set.getBoolean("grundbedarf"),
//								set.getInt("durchschnitt"),
//								set.getFloat("bestellgroesse")
//								));
//		}
//		
//		return list;
//	}
}
