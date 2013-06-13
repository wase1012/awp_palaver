/**
 * 
 */
package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.menueplanverwaltung.ArtikelBedarf;
import de.bistrosoft.palaver.menueplanverwaltung.KochInMenueplan;
import de.bistrosoft.palaver.menueplanverwaltung.MenueComponent;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menueart;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menueplan;
import de.bistrosoft.palaver.menueplanverwaltung.domain.MenueplanItem;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueartverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasArtikel;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezeptart;
import de.bistrosoft.palaver.rezeptverwaltung.service.Fussnotenverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Geschmackverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptartverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptverwaltung;
import de.bistrosoft.palaver.util.Week;
import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver2.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver2.artikelverwaltung.service.Mengeneinheitverwaltung;
import de.hska.awp.palaver2.data.AbstractDAO;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.service.Mitarbeiterverwaltung;

/**
 * @author Eike
 * 
 */
public class MenueplanDAO extends AbstractDAO {
	private static MenueplanDAO instance;
	private final String TABLE = "menueplan";
	private final String ID = "id";

	private final String GET_MENUEPLAN_BY_WEEK = "SELECT * FROM " + TABLE
			+ " WHERE week = {0} AND year = {1,number,#}";
	private final String GET_MENUES_BY_MENUEPLAN = "SELECT men.*,mhm.angezName, mhm.spalte, mhm.zeile, mhm.portion "
			+ "FROM menue men, menueplan_has_menues mhm "
			+ "WHERE men.id = mhm.menue AND mhm.menueplan = {0}";
	private final String CREATE_MENUE_FOR_MENUEPLAN = "INSERT INTO menueplan_has_menues (menueplan, menue,angezName, spalte, zeile, portion) "
			+ "VALUES ({0},{1},{2},{3},{4},{5})";
	private final String CREATE_MENUEPLAN = "INSERT INTO menueplan (week,year)  VALUES ({0},{1,number,#})";
	private final String UPDATE_MENUEPLAN_FREIGEBEN = "UPDATE menueplan SET freigegeben = {0}";
	private final String DELETE_MENUPLANITEMS_BY_MENUEPLAN = "DELETE FROM menueplan_has_menues WHERE menueplan = {0}";
	private final String GET_ARTIKEL_BEDARF = "select rha.artikel_fk, rha.menge, rha.einheit, mhm.spalte, mhm.portion from rezept_has_artikel rha, menueplan_has_menues mhm, menue_has_rezept mhr where rezept_fk IN "+
	"(select rezept_id from menue_has_rezept where menue_id IN"+ 
		"(select menue from menueplan_has_menues where menueplan = "+
			"(select id from menueplan where week = {0} AND year={1,number,#}))) "+
"AND mhm.menue=mhr.menue_id AND mhr.rezept_id=rha.rezept_fk AND mhm.menueplan = (select id from menueplan where week = {0} AND year={1,number,#})";
	private final static String GET_FUSSNOTE_BY_MENUE = "Select fussnote.id, fussnote.name, fussnote.abkuerzung from fussnote JOIN menue_has_fussnote On menue_has_fussnote.fussnote_fk = fussnote.id WHERE menue_has_fussnote.menue_fk = {0}";
	private final static String GET_FUSSNOTE_BY_ID = "SELECT * FROM fussnote WHERE id={0}";
	private static final String GET_MENUEART_BY_ID = "SELECT * FROM menueart WHERE id={0}";
	private final static String GET_GESCHMACK_BY_ID = "SELECT * FROM geschmack WHERE id={0}";
	
	
	// private final String GET_MENUES_BY_MENUEPLAN = "";

	public MenueplanDAO() {
		super();
	}

	public static MenueplanDAO getInstance() {
		if (instance == null) {
			instance = new MenueplanDAO();
		}
		return instance;
	}
	
