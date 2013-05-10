package de.bistrosoft.palaver.menueplanverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;
import de.bistrosoft.palaver.data.MenueDAO;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.menueplanverwaltung.domain.MenueHasFussnote;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;


public class Menueverwaltung extends MenueDAO{
	
	private static Menueverwaltung instance = null;

	private Menueverwaltung() {
		super();
	}

	public static Menueverwaltung getInstance() {
		if (instance == null) {
			instance = new Menueverwaltung();
		}
		return instance;
	}
	
	public List<Menue> getAllMenues() {
		try {
			return super.getAllMenues();
		} catch (ConnectException | DAOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	public void createMenue(Menue menue) throws ConnectException,
	DAOException, SQLException {
super.createMenue(menue);
}
	public void FussnoteAdd(MenueHasFussnote menueHasFussnote) throws ConnectException,
	DAOException, SQLException {
super.FussnoteAdd(menueHasFussnote);
}
	
}
