package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.menueplanverwaltung.domain.MenueHasFussnote;
import de.bistrosoft.palaver.menueplanverwaltung.domain.MenueHasRezept;

public class MenueDAO extends AbstractDAO {
	private static MenueDAO instance;
	private final String TABLE = "menue";
	private final String ID = "id";
	private final static String NAME = "name";
	private final static String KOCH = "koch";

	private final String GET_ALL_MENUES = "SELECT * FROM menue";
	private final String GET_MENUE_BY_NAME = "SELECT * FROM menue WHERE menue.name = {0} ";
	private static final String GET_MENUE_BY_ID = "SELECT * FROM menue WHERE id = {0}";

	public MenueDAO() {
		super();
	}

	public static MenueDAO getInstance() {
		if (instance == null) {
			instance = new MenueDAO();
		}
		return instance;
	}

	public List<Menue> getAllMenues() throws ConnectException, DAOException,
			SQLException {
		List<Menue> list = new ArrayList<Menue>();
		ResultSet set = get(GET_ALL_MENUES);

		while (set.next()) {
			list.add(new Menue(set.getLong(ID), set.getString("name"),
					MitarbeiterDAO.getInstance()
							.getMitarbeiterById(set.getLong("koch"))
							.getVorname()));
		}

		return list;
	}

	public Menue getMenueByName(String namemenue) throws ConnectException,
			DAOException, SQLException {
		Menue result = null;

		ResultSet set = get(MessageFormat.format(GET_MENUE_BY_NAME, NAME));

		while (set.next()) {
			result = new Menue(set.getLong("id"), set.getString("name"),
					MitarbeiterDAO.getInstance().getMitarbeiterById(
							set.getLong("koch")));
		}

		return result;
	}

	public void createMenue(Menue menue) throws ConnectException, DAOException,
			SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(" + NAME + "," + KOCH
				+ ")" + "VALUES" + "('" + menue.getName() + "','"
				+ menue.getKoch().getId() + "')";
		this.put(INSERT_QUERY);
	}

	public void FussnoteAdd(MenueHasFussnote menueHasFussnote)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO menue_has_fussnote (menue_fk, fussnote_fk) VALUES"
				+ "("
				+ menueHasFussnote.getMenue()
				+ ", "
				+ menueHasFussnote.getFussnote() + ")";
		this.put(INSERT_QUERY);
	}

	public void RezepteAdd(MenueHasRezept menueHasRezept)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO menue_has_rezept (menue_id, rezept_id) VALUES"
				+ "("
				+ menueHasRezept.getMenue()
				+ ", "
				+ menueHasRezept.getRezept() + ")";
		this.put(INSERT_QUERY);
	}

}
