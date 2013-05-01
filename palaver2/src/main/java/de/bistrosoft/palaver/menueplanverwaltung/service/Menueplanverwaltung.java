package de.bistrosoft.palaver.menueplanverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;
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
	
	public Menueplan getMenueplanByWeek(Week week){
		Menueplan mpl=null;
		try {
			mpl = super.getMenueplanByWeek(week);
		} catch (ConnectException | DAOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mpl;
	}

	public void persist(Menueplan menueplan) {
		List<MenueComponent> menues = menueplan.getMenues();
		for (MenueComponent mc : menues){
			if(mc.isChanged()){
				try {
					super.createMenueForMenueplan(menueplan, mc.getMenue(), mc.getCol(), mc.getRow());
				} catch (ConnectException | DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

//	public List<Rezept> getAllRezept() throws ConnectException, DAOException,
//			SQLException {
//		List<Rezept> result = null;
//
//		result = super.getAllRezept();
//
//		return result;
//	}
//
//	public Rezept getRezeptById(Long id) throws ConnectException, DAOException,
//			SQLException {
//		Rezept result = null;
//
//		result = super.getRezeptById(id);
//
//		return result;
//	}
//
//	public List<Rezept> getRezeptByName(String name) throws ConnectException,
//			DAOException, SQLException {
//		List<Rezept> result = null;
//
//		result = super.getRezeptByName(name);
//
//		return result;
//	}

//	public void createRezept(Rezept Rezept) throws ConnectException,
//			DAOException, SQLException {
//		super.createRezept(Rezept);
//	}

//	public void updateRezept(Rezept Rezept) throws ConnectException,
//			DAOException {
//		super.updateRezept(Rezept);
//	}

//	public List<Zubereitung> getAllZubereitung() throws ConnectException,
//			DAOException, SQLException {
//		List<Zubereitung> result = null;
//
//		result = ZubereitungDAO.getInstance().getAllZubereitung();
//
//		return result;
//	}
//
//	public List<Fussnote> getAllFussnote() throws ConnectException,
//			DAOException, SQLException {
//		List<Fussnote> result = null;
//
//		result = FussnoteDAO.getInstance().getAllFussnote();
//
//		return result;
//	}
//
//	public List<Rezeptart> Rezeptart() throws ConnectException, DAOException,
//			SQLException {
//		List<Rezeptart> result = null;
//
//		result = RezeptartDAO.getInstance().getAllRezeptart();
//
//		return result;
//	}
//
//	public List<Geschmack> Geschmack() throws ConnectException, DAOException,
//			SQLException {
//		List<Geschmack> result = null;
//
//		result = GeschmackDAO.getInstance().getAllGeschmack();
//
//		return result;
//	}

}
