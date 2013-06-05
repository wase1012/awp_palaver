package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.menueplanverwaltung.domain.MenueHasFussnote;
import de.bistrosoft.palaver.menueplanverwaltung.domain.MenueHasRezept;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.service.Fussnotenverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptverwaltung;
import de.bistrosoft.palaver.util.Util;
import de.hska.awp.palaver2.data.AbstractDAO;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.data.MitarbeiterDAO;

public class MenueDAO extends AbstractDAO {
	private static MenueDAO instance;
	private final String TABLE = "menue";
	private final String ID = "id";
	private final static String NAME = "name";
	private static final String MENUEART = "menueart_fk";
	private final static String KOCH = "koch";
	private final String GET_HAUPTGERICHT = "Select * from rezept join menue_has_rezept ON rezept.id = menue_has_rezept.rezept_id WHERE (menue_has_rezept.hauptgericht = true) AND (menue_has_rezept.menue_id = {0})";
	private final String GET_Beilagen = "Select * from rezept join menue_has_rezept ON rezept.id = menue_has_rezept.rezept_id WHERE (menue_has_rezept.hauptgericht = false) AND (menue_has_rezept.menue_id = {0})";
	private final String GET_ALL_MENUES = "SELECT * FROM menue";
	private final String GET_MENUE_BY_NAME = "SELECT * FROM menue WHERE menue.name = {0}";
	private static final String GET_MENUE_BY_ID = "SELECT * FROM menue WHERE id = {0}";
	private static final String GET_REZEPTE_BY_MENUE = "SELECT * FROM rezept JOIN menue_has_rezept ON rezept.id = menue_has_rezept.rezept_fk WHERE menue_has_rezept.menue_fk = {0}";

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
		ResultSet set = getManaged(GET_ALL_MENUES);

		while (set.next()) {
			list.add(new Menue(set.getLong(ID), set.getString("name"),
					MitarbeiterDAO.getInstance()
							.getMitarbeiterById(set.getLong("koch"))
							.getVorname()));

		}