	public List<ArtikelBedarf> getArtikelBedarf(Week week) throws ConnectException, DAOException, SQLException{
		List<ArtikelBedarf> bedarf = new ArrayList<ArtikelBedarf>();
		
		ResultSet set = getManaged(MessageFormat.format(
				GET_ARTIKEL_BEDARF, week.getWeek(), week.getYear()));
		
		while (set.next()) {
			//rha.artikel_fk, rha.menge, rha.einheit, mhm.spalte, mhm.portion
			Long artikelId = set.getLong("artikel_fk");
			Double menge = set.getDouble("menge");
			Long einheitId = set.getLong("einheit");
			Integer portion = set.getInt("portion");
			Integer tag = set.getInt("spalte");
			
			menge = menge * (portion/100);
			
			Artikel artikel = Artikelverwaltung.getInstance().getArtikelById(artikelId);
			Mengeneinheit einheit = Mengeneinheitverwaltung.getInstance().getMengeneinheitById(einheitId);
			
			ArtikelBedarf ab = new ArtikelBedarf(artikel, menge, einheit, tag);
			
			bedarf.add(ab);
		}
		
		return bedarf;
	}

	public Menueplan getMenueplanItems(Week week) throws ConnectException, DAOException, SQLException{
		Menueplan mpl = null;

		ResultSet setMpl = getManaged(MessageFormat.format(
				GET_MENUEPLAN_BY_WEEK, week.getWeek(), week.getYear()));

		while (setMpl.next()) {
			mpl = new Menueplan(setMpl.getLong(ID), week);
		}
		
		if (mpl != null){
			List<MenueComponent> menues = new ArrayList<MenueComponent>();
			ResultSet setMenues = getManaged(MessageFormat.format(
					GET_MENUES_BY_MENUEPLAN, mpl.getId()));
			
			while (setMenues.next()) {
				Long id = setMenues.getLong("id");
				String name = setMenues.getString("name");
				Mitarbeiter koch = null;
				Menue menue = new Menue(id, name, koch);
				int row = setMenues.getInt("zeile");
				int col = setMenues.getInt("spalte");
				// Rezepte hinzufügen
//				List<Rezept> rezepte = Rezeptverwaltung.getInstance()
//						.getRezepteByMenue(menue);
//				menue.setRezepte(rezepte);
				List<Fussnote> fussnoten = Fussnotenverwaltung.getInstance()
						.getFussnoteByMenue(id);
				menue.setFussnoten(fussnoten);
				
				Geschmack geschmack = Geschmackverwaltung.getInstance()
						.getGeschmackById(setMenues.getLong("geschmack_fk"));
				menue.setGeschmack(geschmack);
				
				Menueart menueart = Menueartverwaltung.getInstance()
						.getMenueartById(setMenues.getLong("menueart_fk"));
				menue.setMenueart(menueart);
				
				String angezName = setMenues.getString("angezName");
				
				Integer portion = setMenues.getInt("portion");
				
				MenueComponent menueComp = new MenueComponent(menue, angezName,
						null, null, row, col, false,portion);
				
				menues.add(menueComp);
			}
			mpl.setMenues(menues);
		}
		return mpl;
	}
	
	public Menueplan getMenueplanByWeekWithItems(Week week)
			throws ConnectException, DAOException, SQLException {
		Menueplan menueplan = null;
		openConnection();
		ResultSet setMpl = getMany(MessageFormat.format(
				GET_MENUEPLAN_BY_WEEK, week.getWeek(), week.getYear()));

		while (setMpl.next()) {
			menueplan = new Menueplan(setMpl.getLong(ID), week);
		}

		if (menueplan != null) {
			// TODO: Köche laden

			List<MenueComponent> menues = new ArrayList<MenueComponent>();
			// TODO: Menüs laden
			ResultSet setMenues = getManaged(MessageFormat.format(
					GET_MENUES_BY_MENUEPLAN, menueplan.getId()));

			while (setMenues.next()) {
				Long id = setMenues.getLong("id");
				String name = setMenues.getString("name");
				Mitarbeiter koch = null;
				// TODO: = new Mitarbeiter(name, vorname);
				Menue menue = new Menue(id, name, koch);
				int row = setMenues.getInt("zeile");
				int col = setMenues.getInt("spalte");
				// Rezepte hinzufügen
				List<Rezept> rezepte = getRezepteByMenue(menue);
				menue.setRezepte(rezepte);
				List<Fussnote> fussnoten = getFussnoteByMenue(id);
				menue.setFussnoten(fussnoten);
				Geschmack geschmack = getGeschmackById(setMenues.getLong("geschmack_fk"));
				menue.setGeschmack(geschmack);
				Menueart menueart = getMenueartById(setMenues.getLong("menueart_fk"));
				menue.setMenueart(menueart);
				String angezName = setMenues.getString("angezName");
				Integer portion = setMenues.getInt("portion");
				MenueComponent menueComp = new MenueComponent(menue, angezName,
						null, null, row, col, false,portion);
				menues.add(menueComp);
			}
			menueplan.setMenues(menues);
		}
		closeConnection();
		return menueplan;
	}

