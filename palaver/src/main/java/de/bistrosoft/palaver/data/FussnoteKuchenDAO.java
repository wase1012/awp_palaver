package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.FussnoteKuchen;

import de.hska.awp.palaver2.data.AbstractDAO;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;


public class FussnoteKuchenDAO extends AbstractDAO {

	private final static String ID = "id";
	private final static String NAME = "name";
	private static final String ABKUERZUNG = "abkuerzung";
	private final static String TABLE = "fussnotekuchen";

	private static FussnoteKuchenDAO instance = null;

	FussnoteKuchen fussnotekuchen;

	private final static String GET_ALL_FUSSNOTEKUCHEN = "SELECT * FROM " + TABLE;
	private final static String GET_FUSSNOTEKUCHEN_BY_ID = "SELECT * FROM " + TABLE
			+ " WHERE " + ID + "={0}";
	private final static String GET_FUSSNOTEKUCHEN_BY_NAME = "SELECT * FROM fussnotekuchen WHERE name = {0}";
	private static final String DELETE_FUSSNOTEKUCHEN_BY_NAME = "DELETE FROM "
			+ TABLE + " WHERE " + NAME + " LIKE" + " '%";
	private static final String DELETE_FUSSNOTEKUCHEN_BY_ID = "DELETE FROM " + TABLE
			+ " WHERE id = {0}";
	private final static String GET_FUSSNOTEKUCHEN_BY_KUCHEN = "Select fussnotekuchen.id, fussnotekuchen.name, fussnotekuchen.abkuerzung from fussnotekuchen JOIN kuchenrezept_has_fussnote On kuchenrezept_has_fussnote.fussnotekuchen_fk = fussnotekuchen.id WHERE kuchenrezept_has_fussnote.kuchenrezept_fk = {0}";

	public FussnoteKuchenDAO() {
		super();
	}

	public static FussnoteKuchenDAO getInstance() {
		if (instance == null) {
			instance = new FussnoteKuchenDAO();
		}

		return instance;
	}

	public List<FussnoteKuchen> getAllFussnoteKuchen() throws ConnectException,
			DAOException, SQLException {
		List<FussnoteKuchen> list = new ArrayList<FussnoteKuchen>();
		ResultSet set = getManaged(GET_ALL_FUSSNOTEKUCHEN);
		while (set.next()) {
			list.add(new FussnoteKuchen(set.getLong(ID), set.getString(NAME), set
					.getString(ABKUERZUNG)));
		}
		return list;
	}

	public List<FussnoteKuchen> getFussnoteKuchenByKuchen(Long id) throws ConnectException,
			DAOException, SQLException {
		List<FussnoteKuchen> list = new ArrayList<FussnoteKuchen>();
		System.out.println(MessageFormat.format(GET_FUSSNOTEKUCHEN_BY_KUCHEN,
				id));
		ResultSet set = getManaged(MessageFormat.format(GET_FUSSNOTEKUCHEN_BY_KUCHEN,
				id));
		while (set.next()) {
			list.add(new FussnoteKuchen(set.getLong(ID), set.getString(NAME), set
					.getString(ABKUERZUNG)));
		}
		return list;
	}

	public FussnoteKuchen getFussnoteKuchenById(Long id) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = getManaged(MessageFormat.format(GET_FUSSNOTEKUCHEN_BY_ID, id));
		while (set.next()) {
			fussnotekuchen = new FussnoteKuchen(set.getLong(ID), set.getString(NAME),
					set.getString(ABKUERZUNG));
		}
		return fussnotekuchen;
	}

	// public List<Fussnote> getFussnoteByName(String name)
	// throws ConnectException, DAOException, SQLException {
	// List<Fussnote> list = new ArrayList<Fussnote>();
	// ResultSet set = get(GET_FUSSNOTE_BY_NAME + name + "%'");
	// while (set.next()) {
	// list.add(new Fussnote(set.getLong(ID), set.getString(NAME), set
	// .getString(ABKUERZUNG)));
	// }
	// return list;
	// }

	public FussnoteKuchen getFussnoteKuchenByName(String fn) throws ConnectException,
			DAOException, SQLException {
		FussnoteKuchen result = null;
		fn = "'" + fn + "'";

		ResultSet set = getManaged(MessageFormat.format(GET_FUSSNOTEKUCHEN_BY_NAME,
				fn));
		while (set.next()) {
			result = new FussnoteKuchen(set.getLong("id"), set.getString("name"),
					set.getString("abkuerzung"));
		}

		return result;
	}

	public void createFussnoteKuchen(FussnoteKuchen fussnotekuchen) throws ConnectException,
			DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE
				+ "(name, abkuerzung) VALUES('" + fussnotekuchen.getBezeichnung() + "','"
				+ fussnotekuchen.getAbkuerzung() + "');";
		this.putManaged(INSERT_QUERY);
	}

	public void updateFussnoteKuchen(FussnoteKuchen fussnotekuchen) throws ConnectException,
			DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + NAME + "='"
				+ fussnotekuchen.getBezeichnung() + "'," + ABKUERZUNG + "='"
				+ fussnotekuchen.getAbkuerzung() + "'" + " WHERE " + ID + "='"
				+ fussnotekuchen.getId() + "'";
		this.putManaged(UPDATE_QUERY);
		
		
	}

	public void deleteFussnoteKuchenByName(String name) throws ConnectException,
			DAOException, SQLException {
		if (name == null) {
			throw new NullPointerException("keine Fussnote Übergeben!");
		}
		putManaged(DELETE_FUSSNOTEKUCHEN_BY_NAME + name + "%'");
	}

	public void deleteFussnoteKuchenById(Long id) throws ConnectException,
			DAOException, SQLException {
		if (id == null) {
			throw new NullPointerException("keine Fussnote Übergeben!");
		}
		putManaged(MessageFormat.format(DELETE_FUSSNOTEKUCHEN_BY_ID, id));
	}
}
