package main.java.de.bistrosoft.palaver.rezeptverwaltung.domain;

// Generated 25.04.2013 13:27:05 by Hibernate Tools 4.0.0

/**
 * RezeptHasArtikelId generated by hbm2java
 */
public class RezeptHasArtikelId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6342617785477300931L;
	/**
	 * 
	 */
	private int rezeptFk;
	private int artikelFk;

	public RezeptHasArtikelId() {
	}

	public RezeptHasArtikelId(int rezeptFk, int artikelFk) {
		this.rezeptFk = rezeptFk;
		this.artikelFk = artikelFk;
	}

	public int getRezeptFk() {
		return this.rezeptFk;
	}

	public void setRezeptFk(int rezeptFk) {
		this.rezeptFk = rezeptFk;
	}

	public int getArtikelFk() {
		return this.artikelFk;
	}

	public void setArtikelFk(int artikelFk) {
		this.artikelFk = artikelFk;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RezeptHasArtikelId))
			return false;
		RezeptHasArtikelId castOther = (RezeptHasArtikelId) other;

		return (this.getRezeptFk() == castOther.getRezeptFk())
				&& (this.getArtikelFk() == castOther.getArtikelFk());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getRezeptFk();
		result = 37 * result + this.getArtikelFk();
		return result;
	}

	@Override
	public String toString() {
		return "RezeptHasArtikelId [rezeptFk=" + rezeptFk + ", artikelFk="
				+ artikelFk + "]";
	}

}
