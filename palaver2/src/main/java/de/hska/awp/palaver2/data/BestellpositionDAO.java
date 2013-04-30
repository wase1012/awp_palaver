/**
 * Created by Christian Barth
 * 30.04.2013 - 14:54:02
 */
package de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellposition;

/**
 * @author Christian Barth
 *
 */
public class BestellpositionDAO extends AbstractDAO{
	
	private static BestellpositionDAO instance = null;

	private final static String TABLE = "bestellposition";
	private final static String ID = "id";
	private final static String MENGE = "menge";
	private final static String ARTIKEL_FK = "artikel_fk";
	private final static String BESTELLUNG_FK = "bestellung_fk";

	private static final String GET_BESTELLPOSITION_BY_ID = "SELECT * FROM " + TABLE
			+ " WHERE " + ID + "= {0}";
	
	private static final String GET_BESTELLPOSITIONEN_BY_BESTELLUNGID = "SELECT * FROM " + TABLE
			+ " WHERE " + BESTELLUNG_FK + "= {0}";

	public BestellpositionDAO() {
		super();
	}

	/**
	 * @return instance
	 */
	public static BestellpositionDAO getInstance() {
		if (instance == null) {
			instance = new BestellpositionDAO();
		}
		return instance;
	}

	public Bestellposition getBestellpositionById(Long id) throws ConnectException,
			DAOException, SQLException {

		Bestellposition bp = null;
		ResultSet set = get(MessageFormat.format(GET_BESTELLPOSITION_BY_ID, id));

		while (set.next()) {
			bp = new Bestellposition(set.getLong(ID), set.getInt(MENGE),
					ArtikelDAO.getInstance().getArtikelById(set.getLong(ARTIKEL_FK)),
					BestellungDAO.getInstance().getBestellungById(set.getLong(BESTELLUNG_FK)));
		}

		return bp;
	}
	
	public List<Bestellposition> getBestellpositionenByBestellungId(Long id)
			throws ConnectException, DAOException, SQLException {
		List<Bestellposition> list = new ArrayList<Bestellposition>();

		ResultSet set = get(MessageFormat.format(GET_BESTELLPOSITIONEN_BY_BESTELLUNGID, id));

		while (set.next()) {
			list.add(new Bestellposition(set.getLong(ID), set.getInt(MENGE),
					ArtikelDAO.getInstance().getArtikelById(set.getLong(ARTIKEL_FK)),
					BestellungDAO.getInstance().getBestellungById(set.getLong(BESTELLUNG_FK))));
		}

		return list;
	}
	
	public void createBestellposition(Bestellposition bestellposition) throws ConnectException,
			DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(" + MENGE + ","
				+ ARTIKEL_FK + "," + BESTELLUNG_FK + ")"
				+ "VALUES" + "('" + bestellposition.getMenge() + "','"
				+ bestellposition.getArtikel().getId() + "','"
				+ bestellposition.getBestellung().getId() + "')";
		this.put(INSERT_QUERY);
	}

	public void updateBestellposition(Bestellposition bestellposition) throws ConnectException,
			DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + MENGE + "='"
				+ bestellposition.getMenge() + "'," + ARTIKEL_FK + "='"
				+ bestellposition.getArtikel().getId() + "'," + BESTELLUNG_FK + "='"
				+ bestellposition.getBestellung().getId() + "'" + "WHERE " + ID + "='"
				+ bestellposition.getId() + "'";
		this.put(UPDATE_QUERY);
	}
}
