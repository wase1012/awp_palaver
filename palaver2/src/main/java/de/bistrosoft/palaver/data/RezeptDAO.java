package de.bistrosoft.palaver.data;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.artikelverwaltung.domain.Artikel;
import de.bistrosoft.palaver.artikelverwaltung.domain.Mengeneinheit;
import de.bistrosoft.palaver.artikelverwaltung.service.Artikelverwaltung;
import de.bistrosoft.palaver.artikelverwaltung.service.Mengeneinheitverwaltung;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.menueplanverwaltung.domain.MenueHasFussnote;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasArtikel;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasZubereitung;

public class RezeptDAO extends AbstractDAO {

	private final static String TABLE = "rezept";
	private final static String ID = "id";
	private final static String NAME = "name";
	private final static String GESCHMACK = "geschmack_fk";
	private final static String REZEPTART = "rezeptart_fk";
	private final static String KOMMENTAR = "kommentar";
	private final static String PORTION = "portion";
	private final static String MITARBEITER = "mitarbeiter_fk";
	private final static String ARTIKEL = "artikel_fk";
	private final static String MENGE = "menge";
	private final static String REZEPT_FK = "rezept_fk";
	private final static String MENGENEINHEIT = "mengeneinheit";

	private static RezeptDAO instance = null;
	private final static String GET_ALL_REZEPTS = "SELECT * FROM rezept";
	private final static String GET_REZEPT_BY_ID = "SELECT * FROM rezept WHERE id = {0}";
	private final static String GET_REZEPT_BY_NAME = "SELECT * FROM rezept WHERE name = {0}";
	private final static String PUT_REZEPT = "INSERT INTO rezept(`name`,"
			+ "`rezeptart_fk`," + "`kommentar`,`" + "`portion`,"
			+ "`geschmack_fk`," + "`mitarbeiter_fk`)VALUES({0})";
	private final static String UPDATE_REZEPT = "UPDATE rezept SET `name` = {0}, `rezeptart_fk` = {1},`kommentar` = {2},`portion` = {3},`geschmack_fk` = {4},`mitarebietr_fk` = {5} WHERE id = {6} ";
	private final static String GET_GESCHMACK_NAME = "SELECT geschmack.name FROM geschmack JOIN rezept ON geschmack.id = rezept.geschmack_fk WHERE rezept.id = {0};";
	private final static String GET_FUSSNOTEN_REZEPT = " SELECT fussnote.name FROM fussnote JOIN rezept_has_fussnote ON rezept_has_fussnote.fussnote_fk = fussnote.id WHERE rezept_has_fussnote.rezept_fk = {0};";
	private final static String GET_ZUTATEN_REZEPT = "SELECT artikel.name FROM artikel JOIN rezept_has_artikel ON artikel.id = rezept_has_artikel.artikel_fk JOIN rezept ON rezept.id = rezept_has_artikel.rezept_fk WHERE rezept.id = {0};";
	private final static String GET_ZUBEREITUNG_NAME = "SELECT zubereitung.name FROM zubereitung JOIN rezept_has_zubereitung ON rezept_has_zubereitung.rezept_fk = zubereitung.id WHERE rezept_has_zubereitung.rezept_fk = {0};";
	private final static String GET_REZEPTART_NAME = "SELECT rezeptart.name FROM rezeptart JOIN rezept ON rezept.rezeptart_fk = rezeptart.id WHERE rezept.id = {0};";
	private final static String GET_ARTIKEL_REZEPT_BY_ID = "Select * From artikel Join rezept_has_artikel On artikel.id = rezept_has_artikel.artikel_fk Where rezept_has_artikel.rezept_fk = {0}";
	private final static String GET_ARTIKEL_BY_REZEPT = "SELECT * FROM rezept_has_artikel WHERE rezept_fk={0}";
	private final static String CREATE_REZEPT_HAS_ARTIKEL = "INSERT INTO rezept_has_artikel (REZEPT_FK, ARTIKEL_FK, MENGE, EINHEIT) VALUES({0},{1},{2},{3})";
	private final static String GET_REZEPTE_BY_GESCHMACK = "SELECT * FROM rezept WHERE geschmack_fk = {0};";

	public RezeptDAO() {
		super();
	}

	public static RezeptDAO getInstance() {
		if (instance == null) {
			instance = new RezeptDAO();
		}
		return instance;
	}

