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
	String fehlermeldung;

	public MenueplanRegel() {

	}

	public MenueplanRegel(String regeltyp, List<Integer> rows,
			List<Integer> columns, String operator, List<String> regelwerte,
			String fehlermeldung) {
		this.regeltyp = regeltyp;
		this.rows = rows;
		this.columns = columns;
		this.operator = operator;
		this.regelwerte = regelwerte;
		this.fehlermeldung = fehlermeldung;
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
		regeln.add(new MenueplanRegel("name", r, c, "enthält", rw,
				"Es darf kein Menü namens Pommes in Zeile 1 eingefügt werden!"));

		List<Integer> r2 = new ArrayList<Integer>();
		r2.add(3);
		List<Integer> c2 = new ArrayList<Integer>();
		c2.add(-1);
		List<String> rw2 = new ArrayList<String>();
		rw2.add("3");
		regeln.add(new MenueplanRegel("Kategorie", r2, c2, "enthält", rw2,
				"Es darf kein Rezept mit Kat. 3 in Zeile 1 eingefügt werden!"));

		return regeln;
	}

	public Boolean check(MenueComponent mc) {
		Menue menue = mc.getMenue();

		if (regeltyp.equals("name")) {
			return checkName(menue);
		} else if (regeltyp.equals("Kategorie")) {
			return checkKategorie(menue);
		}
		return true;
	}

	private Boolean checkKategorie(Menue menue) {
		if (menue.getRezepte() != null) {
			if (operator.equals("enthält nicht")) {
				for (Rezept rez : menue.getRezepte()) {
					if (regelwerte.indexOf(rez.getRezeptart().getId().toString()) == -1) {
						System.out.println(fehlermeldung);
						return false;
					}
				}
			} else if (operator.equals("enthält")) {
				for (Rezept rez : menue.getRezepte()) {
					if (regelwerte.indexOf(rez.getRezeptart().getId().toString()) >= 0) {
						System.out.println(fehlermeldung);
						return false;
					}
				}
			}
		}
		return true;
	}

	private Boolean checkName(Menue menue) {
		Boolean isOk = true;
		if (operator.equals("enthält nicht")) {
			if (regelwerte.indexOf(menue.getName()) == -1) {
				System.out.println(fehlermeldung);
				isOk = false;
			}
		} else if (operator.equals("enthält")) {
			if (regelwerte.indexOf(menue.getName()) >= 0) {
				System.out.println(fehlermeldung);
				isOk = false;
			}
			// for (String rw : regelwerte) {
			// if (rw.equals(menue.getName())) {
			// System.out.println(fehlermeldung);
			// return true;
			// }
			// }
		}
		return isOk;
	}

}
