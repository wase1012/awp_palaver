/**
 * 
 */
package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

import de.bistrosoft.palaver.menueplanverwaltung.domain.Menueplan;
import de.bistrosoft.palaver.util.Week;

/**
 * @author Eike
 *
 */
public class MenueplanDAO extends AbstractDAO {
	private static MenueplanDAO instance;
	private final String TABLE = "menueplan";
	private final String ID = "id";
	
	private final String GET_MENUEPLAN_BY_WEEK = "SELECT * FROM " + TABLE + " WHERE week = {0} AND year = {1}";
	
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
		
		ResultSet set = get(MessageFormat.format(GET_MENUEPLAN_BY_WEEK, week.getWeek(),week.getYear()));

		while (set.next()) {
			menueplan = new Menueplan(set.getLong(ID),week);
		}
		
		return menueplan;
	}
}
