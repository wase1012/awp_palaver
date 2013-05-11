package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.hska.awp.palaver2.data.AbstractDAO;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;

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
		List<Menue> list = new ArrayList<Menue>();
		ResultSet set = getManaged(GET_ALL_MENUES);
		
		while (set.next()) {
			list.add(new Menue(set.getLong(ID), set.getString("name"), MitarbeiterDAO
					.getInstance().getMitarbeiterById(
							set.getLong("koch")).getVorname())
			);
		}	
		
		return list;
	}
	
	


}
