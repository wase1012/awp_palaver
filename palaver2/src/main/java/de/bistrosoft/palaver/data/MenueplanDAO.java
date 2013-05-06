/**
 * 
 */
package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.menueplanverwaltung.MenueComponent;
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
	private final String CREATE_MENUE_FOR_MENUEPLAN ="INSERT INTO menueplan_has_menues (menueplan, menue, spalte, zeile) " +
														"VALUES ({0},{1},{2},{3})";
	private final String CREATE_MENUEPLAN = "INSERT INTO menueplan (week,year)  VALUES ({0},{1,number,#})";
	private final String DELETE_MENUPLANITEMS_BY_MENUEPLAN = "DELETE FROM menueplan_has_menues WHERE menueplan = {0}";
	
	public MenueplanDAO() {
		super();
	}

	public static MenueplanDAO getInstance() {
		if (instance == null) {
			instance = new MenueplanDAO();
		}
		return instance;
	}
	
	public Menueplan getMenueplanByWeek(Week week) throws ConnectException, DAOException, SQLException{
		Menueplan menueplan = null;
		ResultSet setMpl = get(MessageFormat.format(GET_MENUEPLAN_BY_WEEK, week.getWeek(),week.getYear()));
		
		while (setMpl.next()) {
			menueplan = new Menueplan(setMpl.getLong(ID),week);
		}	
		
		if(menueplan!=null){
			// TODO: Köche laden
			
			List<MenueComponent> menues = new ArrayList<>();
			// TODO: Menüs laden
			ResultSet setMenues = get(MessageFormat.format(GET_MENUES_BY_MENUEPLAN, menueplan.getId()));
			
			while (setMenues.next()) {
				Long id = setMenues.getLong("id");
				String name = setMenues.getString("name");
				Mitarbeiter koch=null;
//				TODO:  = new Mitarbeiter(name, vorname);
				Menue menue = new Menue(id, name, koch);
				int row = setMenues.getInt("zeile");
				int col = setMenues.getInt("spalte");
				MenueComponent menueComp = new MenueComponent(menue, null, row, col, false);
				menues.add(menueComp);
			}
			menueplan.setMenues(menues);
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
	
	public void createMenueForMenueplan(Menueplan mpl, Menue menue, int col, int row) throws ConnectException, DAOException{
		put(MessageFormat.format(CREATE_MENUE_FOR_MENUEPLAN, mpl.getId(),menue.getId(),col,row));
		
	}

	public void createMenueplan(Menueplan menueplan) throws ConnectException, DAOException {
		Week week = menueplan.getWeek();
		put(MessageFormat.format(CREATE_MENUEPLAN,week.getWeek(),week.getYear() ));
	}
	
	public void deleteItemsByMenueplan(Menueplan menueplan) throws ConnectException, DAOException {
		Week week = menueplan.getWeek();
		put(MessageFormat.format(DELETE_MENUPLANITEMS_BY_MENUEPLAN,menueplan.getId()));
	}
}
