package de.bistrosoft.palaver.menueplanverwaltung;

import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;

public class KochInMenueplan {
	private Mitarbeiter koch;
	private Integer spalte;
	private Integer position;


	public KochInMenueplan(Mitarbeiter koch, Integer col, Integer position) {
		this.koch = koch;
		this.spalte = col;
		this.position = position;
	}

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

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}
}
