package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.rezeptverwaltung.domain.Zubereitung;

/**
 * @author Michael Marschall
 * 
 */

public class ZubereitungDAO extends AbstractDAO {

	private final static String ID = "id";
	private final static String NAME = "name";
	private final static String TABLE = "zubereitung";

	private static ZubereitungDAO instance = null;

	Zubereitung zubereitung;

	private final static String GET_ALL_ZUBEREITUNG = "SELECT * FROM " + TABLE;
	private final static String GET_ZUBEREITUNG_BY_ID = "SELECT * FROM "
			+ TABLE + " WHERE " + ID + "={0}";
	private final static String GET_ZUBEREITUNG_BY_NAME = "SELECT * FROM "
			+ TABLE + " WHERE " + NAME + " LIKE" + " '%";
	private static final String DELETE_ZUBEREITUNG_BY_NAME = "DELETE FROM "
			+ TABLE + " WHERE " + NAME + " LIKE" + " '%";
	private static final String DELETE_ZUBEREITUNG_BY_ID = "DELETE FROM " + TABLE
			+ " WHERE id = {0}";
	public ZubereitungDAO() {
		super();
	}

	public static ZubereitungDAO getInstance() {
		if (instance == null) {
			instance = new ZubereitungDAO();
		}

		return instance;
	}

	public List<Zubereitung> getAllZubereitung() throws ConnectException,
			DAOException, SQLException {
		List<Zubereitung> list = new ArrayList<Zubereitung>();
		ResultSet set = get(GET_ALL_ZUBEREITUNG);
		while (set.next()) {
			list.add(new Zubereitung(set.getLong(ID), set.getString(NAME)));
		}
		return list;
	}

	public Zubereitung getZubereitungById(Long id) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = get(MessageFormat.format(GET_ZUBEREITUNG_BY_ID, id));
		while (set.next()) {
			zubereitung = new Zubereitung(set.getLong(ID), set.getString(NAME));
		}
		return zubereitung;
	}

	public List<Zubereitung> getZubereitungByName(String name)
			throws ConnectException, DAOException, SQLException {
		List<Zubereitung> list = new ArrayList<Zubereitung>();

		ResultSet set = get(GET_ZUBEREITUNG_BY_NAME + name + "%'");

		while (set.next()) {
			list.add(new Zubereitung(set.getLong(ID), set.getString(NAME)));
		}
		return list;
	}

	public void createZubereitung(Zubereitung zubereitung)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(name) VALUES('"
				+ zubereitung.getName() + "');";
		this.put(INSERT_QUERY);
	}

	public void updateZubereitung(Zubereitung zubereitung)
			throws ConnectException, DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + NAME + "='"
				+ zubereitung.getName() + "'" + " WHERE " + ID + "='"
				+ zubereitung.getId() + "'";
		this.put(UPDATE_QUERY);
	}

	public void deleteZubereitungByName(String name) throws ConnectException,
			DAOException, SQLException {
		if (name == null) {
			throw new NullPointerException("keine Zubereitung übergeben!");
		}
		put(DELETE_ZUBEREITUNG_BY_NAME + name + "%'");
	}
	
	public void deleteZubereitungById(Long id) throws ConnectException,
	DAOException, SQLException {
		if(id==null){
			throw new NullPointerException("keine Zubereitung übergeben!");
		}
		put(MessageFormat.format(DELETE_ZUBEREITUNG_BY_ID, id));
	}
}
