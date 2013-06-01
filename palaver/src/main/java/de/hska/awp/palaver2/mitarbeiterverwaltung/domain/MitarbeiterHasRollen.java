package de.hska.awp.palaver2.mitarbeiterverwaltung.domain;

/**
 * Die Klasse repräsentiert die Beziehungstabelle zwischen Mitarbeitern und
 * Rollen.
 * 
 * @author Christian Barth
 * 
 */
public class MitarbeiterHasRollen {

	private Long rollen_fk;
	private Long mitarbeiter_fk;

	public MitarbeiterHasRollen() {
		super();
	}

	public MitarbeiterHasRollen(Long rollen_fk, Long mitarbeiter_fk) {
		super();
		this.rollen_fk = rollen_fk;
		this.mitarbeiter_fk = mitarbeiter_fk;
	}

	public Long getRollen_fk() {
		return rollen_fk;
	}

	public void setRollen_fk(Long rollen_fk) {
		this.rollen_fk = rollen_fk;
	}

	public Long getMitarbeiter_fk() {
		return mitarbeiter_fk;
	}

	public void setMitarbeiter_fk(Long mitarbeiter_fk) {
		this.mitarbeiter_fk = mitarbeiter_fk;
	}

	@Override
	public String toString() {
		return "MitarbeiterHasRollen [rollen_fk=" + rollen_fk + ", mitarbeiter_fk=" + mitarbeiter_fk + "]";
	}

}
