/**
 * Created by Sebastian Walz
 * 24.04.2013 16:08:18
 */
package de.hska.awp.palaver2.artikelverwaltung.service;

import java.util.List;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.util.Dao;

@SuppressWarnings("serial")
public class Artikelverwaltung extends Dao
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
	
	public List<Artikel> getAllArtikel()
	{
		List<Artikel> result = null;
		
		result = find(Artikel.class, Artikel.FIND_ALL_ARTIKLES);
		
		return result;
	}
}
