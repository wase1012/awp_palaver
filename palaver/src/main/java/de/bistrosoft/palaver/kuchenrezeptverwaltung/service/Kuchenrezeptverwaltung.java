/**
 * 
 */
package de.bistrosoft.palaver.kuchenrezeptverwaltung.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.data.ArtikelDAO;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.bistrosoft.palaver.data.KuchenrezeptDAO;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.Kuchenrezept;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.KuchenrezeptHasArtikel;

/**
 * @author Christine Hartkorn
 * 
 */
public class Kuchenrezeptverwaltung extends KuchenrezeptDAO {

	private static Kuchenrezeptverwaltung instance = null;

	private Kuchenrezeptverwaltung() {
		super();
	}

	public static Kuchenrezeptverwaltung getInstance() {
		if (instance == null) {
			instance = new Kuchenrezeptverwaltung();
		}
		return instance;
	}

	public void createKuchenrezept(Kuchenrezept kuchenrezept)
			throws ConnectException, DAOException, SQLException {
		super.createKuchenrezept(kuchenrezept);
	}

	public void saveArtikel(Kuchenrezept kuchenrezept) throws ConnectException,
			DAOException, SQLException {
		super.saveArtikel(kuchenrezept);
	}
	
	public List<Kuchenrezept> getAllKuchenrezepte() throws ConnectException, DAOException,
	SQLException {
		List<Kuchenrezept> result = null;
		result = super.getAllKuchenrezepte(true);
		return result;
	}
	
	public List<Artikel> getAllArtikelByKuchenrezeptId()
			throws ConnectException, DAOException, SQLException {
		List<Artikel> result = null;

		result = ArtikelDAO.getInstance().getAllArtikel();

		return result;
	}

	public void updateRezept(Kuchenrezept kuchenrezept)
			throws ConnectException, DAOException, SQLException {
		super.updateKuchenrezept(kuchenrezept);
	}

	public List<KuchenrezeptHasArtikel> ladeArtikelFuerKuchenrezept(
			Kuchenrezept rez) {
		try {
			return super.ladeArtikelFuerKuchenrezept(rez);
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return new ArrayList<KuchenrezeptHasArtikel>();
	}

	public void deleteZutatenZuKuchenrezept(Kuchenrezept kuchenrezept)
			throws ConnectException, DAOException, SQLException {
		super.deleteZutatenZuKuchenrezept(kuchenrezept);
	}
}
