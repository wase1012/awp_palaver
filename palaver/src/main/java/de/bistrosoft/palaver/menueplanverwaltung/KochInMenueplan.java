package de.bistrosoft.palaver.menueplanverwaltung;

import de.bistrosoft.palaver.mitarbeiterverwaltung.domain.Mitarbeiter;

public class KochInMenueplan {
	private Mitarbeiter koch;
	private Integer spalte;
	
	
	public Mitarbeiter getKoch() {
		return koch;
	}
	public void setKoch(Mitarbeiter koch) {
		this.koch = koch;
	}
	public Integer getSpalte() {
		return spalte;
	}
	public void setSpalte(Integer spalte) {
		this.spalte = spalte;
	}
}
