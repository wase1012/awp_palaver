/**
 * 
 */
package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.Kuchenplan;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.Kuchenrezept;
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
import de.bistrosoft.palaver.rezeptverwaltung.service.Fussnotenverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Geschmackverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptverwaltung;
import de.bistrosoft.palaver.util.Week;
import de.hska.awp.palaver2.data.AbstractDAO;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.service.Mitarbeiterverwaltung;

/**
 * @author Christine
 * 
 */
public class KuchenplanDAO extends AbstractDAO {
	private static KuchenplanDAO instance;
	private final String TABLE = "kuchenplan";
	private final String ID = "id";

	private final String GET_KUCHENPLAN_BY_WEEK = "SELECT * FROM " + TABLE
			+ " WHERE week = {0} AND year = {1,number,#}";
	private final String GET_KUCHENREZEPTE_BY_KUCHENPLAN = "SELECT ku.*, khk.tag, khk.anzahl "
			+ "FROM kuchenrezept ku, kuchenplan_has_kuchenrezepte khk "
			+ "WHERE ku.id = khk.kuchenrezept_fk AND khk.kuchenplan_fk = {0}";
	private final String CREATE_KUCHENREZEPT_FOR_KUCHENPLAN = "INSERT INTO kuchenplan_has_kuchenrezepte (kuchenplan_fk, kuchenrezept_fk, tag, anzahl) "
			+ "VALUES ({0},{1},{2},{3})";
	private final String CREATE_KUCHENPLAN = "INSERT INTO kuchenplan (week,year)  VALUES ({0},{1,number,#})";
	private final String DELETE_KUCHENPLANITEMS_BY_KUCHENPLAN = "DELETE FROM kuchenplan_has_kuchenrezepte WHERE kuchenplan = {0}";

	public KuchenplanDAO() {
		super();
	}