	private Menueart getMenueartById(Long id) throws ConnectException, DAOException, SQLException {
		Menueart menueart = null;
		ResultSet set = getMany(MessageFormat.format(GET_MENUEART_BY_ID, id));
		while (set.next()) {
			menueart = new Menueart(set.getLong("id"), set.getString("name"));
		}
		return menueart;
	}

	private Geschmack getGeschmackById(Long id) throws ConnectException, DAOException, SQLException {
		Geschmack geschmack = null;
		ResultSet set = getMany(MessageFormat
				.format(GET_GESCHMACK_BY_ID, id));
		while (set.next()) {
			geschmack = new Geschmack(set.getLong("id"), set.getString("name"),
					set.getBoolean("inaktiv"));
		}
		return geschmack;
	}

	private List<Fussnote> getFussnoteByMenue(Long id) throws ConnectException, DAOException, SQLException {
		List<Fussnote> list = new ArrayList<Fussnote>();
		ResultSet set = getMany(MessageFormat.format(GET_FUSSNOTE_BY_MENUE,
				id));
		while (set.next()) {
			list.add(new Fussnote(set.getLong("id"), set.getString("name"), set
					.getString("abkuerzung")));
		}
		return list;
	}

	public Fussnote getFussnoteById(Long id) throws ConnectException,
			DAOException, SQLException {
		Fussnote fussnote=null;
		ResultSet set = getManaged(MessageFormat.format(GET_FUSSNOTE_BY_ID, id));
		while (set.next()) {
			fussnote = new Fussnote(set.getLong("id"), set.getString("name"),
					set.getString("abkuerzung"));
		}
		return fussnote;
	}

