package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;

public class GeschmackDAO extends AbstractDAO {

	private static GeschmackDAO instance = null;
	private final static String TABLE = "geschmack";
	private final static String ID = "id";
	private final static String NAME = "name";
	private static final String GET_GESCHMACK_BY_ID = "SELECT * FROM " + TABLE
			+ "WHERE " + ID + "= {0}";
	private final static String GET_ALL_GESCHMACK = "SELECT * FROM " + TABLE;
	private static final String GET_GESCHMACK_BY_NAME = "SELECT * FROM "
			+ TABLE + " WHERE " + NAME + " LIKE" + " '%";

	public GeschmackDAO() {
		super();
	}

	public static GeschmackDAO getInstance() {
		if (instance == null) {
			instance = new GeschmackDAO();
		}
		return instance;
	}

	public Geschmack getGeschmackById(Long id) throws ConnectException,
			DAOException, SQLException {
		Geschmack geschmack = null;
		ResultSet set = get(MessageFormat.format(GET_GESCHMACK_BY_ID, id));

		while (set.next()) {
			geschmack = new Geschmack(set.getLong(ID), set.getString(NAME));
		}
		return geschmack;
	}

	public Geschmack getGeschmackByName(Long id) throws ConnectException,
			DAOException, SQLException {
		Geschmack geschmack = null;
		ResultSet set = get(MessageFormat.format(GET_GESCHMACK_BY_ID, id));

		while (set.next()) {
			geschmack = new Geschmack(set.getLong(ID), set.getString(NAME));
		}
		return geschmack;
	}

	public List<Geschmack> getGeschmackByName(String name)
			throws ConnectException, DAOException, SQLException {
		List<Geschmack> list = new ArrayList<Geschmack>();

		ResultSet set = get(GET_GESCHMACK_BY_NAME + name + "%'");

		while (set.next()) {
			list.add(new Geschmack(set.getLong(ID), set.getString(NAME)));
		}

		return list;
	}

	public List<Geschmack> getAllGeschmack() throws ConnectException,
			DAOException, SQLException {
		List<Geschmack> list = new ArrayList<Geschmack>();

		ResultSet set = get(GET_ALL_GESCHMACK);

		while (set.next()) {
			list.add(new Geschmack(set.getLong(ID), set.getString(NAME)));
		}

		return list;
	}

	public void createGeschmack(Geschmack geschmack) throws ConnectException,
			DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(" + NAME+ "')";
		this.put(INSERT_QUERY);
	}

}