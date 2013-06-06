package de.bistrosoft.palaver.rezeptverwaltung.domain;

// Generated 25.04.2013 13:27:05 by Hibernate Tools 4.0.0

/**
 * RezeptHasFussnoteId generated by hbm2java
 */
public class RezeptHasZubereitungId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5568029081945354994L;
	private Long rezeptFk;
	private Long zubereitungFk;

	public RezeptHasZubereitungId() {
	}

	public RezeptHasZubereitungId(Long rezeptFk, Long zubereitungFk) {
		this.rezeptFk = rezeptFk;
		this.zubereitungFk = zubereitungFk;
	}

	public Long getRezeptFk() {
		return this.rezeptFk;
	}

	public void setRezeptFk(Long rezeptFk) {
		this.rezeptFk = rezeptFk;
	}

	public Long getZubereitungFk() {
		return this.zubereitungFk;
	}

	public void setZubereitungFk(Long zubereitungFk) {
		this.zubereitungFk = zubereitungFk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((zubereitungFk == null) ? 0 : zubereitungFk.hashCode());
		result = prime * result
				+ ((rezeptFk == null) ? 0 : rezeptFk.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RezeptHasZubereitungId other = (RezeptHasZubereitungId) obj;
		if (zubereitungFk == null) {
			if (other.zubereitungFk != null)
				return false;
		} else if (!zubereitungFk.equals(other.zubereitungFk))
			return false;
		if (rezeptFk == null) {
			if (other.rezeptFk != null)
				return false;
		} else if (!rezeptFk.equals(other.rezeptFk))
			return false;
		return true;
	}

}
