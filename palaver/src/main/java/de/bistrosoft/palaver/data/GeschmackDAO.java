package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import de.hska.awp.palaver2.data.AbstractDAO;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;

/**
 * 
 * @author Michael Marschall
 * 
 */

public class GeschmackDAO extends AbstractDAO {

	private final static String ID = "id";
	private final static String NAME = "name";
	private final static String TABLE = "geschmack";
	private final static String INAKTIV = "inaktiv";

	private static GeschmackDAO instance = null;

	Geschmack geschmack;

	private final static String GET_ALL_GESCHMACK = "SELECT * FROM " + TABLE;
	private final static String GET_ALL_GESCHMACK_AKTIV = "SELECT * FROM " + TABLE + " WHERE inaktiv = false";
	private final static String GET_GESCHMACK_BY_ID = "SELECT * FROM " + TABLE
			+ " WHERE " + ID + "={0}";
	private final static String GET_GESCHMACK_BY_NAME = "SELECT * FROM "
			+ TABLE + " WHERE name = '{0}'";
	private static final String DELETE_GESCHMACK_BY_NAME = "DELETE FROM "
			+ TABLE + " WHERE " + NAME + " LIKE" + " '%";
	private static final String DELETE_GESCHMACK_BY_ID = "DELETE FROM " + TABLE
			+ " WHERE id = {0}";
	private static final String GET_GESCHMACK_BY_REZEPT = "Select geschmack.id, geschmack.name, geschmack.inaktiv from geschmack Join rezept On rezept.geschmack_fk = geschmack.id WHERE rezept.id = {0}";

	public GeschmackDAO() {
		super();
	}

	public static GeschmackDAO getInstance() {
		if (instance == null) {
			instance = new GeschmackDAO();
		}

		return instance;
	}

	public List<Geschmack> getAllGeschmack() throws ConnectException,
			DAOException, SQLException {
		List<Geschmack> list = new ArrayList<Geschmack>();
		ResultSet set = getManaged(GET_ALL_GESCHMACK);
		while (set.next()) {
			list.add(new Geschmack(set.getLong(ID), set.getString(NAME), set
					.getBoolean(INAKTIV)));
		}
		return list;
	}

	public List<Geschmack> getAllGeschmackAktiv() throws ConnectException,
			DAOException, SQLException {
		List<Geschmack> list = new ArrayList<Geschmack>();
		ResultSet set = getManaged(GET_ALL_GESCHMACK_AKTIV);
		while (set.next()) {
			list.add(new Geschmack(set.getLong(ID), set.getString(NAME), set
					.getBoolean(INAKTIV)));
		}
		return list;
	}

	public Geschmack getGeschmackByRezept(Long id) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = getManaged(MessageFormat.format(GET_GESCHMACK_BY_REZEPT, id));
		while (set.next()) {
			geschmack = new Geschmack(set.getLong(ID), set.getString(NAME),
					set.getBoolean(INAKTIV));
		}
		return geschmack;
	}

	public Geschmack getGeschmackById(Long id) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = getManaged(MessageFormat.format(GET_GESCHMACK_BY_ID, id));
		while (set.next()) {
			geschmack = new Geschmack(set.getLong(ID), set.getString(NAME),
					null);
		}
		return geschmack;
	}

	public Geschmack getGeschmackByName1(String name) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = getManaged(MessageFormat.format(GET_GESCHMACK_BY_NAME, NAME));
		System.out.println(MessageFormat.format(GET_GESCHMACK_BY_NAME, name));
		System.out.println("geschmackbyname12" + name);
		while (set.next()) {
			geschmack = new Geschmack(set.getLong("id"));
		}
		return geschmack;

	}

	public List<Geschmack> getGeschmackByName(String name)
			throws ConnectException, DAOException, SQLException {
		List<Geschmack> list = new ArrayList<Geschmack>();
		ResultSet set = getManaged(GET_GESCHMACK_BY_NAME + name + "%'");
		while (set.next()) {
			list.add(new Geschmack(set.getLong(ID), set.getString(NAME), null));
		}
		return list;
	}

	public void createGeschmack(Geschmack geschmack) throws ConnectException,
			DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(name, inaktiv) VALUES('"
				+ geschmack.getName() + "' " + ", false)";
		this.putManaged(INSERT_QUERY);
	}

	public void updateGeschmack(Geschmack geschmack) throws ConnectException,
			DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + INAKTIV + "= '"
				+ geschmack.getInaktiv() + "' WHERE " + NAME + " = '"
				+ geschmack.getName() + "'";
		this.putManaged(UPDATE_QUERY);

	}

	public void updateGeschmackInaktiv(Geschmack geschmack)
			throws ConnectException, DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + INAKTIV + " = "
				+ true + " WHERE " + ID + " = '" + geschmack.getId() + "'";
		this.putManaged(UPDATE_QUERY);
		System.out.println(UPDATE_QUERY);
	}

	public void updateGeschmackAktiv(Geschmack geschmack)
			throws ConnectException, DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + INAKTIV + " = "
				+ false + " WHERE " + ID + " = '" + geschmack.getId() + "'";
		this.putManaged(UPDATE_QUERY);
		System.out.println(UPDATE_QUERY);
	}

	public void deleteGeschmackByName(String name) throws ConnectException,
			DAOException, SQLException {
		if (name == null) {
			throw new NullPointerException("keine Geschmack übergeben!");
		}
		putManaged(DELETE_GESCHMACK_BY_NAME + name + "%'");
	}

	public void deleteGeschmackById(Long id) throws ConnectException,
			DAOException, SQLException {
		if (id == null) {
			throw new NullPointerException("keine Geschmack übergeben!");
		}
		putManaged(MessageFormat.format(DELETE_GESCHMACK_BY_ID, id));
	}
}