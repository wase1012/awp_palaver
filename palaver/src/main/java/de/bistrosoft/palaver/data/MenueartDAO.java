package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.menueplanverwaltung.domain.Menueart;
import de.hska.awp.palaver2.data.AbstractDAO;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;

/**
 * 
 * 
 */

public class MenueartDAO extends AbstractDAO {

	private final static String ID = "id";
	private final static String NAME = "name";
	private final static String TABLE = "menueart";

	private static final String GET_ALL_MENUEART = "SELECT * FROM " + TABLE;
	private static final String GET_MENUEART_BY_ID = "SELECT * FROM " + TABLE
			+ " WHERE " + ID + "={0}";
	private static final String GET_MENUEART_BY_NAME = "SELECT * FROM "
			+ TABLE + " WHERE " + NAME + " LIKE" + " '%";
	private static final String DELETE_MENUEART_BY_NAME = "DELETE FROM "
			+ TABLE + " WHERE " + NAME + " LIKE" + " '%";
	private static final String DELETE_MENUEART_BY_ID = "DELETE FROM " + TABLE
			+ " WHERE id = {0}";
	private static final String GET_MENUEART_BY_MENUE = "Select menueart.id, menueart.name from menueart Join menue On menue.menueart_fk = menueart.id WHERE menue.id = {0}";

	private static MenueartDAO instance = null;

	Menueart menueart;

	public MenueartDAO() {
		super();
	}

	public static MenueartDAO getInstance() {
		if (instance == null) {
			instance = new MenueartDAO();
		}

		return instance;
	}

	public List<Menueart> getAllMenueart() throws ConnectException,
			DAOException, SQLException {
		List<Menueart> list = new ArrayList<Menueart>();
		ResultSet set = getManaged(GET_ALL_MENUEART);
		while (set.next()) {
			list.add(new Menueart(set.getLong(ID), set.getString(NAME)));
		}
		return list;
	}

	public Menueart getMenueartById(Long id) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = getManaged(MessageFormat.format(GET_MENUEART_BY_ID, id));
		while (set.next()) {
			menueart = new Menueart(set.getLong("id"), set.getString("name"));
		}
		return menueart;
	}

	public Menueart getMenueartByMenue(Long id) throws ConnectException,
			DAOException, SQLException {
		ResultSet set = getManaged(MessageFormat.format(GET_MENUEART_BY_MENUE, id));
		while (set.next()) {
			menueart = new Menueart(set.getLong(ID), set.getString(NAME));
		}
		return menueart;
	}

	public List<Menueart> getMenueartByName(String name)
			throws ConnectException, DAOException, SQLException {
		List<Menueart> list = new ArrayList<Menueart>();

		ResultSet set = getManaged(GET_MENUEART_BY_NAME + name + "%'");

		while (set.next()) {
			list.add(new Menueart(set.getLong(ID), set.getString(NAME)));
		}
		return list;
	}

	public void createMenueart(Menueart menueart) throws ConnectException,
			DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(name) VALUES('"
				+ menueart.getBezeichnung() + "');";
		this.putManaged(INSERT_QUERY);
	}

	public void updateMenueart(Menueart menueart) throws ConnectException,
			DAOException, SQLException {
		String UPDATE_QUERY = "UPDATE " + TABLE + " SET " + NAME + "='"
				+ menueart.getBezeichnung() + "'" + " WHERE " + ID + "='"
				+ menueart.getId() + "'";
		this.putManaged(UPDATE_QUERY);
	}

	public void deleteMenueartByName(String name) throws ConnectException,
			DAOException, SQLException {
		if (name == null) {
			throw new NullPointerException("keine Menueart Uebergeben!");
		}
		putManaged(DELETE_MENUEART_BY_NAME + name + "%'");
	}

	public void deleteMenueartById(Long id) throws ConnectException,
			DAOException, SQLException {
		if (id == null) {
			throw new NullPointerException("keine Menueart Uebergeben!");
		}
		putManaged(MessageFormat.format(DELETE_MENUEART_BY_ID, id));
	}

}
