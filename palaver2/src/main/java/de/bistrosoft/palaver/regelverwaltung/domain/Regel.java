package de.bistrosoft.palaver.regelverwaltung.domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;

import de.bistrosoft.palaver.menueplanverwaltung.MenueComponent;
import de.bistrosoft.palaver.menueplanverwaltung.MenueplanGridLayout;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.regelverwaltung.service.Regelverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import fi.jasoft.dragdroplayouts.DDGridLayout;

public class Regel {

	List<Integer> zeilenlist;
	String zeilen;
	List<Integer> spaltenlist;
	String spalten;
	String regeltyp;
	String operator;
	List<String> kriterienlist;
	String kriterien;
	String fehlermeldung;
	Boolean aktiv;

	public Regel() {

	}
	
	public String getZeile() {
		return zeilen;
	}
	
	public void setZeile(String zeilen) {
		this.zeilen = zeilen;
	}
	
	public String getSpalte() {
		return spalten;
	}
	
	public void setSpalte(String spalten) {
		this.spalten = spalten;
	}
	
	public List<Integer> getZeilen() {
		return zeilenlist;
	}

	public void setZeilen(List<Integer> zeilen) {
		this.zeilenlist = zeilen;
	}

	public List<Integer> getSpalten() {
		return spaltenlist;
	}

	public void setSpalten(List<Integer> spalten) {
		this.spaltenlist = spalten;
	}
	
	public String getRegeltyp() {
		return regeltyp;
	}

