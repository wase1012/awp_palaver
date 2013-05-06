package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezeptart;

/**
 * @author Michael Marschall
 * 
 */

public class RezeptartDAO extends AbstractDAO {

	private final static String ID = "id";
	private final static String NAME = "name";
	private final static String TABLE = "rezeptart";

	private static final String GET_ALL_REZEPTART = "SELECT * FROM " + TABLE;
	private static final String GET_REZEPTART_BY_ID = "SELECT * FROM " + TABLE
			+ " WHERE " + ID + "={0}";
	private static final String GET_REZEPTART_BY_NAME = "SELECT * FROM "
			+ TABLE + " WHERE " + NAME + " LIKE" + " '%";
	private static final String DELETE_REZEPTART_BY_NAME = "DELETE FROM "
			+ TABLE + " WHERE " + NAME + " LIKE" + " '%";
	private static final String DELETE_REZEPTART_BY_ID = "DELETE FROM " + TABLE
			+ " WHERE id = {0}";

	private static RezeptartDAO instance = null;

	Rezeptart rezeptart;

	public RezeptartDAO() {
		super();
	}

	public static RezeptartDAO getInstance() {
		if (instance == null) {
			instance = new RezeptartDAO();
		}

		return instance;
	}

	public List<Rezeptart> getAllRezeptart() throws ConnectException,
			DAOException, SQLException {
		List<Rezeptart> list = new ArrayList<Rezeptart>();
		ResultSet set = get(GET_ALL_REZEPTART);
		while (set.next()) {
			list.add(new Rezeptart(set.getLong(ID), set.getString(NAME)));
		}
		return list;
	}

	public Rezeptart getRezeptartById(Long id) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = get(MessageFormat.format(GET_REZEPTART_BY_ID, id));
		while (set.next()) {
			rezeptart = new Rezeptart(set.getLong(ID), set.getString(NAME));
		}
		return rezeptart;
	}

	public List<Rezeptart> getRezeptartByName(String name)
			throws ConnectException, DAOException, SQLException {
		List<Rezeptart> list = new ArrayList<Rezeptart>();

		ResultSet set = get(GET_REZEPTART_BY_NAME + name + "%'");

		while (set.next()) {
			list.add(new Rezeptart(set.getLong(ID), set.getString(NAME)));
		}
		return list;
	}

	public void createRezeptart(Rezeptart rezeptart) throws ConnectException,
			DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(name) VALUES('"
				+ rezeptart.getName() + "');";
		this.put(INSERT_QUERY);
	}

	public void updateRezeptart(Rezeptart rezeptart) throws ConnectException,
			DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + NAME + "='"
				+ rezeptart.getName() + "'" + " WHERE " + ID + "='"
				+ rezeptart.getId() + "'";
		this.put(UPDATE_QUERY);
	}

	public void deleteRezeptartByName(String name) throws ConnectException,
			DAOException, SQLException {
		if (name == null) {
			throw new NullPointerException("keine Rezeptart übergeben!");
		}
		put(DELETE_REZEPTART_BY_NAME + name + "%'");
	}

	public void deleteRezeptartById(Long id) throws ConnectException,
			DAOException, SQLException {
		if (id == null) {
			throw new NullPointerException("keine Rezeptart übergeben!");
		}
		put(MessageFormat.format(DELETE_REZEPTART_BY_ID, id));
	}

}
