package de.hska.awp.palaver2.lieferantenverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.hska.awp.palaver2.data.AnsprechpartnerDAO;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
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

	public List<Ansprechpartner> getAllAnsprechpartner() throws ConnectException, DAOException, SQLException {
		
		List<Ansprechpartner> result = null;
		
		result = super.getAllAnsprechpartner();
		
		return result;
	}
	
	public Ansprechpartner getAnsprechpartnerById(Long id) throws ConnectException, DAOException, SQLException
	{
		Ansprechpartner result = null;
		
		result = super.getAnsprechpartnerById(id);
		
		return result;
	}
	
	public List<Ansprechpartner> getAnsprechpartnerByName(String name) throws ConnectException, DAOException, SQLException
	{
		List<Ansprechpartner> result = null;
		
		result = super.getAnsprechpartnerByName(name);
		
		return result;
	}
	
	public void createAnsprechpartner(Ansprechpartner ansprechpartner) throws ConnectException, DAOException, SQLException
	{
		super.createAnsprechpartner(ansprechpartner);
	}
	
	public void updateAnsprechpartner(Ansprechpartner ansprechpartner) throws ConnectException, DAOException, SQLException
	{
		super.updateAnsprechpartner(ansprechpartner);
	}

	/**
	 * @throws SQLException 
	 * @throws DAOException 
	 * @throws ConnectException 
	 */
	public void deleteAnsprechpartner(Long id) throws ConnectException, DAOException, SQLException {
	
		super.deleteAnsprechpartner(id);
	}
	
}
