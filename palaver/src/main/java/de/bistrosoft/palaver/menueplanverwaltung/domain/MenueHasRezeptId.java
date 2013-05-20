package main.java.de.bistrosoft.palaver.menueplanverwaltung.domain;

// Generated 25.04.2013 13:27:05 by Hibernate Tools 4.0.0

public class MenueHasRezeptId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5568029081945354994L;
	private Long menueFk;
	private Long rezeptFk;

	public MenueHasRezeptId() {
	}

	public MenueHasRezeptId(Long menueFk, Long rezeptFk) {
		this.menueFk = menueFk;
		this.rezeptFk = rezeptFk;
	}

	public Long getmenueFk() {
		return this.menueFk;
	}

	public void setMenueFk(Long menueFk) {
		this.menueFk = menueFk;
	}

	public Long getRezeptFk() {
		return this.rezeptFk;
	}

	public void setRezeptFk(Long fussnoteFk) {
		this.rezeptFk = fussnoteFk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((rezeptFk == null) ? 0 : rezeptFk.hashCode());
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
		MenueHasRezeptId other = (MenueHasRezeptId) obj;
		if (rezeptFk == null) {
			if (other.rezeptFk != null)
				return false;
		} else if (!rezeptFk.equals(other.rezeptFk))
			return false;
		if (menueFk == null) {
			if (other.menueFk != null)
				return false;
		} else if (!menueFk.equals(other.menueFk))
			return false;
		return true;
	}



}
