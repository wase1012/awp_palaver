package de.bistrosoft.palaver.menueplanverwaltung.domain;

import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;

public class MenueHasRezept implements java.io.Serializable {

	private MenueHasRezeptId id;
	private Menue menue;
	private Rezept rezept;
	private Boolean hauptgericht;

	public MenueHasRezept() {
	}

	public MenueHasRezept(MenueHasRezeptId id, Menue menue, Rezept rezept) {
		this.id = id;
		this.menue = menue;
		this.rezept = rezept;
	}

	public MenueHasRezept(Menue menue, Rezept rezept) {

		this.menue = menue;
		this.rezept = rezept;
	}

	public MenueHasRezept(Menue menue, Rezept rezept, Boolean hauptgericht) {

		this.menue = menue;
		this.rezept = rezept;
		this.hauptgericht = hauptgericht;
	}

	public MenueHasRezeptId getId() {
		return this.id;
	}

	public void setId(MenueHasRezeptId id) {
		this.id = id;
	}

	public Rezept getRezept() {
		return rezept; // /stand bei beidem noch this.
	}

	public void setRezept(Rezept rezept) {
		this.rezept = rezept;
	}

	public Menue getMenue() {
		return menue;
	}

	public void setMenue(Menue menue) {
		this.menue = menue;
	}

	public Boolean getHauptgericht() {
		return hauptgericht;
	}

	public void setHauptgericht(Boolean hauptgericht) {
		this.hauptgericht = hauptgericht;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rezept == null) ? 0 : rezept.hashCode());
		result = prime * result + ((menue == null) ? 0 : menue.hashCode());
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
		MenueHasRezept other = (MenueHasRezept) obj;
		if (rezept == null) {
			if (other.rezept != null)
				return false;
		} else if (!rezept.equals(other.rezept))
			return false;
		if (menue == null) {
			if (other.menue != null)
				return false;
		} else if (!menue.equals(other.menue))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "menueHasFussnote [fussnote=" + rezept + ", menue=" + menue
				+ "]";
	}

}
