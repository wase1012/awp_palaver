package de.bistrosoft.palaver.kuchenrezeptverwaltung.domain;

/**
 * 
 * @author Jasmin Baumgartner
 * 
 */

public class KuchenrezeptHasFussnoteId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5568029081945354994L;
	private Long kuchenrezeptFk;
	private Long fussnoteFk;

	public KuchenrezeptHasFussnoteId() {
	}

	public KuchenrezeptHasFussnoteId(Long kuchenrezeptFk, Long fussnoteFk) {
		this.kuchenrezeptFk = kuchenrezeptFk;
		this.fussnoteFk = fussnoteFk;
	}

	public Long getRezeptFk() {
		return this.kuchenrezeptFk;
	}

	public void setKuchenFk(Long kuchenrezeptFk) {
		this.kuchenrezeptFk = kuchenrezeptFk;
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
		result = prime * result + ((kuchenrezeptFk == null) ? 0 : kuchenrezeptFk.hashCode());
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
		KuchenrezeptHasFussnoteId other = (KuchenrezeptHasFussnoteId) obj;
		if (fussnoteFk == null) {
			if (other.fussnoteFk != null)
				return false;
		} else if (!fussnoteFk.equals(other.fussnoteFk))
			return false;
		if (kuchenrezeptFk == null) {
			if (other.kuchenrezeptFk != null)
				return false;
		} else if (!kuchenrezeptFk.equals(other.kuchenrezeptFk))
			return false;
		return true;
	}

}
