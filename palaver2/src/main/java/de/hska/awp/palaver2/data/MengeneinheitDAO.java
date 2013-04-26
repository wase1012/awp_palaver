package de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver2.artikelverwaltung.domain.Mengeneinheit;

/**
 * @author Mihail Boehm
 * @datum 19.04.2013
 * @version 1.0
 */
public class MengeneinheitDAO extends AbstractDAO {
	private static MengeneinheitDAO instance = null;
	private final static String TABLE = "mengeneinheit";
	private final static String GET_ALL_MENGENEINHEITEN = "SELECT * FROM "
			+ TABLE;

	/**
	 * Konstruktor
	 * 
	 * @author Mihail Boehm
	 * @datum 19.04.2013
	 */
	private MengeneinheitDAO() {
		super();
	}

	/**
	 * @return instance
	 */
	public static MengeneinheitDAO getInstance() {
		if (instance == null) {
			instance = new MengeneinheitDAO();
		}
		return instance;
	}

	/**
	 * @author Mihail Boehm
	 * @return MengeneinheitenListe
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 * @datum 19.04.2013
	 */
//	public List<Mengeneinheit> getAllMengeneinheiten() throws ConnectException,
//			DAOException, SQLException {
//		List<Mengeneinheit> list = new ArrayList<Mengeneinheit>();
//		ResultSet set = get(GET_ALL_MENGENEINHEITEN);
//		while (set.next()) {
//			list.add(new Mengeneinheit(set.getLong("id"),
//					set.getString("name"), set.getString("kurz")));
//		}
//		return list;
//	}

	/**
	 * @author Mihail Boehm
	 * @param mengeneinheit
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 * @datum 19.04.2013
	 */
	public void createNewMengeneinheit(Mengeneinheit mengeneinheit)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(name, kurz) VALUES('"
				+ mengeneinheit.getName() + "', '" + mengeneinheit.getKurz()
				+ "')";
		this.put(INSERT_QUERY);
	}

	/**
	 * @author Mihail Boehm
	 * @param mengeneinheit
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 * @datum 19.04.2013
	 */
	public void updateMengeneinheit(Mengeneinheit mengeneinheit)
			throws ConnectException, DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET name='"
				+ mengeneinheit.getName() + "', kurz='"
				+ mengeneinheit.getKurz() + "' WHERE id="
				+ mengeneinheit.getId() + "";
		this.put(UPDATE_QUERY);
	}
}
