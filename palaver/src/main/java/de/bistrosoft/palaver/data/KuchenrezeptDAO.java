package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.FussnoteKuchen;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.KuchenplanHasKuchenrezept;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.KuchenrezeptHasFussnote;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.Kuchenrezept;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.KuchenrezeptHasArtikel;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.service.Fussnotekuchenverwaltung;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.service.Kuchenrezeptverwaltung;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.menueplanverwaltung.domain.MenueHasFussnote;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;
import de.bistrosoft.palaver.rezeptverwaltung.service.Fussnotenverwaltung;
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

public class KuchenrezeptDAO extends AbstractDAO {

	private final static String TABLE = "kuchenrezept";
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
	private static final String GET_ALL_KUCHENREZEPT_TABELLE = "SELECT * FROM "
			+ TABLE;

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

	public List<Kuchenrezept> getAllKuchenrezepte(Boolean ladeArtikel)
			throws ConnectException, DAOException, SQLException {
		List<Kuchenrezept> list = new ArrayList<Kuchenrezept>();
		ResultSet set = getManaged(GET_ALL_KUCHENREZEPTS);
		while (set.next()) {
			Kuchenrezept kr = new Kuchenrezept(set.getLong("id"),
					MitarbeiterDAO.getInstance().getMitarbeiterById(
							set.getLong("mitarbeiter_fk")),
					set.getString("name"), set.getString("kommentar"),
					set.getDate("erstellt"));
			if (ladeArtikel) {
				kr.setArtikel(getAllArtikelByKuchenrezeptId1(kr));
			}
			List<FussnoteKuchen> fussnoten = Fussnotekuchenverwaltung.getInstance()
					.getFussnoteKuchenByKuchen(kr.getId());
			String fn="";
			for(FussnoteKuchen f: fussnoten){
				fn = fn+" ("+f.getAbkuerzung().toString()+")";
			}
			kr.setFussnoten(fn);
			list.add(kr);
		}
		return list;
	}

	public List<Kuchenrezept> getAllKuchenrezepteTabelle()
			throws ConnectException, DAOException, SQLException {
		List<Kuchenrezept> list = new ArrayList<Kuchenrezept>();
		ResultSet set = getManaged(GET_ALL_KUCHENREZEPT_TABELLE);

		while (set.next()) {
			Kuchenrezept kuchenrezept = new Kuchenrezept(set.getLong("id"),
					MitarbeiterDAO.getInstance().getMitarbeiterById(
							set.getLong("mitarbeiter_fk")),
					set.getString("name"), set.getString("kommentar"),
					set.getDate("erstellt"));
			
			list.add(kuchenrezept);

		}
		return list;
	}

	public List<Kuchenrezept> getAllKuchenrezepteM() throws ConnectException,
			DAOException, SQLException {
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

	public Kuchenrezept getKuchenrezeptById(Long id, Boolean ladeArtikel)
			throws ConnectException, DAOException, SQLException {
		ResultSet set = getManaged(MessageFormat.format(GET_KUCHENREZEPT_BY_ID,
				id));
		List<Kuchenrezept> list = new ArrayList<Kuchenrezept>();
		while (set.next()) {
			Kuchenrezept kr = new Kuchenrezept(set.getLong("id"),
					MitarbeiterDAO.getInstance().getMitarbeiterById(
							set.getLong("mitarbeiter_fk")),
					set.getString("name"), set.getString("kommentar"),
					set.getDate("erstellt"));
			if (ladeArtikel) {
				kr.setArtikel(getAllArtikelByKuchenrezeptId1(kr));
			}
			kr.setFussnoteKuchen(FussnoteKuchenDAO.getInstance()
					.getFussnoteKuchenByKuchen(id));
			list.add(kr);
			
		}
		
		return list.get(0);
	}

	public Kuchenrezept getKuchenrezept1(Long id) throws ConnectException,
			DAOException, SQLException {
		Kuchenrezept result = null;
		ResultSet set = getManaged(MessageFormat.format(GET_KUCHENREZEPT_BY_ID,
				id));

		while (set.next()) {
			result = new Kuchenrezept(set.getLong("id"));
		}
		return result;
	}

	public Kuchenrezept getKuchenrezeptByName(String namekuchenrezept)
			throws ConnectException, DAOException, SQLException {
		Kuchenrezept result = null;

		ResultSet set = getManaged(MessageFormat.format(
				GET_KUCHENREZEPT_BY_NAME, NAME));

		while (set.next()) {
			result = new Kuchenrezept(set.getLong("id"), MitarbeiterDAO
					.getInstance().getMitarbeiterById(
							set.getLong("mitarbeiter")), set.getString("name"),
					null);
		}
		return result;
	}

	public Kuchenrezept getKuchenrezeptByName1(String name)
			throws ConnectException, DAOException, SQLException {
		Kuchenrezept result = null;

		ResultSet set = getManaged(MessageFormat.format(
				GET_KUCHENREZEPT_BY_NAME, "name"));

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
			kuchenrezept.add(new Kuchenrezept(MitarbeiterDAO.getInstance()
					.getMitarbeiterById(set.getLong("id")), set
					.getString("name"), null));
		}
		return kuchenrezept;
	}

