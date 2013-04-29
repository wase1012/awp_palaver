package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

import de.bistrosoft.palaver.rezeptverwaltung.domain.Zubereitung;

public class ZubereitungDAO extends AbstractDAO {

	private static ZubereitungDAO instance = null;
	private final static String TABLE = "zubereitung";
	private final static String ID = "id";
	private final static String NAME = "name";
	private static final String GET_ZUBEREITUNG_BY_ID = "SELECT * FROM "
			+ TABLE + " WHERE " + ID + "= {0}";

	public ZubereitungDAO() {
		super();
	}

	public static ZubereitungDAO getInstance() {
		if (instance == null) {
			instance = new ZubereitungDAO();
		}
		return instance;
	}

	public Zubereitung getZubereitungById(Long id) throws ConnectException,
			DAOException, SQLException {
		Zubereitung zubereitung = null;
		ResultSet set = get(MessageFormat.format(GET_ZUBEREITUNG_BY_ID, id));

		while (set.next()) {
			zubereitung = new Zubereitung(set.getLong(ID), set.getString(NAME));
		}
		return zubereitung;
	}

}
