/**
 * Created by Sebastian Walz
 * 24.04.2013 16:08:18
 */
package de.hska.awp.palaver2.artikelverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver2.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver2.data.ArtikelDAO;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.data.KategorieDAO;
import de.hska.awp.palaver2.data.MengeneinheitDAO;

public class Artikelverwaltung extends ArtikelDAO {
	private static Artikelverwaltung instance = null;

	private Artikelverwaltung() {
		super();
	}

	public static Artikelverwaltung getInstance() {
		if (instance == null) {
			instance = new Artikelverwaltung();
		}
		return instance;
	}
	
	/**
	 * Die Methode liefert alle Artikeln zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 * @return List<Artikel>
	 */
	public List<Artikel> getAllArtikel() throws ConnectException, DAOException,
			SQLException {
		List<Artikel> result = null;
		result = super.getAllArtikel();
		return result;
	}

	/**
	 * Die Methode liefert ein Artikel anhand des Parameter id zurück.
	 * 
	 * @param id 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 * 
	 * @return Artikel
	 */
	public Artikel getArtikelById(Long id) throws ConnectException,
			DAOException, SQLException {
		Artikel result = null;
		result = super.getArtikelById(id);
		return result;
	}

	/**
	 * Die Methode liefert ein Artikel anhand des Parameter name zurück.
	 * 
	 * @param id 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 * 
	 * @return Artikel
	 */
	public List<Artikel> getArtikelByName(String name) throws ConnectException,
			DAOException, SQLException {
		List<Artikel> result = null;
		result = super.getArtikelByName(name);
		return result;
	}

	/**
	 * Die Methode erzeugt ein Artikel.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	public void createArtikel(Artikel artikel) throws ConnectException,
			DAOException {
		super.createArtikel(artikel);
	}

	/**
	 * Die Methode aktualisiert ein Artikel.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	public void updateArtikel(Artikel artikel) throws ConnectException,
			DAOException {
		super.updateArtikel(artikel);
	}

	/* see Kategorienverwaltung
	 * 
	 * public List<Kategorie> getAllKategorien() throws ConnectException,
			DAOException, SQLException {
		List<Kategorie> result = null;

		result = KategorieDAO.getInstance().getAllKategories();

		return result;
	}
	*/
}
