package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.rezeptverwaltung.domain.Zubereitung;

public class ZubereitungDAO extends AbstractDAO {

	private final static String ID = "id";
	private final static String NAME = "name";

	private static ZubereitungDAO instance = null;

	Zubereitung zubereitung;

	private final static String TABLE = "zubereitung";
	private final static String GET_ALL_ZUBEREITUNG = "SELECT * FROM " + TABLE;
	private final static String GET_ZUBEREITUNG_BY_ID = "SELECT * FROM "
			+ TABLE + " WHERE " + ID + "={0}";

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

	public void createZubereitung(Zubereitung zubereitung)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(name) VALUES('"
				+ zubereitung.getName() + "');";
		this.put(INSERT_QUERY);
	}

	// public void deleteZubereitung{
	// ???
	// }

}
