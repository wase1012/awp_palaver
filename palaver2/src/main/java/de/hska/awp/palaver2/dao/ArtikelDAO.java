/**
 * 	Sebastian Walz
 */
package de.hska.awp.palaver2.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ArtikelDAO extends AbstractDAO
{
	private static ArtikelDAO instance = null;
	
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
	
//	public List<Artikel> getAllArtikel()
//	{
//		List<T> list = new ArrayList<T>();
//		
//		ResultSet set = get(GET_ALL_ARTIKLES);
//		
//		return list;
//	}
}
