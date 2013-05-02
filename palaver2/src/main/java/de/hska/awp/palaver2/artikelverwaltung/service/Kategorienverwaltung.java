package de.hska.awp.palaver2.artikelverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.hska.awp.palaver2.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.data.KategorieDAO;

public class Kategorienverwaltung extends KategorieDAO {

	private static Kategorienverwaltung instance = null;

	private Kategorienverwaltung() {
		super();
	}

	public static Kategorienverwaltung getInstance() {
		if (instance == null) {
			instance = new Kategorienverwaltung();
		}
		return instance;
	}
	
	public List<Kategorie> getAllKategories() throws ConnectException,
			DAOException, SQLException {
		List<Kategorie> result = null;
		result = super.getAllKategories();
		return result;
	}
	
	public Kategorie getKategorieById(Long id) throws ConnectException, DAOException, SQLException
	{
		Kategorie result = null;		
		result = super.getKategorieById(id);		
		return result;
	}
	
	public void createNewKategorie(Kategorie kategorie) throws ConnectException, DAOException, SQLException
	{
		super.createNewKategorie(kategorie);
	}
	
	public void updateKategorie(Kategorie kategorie) throws ConnectException, DAOException, SQLException
	{
		super.updateKategorie(kategorie);
	}
}
