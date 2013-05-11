/**
 * Created by Elena W
 */

package de.hska.awp.palaver2.bestellverwaltung.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.data.BestellpositionDAO;
import de.hska.awp.palaver2.util.BestellungData;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellposition;

/**
 * Die Klasse ermöglicht die Verwaltung der BEstellpositionen und stellt für die GUI
 * Methoden bereit.
 * 
 * @author Elena W
 */

public class Bestellpositionverwaltung extends BestellpositionDAO {
	
	private static Bestellpositionverwaltung instance = null;

	private Bestellpositionverwaltung() {
		super();
	}

	public static Bestellpositionverwaltung getInstance() {
		if (instance == null) {
			instance = new Bestellpositionverwaltung();
		}
		return instance;
	}
	/**
	 * Die Methode erzeugt eine Bestellposition.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	public void createBestellposition(Bestellposition bestellposition) throws ConnectException,
			DAOException, SQLException {

		try {
			super.createBestellposition(bestellposition);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Die Methode aktualisiert eine Bestellposition.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	public void updateBestellposition(Bestellposition bestellposition) throws ConnectException,
			DAOException, SQLException {

		super.updateBestellposition(bestellposition);

	}
	/**
	 * Die Methode liefert eine Bestellposition anhand des Parameter id zurück.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws ConnectException
	 */
	public Bestellposition getBestellpositionById(Long id) throws ConnectException,
			DAOException, SQLException {
		Bestellposition bestellposition = super.getBestellpositionById(id);
		return bestellposition;
	}
	
	public List<Bestellposition> getBestellpositionen(List<BestellungData> bestData) {
		List<Bestellposition> bpen = new ArrayList<Bestellposition>();
		Bestellposition bp = new Bestellposition();
		 for( int i = 0; i < bestData.size(); ++i){
			 bp.setArtikel(bestData.get(i).getBestellungArtikel());
			 bp.setDurchschnitt(Integer.parseInt(bestData.get(i).getDurchschnitt().getValue()));
			 bp.setKantine(Integer.parseInt(bestData.get(i).getKantine().getValue()));
			 bp.setGesamt(Integer.parseInt(bestData.get(i).getGesamt().getValue()));
			 bp.setFreitag(bestData.get(i).getFreitag().getValue());
			 bp.setMontag(bestData.get(i).getMontag().getValue());
			 bpen.add(bp);
		 }
		 return bpen;
	}
}
