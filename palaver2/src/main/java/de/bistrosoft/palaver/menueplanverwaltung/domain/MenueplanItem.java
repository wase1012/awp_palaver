package de.bistrosoft.palaver.menueplanverwaltung.domain;

public class MenueplanItem {
	private Menue menue;
	private Integer zeile;
	private Integer spalte;
	
	public Menue getMenue() {
		return menue;
	}
	
	public void setMenue(Menue menue) {
		this.menue = menue;
	}
	
	public Integer getZeile() {
		return zeile;
	}
	
	public void setZeile(Integer zeile) {
		this.zeile = zeile;
	}
	
	public Integer getSpalte() {
		return spalte;
	}
	
	public void setSpalte(Integer spalte) {
		this.spalte = spalte;
	}
}
