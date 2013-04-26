package de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.lieferantenverwaltung.domain.Ansprechpartner;

/**
 * @author Mihail Boehm
 * @datum 19.04.2013
 * @version 1.0
 */
public class AnsprechpartnerDAO extends AbstractDAO {
	
	private static AnsprechpartnerDAO instance = null;
	
	private final static String TABLE = "ansprechpartner";
	private final static String ID = "id";
	private final static String NAME = "name";
	private final static String TELEFON = "telefon";
	private final static String HANDY = "handy";
	private final static String FAX = "fax";
	private final static String LIEFERANT_FK = "lieferant_fk";
	private final static String GET_ALL_ANSPRECHPARTNER = "SELECT * FROM "
			+ TABLE;

	private static final String GET_ANSPRECHPARTNER_BY_ID = "SELECT * FROM "+ TABLE+" WHERE "+ID+"= {0}";
	private static final String GET_ANSPRECHPARTNER_BY_NAME = "SELECT * FROM "+ TABLE+" WHERE name= '";
	private static final String	DELETE_NACHRICHT = "DELETE FROM" + TABLE + "WHERE id = {0}";

	/**
	 * Konstruktor
	 * 
	 * @author Mihail Boehm
	 * @datum 19.04.2013
	 */
	public AnsprechpartnerDAO() {
		super();
	}

	/**
	 * @return instance
	 */
	public static AnsprechpartnerDAO getInstance() {
		if (instance == null) {
			instance = new AnsprechpartnerDAO();
		}
		return instance;
	}

	/**
	 * @author Mihail Boehm
	 * @return AnsprechpartnerListe
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 * @datum 19.04.2013
	 */
	public List<Ansprechpartner> getAllAnsprechpartner()
			throws ConnectException, DAOException, SQLException {
		List<Ansprechpartner> list = new ArrayList<Ansprechpartner>();
		ResultSet set = get(GET_ALL_ANSPRECHPARTNER);
		while (set.next()) {
			list.add(new Ansprechpartner(set.getLong(ID), 
					set.getString("name"), 
					set.getString("telefon"), 
					set.getString("handy"), 
					set.getString("fax"),
					LieferantDAO.getInstance().getLieferantById(set.getLong("lieferant_fk"))
					));
		}
		return list;
	}

	public List<Ansprechpartner> getAnsprechpartnerByName(String name)
			throws ConnectException, DAOException, SQLException {
		List<Ansprechpartner> list = new ArrayList<Ansprechpartner>();
		
		ResultSet set = get(GET_ANSPRECHPARTNER_BY_NAME + name + "'");
		
		while (set.next()) {
			list.add(new Ansprechpartner(set.getLong(ID), 
					set.getString(NAME),
					set.getString(TELEFON), 
					set.getString(HANDY), 
					set.getString(FAX),
					LieferantDAO.getInstance().getLieferantById(set.getLong(LIEFERANT_FK))
					));
		}

		return list;
	}
	
	public Ansprechpartner getAnsprechpartnerById(Long id) throws ConnectException,
			DAOException, SQLException {

		Ansprechpartner ansprechpartner = null;
		ResultSet set = get(MessageFormat.format(GET_ANSPRECHPARTNER_BY_ID, id));

		while (set.next()) {
			ansprechpartner = new Ansprechpartner(set.getLong(ID), 
					set.getString(NAME),
					set.getString(TELEFON), 
					set.getString(HANDY), 
					set.getString(FAX),
					LieferantDAO.getInstance().getLieferantById(set.getLong(LIEFERANT_FK))
					);
		}

		return ansprechpartner;
	}
	
	
	/**
	 * @author Mihail Boehm
	 * @param ansprechpartner
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 * @datum 19.04.2013
	 */
	public void createAnsprechpartner(Ansprechpartner ansprechpartner)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(" + NAME + ","
				+ TELEFON + "," + HANDY + "," + FAX	+ "," + LIEFERANT_FK +")"
				+ "VALUES" + "('" + ansprechpartner.getName() + "','"
				+ ansprechpartner.getTelefon() + "','"
				+ ansprechpartner.getHandy() + "','" + ansprechpartner.getFax()
				+ "','" + ansprechpartner.getLieferant().getId() + "')";
		this.put(INSERT_QUERY);
	}

	/**
	 * @author Christian Barth
	 * @param ansprechpartner
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 * @datum 26.04.2013
	 */
	public void updateAnsprechpartner(Ansprechpartner ansprechpartner) throws ConnectException,
	DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + NAME + "='"
		+ ansprechpartner.getName() + "'," + TELEFON + "='"
		+ ansprechpartner.getTelefon() + "'," + HANDY + "='"
		+ ansprechpartner.getHandy() + "'," + FAX + "='"
		+ ansprechpartner.getFax()+ "'," + LIEFERANT_FK + "='"
		+ ansprechpartner.getLieferant().getId() + "'" + "WHERE id='" + ansprechpartner.getId()
		+ "'";
		this.put(UPDATE_QUERY);
}
	
	public void deleteAnsprechpartner(Long id) 
			throws ConnectException, DAOException, SQLException {
		
		if(id == null) {
			throw new NullPointerException("kein Ansprechpartner übergeben");
		}		
		put(MessageFormat.format(DELETE_NACHRICHT, id));
	}
}