	public static KuchenplanDAO getInstance() {
		if (instance == null) {
			instance = new KuchenplanDAO();
		}
		return instance;
	}

//	public Kuchenplan getKuchenplanByWeekWithItems(Week week)
//			throws ConnectException, DAOException, SQLException {
//		Kuchenplan kuchenplan = null;
//		ResultSet setMpl = getManaged(MessageFormat.format(
//				GET_KUCHENPLAN_BY_WEEK, week.getWeek(), week.getYear()));
//
//		while (setMpl.next()) {
//			kuchenplan = new Kuchenplan(setMpl.getLong(ID), week);
//		}
//
//		if (kuchenplan != null) {
//			List<Kuchenrezept> kuchenrezepte = new ArrayList<Kuchenrezept>();
//			ResultSet setKuchenrezepte = getManaged(MessageFormat.format(
//					GET_KUCHENREZEPTE_BY_KUCHENPLAN, kuchenplan.getId()));
//
//			while (setKuchenrezepte.next()) {
//				Long id = setKuchenrezepte.getLong("id");
//				String name = setKuchenrezepte.getString("name");
////				Mitarbeiter baecker = null;
////				Kuchenrezept kuchenrezept = new Kuchenrezept(id, name);
////				int tag = setKuchenrezepte.getInt("tag");
////				
//				Kuchenrezept kr = new Kuchenrezept(menue, angezName,
//						null, null, row, col, false);
//				kuchenrezepte.add(Kuchenrezept);
//			}
//			menueplan.setMenues(kuchenrezepte);
//		}
//		return menueplan;
//	}
//
//	// public Menueplan getMenueplanByWeekWithMenues(Week week) throws
//	// ConnectException, DAOException, SQLException{
//	// Menueplan menueplan = null;
//	// ResultSet setMpl = get(MessageFormat.format(GET_MENUEPLAN_BY_WEEK,
//	// week.getWeek(),week.getYear()));
//	//
//	// while (setMpl.next()) {
//	// menueplan = new Menueplan(setMpl.getLong(ID),week);
//	// }
//	//
//	//
//	//
//	// if(menueplan!=null){
//	// // TODO: Köche laden
//	//
//	// List<MenueComponent> menues = new ArrayList<>();
//	// // TODO: Menüs laden
//	// ResultSet setMenues = get(MessageFormat.format(GET_MENUES_BY_MENUEPLAN,
//	// menueplan.getId()));
//	//
//	// while (setMenues.next()) {
//	// Long id = setMenues.getLong("id");
//	// String name = setMenues.getString("name");
//	// Mitarbeiter koch=null;
//	// // TODO: = new Mitarbeiter(name, vorname);
//	// Menue menue = new Menue(id, name, koch);
//	// int row = setMenues.getInt("zeile");
//	// int col = setMenues.getInt("spalte");
//	// MenueComponent menueComp = new MenueComponent(menue, null, null, row,
//	// col, false);
//	// menues.add(menueComp);
//	// }
//	// menueplan.setMenues(menues);
//	// }
//	// return menueplan;
//	// }
//
//	public List<MenueplanItem> getItemsForMenueplan(Long menuplanID)
//			throws ConnectException, DAOException, SQLException {
//		List<MenueplanItem> items = new ArrayList<MenueplanItem>();
//
//		ResultSet set = getManaged(MessageFormat.format(
//				GET_MENUES_BY_MENUEPLAN, menuplanID));
//
//		while (set.next()) {
//			Long id = set.getLong("id");
//			String name = set.getString("name");
//			Mitarbeiter koch = null;
//			Menue men = new Menue(id, name, koch);
//			MenueplanItem item = new MenueplanItem();
//			item.setMenue(men);
//			int spalte = set.getInt("spalte");
//			item.setSpalte(spalte);
//			int zeile = set.getInt("zeile");
//			item.setZeile(zeile);
//			items.add(item);
//		}
//		return items;
//	}
//
//	public List<Menue> getMenuesByMenueplan(Long menuplanID)
//			throws ConnectException, DAOException, SQLException {
//		List<Menue> menues = new ArrayList<Menue>();
//
//		List<MenueplanItem> items = getItemsForMenueplan(menuplanID);
//
//		for (MenueplanItem i : items) {
//			menues.add(i.getMenue());
//		}
//
//		return menues;
//	}
//
//	public void createMenueForMenueplan(Menueplan mpl, Menue menue,
//			String name, int col, int row) throws ConnectException,
//			DAOException {
//		String strName = "'" + name + "'";
//		putManaged(MessageFormat.format(CREATE_MENUE_FOR_MENUEPLAN,
//				mpl.getId(), menue.getId(), strName, col, row));
//
//	}
//
//	public void createMenueplan(Menueplan menueplan) throws ConnectException,
//			DAOException {
//		Week week = menueplan.getWeek();
//		putManaged(MessageFormat.format(CREATE_MENUEPLAN, week.getWeek(),
//				week.getYear()));
//	}
//
//	public void deleteItemsByMenueplan(Menueplan menueplan)
//			throws ConnectException, DAOException {
//		putManaged(MessageFormat.format(DELETE_MENUPLANITEMS_BY_MENUEPLAN,
//				menueplan.getId()));
//	}
//
//	public void createKochForMenueplan(Menueplan menueplan, KochInMenueplan kim)
//			throws ConnectException, DAOException {
//		String sql = "INSERT INTO menueplan_has_koeche (menueplan, spalte,position, koch) VALUES ({0},{1},{2},{3})";
//		putManaged(MessageFormat.format(sql, menueplan.getId(),
//				kim.getSpalte(), kim.getPosition(), kim.getKoch().getId()));
//
//	}
//	
//	public List<KochInMenueplan> getKoecheByMenueplan(Menueplan menueplan) throws ConnectException, DAOException, SQLException{
//		List<KochInMenueplan> kim = new ArrayList<KochInMenueplan>();
//		String sql = "SELECT * FROM menueplan_has_koeche WHERE " + menueplan.getId();
//		
//		ResultSet set = getManaged(sql);
//		
//		while(set.next()){
//			Integer spalte = set.getInt("spalte");
//			Integer position = set.getInt("position");
//			Long kochId = set.getLong("koch");
//			
//			KochInMenueplan k = new KochInMenueplan();
//			k.setKoch(Mitarbeiterverwaltung.getInstance().getMitarbeiterById(kochId));
//			k.setPosition(position);
//			k.setSpalte(spalte);
//			
//			kim.add(k);
//		}
//		return kim;
//	}
//
//	public void deleteKoecheByMenueplan(Menueplan menueplan)
//			throws ConnectException, DAOException {
//		String sql = "DELETE FROM menueplan_has_koeche WHERE menueplan = "
//				+ menueplan.getId();
//		putManaged(sql);
//	}
}
