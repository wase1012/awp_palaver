package de.hska.awp.palaver2.lieferantenverwaltung.service;

import java.util.List;

import de.hska.awp.palaver2.data.AnsprechpartnerDAO;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Ansprechpartner;

public class Ansprechpartnerverwaltung extends AnsprechpartnerDAO{
	
	private static Ansprechpartnerverwaltung instance = null;
	
	private Ansprechpartnerverwaltung()
	{
		super();
	}
	
	public static Ansprechpartnerverwaltung getInstance()
	{
		if (instance == null)
		{
			instance = new Ansprechpartnerverwaltung();
		}
		return instance;
	}

	public List<Ansprechpartner> findAllAnsprechpartner() {
		
		List<Ansprechpartner> result = null;
		
		result = super.getAllAnsprechpartner();
		
		return result;
	}
	
	public List<Ansprechpartner> findAnsprechpartnerByName(String name) {
		final List<Ansprechpartner> aplist = dao.findAnsprechpartnerByName(name);
		return aplist;
	}

	public Ansprechpartner findAnsprechpartnerById(Long id) {
		final Ansprechpartner ap = dao.findAnsprechpartnerById(id);
		return ap;
	}
	
	public Ansprechpartner createAnsprechpartner(Ansprechpartner ap) {
		
		if (ap == null) {
			return ap;
		}
		
		ap = (Ansprechpartner) dao.create(ap);
		
		return ap;
	}
	
	public Ansprechpartner updateAnsprechpartner(Ansprechpartner ap) {
		
		if (ap == null) {
			return null;
		}

		ap = (Ansprechpartner) dao.update(ap);
		return ap;
	}

	/**
	 */
	public void deleteAnsprechpartner(Ansprechpartner ap) {
		
		if (ap == null) {
			return;
		}

		dao.delete(ap);
	}
	
}
