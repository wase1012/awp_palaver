/**
 * 
 */
package de.bistrosoft.palaver.kuchenrezeptverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.data.ArtikelDAO;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.bistrosoft.palaver.data.FussnoteDAO;
import de.bistrosoft.palaver.data.GeschmackDAO;
import de.bistrosoft.palaver.data.KuchenrezeptDAO;
import de.bistrosoft.palaver.data.RezeptartDAO;
import de.bistrosoft.palaver.data.ZubereitungDAO;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasArtikel;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasZubereitung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezeptart;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Zubereitung;
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

	// public void createArtikelForRezept(Rezept rezept){
	// try {
	// super.createArtikelForRezept(rezept);
	// } catch (ConnectException | DAOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	//
	// public List<RezeptHasArtikel> getArtikelByRezept(Rezept rezept){
	// List<RezeptHasArtikel> artikel=null;
	//
	// try {
	// super.getArtikelByRezept(rezept);
	// } catch (ConnectException | DAOException | SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// return artikel;
	// }

	// public List<Rezept> getAllRezept() throws ConnectException, DAOException,
	// SQLException {
	// List<Rezept> result = null;
	//
	// result = super.getAllRezept();
	//
	// return result;
	// }
	//
	// public Rezept getRezeptById(Long id) throws ConnectException,
	// DAOException,
	// SQLException {
	// Rezept result = null;
	//
	// result = super.getRezeptById(id);
	//
	// return result;
	// }
	//
	// public List<Rezept> getRezeptByName(String name) throws ConnectException,
	// DAOException, SQLException {
	// List<Rezept> result = null;
	//
	// result = super.getRezeptByName(name);
	//
	// return result;
	// }

	public void createKuchenrezept(Kuchenrezept kuchenrezept) throws ConnectException,
			DAOException, SQLException {
		super.createKuchenrezept(kuchenrezept);
	}

	// public void addArtikel(Artikel a, BigDecimal m, Rezept r, Mengeneinheit
	// me) throws ConnectException,
	// DAOException, SQLException {
	// this.Rezept.getId();
	// }

	// public void updateRezept(Rezept Rezept) throws ConnectException,
	// DAOException {
	// super.updateRezept(Rezept);
	// }

	
	public void saveArtikel(Kuchenrezept kuchenrezept) throws ConnectException,
			DAOException, SQLException {
		
		super.saveArtikel(kuchenrezept);	
	}

	public List<Artikel> getAllArtikelByKuchenrezeptId() throws ConnectException,
			DAOException, SQLException {
		List<Artikel> result = null;

		result = ArtikelDAO.getInstance().getAllArtikel();

		return result;
	}


	public void updateRezept(Kuchenrezept kuchenrezept) throws ConnectException,
			DAOException, SQLException {
		super.updateKuchenrezept(kuchenrezept);
	}

	
	// public void addZutat(RezeptHasArtikel rezeptHasArtikel) throws
	// ConnectException,
	// DAOException, SQLException {
	//
	// super.addZutat(rezeptHasArtikel);
	// }

}