	public void setRegeltyp(String regeltyp) {
		this.regeltyp = regeltyp;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getKriterien() {
		return kriterien;
	}

	public void setKriterien(String kriterien) {
		this.kriterien = kriterien;
	}

	public String getFehlermeldung() {
		return fehlermeldung;
	}

	public void setFehlermeldung(String fehlermeldung) {
		this.fehlermeldung = fehlermeldung;
	}

	public Boolean getAktiv() {
		return aktiv;
	}

	public void setAktiv(Boolean aktiv) {
		this.aktiv = aktiv;
	}

	public Regel(String regeltyp, String zeilen,
			String spalten, String operator, String kriterien,
			String fehlermeldung, Boolean aktiv) {
		this.regeltyp = regeltyp;
		this.zeilen = zeilen;
		this.spalten = spalten;
		this.operator = operator;
		this.kriterien = kriterien;
		this.fehlermeldung = fehlermeldung;
		this.aktiv = aktiv;
		
//		String [] s = rows.split(",");
//		for (String row : s) {
//			rowslist.add(Integer.valueOf(s.toString()));
//		}
//		
//		String [] a = columns.split(",");
//		for (String column : a) {
//			columnslist.add(Integer.valueOf(a.toString()));
//		}
	}
	
	public Regel(String regeltyp, List<Integer> zeilen, 
			List<Integer> spalten, String operator, List<String> kriterien,
			String fehlermeldung, Boolean aktiv) {
		this.regeltyp = regeltyp;
		this.zeilenlist = zeilen;
		this.spaltenlist = spalten;
		this.operator = operator;
		this.kriterienlist = kriterien;
		this.fehlermeldung = fehlermeldung;
		this.aktiv = aktiv;
	}

	// Testdaten
	public static List<Regel> getTestRegeln() {
		List<Regel> regeln = new ArrayList<Regel>();
		List<Integer> r = new ArrayList<Integer>();
		r.add(2);
		List<Integer> c = new ArrayList<Integer>();
		c.add(-1);
		List<String> rw = new ArrayList<String>();
		rw.add("Pommes");
		regeln.add(new Regel("name", r, c, "enthält", rw,
				"Es darf kein Menü namens Pommes in Zeile 1 eingefügt werden! ", true));

		List<Integer> r2 = new ArrayList<Integer>();
		r2.add(3);
		r2.add(4);
		List<Integer> c2 = new ArrayList<Integer>();
		c2.add(-1);
		List<String> rw2 = new ArrayList<String>();
		rw2.add("3");
		regeln.add(new Regel("Kategorie", r2, c2, "max", rw2,
				"Es dürfen maximal 3 menüs einer Kat eingefügt werden", true));

		return regeln;
	}

	public void addMenueComponentWarnung(MenueComponent mc){

	}
	
	public void check(MenueComponent mc, MenueplanGridLayout mp) {
		if (regeltyp.equals("name")) {
			mc.addFehlerRegel(checkName(mc, mp));
		} else if (regeltyp.equals("Kategorie")) {
			mc.addFehlerRegel(checkKategorie(mc, mp));
		} 
			
	}
	
//	public Boolean check(MenueComponent mc, MenueplanGridLayout mp) {
//		if (regeltyp.equals("name")) {
//			return checkName(mc, mp);
//		} else if (regeltyp.equals("Kategorie")) {
//			return checkKategorie(mc, mp);
//		} else
//			return true;
//	}
	
	private Regel checkKategorie(MenueComponent mc, MenueplanGridLayout mp) {
		Menue menue = mc.getMenue();
		if (menue.getRezepte() != null) {
			if (operator.equals("enthält nicht")) {
				for (Rezept rez : menue.getRezepte()) {
					if (kriterienlist.indexOf(rez.getRezeptart().getId()
							.toString()) == -1) {
						System.out.println(fehlermeldung);
						
						return this;
					}
				}
			} else if (operator.equals("enthält")) {
				for (Rezept rez : menue.getRezepte()) {
					if (kriterienlist.indexOf(rez.getRezeptart().getId()
							.toString()) >= 0) {
						System.out.println(fehlermeldung);
						
						return this;
					}
				}
			} else if (operator.equals("max")) {
				int count = 0;
				int maxValue = Integer.MAX_VALUE;
				try {
					maxValue = Integer.parseInt(kriterienlist.get(0));
				} catch (NumberFormatException e) {
				      //do something! anything to handle the exception.
				}
				
				DDGridLayout grid = mp.layout;
				for (int col = 0; col < grid.getColumns(); ++col) {
					for (int row = 0; row < grid.getRows(); ++row) {
						if ((zeilenlist.indexOf(row) >= 0 || zeilenlist.indexOf(-1) >= 0) && (spaltenlist.indexOf(col) >= 0 || spaltenlist.indexOf(-1) >= 0)) {
							if (grid.getComponent(col, row) instanceof MenueComponent) {
								MenueComponent tmp = (MenueComponent) grid.getComponent(col, row);
								if (mc.getMenue().getRezepte().get(0).getRezeptart().equals(tmp.getMenue().getRezepte().get(0).getRezeptart())) {
									++count;
									if (count > maxValue) {
										System.out.println(fehlermeldung);
										
										return this;
									}
								}
							}
						}
					}
				}
			} 
		}
		return null;
	}


	private Regel checkName(MenueComponent mc, MenueplanGridLayout mp) {
		Menue menue = mc.getMenue();
		
		if (operator.equals("enthält nicht")) {
			if (kriterienlist.indexOf(menue.getName()) == -1) {
				System.out.println(fehlermeldung);
				
				return this;
			}
		} else if (operator.equals("enthält")) {
			if (kriterienlist.indexOf(menue.getName()) >= 0) {
				System.out.println(fehlermeldung);

				return this;
			}
		}
		return null;
	}
	
	public static void speichern(String regeltyp, String zeile, String spalte,
			String operator, String kriterium, String fehlermeldung, Boolean aktiv){
    	
    	Regel regel = new Regel();
    	
    	regel.setZeile(zeile);
    	regel.setSpalte(spalte);
    	regel.setRegeltyp(regeltyp);
    	regel.setOperator(operator);
    	regel.setKriterien(kriterium);
    	regel.setFehlermeldung(fehlermeldung);
    	regel.setAktiv(aktiv);
    	
			try {
				Regelverwaltung.getInstance().createRegel(regel);
			} catch (ConnectException | DAOException | SQLException e) {
				e.printStackTrace();
			} 

    }
}
