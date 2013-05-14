package de.bistrosoft.palaver.menueplanverwaltung;

import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import fi.jasoft.dragdroplayouts.DDGridLayout;

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
				"Es darf kein Menü namens Pommes in Zeile 1 eingefügt werden! "));

		List<Integer> r2 = new ArrayList<Integer>();
		r2.add(3);
		r2.add(4);
		List<Integer> c2 = new ArrayList<Integer>();
		c2.add(-1);
		List<String> rw2 = new ArrayList<String>();
		rw2.add("3");
		regeln.add(new MenueplanRegel("Kategorie", r2, c2, "max", rw2,
				"Es dürfen maximal 3 menüs einer Kat eingefügt werden"));

		return regeln;
	}

	public Boolean check(MenueComponent mc, MenueplanGridLayout mp) {
		if (regeltyp.equals("name")) {
			return checkName(mc, mp);
		} else if (regeltyp.equals("Kategorie")) {
			return checkKategorie(mc, mp);
		} else
			return true;
	}

	private Boolean checkKategorie(MenueComponent mc, MenueplanGridLayout mp) {
		Menue menue = mc.getMenue();
		if (menue.getRezepte() != null) {
			if (operator.equals("enthält nicht")) {
				for (Rezept rez : menue.getRezepte()) {
					if (regelwerte.indexOf(rez.getRezeptart().getId()
							.toString()) == -1) {
						System.out.println(fehlermeldung);
						List<String> fehlermeldungen = new ArrayList<>();
						fehlermeldungen.add(fehlermeldung);
						mc.setFehlermeldungen(fehlermeldungen);
						return false;
					}
				}
			} else if (operator.equals("enthält")) {
				for (Rezept rez : menue.getRezepte()) {
					if (regelwerte.indexOf(rez.getRezeptart().getId()
							.toString()) >= 0) {
						System.out.println(fehlermeldung);
						List<String> fehlermeldungen = new ArrayList<>();
						fehlermeldungen.add(fehlermeldung);
						mc.setFehlermeldungen(fehlermeldungen);
						return false;
					}
				}
			} else if (operator.equals("max")) {
				int count = 0;
				int maxValue = Integer.MAX_VALUE;
				try {
					maxValue = Integer.parseInt(regelwerte.get(0));
				} catch (NumberFormatException e) {
				      //do something! anything to handle the exception.
				}
				
				DDGridLayout grid = mp.layout;
				for (int col = 0; col < grid.getColumns(); ++col) {
					for (int row = 0; row < grid.getRows(); ++row) {
						if ((rows.indexOf(row) >= 0 || rows.indexOf(-1) >= 0) && (columns.indexOf(col) >= 0 || columns.indexOf(-1) >= 0)) {
							if (grid.getComponent(col, row) instanceof MenueComponent) {
								MenueComponent tmp = (MenueComponent) grid.getComponent(col, row);
								if (mc.getMenue().getRezepte().get(0).getRezeptart().equals(tmp.getMenue().getRezepte().get(0).getRezeptart())) {
									++count;
									if (count > maxValue) {
										System.out.println(fehlermeldung);
										List<String> fehlermeldungen = new ArrayList<>();
										fehlermeldungen.add(fehlermeldung);
										mc.setFehlermeldungen(fehlermeldungen);
										return false;
									}
								}

							}
						}
					}
				}
			}
		}
		return true;
	}

	private Boolean checkName(MenueComponent mc, MenueplanGridLayout mp) {
		Menue menue = mc.getMenue();
		Boolean isOk = true;
		if (operator.equals("enthält nicht")) {
			if (regelwerte.indexOf(menue.getName()) == -1) {
				System.out.println(fehlermeldung);
				List<String> fehlermeldungen = new ArrayList<>();
				fehlermeldungen.add(fehlermeldung);
				mc.setFehlermeldungen(fehlermeldungen);
				isOk = false;
			}
		} else if (operator.equals("enthält")) {
			if (regelwerte.indexOf(menue.getName()) >= 0) {
				System.out.println(fehlermeldung);
				List<String> fehlermeldungen = new ArrayList<>();
				fehlermeldungen.add(fehlermeldung);
				mc.setFehlermeldungen(fehlermeldungen);
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
