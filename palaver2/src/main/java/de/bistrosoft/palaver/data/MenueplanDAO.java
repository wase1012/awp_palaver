/**
 * 
 */
package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menueplan;
import de.bistrosoft.palaver.menueplanverwaltung.domain.MenueplanItem;
import de.bistrosoft.palaver.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.bistrosoft.palaver.util.Week;

/**
 * @author Eike
 *
 */
public class MenueplanDAO extends AbstractDAO {
	private static MenueplanDAO instance;
	private final String TABLE = "menueplan";
	private final String ID = "id";
	
	private final String GET_MENUEPLAN_BY_WEEK = "SELECT * FROM " + TABLE + " WHERE week = {0} AND year = {1,number,#}";
	private final String GET_MENUES_BY_MENUEPLAN = "SELECT men.*, mhm.spalte, mhm.zeile " +
							"FROM menue men, menueplan_has_menues mhm " +
							"WHERE men.id = mhm.menue AND mhm.menueplan = {0}";
	
	public MenueplanDAO() {
		super();
	}

	public static MenueplanDAO getInstance() {
		if (instance == null) {
			instance = new MenueplanDAO();
		}
		return instance;
	}
	
	public Menueplan getMenuePlanByWeek(Week week) throws ConnectException, DAOException, SQLException{
		Menueplan menueplan = null;
		System.out.println(MessageFormat.format(GET_MENUEPLAN_BY_WEEK, week.getWeek(),week.getYear()));
		ResultSet set = get(MessageFormat.format(GET_MENUEPLAN_BY_WEEK, week.getWeek(),week.getYear()));
		
		while (set.next()) {
			menueplan = new Menueplan(set.getLong(ID),week);
		}
		
		return menueplan;
	}
	
	public List<MenueplanItem> getItemsForMenueplan(Long menuplanID) throws ConnectException, DAOException, SQLException{
		List<MenueplanItem> items = new ArrayList<MenueplanItem>();
		
		ResultSet set = get(MessageFormat.format(GET_MENUES_BY_MENUEPLAN, menuplanID));
		
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
}
