package de.bistrosoft.palaver.menueplanverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.bistrosoft.palaver.data.MenueplanDAO;
import de.bistrosoft.palaver.menueplanverwaltung.MenueComponent;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menueplan;
import de.bistrosoft.palaver.util.Week;

public class Menueplanverwaltung extends MenueplanDAO {

	private static Menueplanverwaltung instance = null;

	private Menueplanverwaltung() {
		super();
	}

	public static Menueplanverwaltung getInstance() {
		if (instance == null) {
			instance = new Menueplanverwaltung();
		}
		return instance;
	}
	
	public Menueplan getMenueplanByWeekWithItems(Week week){
		Menueplan mpl=null;
		try {
			mpl = super.getMenueplanByWeekWithItems(week);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mpl;
	}

	public void persist(Menueplan menueplan) {
		if(menueplan.getId()==null){
			try {
				super.createMenueplan(menueplan);
				menueplan.setId(super.getMenueplanByWeekWithItems(menueplan.getWeek()).getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			super.deleteItemsByMenueplan(menueplan);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		for (MenueComponent mc : menueplan.getMenues()){

			try {
				super.createMenueForMenueplan(menueplan, mc.getMenue(), mc.getCol(), mc.getRow());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void deleteItemsByMenueplan(Menueplan mpl){
		try {
			super.deleteItemsByMenueplan(mpl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
