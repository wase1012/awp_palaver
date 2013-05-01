package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menueplan;

public class MenueDAO extends AbstractDAO {
	private static MenueDAO instance;
	private final String TABLE = "menue";
	private final String ID = "id";
	
	private final String GET_ALL_MENUES = "SELECT * FROM menue";
	
	public MenueDAO() {
		super();
	}

	public static MenueDAO getInstance() {
		if (instance == null) {
			instance = new MenueDAO();
		}
		return instance;
	}
	
	public List<Menue> getAllMenues() throws ConnectException, DAOException, SQLException{
		List<Menue> menues = new ArrayList<>();
		ResultSet set = get(GET_ALL_MENUES);
		
		while (set.next()) {
			Menue m = new Menue();
			m.setId(set.getLong("id"));
			m.setName(set.getString("name"));
			m.setKoch(null);
			m.setRezepte(null);
			menues.add(m);
		}	
		
		return menues;
	}

}
