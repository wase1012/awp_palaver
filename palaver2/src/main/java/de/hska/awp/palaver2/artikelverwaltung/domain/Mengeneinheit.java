package de.hska.awp.palaver2.artikelverwaltung.domain;

import java.util.HashSet;
import java.util.Set;

import de.hska.awp.palaver2.rezeptverwaltung.domain.RezeptHasArtikel;

/**
 * 
 * @author bach1014
 *
 */

public class Mengeneinheit implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5210068506937506344L;

	private static final String PREFIX = "Mengeneinheit.";

	public static final String FIND_MENGENEINHEIT_BY_NAME = PREFIX
			+ "findMengeneinheitByName";
	public static final String FIND_MENGENEINHEIT_BY_ID = PREFIX
			+ "findMengeneinheitById";
	public static final String FIND_MENGENEINHEIT_BY_KURZ = PREFIX
			+ "findMengeneinheitByKurz";
	public static final String FIND_ALL_MENGENEINHEIT = PREFIX + "findAllMengeneinheit";
	public static final String PARAM_NAME = "name";
	public static final String PARAM_ID = "id";
	public static final String PARAM_KURZ = "kurz";
	
	private Long id;
	private String name;
	private String kurz;
	private Set<RezeptHasArtikel> rezeptHasArtikels = new HashSet<RezeptHasArtikel>(
			0);
	private Set<Artikel> artikels = new HashSet<Artikel>(0);

	public Mengeneinheit() {
	}

	public Mengeneinheit(String name, String kurz,
			Set<RezeptHasArtikel> rezeptHasArtikels, Set<Artikel> artikels) {
		this.name = name;
		this.kurz = kurz;
		this.rezeptHasArtikels = rezeptHasArtikels;
		this.artikels = artikels;
	}

	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKurz() {
		return this.kurz;
	}

	public void setKurz(String kurz) {
		this.kurz = kurz;
	}

	public Set<RezeptHasArtikel> getRezeptHasArtikels() {
		return this.rezeptHasArtikels;
	}

	public void setRezeptHasArtikels(Set<RezeptHasArtikel> rezeptHasArtikels) {
		this.rezeptHasArtikels = rezeptHasArtikels;
	}

	public Set<Artikel> getArtikels() {
		return this.artikels;
	}

	public void setArtikels(Set<Artikel> artikels) {
		this.artikels = artikels;
	}

	
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mengeneinheit other = (Mengeneinheit) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Mengeneinheit [id=" + id + ", name=" + name + ", kurz=" + kurz
				+ "]";
	}

}