	private List<Rezept> getRezepteByMenue(Menue menue) throws ConnectException, DAOException, SQLException {
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

	private List<RezeptHasArtikel> ladeArtikelFuerRezept(Rezept rez) throws ConnectException, DAOException, SQLException {
		List<RezeptHasArtikel> rha = new ArrayList<RezeptHasArtikel>();

		ResultSet set = getMany("select * from rezept_has_artikel where rezept_fk="
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

	// public Menueplan getMenueplanByWeekWithMenues(Week week) throws
	// ConnectException, DAOException, SQLException{
	// Menueplan menueplan = null;
	// ResultSet setMpl = get(MessageFormat.format(GET_MENUEPLAN_BY_WEEK,
	// week.getWeek(),week.getYear()));
	//
	// while (setMpl.next()) {
	// menueplan = new Menueplan(setMpl.getLong(ID),week);
	// }
	//
	//
	//
	// if(menueplan!=null){
	// // TODO: Köche laden
	//
	// List<MenueComponent> menues = new ArrayList<>();
	// // TODO: Menüs laden
	// ResultSet setMenues = get(MessageFormat.format(GET_MENUES_BY_MENUEPLAN,
	// menueplan.getId()));
	//
	// while (setMenues.next()) {
	// Long id = setMenues.getLong("id");
	// String name = setMenues.getString("name");
	// Mitarbeiter koch=null;
	// // TODO: = new Mitarbeiter(name, vorname);
	// Menue menue = new Menue(id, name, koch);
	// int row = setMenues.getInt("zeile");
	// int col = setMenues.getInt("spalte");
	// MenueComponent menueComp = new MenueComponent(menue, null, null, row,
	// col, false);
	// menues.add(menueComp);
	// }
	// menueplan.setMenues(menues);
	// }
	// return menueplan;
	// }

	public List<MenueplanItem> getItemsForMenueplan(Long menuplanID)
			throws ConnectException, DAOException, SQLException {
		List<MenueplanItem> items = new ArrayList<MenueplanItem>();

		ResultSet set = getManaged(MessageFormat.format(
				GET_MENUES_BY_MENUEPLAN, menuplanID));

		while (set.next()) {
			Long id = set.getLong("id");
			String name = set.getString("name");
			Mitarbeiter koch = null;
			Menue men = new Menue(id, name, koch);
			MenueplanItem item = new MenueplanItem();
			item.setMenue(men);
			int spalte = set.getInt("spalte");
			item.setSpalte(spalte);
			int zeile = set.getInt("zeile");
			item.setZeile(zeile);
			items.add(item);
		}
		return items;
	}

	public List<Menue> getMenuesByMenueplan(Long menuplanID)
			throws ConnectException, DAOException, SQLException {
		List<Menue> menues = new ArrayList<Menue>();

		List<MenueplanItem> items = getItemsForMenueplan(menuplanID);

		for (MenueplanItem i : items) {
			menues.add(i.getMenue());
		}

		return menues;
	}

	public void createMenueForMenueplan(Menueplan mpl, Menue menue,
			String name, int col, int row, Integer portion) throws ConnectException,
			DAOException {
		String strName = "'" + name + "'";
		putManaged(MessageFormat.format(CREATE_MENUE_FOR_MENUEPLAN,
				mpl.getId(), menue.getId(), strName, col, row,portion));

	}

	public void updateMenueplan(Menueplan menueplan) throws ConnectException, 
			DAOException{
		putManaged(MessageFormat.format(UPDATE_MENUEPLAN_FREIGEBEN, menueplan.getFreigegeben()));
	}
	
	public void createMenueplan(Menueplan menueplan) throws ConnectException,
			DAOException {
		Week week = menueplan.getWeek();
		putManaged(MessageFormat.format(CREATE_MENUEPLAN, week.getWeek(),
				week.getYear()));
	}

	public void deleteItemsByMenueplan(Menueplan menueplan)
			throws ConnectException, DAOException {
		putManaged(MessageFormat.format(DELETE_MENUPLANITEMS_BY_MENUEPLAN,
				menueplan.getId()));
	}

	public void createKochForMenueplan(Menueplan menueplan, KochInMenueplan kim)
			throws ConnectException, DAOException {
		String sql = "INSERT INTO menueplan_has_koeche (menueplan, spalte,position, koch) VALUES ({0},{1},{2},{3})";
		putManaged(MessageFormat.format(sql, menueplan.getId(),
				kim.getSpalte(), kim.getPosition(), kim.getKoch().getId()));

	}
	
	public List<KochInMenueplan> getKoecheByMenueplan(Menueplan menueplan) throws ConnectException, DAOException, SQLException{
		List<KochInMenueplan> kim = new ArrayList<KochInMenueplan>();
		String sql = "SELECT * FROM menueplan_has_koeche WHERE " + menueplan.getId();
		
		ResultSet set = getManaged(sql);
		
		while(set.next()){
			Integer spalte = set.getInt("spalte");
			Integer position = set.getInt("position");
			Long kochId = set.getLong("koch");
			
			KochInMenueplan k = new KochInMenueplan();
			k.setKoch(Mitarbeiterverwaltung.getInstance().getMitarbeiterById(kochId));
			k.setPosition(position);
			k.setSpalte(spalte);
			
			kim.add(k);
		}
		return kim;
	}

	public void deleteKoecheByMenueplan(Menueplan menueplan)
			throws ConnectException, DAOException {
		String sql = "DELETE FROM menueplan_has_koeche WHERE menueplan = "
				+ menueplan.getId();
		putManaged(sql);
	}
}
