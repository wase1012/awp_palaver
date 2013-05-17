package de.hska.awp.palaver2.mitarbeiterverwaltung.domain;

public class MitarbeiterHasRollen {
	
//	private Long id;
	private Long rollen_fk;
	private Long mitarbeiter_fk;
	
	public MitarbeiterHasRollen(){
		super();
	}
	
	public MitarbeiterHasRollen(Long rollen_fk, Long mitarbeiter_fk) {
		super();
		this.rollen_fk = rollen_fk;
		this.mitarbeiter_fk = mitarbeiter_fk;
	}
	
//	public MitarbeiterHasRollen(Long id, Long rollen_fk, Long mitarbeiter_fk) {
//		super();
//		this.id = id;
//		this.rollen_fk = rollen_fk;
//		this.mitarbeiter_fk = mitarbeiter_fk;
//	}

//	/**
//	 * @return the id
//	 */
//	public Long getId() {
//		return id;
//	}
//
//	/**
//	 * @param id the id to set
//	 */
//	public void setId(Long id) {
//		this.id = id;
//	}

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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MitarbeiterHasRollen [rollen_fk=" + rollen_fk
				+ ", mitarbeiter_fk=" + mitarbeiter_fk + "]";
	}
	

}