	// public List<RezeptHasArtikel> getArtikelByRezept(Rezept rezept) throws
	// ConnectException, DAOException, SQLException{
	// List<RezeptHasArtikel> list=null;
	// Long rezeptId = rezept.getId();
	// ResultSet set =
	// get(MessageFormat.format(GET_ARTIKEL_BY_REZEPT,rezeptId));
	// while (set.next()) {
	// Artikelverwaltung av=Artikelverwaltung.getInstance();
	//
	// RezeptHasArtikel rha = new RezeptHasArtikel();
	// Artikel art = av.getArtikelById(set.getLong("artikel_fk"));
	// rha.setArtikel(art);
	// rha.setMenge(set.getBigDecimal("menge"));
	// Mengeneinheit me = Mengeneinheitverwaltung.getInstance()
	// .getMengeneinheitById(set.getLong("mengeneinheit"));
	// rha.setMengeneinheit(me);
	// }
	// return list;
	// }
	//
	// public void createArtikelForRezept(Rezept rezept) throws
	// ConnectException, DAOException{
	// List<RezeptHasArtikel> artikel = rezept.getArtikel();
	// Long rezeptId = rezept.getId();
	//
	// for(RezeptHasArtikel rha : artikel){
	// Long artikelId = rha.getArtikel().getId();
	// BigDecimal menge = rha.getMenge();
	// Long meId = rha.getMengeneinheit().getId();
	//
	// put(MessageFormat.format(CREATE_REZEPT_HAS_ARTIKEL,
	// rezeptId,artikelId,menge, meId ));
	// }
	//
	//
	// }

	public List<Rezept> getAllRezepte() throws ConnectException, DAOException,
			SQLException {
		List<Rezept> list = new ArrayList<Rezept>();
		ResultSet set = get(GET_ALL_REZEPTS);
		;
		while (set.next()) {
			list.add(new Rezept(RezeptartDAO.getInstance().getRezeptartById(
					set.getLong("rezeptart_fk")), GeschmackDAO.getInstance()
					.getGeschmackById(set.getLong("geschmack_fk")),
					MitarbeiterDAO.getInstance().getMitarbeiterById(
							set.getLong("mitarbeiter_fk")), set
							.getString("name"), null, set.getInt("portion")

			));
		}
		return list;
	}

	public List<Rezept> getAllRezepteM() throws ConnectException, DAOException,
			SQLException {
		List<Rezept> list = new ArrayList<Rezept>();
		ResultSet set = get(GET_ALL_REZEPTS);
		;
		while (set.next()) {
			list.add(new Rezept(set.getLong("id"), RezeptartDAO.getInstance()
					.getRezeptartById(set.getLong("rezeptart_fk")),
					GeschmackDAO.getInstance().getGeschmackById(
							set.getLong("geschmack_fk")), MitarbeiterDAO
							.getInstance().getMitarbeiterById(
									set.getLong("mitarbeiter_fk")), set
							.getString("name"), null, set.getInt("portion")

			));
		}
		return list;
	}

	// public List<Rezept> getRezeptById(long rezeptID) throws ConnectException,
	// DAOException, SQLException {
	// List<Rezept> rezept = new ArrayList<Rezept>();
	// ResultSet set = get(GET_REZEPT_BY_ID);
	// while (set.next()) {
	// rezept.add(new Rezept(RezeptartDAO.getInstance().getRezeptartById(
	// set.getLong("rezeptart")), GeschmackDAO.getInstance()
	// .getGeschmackById(set.getLong("geschmack")), MitarbeiterDAO
	// .getInstance().getMitarbeiterById(set.getLong("mitarbeiter")), set
	// .getString("name"), null, set.getInt("portion")));
	// }
	// return rezept;
	// }

	public Rezept getRezeptById(Long id) throws ConnectException, DAOException,
			SQLException {
		Rezept result = new Rezept();
		ResultSet set = get(MessageFormat.format(GET_REZEPT_BY_ID, id));

		while (set.next()) {
			result = new Rezept(set.getLong("id"), RezeptartDAO.getInstance()
					.getRezeptartById(set.getLong("rezeptart")), GeschmackDAO
					.getInstance().getGeschmackById(set.getLong("geschmack")),
					MitarbeiterDAO.getInstance().getMitarbeiterById(
							set.getLong("mitarbeiter")), set.getString("name"),
					null, set.getInt("portion"));
		}

		return result;
	}

