package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import de.hska.awp.palaver2.util.Util;

import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
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
	private final static String AUFWAND = "aufwand";
	private final static String FAVORIT = "favorit";
	private final static String ERSTELLT = "erstellt";

	private static RezeptDAO instance = null;
	private final static String GET_ALL_REZEPTS = "SELECT * FROM rezept";
	private final static String GET_REZEPT_BY_ID = "SELECT * FROM rezept WHERE id = {0}";
	private final static String GET_REZEPT_BY_NAME = "SELECT * FROM rezept WHERE rezept.name = {0}";
	private final static String GET_ARTIKEL_REZEPT_BY_ID = "Select * From artikel Join rezept_has_artikel On artikel.id = rezept_has_artikel.artikel_fk Where rezept_has_artikel.rezept_fk = {0}";
	private final static String GET_REZEPTE_BY_GESCHMACK = "SELECT * FROM rezept WHERE geschmack_fk = {0};";

	Rezept rezept;
	
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
			list.add(new Rezept(set.getLong("id"),GeschmackDAO.getInstance()
			.getGeschmackById(set.getLong("geschmack_fk")), RezeptartDAO.getInstance().getRezeptartById(
							set.getLong("rezeptart_fk")),
					MitarbeiterDAO.getInstance().getMitarbeiterById(
							set.getLong("mitarbeiter_fk")), set
							.getString("name"), null, set.getInt("portion"), set.getBoolean("aufwand"), set.getDate("erstellt"),set.getBoolean("favorit")

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
		ResultSet set = get(MessageFormat.format(GET_REZEPT_BY_ID, id));

		while (set.next()) {
			rezept = new Rezept(set.getLong("id"), RezeptartDAO.getInstance()
					.getRezeptartById(set.getLong("rezeptart_fk")), GeschmackDAO
					.getInstance().getGeschmackById(set.getLong("geschmack_fk")),
					MitarbeiterDAO.getInstance().getMitarbeiterById(
							set.getLong("mitarbeiter_fk")), set.getString("name"),
					null, set.getInt("portion"));
		}

		return rezept;
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

	public Rezept getRezeptByName1(String name) throws ConnectException,
			DAOException, SQLException {
		Rezept result = null;

		ResultSet set = get(MessageFormat.format(GET_REZEPT_BY_NAME, "name"));

		while (set.next()) {
			result = new Rezept(set.getLong("id"));
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
				+ REZEPTART + "," + KOMMENTAR + "," + PORTION + "," + GESCHMACK
				+ "," + MITARBEITER + "," + AUFWAND + "," + ERSTELLT + ","
				+ FAVORIT + ")" + " VALUES" + "('" + rezept.getName() + "','"
				+ rezept.getRezeptart().getId() + "','" + rezept.getKommentar()
				+ "','" + rezept.getPortion() + "','"
				+ rezept.getGeschmack().getId() + "','"
				+ rezept.getMitarbeiter().getId() + "','" + Util.convertBoolean(rezept.getAufwand())
				+ "','" + rezept.getErstellt() + "','" + Util.convertBoolean(rezept.getFavorit())
				+ "')";
		this.put(INSERT_QUERY);
	}

	public void ZubereitungAdd(RezeptHasZubereitung rezeptHasZubereitung)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO rezept_has_zubereitung (rezept_fk, zubereitung_fk) VALUES"
				+ "("
				+ rezeptHasZubereitung.getRezept()
				+ ", "
				+ rezeptHasZubereitung.getZubereitung() + ")";
		this.put(INSERT_QUERY);

	}

}
