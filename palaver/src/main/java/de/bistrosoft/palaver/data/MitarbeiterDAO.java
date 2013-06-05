//package de.bistrosoft.palaver.data;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.text.MessageFormat;
//import java.util.ArrayList;
//import java.util.List;
//
//import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
//import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
//import de.hska.awp.palaver2.data.AbstractDAO;
//import de.hska.awp.palaver2.data.ConnectException;
//import de.hska.awp.palaver2.data.DAOException;
//
///*
// * @Author PhilippT
// */
//
//public class MitarbeiterDAO extends AbstractDAO {
//
//	private static MitarbeiterDAO instance = null;
//
//	private final static String TABLE = "mitarbeiter";
//	private final static String ID = "id";
//	private final static String VORNAME = "vorname";
//	private final static String NAME = "name";
//
//	private static final String GET_ALL_MITARBEITER = "SELECT * FROM " + TABLE;
//	private static final String GET_MITARBEITER_BY_ID = "SELECT * FROM mitarbeiter WHERE id = {0}";
//	private static final String GET_MITARBEITER_BY_NAME = "SELECT * FROM mitarbeiter WHERE name = {0}";
//	private static final String CREATE_MITARBEITER = "INSERT INTO mitarbeiter (`id`,`name`,`vorname`,"
//			+ "`email`,`passwort`,`eintrittsdatum`,`austrittsdatum`)VALUES({0})";
//	private final static String GET_MITARBEITER_BY_MENUE = "Select mitarbeiter.id, mitarbeiter.name, mitarbeiter.vorname from mitarbeiter JOIN menue  on menue.koch = mitarbeiter.id WHERE menue.id = {0}";
//	private final static String GET_MITARBEITER_BY_REZEPT = "Select mitarbeiter.id, mitarbeiter.name, mitarbeiter.vorname from mitarbeiter JOIN rezept  on rezept.mitarbeiter_fk = mitarbeiter.id WHERE rezept.id = {0}";
//	private final static String GET_MITARBEITER_BY_KUCHENREZEPT = "Select mitarbeiter.id, mitarbeiter.name, mitarbeiter.vorname from mitarbeiter JOIN kuchenrezept  on kuchenrezept.mitarbeiter_fk = mitarbeiter.id WHERE kuchenrezept.id = {0}";
//	
//	public MitarbeiterDAO() {
//		super();
//	}
//
//	public static MitarbeiterDAO getInstance() {
//		if (instance == null) {
//			instance = new MitarbeiterDAO();
//		}
//		return instance;
//	}
//
//	public Mitarbeiter getMitarbeiterById(Long id) throws ConnectException,
//			DAOException, SQLException {
//		Mitarbeiter mitarbeiter = null;
//		ResultSet set = getManaged(MessageFormat.format(GET_MITARBEITER_BY_ID, id));
//
//		while (set.next()) {
//			mitarbeiter = new Mitarbeiter(set.getLong("id"),
//					set.getString("name"), set.getString("vorname"));
//		}
//		return mitarbeiter;
//	}
//	public Mitarbeiter getMitarbeiterByMenue(Long id) throws ConnectException,
//	DAOException, SQLException {
//Mitarbeiter mitarbeiter = null;
//ResultSet set = getManaged(MessageFormat.format(GET_MITARBEITER_BY_MENUE, id));
//
//while (set.next()) {
//	mitarbeiter = new Mitarbeiter(set.getLong("id"),
//			set.getString("name"), set.getString("vorname"));
//}
//return mitarbeiter;
//}
//	
//	public Mitarbeiter getMitarbeiterByRezept(Long id) throws ConnectException,
//	DAOException, SQLException {
//Mitarbeiter mitarbeiter = null;
//ResultSet set = getManaged(MessageFormat.format(GET_MITARBEITER_BY_REZEPT, id));
//
//while (set.next()) {
//	mitarbeiter = new Mitarbeiter(set.getLong("id"),
//			set.getString("name"), set.getString("vorname"));
//}
//return mitarbeiter;
//}
//
//	public List<Mitarbeiter> getMitarbeiterByName(String name)
//			throws ConnectException, DAOException, SQLException {
//		List<Mitarbeiter> list = new ArrayList<Mitarbeiter>();
//
//		ResultSet set = getManaged(GET_MITARBEITER_BY_NAME + name + "'%'");
//
//		while (set.next()) {
//			list.add(new Mitarbeiter(set.getLong("id"), set.getString("name"),
//					set.getString("vorname")));
//		}
//
//		return list;
//	}
//
//	public List<Mitarbeiter> getAllMitarbeiter() throws ConnectException,
//			DAOException, SQLException {
//		List<Mitarbeiter> list = new ArrayList<Mitarbeiter>();
//
//		ResultSet set = getManaged(GET_ALL_MITARBEITER);
//
//		while (set.next()) {
//			list.add(new Mitarbeiter(set.getLong(ID), set.getString(NAME), set
//					.getString(VORNAME)));
//		}
//
//		return list;
//	}
//
//	public void createMitarbeiter(Mitarbeiter mitarbeiter)
//			throws ConnectException, DAOException, SQLException {
//		String INSERT_QUERY = "INSERT INTO " + TABLE
//				+ "(name,vorname) VALUES('" + mitarbeiter.getName() + "','"
//				+ mitarbeiter.getVorname() + "');";
//		this.putManaged(INSERT_QUERY);
//	}
//	
//	public Mitarbeiter getMitarbeiterByKuchenrezept(Long id) throws ConnectException,
//	DAOException, SQLException {
//		Mitarbeiter mitarbeiter = null;
//		ResultSet set = getManaged(MessageFormat.format(GET_MITARBEITER_BY_KUCHENREZEPT, id));
//
//		while (set.next()) {
//			mitarbeiter = new Mitarbeiter(set.getLong("id"),
//			set.getString("name"), set.getString("vorname"));
//		}
//		return mitarbeiter;
//	}
//}
