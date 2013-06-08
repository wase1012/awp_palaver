package de.bistrosoft.palaver.kuchenrezeptverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.bistrosoft.palaver.data.KuchenplanDAO;
import de.bistrosoft.palaver.data.MenueplanDAO;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.Kuchenplan;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.Kuchenrezept;
import de.bistrosoft.palaver.menueplanverwaltung.KochInMenueplan;
import de.bistrosoft.palaver.menueplanverwaltung.MenueComponent;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menueplan;
import de.bistrosoft.palaver.util.Week;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;

public class Kuchenplanverwaltung extends KuchenplanDAO {

	private static Kuchenplanverwaltung instance = null;

	private Kuchenplanverwaltung() {
		super();
	}

	public static Kuchenplanverwaltung getInstance() {
		if (instance == null) {
			instance = new Kuchenplanverwaltung();
		}
		return instance;
	}

//	public Kuchenplan getKuchenplanByWeekWithItems(Week week) {
//		Kuchenplan kpl = null;
//		try {
//			kpl = super.getKuchenplanByWeekWithItems(week);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return kpl;
//	}
//
//	public void persist(Kuchenplan kuchenplan) {
//		try {
//			if (kuchenplan.getId() == null) {
//				super.createKuchenplan(kuchenplan);
//				// TODO: Id anders ziehen
//				kuchenplan.setId(super.getKuchenplanByWeekWithItems(
//						kuchenplan.getWeek()).getId());
//			}
//			super.deleteItemsByKuchenplan(kuchenplan);
//			for (Kuchenrezept kr : kuchenplan.getKuchenrezepte()) {
//
////				super.createKuchenrezeptForKuchenplan(kuchenplan, kr.getKuchenrezept(),
////						mc.getAngezeigterName(), mc.getCol(), mc.getRow());
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//	
//	public void deleteItemsByKuchenplan(Kuchenplan kpl) {
//		try {
//			super.deleteItemsByKuchenplan(kpl);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
