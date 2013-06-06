package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasArtikel;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasZubereitung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezeptart;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Zubereitung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptartverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Zubereitungverwaltung;
import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver2.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver2.artikelverwaltung.service.Mengeneinheitverwaltung;
import de.hska.awp.palaver2.data.AbstractDAO;
import de.hska.awp.palaver2.data.ArtikelDAO;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.data.MengeneinheitDAO;
import de.hska.awp.palaver2.data.MitarbeiterDAO;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.service.Mitarbeiterverwaltung;

public class RezeptDAO extends AbstractDAO {

	private final static String TABLE = "rezept";
	private final static String ID = "id";
	private final static String NAME = "name";
	private final static String REZEPTART = "rezeptart_fk";
	private final static String KOMMENTAR = "kommentar";
	private final static String MITARBEITER = "mitarbeiter_fk";
	private final static String ERSTELLT = "erstellt";
	private final static String REZEPTHASARTIKEL = "rezept_has_artikel";
	private final static String REZEPTFK = "rezept_fk";
	private final static String MENUE = "menue";

	private static RezeptDAO instance = null;
	private final static String GET_ALL_REZEPTS = "SELECT * FROM rezept";
	private final static String GET_ALL_HAUPTGERICHTE = "SELECT * FROM rezept WHERE rezept.rezeptart_fk = 1";
	private final static String GET_ALL_BEILAGEN = "SELECT * FROM rezept WHERE rezept.rezeptart_fk = 2";
	private final static String GET_REZEPT_BY_ID = "SELECT * FROM rezept WHERE id = {0}";
	private final static String GET_REZEPT_BY_NAME = "SELECT * FROM rezept WHERE rezept.name = {0}";
	private final static String GET_ARTIKEL_REZEPT_BY_ID = "Select * From artikel Join rezept_has_artikel On artikel.id = rezept_has_artikel.artikel_fk Where rezept_has_artikel.rezept_fk = {0}";
	private final static String SAVE_ARTIKEL = "INSERT INTO rezept_has_artikel VALUES ({0},{1},{2},{3})";
	private final static String GET_REZEPTHASARTIKEL_BY_REZEPT_ID = "SELECT * FROM "
			+ REZEPTHASARTIKEL + " WHERE " + REZEPTFK + " = {0}";
	private static final String GET_ALL_REZEPT_TABELLE = "SELECT * FROM "
			+ TABLE;

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

	public List<Rezept> getAllRezepte() throws ConnectException, DAOException,
			SQLException {
		List<Rezept> list = new ArrayList<Rezept>();
		ResultSet set = getManaged(GET_ALL_REZEPTS);
		while (set.next()) {
			list.add(new Rezept(set.getLong("id"), RezeptartDAO.getInstance()
					.getRezeptartById(set.getLong("rezeptart_fk")),
					MitarbeiterDAO.getInstance().getMitarbeiterById(
							set.getLong("mitarbeiter_fk")), set
							.getString("name"), set.getString("kommentar")));
		}
		return list;
	}

	public List<Rezept> getAllHauptgerichte() throws ConnectException,
			DAOException, SQLException {
		List<Rezept> list = new ArrayList<Rezept>();
		ResultSet set = getManaged(GET_ALL_HAUPTGERICHTE);
		while (set.next()) {
			list.add(new Rezept(set.getLong("id"), RezeptartDAO.getInstance()
					.getRezeptartById(set.getLong("rezeptart_fk")),
					MitarbeiterDAO.getInstance().getMitarbeiterById(
							set.getLong("mitarbeiter_fk")), set
							.getString("name"), set.getString("kommentar")));
		}
		return list;
	}

	public List<Rezept> getAllBeilagen() throws ConnectException, DAOException,
			SQLException {
		List<Rezept> list = new ArrayList<Rezept>();
		ResultSet set = getManaged(GET_ALL_BEILAGEN);
		while (set.next()) {
			list.add(new Rezept(set.getLong("id"), RezeptartDAO.getInstance()
					.getRezeptartById(set.getLong("rezeptart_fk")),
					MitarbeiterDAO.getInstance().getMitarbeiterById(
							set.getLong("mitarbeiter_fk")), set
							.getString("name"), set.getString("kommentar")));
		}
		return list;
	}

	public List<Rezept> getAllRezepteTabelle() throws ConnectException,
			DAOException, SQLException {
		List<Rezept> list = new ArrayList<Rezept>();
		ResultSet set = getManaged(GET_ALL_REZEPT_TABELLE);

		while (set.next()) {
			Rezept rezept = new Rezept(set.getLong("id"), RezeptartDAO
					.getInstance()
					.getRezeptartById(set.getLong("rezeptart_fk")),
					MitarbeiterDAO.getInstance().getMitarbeiterById(
							set.getLong("mitarbeiter_fk")),
					set.getString("name"), set.getString("kommentar"),
					set.getDate("erstellt"));
			List<Zubereitung> zubereitung = Zubereitungverwaltung.getInstance()
					.getZubereitungByRezept(rezept.getId());
			rezept.setZubereitung(zubereitung);
			list.add(rezept);

		}
		return list;
	}

