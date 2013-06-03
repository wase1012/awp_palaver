package de.hska.awp.palaver2.mitarbeiterverwaltung.domain;

/**
 * Die Klasse repräsentiert die Beziehungstabelle zwischen Mitarbeitern und
 * Rollen.
 * 
 * @author Christian Barth
 * 
 */
public class MitarbeiterHasRollen {

	private Long rollenfk;
	private Long mitarbeiterfk;

	public MitarbeiterHasRollen() {
		super();
	}

	public MitarbeiterHasRollen(Long rollenfk, Long mitarbeiterfk) {
		super();
		this.rollenfk = rollenfk;
		this.mitarbeiterfk = mitarbeiterfk;
	}

	public Long getRollenfk() {
		return rollenfk;
	}

	public void setRollenfk(Long rollenfk) {
		this.rollenfk = rollenfk;
	}

	public Long getMitarbeiterfk() {
		return mitarbeiterfk;
	}

	public void setMitarbeiterfk(Long mitarbeiterfk) {
		this.mitarbeiterfk = mitarbeiterfk;
	}

	@Override
	public String toString() {
		return "MitarbeiterHasRollen [rollenfk=" + rollenfk + ", mitarbeiterfk=" + mitarbeiterfk + "]";
	}

}
