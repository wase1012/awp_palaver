package de.bistrosoft.palaver.menueplanverwaltung.domain;

import java.util.List;

import de.bistrosoft.palaver.menueplanverwaltung.KochInMenueplan;
import de.bistrosoft.palaver.menueplanverwaltung.MenueComponent;
import de.bistrosoft.palaver.util.Week;

public class Menueplan {
	private Long id;
	private Week week;
	private List<MenueComponent> menues;
	private List<KochInMenueplan> koeche;

	public Menueplan(Long id, Week week) {
		this.id = id;
		this.week = week;
		this.menues = null;
		this.koeche = null;
	}

	public Menueplan(Week week) {
		this.week = week;
	}

	public Menueplan() {

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

	public List<MenueComponent> getMenues() {
		return menues;
	}

	public void setMenues(List<MenueComponent> menues) {
		this.menues = menues;
	}

	public List<KochInMenueplan> getKoeche() {
		return koeche;
	}

	public void setKoeche(List<KochInMenueplan> koeche) {
		this.koeche = koeche;
	}
}
