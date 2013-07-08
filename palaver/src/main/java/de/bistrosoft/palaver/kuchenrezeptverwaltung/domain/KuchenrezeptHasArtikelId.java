package de.bistrosoft.palaver.kuchenrezeptverwaltung.domain;

/**
 * 
 * @author Christine Hartkorn
 * 
 */

public class KuchenrezeptHasArtikelId implements java.io.Serializable {

	// Variablen
	private static final long serialVersionUID = -6342617785477300931L;

	private int kuchenrezeptFk;
	private int artikelFk;

	// Konstruktoren
	public KuchenrezeptHasArtikelId() {
	}

	public KuchenrezeptHasArtikelId(int kuchenrezeptFk, int artikelFk) {
		this.kuchenrezeptFk = kuchenrezeptFk;
		this.artikelFk = artikelFk;
	}

	// Getter und Setter
	public int getKuchenrezeptFk() {
		return this.kuchenrezeptFk;
	}

	public void setKuchenrezeptFk(int kuchenrezeptFk) {
		this.kuchenrezeptFk = kuchenrezeptFk;
	}

	public int getArtikelFk() {
		return this.artikelFk;
	}

	public void setArtikelFk(int artikelFk) {
		this.artikelFk = artikelFk;
	}

	// toString
	@Override
	public String toString() {
		return "KuchenrezeptHasArtikelId [kuchenrezeptFk=" + kuchenrezeptFk
				+ ", artikelFk=" + artikelFk + "]";
	}

	// Hash-Code
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + artikelFk;
		result = prime * result + kuchenrezeptFk;
		return result;
	}

	// Equals
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KuchenrezeptHasArtikelId other = (KuchenrezeptHasArtikelId) obj;
		if (artikelFk != other.artikelFk)
			return false;
		if (kuchenrezeptFk != other.kuchenrezeptFk)
			return false;
		return true;
	}

}
