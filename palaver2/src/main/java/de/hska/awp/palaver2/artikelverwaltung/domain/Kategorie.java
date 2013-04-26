/**
 *  Created by Sebastian Walz
 */
package de.hska.awp.palaver2.artikelverwaltung.domain;



public class Kategorie implements java.io.Serializable {
	private static final long serialVersionUID = -4647006694762094989L;

	private Long id;
	private String name;
	/**
	 * Standardkonstruktor
	 */
	public Kategorie() {
		super();
	}

	/**
	 * Konstruktor
	 * 
	 * @param name
	 *            Kategoriename
	 */
	public Kategorie(String name) {
		this.name = name;
	}

	/**
	 * getId + setId
	 * 
	 * @return KategorieId
	 */

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * getName + setName
	 * 
	 * @return KategorieName
	 */
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Kategorie [id=" + id + ", name=" + name + "]";
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
		Kategorie other = (Kategorie) obj;
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
	
}
