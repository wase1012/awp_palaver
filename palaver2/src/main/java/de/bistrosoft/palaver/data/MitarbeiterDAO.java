package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;

/*
 * @Author PhilippT
 */

public class MitarbeiterDAO extends AbstractDAO {

	private static MitarbeiterDAO instance = null;

	private final static String TABLE = "geschmack";
	private final static String ID = "id";
	private final static String VORNAME = "vorname";
	private final static String NAME = "name";

	private static final String GET_ALL_MITARBEITER = "SELECT * FROM " + TABLE;
	private static final String GET_MITARBEITER_BY_ID = "SELECT * FROM Mitarbeiter WHERE id = {0}";
	private static final String GET_MITARBEITER_BY_NAME = "SELECT * FROM Mitarbeiter WHERE name = {0}";
	private static final String CREATE_MITARBEITER = "INSERT INTO Mitarbeiter (`id`,`name`,`vorname`,"
			+ "`email`,`passwort`,`eintrittsdatum`,`austrittsdatum`)VALUES({0})";

	public MitarbeiterDAO() {
		super();
	}

	public static MitarbeiterDAO getInstance() {
		if (instance == null) {
			instance = new MitarbeiterDAO();
		}
		return instance;
	}

	public Mitarbeiter getMitarbeiterById(Long id) throws ConnectException,
			DAOException, SQLException {
		Mitarbeiter mitarbeiter = null;
		ResultSet set = get(MessageFormat.format(GET_MITARBEITER_BY_ID, id));

		while (set.next()) {
			mitarbeiter = new Mitarbeiter(set.getLong("id"),
					set.getString("name"), set.getString("vorname"));
		}
		return mitarbeiter;
	}

	public List<Mitarbeiter> getMitarbeiterByName(String name)
			throws ConnectException, DAOException, SQLException {
		List<Mitarbeiter> list = new ArrayList<Mitarbeiter>();

		ResultSet set = get(GET_MITARBEITER_BY_NAME + name + "'%'");

		while (set.next()) {
			list.add(new Mitarbeiter(set.getLong("id"), set.getString("name"),
					set.getString("vorname")));
		}

		return list;
	}

	public List<Mitarbeiter> getAllMitarbeiter() throws ConnectException,
			DAOException, SQLException {
		List<Mitarbeiter> list = new ArrayList<Mitarbeiter>();

		ResultSet set = get(GET_ALL_MITARBEITER);

		while (set.next()) {
			list.add(new Mitarbeiter(set.getLong(ID), set.getString(NAME), set
					.getString(VORNAME)));
		}

		return list;
	}

	public void createMitarbeiter(Mitarbeiter mitarbeiter)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE
				+ "(name,vorname) VALUES('" + mitarbeiter.getName() + "','"
				+ mitarbeiter.getVorname() + "');";
		this.put(INSERT_QUERY);
	}
}
