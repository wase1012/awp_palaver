/**
 * 
 */
package de.bistrosoft.palaver.rezeptverwaltung.service;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;
import de.bistrosoft.palaver.data.FussnoteDAO;
import de.bistrosoft.palaver.data.GeschmackDAO;
import de.bistrosoft.palaver.data.RezeptDAO;
import de.bistrosoft.palaver.data.RezeptartDAO;
import de.bistrosoft.palaver.data.ZubereitungDAO;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezeptart;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Zubereitung;


public class Fussnotenverwaltung extends FussnoteDAO {

	private static Fussnotenverwaltung instance = null;

	private Fussnotenverwaltung() {
		super();
	}

	public static Fussnotenverwaltung getInstance() {
		if (instance == null) {
			instance = new Fussnotenverwaltung();
		}
		return instance;
	}

	public List<Fussnote> getAllFussnote() throws ConnectException, DAOException,
	SQLException {
List<Fussnote> result = null;

result = super.getAllFussnote();

return result;
}


//	public void createRezept(Rezept Rezept) throws ConnectException,
//			DAOException {
//		super.createRezept(Rezept);
//	}
//
////	public void updateRezept(Rezept Rezept) throws ConnectException,
////			DAOException {
////		super.updateRezept(Rezept);
////	}
//
//	public List<Zubereitung> getAllZubereitung() throws ConnectException,
//			DAOException, SQLException {
//		List<Zubereitung> result = null;
//
//		result = ZubereitungDAO.getInstance().getAllZubereitung();
//
//		return result;
//	}
//
//	public List<Fussnote> getAllFussnote() throws ConnectException,
//			DAOException, SQLException {
//		List<Fussnote> result = null;
//
//		result = FussnoteDAO.getInstance().getAllFussnote();
//
//		return result;
//	}
//
//	public List<Rezeptart> Rezeptart() throws ConnectException, DAOException,
//			SQLException {
//		List<Rezeptart> result = null;
//
//		result = RezeptartDAO.getInstance().getAllRezeptart();
//
//		return result;
//	}
//
//	public List<Geschmack> Geschmack() throws ConnectException, DAOException,
//			SQLException {
//		List<Geschmack> result = null;
//
//		result = GeschmackDAO.getInstance().getAllGeschmack();
//
//		return result;
//	}

}
