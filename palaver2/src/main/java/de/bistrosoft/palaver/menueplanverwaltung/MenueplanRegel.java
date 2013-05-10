package de.bistrosoft.palaver.menueplanverwaltung;

import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;

public class MenueplanRegel {

	
	List<Integer> rows;
	List<Integer> columns;
	String regelfeld;
	String operator;
	List<String> regelwerte;

	public MenueplanRegel() {

	}

	public MenueplanRegel(String regelfeld,	List<Integer> rows,	List<Integer> columns, String operator, List<String> regelwerte) {
		this.regelfeld=regelfeld;
		this.rows=rows;
		this.columns=columns;
		this.operator=operator;
		this.regelwerte=regelwerte;
	}
	
	//Testdaten
	public static List<MenueplanRegel> getTestRegeln(){
		List<MenueplanRegel> regeln = new ArrayList<MenueplanRegel>();
		List<Integer> r=new ArrayList<Integer>();
		r.add(2);
		List<Integer> c=new ArrayList<Integer>();
		c.add(-1);
		List<String> rw =new ArrayList<String>();
		rw.add("Pommes");
		regeln.add(new MenueplanRegel("name",r,c,"enthält nicht",rw));
		
		return regeln;
	}

	public void check(MenueComponent mc) {
		// DDGridLayout mp = menueplan.layout;
		//
		// final int COLUMNS = mp.getColumns();
		// final int ROWS = mp.getRows();
		//
		// for (int col=0;col<COLUMNS;++col){
		// for (int row=0;row<ROWS;++row){
		// mp.getComponent(col, row)
		// }
		// }
		System.out.println("check1");
		Menue menue = mc.getMenue();

		if (regelfeld == "name") {
			System.out.println("check2");
			for (String rw : regelwerte){
				System.out.println("check3");
				System.out.println(rw+"=="+menue.getName());
				String r=rw.toLowerCase().trim();
				String m=menue.getName().toLowerCase().trim();
				System.out.println(r+"=="+m);
				if(r.equals(m)){
					System.out.println("Nicht erlaubt");
				}
			}
		}
	}

}
