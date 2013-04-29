package de.bistrosoft.palaver.menueplanverwaltung.domain;

import java.util.List;

import de.bistrosoft.palaver.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.bistrosoft.palaver.util.Week;

public class Menueplan {
	private Long id;
	private Week week;
	private List<MenueplanItem> menues;
	private List<Mitarbeiter> koeche;
	
	public Menueplan(Long id, Week week){
		this.id = id;
		this.week = week;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Week getWeek() {
		return week;
	}
	
	public void setWeek(Week week) {
		this.week = week;
	}
	
	public List<MenueplanItem> getMenues() {
		return menues;
	}
	
	public void setMenues(List<MenueplanItem> menues) {
		this.menues = menues;
	}
	
	public List<Mitarbeiter> getKoeche() {
		return koeche;
	}
	
	public void setKoeche(List<Mitarbeiter> koeche) {
		this.koeche = koeche;
	}
}
