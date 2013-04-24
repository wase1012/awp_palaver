/**
 *  Created by Sebastian Walz
 */
package de.hska.awp.palaver2.artikelverwaltung.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.UniqueConstraint;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "kategorie", catalog = "palaver", uniqueConstraints = {
		@UniqueConstraint(columnNames = "name")})
@NamedQueries({
	@NamedQuery(name = Kategorie.FIND_KATEGORIE_BY_NAME, query = "SELECT k FROM Kategorie k WHERE k.name = :"
			+ Kategorie.PARAM_NAME),
	@NamedQuery(name = Kategorie.FIND_KATEGORIE_BY_ID, query = "Select k FROM Kategorie k WHERE k.id = :"
			+ Kategorie.PARAM_ID),
	@NamedQuery(name = Kategorie.FIND_ALL_KATEGORIE, query = "Select k FROM Kategorie k")
})
public class Kategorie implements java.io.Serializable {
	private static final long serialVersionUID = -4647006694762094989L;

	private Long id;
	private String name;

	private static final String PREFIX = "Kategorie.";

	public static final String FIND_KATEGORIE_BY_NAME = PREFIX
			+ "findKategorieByName";
	public static final String FIND_KATEGORIE_BY_ID = PREFIX
			+ "findKategorieById";
	public static final String FIND_ALL_KATEGORIE = PREFIX + "findAllKategories";
	public static final String PARAM_NAME = "name";
	public static final String PARAM_ID = "id";
	
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
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
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
	@Column(name = "name", unique = false, length = 45)
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
