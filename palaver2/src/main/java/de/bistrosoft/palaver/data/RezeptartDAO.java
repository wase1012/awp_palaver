package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezeptart;

public class RezeptartDAO extends AbstractDAO {

	private static RezeptartDAO instance = null;
	private final static String TABLE = "rezeptart";
	private final static String ID = "id";
	private final static String NAME = "name";
	private static final String GET_REZEPTART_BY_ID = "SELECT * FROM " + TABLE
			+ "WHERE " + ID + "= {0}";
	private static final String GET_REZEPTART_BY_NAME = "SELECT * FROM "
			+ TABLE + " WHERE " + NAME + " LIKE" + " '%";
	private final static String GET_ALL_REZEPTART = "SELECT * FROM " + TABLE;

	public RezeptartDAO() {
		super();
	}

	public static RezeptartDAO getInstance() {
		if (instance == null) {
			instance = new RezeptartDAO();
		}
		return instance;
	}

	public Rezeptart getRezeptartById(Long id) throws ConnectException,
			DAOException, SQLException {
		Rezeptart rezeptart = null;
		ResultSet set = get(MessageFormat.format(GET_REZEPTART_BY_ID, id));

		while (set.next()) {
			rezeptart = new Rezeptart(set.getLong(ID), set.getString(NAME));
		}
		return rezeptart;
	}

	public Rezeptart getRezeptartByName(Long id) throws ConnectException,
			DAOException, SQLException {
		Rezeptart rezeptart = null;
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

	public List<Rezeptart> getAllRezeptart() throws ConnectException,
			DAOException, SQLException {
		List<Rezeptart> list = new ArrayList<Rezeptart>();

		ResultSet set = get(GET_ALL_REZEPTART);

		while (set.next()) {
			list.add(new Rezeptart(set.getLong(ID), set.getString(NAME)));
		}

		return list;
	}

	public void createRezeptart(Rezeptart rezeptart) throws ConnectException,
			DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(" + NAME + "')";
		this.put(INSERT_QUERY);
	}

}
