package de.bistrosoft.palaver.kuchenrezeptverwaltung.domain;

import java.util.List;
import de.bistrosoft.palaver.util.Week;

/**
 * 
 * @author Christine Hartkorn
 * 
 */

public class Kuchenplan {
	
	// Variablen
	private Long id;
	private Week week;
	private List<KuchenplanHasKuchenrezept> kuchenrezepte;

	// Konstruktoren
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

	// Getter- und Setter
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
