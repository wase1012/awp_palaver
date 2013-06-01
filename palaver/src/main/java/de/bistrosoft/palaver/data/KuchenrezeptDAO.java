package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasArtikel;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasZubereitung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptverwaltung;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.Kuchenrezept;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.KuchenrezeptHasArtikel;
import de.bistrosoft.palaver.util.Util;
import de.hska.awp.palaver2.data.AbstractDAO;
import de.hska.awp.palaver2.data.ArtikelDAO;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.data.MengeneinheitDAO;

public class KuchenrezeptDAO extends AbstractDAO {

	private final static String TABLE = "kuchenrezept";
	private final static String ID = "id";
	private final static String NAME = "name";
	private final static String KOMMENTAR = "kommentar";
	private final static String MITARBEITER = "mitarbeiter_fk";
	private final static String ERSTELLT = "erstellt";
	private final static String KUCHENREZEPTHASARTIKEL = "kuchenrezept_has_artikel";
	private final static String KUCHENREZEPTFK = "kuchenrezept_fk";

	private static KuchenrezeptDAO instance = null;
	private final static String GET_ALL_KUCHENREZEPTS = "SELECT * FROM kuchenrezept";
	private final static String GET_KUCHENREZEPT_BY_ID = "SELECT * FROM kuchenrezept WHERE id = {0}";
	private final static String GET_KUCHENREZEPT_BY_NAME = "SELECT * FROM kuchenrezept WHERE kuchenrezept.name = {0}";
	private final static String GET_ARTIKEL_KUCHENREZEPT_BY_ID = "Select * From artikel Join kuchenrezept_has_artikel On artikel.id = kuchenrezept_has_artikel.artikel_fk Where kuchenrezept_has_artikel.kuchenrezept_fk = {0}";
	private final static String SAVE_ARTIKEL = "INSERT INTO kuchenrezept_has_artikel VALUES ({0},{1},{2},{3})";
	private final static String GET_KUCHENREZEPTHASARTIKEL_BY_KUCHENREZEPT_ID = "SELECT * FROM "
			+ KUCHENREZEPTHASARTIKEL + " WHERE " + KUCHENREZEPTFK + " = {0}";

	Kuchenrezept kuchenrezept;

	public KuchenrezeptDAO() {
		super();
	}

