package main.java.de.hska.awp.palaver2.artikelverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import main.java.de.hska.awp.palaver2.artikelverwaltung.domain.Kategorie;
import main.java.de.hska.awp.palaver2.data.ConnectException;
import main.java.de.hska.awp.palaver2.data.DAOException;
import main.java.de.hska.awp.palaver2.data.KategorieDAO;

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

	/**
	 * Die Methode liefert alle Kategorien zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 * @return List<Kategorie>
	 */
	public List<Kategorie> getAllKategories() throws ConnectException,
			DAOException, SQLException {
		List<Kategorie> result = null;
		result = super.getAllKategories();
		return result;
	}

	/**
	 * Die Methode liefert eine Kategorie anhand des Parameter id zurück.
	 * 
	 * @param id 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 * 
	 * @return Kategorie
	 */
	public Kategorie getKategorieById(Long id) throws ConnectException,
			DAOException, SQLException {
		Kategorie result = null;
		result = super.getKategorieById(id);
		return result;
	}

	/**
	 * Die Methode erzeugt eine Kategorie.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	public void createNewKategorie(Kategorie kategorie)
			throws ConnectException, DAOException, SQLException {
		super.createNewKategorie(kategorie);
	}

	/**
	 * Die Methode aktualisiert eine Kategorie.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	public void updateKategorie(Kategorie kategorie) throws ConnectException,
			DAOException, SQLException {
		super.updateKategorie(kategorie);
	}
}
