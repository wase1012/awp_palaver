package de.bistrosoft.palaver.kuchenrezeptverwaltung.domain;

import java.util.List;
import de.bistrosoft.palaver.util.Week;

public class Kuchenplan {
	private Long id;
	private Week week;
	private List<KuchenplanHasKuchenrezept> kuchenrezepte;

	public Kuchenplan(Long id, Week week) {
		this.id = id;
		this.week = week;
		this.kuchenrezepte = null;
	}

	public Kuchenplan(Week week) {
		this.week = week;
	}

	public Kuchenplan() {

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
	
	public List<KuchenplanHasKuchenrezept> getKuchenrezepte() {
		return kuchenrezepte;
	}

	public void setKuchenrezepte(List<KuchenplanHasKuchenrezept> kuchenrezepte) {
		this.kuchenrezepte = kuchenrezepte;
	}

}
