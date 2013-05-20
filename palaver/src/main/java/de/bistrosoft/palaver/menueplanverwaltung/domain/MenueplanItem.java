package main.java.de.bistrosoft.palaver.menueplanverwaltung.domain;

import java.awt.Component;

public class MenueplanItem {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3949683156254221803L;
	
	private Menue menue;
	private int zeile;
	private int spalte;
	
	public Menue getMenue() {
		return menue;
	}
	
	public void setMenue(Menue menue) {
		this.menue = menue;
	}
	
	public int getZeile() {
		return zeile;
	}
	
	public void setZeile(int zeile) {
		this.zeile = zeile;
	}
	
	public int getSpalte() {
		return spalte;
	}
	
	public void setSpalte(int spalte) {
		this.spalte = spalte;
	}
}