	public List<Rezept> getAllRezepteM() throws ConnectException, DAOException,
			SQLException {
		List<Rezept> list = new ArrayList<Rezept>();
		ResultSet set = getManaged(GET_ALL_REZEPTS);
		;
		while (set.next()) {
			list.add(new Rezept(set.getLong("id"), RezeptartDAO.getInstance()
					.getRezeptartById(set.getLong("rezeptart_fk")),
					MitarbeiterDAO.getInstance().getMitarbeiterById(
							set.getLong("mitarbeiter_fk")), set
							.getString("name"), null

			));
		}
		return list;
	}

	public Rezept getRezeptById(Long id) throws ConnectException, DAOException,
			SQLException {
		ResultSet set = getManaged(MessageFormat.format(GET_REZEPT_BY_ID, id));

		while (set.next()) {
			rezept = new Rezept(set.getLong("id"), RezeptartDAO.getInstance()
					.getRezeptartById(set.getLong("rezeptart_fk")),
					MitarbeiterDAO.getInstance().getMitarbeiterById(
							set.getLong("mitarbeiter_fk")),
					set.getString("name"), set.getString("kommentar"));
		}

		return rezept;
	}

	public Rezept getRezept1(Long id) throws ConnectException, DAOException,
			SQLException {
		Rezept result = null;
		ResultSet set = getManaged(MessageFormat.format(GET_REZEPT_BY_ID, id));

		while (set.next()) {
			result = new Rezept(set.getLong("id"));
		}

		return result;
	}

	public Rezept getRezeptByName(String namerezept) throws ConnectException,
			DAOException, SQLException {
		Rezept result = null;

		ResultSet set = getManaged(MessageFormat.format(GET_REZEPT_BY_NAME,
				NAME));

		while (set.next()) {
			result = new Rezept(set.getLong("id"), RezeptartDAO.getInstance()
					.getRezeptartById(set.getLong("rezeptart")), MitarbeiterDAO
					.getInstance().getMitarbeiterById(
							set.getLong("mitarbeiter")), set.getString("name"),
					null);
		}

		return result;
	}

	public Rezept getRezeptByName1(String name) throws ConnectException,
			DAOException, SQLException {
		Rezept result = null;

		ResultSet set = getManaged(MessageFormat.format(GET_REZEPT_BY_NAME,
				"name"));

		while (set.next()) {
			result = new Rezept(set.getLong("id"));
		}

		return result;
	}

	public List<Rezept> getAllArtikelByRezeptId(int rezeptID)
			throws ConnectException, DAOException, SQLException {
		List<Rezept> rezept = new ArrayList<Rezept>();
		ResultSet set = getManaged(GET_ARTIKEL_REZEPT_BY_ID);
		while (set.next()) {
			rezept.add(new Rezept(RezeptartDAO.getInstance().getRezeptartById(
					set.getLong("id")), MitarbeiterDAO.getInstance()
					.getMitarbeiterById(set.getLong("id")), set
					.getString("name"), null));
		}
		return rezept;
	}

	public List<RezeptHasArtikel> getAllArtikelByRezeptId1(Long rezeptID)
			throws ConnectException, DAOException, SQLException {
		List<RezeptHasArtikel> rha = new ArrayList<RezeptHasArtikel>();
		ResultSet set = getManaged(MessageFormat.format(
				GET_REZEPTHASARTIKEL_BY_REZEPT_ID, rezeptID));
		while (set.next()) {
			rha.add(new RezeptHasArtikel(RezeptDAO.getInstance().getRezeptById(
					set.getLong("rezept_fk")), ArtikelDAO.getInstance()
					.getArtikelById(set.getLong("artikel_fk")),
					MengeneinheitDAO.getInstance().getMengeneinheitById(
							set.getLong("einheit")), set.getDouble("menge")));
		}
		return rha;
	}

	public void createRezept(Rezept rezept) throws ConnectException,
			DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(" + NAME + ","
				+ REZEPTART + "," + KOMMENTAR + "," + MITARBEITER + ","
				+ ERSTELLT + ")" + " VALUES" + "('" + rezept.getName() + "',"
				+ rezept.getRezeptart().getId() + ",'" + rezept.getKommentar()
				+ "'," + rezept.getMitarbeiter().getId() + ",'"
				+ rezept.getErstellt() + "')";
		this.putManaged(INSERT_QUERY);
	}

