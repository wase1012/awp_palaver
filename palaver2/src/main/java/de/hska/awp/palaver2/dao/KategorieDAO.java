package de.hska.awp.palaver2.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.management.openmbean.TabularType;

import com.vaadin.ui.AbstractSelect.TargetItemIs;

import de.hska.awp.palaver2.artikelverwaltung.domain.Kategorie;

/**
 * @author Mihail Boehm
 * @datum 18.04.2013
 * @version 1.0
 */
public class KategorieDAO extends AbstractDAO {

	private static KategorieDAO instance = null;
	private final static String TABLE = "kategorie";

	private final static String GET_ALL_KATEGORIES = "SELECT * FROM " + TABLE;

	/**
	 * Konstruktor
	 * 
	 * @author Mihail Boehm
	 * @datum 18.04.2013
	 */
	private KategorieDAO() {
		super();
	}

	/**
	 * @return instance
	 */
	public static KategorieDAO getInstance() {
		if (instance == null) {
			instance = new KategorieDAO();
		}
		return instance;
	}

	/**
	 * @author Mihail Boehm
	 * @return alle Kategorie zurück
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 * @datum 18.04.2013
	 */
	public List<Kategorie> getAllKategories() throws ConnectException,
			DAOException, SQLException {
		List<Kategorie> list = new ArrayList<Kategorie>();
		ResultSet set = get(GET_ALL_KATEGORIES);
		while (set.next()) {
			list.add(new Kategorie(set.getLong("id"), set.getString("name")));
		}
		return list;
	}

	/**
	 * @author Mihail Boehm
	 * @param kategorie
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 * @datum 19.04.2013
	 */
	public void createNewKategorie(Kategorie kategorie)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(name) VALUES('"
				+ kategorie.getName() + "')";
		this.put(INSERT_QUERY);
	}

	/**
	 * @author Mihail Boehm
	 * @param kategorie
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 * @datum 19.04.2013
	 */
	public void updateKategorie(Kategorie kategorie) throws ConnectException,
			DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET name='"
				+ kategorie.getName() + "' WHERE id=" + kategorie.getId()
				+ "";
		this.put(UPDATE_QUERY);
	}
}