	public List<KuchenrezeptHasArtikel> getAllArtikelByKuchenrezeptId1(
			Kuchenrezept kr) throws ConnectException, DAOException,
			SQLException {
		List<KuchenrezeptHasArtikel> rha = new ArrayList<KuchenrezeptHasArtikel>();
		ResultSet set = getManaged(MessageFormat.format(
				GET_KUCHENREZEPTHASARTIKEL_BY_KUCHENREZEPT_ID, kr.getId()));
		while (set.next()) {
			rha.add(new KuchenrezeptHasArtikel(kr, ArtikelDAO.getInstance()
					.getArtikelById(set.getLong("artikel_fk")),
					MengeneinheitDAO.getInstance().getMengeneinheitById(
							set.getLong("einheit")), set.getDouble("menge")));
		}
		return rha;
	}

	public void createKuchenrezept(Kuchenrezept kuchenrezept)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO " + TABLE + "(" + NAME + ","
				+ KOMMENTAR + "," + MITARBEITER + "," + ERSTELLT + ")"
				+ " VALUES" + "('" + kuchenrezept.getName() + "','"
				+ kuchenrezept.getKommentar() + "','"
				+ kuchenrezept.getMitarbeiter().getId() + "','"
				+ kuchenrezept.getErstellt() + "')";
		this.putManaged(INSERT_QUERY);
	}

	public void saveArtikel(Kuchenrezept kuchenrezept) throws ConnectException,
			DAOException, SQLException {
		List<KuchenrezeptHasArtikel> rha = kuchenrezept.getArtikel();
		for (KuchenrezeptHasArtikel a : rha) {
			String rez = kuchenrezept.getId().toString();
			String artikel_fk = a.getArtikelId().toString();
			String menge = Double.toString(a.getMenge());
			String me = "1";
            System.out.println(MessageFormat.format(SAVE_ARTIKEL, rez, artikel_fk,menge, me));
			putManaged(MessageFormat.format(SAVE_ARTIKEL, rez, artikel_fk,
					menge, me));
		}
	}
	public void FussnoteKuchenAdd(KuchenrezeptHasFussnote kuchenHasFussnote)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "INSERT INTO kuchenrezept_has_fussnote (kuchenrezept_fk, fussnotekuchen_fk) VALUES"
				+ "("
				+ kuchenHasFussnote.getKuchen().getId()
				+ ", "
				+ kuchenHasFussnote.getFussnoteKuchen().getId() + ")";
		System.out.println(INSERT_QUERY);
		this.putManaged(INSERT_QUERY);
	}
	
	public void FussnoteKuchenDelete(Kuchenrezept kuchenrezept) throws ConnectException,
	DAOException, SQLException {
String DELETE_QUERY = "DELETE  from kuchenrezept_has_fussnote WHERE kuchenrezept_fk = "
		+ kuchenrezept.getId() + ";";

this.putManaged(DELETE_QUERY);
}
	public void updateKuchenrezept(Kuchenrezept kuchenrezept)
			throws ConnectException, DAOException, SQLException {
		String INSERT_QUERY = "UPDATE kuchenrezept SET name = '"
				+ kuchenrezept.getName() + "'," + "kommentar='"
				+ kuchenrezept.getKommentar() + "'," + "mitarbeiter_fk = "
				+ kuchenrezept.getMitarbeiter().getId() + "," + "erstellt='"
				+ kuchenrezept.getErstellt() + "' WHERE id = "
				+ kuchenrezept.getId();
		this.putManaged(INSERT_QUERY);
		
//		Kuchenrezeptverwaltung.getInstance().FussnoteKuchenDelete(kuchenrezept);
//		for (FussnoteKuchen fs : kuchenrezept.getFussnoteKuchen()) {
//			Kuchenrezeptverwaltung.getInstance().FussnoteKuchenAdd(
//					new KuchenrezeptHasFussnote(fs, kuchenrezept));
//		}
	}

	public List<KuchenrezeptHasArtikel> ladeArtikelFuerKuchenrezept(
			Kuchenrezept rez) throws ConnectException, DAOException,
			SQLException {
		List<KuchenrezeptHasArtikel> rha = new ArrayList<KuchenrezeptHasArtikel>();

		ResultSet set = getManaged("select * from kuchenrezept_has_artikel where kuchenrezept_fk="
				+ rez.getId());

		while (set.next()) {
			KuchenrezeptHasArtikel a = new KuchenrezeptHasArtikel();
			a.setKuchenrezept(rez);
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

	public void deleteZutatenZuKuchenrezept(Kuchenrezept rez2)
			throws ConnectException, DAOException, SQLException {
		String DELETE_QUERY = "DELETE FROM kuchenrezept_has_artikel WHERE "
				+ KUCHENREZEPTFK + "=" + rez2.getId() + ";";
		this.putManaged(DELETE_QUERY);
	}
}
