package de.bistrosoft.palaver.menueplanverwaltung;

import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;

public class MenueplanRegel {

	List<Integer> rows;
	List<Integer> columns;
	String regeltyp;
	String operator;
	List<String> regelwerte;

	public MenueplanRegel() {

	}

	public MenueplanRegel(String regeltyp, List<Integer> rows,
			List<Integer> columns, String operator, List<String> regelwerte) {
		this.regeltyp = regeltyp;
		this.rows = rows;
		this.columns = columns;
		this.operator = operator;
		this.regelwerte = regelwerte;
	}

	// Testdaten
	public static List<MenueplanRegel> getTestRegeln() {
		List<MenueplanRegel> regeln = new ArrayList<MenueplanRegel>();
		List<Integer> r = new ArrayList<Integer>();
		r.add(2);
		List<Integer> c = new ArrayList<Integer>();
		c.add(-1);
		List<String> rw = new ArrayList<String>();
		rw.add("Pommes");
		regeln.add(new MenueplanRegel("name", r, c, "enthält nicht", rw));

		return regeln;
	}

	public void check(MenueComponent mc) {
		Menue menue = mc.getMenue();

		if (regeltyp.equals("name")) {
			if (operator.equals("enthält nicht")) {
				for (String rw : regelwerte) {
					if (rw.equals(menue.getName())) {
						System.out.println("Nicht erlaubt");
					}
				}
			}
		} else if (regeltyp.equals("Kategorie")) {
			for (Rezept rez : menue.getRezepte()) {
				if (rez.getRezeptart().equals("")) {
					
				}
					
				// TODO: Kategorie vergleich implementieren
			}
		}
	}

}