	public Rezept getRezept1(Long id) throws ConnectException, DAOException,
			SQLException {
		Rezept result = null;
		ResultSet set = get(MessageFormat.format(GET_REZEPT_BY_ID, id));

		while (set.next()) {
			result = new Rezept(set.getLong("id"));
		}

		return result;
	}

	public Rezept getRezeptByName(String namerezept) throws ConnectException,
			DAOException, SQLException {
		Rezept result = null;

		ResultSet set = get(MessageFormat.format(GET_REZEPT_BY_NAME, NAME));

		while (set.next()) {
			result = new Rezept(set.getLong("id"), RezeptartDAO.getInstance()
					.getRezeptartById(set.getLong("rezeptart")), GeschmackDAO
					.getInstance().getGeschmackById(set.getLong("geschmack")),
					MitarbeiterDAO.getInstance().getMitarbeiterById(
							set.getLong("mitarbeiter")), set.getString("name"),
					null, set.getInt("portion"));
		}

		return result;
	}

	public List<Rezept> getAllArtikelByRezeptId(int rezeptID)
			throws ConnectException, DAOException, SQLException {
		List<Rezept> rezept = new ArrayList<Rezept>();
		ResultSet set = get(GET_ARTIKEL_REZEPT_BY_ID);
		while (set.next()) {
			rezept.add(new Rezept(RezeptartDAO.getInstance().getRezeptartById(
					set.getLong("id")), GeschmackDAO.getInstance()
					.getGeschmackById(set.getLong("id")), MitarbeiterDAO
					.getInstance().getMitarbeiterById(set.getLong("id")), set
					.getString("name"), null, set.getInt("portion")));
		}
		return rezept;
	}

	public List<Rezept> getRezeptebyGeschmack(Geschmack geschmack)
			throws ConnectException, DAOException, SQLException {
		List<Rezept> rezept = new ArrayList<Rezept>();
		ResultSet set = get(GET_REZEPTE_BY_GESCHMACK);
		while (set.next()) {
			rezept.add(new Rezept(RezeptartDAO.getInstance().getRezeptartById(
					set.getLong("id")), GeschmackDAO.getInstance()
					.getGeschmackById(set.getLong("geschmack_fk")),
					MitarbeiterDAO.getInstance().getMitarbeiterById(
							set.getLong("mitarbeiter_fk")), set
							.getString("name"), null, set.getInt("portion")));
		}
		return rezept;

	}

	//
	// public void addZutat(RezeptHasArtikel rezeptHasArtikel)throws
	// ConnectException,
	// DAOException, SQLException {
	// String INSERT_QUERY = "INSERT INTO rezept_has_artikel (" + REZEPT_FK +
	// ","
	// + ARTIKEL + "," + MENGE + "," + MENGENEINHEIT +")" + "VALUES" + "('" +
	// rezeptHasArtikel.getRezept().getId()
	// + "','" + rezeptHasArtikel.getArtikel().getId() + "','"
	// + rezeptHasArtikel.getMenge() + "','" +
	// rezeptHasArtikel.getMenge().getId() + "')";
	// this.put(INSERT_QUERY);
	//
	// }

	public void createRezept(Rezept rezept) throws ConnectException,
			DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(" + NAME + ","
				+ GESCHMACK + "," + REZEPTART + "," + KOMMENTAR + "," + PORTION
				+ "," + MITARBEITER + ")" + "VALUES" + "('" + rezept.getName()
				+ "','" + rezept.getGeschmack().getId() + "','"
				+ rezept.getRezeptart().getId() + "','" + rezept.getKommentar()
				+ "','" + rezept.getPortion() + "','"
				+ rezept.getMitarbeiter().getId() + "')";
		this.put(INSERT_QUERY);
	}

	public void ZubereitungAdd(RezeptHasZubereitung rezeptHasZubereitung)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO rezept_has_fussnote (rezept_fk, zubereitung_fk) VALUES"
				+ "("
				+ rezeptHasZubereitung.getRezept()
				+ ", "
				+ rezeptHasZubereitung.getZubereitung() + ")";
		this.put(INSERT_QUERY);

	}

}
