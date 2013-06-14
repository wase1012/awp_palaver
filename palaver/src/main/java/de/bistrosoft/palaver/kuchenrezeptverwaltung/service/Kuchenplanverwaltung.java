package de.bistrosoft.palaver.kuchenrezeptverwaltung.service;

import de.bistrosoft.palaver.data.KuchenplanDAO;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.Kuchenplan;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.KuchenplanHasKuchenrezept;

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
	
	public void persist(Kuchenplan kuchenplan) {
		try {
			if (kuchenplan.getId() == null) {
				super.createKuchenplan(kuchenplan);
				// TODO: Id anders ziehen
				kuchenplan.setId(super.getKuchenplanByWeekWithItems(
						kuchenplan.getWeek()).getId());
			}
			super.deleteItemsByKuchenplan(kuchenplan);
			for (KuchenplanHasKuchenrezept kc : kuchenplan.getKuchenrezepte()) {

				super.createKuchenrezepteForKuchenplan(kuchenplan, kc.getKuchenrezept(),
						kc.getKuchenname(), kc.getTag(), kc.getAnzahl());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
