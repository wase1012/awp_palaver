//package de.bistrosoft.palaver.menueplanverwaltung;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
//import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
//import fi.jasoft.dragdroplayouts.DDGridLayout;
//
//public class MenueplanRegel {
//
//	List<Integer> rows;
//	List<Integer> columns;
//	String regeltyp;
//	String operator;
//	List<String> regelwerte;
//	String fehlermeldung;
//
//	public MenueplanRegel() {
//
//	}
//
//	public MenueplanRegel(String regeltyp, List<Integer> rows,
//			List<Integer> columns, String operator, List<String> regelwerte,
//			String fehlermeldung) {
//		this.regeltyp = regeltyp;
//		this.rows = rows;
//		this.columns = columns;
//		this.operator = operator;
//		this.regelwerte = regelwerte;
//		this.fehlermeldung = fehlermeldung;
//	}
//
//	public List<Integer> getRows() {
//		return rows;
//	}
//
//	public void setRows(List<Integer> rows) {
//		this.rows = rows;
//	}
//
//	public List<Integer> getColumns() {
//		return columns;
//	}
//
//	public void setColumns(List<Integer> columns) {
//		this.columns = columns;
//	}
//
//	public String getRegeltyp() {
//		return regeltyp;
//	}
//
//	public void setRegeltyp(String regeltyp) {
//		this.regeltyp = regeltyp;
//	}
//
//	public String getOperator() {
//		return operator;
//	}
//
//	public void setOperator(String operator) {
//		this.operator = operator;
//	}
//
//	public List<String> getRegelwerte() {
//		return regelwerte;
//	}
//
//	public void setRegelwerte(List<String> regelwerte) {
//		this.regelwerte = regelwerte;
//	}
//
//	public String getFehlermeldung() {
//		return fehlermeldung;
//	}
//
//	public void setFehlermeldung(String fehlermeldung) {
//		this.fehlermeldung = fehlermeldung;
//	}
//
//	// Testdaten
//	public static List<MenueplanRegel> getTestRegeln() {
//		List<MenueplanRegel> regeln = new ArrayList<MenueplanRegel>();
//		List<Integer> r = new ArrayList<Integer>();
//		r.add(2);
//		List<Integer> c = new ArrayList<Integer>();
//		c.add(-1);
//		List<String> rw = new ArrayList<String>();
//		rw.add("Pommes");
//		regeln.add(new MenueplanRegel("name", r, c, "enthält", rw,
//				"Es darf kein Menü namens Pommes in Zeile 1 eingefügt werden! "));
//
//		List<Integer> r2 = new ArrayList<Integer>();
//		r2.add(3);
//		r2.add(4);
//		List<Integer> c2 = new ArrayList<Integer>();
//		c2.add(-1);
//		List<String> rw2 = new ArrayList<String>();
//		rw2.add("3");
//		regeln.add(new MenueplanRegel("Kategorie", r2, c2, "max", rw2,
//				"Es dürfen maximal 3 menüs einer Kat in den Zeilen 2 und 3 eingefügt werden"));
//
//		return regeln;
//	}
//
//	public void check(MenueComponent mc, MenueplanGridLayout mp) {
//		if (regeltyp.equals("name")) {
//			mc.addFehlerRegel(checkName(mc, mp));
//		} else if (regeltyp.equals("Kategorie")) {
//			mc.addFehlerRegel(checkKategorie(mc, mp));
//		}
//
//	}
//
//	private MenueplanRegel checkKategorie(MenueComponent mc,
//			MenueplanGridLayout mp) {
//		Menue menue = mc.getMenue();
//		if (menue.getRezepte() != null) {
//			if (operator.equals("enthält nicht")) {
//				for (Rezept rez : menue.getRezepte()) {
//					if (regelwerte.indexOf(rez.getRezeptart().getId()
//							.toString()) == -1) {
//						System.out.println(fehlermeldung);
//
//						return this;
//					}
//				}
//			} else if (operator.equals("enthält")) {
//				for (Rezept rez : menue.getRezepte()) {
//					if (regelwerte.indexOf(rez.getRezeptart().getId()
//							.toString()) >= 0) {
//						System.out.println(fehlermeldung);
//
//						return this;
//					}
//				}
//			} else if (operator.equals("max")) {
//				int count = 0;
//				int maxValue = Integer.MAX_VALUE;
//				try {
//					maxValue = Integer.parseInt(regelwerte.get(0));
//				} catch (NumberFormatException e) {
//					// do something! anything to handle the exception.
//				}
//
//				DDGridLayout grid = mp.layout;
//				for (int col = 0; col < grid.getColumns(); ++col) {
//					for (int row = 0; row < grid.getRows(); ++row) {
//						if ((rows.indexOf(row) >= 0 || rows.indexOf(-1) >= 0)
//								&& (columns.indexOf(col) >= 0 || columns
//										.indexOf(-1) >= 0)) {
//							if (grid.getComponent(col, row) instanceof MenueComponent) {
//								MenueComponent tmp = (MenueComponent) grid.getComponent(col, row);
//								if (mc.getMenue().getRezepte().get(0).getRezeptart().equals(tmp.getMenue().getRezepte().get(0).getRezeptart())) {
//									++count;
//									if (tmp.getFehlerRegeln() != null) {
//										if (tmp.getFehlerRegeln().indexOf(this) == -1) {
//
//											if (count > maxValue) {
//												System.out.println(fehlermeldung);
//												return this;
//											}
//										}
//									} else{
//										if (count > maxValue) {
//											System.out.println(fehlermeldung);
//											return this;
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//		return null;
//	}
//		return null;}
//
//	private MenueplanRegel checkName(MenueComponent mc, MenueplanGridLayout mp) {
//		Menue menue = mc.getMenue();
//
//		if (operator.equals("enthält nicht")) {
//			if (regelwerte.indexOf(menue.getName()) == -1) {
//				System.out.println(fehlermeldung);
//
//				return this;
//			}
//		} else if (operator.equals("enthält")) {
//			if (regelwerte.indexOf(menue.getName()) >= 0) {
//				System.out.println(fehlermeldung);
//
//				return this;
//			}
//		}
//		return null;
//	}
//}
