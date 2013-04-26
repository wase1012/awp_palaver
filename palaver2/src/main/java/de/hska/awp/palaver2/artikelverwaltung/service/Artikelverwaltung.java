/**
 * Created by Sebastian Walz
 * 24.04.2013 16:08:18
 */
package de.hska.awp.palaver2.artikelverwaltung.service;

public class Artikelverwaltung
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
	
	
}
