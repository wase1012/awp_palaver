package main.java.de.bistrosoft.palaver.menueplanverwaltung.domain;

// Generated 25.04.2013 13:27:05 by Hibernate Tools 4.0.0

public class MenueHasFussnoteId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5568029081945354994L;
	private Long menueFk;
	private Long fussnoteFk;

	public MenueHasFussnoteId() {
	}

	public MenueHasFussnoteId(Long menueFk, Long fussnoteFk) {
		this.menueFk = menueFk;
		this.fussnoteFk = fussnoteFk;
	}

	public Long getRezeptFk() {
		return this.menueFk;
	}

	public void setMenueFk(Long menueFk) {
		this.menueFk = menueFk;
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
				+ ((menueFk == null) ? 0 : menueFk.hashCode());
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
		MenueHasFussnoteId other = (MenueHasFussnoteId) obj;
		if (fussnoteFk == null) {
			if (other.fussnoteFk != null)
				return false;
		} else if (!fussnoteFk.equals(other.fussnoteFk))
			return false;
		if (menueFk == null) {
			if (other.menueFk != null)
				return false;
		} else if (!menueFk.equals(other.menueFk))
			return false;
		return true;
	}



}