		return list;
	}

	public List<Menue> getAllMenuesTabelle() throws ConnectException,
			DAOException, SQLException {
		List<Menue> list = new ArrayList<Menue>();
		ResultSet set = getManaged(GET_ALL_MENUES);

		while (set.next()) {
			list.add(new Menue(set.getLong("id"), set.getString("name"),
					MitarbeiterDAO.getInstance()
							.getMitarbeiterById(set.getLong("koch"))
							.getVorname(), MenueartDAO.getInstance()
							.getMenueartById(set.getLong("menueart_fk")),
					GeschmackDAO.getInstance().getGeschmackById(
							set.getLong("geschmack_fk"))));

		}

		return list;
	}

	public Menue getAllItemsForUpdate(Long id) throws ConnectException,
			DAOException, SQLException {
		Menue list = new Menue();
		ResultSet set = getManaged(MessageFormat.format(GET_MENUE_BY_ID, id));

		while (set.next()) {
			list = new Menue(set.getLong("id"), set.getString("name"),
					MitarbeiterDAO.getInstance()
							.getMitarbeiterById(set.getLong("koch")),
							GeschmackDAO.getInstance()
							.getGeschmackById(set.getLong("geschmack_fk")),
					MenueartDAO.getInstance().getMenueartById(
							set.getLong("menueart_fk")),
					set.getBoolean("aufwand"), set.getBoolean("favorit"));

		}
		return list;
	}

	public List<Rezept> getRezepteByMenue() throws ConnectException,
			DAOException, SQLException {
		List<Rezept> list = new ArrayList<Rezept>();
		ResultSet set = getManaged(GET_REZEPTE_BY_MENUE);

		while (set.next()) {
			list.add(new Rezept(set.getLong("id")));
		}

		return list;
	}

	public String getHauptgerichtByMenue(Long id) throws ConnectException,
			DAOException, SQLException {
		Rezept list = null;
		ResultSet set = getManaged(MessageFormat.format(GET_HAUPTGERICHT, id));

		while (set.next()) {
			list = new Rezept(set.getLong("id"), RezeptartDAO.getInstance()
					.getRezeptartById(set.getLong("rezeptart_fk")),
					MitarbeiterDAO.getInstance().getMitarbeiterById(
							set.getLong("mitarbeiter_fk")),
					set.getString("name"), null);
		}

		return list.getName();
	}

	public Rezept getHauptgerichtMenue(Long id) throws ConnectException,
			DAOException, SQLException {
		Rezept list = null;
		ResultSet set = getManaged(MessageFormat.format(GET_HAUPTGERICHT, id));

		while (set.next()) {
			list = new Rezept(set.getLong("id"), RezeptartDAO.getInstance()
					.getRezeptartById(set.getLong("rezeptart_fk")),
					MitarbeiterDAO.getInstance().getMitarbeiterById(
							set.getLong("mitarbeiter_fk")),
					set.getString("name"), null);
		}

		return list;
	}

	public List<Rezept> getBeilagenByMenue(Long id) throws ConnectException,
			DAOException, SQLException {
		List<Rezept> list = new ArrayList<Rezept>();
		ResultSet set = getManaged(MessageFormat.format(GET_Beilagen, id));

		while (set.next()) {
			list.add(new Rezept(set.getLong("id"), RezeptartDAO.getInstance()
					.getRezeptartById(set.getLong("rezeptart_fk")),
					MitarbeiterDAO.getInstance().getMitarbeiterById(
							set.getLong("mitarbeiter_fk")), set
							.getString("name"), null));
		}

		return list;
	}

	public Menue getMenueByName1(String namemenue) throws ConnectException,
			DAOException, SQLException {
		Menue result = null;
		String name = "'" + namemenue + "'";
		System.out.println(MessageFormat.format(GET_MENUE_BY_NAME, name));
		ResultSet set = getManaged(MessageFormat
				.format(GET_MENUE_BY_NAME, name));

		while (set.next()) {
			result = new Menue(set.getLong("id"), set.getString("name"),
					MitarbeiterDAO.getInstance().getMitarbeiterById(
							set.getLong("koch")));
		}
		return result;
	}

	public Menue getMenueByName(String namemenue) throws ConnectException,
			DAOException, SQLException {
		Menue result = null;
		String name = "'" + namemenue + "'";
		System.out.println(MessageFormat.format(GET_MENUE_BY_NAME, name));
		ResultSet set = getManaged(MessageFormat
				.format(GET_MENUE_BY_NAME, name));

		while (set.next()) {
			result = new Menue(set.getLong("id"), set.getString("name"),
					MitarbeiterDAO.getInstance().getMitarbeiterById(
							set.getLong("koch")), GeschmackDAO.getInstance()
							.getGeschmackById(set.getLong("geschmack_fk")),
					MenueartDAO.getInstance().getMenueartById(
							set.getLong("menueart_fk")),
					set.getBoolean("aufwand"), set.getBoolean("favorit"));
			List<Fussnote> fussnoten = Fussnotenverwaltung.getInstance()
					.getFussnoteByMenue(set.getLong("id"));
			result.setFussnoten(fussnoten);
		}
		List<Rezept> rezepte = Rezeptverwaltung.getInstance()
				.getRezepteByMenue(result);
		result.setRezepte(rezepte);
		return result;
	}

	public Menue getMenueIdByName(String name) throws ConnectException,
			DAOException, SQLException {
		Menue result = null;

		ResultSet set = getManaged(MessageFormat.format(GET_MENUE_BY_NAME,
				"name"));

		while (set.next()) {
			result = new Menue(set.getLong("id"));
		}

		return result;
	}

	public void createMenue(Menue menue) throws ConnectException, DAOException,
			SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(" + NAME + "," + KOCH
				+ ", geschmack_fk, menueart_fk, aufwand, favorit)" + " VALUES"
				+ "('" + menue.getName() + "'," + menue.getKoch().getId()
				+ ", " + menue.getGeschmack().getId() + ", "
				+ menue.getMenueart().getId() + ", "
				+ Util.convertBoolean(menue.getAufwand()) + ", "
				+ Util.convertBoolean(menue.getFavorit()) + ")";
		System.out.println(INSERT_QUERY);
		this.putManaged(INSERT_QUERY);
	}

	public void createRezeptAlsMenue(Menue menue) throws ConnectException,
			DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(" + NAME + "," + KOCH
				+ "," + MENUEART + ")" + " VALUES" + "('" + menue.getName()
				+ "'," + menue.getKoch().getId() + ",1)";
		this.putManaged(INSERT_QUERY);
	}

	public void updateMenue(Menue menue, Long mid) throws ConnectException,
			DAOException, SQLException {
		String INSERT_QUERY = "UPDATE " + TABLE + " SET " + NAME + " = '"
				+ menue.getName() + "' ," + KOCH + " = "
				+ menue.getKoch().getId() + ", geschmack_fk = "
				+ menue.getGeschmack().getId() + ", menueart_fk = "
				+ menue.getMenueart().getId() + ", aufwand = "
				+ Util.convertBoolean(menue.getAufwand()) + ", favorit = "
				+ Util.convertBoolean(menue.getFavorit())
				+ " WHERE menue.id = " + mid + ";";
		System.out.println(INSERT_QUERY);
		this.putManaged(INSERT_QUERY);
	}

	public void FussnoteAdd(MenueHasFussnote menueHasFussnote)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO menue_has_fussnote (menue_fk, fussnote_fk) VALUES"
				+ "("
				+ menueHasFussnote.getMenue()
				+ ", "
				+ menueHasFussnote.getFussnoteid() + ")";
		System.out.println(INSERT_QUERY);
		this.putManaged(INSERT_QUERY);
	}

	public void RezepteAdd(MenueHasRezept menueHasRezept)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO menue_has_rezept (menue_id, rezept_id, hauptgericht) VALUES"
				+ "("
				+ menueHasRezept.getMenue()
				+ ", "
				+ menueHasRezept.getRezept()
				// + ", true" + ")";
				+ "," + menueHasRezept.getHauptgericht() + ")";
		System.out.println(INSERT_QUERY);
		this.putManaged(INSERT_QUERY);
	}

	// /

	public void FussnoteDelete(Menue menue1) throws ConnectException,
			DAOException, SQLException {
		String DELETE_QUERY = "DELETE  from menue_has_fussnote WHERE menue_fk = "
				+ menue1.getId() + ";";

		this.putManaged(DELETE_QUERY);
	}

	public void RezepteDelete(Menue menue1, String rezeptid)
			throws ConnectException, DAOException, SQLException {
		String DELETE_QUERY = "DELETE  from menue_has_rezept WHERE menue_id = "
				+ menue1.getId() + " And rezept_id = " + rezeptid + ";";
		System.out.println(DELETE_QUERY);
		this.putManaged(DELETE_QUERY);
	}

}