	public void ZubereitungAdd(RezeptHasZubereitung rezeptHasZubereitung)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO rezept_has_zubereitung (rezept_fk, zubereitung_fk) VALUES"
				+ "("
				+ rezeptHasZubereitung.getRezept()
				+ ", "
				+ rezeptHasZubereitung.getZubereitung().getId() + ")";
		this.putManaged(INSERT_QUERY);
	}

	public void saveArtikel(Rezept rezept) throws ConnectException,
			DAOException, SQLException {
		System.out.println("saveArtikel");
		List<RezeptHasArtikel> rha = rezept.getArtikel();
		System.out.println(rha.size());
		for (RezeptHasArtikel a : rha) {
			String rez = rezept.getId().toString();
			String artikel_fk = a.getArtikelId().toString();
			String menge = Double.toString(a.getMenge());
			String me = Long
					.toString(a.getArtikel().getMengeneinheit().getId());

			System.out.println(MessageFormat.format(SAVE_ARTIKEL, rez,
					artikel_fk, menge, me));
			putManaged(MessageFormat.format(SAVE_ARTIKEL, rez, artikel_fk,
					menge, me));
		}
	}

	public void updateRezept(Rezept rezept) throws ConnectException,
			DAOException, SQLException {
		String INSERT_QUERY = "UPDATE rezept SET name = '" + rezept.getName()
				+ "'," + "rezeptart_fk=" + rezept.getRezeptart().getId() + ","
				+ "kommentar='" + rezept.getKommentar() + "',"
				+ "mitarbeiter_fk = " + rezept.getMitarbeiter().getId() + ","
				+ "erstellt='" + rezept.getErstellt() + "' WHERE id = "
				+ rezept.getId();
		this.putManaged(INSERT_QUERY);
	}

	public void ZubereitungenDelete(Rezept rezept1) throws ConnectException,
			DAOException, SQLException {
		String DELETE_QUERY = "DELETE  from rezept_has_zubereitung WHERE rezept_fk = "
				+ rezept1.getId() + ";";

		this.putManaged(DELETE_QUERY);
	}

	public List<Rezept> getRezepteByMenue(Menue menue) throws ConnectException,
			DAOException, SQLException {
		List<Rezept> rezepte = new ArrayList<Rezept>();
		ResultSet set = getManaged("select rez.* from menue_has_rezept mhr, rezept rez where mhr.rezept_id=rez.id and mhr.menue_id = "
				+ menue.getId());
		while (set.next()) {
			Rezept rez = new Rezept();
			Long id = set.getLong("id");
			String name = set.getString("name");
			rez.setId(id);
			rez.setName(name);
			List<RezeptHasArtikel> artikel = ladeArtikelFuerRezept(rez);
			rez.setArtikel(artikel);
			Rezeptart rezArt = Rezeptartverwaltung.getInstance()
					.getRezeptartById(set.getLong("rezeptart_fk"));
			rez.setRezeptart(rezArt);
			Mitarbeiter koch = Mitarbeiterverwaltung.getInstance()
					.getMitarbeiterById(set.getLong("mitarbeiter_fk"));
			rez.setMitarbeiter(koch);
			rezepte.add(rez);
		}
		return rezepte;
	}

	public List<RezeptHasArtikel> ladeArtikelFuerRezept(Rezept rez)
			throws ConnectException, DAOException, SQLException {
		List<RezeptHasArtikel> rha = new ArrayList<RezeptHasArtikel>();

		ResultSet set = getManaged("select * from rezept_has_artikel where rezept_fk="
				+ rez.getId());

		while (set.next()) {
			RezeptHasArtikel a = new RezeptHasArtikel();
			a.setRezept(rez);
			a.setMenge(set.getDouble("menge"));
			Artikel artikel = Artikelverwaltung.getInstance().getArtikelById(
					set.getLong("artikel_fk"));
			a.setArtike(artikel);
			Mengeneinheit me = Mengeneinheitverwaltung.getInstance()
					.getMengeneinheitById(set.getLong("einheit"));
			a.setMengeneinheit(me);
			rha.add(a);
		}
		return rha;
	}

	public void deleteZutatenZuRezept(Rezept rez2) throws ConnectException,
			DAOException, SQLException {
		String DELETE_QUERY = "DELETE FROM rezept_has_artikel WHERE "
				+ REZEPTFK + "=" + rez2.getId() + ";";
		this.putManaged(DELETE_QUERY);
	}

	public void deleteZubereitungZuRezept(Rezept rezept)
			throws ConnectException, DAOException {
		String DELETE_QUERY = "DELETE FROM rezept_has_zubereitung WHERE "
				+ REZEPTFK + "=" + rezept.getId() + ";";
		this.putManaged(DELETE_QUERY);
	}
}
