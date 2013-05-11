package de.bistrosoft.palaver.rezeptverwaltung.domain;

// Generated 25.04.2013 13:27:05 by Hibernate Tools 4.0.0

/**
 * RezeptHasFussnoteId generated by hbm2java
 */
public class RezeptHasFussnoteId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5568029081945354994L;
	private Long rezeptFk;
	private Long fussnoteFk;

	public RezeptHasFussnoteId() {
	}

	public RezeptHasFussnoteId(Long rezeptFk, Long fussnoteFk) {
		this.rezeptFk = rezeptFk;
		this.fussnoteFk = fussnoteFk;
	}

	public Long getRezeptFk() {
		return this.rezeptFk;
	}

	public void setRezeptFk(Long rezeptFk) {
		this.rezeptFk = rezeptFk;
	}

	public Long getFussnoteFk() {
		return this.fussnoteFk;
	}

	public void setFussnoteFk(Long fussnoteFk) {
		this.fussnoteFk = fussnoteFk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fussnoteFk == null) ? 0 : fussnoteFk.hashCode());
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
		RezeptHasFussnoteId other = (RezeptHasFussnoteId) obj;
		if (fussnoteFk == null) {
			if (other.fussnoteFk != null)
				return false;
		} else if (!fussnoteFk.equals(other.fussnoteFk))
			return false;
		if (rezeptFk == null) {
			if (other.rezeptFk != null)
				return false;
		} else if (!rezeptFk.equals(other.rezeptFk))
			return false;
		return true;
	}



}