	public static KuchenrezeptDAO getInstance() {
		if (instance == null) {
			instance = new KuchenrezeptDAO();
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

	public List<Kuchenrezept> getAllKuchenrezepte() throws ConnectException, DAOException,
			SQLException {
		List<Kuchenrezept> list = new ArrayList<Kuchenrezept>();
		ResultSet set = getManaged(GET_ALL_KUCHENREZEPTS);
		;
		while (set.next()) {
			list.add(new Kuchenrezept(set.getLong("id"), MitarbeiterDAO
							.getInstance().getMitarbeiterById(
									set.getLong("mitarbeiter_fk")), set
							.getString("name"), set.getString("kommentar"), set
							.getDate("erstellt")
			));
		}
		return list;
	}

	public List<Kuchenrezept> getAllKuchenrezepteM() throws ConnectException, DAOException,
			SQLException {
		List<Kuchenrezept> list = new ArrayList<Kuchenrezept>();
		ResultSet set = getManaged(GET_ALL_KUCHENREZEPTS);
		;
		while (set.next()) {
			list.add(new Kuchenrezept(set.getLong("id"), MitarbeiterDAO
							.getInstance().getMitarbeiterById(
									set.getLong("mitarbeiter_fk")), set
							.getString("name"), null

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

	public Kuchenrezept getKuchenrezeptById(Long id) throws ConnectException, DAOException,
			SQLException {
		ResultSet set = getManaged(MessageFormat.format(GET_KUCHENREZEPT_BY_ID, id));

		while (set.next()) {
			kuchenrezept = new Kuchenrezept(set.getLong("id"), MitarbeiterDAO
							.getInstance().getMitarbeiterById(
									set.getLong("mitarbeiter_fk")),
					set.getString("name"), set.getString("kommentar"));
		}

		return kuchenrezept;
	}

	public Kuchenrezept getKuchenrezept1(Long id) throws ConnectException, DAOException,
			SQLException {
		Kuchenrezept result = null;
		ResultSet set = getManaged(MessageFormat.format(GET_KUCHENREZEPT_BY_ID, id));

		while (set.next()) {
			result = new Kuchenrezept(set.getLong("id"));
		}

		return result;
	}

	public Kuchenrezept getKuchenrezeptByName(String namekuchenrezept) throws ConnectException,
			DAOException, SQLException {
		Kuchenrezept result = null;

		ResultSet set = getManaged(MessageFormat.format(GET_KUCHENREZEPT_BY_NAME, NAME));

		while (set.next()) {
			result = new Kuchenrezept(set.getLong("id"),
					MitarbeiterDAO.getInstance().getMitarbeiterById(
							set.getLong("mitarbeiter")), set.getString("name"),
					null);
		}

		return result;
	}

	public Kuchenrezept getKuchenrezeptByName1(String name) throws ConnectException,
			DAOException, SQLException {
		Kuchenrezept result = null;

		ResultSet set = getManaged(MessageFormat.format(GET_KUCHENREZEPT_BY_NAME, "name"));

		while (set.next()) {
			result = new Kuchenrezept(set.getLong("id"));
		}

		return result;
	}

	public List<Kuchenrezept> getAllArtikelByKuchenrezeptId(int kuchenrezeptID)
			throws ConnectException, DAOException, SQLException {
		List<Kuchenrezept> kuchenrezept = new ArrayList<Kuchenrezept>();
		ResultSet set = getManaged(GET_ARTIKEL_KUCHENREZEPT_BY_ID);
		while (set.next()) {
			kuchenrezept.add(new Kuchenrezept(MitarbeiterDAO
					.getInstance().getMitarbeiterById(set.getLong("id")), set
					.getString("name"), null));
		}
		return kuchenrezept;
	}

	public List<KuchenrezeptHasArtikel> getAllArtikelByKuchenrezeptId1(Long kuchenrezeptID)
			throws ConnectException, DAOException, SQLException {
		List<KuchenrezeptHasArtikel> rha = new ArrayList<KuchenrezeptHasArtikel>();
		ResultSet set = getManaged(MessageFormat.format(GET_KUCHENREZEPTHASARTIKEL_BY_KUCHENREZEPT_ID,kuchenrezeptID));
		while (set.next()) {
			rha.add(new KuchenrezeptHasArtikel(KuchenrezeptDAO.getInstance().getKuchenrezeptById(
					set.getLong("kuchenrezept_fk")), ArtikelDAO.getInstance()
					.getArtikelById(set.getLong("artikel_fk")), MengeneinheitDAO
					.getInstance().getMengeneinheitById(set.getLong("einheit")), set
					.getDouble("menge")));
		}
		return rha;
	}

//	public List<Kuchenrezept> getKuchenrezeptebyGeschmack(Geschmack geschmack)
//			throws ConnectException, DAOException, SQLException {
//		List<Kuchenrezept> kuchenrezept = new ArrayList<Kuchenrezept>();
//		ResultSet set = getManaged(GET_KUCHENREZEPTE_BY_GESCHMACK);
//		while (set.next()) {
//			kuchenrezept.add(new Rezept(RezeptartDAO.getInstance().getRezeptartById(
//					set.getLong("id")), GeschmackDAO.getInstance()
//					.getGeschmackById(set.getLong("geschmack_fk")),
//					MitarbeiterDAO.getInstance().getMitarbeiterById(
//							set.getLong("mitarbeiter_fk")), set
//							.getString("name"), null, set.getInt("portion")));
//		}
//		return rezept;
//
//	}

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

	public void createKuchenrezept(Kuchenrezept kuchenrezept) throws ConnectException,
			DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(" + NAME + ","
				+ KOMMENTAR	+ "," + MITARBEITER + "," + ERSTELLT + ")" 
				+ " VALUES" + "('" + kuchenrezept.getName() + "','"
			    + kuchenrezept.getKommentar() + "','"
				+ kuchenrezept.getMitarbeiter().getId() + "','"
				+ kuchenrezept.getErstellt() + "')";
		this.putManaged(INSERT_QUERY);
	}

//	public void ZubereitungAdd(RezeptHasZubereitung rezeptHasZubereitung)
//			throws ConnectException, DAOException, SQLException {
//		String INSERT_QUERY = "INSERT INTO rezept_has_zubereitung (rezept_fk, zubereitung_fk) VALUES"
//				+ "("
//				+ rezeptHasZubereitung.getRezept()
//				+ ", "
//				+ rezeptHasZubereitung.getZubereitung() + ")";
//		this.putManaged(INSERT_QUERY);
//
//	}

	public void saveArtikel(Kuchenrezept kuchenrezept) throws ConnectException,
			DAOException, SQLException {
		System.out.println("saveArtikel");
		List<KuchenrezeptHasArtikel> rha = kuchenrezept.getArtikel();
		System.out.println(rha.size());
		for (KuchenrezeptHasArtikel a : rha) {
			String rez = kuchenrezept.getId().toString();
			String artikel_fk = a.getArtikelId().toString();
			String menge = Double.toString(a.getMenge());
			String me = "1";

			putManaged(MessageFormat.format(SAVE_ARTIKEL, rez, artikel_fk, menge, me));
		}
	}

	public void updateKuchenrezept(Kuchenrezept kuchenrezept) throws ConnectException,
			DAOException, SQLException {
		String INSERT_QUERY = "UPDATE kuchenrezept SET name = '" + kuchenrezept.getName()
				+ "," + "kommentar='" + kuchenrezept.getKommentar() + "'," + "mitarbeiter_fk = "
				+ kuchenrezept.getMitarbeiter().getId() + "," + "," + "erstellt='"
				+ kuchenrezept.getErstellt() + " WHERE id = "
				+ kuchenrezept.getId();
		this.putManaged(INSERT_QUERY);
	}

//	public void ZubereitungenDelete(Rezept rezept1) throws ConnectException,
//			DAOException, SQLException {
//		String DELETE_QUERY = "DELETE  from rezept_has_zubereitung WHERE rezept_fk = "
//				+ rezept1.getId() + ";";
//
//		this.putManaged(DELETE_QUERY);
//	}

//	public List<Rezept> getRezepteByMenue(Menue menue) throws ConnectException, DAOException, SQLException {
//		List<Rezept> rezepte=new ArrayList<Rezept>();
//		ResultSet set =getManaged("select rezept_id from menue_has_rezept where menue_id="+menue.getId());
//		
//		while (set.next()) {
//			Long rezId=set.getLong("rezept_id");
//			Rezept rez = Rezeptverwaltung.getInstance().getRezeptById(rezId);
//			rezepte.add(rez);
//		}
//		
//		return rezepte;
//	}

}
