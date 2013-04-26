/**
 * Created by Sebastian Walz
 * 24.04.2013 16:08:18
 */
package de.hska.awp.palaver2.artikelverwaltung.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.data.ArtikelDAO;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;

public class Artikelverwaltung extends ArtikelDAO
{
	private static Artikelverwaltung		instance = null;
	
	private Artikelverwaltung()
	{
		super();
	}
	
	public static Artikelverwaltung getInstance()
	{
		if (instance == null)
		{
			instance = new Artikelverwaltung();
		}
		return instance;
	}
	
	public List<Artikel> getAllArtikel() throws ConnectException, DAOException, SQLException
	{
		List<Artikel> result = null;
		
		result = super.getAllArtikel();
		
		return result;
	}
}
