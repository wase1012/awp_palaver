package de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.artikelverwaltung.domain.Mengeneinheit;

/**
 * @author Mihail Boehm
 * @datum 19.04.2013
 * @version 1.0
 */
public class MengeneinheitDAO extends AbstractDAO {
	
	private final static String TABLE = "mengeneinheit";
	private static MengeneinheitDAO instance = null;
	private final static String GET_ALL_MENGENEINHEITEN = "SELECT * FROM "+ TABLE;
	private final static String GET_MENGENEINHEIT_BY_ID = "SELECT * FROM Mengeneinheit WHERE id = {0}";
	private final static String GET_MENGENEINHEIT_BY_NAME = "SELECT * FROM Mengeneinheit WHERE name = '";
	private final static String PUT_MENGENEINHEIT = "INSERT into Mengeneinheit (name, kurz) values (";

	/**
	 * Konstruktor
	 * 
	 * @author Mihail Boehm
	 * @datum 19.04.2013
	 */
	public MengeneinheitDAO() {
		super();
	}
	
	/**
	 * @return instance
	 */
	public static MengeneinheitDAO getInstance() {
		if (instance == null) {
			instance = new MengeneinheitDAO();
		}
		return instance;
	}

	/**
	 * @author Mihail Boehm
	 * @return MengeneinheitenListe
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 * @datum 19.04.2013
	 */
	public List<Mengeneinheit> getAllMengeneinheit() throws ConnectException, DAOException, SQLException
	{
		List<Mengeneinheit> list = new ArrayList<Mengeneinheit>();
		ResultSet set = getManaged(GET_ALL_MENGENEINHEITEN);	
		
		while(set.next())
		{
			list.add(new Mengeneinheit(set.getLong("id"),
								set.getString("name"),
								set.getString("kurz")
								));
		}
		return list;
	}
	
	public List<Mengeneinheit> getMengeneinheitByName(String name)
			throws ConnectException, DAOException, SQLException {
		List<Mengeneinheit> list = new ArrayList<Mengeneinheit>();
		
		ResultSet set = getManaged(GET_MENGENEINHEIT_BY_NAME + name + "'");
		
		while (set.next()) {
			list.add(new Mengeneinheit(set.getLong("id"), 
					set.getString("name"),
					set.getString("kurz")));
		}

		return list;
	}

	/**
	 * @author Mihail Boehm
	 * @param mengeneinheit
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 * @datum 19.04.2013
	 */
	public void createNewMengeneinheit(Mengeneinheit mengeneinheit)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(name, kurz) VALUES('"
				+ mengeneinheit.getName() + "', '" + mengeneinheit.getKurz()
				+ "')";
		putManaged(INSERT_QUERY);
	}

	/**
	 * @author Mihail Boehm
	 * @param mengeneinheit
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 * @datum 19.04.2013
	 */
	public void updateMengeneinheit(Mengeneinheit mengeneinheit)
			throws ConnectException, DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET name='"
				+ mengeneinheit.getName() + "', kurz='"
				+ mengeneinheit.getKurz() + "' WHERE id="
				+ mengeneinheit.getId() + "";
		putManaged(UPDATE_QUERY);
	}

	public Mengeneinheit getMengeneinheitById(Long id) throws ConnectException,
	DAOException, SQLException {

		Mengeneinheit me = null;
		ResultSet set = getManaged(MessageFormat.format(GET_MENGENEINHEIT_BY_ID, id));

		while (set.next()) {
			me = new Mengeneinheit(set.getLong("id"), set.getString("name"),
			set.getString("kurz"));
		}

		return me;
	}
}
