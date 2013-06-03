package de.bistrosoft.palaver.regelverwaltung.domain;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

import de.bistrosoft.palaver.menueplanverwaltung.MenueComponent;
import de.bistrosoft.palaver.menueplanverwaltung.MenueplanGridLayout;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.regelverwaltung.service.Regelverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;
import fi.jasoft.dragdroplayouts.DDGridLayout;

public class Regel {
	Long id;
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
	public Boolean getAktiv() {
		return aktiv;
	}

	public void setAktiv(Boolean aktiv) {
		this.aktiv = aktiv;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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

	public Regel(Long id,String regeltyp, String zeilen, String spalten,
			String operator, String kriterien, String fehlermeldung,
			Boolean aktiv) {
		this.id = id;
		this.regeltyp = regeltyp;
		this.zeilen = zeilen;
		this.spalten = spalten;
		this.operator = operator;
		this.kriterien = kriterien;
		this.fehlermeldung = fehlermeldung;
		this.aktiv = aktiv;
	}

	public Regel(String regeltyp, List<Integer> zeilen, List<Integer> spalten,
			String operator, List<String> kriterien, String fehlermeldung,
			Boolean aktiv) {
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
		rw.add("3");
		regeln.add(new Regel("Fussnote",r,c,"max",rw,"Nur Vegan!",true));
		
		List<Integer> r3 = new ArrayList<Integer>();
		r3.add(2);
		List<Integer> c3 = new ArrayList<Integer>();
		c3.add(-1);
		List<String> rw3 = new ArrayList<String>();
		rw3.add("deutsch");
		regeln.add(new Regel("Geschmack",r3,c3,"enthält nicht",rw3,"Geschmack",true));
		
		List<Integer> r2 = new ArrayList<Integer>();
		r2.add(3);
		r2.add(4);
		List<Integer> c2 = new ArrayList<Integer>();
		c2.add(-1);
		List<String> rw2 = new ArrayList<String>();
		rw2.add("3");
		regeln.add(new Regel("Menueart",r2,c2,	"max",rw2,"Es d�rfen maximal 3 Men�s einer Kat in den Zeilen 2 und 3 eingef�gt werden",	true));

		return regeln;
	}

	public void check(MenueComponent mc, MenueplanGridLayout mp) {
//		if (regeltyp.equals("name")) {
//			mc.addFehlerRegel(checkName(mc, mp));
//		} else if (regeltyp.equals("Menueart")) {
//			mc.addFehlerRegel(checkMenueart(mc, mp));
//		}
		if (this.getZeilen().indexOf(mc.row) >= 0 || this.getZeilen().indexOf(-1) >= 0) {
			if (this.getSpalten().indexOf(mc.col) >= 0 || this.getSpalten().indexOf(-1) >= 0) {
				this.findeRegel(mc,mp);
			}
		}

	}
	
//	Geschmack!, Fu�note, Zubereitung, Menueart!
	
	public void findeRegel(MenueComponent mc, MenueplanGridLayout mp) {
		System.out.println("Count Fussnoten: "+mc.getMenue().getFussnoten().size());
		if (regeltyp.equals("Name")) {
			mc.addFehlerRegel(checkName(mc, mp));
		} else if (regeltyp.equals("Menueart")) {
			mc.addFehlerRegel(checkMenueart(mc, mp));
		} else if (regeltyp.equals("Geschmack")) {
			mc.addFehlerRegel(checkGeschmack(mc, mp));
		} else if (regeltyp.equals("Fussnote")) {
			mc.addFehlerRegel(checkFussnote(mc, mp));
		}

	}
	
	private Regel checkFussnote(MenueComponent mc, MenueplanGridLayout mp) {
		Menue menue = mc.getMenue();
		System.out.print(mc.col+"/");
		System.out.print(mc.row+"/");
		System.out.print("Geschmack/");
		System.out.println(menue.getGeschmack());
//		if(menue.getFussnoten()!=null){
			if (operator.equals("enthält nicht")) {	
				System.out.println("#Fussnote: enthält nicht");
				if(menue.getFussnoten()!=null && menue.getFussnoten().size()>0){
					System.out.println("#Fussnote: not null"+menue.getFussnoten().size());
					for(Fussnote fs: menue.getFussnoten()){
						System.out.println("#Fussnote:"+ fs.getName());
						if (kriterienlist.indexOf(fs.getName()) >= 0) {
							System.out.println("#Fussnote: return "+ fs.getName());
							return null;
						} return this;
					}
				} else return this;
				
			} else if (operator.equals("enthält")) {
				if(menue.getFussnoten()!=null && menue.getFussnoten().size()>0){
					for(Fussnote fs: menue.getFussnoten()){
						if (kriterienlist.indexOf(fs.getName()) == -1) {
							return null;
						}else return this;
					}
				} else return null;
				
			} else if (operator.equals("max")) {
				if(menue.getFussnoten()!=null){
					int count = 0;
					int maxValue = Integer.MAX_VALUE;
					try {
						maxValue = Integer.parseInt(kriterienlist.get(0));
					} catch (NumberFormatException e) {
						// do something! anything to handle the exception.
					}

					DDGridLayout grid = mp.layout;
					for(Fussnote fs : mc.getMenue().getFussnoten()){
						count=0;
					for (int col = 0; col < grid.getColumns(); ++col) {
						for (int row = 0; row < grid.getRows(); ++row) {
							if ((zeilenlist.indexOf(row) >= 0 || zeilenlist.indexOf(-1) >= 0)
									&& (spaltenlist.indexOf(col) >= 0 || spaltenlist.indexOf(-1) >= 0)) {
								if (grid.getComponent(col, row) instanceof MenueComponent) {
									MenueComponent tmp = (MenueComponent) grid.getComponent(col, row);
										if (tmp.getMenue().getFussnoten().indexOf(fs) >= 0) {
											if (tmp.getFehlerRegeln() != null) {
												if (tmp.getFehlerRegeln().indexOf(this) == -1) {
													++count;
													if (count > maxValue) {
														return this;
													}
												}
											} 
											else {
												++count;
												if (count > maxValue) {
													return this;
												}
											}
										}

									}
								}
							}
						}
					}
				} else return null;
				
			}
//		}
		
		return null;
	}


	private Regel checkGeschmack(MenueComponent mc, MenueplanGridLayout mp) {
		System.out.println("#Geschmack");
		Menue menue = mc.getMenue();
		System.out.print(mc.col+"/");
		System.out.print(mc.row+"/");
		System.out.print("Geschmack/");
		System.out.println(menue.getGeschmack());
		if(menue.getGeschmack()!=null){
			if (operator.equals("enthält nicht")) {
				System.out.println("ent nit");
				System.out.println(menue.getGeschmack().getName().toString());
					if (kriterienlist.indexOf(menue.getGeschmack().getName().toString()) == -1) {
						return this;
				}
			} else if (operator.equals("enth�lt")) {
					if (kriterienlist.indexOf(menue.getGeschmack().getName().toString()) >= 0) {
						return this;
					}
			} else if (operator.equals("max")) {
				int count = 0;
				int maxValue = Integer.MAX_VALUE;
				try {
					maxValue = Integer.parseInt(kriterienlist.get(0));
				} catch (NumberFormatException e) {
					// do something! anything to handle the exception.
				}

				DDGridLayout grid = mp.layout;
				for (int col = 0; col < grid.getColumns(); ++col) {
					for (int row = 0; row < grid.getRows(); ++row) {
						if ((zeilenlist.indexOf(row) >= 0 || zeilenlist.indexOf(-1) >= 0)
								&& (spaltenlist.indexOf(col) >= 0 || spaltenlist.indexOf(-1) >= 0)) {
							if (grid.getComponent(col, row) instanceof MenueComponent) {
								MenueComponent tmp = (MenueComponent) grid.getComponent(col, row);
								if (mc.getMenue().getGeschmack().equals(tmp.getMenue().getGeschmack())) {
									if (tmp.getFehlerRegeln() != null) {
										if (tmp.getFehlerRegeln().indexOf(this) == -1) {
											++count;
											if (count > maxValue) {
												return this;
											}
										}
									} else {
										++count;
										if (count > maxValue) {
											return this;
										}
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

	private Regel checkMenueart(MenueComponent mc, MenueplanGridLayout mp) {
		Menue menue = mc.getMenue();
		System.out.print(mc.col+"/");
		System.out.print(mc.row+"/");
		System.out.println(menue.getMenueart());
		if(menue.getMenueart()!=null){
			if (operator.equals("enth�lt nicht")) {
					if (kriterienlist.indexOf(menue.getMenueart().getName().toString()) == -1) {
						return this;
				}
			} else if (operator.equals("enth�lt")) {
					if (kriterienlist.indexOf(menue.getMenueart().getName().toString()) >= 0) {
						return this;
					}
			} else if (operator.equals("max")) {
				int count = 0;
				int maxValue = Integer.MAX_VALUE;
				try {
					maxValue = Integer.parseInt(kriterienlist.get(0));
				} catch (NumberFormatException e) {
					// do something! anything to handle the exception.
				}

				DDGridLayout grid = mp.layout;
				for (int col = 0; col < grid.getColumns(); ++col) {
					for (int row = 0; row < grid.getRows(); ++row) {
						if ((zeilenlist.indexOf(row) >= 0 || zeilenlist.indexOf(-1) >= 0)
								&& (spaltenlist.indexOf(col) >= 0 || spaltenlist.indexOf(-1) >= 0)) {
							if (grid.getComponent(col, row) instanceof MenueComponent) {
								MenueComponent tmp = (MenueComponent) grid.getComponent(col, row);
								if (mc.getMenue().getMenueart().equals(tmp.getMenue().getMenueart())) {
									if (tmp.getFehlerRegeln() != null) {
										if (tmp.getFehlerRegeln().indexOf(this) == -1) {
											++count;
											if (count > maxValue) {
												return this;
											}
										}
									} else {
										++count;
										if (count > maxValue) {
											return this;
										}
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

		if (operator.equals("enth�lt nicht")) {
			if (kriterienlist.indexOf(menue.getName()) == -1) {

				return this;
			}
		} else if (operator.equals("enth�lt")) {
			if (kriterienlist.indexOf(menue.getName()) >= 0) {

				return this;
			}
		}
		return null;
	}

	public static void speichern(String regeltyp, String zeile, String spalte,
			String operator, String kriterium, String fehlermeldung,
			Boolean aktiv) {

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
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void l�schen(Regel regel) {

		try {
			Regelverwaltung.getInstance().deleteRegel(regel);
		} catch (Exception  e) {
			e.printStackTrace();
		} 
		
		Notification notification = new Notification("Regel wurde gel�scht");
		notification.setDelayMsec(500);
		notification.show(Page.getCurrent());
	}
}
