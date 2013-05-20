package main.java.de.hska.awp.palaver2.artikelverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import main.java.de.hska.awp.palaver2.artikelverwaltung.domain.Mengeneinheit;
import main.java.de.hska.awp.palaver2.data.ConnectException;
import main.java.de.hska.awp.palaver2.data.DAOException;
import main.java.de.hska.awp.palaver2.data.MengeneinheitDAO;

public class Mengeneinheitverwaltung extends MengeneinheitDAO {

	private static Mengeneinheitverwaltung		instance = null;
	private MengeneinheitDAO dao;
	
	private Mengeneinheitverwaltung()
	{
		super();
	}
	
	public static Mengeneinheitverwaltung getInstance()
	{
		if (instance == null)
		{
			instance = new Mengeneinheitverwaltung();
		}
		return instance;
	}
	
	/**
	 * Die Methode liefert alle Mengeneinheiten zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 * @return List<Mengeneinheit>
	 */
	public List<Mengeneinheit> getAllMengeneinheit() throws ConnectException, DAOException, SQLException
	{
		List<Mengeneinheit> result = null;		
		result = super.getAllMengeneinheit();		
		return result;
	}
	
	/**
	 * Die Methode liefert eine Mengeneinheit anhand des Parameter id zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 * @return Mengeneinheit
	 */
	public Mengeneinheit getMengeneinheitById(Long id) throws ConnectException, DAOException, SQLException
	{
		Mengeneinheit result = null;		
		result = super.getMengeneinheitById(id);		
		return result;
	}
	
	/**
	 * Die Methode liefert eine Mengeneinheit anhand des Parameter name zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 * @return Mengeneinheit
	 */
	public List<Mengeneinheit> getMengeneinheitByName(String name) throws ConnectException, DAOException, SQLException
	{
		List<Mengeneinheit> result = null;		
		result = super.getMengeneinheitByName(name);		
		return result;
	}
	
	/**
	 * Die Methode erzeugt eine Mengeneinheit.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	public void createMengeneinheit(Mengeneinheit mengeneinheit) throws ConnectException, DAOException, SQLException
	{
		dao.createNewMengeneinheit(mengeneinheit);
	}
	
	/**
	 * Die Methode aktualisiert eine Mengeneinheit.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	public void updateMengeneinheit(Mengeneinheit mengeneinheit) throws ConnectException, DAOException, SQLException
	{
		super.updateMengeneinheit(mengeneinheit);
	}
}

